package com.example.fixit;

import com.parse.ParseObject;

import org.parceler.Parcel;

//@Parcel
public class Professional extends ParseObject {

    //Required by Parcel
    public Professional(){};

    public static final String KEY_RATINGS = "ratings";
    public static final String KEY_PHONE = "phone";
    public static final String KEY_ZIPCODE = "zipcode";

    //----------------------Getters and Setters--------------------------------

    //ratings
    public Number getRatings(){
        return getNumber(String.valueOf(KEY_RATINGS));
    }
    public void setRatings(Number ratings){
        put(String.valueOf(KEY_RATINGS),ratings);
    }

    //phone
    public Number getPhone(){
        return getNumber(String.valueOf(KEY_PHONE));
    }
    public void setPhone(Number phone){
        put(String.valueOf(KEY_PHONE),phone);
    }

    //zipcode
    public Number getZipcode(){
        return getNumber(String.valueOf(KEY_ZIPCODE));
    }
    public void setZipcode(Number zipcode){
        put(String.valueOf(KEY_ZIPCODE),zipcode);
    }


}
