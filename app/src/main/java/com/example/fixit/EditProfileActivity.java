package com.example.fixit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.fixit.databinding.ActivityEditProfileBinding;
import com.example.fixit.databinding.ActivityLoginBinding;

public class EditProfileActivity extends AppCompatActivity {

    private ActivityEditProfileBinding activityEditProfileBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        activityEditProfileBinding = DataBindingUtil.setContentView(this,R.layout.activity_edit_profile);

    }
}