package com.echo.bot.service.impl;

import com.echo.bot.model.AddCustomerBodyModel;
import com.echo.bot.model.MessageEventModel;
import com.echo.bot.model.UserMessageBodyModel;
import com.echo.bot.model.UserMessageResponseModel;
import com.echo.bot.service.MessageSender;
import com.echo.bot.web.Gateway;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

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

        String receivedMessage = update.getMessage().getText();
        long userId = update.getMessage().getFrom().getId();
        long chatId = update.getMessage().getChatId();
        boolean isExist = gateway.isCustomerExist(userId);

        if(!isExist && receivedMessage.equals(start)) {
            gateway.addCustomer(new AddCustomerBodyModel(chatId, userId, start, 0));
        } else if(isExist && update.hasMessage() && !receivedMessage.equals(start)) {
            UserMessageResponseModel responseModel = gateway.saveMessage(
              new UserMessageBodyModel(chatId, receivedMessage, userId)
            );

            messageSender.sendMessage(
              new MessageEventModel(chatId, receivedMessage, responseModel.getIndex()), responseModel.getDelay()
            );
        }
    }

    public void sendMessage(MessageEventModel event) {
        SendMessage message = new SendMessage();
        message.setChatId(event.getChatId());
        message.setText(event.getMessage() + " " + event.getIndex());

        try {
            execute(message);
        }
        catch(TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }
}
