package com.echo.bot.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UserMessageBodyModel {

    private long chatId;
    private String message;
    private long userId;
    private String username;
}
