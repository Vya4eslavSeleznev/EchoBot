package com.echo.bot.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UserMessageResponseModel {

    private long chatId;
    private String message;
    private long index;
    private long delay;
}
