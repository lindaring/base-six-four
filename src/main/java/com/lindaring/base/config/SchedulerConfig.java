package com.lindaring.base.config;

import com.lindaring.base.properties.MailProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

@Configuration
@EnableScheduling
public class SchedulerConfig {

    private static final Logger log = LoggerFactory.getLogger(SchedulerConfig.class);

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private MailProperties mailProperties;

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

        String body = mailProperties.getBody()
                .replace("#TODAYS_DATE", new Date().toString())
                .replace("#NUMBER_OF_USERS", 3+"")
                .replace("#NUMBER_OF_HITS", 65+"");
        helper.setText(body, true);

        javaMailSender.send(msg);
        log.info("Today's developer tools visitors report email was sent!");
    }

}
