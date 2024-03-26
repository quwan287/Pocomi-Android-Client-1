package com.example.poly_truyen_client.api;

import com.example.poly_truyen_client.models.Comic;
import com.example.poly_truyen_client.models.History;
import com.google.gson.JsonObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface HistoryServices {
    @GET("history/store")
    Call<History> storeHistory(@Query("user") String idUser, @Query("comic") String idComic);

    @GET("history/delete")
    Call<History> deleteHistory(@Query("user") String idUser, @Query("comic") String idComic);

    @GET("history/{idUser}")
    Call<ArrayList<Comic>> getCaches(@Path("idUser") String idUser);

    @GET("history/clear-cache/{idUser}")
    Call<JsonObject> clearCache(@Path("idUser") String idUser);
}
