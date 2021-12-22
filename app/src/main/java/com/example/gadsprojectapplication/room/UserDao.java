package com.example.gadsprojectapplication.room;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.gadsprojectapplication.User;

import java.util.List;

//we need to add the room annotation for daos
@Dao
public interface UserDao {
    //we want only one row in this table to hold the user of this device
    @Query("select * from user")
    User getUser();//lets create a 'work' pakage
    //lets user a list getter to test number of users to make sure its only one
    @Query("select * from user")
    List<User> getUsers();
//in the wok package we will put two work manager classes, one to insert  a user and another to get back the user
    @Insert
    void insertUser(User user);
    @Update
    void updateUser(User user);//lets edit our user class to use it as a database entity
}
