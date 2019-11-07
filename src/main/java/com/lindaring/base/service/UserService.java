package com.lindaring.base.service;

import com.lindaring.base.client.GeolocationClientService;
import com.lindaring.base.client.model.Geolocation;
import com.lindaring.base.dto.VisitorDto;
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
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Service
public class UserService {

    private static final Logger log = LoggerFactory.getLogger(Base64Service.class);

    @Autowired
    private VisitorsRepo visitorsRepo;

    @Autowired
    private GeolocationClientService geolocationClientService;

    @Autowired
    private AMQPProducerService amqpProducerService;

    @Async
    public void recordUser(HttpServletRequest httpRequest, User user) {
        try {
            String ip = GeneralUtils.getClientIp(httpRequest);
            String userAgent = GeneralUtils.getUserAgent(httpRequest);
            String location = null;
            if (ip != null) {
                Geolocation geoInfo = geolocationClientService.getLocation(ip);
                location = getLocation(geoInfo);
            }

            VisitorDto visitor = new VisitorDto(0, ip, new Date(), userAgent, user.getUrl(), location);
            amqpProducerService.sendMessage(visitor);
            //visitorsRepo.save(visitor);
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
