package com.example.gadsprojectapplication.path;

import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.gadsprojectapplication.MainActivity;
import com.example.gadsprojectapplication.R;
import com.example.gadsprojectapplication.User;
import com.example.gadsprojectapplication.room.UserDatabase;
import com.example.gadsprojectapplication.work.GetUserWork;
import com.example.gadsprojectapplication.work.InsertUseWrork;
import com.example.gadsprojectapplication.work.TestUserNumber;

public class AFragment extends Fragment {
    //lets use a view to get this fragments view
    View view = null;
    MainActivity.ShareData shareData;
    User user;
    public AFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //perhaps we didn' need that
        //we have added views to frag a and then frag b
        //now to navigate between them
        final NavController navController = Navigation.findNavController(view);
        shareData = new ViewModelProvider(requireActivity()).get(MainActivity.ShareData.class);
        user = new User();//we might use that here
        OneTimeWorkRequest oneTimeWorkRequest = new OneTimeWorkRequest//this will instantiate the worker class
                .Builder(TestUserNumber.class)
                .build();
        WorkManager workManager=WorkManager.getInstance();
        workManager.enqueue(oneTimeWorkRequest);//lets use testnymber worker to see if the daabase has one row in it. if it does we
        //use that row if not we add a new row


        final EditText name = view.findViewById(R.id.data1);//we nw will use three edittexts to set three values of our user class
        final EditText email = view.findViewById(R.id.email);
        final EditText age = view.findViewById(R.id.age);
        workManager.getWorkInfoByIdLiveData(oneTimeWorkRequest.getId())
                .observe(getViewLifecycleOwner(), new Observer<WorkInfo>() {
                    @Override
                    public void onChanged(WorkInfo workInfo) {
                        Data data = workInfo.getOutputData();
                        if (data.getInt("size",0)==1){
                            OneTimeWorkRequest get_user = new OneTimeWorkRequest
                                    .Builder(GetUserWork.class)
                                    .build();
                            WorkManager workManager1=WorkManager.getInstance();
                            workManager1.enqueue(get_user);
                            workManager1.getWorkInfoByIdLiveData(get_user.getId())
                                    .observe(getViewLifecycleOwner(), new Observer<WorkInfo>() {
                                        @Override
                                        public void onChanged(WorkInfo workInfo) {
                                            Data data1 = workInfo.getOutputData();
                                            //String user = data1.getString("name");
                                            name.setText(data1.getString("name"));
                                            //name.setEnabled(false);
                                            email.setText(data1.getString("email"));//lets run it to see
                                           // name.setEnabled(false);
                                            age.setText(""+data1.getInt("age",18));
                                            //age.setEnabled(false);//the problem might be...
                                            //Toast.makeText(getContext(),""+data.getInt("size",0)+" \n"+user,Toast.LENGTH_LONG).show();
//all that means there is one row in the sqlite table now. ok then.
                                        }//lets see if it has a row in its table
                                        //the problem may be
                                    });


                        }
                    }
                });

        final Button nav_from_a = view.findViewById(R.id.nav_a);


        nav_from_a.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {//here we collect and set the first three values of name, email and age
                //but first...
                //now set the user's first three values
                user.setName(name.getText().toString());
                user.setEmail(email.getText().toString());
                user.setAge(Integer.parseInt(age.getText().toString()));
                shareData.setShare(user);//we will tak the string from the edittext
                //the sharedata class will persist these first three class values as long as the app is living
                //lets add the post value in fragment b
                navController.navigate(R.id.action_AFragment_to_BFragment);//lets see the action in the navigation grah to go to frag b

            }//now lets coplete the activity main for the navigation
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_a, container, false);

        return view;
    }
}