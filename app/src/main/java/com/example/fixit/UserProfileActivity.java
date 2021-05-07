package com.example.fixit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.fixit.databinding.ActivityEditProfileBinding;
import com.example.fixit.databinding.ActivityUserProfileBinding;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import org.parceler.Parcels;

import java.util.List;

public class UserProfileActivity extends AppCompatActivity {
    private ActivityUserProfileBinding activityUserProfileBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        activityUserProfileBinding =DataBindingUtil.setContentView(this,R.layout.activity_user_profile);
        activityUserProfileBinding.topAppBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(view.getContext(), MainActivity.class);
                startActivity(i);
            }
        });
        User user= Parcels.unwrap(getIntent().getParcelableExtra("user"));
        ParseUser parseUser = Parcels.unwrap(getIntent().getParcelableExtra("ParseUser"));
        activityUserProfileBinding.setUser(user);
        Professional professional  = new Professional();

        activityUserProfileBinding.setProfessional(professional);

        if(user.getKeyIsProfessional()){
            ParseQuery<Professional> query = ParseQuery.getQuery(Professional.class);

            query.whereEqualTo(Professional.KEY_USER, parseUser);
//            query.include(Professional.KEY_USER);
            query.findInBackground(new FindCallback<Professional>() {
                @Override
                public void done(List<Professional> professionals, ParseException e) {
                    if (e!=null){

                        return;
                    }

                    activityUserProfileBinding.setProfessional(professionals.get(0));


                }
            });
        }
    }
}


