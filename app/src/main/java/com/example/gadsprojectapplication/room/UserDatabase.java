package com.example.gadsprojectapplication.room;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.gadsprojectapplication.User;

//make this class abstract and extend RoomDatabase
//add anottations for room database
@Database(entities = User.class,version = 2)
public abstract class UserDatabase extends RoomDatabase {
    private static String DB_NAME = "user_db";
    private static UserDatabase instance;
    public abstract UserDao userDao();
    public static synchronized UserDatabase getInstance(Context context){
        if(instance ==null){
            instance = Room.databaseBuilder(context.getApplicationContext(),UserDatabase.class,DB_NAME)
                    .fallbackToDestructiveMigration()
                    .build();
        }//perhaps i need to build it while online
        return instance;
    }
}//these are what ou need to do within the database class. now we should deal with the dao
