package com.echo.bot.service.impl;

import com.echo.bot.model.AddCustomerBodyModel;
import com.echo.bot.model.UserMessageEventModel;
import com.echo.bot.service.MessageSender;
import com.echo.bot.web.Gateway;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

import javax.validation.constraints.NotNull;

@Service
public class TelegramBotLogic extends TelegramLongPollingBot {

    @Value("${bot.name}")
    private String botName;

    private final MessageSender messageSender;
    private final Gateway gateway;

    public TelegramBotLogic(@Value("${bot.token}") String token, MessageSender messageSender, Gateway gateway) {
        super(token);
        this.messageSender = messageSender;
        this.gateway = gateway;
    }

    @Override
    public String getBotUsername() {
        return botName;
    }

    @Override
    public void onUpdateReceived(@NotNull Update update) {
        String start = "/start";
        String delay = "/delay";

        String receivedMessage = update.getMessage().getText();
        long userId = update.getMessage().getFrom().getId();
        long chatId = update.getMessage().getChatId();
        boolean isExist = gateway.isCustomerExist(userId);

        if(!isExist && update.getMessage().getText().equals(start)) {
            gateway.addCustomer(new AddCustomerBodyModel(chatId, userId, start, 0));
        } else if(isExist && update.hasMessage()) {
            messageSender.sendMessage(new UserMessageEventModel(chatId, receivedMessage, userId));



        } else if(isExist && update.getMessage().getText().equals(delay)) {
            // setDelay
        }
    }
}
