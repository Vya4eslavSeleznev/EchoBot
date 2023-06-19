package com.echo.bot.web;

import com.echo.bot.model.AddCustomerBodyModel;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface BackendService {

    @POST("/customer/init")
    Call<Void> addCustomer(@Body AddCustomerBodyModel addCustomerBodyModel);

    @GET("/customer/exist/{id}")
    Call<Boolean> isCustomerExist(@Path("id") long id);
}
