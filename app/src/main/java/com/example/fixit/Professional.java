package com.example.fixit;

import android.content.Intent;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseUser;

import org.parceler.Parcel;

@ParseClassName("Professional")
public class Professional extends ParseObject {

    public static final String KEY_USER = "user";
    public static final String KEY_TITLE = "title";
    public static final String KEY_COMPANY = "company";
    public static final String KEY_RATINGS = "ratings";
    public static final String KEY_PHONE = "phone";
    public static final String KEY_STREET = "street";
    public static final String KEY_UNIT = "unit";
    public static final String KEY_CITY = "city";
    public static final String KEY_STATE = "state";
    public static final String KEY_ZIPCODE = "zipcode";

    //Required by Parcel
    public Professional(){};

    //----------------------Getters and Setters--------------------------------

    //user
    public ParseUser getUser() {
        return getParseUser(KEY_USER);
    }
    public void setUser(ParseUser user) {
        put(KEY_USER, user);
    }

    //job title
    public String getTitle() {
        return getString(KEY_TITLE);
    }
    public void setTitle(String title) {
        put(KEY_TITLE, title);
    }

    //company name
    public String getCompany() {
        return getString(KEY_COMPANY);
    }
    public void setCompany(String company) {
        put(KEY_COMPANY, company);
    }

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

    //street name
    public String getStreet() {
        return getString(KEY_STREET);
    }
    public void setStreet(String street) {
        put(KEY_STREET, street);
    }

    //unit
    public String getUnit() {
        return getString(KEY_UNIT);
    }
    public void setUnit(String unit) {
        put(KEY_UNIT, unit);
    }

    //city
    public String getCity() {
        return getString(KEY_CITY);
    }
    public void setCity(String city) {
        put(KEY_CITY, city);
    }

    //state
    public String getState() {
        return getString(KEY_STATE);
    }
    public void setState(String state) {
        put(KEY_STATE, state);
    }

    //zipcode
    public Number getZipcode(){
        return getNumber(String.valueOf(KEY_ZIPCODE));
    }
    public void setZipcode(Number zipcode){
        put(String.valueOf(KEY_ZIPCODE),zipcode);
    }


}
