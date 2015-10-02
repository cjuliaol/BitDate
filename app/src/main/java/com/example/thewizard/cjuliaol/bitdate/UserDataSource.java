package com.example.thewizard.cjuliaol.bitdate;

import android.util.Log;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cjuliaol on 11-Sep-15.
 */
public class UserDataSource {
    private static final String COLUMN_ID = "objectId";
    private static User sCurrentUser;
    private static final String TAG = "UserDataSourceLog";

    public static User getCurrentUser() {
        if (sCurrentUser == null && ParseUser.getCurrentUser() != null) {
            ParseUser parseUser = ParseUser.getCurrentUser();
            sCurrentUser = ParseUserToUser(parseUser);

        }
        return sCurrentUser;
    }

    public static void getUnseenUsers(final UserDataCallback callback) {
       ParseQuery<ParseObject> seenUsersQuery = new ParseQuery<ParseObject>(ActionDataSource.TABLE_NAME);
        seenUsersQuery.whereEqualTo(ActionDataSource.COLUMN_BY_USER, ParseUser.getCurrentUser().getObjectId());
        seenUsersQuery.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> list, ParseException e) {
                if (e == null && list.size()>0) {
                     List<String> ids = new ArrayList<String>();
                    for (ParseObject parseObject:list) {
                        ids.add(ActionDataSource.COLUMN_TO_USER);
                    }
                    ParseQuery<ParseUser> usersQuery = ParseUser.getQuery();
                    usersQuery.whereNotEqualTo("objectId", getCurrentUser().getId());
                    usersQuery.whereNotContainedIn("objectId",ids);

                    usersQuery.findInBackground(new FindCallback<ParseUser>() {
                        @Override
                        public void done(List<ParseUser> list, ParseException e) {
                            Log.d(ChoosingFragment.TAGFollowing,"UserDataSource: getUnseenUsers : findInBackground");
                            formatCallback(list, e, callback);

                        }
                    });

                }
            }
        });




    }

    private static void formatCallback(List<ParseUser> list, ParseException e, UserDataCallback callback) {
        if (list != null && e == null) {
            List<User> users = new ArrayList<User>();
            for (ParseUser parseUser : list) {
                User user = ParseUserToUser(parseUser);
                users.add(user);
                Log.d(TAG, user.getFirstName() + "- " + user.getLastName());
            }
            if (callback != null) {
                callback.onUserFetched(users);
                Log.d(ChoosingFragment.TAGFollowing, "UserDataSource: getUnseenUsers : findInBackground: callback.onUserFetched ");
            }
        }
    }

    public static void getUsersIn(List<String> ids, final UserDataCallback callback) {
        ParseQuery<ParseUser> query = ParseUser.getQuery();
        query.whereContainedIn(COLUMN_ID,ids);
        query.findInBackground(new FindCallback<ParseUser>() {
            @Override
            public void done(List<ParseUser> list, ParseException e) {
                formatCallback(list, e, callback);
            }
        });
    }

    private static User ParseUserToUser (ParseUser parseUser) {
        User user = new User();
        user.setFirstName(parseUser.getString("firstName"));
        user.setLastName(parseUser.getString("lastName"));
        user.setPictureUrl(parseUser.getString("picture"));
        user.setFacebookId(parseUser.getString("facebookId"));
        user.setId(parseUser.getObjectId());
     return  user;
    }


    public interface UserDataCallback {
      public  void onUserFetched(List<User> users);
    }

}
