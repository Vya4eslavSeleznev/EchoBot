package com.echo.bot.web;

import com.echo.bot.model.AddCustomerBodyModel;
import com.echo.bot.model.GetLastMessageResponseModel;
import com.echo.bot.model.UserMessageBodyModel;
import com.echo.bot.model.UserMessageResponseModel;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;

@Component
public class Gateway {

    private final BackendService service;

    public Gateway(@Value("${backend.url}") String backendUrl) {
        Gson gson = new GsonBuilder()
          .setDateFormat("yyyy-MM-dd")
          .create();

        Retrofit retrofit = new Retrofit.Builder()
          .baseUrl(backendUrl)
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

    public UserMessageResponseModel saveMessage(UserMessageBodyModel userMessageBodyModel) {
        Call<UserMessageResponseModel> msg = service.saveMessage(userMessageBodyModel);

        try {
            Response<UserMessageResponseModel> response = msg.execute();
            return response.body();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public GetLastMessageResponseModel getLastMessage(long userId) {
        Call<GetLastMessageResponseModel> msg = service.getLastMessage(userId);

        try {
            Response<GetLastMessageResponseModel> response = msg.execute();
            return response.body();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public void updateDelay(long value) {
        Call<Void> delay = service.updateDelay(value);

        try {
            delay.execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
