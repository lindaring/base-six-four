package com.lindaring.base.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class GeneralService {

    private static final Logger log = LoggerFactory.getLogger(GeneralService.class);

    /**
     * Get the current system year.
     * @return current system year.
     */
    public int getCurrentYear() {
        return LocalDate.now().getYear();
    }

}
