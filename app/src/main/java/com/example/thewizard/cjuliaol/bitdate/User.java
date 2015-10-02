package com.example.thewizard.cjuliaol.bitdate;

import java.io.Serializable;

/**
 * Created by cjuliaol on 11-Sep-15.
 */
public class User implements Serializable{

    private String mFirstName;
    private String mLastName;
    private String mPictureUrl;
    private String mId;
    private String mFacebookId;

    public String getLargePicture() {
        return "https://graph.facebook.com/v2.3/"+mFacebookId+"/picture?type=large";
    }

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    User() {

    }

    public String getFacebookId() {
        return mFacebookId;
    }

    public void setFacebookId(String facebookId) {
        mFacebookId = facebookId;
    }

    public String getFirstName() {
        return mFirstName;
    }

    public void setFirstName(String firstName) {
        mFirstName = firstName;
    }

    public String getLastName() {
        return mLastName;
    }

    public void setLastName(String lastName) {
        mLastName = lastName;
    }

    public String getPictureUrl() {
        return mPictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        mPictureUrl = pictureUrl;
    }
}
