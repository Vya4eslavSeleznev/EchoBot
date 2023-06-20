package com.echo.bot.service;

import com.echo.bot.entity.Delay;
import com.echo.bot.repository.DelayRepository;
import com.echo.bot.service.impl.DelayServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DelayServiceImplTest {

    @InjectMocks
    private DelayServiceImpl delayService;

    @Mock
    private DelayRepository delayRepository;

    private Iterable<Delay> iterator;
    private Delay delay;
    private long delayValue;

    @BeforeEach
    public void init() {
        delayValue = 2000;
        delay = new Delay(delayValue);
        iterator = List.of(delay);
    }

    @Test
    public void update_delay() {
        when(delayRepository.findAll()).thenReturn(iterator);
        when(delayRepository.save(delay)).thenReturn(delay);

        delayService.updateDelay(5000);

        verify(delayRepository, times(1)).findAll();
        verify(delayRepository, times(1)).save(any(Delay.class));
    }

    @Test
    public void get_value_of_delay_delay_returned() {
        when(delayRepository.findAll()).thenReturn(iterator);

        long actualValue = delayService.getValueOfDelay();

        verify(delayRepository, times(1)).findAll();

        assertEquals(delayValue, actualValue);
    }

    @Test
    public void get_value_of_delay_negative_digit_returned() {
        long actualValue = delayService.getValueOfDelay();

        verify(delayRepository, times(1)).findAll();

        assertEquals(-1, actualValue);
    }
}
