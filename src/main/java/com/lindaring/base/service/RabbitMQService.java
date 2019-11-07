package com.lindaring.base.service;

import com.lindaring.base.dto.VisitorDto;
import com.lindaring.base.properties.RabbitMQProperties;
import com.lindaring.base.utils.VisitorHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class RabbitMQService {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private RabbitMQProperties rabbitMQProperties;

    @RabbitListener(queues="${spring.rabbitmq.queueName}")
    public void listen(VisitorDto msg) {
        log.info(msg + " message received.");
        if (msg != null) {
            VisitorHelper.visitorsList.add(msg);
        }
    }

    public void sendMessage(VisitorDto msg){
        log.info("Sending message to queue..." + msg);
        rabbitTemplate.convertAndSend(rabbitMQProperties.getExchangeName(), rabbitMQProperties.getRoutingKey(), msg);
    }

}
