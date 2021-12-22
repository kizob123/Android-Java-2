package com.example.gadsprojectapplication.online;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitForUsers {
    //here we will create an instance of retrofit
    private static  Retrofit retrofit;
    //lets create the api
    public final static String  BASE_URL = "http://10.0.2.2:3000";//on android this is how you get localhost
    public static Retrofit getRetrofitInstance(){
        if(retrofit==null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;//this is how you create a retrofit instance
    }

}
