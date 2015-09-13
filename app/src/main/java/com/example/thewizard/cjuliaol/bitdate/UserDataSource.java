package com.example.thewizard.cjuliaol.bitdate;

import com.parse.ParseUser;

/**
 * Created by cjuliaol on 11-Sep-15.
 */
public class UserDataSource {
    private static User sCurrentUser;

    public static User getCurrentUser() {
        if (sCurrentUser == null && ParseUser.getCurrentUser() != null) {
            ParseUser user = ParseUser.getCurrentUser();
            sCurrentUser = new User();
            sCurrentUser.setFirstName(user.getString("firstName"));
            sCurrentUser.setLastName(user.getString("lastName"));
            sCurrentUser.setPictureUrl(user.getString("pictureURL"));
        }
        return sCurrentUser;
    }
}
