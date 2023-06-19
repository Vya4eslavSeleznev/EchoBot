package com.echo.bot.service;

import com.echo.bot.model.UserMessageEventModel;

public interface MessageSender {

    void sendMessage(UserMessageEventModel userMessageEventModel);
}
