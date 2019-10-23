package com.lindaring.base.utils;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;

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

}
