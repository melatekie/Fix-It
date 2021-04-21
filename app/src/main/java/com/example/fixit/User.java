package com.example.fixit;

import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;

@ParseClassName("User")
public class User extends ParseObject {

    public static final String KEY_FIRST_NAME = "firstName";
    public static final String KEY_LAST_NAME = "lastName";
    public static final String KEY_EMAIL = "email";

    public static final String KEY_PROFILE_IMAGE = "profileImage";
    public static final String KEY_WHO = "who";
    public static final String KEY_IS_PROFESSIONAL = "isProfessional";


    //--------------------Getters and Setters----------------------

    //First Name
    public String getFirstName(){
        return getString(KEY_FIRST_NAME);
    }
    public void setFirstName(String firstName){
        put(KEY_FIRST_NAME, firstName);
    }

    //Last Name
    public String getLastName(){
        return getString(KEY_LAST_NAME);
    }
    public void setLastName(String lastName){
        put(KEY_LAST_NAME, lastName);
    }

    //Email
    public String getEmail(){
        return getString(KEY_EMAIL);
    }
    public void setEmail(String email){
        put(KEY_EMAIL, email);
    }

    //Profile Image
    public ParseFile getProfileImage(){
        return getParseFile(KEY_PROFILE_IMAGE);
    }
    public void setImage(ParseFile parseFile){
        put(KEY_PROFILE_IMAGE, parseFile);
    }

    //Who
    public ParseUser getWho(){
        return getParseUser(KEY_WHO);
    }
    public void setWho(ParseUser who){
        put(KEY_WHO, who);
    }


}
