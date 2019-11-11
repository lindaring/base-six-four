package com.lindaring.base.config;

import com.lindaring.base.properties.MailProperties;
import com.lindaring.base.service.UserService;
import com.lindaring.base.utils.GeneralUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Date;

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

}
