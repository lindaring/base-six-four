package com.lindaring.base.service;

import com.lindaring.base.client.GeolocationClientService;
import com.lindaring.base.client.dto.geolocation.Geolocation;
import com.lindaring.base.dto.ActivationRequest;
import com.lindaring.base.dto.RegisteredUser;
import com.lindaring.base.dto.UserDto;
import com.lindaring.base.dto.VisitorDto;
import com.lindaring.base.entity.ActivationCodeEntity;
import com.lindaring.base.entity.RoleEntity;
import com.lindaring.base.entity.UserEntity;
import com.lindaring.base.entity.VisitorEntity;
import com.lindaring.base.exception.ParamsException;
import com.lindaring.base.exception.TechnicalException;
import com.lindaring.base.properties.MailProperties;
import com.lindaring.base.repo.ActivationCodesRepo;
import com.lindaring.base.repo.RolesRepo;
import com.lindaring.base.repo.UsersRepo;
import com.lindaring.base.repo.VisitorsRepo;
import com.lindaring.base.utils.GeneralUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.StreamSupport;

@Slf4j
@Service
public class UserService {

    @Autowired
    private VisitorsRepo visitorsRepo;

    @Autowired
    private UsersRepo usersRepo;

    @Autowired
    private RolesRepo rolesRepo;

    @Autowired
    private ActivationCodesRepo activationCodesRepo;

    @Autowired
    private GeolocationClientService geolocationClientService;

    @Autowired
    private RabbitMQService rabbitMQService;

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private MailProperties mailProperties;

    public void registerUser(RegisteredUser user) throws TechnicalException, ParamsException {
        validateRegisterUserParams(user);
        checkUserExists(user.getUsername());
        UserEntity userEntity = persistUserInfo(user);
        ActivationCodeEntity activationCodeEntity = generateActivationCode(userEntity);
        sendRegistrationConfirmationEmail(userEntity.getUsername(), activationCodeEntity.getCode());
    }

    private void validateRegisterUserParams(RegisteredUser user) throws ParamsException {
        if (StringUtils.isEmpty(user.getUsername()) || !EmailValidator.getInstance().isValid(user.getUsername())) {
            throw new ParamsException("Enter a valid email address.");
        } else if (StringUtils.isEmpty(user.getPassword())) {
            throw new ParamsException("Enter a password.");
        } else if (ObjectUtils.isEmpty(user.getRoles())) {
            throw new ParamsException("Select at least 1 role.");
        }
    }

    private void checkUserExists(String username) throws ParamsException {
        Optional<UserEntity> user = usersRepo.getUserByUsername(username);
        if (user.isPresent()) {
            throw new ParamsException("Account already exists.");
        }
    }

    private UserEntity persistUserInfo(RegisteredUser user) throws TechnicalException {
        try {
            UserEntity newUser = new UserEntity();
            newUser.setUsername(user.getUsername());
            newUser.setPassword(GeneralUtils.encryptPassword(user.getPassword()));
            newUser.setActive(0);
            newUser.setInsertDate(new Date());
            newUser.setRoles(mapUserRoles(user));
            return usersRepo.save(newUser);
        } catch (Exception e) {
            log.error("Could persist user details.", e);
            throw new TechnicalException("Could not register user.", e);
        }
    }

    private ActivationCodeEntity generateActivationCode(UserEntity newUser) throws TechnicalException {
        try {
            ActivationCodeEntity code = new ActivationCodeEntity();
            code.setCode(UUID.randomUUID().toString().toUpperCase());
            code.setInsertDate(new Date());
            code.setUser(newUser);
            return activationCodesRepo.save(code);
        } catch (Exception e) {
            log.error("Could generate activation code.", e);
            throw new TechnicalException("Could not register user.", e);
        }
    }

    private void sendRegistrationConfirmationEmail(String destinationEmail, String activationCode) throws TechnicalException {
        if (!mailProperties.getRegister().isEnabled()) {
            log.info("Confirmation email is disabled!");
            return;
        }
        try {
            MimeMessage msg = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(msg, true);

            helper.setTo(destinationEmail);
            helper.setSubject(mailProperties.getRegister().getSubject());

            String link = mailProperties.getRegister().getLink()
                    .replaceAll("#ACTIVATION_CODE", activationCode);

            String body = mailProperties.getRegister().getBody()
                    .replaceAll("#ACTIVATION_LINK", link);
            helper.setText(body, true);

            javaMailSender.send(msg);
        } catch (MessagingException e) {
            log.error("Could send registration confirmation email.", e);
            throw new TechnicalException("Could not register user.", e);
        }
    }

