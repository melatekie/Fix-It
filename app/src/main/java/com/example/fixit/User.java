package com.example.fixit;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.parse.GetDataCallback;
import com.parse.ParseClassName;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;

import org.parceler.Parcel;

@ParseClassName("User")
//@Parcel
public class User extends ParseObject {

    //Parceler requirement
    public User(){
    }

    public static final String KEY_FIRST_NAME = "firstName";
    public static final String KEY_LAST_NAME = "lastName";
    public static final String KEY_EMAIL = "email";
    public static final String KEY_USERNAME = "username";
    public static final String KEY_PROFILE_IMAGE = "profileImage";
    public static final String KEY_IS_PROFESSIONAL = "isProfessional";
    public static final String KEY_OBJECT_ID = "objectID";



    //--------------------Getters and Setters----------------------
    // Object ID (user id)
    public String getKeyObjectID() { return getString(KEY_OBJECT_ID); }
    public void setObjectID(String id) {put(KEY_OBJECT_ID, id); }


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

    public void setEmail(String email){ put(KEY_EMAIL, email); }


    //Profile Image
    public ParseFile getProfileImage(){
        return getParseFile(KEY_PROFILE_IMAGE);
    }
    public void setImage(ParseFile parseFile){
        put(KEY_PROFILE_IMAGE, parseFile);
    }


    //IsProfessional
    public Boolean getKeyIsProfessional() { return getBoolean(KEY_IS_PROFESSIONAL); }
    public void setIsProfessional(boolean isProfessional) { put(KEY_IS_PROFESSIONAL, isProfessional); }

    //username
    public String getKeyUsername() { return getString(KEY_USERNAME); }
    public void setUsername(String username){
        put(KEY_USERNAME, username);
    }


    @BindingAdapter({"android:loadImage"})
    public static void loadImage(ImageView img, User user ) {

        if (user.getProfileImage() != null) {

            user.getProfileImage() .getDataInBackground(new GetDataCallback() {
                @Override
                public void done(byte[] data, ParseException e) {
                    if (e == null) {
                        Bitmap bmp = BitmapFactory.decodeByteArray(data, 0, data.length);
                        img.setImageBitmap(bmp);
                    }
                }
            });
        } else {
            if(user.getKeyIsProfessional()){

                img.setImageResource(R.drawable.professional_img);
            }
            else {
                img.setImageResource(R.drawable.default_img);
            }
        }
    }// load image

}
