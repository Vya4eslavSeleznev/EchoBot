package com.echo.bot.service;

import com.echo.bot.model.AddCustomerBodyModel;

public interface CustomerService {

    void addCustomer(AddCustomerBodyModel addCustomerBodyModel);
    boolean isCustomerExist(long userId);
}