    public void activateRegistration(ActivationRequest activationCode) throws TechnicalException, ParamsException {
        try {
            ActivationCodeEntity activation = getActivationCode(activationCode);
            UserEntity user = activation.getUser();
            checkAccountActive(user);
            checkActivationExpired(activation);
            activateAccount(user);
            String approvalCode = generateApprovalCode(activation);
            sendRegistrationApprovalEmail(approvalCode);
        } catch (ParamsException e) {
            throw e;
        } catch (Exception e) {
            log.error("Could not activate registration.", e);
            throw new TechnicalException("Could not activate registration.", e);
        }
    }

    private ActivationCodeEntity getActivationCode(ActivationRequest activationCode) throws TechnicalException {
        Optional<ActivationCodeEntity> codeOptional = activationCodesRepo.getActivationCodeByCode(activationCode.getCode());
        return codeOptional.orElseThrow(() -> new TechnicalException("Failed to activate registration."));
    }

    private void checkAccountActive(UserEntity user) throws ParamsException {
        if (user.getActive() == 1) {
            throw new ParamsException("Account is already activate.");
        }
    }

    private void checkActivationExpired(ActivationCodeEntity code) throws ParamsException {
        Date codeGeneratedOn = code.getInsertDate();
        Date codeExpiresOn = DateUtils.addDays(new Date(), 2);
        if (codeGeneratedOn.after(codeExpiresOn)) {
            throw new ParamsException("Activation code has expired. You need to register again.");
        }
    }

    private void activateAccount(UserEntity user) {
        user.setActive(1);
        usersRepo.save(user);
    }

    private String generateApprovalCode(ActivationCodeEntity code) {
        String approvalCode = UUID.randomUUID().toString().toUpperCase();
        code.setApprovalCode(approvalCode);
        activationCodesRepo.save(code);
        return approvalCode;
    }

    private void sendRegistrationApprovalEmail(String approvalCode) throws TechnicalException {
        if (!mailProperties.getApproval().isEnabled()) {
            log.info("Approval email is disabled!");
            return;
        }
        try {
            MimeMessage msg = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(msg, true);

            helper.setTo(mailProperties.getApproval().getTo());
            helper.setSubject(mailProperties.getRegister().getSubject());

            String link = mailProperties.getApproval().getLink()
                    .replaceAll("#APPROVAL_CODE", approvalCode);

            String body = mailProperties.getApproval().getBody()
                    .replaceAll("#APPROVAL_LINK", link);
            helper.setText(body, true);

            javaMailSender.send(msg);
        } catch (MessagingException e) {
            log.error("Could send approval confirmation email.", e);
            throw new TechnicalException("Could not register user.", e);
        }
    }

    public void approveRegistration(ActivationRequest approvalCode) throws TechnicalException, ParamsException {
        try {
            ActivationCodeEntity activation = getApprovalCode(approvalCode);
            UserEntity user = activation.getUser();
            checkAccountApproved(user);
            approveAccount(user);
            sendRegistrationWelcomeEmail(activation.getApprovalCode(), user.getUsername());
        } catch (ParamsException | TechnicalException e) {
            throw e;
        } catch (Exception e) {
            log.error("Could not approve registration.", e);
            throw new TechnicalException("Could not approve registration user.", e);
        }
    }

    private ActivationCodeEntity getApprovalCode(ActivationRequest activationCode) throws TechnicalException {
        Optional<ActivationCodeEntity> codeOptional = activationCodesRepo.getActivationCodeByApprovalCode(activationCode.getCode());
        return codeOptional.orElseThrow(() -> new TechnicalException("Failed to activate registration."));
    }

    private void checkAccountApproved(UserEntity user) throws ParamsException {
        if (user.getActive() == 0) {
            throw new ParamsException("Email has not been confirmed.");
        } else if (user.getActive() == 2) {
            throw new ParamsException("Account is already approved.");
        }
    }

