package com.example.thewizard.cjuliaol.bitdate;

/**
 * Created by cjuliaol on 11-Sep-15.
 */
public class User {

    private String mFirstName;
    private String mLastName;
    private String mPictureUrl;

    User() {

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
