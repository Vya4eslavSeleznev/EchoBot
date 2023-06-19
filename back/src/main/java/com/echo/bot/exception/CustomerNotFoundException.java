package com.echo.bot.exception;

public class CustomerNotFoundException extends Exception {

    public CustomerNotFoundException() {
        super("Customer not found");
    }
}
