package com.lindaring.base.service;

import com.lindaring.base.entity.Visitor;
import com.lindaring.base.model.GeneralResponse;
import com.lindaring.base.repo.VisitorsRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
public class UserService {

    private static final Logger log = LoggerFactory.getLogger(Base64Service.class);

    @Autowired
    private VisitorsRepo visitorsRepo;

    public GeneralResponse recordUser(HttpServletRequest httpRequest) {
        return new GeneralResponse(true);
    }
}
