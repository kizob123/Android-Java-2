package com.example.gadsprojectapplication.online;

import com.example.gadsprojectapplication.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface UsersAPI {
    @GET("users")
    Call<List<User>>getUsers();//this function will get the list of users
    @POST("users")
    Call<User>createUser(@Body User user);

    @GET("posts")
    Call<List<User>>getPost();
    @POST("posts")
    Call<User>createPost(@Body User user);//this function ill create posts of the users
}//we might create user in the frag c
