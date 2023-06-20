package com.echo.bot.web;

import com.echo.bot.model.AddCustomerBodyModel;
import com.echo.bot.model.GetLastMessageResponseModel;
import com.echo.bot.model.UserMessageBodyModel;
import com.echo.bot.model.UserMessageResponseModel;
import retrofit2.Call;
import retrofit2.http.*;

public interface BackendService {

    @POST("/customer/init")
    Call<Void> addCustomer(@Body AddCustomerBodyModel addCustomerBodyModel);

    @GET("/customer/exist/{id}")
    Call<Boolean> isCustomerExist(@Path("id") long id);

    @POST("/customer/index")
    Call<UserMessageResponseModel> saveMessage(@Body UserMessageBodyModel userMessageEventBodyModel);

    @GET("/customer/last")
    Call<GetLastMessageResponseModel> getLastMessage(@Path("id") long id);

    @PUT("/delay")
    Call<Void> updateDelay(@Path("value") long value);
}
