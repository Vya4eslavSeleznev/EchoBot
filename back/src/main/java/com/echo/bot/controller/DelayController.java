package com.echo.bot.controller;

import com.echo.bot.service.DelayService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/delay")
@AllArgsConstructor
public class DelayController {

    private DelayService delayService;

    @PutMapping()
    public ResponseEntity<?> updateDelay(@PathVariable("value") long value) {
        delayService.updateDelay(value);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
