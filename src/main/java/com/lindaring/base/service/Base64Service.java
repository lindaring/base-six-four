package com.lindaring.base.service;

import com.lindaring.base.cache.Base64Cache;
import com.lindaring.base.enumerator.Charset;
import com.lindaring.base.model.Base64;
import com.lindaring.base.properties.MessageProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Base64Utils;
import org.springframework.util.StringUtils;

import javax.naming.InvalidNameException;
import java.io.UnsupportedEncodingException;
import java.util.List;

import static java.lang.String.format;

@Service
public class Base64Service {

    private static final Logger log = LoggerFactory.getLogger(Base64Service.class);

    @Autowired
    private Base64Cache cache;

    @Autowired
    private MessageProperties messages;

    /**
     * Convert string to base 64 encoded string.
     *
     * @param nonBase64 the string to be encoded.
     * @param charset   the selected output charset.
     * @return the base encoded string.
     * @throws InvalidNameException if the provided string is empty or null.
     */
    public Base64 getEncodedBase64(String nonBase64, Charset charset) throws InvalidNameException, UnsupportedEncodingException {
        log.debug(format("Entering :: getEncodedBase64 with :: [%s][%s]", nonBase64, charset));

        validateInput(nonBase64);
        String base64 = cache.getCache(nonBase64);

        if (StringUtils.isEmpty(base64)) {
            byte[] base64Byte = Base64Utils.encode(nonBase64.getBytes());
            base64 = new String(base64Byte, Charset.getValue(charset));

            cache.cachePut(nonBase64, base64);
            cache.cachePut(base64, nonBase64);
        }

        Base64 response = new Base64(nonBase64, base64);
        log.debug(format("Exiting :: getEncodedBase64 with :: [%s]", response));
        return response;
    }

    /**
     * Convert base 64 to string decoded string.
     *
     * @param base64Encoded the encoded string.
     * @param charset       the selected output charset.
     * @return the decoded string.
     * @throws InvalidNameException if the provided string is empty or null.
     */
    public Base64 getDecodedBase64(String base64Encoded, Charset charset) throws InvalidNameException, UnsupportedEncodingException {
        log.debug(format("Entering :: getDecodedBase64 with :: [%s][%s]", base64Encoded, charset));

        validateInput(base64Encoded);
        String nonBase64 = cache.getCache(base64Encoded);

        if (StringUtils.isEmpty(nonBase64)) {
            byte[] base64Byte = Base64Utils.decode(base64Encoded.getBytes());
            nonBase64 = new String(base64Byte, Charset.getValue(charset));

            cache.cachePut(base64Encoded, nonBase64);
            cache.cachePut(nonBase64, base64Encoded);
        }

        Base64 response = new Base64(nonBase64, base64Encoded);
        log.debug(format("Exiting :: getEncodedBase64 with :: [%s]", response));
        return response;
    }

    /**
     * Get a list of all charset keys.
     * @return charset keys.
     */
    public List getCharsets() {
        return Charset.getCharsetKeys();
    }

    private void validateInput(String input) throws InvalidNameException {
        if (StringUtils.isEmpty(input)) {
            log.error("Invalid string found");
            throw new InvalidNameException(messages.getInvalidString());
        }
    }

}
