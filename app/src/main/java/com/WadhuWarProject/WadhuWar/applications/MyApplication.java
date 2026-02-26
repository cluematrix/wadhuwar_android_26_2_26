package com.WadhuWarProject.WadhuWar.applications;

import android.app.Application;
import android.util.Log;
import android.view.View;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;
import androidx.lifecycle.ProcessLifecycleOwner;

import com.WadhuWarProject.WadhuWar.SharedPrefManagerFile.SharedPrefManager;
import com.WadhuWarProject.WadhuWar.activity.ChatActivity;
import com.WadhuWarProject.WadhuWar.api.Api;
import com.WadhuWarProject.WadhuWar.api.RetrofitClient;
import com.WadhuWarProject.WadhuWar.model.InsertResponse;
import com.WadhuWarProject.WadhuWar.model.UserData;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyApplication extends Application implements LifecycleObserver {
    UserData userData;

    @Override
    public void onCreate() {
        super.onCreate();
        ProcessLifecycleOwner.get().getLifecycle().addObserver(this);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    private void onAppBackgrounded() {
        Log.d("MyApp-------", "App in background");

        setStatusUse("0");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    private void onAppForegrounded() {
        Log.d("MyApp=======", "App in foreground");

        setStatusUse("1");
    }


    public  void  setStatusUse(String status){

        userData = SharedPrefManager.getInstance(MyApplication.this).getUser();

        System.out.println("user id======="  + userData.getUser_id());

        Api apiService = RetrofitClient.getApiService();
        Call<InsertResponse> userResponse = apiService.setStatusOnline(String.valueOf(userData.getUser_id()),status);
        userResponse.enqueue(new Callback<InsertResponse>() {

            @Override
            public void onResponse(Call<InsertResponse> call, Response<InsertResponse> response) {
                if (response.isSuccessful()) {
                    System.out.println("1111status resp-----------" +status+ "-------->" +  new Gson().toJson(response.body()));


                    if(response.body().getResid().equals("200")){


                    }else{

                    }
                }

            }

            @Override
            public void onFailure(Call<InsertResponse> call, Throwable t) {

                System.out.println("1111chat status******" + t.toString());

            }
        });
    }

}