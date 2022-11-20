package com.senai.sender.domain;

import com.senai.sender.rabbitmq.RabbitSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/students")
public class StudentController {

    private final RabbitSender rabbitSender;
    @Autowired
    public StudentController(RabbitSender rabbitSender) {
        this.rabbitSender = rabbitSender;
    }

    @PostMapping
    public void sendMessage(@RequestBody Student student) {
        rabbitSender.send(student);
    }
}
