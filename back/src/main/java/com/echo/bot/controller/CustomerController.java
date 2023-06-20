package com.echo.bot.controller;

import com.echo.bot.exception.CustomerNotFoundException;
import com.echo.bot.model.AddCustomerBodyModel;
import com.echo.bot.model.GetLastMessageResponseModel;
import com.echo.bot.model.UserMessageBodyModel;
import com.echo.bot.model.UserMessageResponseModel;
import com.echo.bot.service.CustomerService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/customer")
@AllArgsConstructor
public class CustomerController {

    private CustomerService customerService;

    @PostMapping("/init")
    public ResponseEntity<?> addCustomer(@RequestBody AddCustomerBodyModel addCustomerBodyModel) {
        customerService.addCustomer(addCustomerBodyModel);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/exist/{id}")
    public ResponseEntity<Boolean> isCustomerExist(@PathVariable("id") long id) {
        return new ResponseEntity<>(customerService.isCustomerExist(id), HttpStatus.OK);
    }

    @PostMapping("/index")
    public ResponseEntity<UserMessageResponseModel> saveMessage(
      @RequestBody UserMessageBodyModel userMessageEventBodyModel) {

        try {
            return new ResponseEntity<>(customerService.saveMessage(userMessageEventBodyModel), HttpStatus.OK);
        }
        catch(CustomerNotFoundException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.SERVICE_UNAVAILABLE);
        }
    }

    @GetMapping("/last")
    public ResponseEntity<GetLastMessageResponseModel> getLastMessage(@PathVariable long id) {
        try {
            return new ResponseEntity<>(customerService.getMessage(id), HttpStatus.OK);
        }
        catch(CustomerNotFoundException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.SERVICE_UNAVAILABLE);
        }
    }
}
