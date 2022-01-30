package com.javastart.notification.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import static com.javastart.notification.config.RabbitMQConfig.QUEUE_DEPOSIT;

@Service
public class DepositMessageHandler {

    private final JavaMailSender javaMailSender;

    @Autowired
    public DepositMessageHandler(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @RabbitListener(queues = QUEUE_DEPOSIT) //очередь, из которой будут приходить сообщения, это часть Consumer
    public void receive(Message message) throws JsonProcessingException {
        System.out.println(message);
        byte[] body = message.getBody();
        String jsonBody = new String(body);
        ObjectMapper objectMapper = new ObjectMapper();
        DepositResponseDTO depositResponseDTO = objectMapper.readValue(jsonBody, DepositResponseDTO.class);
        System.out.println(depositResponseDTO);

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(depositResponseDTO.getEmail());
        mailMessage.setFrom("feduncov@gmail.com");
        mailMessage.setSubject("Deposit");
        mailMessage.setText("make deposit? sum=" + depositResponseDTO.getAmount());

        try {
            javaMailSender.send(mailMessage);
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
