package com.example.poly_truyen_client.api;

import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

public class ConnectAPI {

    public String host = "172.20.10.3:3001";
    public String API_URL = "http://" + host + "/";
    public Retrofit connect = new Retrofit.Builder().baseUrl(API_URL).addConverterFactory(GsonConverterFactory.create()).build();

}
