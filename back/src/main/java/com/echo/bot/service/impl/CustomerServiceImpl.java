package com.echo.bot.service.impl;

import com.echo.bot.entity.Customer;
import com.echo.bot.exception.CustomerNotFoundException;
import com.echo.bot.model.AddCustomerBodyModel;
import com.echo.bot.model.GetLastMessageResponseModel;
import com.echo.bot.model.UserMessageBodyModel;
import com.echo.bot.model.UserMessageResponseModel;
import com.echo.bot.repository.CustomerRepository;
import com.echo.bot.service.CustomerService;
import com.echo.bot.service.DelayService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private CustomerRepository customerRepository;
    private DelayService delayService;

    @Override
    public void addCustomer(AddCustomerBodyModel addCustomerBodyModel) {
        customerRepository.save(
          new Customer(addCustomerBodyModel.getChatId(), addCustomerBodyModel.getUserId(),
            addCustomerBodyModel.getLastMessage(), addCustomerBodyModel.getIndex(), addCustomerBodyModel.getUsername())
        );
    }

    @Override
    public boolean isCustomerExist(long userId) {
        Optional<Customer> customer = customerRepository.findByUserId(userId);
        return customer.isPresent();
    }

    @Override
    public UserMessageResponseModel saveMessage(UserMessageBodyModel userMessageEventModel)
      throws CustomerNotFoundException {
        Customer customer = getCustomer(userMessageEventModel.getUserId());

        long oldIndex = customer.getIndex();

        customer.setLastMessage(userMessageEventModel.getMessage());
        customer.setIndex(++oldIndex);
        customer.setUsername(userMessageEventModel.getUsername());

        customerRepository.save(customer);

        return new UserMessageResponseModel(
          userMessageEventModel.getChatId(), userMessageEventModel.getMessage(),
          customer.getIndex(), delayService.getValueOfDelay()
        );
    }

    @Override
    public GetLastMessageResponseModel getMessage(long userId) throws CustomerNotFoundException {
        Customer customer = getCustomer(userId);

        return new GetLastMessageResponseModel(
          customer.getUserId(), customer.getLastMessage()
        );
    }

    private Customer getCustomer(long userId) throws CustomerNotFoundException {
        Optional<Customer> customer = customerRepository.findByUserId(userId);

        if(customer.isEmpty()) {
            throw new CustomerNotFoundException();
        }

        return customer.get();
    }
}




















