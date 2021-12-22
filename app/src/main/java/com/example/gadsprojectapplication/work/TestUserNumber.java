package com.example.gadsprojectapplication.work;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.work.Data;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.example.gadsprojectapplication.User;
import com.example.gadsprojectapplication.room.UserDatabase;

import java.util.List;

public class TestUserNumber extends Worker {
    public TestUserNumber(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        UserDatabase userDatabase = UserDatabase.getInstance(getApplicationContext());//lets also test if the database already has data here
        List<User> users = userDatabase.userDao().getUsers();
        Data data = new Data.Builder()

                .putInt("size", users.size())
                .build();
        return Result.success(data);

    }
}
