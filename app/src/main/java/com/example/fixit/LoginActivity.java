package com.example.fixit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.fixit.databinding.ActivityLoginBinding;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;


public class LoginActivity extends AppCompatActivity {

    //TESTING PURPOSES
    public static final String TAG = "LoginActivity";

    private ActivityLoginBinding binding;
    private Button btnSignUpPage;
    private Button btnLogin;
    private EditText etUsername;
    private EditText etPassword;

    ParseUser user = new ParseUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_login);

        //If the current user is not empty, then proceed to the main activity
        if (ParseUser.getCurrentUser() != null){
            goMainActivity();
            return;
        }

        //Upon clicking the signup button
        binding.btnSignUpPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(view.getContext(), SignUpActivity.class);
                startActivity(i);
            }
        });


        //Upon clicking the login button
        binding.btnLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "Login works!");


                String username = binding.etUsername.getText().toString();
                String password = binding.etPassword.getText().toString();

                loginUser(username, password);
            }
        });

    }

    //login function
    private void loginUser(String username, String password){
        Log.i(TAG, "Login function works!");

        //Calls the parse login function with the given username, password, and a new LogInCallBack call
        ParseUser.logInInBackground(username, password, new LogInCallback() {
            @Override
            //This is to check if it works or not
            public void done(ParseUser user, ParseException e) {
                //On fail
                if (e != null){
                    Log.e(TAG, "Login failed!", e);
                    Toast.makeText(LoginActivity.this,"PLEASE CHECK LOGIN, THERE WAS AN ISSUE!", Toast.LENGTH_SHORT).show();
                    return;
                }
                //Otherwise, proceed to mainActivity
                goMainActivity();
                Toast.makeText(LoginActivity.this,"You are now in!", Toast.LENGTH_SHORT).show();
                return;
            }
        });

    };


    //Function to go to mainActivity
    private void goMainActivity(){
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
        finish();
    }
}