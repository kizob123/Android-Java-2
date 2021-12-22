package com.example.gadsprojectapplication.work;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.example.gadsprojectapplication.User;
import com.example.gadsprojectapplication.online.RetrofitForUsers;
import com.example.gadsprojectapplication.online.UsersAPI;
import com.example.gadsprojectapplication.room.UserDatabase;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class InsertUseWrork extends Worker {
    public InsertUseWrork(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        //here we tryto get data from the user and insert in into our database
        String name = getInputData().getString("name");
        String email = getInputData().getString("email");
        int age = getInputData().getInt("age",18);
        //now we instntiate the database
        UserDatabase userDatabase = UserDatabase.getInstance(getApplicationContext());//lets also test if the database already has data here
        User user = userDatabase.userDao().getUser();
        if (user==null) {//lets test it again to see
            userDatabase.userDao().insertUser(new User(name, email, age));
            //maybe we might put it here. but in a good application you need to use different paths for
            //creating users and getting users maybe using a create user button and a get post buttun.
            Retrofit retrofit = RetrofitForUsers.getRetrofitInstance();//base url = localhost
            UsersAPI usersAPI = retrofit.create(UsersAPI.class);// attach /users or /posts//this is how you instantiate a retrofit to
            //get the api
            Call<User> userCall =usersAPI.createUser(new User(name, email, age));
            userCall.enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    User user1 = response.body();
                    Toast.makeText(getApplicationContext(),"user "+user1.getName()+" has been stored in online db",Toast.LENGTH_LONG).show();
                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {
                    Toast.makeText(getApplicationContext(),"Something wrong: "+t.getMessage(),Toast.LENGTH_LONG).show();
                }
            });
        }
        return Result.success();//getInputData() gets whatever data was supplied to the workmanager
    }
}//lets see if the data has one data in it.
