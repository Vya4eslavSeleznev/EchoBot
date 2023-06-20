package com.echo.bot.service;

public interface DelayService {

    void initDelay();
    void updateDelay(long value);
    long getValueOfDelay();
}
