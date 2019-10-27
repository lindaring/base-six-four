package com.lindaring.base.config;

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

    @Async
    @Scheduled(cron = "0 0/1 * * * ?")
    public void scheduleFixedRateTaskAsync() throws MessagingException {
        MimeMessage msg = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(msg, true);

        helper.setTo("lndsimelane@gmail.com");
        helper.setSubject("Developer Tools Visitors");

        String body = "<html><head><style>body { font-family: \"proxima-nova\", \"proxima nova\", \"helvetica neue\", \"helvetica\", \"arial\", sans-serif; }</style></head><body><h2>Users for Today (#TODAYS_DATE)</h2><p><strong>Visitors:</strong> #NUMBER_OF_USERS<br/><strong>Hits:</strong> #NUMBER_OF_HITS</p></body></html>";
        body = body.replace("#TODAYS_DATE", new Date().toString());
        body = body.replace("#NUMBER_OF_USERS", 3+"");
        body = body.replace("#NUMBER_OF_HITS", 65+"");
        helper.setText(body, true);

        javaMailSender.send(msg);
        log.info("Today's email sent!");
    }

}
