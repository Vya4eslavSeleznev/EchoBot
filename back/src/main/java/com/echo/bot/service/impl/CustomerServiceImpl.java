package com.echo.bot.service.impl;

import com.echo.bot.entity.Customer;
import com.echo.bot.entity.Delay;
import com.echo.bot.model.AddCustomerBodyModel;
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
}
