package com.example.poly_truyen_client.api;

import com.example.poly_truyen_client.models.User;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface AuthServices {
    @POST("sign/in")
    Call<User> signIn(@Body JsonObject loginPayload);
}
