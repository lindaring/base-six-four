package com.lindaring.base.service;

import com.lindaring.base.entity.Visitor;
import com.lindaring.base.model.User;
import com.lindaring.base.repo.VisitorsRepo;
import com.lindaring.base.utils.GeneralUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Service
public class UserService {

    private static final Logger log = LoggerFactory.getLogger(Base64Service.class);

    @Autowired
    private VisitorsRepo visitorsRepo;

    @Async
    public void recordUser(HttpServletRequest httpRequest, User user) {
        try {
            String ip = GeneralUtils.getClientIp(httpRequest);
            String userAgent = GeneralUtils.getUserAgent(httpRequest);

            Visitor visitor = new Visitor(0, ip, new Date(), userAgent, user.getUrl());
            visitorsRepo.save(visitor);
        } catch (Exception e) {
            log.error("Could not record visitor.", e);
        }
    }
}
