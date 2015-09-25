package com.example.thewizard.cjuliaol.bitdate;

import android.support.annotation.NonNull;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.List;

/**
 * Created by cjuliaol on 23-Sep-15.
 */
public class ActionDataSource {
    public static final String TABLE_NAME = "Action";
    public static final String COLUMN_BY_USER = "byUser";
    public static final String COLUMN_TO_USER = "toUser";
    public static final String COLUMN_TYPE = "type";

    public static final String TYPE_LIKED = "liked";
    public static final String TYPE_MATCHED = "matched";
    public static final String TYPE_SKIPPED = "skipped";


    public static void saveLikedUser(final User user) {
        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>(TABLE_NAME);
        query.whereEqualTo(COLUMN_TO_USER, ParseUser.getCurrentUser().getObjectId());
        query.whereEqualTo(COLUMN_BY_USER, user.getId());
        query.whereEqualTo(COLUMN_TYPE, TYPE_LIKED);
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> list, ParseException e) {
                ParseObject action = null;
                if (e == null && list.size() > 0) {
                    ParseObject otherAction = list.get(0);
                    otherAction.put(COLUMN_TYPE, TYPE_MATCHED);
                    otherAction.saveInBackground();
                    action = createAction(user, TYPE_MATCHED);
                } else {
                    action = createAction(user, TYPE_MATCHED);
                }
                action.saveInBackground();
            }
        });


    }

    public static void saveSkippedUser(User user) {
        ParseObject action = createAction(user, TYPE_SKIPPED);
        action.saveInBackground();
    }

    @NonNull
    private static ParseObject createAction(User user, String type) {
        ParseObject action = new ParseObject(TABLE_NAME);
        action.put(COLUMN_BY_USER, ParseUser.getCurrentUser().getObjectId());
        action.put(COLUMN_TO_USER, user.getId());
        action.put(COLUMN_TYPE, type);
        return action;
    }
}
