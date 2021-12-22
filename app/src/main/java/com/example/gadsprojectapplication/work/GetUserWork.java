package com.example.gadsprojectapplication.work;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.work.Data;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.example.gadsprojectapplication.User;
import com.example.gadsprojectapplication.room.UserDatabase;

public class GetUserWork extends Worker {
    public GetUserWork(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        UserDatabase userDatabase = UserDatabase.getInstance(getApplicationContext());
        User user = userDatabase.userDao().getUser();
        if(user!=null) {
            Data data = new Data.Builder()
                    .putString("name", user.getName())
                    .putString("email", user.getEmail())
                    .putInt("age", user.getAge())
                    .build();
            return Result.success(data);//it shows that the database has  a size of zero initially. lets try to add something.oops diabled
        }//this is th data we will et from the data base
        return Result.success();
    }
}
