package com.example.poly_truyen_client.api;

import com.example.poly_truyen_client.models.User;
import com.google.gson.JsonObject;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface UserServices {
    @POST("user/create")
    Call<User> registerAccount(@Body JsonObject userPayload);

    @Multipart
    @PUT("user/update/{id}")
    Call<User> updateUserAvatar(@Part MultipartBody.Part avatar, @Path("id") String id);

    @PUT("user/update/{id}")
    Call<User> updateUserInformation(@Path("id") String id, @Body User userPayload);
}