    private void approveAccount(UserEntity user) {
        user.setActive(2);
        usersRepo.save(user);
    }

    private void sendRegistrationWelcomeEmail(String approvalCode, String destinationEmail) throws TechnicalException {
        if (!mailProperties.getWelcome().isEnabled()) {
            log.info("Approval email is disabled!");
            return;
        }
        try {
            MimeMessage msg = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(msg, true);

            helper.setTo(destinationEmail);
            helper.setSubject(mailProperties.getWelcome().getSubject());

            String body = mailProperties.getWelcome().getBody()
                    .replaceAll("#LOGIN_LINK", mailProperties.getWelcome().getLink());
            helper.setText(body, true);

            javaMailSender.send(msg);
        } catch (MessagingException e) {
            log.error("Could send welcome email.", e);
            throw new TechnicalException("Could not register user.", e);
        }
    }

    @Async
    public void recordVisit(HttpServletRequest httpRequest, UserDto userDto) {
        try {
            String ip = GeneralUtils.getClientIp(httpRequest);
            String userAgent = GeneralUtils.getUserAgent(httpRequest);
            String location = null;
            if (ip != null) {
                Geolocation geoInfo = geolocationClientService.getLocation(ip);
                location = getLocation(geoInfo);
            }

            VisitorDto visitor = new VisitorDto(0, ip, new Date(), userAgent, userDto.getUrl(), location);
            rabbitMQService.sendMessage(visitor);
        } catch (Exception e) {
            log.error("Could not record visitor.", e);
        }
    }

    private List<RoleEntity> mapUserRoles(RegisteredUser user) throws ParamsException {
        List<RoleEntity> roles = new ArrayList<>();

        Iterable<RoleEntity> dbRoles = rolesRepo.findAll();
        user.getRoles().forEach(roleType ->
            {
                // Map role type to role entity
                final Optional<RoleEntity> optionalRoleEntity = StreamSupport.stream(dbRoles.spliterator(), false)
                        .filter(dbRole -> dbRole.getDesc().equals(roleType.getFullDescription()))
                        .findFirst();
                // Add role to user roles list
                if (optionalRoleEntity.isPresent()) {
                    RoleEntity roleEntity = new RoleEntity();
                    roleEntity.setId(optionalRoleEntity.get().getId());
                    roleEntity.setDesc(optionalRoleEntity.get().getDesc());
                    roleEntity.setInsertDate(new Date());
                    roles.add(roleEntity);
                }
            }
        );

        if (roles.isEmpty()) {
            throw new ParamsException("Select at least 1 role.");
        }

        return roles;
    }

    private String getLocation(Geolocation geolocation) {
        StringBuilder builder = new StringBuilder();
        if (geolocation.getContinentName() != null) {
            builder.append(geolocation.getContinentName())
                    .append(", ");
        }
        if (geolocation.getCountryName() != null) {
            builder.append(geolocation.getCountryName())
                    .append(", ");
        }
        if (geolocation.getRegionName() != null) {
            builder.append(geolocation.getRegionName())
                    .append(", ");
        }
        if (geolocation.getCity() != null) {
            builder.append(geolocation.getCity())
                    .append(", ");
        }
        if (geolocation.getLocation().getCapital() != null) {
            builder.append(geolocation.getLocation().getCapital());
        }
        return builder.toString();
    }

    private List<String> getTodayVisitors() {
        LocalDateTime now = LocalDateTime.now();
        Date startDate = GeneralUtils.getStartOfDay(now);
        Date endDate = GeneralUtils.getEndOfDay(now);

        return visitorsRepo.findDistinctIpByInsertDate(startDate, endDate);
    }

    public int getTodayVisitorsCount() {
        return getTodayVisitors().size();
    }

    private List<VisitorEntity> getTodayHits() {
        LocalDateTime now = LocalDateTime.now();
        Date startDate = GeneralUtils.getStartOfDay(now);
        Date endDate = GeneralUtils.getEndOfDay(now);

        return visitorsRepo.findByInsertDate(startDate, endDate);
    }

    public int getTodayHitsCount() {
        return getTodayHits().size();
    }
}
