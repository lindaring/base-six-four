package com.lindaring.base.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
public class MessageProperties {

    @Value("${custom.messages.exception.invalidString}")
    private String invalidString;

}
