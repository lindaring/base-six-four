package com.lindaring.base.service;

import com.lindaring.base.entity.Visitor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class AMQPConsumerListener {

    @RabbitListener(queues="${spring.rabbitmq.queueName}")
    public void listen(Visitor msg) {
        System.out.println("Received a new notification...");
        System.out.println(msg);
    }

}
