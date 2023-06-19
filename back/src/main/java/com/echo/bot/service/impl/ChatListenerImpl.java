package com.echo.bot.service.impl;

import com.echo.bot.model.UserMessageEventModel;
import com.echo.bot.service.ChatListener;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ChatListenerImpl implements ChatListener {

    private ObjectMapper objectMapper;

    @Override
    @JmsListener(destination = "${bot.queue}")
    public void processMessage(String model) {
        try {
            UserMessageEventModel event = objectMapper.readValue(model, UserMessageEventModel.class);
            System.out.println();
        }
        catch(JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}
