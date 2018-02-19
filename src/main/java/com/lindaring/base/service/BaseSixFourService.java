package com.lindaring.base.service;

import com.lindaring.base.properties.MessageProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Base64Utils;
import org.springframework.util.StringUtils;

import javax.naming.InvalidNameException;

@Service
public class BaseSixFourService {

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
        if (StringUtils.isEmpty(nonBase64)) {
            throw new InvalidNameException(messages.getInvalidString());
        }
        byte[] nonBase64Byte = nonBase64.getBytes();
        byte[] base64Byte = Base64Utils.encode(nonBase64Byte);
        return base64Byte.toString();
    }

}
