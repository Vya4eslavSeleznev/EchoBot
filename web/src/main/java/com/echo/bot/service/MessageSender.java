package com.echo.bot.service;

import com.echo.bot.model.MessageEventModel;

public interface MessageSender {

    void sendMessage(MessageEventModel messageEventModel, long delay);
}
