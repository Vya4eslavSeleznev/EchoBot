package com.echo.bot.service;

import com.echo.bot.exception.CustomerNotFoundException;
import com.echo.bot.model.AddCustomerBodyModel;
import com.echo.bot.model.UpdateDelayBodyModel;
import com.echo.bot.model.UserMessageBodyModel;
import com.echo.bot.model.UserMessageResponseModel;

public interface CustomerService {

    void addCustomer(AddCustomerBodyModel addCustomerBodyModel);
    boolean isCustomerExist(long userId);
    void updateDelay(UpdateDelayBodyModel updateDelayBodyModel) throws CustomerNotFoundException;
    UserMessageResponseModel saveMessage(UserMessageBodyModel userMessageEventModel)
      throws CustomerNotFoundException;
}
