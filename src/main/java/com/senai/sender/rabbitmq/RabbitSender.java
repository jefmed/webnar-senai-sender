package com.senai.sender.rabbitmq;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.senai.sender.domain.Student;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.AmqpUnsupportedEncodingException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class RabbitSender {
    private final RabbitTemplate rabbitTemplate;
    private final ObjectMapper objectMapper;

    @Autowired
    public RabbitSender(RabbitTemplate rabbitTemplate, ObjectMapper objectMapper) {
        this.rabbitTemplate = rabbitTemplate;
        this.objectMapper = objectMapper;
    }

    public void send(Student student) {
        log.info("Enviando mensagem para o receiver do estudande: " + student.getName());
        rabbitTemplate.convertAndSend(RabbitConfig.exchange, RabbitConfig.routingkey, buildProduceMessage(student));
    }


    private Message buildProduceMessage(Object object) {
        return MessageBuilder
                .withBody(convertObjectToByte(object))
                .setContentType(MessageProperties.CONTENT_TYPE_JSON)
                .build();
    }


    private byte[] convertObjectToByte(Object object) {
        try {
            return objectMapper.writeValueAsString(object).getBytes();
        } catch (JsonProcessingException e) {
            throw new AmqpUnsupportedEncodingException(e);
        }

    }
}
