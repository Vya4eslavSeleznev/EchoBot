package com.echo.bot.service.impl;

import com.echo.bot.entity.Customer;
import com.echo.bot.entity.Delay;
import com.echo.bot.exception.CustomerNotFoundException;
import com.echo.bot.model.AddCustomerBodyModel;
import com.echo.bot.model.UpdateDelayBodyModel;
import com.echo.bot.model.UserMessageBodyModel;
import com.echo.bot.model.UserMessageResponseModel;
import com.echo.bot.repository.CustomerRepository;
import com.echo.bot.repository.DelayRepository;
import com.echo.bot.service.CustomerService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private CustomerRepository customerRepository;
    private DelayRepository delayRepository;

    @Override
    public void addCustomer(AddCustomerBodyModel addCustomerBodyModel) {
        Delay delay = delayRepository.save(
          new Delay(2000)
        );

        customerRepository.save(
          new Customer(addCustomerBodyModel.getChatId(), addCustomerBodyModel.getUserId(),
            addCustomerBodyModel.getLastMessage(), addCustomerBodyModel.getIndex(), delay)
        );
    }

    @Override
    public boolean isCustomerExist(long userId) {
        Optional<Customer> customer = customerRepository.findByUserId(userId);
        return customer.isPresent();
    }

    @Override
    public void updateDelay(UpdateDelayBodyModel updateDelayBodyModel) throws CustomerNotFoundException {
        Optional<Customer> customer = customerRepository.findByUserId(updateDelayBodyModel.getUserId());

        if(customer.isEmpty()) {
            throw new CustomerNotFoundException();
        }

        Optional<Delay> delay = delayRepository.findById(customer.get().getDelay().getId());
        delay.get().setValue(updateDelayBodyModel.getDelay());
        customer.get().setDelay(delay.get());
    }

    @Override
    public UserMessageResponseModel saveMessage(UserMessageBodyModel userMessageEventModel)
      throws CustomerNotFoundException {
        Optional<Customer> customer = customerRepository.findByUserId(userMessageEventModel.getUserId());

        if(customer.isEmpty()) {
            throw new CustomerNotFoundException();
        }

        long oldIndex = customer.get().getIndex();

        customer.get().setLastMessage(userMessageEventModel.getMessage());
        customer.get().setIndex(++oldIndex);

        customerRepository.save(customer.get());

        return new UserMessageResponseModel(
          userMessageEventModel.getChatId(), userMessageEventModel.getMessage(),
          customer.get().getIndex(), customer.get().getDelay().getValue()
        );
    }
}




















