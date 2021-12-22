package com.example.gadsprojectapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;

import android.os.Bundle;
import android.widget.Toast;

import com.example.gadsprojectapplication.room.UserDatabase;
import com.example.gadsprojectapplication.work.GetUserWork;
import com.example.gadsprojectapplication.work.TestUserNumber;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        OneTimeWorkRequest oneTimeWorkRequest = new OneTimeWorkRequest//this will instantiate the worker class
                .Builder(TestUserNumber.class)
                .build();
        WorkManager workManager=WorkManager.getInstance();
        workManager.enqueue(oneTimeWorkRequest);
        workManager.getWorkInfoByIdLiveData(oneTimeWorkRequest.getId())
                .observe(this, new Observer<WorkInfo>() {
                    @Override
                    public void onChanged(WorkInfo workInfo) {
                        Data data = workInfo.getOutputData();
                        OneTimeWorkRequest get_user = new OneTimeWorkRequest
                                .Builder(GetUserWork.class)
                                .build();
                        WorkManager workManager1=WorkManager.getInstance();
                        workManager1.enqueue(get_user);
                        workManager1.getWorkInfoByIdLiveData(get_user.getId())
                                .observe(MainActivity.this, new Observer<WorkInfo>() {
                                    @Override
                                    public void onChanged(WorkInfo workInfo) {
                                        Data data1 = workInfo.getOutputData();
                                        String user = data1.getString("name");
                                        Toast.makeText(getApplicationContext(),""+data.getInt("size",0)+" \n"+user,Toast.LENGTH_LONG).show();

                                    }//lets see if it has a row in its table
                                    //the problem may be
                                });//lets create a package called 'online'
                        //we will put in it two classes which are one interface and a class for getting a retrofit instance

                        }
                });

        //or rather we put it in a workmanager to avoid delays

    }//json-server --watch db.json

    public static class ShareData extends ViewModel{
        //lets change the data type in the type parameter to User
        MutableLiveData<User> share = new MutableLiveData<>();
        public LiveData<User> getShare(){//lets run it to see
            return share;
        }
        public void setShare(User share1){
            share.setValue(share1);
            }//lets use room database framework to create a database to srore user data
        //first we create a 'room' package
        //in the 'room' packege we put in a database class and a data access object(DAO)
    }
}