package com.echo.bot.web;

import com.echo.bot.model.AddCustomerBodyModel;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.stereotype.Component;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;

@Component
public class Gateway {

    private final BackendService service;

    public Gateway() {
        Gson gson = new GsonBuilder()
          .setDateFormat("yyyy-MM-dd")
          .create();

        Retrofit retrofit = new Retrofit.Builder()
          .baseUrl("http://192.168.15.4:8080")
          .addConverterFactory(GsonConverterFactory.create(gson))
          .build();

        service = retrofit.create(BackendService.class);
    }

    public void addCustomer(AddCustomerBodyModel addCustomerBodyModel) {
        Call<Void> customer = service.addCustomer(addCustomerBodyModel);

        try {
            customer.execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean isCustomerExist(long userId) {
        Call<Boolean> isExist = service.isCustomerExist(userId);

        try {
            Response<Boolean> response = isExist.execute();
            return Boolean.TRUE.equals(response.body());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }
}
