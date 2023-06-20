package com.echo.bot.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class AddCustomerBodyModel {

    private long chatId;
    private long userId;
    private String lastMessage;
    private long index;
    private String username;
}
