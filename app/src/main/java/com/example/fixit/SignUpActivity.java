package com.example.fixit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.fixit.databinding.ActivitySignUpBinding;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.switchmaterial.SwitchMaterial;
import com.google.android.material.textfield.TextInputEditText;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.parse.SignUpCallback;

import java.util.BitSet;

public class SignUpActivity extends AppCompatActivity {

    public static final String TAG = "SignUpActivity";
    private ActivitySignUpBinding binding;
    private MaterialToolbar toolbar;
    private SwitchMaterial smSwitch;
    private RelativeLayout rlUser, rlProf;
    private Button btnUser, btnProf;
    private TextInputEditText etFirst, etLast, etUsername, etEmail, etPassword, etConfirmPwd;
    private TextInputEditText etJobTitle, etPhone, etCompany, etWorkStreet, etUnit, etCity, etState, etZip;

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
        etFirst = binding.etFirst;
        etLast = binding.etLast;
        etUsername = binding.etUsername;
        etEmail = binding.etEmail;
        etPassword = binding.etPassword;
        etConfirmPwd = binding.etConfirmPassword;
        etJobTitle = binding.etJobTitle;
        etPhone = binding.etPhone;
        etCompany = binding.etCompany;
        etWorkStreet = binding.etStreet;
        etUnit = binding.etUnit;
        etCity = binding.etCity;
        etState = binding.etState;
        etZip = binding.etZipcode;

        setSupportActionBar(toolbar);

        //switch between user and professional in layout
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
                finish();
            }
        });

        if (ParseUser.getCurrentUser() != null) {
            goMainActivity();
        }

        btnUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserSignUp();
            }
        });

        btnProf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ProfSignUp();
            }
        });
    }

    //register the user and does not log in
    private void ProfSignUp() {

        ParseUser user = new ParseUser();
        user.setUsername(etUsername.getText().toString());
        user.setPassword(etPassword.getText().toString());
        user.setEmail(etEmail.getText().toString());
        //user.put("who", prof);
        user.put("firstName", etFirst.getText().toString());
        user.put("lastName", etLast.getText().toString());
        user.put("isProfessional", true);

        String email = etEmail.getText().toString();
        String password = etPassword.getText().toString();
        String pdConfirm = (etConfirmPwd.getText()).toString();

        if (email.isEmpty()) {
            Toast.makeText(this, "Email cannot be empty", Toast.LENGTH_SHORT).show();
            return;
        }
        if (password.equals(pdConfirm)) {
            // Invoke signUpInBackground
            user.signUpInBackground(new SignUpCallback() {
                public void done(ParseException e) {
                    if (e == null) {
                        Professional prof = new Professional();
                        prof.setUser(ParseUser.getCurrentUser());
                        prof.setTitle(etJobTitle.getText().toString());
                        prof.setPhone(Integer.parseInt(etPhone.getText().toString()));
                        prof.setCompany(etCompany.getText().toString());
                        prof.setStreet(etWorkStreet.getText().toString());
                        prof.setUnit(etUnit.getText().toString());
                        prof.setCity(etCity.getText().toString());
                        prof.setState(etState.getText().toString());
                        prof.setZipcode(Integer.parseInt(etZip.getText().toString()));

                        prof.saveInBackground(new SaveCallback() {
                            @Override
                            public void done(ParseException e) {
                                if (e != null) {
                                    Log.e(TAG, "Error while saving", e);
                                    Toast.makeText(SignUpActivity.this,  e.getMessage(), Toast.LENGTH_SHORT).show();
                                    return;
                                }
                                Log.i(TAG, "Info save was successful!");

                            }
                        });
                        Toast.makeText(SignUpActivity.this, "Thanks for signing up. Please wait for account verification.", Toast.LENGTH_LONG).show();

                        ParseUser.logOut();
                        goLoginActivity();
                    } else {
                        ParseUser.logOut();
                        Toast.makeText(SignUpActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            });
        } else {
            Toast.makeText(SignUpActivity.this, "Confirm password is not the same.", Toast.LENGTH_LONG).show();
        }

    }

    //register the user and logs in immediately
    private void UserSignUp() {
        ParseUser user = new ParseUser();
        user.setUsername(etUsername.getText().toString());
        user.setPassword(etPassword.getText().toString());
        user.setEmail(etEmail.getText().toString());
        user.put("firstName", etFirst.getText().toString());
        user.put("lastName", etLast.getText().toString());

        String username = etUsername.getText().toString();
        String email = etEmail.getText().toString();
        String password = etPassword.getText().toString();
        String pdConfirm = (etConfirmPwd.getText()).toString();

        if (email.isEmpty()) {
            Toast.makeText(this, "Email cannot be empty", Toast.LENGTH_SHORT).show();
            return;
        }
        if (password.equals(pdConfirm)) {
            // Invoke signUpInBackground
            user.signUpInBackground(new SignUpCallback() {
                public void done(ParseException e) {
                    if (e == null) {
                        Toast.makeText(SignUpActivity.this, "Successful Sign Up!", Toast.LENGTH_SHORT).show();
                        loginUser(username, password);

                    } else {
                        ParseUser.logOut();
                        Toast.makeText(SignUpActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            });
        } else {
            Toast.makeText(SignUpActivity.this, "Confirm password is not the same.", Toast.LENGTH_LONG).show();
        }
    }

    private void loginUser(String username, String password) {
        Log.i(TAG, "Attempting to login user " + username);
        ParseUser.logInInBackground(username, password, new LogInCallback() {
            @Override
            public void done(ParseUser user, ParseException e) {
                if (e != null) {
                    Log.e(TAG, "Issue with login", e);
                    Toast.makeText(SignUpActivity.this, "Issue with login", Toast.LENGTH_SHORT).show();
                    return;
                }
                //Navigate to the main activity if the user has signed in properly
                goMainActivity();
            }
        });
    }

    private void goMainActivity() {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
        finish();
    }

    private void goLoginActivity() {
        Intent i = new Intent(this, LoginActivity.class);
        startActivity(i);
        finish();
    }
}