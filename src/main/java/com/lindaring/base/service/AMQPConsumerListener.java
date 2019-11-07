package com.lindaring.base.service;

import com.lindaring.base.dto.VisitorDto;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class AMQPConsumerListener {

    @RabbitListener(queues="${spring.rabbitmq.queueName}")
    public void listen(VisitorDto msg) {
        System.out.println("Received a new notification...");
        System.out.println(msg);
    }

}
