package com.example.thewizard.cjuliaol.bitdate;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import java.util.ArrayList;
import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class ChoosingFragment extends Fragment implements UserDataSource.UserDataCallback {
    public static final String TAGFollowing ="CheckingProgramProcess";
    private CardStackContainer mCardStackContainer;
    private List<User> mUsers;
    private CardAdapter mCardAdapter;


    public ChoosingFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_main, container, false);
        mCardStackContainer = (CardStackContainer) view.findViewById(R.id.card_stack);

        Log.d(TAGFollowing, "Choosing Fragment: UserDataSource.getUsers");
        UserDataSource.getUsers(this);

        mUsers = new ArrayList<>();
        mCardAdapter = new CardAdapter(getActivity(), mUsers);
        mCardStackContainer.setCardAdapter(mCardAdapter);


        ImageButton nahButton = (ImageButton) view.findViewById(R.id.nah_button);
        nahButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCardStackContainer.swipeLeft();
            }
        });

        ImageButton yeahButton = (ImageButton) view.findViewById(R.id.yeah_button);
        yeahButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCardStackContainer.swipeRight();
            }
        });

        return view;
    }

    @Override
    public void onUserFetched(List<User> users) {
        Log.d(TAGFollowing, "Choosing Fragment: onUserFetched");
        mUsers.addAll(users);
        mCardAdapter.notifyDataSetChanged();

    }
}
