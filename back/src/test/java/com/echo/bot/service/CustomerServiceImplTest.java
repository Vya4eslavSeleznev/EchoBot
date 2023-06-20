package com.echo.bot.service;

import com.echo.bot.entity.Customer;
import com.echo.bot.exception.CustomerNotFoundException;
import com.echo.bot.model.AddCustomerBodyModel;
import com.echo.bot.model.GetLastMessageResponseModel;
import com.echo.bot.model.UserMessageBodyModel;
import com.echo.bot.model.UserMessageResponseModel;
import com.echo.bot.repository.CustomerRepository;
import com.echo.bot.service.impl.CustomerServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CustomerServiceImplTest {

    @InjectMocks
    private CustomerServiceImpl customerService;

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private DelayService delayService;

    private long userId;
    private long chatId;
    private long index;
    private String username;
    private String lastMessage;
    private UserMessageBodyModel userMessageBodyModel;
    private Optional<Customer> customerOpt;

    @BeforeEach
    public void init() {
        userId = 1;
        lastMessage = "Hello";
        username = "Vya4eslav";
        chatId = 3;
        index = 2;
        userMessageBodyModel = new UserMessageBodyModel(chatId, lastMessage, userId, username);
        Customer customer = new Customer(chatId, userId, lastMessage, index, username);
        customerOpt = Optional.of(customer);
    }

    @Test
    public void add_customer() {
        AddCustomerBodyModel addCustomerBodyModel =
          new AddCustomerBodyModel(chatId, userId, lastMessage, index, username);

        customerService.addCustomer(addCustomerBodyModel);

        verify(customerRepository, times(1)).save(any(Customer.class));
    }

    @Test
    public void is_customer_exist_true_returned() {
        when(customerRepository.findByUserId(userId)).thenReturn(customerOpt);

        boolean actualVal = customerService.isCustomerExist(userId);

        verify(customerRepository, times(1)).findByUserId(1);

        assertTrue(actualVal);
    }

    @Test
    public void is_customer_exist_false_returned() {
        boolean actualVal = customerService.isCustomerExist(userId);

        verify(customerRepository, times(1)).findByUserId(userId);

        assertFalse(actualVal);
    }

    @Test
    public void save_message_throw_exception() {
        assertThrows(CustomerNotFoundException.class, () -> customerService.saveMessage(userMessageBodyModel));
    }

    @Test
    public void save_message_successful() throws CustomerNotFoundException {
        long delay = 2000;

        when(customerRepository.findByUserId(userId)).thenReturn(customerOpt);
        when(delayService.getValueOfDelay()).thenReturn(delay);

        UserMessageResponseModel actualModel = customerService.saveMessage(userMessageBodyModel);

        verify(customerRepository, times(1)).findByUserId(userId);
        verify(delayService, times(1)).getValueOfDelay();

        assertEquals(userMessageBodyModel.getChatId(), actualModel.getChatId());
        assertEquals(userMessageBodyModel.getMessage(), actualModel.getMessage());
        assertEquals(customerOpt.get().getIndex(), actualModel.getIndex());
        assertEquals(delay, actualModel.getDelay());
    }

    @Test
    public void get_message_throw_exception() {
        assertThrows(CustomerNotFoundException.class, () -> customerService.getMessage(userId));
    }

    @Test
    public void get_message_successful() throws CustomerNotFoundException {
        when(customerRepository.findByUserId(userId)).thenReturn(customerOpt);

        GetLastMessageResponseModel expectedModel =
          new GetLastMessageResponseModel(customerOpt.get().getUserId(), customerOpt.get().getLastMessage());

        GetLastMessageResponseModel actualMode = customerService.getMessage(userId);

        assertEquals(expectedModel.getMessage(), actualMode.getMessage());
        assertEquals(expectedModel.getUsername(), actualMode.getUsername());
    }
}
