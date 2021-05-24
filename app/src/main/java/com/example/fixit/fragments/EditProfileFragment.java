package com.example.fixit.fragments;

import android.content.ClipData;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.fixit.LoginActivity;
import com.example.fixit.MainActivity;
import com.example.fixit.Professional;
import com.example.fixit.R;
import com.example.fixit.User;
import com.example.fixit.databinding.FragmentEditProfileBinding;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.List;

import static com.parse.ParseUser.logOut;


public class EditProfileFragment extends Fragment {
    private String professionalID;
    private FragmentEditProfileBinding fragmentEditProfileBinding;
    public EditProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_edit_profile, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        fragmentEditProfileBinding = FragmentEditProfileBinding.bind(view);
        ParseUser currentUser = ParseUser.getCurrentUser();
        User user = new User();
        user.setEmail(currentUser.getEmail());
        user.setUsername(currentUser.getUsername());
        user.setFirstName(currentUser.getString("firstName"));
        user.setLastName(currentUser.getString("lastName"));
        user.setIsProfessional(currentUser.getBoolean("isProfessional"));
        if(currentUser.getParseFile("profileImage")!=null){
            user.setImage(currentUser.getParseFile("profileImage"));
        }
        Professional professional  = new Professional();
        user.setObjectID(currentUser.getObjectId());
        fragmentEditProfileBinding.setProfessional(professional);

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
                    fragmentEditProfileBinding.setProfessional(professionals.get(0));


                }
            });
        }

        fragmentEditProfileBinding.setUser(user);

        fragmentEditProfileBinding.btnSave.setOnClickListener(new View.OnClickListener() {
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
                String email="";
                if(user.getKeyIsProfessional()) { // is professional

                    street = fragmentEditProfileBinding.etStreet.getText().toString();
                    company = fragmentEditProfileBinding.etCompany.getText().toString();
                    state = fragmentEditProfileBinding.etState.getText().toString();
                    unit = fragmentEditProfileBinding.etUnit.getText().toString();
                    city = fragmentEditProfileBinding.etCity.getText().toString();
                    title = fragmentEditProfileBinding.etTitle.getText().toString();
                    zipcode = fragmentEditProfileBinding.etZipcode.getText().toString();
                    phone = fragmentEditProfileBinding.etPhone.getText().toString();
                    saveProfessional(street, company, state, unit, city, zipcode, title, phone);
                }
                password=fragmentEditProfileBinding.etPassword.getText().toString();
                email=fragmentEditProfileBinding.etEmail.getText().toString();
                saveUser(currentUser,password,  email);

                goMainActivity();
            }
        });

        //logOut button in
        fragmentEditProfileBinding.topAppBar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.logout:
                        logOut();
                        break;

                }
                return false;
            }
        });

    }

    private void logOut() {
        ParseUser.logOut();
        ParseUser currentUser = ParseUser.getCurrentUser(); // this will now be null
        if (currentUser == null) {
            Intent i = new Intent(getContext(), LoginActivity.class);
            startActivity(i);
            getActivity().finish();
        }
    }
    private void saveUser(ParseUser currentUser, String password,  String email) {
        if (currentUser != null) {
            // Other attributes than "email" will remain unchanged!
            if(email.length()>0) currentUser.put(User.KEY_EMAIL, email);

            if(password.length()>0) currentUser.setPassword(password);
            // Saves the object.
            currentUser.saveInBackground(e -> {
                if(e==null){
                    //Save successfull
                    Toast.makeText(getContext(), "Save Successful", Toast.LENGTH_SHORT).show();
                }else{
                    // Something went wrong while saving
                    Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }

    }

    private void goMainActivity() {
        Intent i = new Intent(getContext(), MainActivity.class);
        startActivity(i);
    }
    private void saveProfessional(String street,String company, String state, String unit, String city, String zipcode, String title, String phone) {
        ParseQuery<ParseObject> professional = ParseQuery.getQuery("Professional");
        // Retrieve the object by id

        professional.getInBackground(professionalID, (object, e) -> {
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
                Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });



    }
}