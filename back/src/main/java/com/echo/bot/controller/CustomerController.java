package com.echo.bot.controller;

import com.echo.bot.exception.CustomerNotFoundException;
import com.echo.bot.model.AddCustomerBodyModel;
import com.echo.bot.model.UpdateDelayBodyModel;
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
    public ResponseEntity<Boolean> isCustomerExist(@PathVariable long id) {
        return new ResponseEntity<>(customerService.isCustomerExist(id), HttpStatus.OK);
    }

    @PutMapping()
    public ResponseEntity<?> updateDelay(UpdateDelayBodyModel updateDelayBodyModel) {
        try {
            customerService.updateDelay(updateDelayBodyModel);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        catch(CustomerNotFoundException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.SERVICE_UNAVAILABLE);
        }

    }
}
