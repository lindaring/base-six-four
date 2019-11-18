package com.lindaring.base.service;

import com.lindaring.base.client.GeolocationClientService;
import com.lindaring.base.client.dto.geolocation.Geolocation;
import com.lindaring.base.dto.RegisteredUser;
import com.lindaring.base.dto.UserDto;
import com.lindaring.base.dto.VisitorDto;
import com.lindaring.base.entity.Role;
import com.lindaring.base.entity.User;
import com.lindaring.base.entity.Visitor;
import com.lindaring.base.exception.ParamsException;
import com.lindaring.base.exception.TechnicalException;
import com.lindaring.base.repo.RolesRepo;
import com.lindaring.base.repo.UsersRepo;
import com.lindaring.base.repo.VisitorsRepo;
import com.lindaring.base.utils.GeneralUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
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
    private GeolocationClientService geolocationClientService;

    @Autowired
    private RabbitMQService rabbitMQService;

    public void registerUser(RegisteredUser user) throws TechnicalException, ParamsException {
        //Validation params
        if (StringUtils.isEmpty(user.getUsername()) || !EmailValidator.getInstance().isValid(user.getUsername())) {
            throw new ParamsException("Enter a valid email address.");
        } else if (StringUtils.isEmpty(user.getPassword())) {
            throw new ParamsException("Enter a password.");
        } else if (ObjectUtils.isEmpty(user.getRoles())) {
            throw new ParamsException("Select at least 1 role.");
        }

        checkUserExists(user.getUsername());

        //Register user
        try {
            User newUser = new User();
            newUser.setUsername(user.getUsername());
            newUser.setPassword(GeneralUtils.encryptPassword(user.getPassword()));
            newUser.setActive(0);
            newUser.setRoles(getUserRoles(user));
            usersRepo.save(newUser);
        } catch (Exception e) {
            log.error("Could not register user.", e);
            throw new TechnicalException("Could not register user.", e);
        }
    }

    private List<Role> getUserRoles(RegisteredUser user) throws ParamsException {
        List<Role> roles = new ArrayList<>();

        Iterable<Role> dbRoles = rolesRepo.findAll();
        user.getRoles().forEach(roleType ->
                StreamSupport.stream(dbRoles.spliterator(), false)
                        .filter(dbRole -> dbRole.getDesc().equals(roleType.getFullDescription()))
                        .findFirst()
                        .map(dbRole -> roles.add(new Role(dbRole.getId(), dbRole.getDesc(), null)))
        );

        if (roles.isEmpty()) {
            throw new ParamsException("Select at least 1 role.");
        }

        return roles;
    }

    private void checkUserExists(String username) throws ParamsException {
        Optional<User> user = usersRepo.getUserByUsername(username);
        if (user.isPresent()) {
            throw new ParamsException("Account already exists.");
        }
    }

    @Async
    public void recordUser(HttpServletRequest httpRequest, UserDto userDto) {
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

    private List<Visitor> getTodayHits() {
        LocalDateTime now = LocalDateTime.now();
        Date startDate = GeneralUtils.getStartOfDay(now);
        Date endDate = GeneralUtils.getEndOfDay(now);

        return visitorsRepo.findByInsertDate(startDate, endDate);
    }

    public int getTodayHitsCount() {
        return getTodayHits().size();
    }
}
