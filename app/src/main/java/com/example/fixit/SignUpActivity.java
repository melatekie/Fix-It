package com.example.fixit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;

import com.example.fixit.databinding.ActivitySignUpBinding;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.switchmaterial.SwitchMaterial;

public class SignUpActivity extends AppCompatActivity {

    private ActivitySignUpBinding binding;
    private MaterialToolbar toolbar;
    private SwitchMaterial smSwitch;
    private RelativeLayout rlUser, rlProf;
    private Button btnUser, btnProf;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_sign_up);

        smSwitch = binding.smSwitch;
        rlUser = binding.rlUser;
        rlProf = binding.rlProfessional;
        btnUser = binding.btnUserSignUp;
        btnProf = binding.btnProfSignUp;
        toolbar = binding.topAppBar;
        setSupportActionBar(toolbar);

        //switch between user and professional
        smSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if(isChecked){
                    rlProf.setVisibility(rlProf.VISIBLE);
                    rlUser.setVisibility(rlUser.GONE);
                }else {
                    rlProf.setVisibility(rlProf.GONE);
                    rlUser.setVisibility(rlUser.VISIBLE);
                }
            }
        });

        //onClick for back button
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(SignUpActivity.this, LoginActivity.class);
                startActivity(i);
            }
        });



    }
}