package com.example.fixit.fragments;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.fixit.EditProfileActivity;
import com.example.fixit.LoginActivity;
import com.example.fixit.MainActivity;
import com.example.fixit.Post;
import com.example.fixit.Professional;
import com.example.fixit.R;
import com.example.fixit.SignUpActivity;
import com.example.fixit.User;
import com.example.fixit.databinding.FragmentPostsBinding;
import com.example.fixit.databinding.FragmentProfileBinding;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import org.parceler.Parcels;

import java.io.ByteArrayOutputStream;
import java.util.List;


public class ProfileFragment extends Fragment {

    private FragmentProfileBinding fragmentProfileBinding;
    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        fragmentProfileBinding = FragmentProfileBinding.bind(view);
        ParseUser currentUser = ParseUser.getCurrentUser();
        User user = new User();

        //Potential issues with null field such as email or others causing crashing upon clicking 'profile', added fix to setter
        user.setEmail(currentUser.getEmail());
        user.setUsername(currentUser.getUsername());
        user.setFirstName(currentUser.getString("firstName"));
        user.setLastName(currentUser.getString("lastName"));
        user.setIsProfessional(currentUser.getBoolean("isProfessional"));
        if(user.getParseFile("profileImage")!=null){
            user.setImage(user.getParseFile("profileImage"));
        }
        Professional professional  = new Professional();
        user.setObjectID(currentUser.getObjectId());
        fragmentProfileBinding.setProfessional(professional);

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

                    fragmentProfileBinding.setProfessional(professionals.get(0));


                }
            });
        }


        fragmentProfileBinding.setUser(user);

        fragmentProfileBinding.ivEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(view.getContext(), EditProfileActivity.class);
                i.putExtra("user", Parcels.wrap(user));

                startActivity(i);
            }
        });
        fragmentProfileBinding.topAppBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(view.getContext(), MainActivity.class);
                startActivity(i);
            }
        });

    }
}