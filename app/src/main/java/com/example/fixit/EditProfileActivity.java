package com.example.fixit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.fixit.databinding.ActivityEditProfileBinding;
import com.example.fixit.databinding.ActivityLoginBinding;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import org.parceler.Parcels;

import java.io.File;
import java.util.List;

public class EditProfileActivity extends AppCompatActivity {
    private String professionalID;
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
        ParseUser currentUser = ParseUser.getCurrentUser();
        if(user.getKeyIsProfessional()){
            ParseQuery<Professional> query = ParseQuery.getQuery(Professional.class);

            query.whereEqualTo(Professional.KEY_USER, currentUser);
            query.include(Professional.KEY_USER);
            query.findInBackground(new FindCallback<Professional>() {
                @Override
                public void done(List<Professional> professionals, ParseException e) {
                    if (e!=null){

                        return;
                    }
                    professionalID = professionals.get(0).getObjectId();
                    activityEditProfileBinding.setProfessional(professionals.get(0));
                }
            });
        }
        activityEditProfileBinding.setUser(user);



        activityEditProfileBinding.btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ParseUser currentUser = ParseUser.getCurrentUser();
                String street="";
                String company="";
                String state="";
                String unit="";
                String city="";
                String zipcode="";
                String title="";
                String phone = "";
                String password = "";
                String firstName = "";
                String lastName = "";
                String email="";
                if(user.getKeyIsProfessional()) { // is professional

                    street = activityEditProfileBinding.etStreet.getText().toString();
                    company = activityEditProfileBinding.etCompany.getText().toString();
                    state = activityEditProfileBinding.etState.getText().toString();
                    unit = activityEditProfileBinding.etUnit.getText().toString();
                    city = activityEditProfileBinding.etCity.getText().toString();
                    title = activityEditProfileBinding.etTitle.getText().toString();
                    zipcode = activityEditProfileBinding.etZipcode.getText().toString();
                    phone = activityEditProfileBinding.etPhone.getText().toString();
                    saveProfessional(street, company, state, unit, city, zipcode, title, phone);
                }
                password=activityEditProfileBinding.etPassword.getText().toString();
                firstName=activityEditProfileBinding.etFirstName.getText().toString();
                lastName=activityEditProfileBinding.etLastName.getText().toString();
                email=activityEditProfileBinding.etEmail.getText().toString();
                saveUser(currentUser,password, firstName, lastName, email);

                goMainActivity();
            }
        });
    }

    private void saveUser(ParseUser currentUser, String password, String firstName, String lastName, String email) {
        if (currentUser != null) {
            // Other attributes than "email" will remain unchanged!
            if(email.length()>0) currentUser.put(User.KEY_EMAIL, email);
            if(lastName.length()>0) currentUser.put(User.KEY_LAST_NAME, lastName);
            if(firstName.length()>0) currentUser.put(User.KEY_FIRST_NAME, firstName);
            if(password.length()>0) currentUser.setPassword(password);
            // Saves the object.
            currentUser.saveInBackground(e -> {
                if(e==null){
                    //Save successfull
                    Toast.makeText(this, "Save Successful", Toast.LENGTH_SHORT).show();
                }else{
                    // Something went wrong while saving
                    Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }

    }

    private void goMainActivity() {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }
    private void saveProfessional(String street,String company, String state, String unit, String city, String zipcode, String title, String phone) {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Professional");
        // Retrieve the object by id

        query.getInBackground(professionalID, (object, e) -> {
            if (e == null) {
                //Object was successfully retrieved
                // Update the fields we want to
                if(title.length()>0) object.put("title", title);
                if(company.length()>0) object.put("company",company);
                if(unit.length()>0) object.put("unit",unit);
                if(zipcode!="null" ||zipcode.length()>0 ){
                   Number zip=Integer.parseInt(zipcode);
                   object.put(Professional.KEY_ZIPCODE,zip);
                }
                if(phone!="null"||phone.length()>0) {
                    Number pphone = Integer.parseInt(phone);
                    object.put(Professional.KEY_PHONE,pphone);
                }
                if(state.length()>0) object.put("state", state);

                if(city.length()>0) object.put("city", city);
                if(street.length()>0) object.put("street", street);

                //All other fields will remain the same
                object.saveInBackground();

            } else {
                // something went wrong
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}