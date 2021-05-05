package com.example.fixit;

import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;

import org.parceler.Parcel;

@ParseClassName("Post")
//@Parcel
public class Post extends ParseObject {

    //Parceler requirement
    public Post(){

    }
    public static final String KEY_QUESTION = "question";
    public static final String KEY_IMAGE = "image";
    public static final String KEY_AUTHOR = "author";
    public static final String KEY_CATEGORY = "category";
    public static final String KEY_CREATED_AT = "createdAt";

    public static final String KEY_LIKES_COUNT = "likesCount";
    public static final String KEY_COMMENTS_COUNT = "commentsCount";

    public static final String KEY_SOLVED = "solved";



    //----------------------Getters and Setters--------------------------------

    //Question
    public String getQuestion(){
        return getString(KEY_QUESTION);
    }
    public void setQuestion(String question) { put(KEY_QUESTION, question); }

    //Image
    public ParseFile getImage(){ return getParseFile(KEY_IMAGE);}
    public void setImage(ParseFile parseFile){ put(KEY_IMAGE, parseFile);}

    //author
    public ParseUser getAuthor(){
        return getParseUser(KEY_AUTHOR);
    }
    public void setAuthor(ParseUser author) { put(KEY_AUTHOR, author); }

    //category
    public String getCategory(){
        return getString(KEY_CATEGORY);
    }
    public void setKeyCategory(String category) {put(KEY_CATEGORY, category); }

    //likes count
    public Number getLikesCount(){
        return getNumber(String.valueOf(KEY_LIKES_COUNT));
    }
    public void setLikesCount(Number likesCount){
        put(String.valueOf(KEY_LIKES_COUNT), likesCount);
    }

    //Comments count
    public Number getCommentsCount(){
        return getNumber(String.valueOf(KEY_COMMENTS_COUNT));
    }
    public void setCommentsCount(Number commentsCount){
        put(String.valueOf(KEY_COMMENTS_COUNT), commentsCount);
    }

    //solved
    public Boolean getSolved(){
        return getBoolean(String.valueOf(KEY_SOLVED));
    }
    public void setSolved(Boolean solved){
        put(String.valueOf(KEY_SOLVED), solved);
    }



}
