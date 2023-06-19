package com.echo.bot.service.impl;

import com.echo.bot.model.UserMessageEventModel;
import com.echo.bot.service.MessageSender;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.activemq.ScheduledMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

@Service
public class MessageSenderImpl implements MessageSender {

    @Value("${bot.queue}")
    private String queue;

    private final JmsTemplate jmsTemplate;
    private final ObjectMapper objectMapper;

    public MessageSenderImpl(JmsTemplate jmsTemplate, ObjectMapper objectMapper) {
        this.jmsTemplate = jmsTemplate;
        this.objectMapper = objectMapper;
    }

    @Override
    public void sendMessage(UserMessageEventModel userMessageEventModel) {
        try {
            jmsTemplate.convertAndSend(queue, objectMapper.writeValueAsString(userMessageEventModel), m -> {
                m.setLongProperty(ScheduledMessage.AMQ_SCHEDULED_DELAY, 10000);
                return m;
            });
        }
        catch(JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}
