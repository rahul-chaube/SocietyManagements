package com.SocietyManagements.controller;

import android.content.Context;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public abstract class RetrofitConfig {

    public static RequestAPI getClient(Context context)
    {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://maps.googleapis.com/maps/api/place/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return   retrofit.create(RequestAPI.class);
    }
}
