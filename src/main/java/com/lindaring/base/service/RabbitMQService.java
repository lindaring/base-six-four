package com.lindaring.base.service;

import com.lindaring.base.dto.VisitorDto;
import com.lindaring.base.properties.RabbitMQProperties;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQService {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private RabbitMQProperties rabbitMQProperties;



    @RabbitListener(queues="${spring.rabbitmq.queueName}")
    public void listen(VisitorDto msg) {
        System.out.println("Received a new notification...");
        System.out.println(msg);
    }

    public void sendMessage(VisitorDto msg){
        System.out.println("Send msg = " + msg.toString());
        rabbitTemplate.convertAndSend(rabbitMQProperties.getExchangeName(), rabbitMQProperties.getRoutingKey(), msg);
    }

}
