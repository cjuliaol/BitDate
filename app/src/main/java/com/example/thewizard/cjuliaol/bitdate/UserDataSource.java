package com.example.thewizard.cjuliaol.bitdate;

import android.util.Log;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cjuliaol on 11-Sep-15.
 */
public class UserDataSource {
    private static User sCurrentUser;
    private static final String TAG = "UserDataSourceLog";

    public static User getCurrentUser() {
        if (sCurrentUser == null && ParseUser.getCurrentUser() != null) {
            ParseUser parseUser = ParseUser.getCurrentUser();
            sCurrentUser = ParseUserToUser(parseUser);

        }
        return sCurrentUser;
    }

    public static void getUsers(final UserDataCallback callback) {
       final List<User> users = new ArrayList<User>();

        ParseQuery<ParseUser> usersQuery = ParseUser.getQuery();
        usersQuery.whereNotEqualTo("objectId", getCurrentUser().getId());

        usersQuery.findInBackground(new FindCallback<ParseUser>() {
            @Override
            public void done(List<ParseUser> list, ParseException e) {
                Log.d(ChoosingFragment.TAGFollowing,"UserDataSource: getUsers : findInBackground");
                if (list != null && e == null) {

                    for (ParseUser parseUser : list) {
                      User user = ParseUserToUser(parseUser);
                       users.add(user);
                        Log.d(TAG, user.getFirstName() + "- " + user.getLastName());
                    }
                    if (callback != null) {
                        callback.onUserFetched(users);
                        Log.d(ChoosingFragment.TAGFollowing, "UserDataSource: getUsers : findInBackground: callback.onUserFetched ");
                    }
                }

            }
        });


    }

    private static User ParseUserToUser (ParseUser parseUser) {
        User user = new User();
        user.setFirstName(parseUser.getString("firstName"));
        user.setLastName(parseUser.getString("lastName"));
        user.setPictureUrl(parseUser.getString("picture"));
        user.setId(parseUser.getObjectId());
     return  user;
    }

    public interface UserDataCallback {
      public  void onUserFetched(List<User> users);
    }

}
