package com.lindaring.base.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;

@Slf4j
public class GeneralUtils {

  public static String getClientIp(HttpServletRequest httpServletRequest) {
    String ipAddress = "";

    if (httpServletRequest != null) {
      ipAddress = httpServletRequest.getHeader("X-FORWARDED-FOR");
      if (ipAddress == null || "".equals(ipAddress)) {
        ipAddress = httpServletRequest.getRemoteAddr();
      }
    }

    log.debug("GeneralUtils :: getClientIp :: returned {}", ipAddress);
    return ipAddress;
  }

  public static String getUserAgent(HttpServletRequest httpServletRequest) {
    return httpServletRequest.getHeader("User-Agent");
  }

  public static boolean stringEmpty(String compare) {
    return (compare == null || "".equals(compare.trim()));
  }

  public static Date getStartOfDay(LocalDateTime localDateTime) {
    LocalDateTime startOfDay = localDateTime.with(LocalTime.MIN);
    return Date.from(startOfDay.atZone(ZoneId.systemDefault()).toInstant());
  }

  public static Date getEndOfDay(LocalDateTime localDateTime) {
    LocalDateTime endOfDay = localDateTime.with(LocalTime.MAX);
    return Date.from(endOfDay.atZone(ZoneId.systemDefault()).toInstant());
  }

  public static String getFormattedDate(Date date, String format) {
    SimpleDateFormat formattedDate = new SimpleDateFormat(format);
    return formattedDate.format(date);
  }

  public static BCryptPasswordEncoder getPasswordEncoder() {
    return new BCryptPasswordEncoder(5);
  }

  public static String encryptPassword(String password) {
    BCryptPasswordEncoder passwordEncoder = getPasswordEncoder();
    return passwordEncoder.encode(password);
  }
}
