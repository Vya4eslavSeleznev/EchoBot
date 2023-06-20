package com.echo.bot.service;

import com.echo.bot.model.AddCustomerBodyModel;
import com.echo.bot.model.MessageEventModel;
import com.echo.bot.model.UserMessageBodyModel;
import com.echo.bot.model.UserMessageResponseModel;
import com.echo.bot.service.impl.TelegramBotLogic;
import com.echo.bot.web.Gateway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TelegramBotLogicTest {

    @InjectMocks
    private TelegramBotLogic telegramBotLogic;

    @Mock
    private MessageSender messageSender;

    @Mock
    private Gateway gateway;

    @Mock
    private Update update;

    @Mock
    private Message message;

    private long userId;
    private User user;

    @BeforeEach
    public void init() {
        userId = 1;
        user = new User();

        user.setUserName("username");
        user.setId(userId);
    }

    @Test
    public void on_update_received_with_start_message_and_user_not_exist() {
        when(update.getMessage()).thenReturn(message);
        when(message.getFrom()).thenReturn(user);
        when(message.getText()).thenReturn("/start");
        when(gateway.isCustomerExist(userId)).thenReturn(false);

        telegramBotLogic.onUpdateReceived(update);

        verify(gateway, times(1)).isCustomerExist(userId);

        verify(gateway, times(1)).addCustomer(any(AddCustomerBodyModel.class));
    }

    @Test
    public void on_update_received_with_random_message_and_user_exist() {
        String msg = "Hello";
        long delay = 2000;
        long chatId = 1;
        UserMessageResponseModel userMessageResponseModel = new UserMessageResponseModel(chatId, msg, 1, delay);

        when(update.hasMessage()).thenReturn(true);
        when(update.getMessage()).thenReturn(message);
        when(message.getFrom()).thenReturn(user);
        when(message.getText()).thenReturn(msg);
        when(gateway.isCustomerExist(userId)).thenReturn(true);
        when(gateway.saveMessage(any(UserMessageBodyModel.class))).thenReturn(userMessageResponseModel);

        telegramBotLogic.onUpdateReceived(update);

        verify(gateway, times(1)).isCustomerExist(userId);
        verify(gateway, times(1)).saveMessage(any(UserMessageBodyModel.class));
        verify(messageSender, times(1)).sendMessage(any(MessageEventModel.class), eq(delay));
    }
}
