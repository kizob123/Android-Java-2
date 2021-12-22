package com.example.gadsprojectapplication.path;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gadsprojectapplication.MainActivity;
import com.example.gadsprojectapplication.R;
import com.example.gadsprojectapplication.User;
import com.example.gadsprojectapplication.online.RetrofitForUsers;
import com.example.gadsprojectapplication.online.UsersAPI;
import com.example.gadsprojectapplication.work.InsertUseWrork;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class CFragment extends Fragment{



    public CFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final TextView the_name = view.findViewById(R.id.the_name);
        final TextView the_email = view.findViewById(R.id.the_email);
        final TextView the_age = view.findViewById(R.id.the_age);
        final TextView the_post = view.findViewById(R.id.the_post);//here we set the textvieiws with all the user variables where we need to
        //it shoud have persistd since its in the viewmodel
        MainActivity.ShareData shareData = new ViewModelProvider(requireActivity()).get(MainActivity.ShareData.class);
        shareData.getShare().observe(getViewLifecycleOwner(),user->{
            the_name.setText(user.getName());
            the_email.setText(user.getEmail());
            the_age.setText(""+user.getAge());//better to do this because settext is strict on strings... ha  ah h...!!!
            the_post.setText(user.getPost());
            //we might apply the isert here
            //we will use the Data class to give getInputData() its data
            Data data = new Data.Builder()//this object will hold the data to insert in the getInputData
                    .putString("name",user.getName())
                    .putString("email",user.getEmail())
                    .putInt("get",user.getAge())
                    .build();
            OneTimeWorkRequest oneTimeWorkRequest = new OneTimeWorkRequest//this will instantiate the worker class
                    .Builder(InsertUseWrork.class)
                    .setInputData(data)//this will input data for the worker class' getIutDta
                    .build();//this will build the work rquest
            WorkManager.getInstance().enqueue(oneTimeWorkRequest);
            //we could post later posts here
            Retrofit retrofit = RetrofitForUsers.getRetrofitInstance();//base url = localhost
            UsersAPI usersAPI = retrofit.create(UsersAPI.class);// attach /users or /posts//this is how you instantiate a retrofit to
            //get the api
            Call<User> postCall =usersAPI.createPost(new User(user.getName(), user.getEmail(), user.getPost(),user.getAge()));
            postCall.enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    User user1 = response.body();
                    Toast.makeText(getContext(),"new post "+user1.getPost()+" has been created in online db",Toast.LENGTH_LONG).show();
                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {
                    Toast.makeText(getContext(),"Something wrong: "+t.getMessage(),Toast.LENGTH_LONG).show();
                }//lets see if something goes wrong
            });
        });
        //lets run it to see
        Button button = view.findViewById(R.id.button);
        final TextView textView = view.findViewById(R.id.textView5);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                textView.setText("helloooooooo!");
            }//lets run this
        });//watch what hppens when i push the button and rotate
    }//  you can see the user values persisted. lets rotate the view
    //you can see the textview from c frg dd not persist its own value.
    //all for now
//it still persists
    //lets add a view on the last fragment
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_c, container, false);
    }

}