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

    @Autowired
    private RabbitSender rabbitSender;

    @PostMapping
    public void sendMessage(@RequestBody Student student) {
        rabbitSender.send(student);
    }
}
