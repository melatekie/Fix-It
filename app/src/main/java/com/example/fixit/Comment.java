package com.example.fixit;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseUser;

@ParseClassName("Comment")
//@Parcel
public class Comment extends ParseObject {

    //Parceler requirement
    public Comment() {

    }

    public static final String KEY_POSTID = "postId";
    public static final String KEY_USERID = "userId";
    public static final String KEY_COMMENT = "comment";

    public  ParseObject getPostId() {
        return getParseObject(KEY_POSTID);
    }
    public void setPostId(ParseObject postId) { put(KEY_POSTID, postId); }

    public  ParseUser getUserId() {
        return getParseUser(KEY_USERID);
    }
    public void setUserId(ParseUser userId) { put(KEY_USERID, userId); }


    public  String getComment() {
        return getString(KEY_COMMENT);
    }
    public void setComment(String comment) { put(KEY_COMMENT, comment); }


}

