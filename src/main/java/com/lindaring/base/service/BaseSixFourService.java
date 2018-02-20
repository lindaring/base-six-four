package com.lindaring.base.service;

import com.lindaring.base.properties.MessageProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Base64Utils;
import org.springframework.util.StringUtils;

import javax.naming.InvalidNameException;

import java.io.UnsupportedEncodingException;

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
    public String getEncodedBase64(String nonBase64) throws InvalidNameException, UnsupportedEncodingException {
        log.debug(format("Entering :: getEncodedBase64 with :: %s", nonBase64));

        validateInput(nonBase64);
        byte[] base64Byte = Base64Utils.encode(nonBase64.getBytes());

        log.debug(format("Exiting :: getEncodedBase64 with :: %s", nonBase64));
        return new String(base64Byte, "UTF-8");
    }

    /**
     * Convert base 64 to string decoded string.
     *
     * @param base64Encoded the encoded string.
     * @return the decoded string.
     * @throws InvalidNameException if the provided string is empty or null.
     */
    public String getDecodedBase64(String base64Encoded) throws InvalidNameException, UnsupportedEncodingException {
        log.debug(format("Entering :: getDecodedBase64 with :: %s", base64Encoded));

        validateInput(base64Encoded);
        byte[] base64Byte = Base64Utils.decode(base64Encoded.getBytes());

        log.debug(format("Exiting :: getDecodedBase64 with :: %s", base64Encoded));
        return new String(base64Byte, "UTF-8");
    }

    private void validateInput(String input) throws InvalidNameException {
        if (StringUtils.isEmpty(input)) {
            log.error("Invalid string found");
            throw new InvalidNameException(messages.getInvalidString());
        }
    }

}
