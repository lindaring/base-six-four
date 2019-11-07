package com.lindaring.base.config;

import com.lindaring.base.dto.VisitorDto;
import com.lindaring.base.entity.Visitor;
import com.lindaring.base.properties.MailProperties;
import com.lindaring.base.repo.VisitorsRepo;
import com.lindaring.base.service.UserService;
import com.lindaring.base.utils.GeneralUtils;
import com.lindaring.base.utils.VisitorHelper;
import lombok.extern.slf4j.Slf4j;
import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Slf4j
@Configuration
@EnableScheduling
public class SchedulerConfig {

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private MailProperties mailProperties;

    @Autowired
    private UserService userService;

    @Autowired
    private VisitorsRepo visitorsRepo;

    @Async
    @Scheduled(cron = "${api.mail.cron}")
    public void sendDailyVisitorsReport() throws MessagingException {
        if (!mailProperties.isEnabled()) {
            log.info("Developer tools visitors report email is disabled!");
            return;
        }
        MimeMessage msg = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(msg, true);

        helper.setTo(mailProperties.getTo());
        helper.setSubject(mailProperties.getSubject());

        String date = GeneralUtils.getFormattedDate(new Date(), "EEEE dd MMMM yyyy");
        int numberOfHits = userService.getTodayHitsCount();
        int numberOfVisitors = userService.getTodayVisitorsCount();

        String body = mailProperties.getBody()
                .replace("#TODAYS_DATE", date)
                .replace("#NUMBER_OF_USERS", numberOfHits+"")
                .replace("#NUMBER_OF_HITS", numberOfVisitors+"");
        helper.setText(body, true);

        javaMailSender.send(msg);
        log.info("Today's developer tools visitors report email was sent!");
    }

    @Async
    @Scheduled(cron = "0 0/5 * * * ?")
    public void writeVisitorQueueToDatabase() {
        if (!VisitorHelper.visitorsList.isEmpty()) {
            log.info("Writing visitor queue to database..." + VisitorHelper.visitorsList.size());

            DozerBeanMapper mapper = new DozerBeanMapper();
            List<Visitor> entityList = new ArrayList<>();
            for (VisitorDto dto : VisitorHelper.visitorsList) {
                Visitor entity = mapper.map(dto, Visitor.class);
                entityList.add(entity);
            }

            visitorsRepo.saveAll(entityList);
            VisitorHelper.visitorsList.clear();
        } else {
            log.info("No visitors to write.");
        }
    }

}
