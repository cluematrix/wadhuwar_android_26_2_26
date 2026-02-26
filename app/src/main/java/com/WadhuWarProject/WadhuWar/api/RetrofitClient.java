package com.WadhuWarProject.WadhuWar.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    public static final String basePath="https://wadhuwar.com/Api_con/";
//    private static final String basePath="https://wadhuwar.com/Api_con/";
//    private static final String basePath="https://buddhistwadhuwar.com/Api_con/";
//    private static final String basePath="https://wadhuwar.com/Testing/Api_con/";
//        public static final String basePath="https://buddishtwadhuwar.com/bdnov/Api_con/";                      // testing ip commented by

//    public static final String basePath="https://buddishtwadhuwar.com/ertest/Api_con/";

    private static Retrofit getRetrofitInstance(){
        Gson gson= new GsonBuilder().setLenient().create();
//        return new Retrofit.Builder().baseUrl(basePath).addConverterFactory(GsonConverterFactory.create()).build();
        return new Retrofit.Builder().baseUrl(basePath).addConverterFactory(GsonConverterFactory.create(gson)).build();
    }

    public static Api getApiService(){
        return getRetrofitInstance().create(Api.class);
    }

}
