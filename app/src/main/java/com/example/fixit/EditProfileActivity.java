package com.example.fixit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.fixit.databinding.ActivityEditProfileBinding;
import com.example.fixit.databinding.ActivityLoginBinding;

import org.parceler.Parcels;

public class EditProfileActivity extends AppCompatActivity {

    private ActivityEditProfileBinding activityEditProfileBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        activityEditProfileBinding = DataBindingUtil.setContentView(this,R.layout.activity_edit_profile);
        //onClick for back button
        activityEditProfileBinding.topAppBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(view.getContext(), MainActivity.class);
                startActivity(i);
            }
        });
        User user= Parcels.unwrap(getIntent().getParcelableExtra("user"));
        activityEditProfileBinding.setUser(user);
        activityEditProfileBinding.btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(user.getKeyIsProfessional()){ // is professional
                    //https://github.com/liulanz/Instagram-Parse-App/blob/master/app/src/main/java/com/example/instagram/fragments/ComposeFragment.java
                }
                else{               // not professional

                }

                goMainActivity();
            }
        });
    }
    private void goMainActivity() {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }
}