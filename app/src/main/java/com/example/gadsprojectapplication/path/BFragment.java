package com.example.gadsprojectapplication.path;

import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.gadsprojectapplication.MainActivity;
import com.example.gadsprojectapplication.R;
import com.example.gadsprojectapplication.User;
import com.example.gadsprojectapplication.room.UserDatabase;

public class BFragment extends Fragment {
    MainActivity.ShareData shareData;//u might need this fragment for something else so you can make the sharedata variable
    //accessible thru a get function later

    public BFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final NavController navController = Navigation.findNavController(view);


        final EditText post = view.findViewById(R.id.post);
        final Button b_button = view.findViewById(R.id.post_button);
        shareData =new ViewModelProvider(requireActivity()).get(MainActivity.ShareData.class);//this shareData will now contain the original user class with the
        //class variables having the values you setted from before
        b_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //now to set the lst post value
                shareData.getShare().observe(getViewLifecycleOwner(),user->{
                    user.setPost(post.getText().toString());
                });
                navController.navigate(R.id.action_BFragment_to_CFragment);//lets get the ation id from b frg to c frag
            }
        });
    }
//the view model exists as long as the application exists and keeps the live data the same throughout the lifetime of the app
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {//lets make a model class
        // Inflate the layout for this fragment
        //lets see if there is data already in the database we immediately go to post
        return inflater.inflate(R.layout.fragment_b, container, false);
    }
}