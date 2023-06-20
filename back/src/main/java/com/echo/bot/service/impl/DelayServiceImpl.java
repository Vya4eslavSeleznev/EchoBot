package com.echo.bot.service.impl;

import com.echo.bot.entity.Delay;
import com.echo.bot.repository.DelayRepository;
import com.echo.bot.service.DelayService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Service
public class DelayServiceImpl implements DelayService {

    private final DelayRepository delayRepository;

    @Value("${delay.value}")
    private String delayValue;

    public DelayServiceImpl(DelayRepository delayRepository) {
        this.delayRepository = delayRepository;
    }

    @Override
    @PostConstruct
    public void initDelay() {
        delayRepository.save(
          new Delay(Integer.parseInt(delayValue))
        );
    }

    @Override
    public void updateDelay(long value) {
        Delay delay = getDelay();

        if(delay == null) {
            return;
        }

        delay.setValue(value);
        delayRepository.save(delay);
    }

    @Override
    public long getValueOfDelay() {
        Delay delay = getDelay();

        if(delay == null) {
            return -1;
        }

        return delay.getValue();
    }

    private Delay getDelay() {
        Iterable<Delay> iterator = delayRepository.findAll();
        List<Delay> delay = new ArrayList<>();
        iterator.iterator().forEachRemaining(delay::add);

        if(delay.size() == 0) {
            return null;
        }

        return delay.get(0);
    }
}
