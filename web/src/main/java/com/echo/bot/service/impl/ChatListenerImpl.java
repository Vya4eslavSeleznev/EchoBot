package com.echo.bot.service.impl;

import com.echo.bot.model.MessageEventModel;
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
    private TelegramBotLogic botLogic;

    @Override
    @JmsListener(destination = "${bot.queue}")
    public void processMessage(String model) {
        try {
            MessageEventModel event = objectMapper.readValue(model, MessageEventModel.class);
            botLogic.sendMessage(event);
        }
        catch(JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}
