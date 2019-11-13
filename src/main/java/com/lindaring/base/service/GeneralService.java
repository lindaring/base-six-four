package com.lindaring.base.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Slf4j
@Service
public class GeneralService {

    /**
     * Get the current system year.
     * @return current system year.
     */
    public int getCurrentYear() {
        return LocalDate.now().getYear();
    }

}
