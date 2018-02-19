package com.lindaring.base.service;

import com.lindaring.base.properties.MessageProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Base64Utils;
import org.springframework.util.StringUtils;

import javax.naming.InvalidNameException;

import static java.lang.String.format;

@Service
public class BaseSixFourService {

    private static final Logger log = LoggerFactory.getLogger(BaseSixFourService.class);

    @Autowired
    private MessageProperties messages;

    /**
     * Convert string to base 64 encoded string.
     *
     * @param nonBase64 the string to be encoded.
     * @return the base encoded string.
     * @throws InvalidNameException if the provided string is empty or null.
     */
    public String getEncodedBase64(String nonBase64) throws InvalidNameException {
        log.debug(format("Entering getEncodedBase64 with %s", nonBase64));

        if (StringUtils.isEmpty(nonBase64)) {
            log.error("Invalid string found");
            throw new InvalidNameException(messages.getInvalidString());
        }
        byte[] nonBase64Byte = nonBase64.getBytes();
        byte[] base64Byte = Base64Utils.encode(nonBase64Byte);

        log.debug(format("Exiting getEncodedBase64 with %s", nonBase64));
        return base64Byte.toString();
    }

}
