package com.example.gadsprojectapplication;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

@Entity(tableName = "user")
public class User {
    @PrimaryKey(autoGenerate = true)
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    @SerializedName("name")
    @ColumnInfo(name = "name")
    private String name;
    @SerializedName("email")
    @ColumnInfo(name = "email")
    private String email;
    @SerializedName("post")
    private String post;
    @SerializedName("age")
    @ColumnInfo(name = "age")
    private int age;
//lets create another constructor for the database
public User(String name, String email, int age) {
    this.name = name;
    this.email = email;

    this.age = age;//now we can insert new row in the c frgment as that is the where we retrieve data from users
}
    @Ignore
    public User(String name, String email, String post, int age) {
        this.name = name;
        this.email = email;
        this.post = post;
        this.age = age;
    }
    //lets give the User a default constructor
    @Ignore
    public User(){

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
