package com.echo.bot.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UserMessageEventModel {

    private String username;
    private String message;
    private Long userId;
}
