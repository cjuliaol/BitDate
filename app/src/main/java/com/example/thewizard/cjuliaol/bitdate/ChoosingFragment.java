package com.example.thewizard.cjuliaol.bitdate;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class ChoosingFragment extends Fragment {

    private CardStackContainer mCardStackContainer;

    public ChoosingFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_main, container, false);


        User user1 = new User();
        user1.setFirstName("Veronica");

        User user2 = new User();
        user2.setFirstName("Carla");


        User user3 = new User();
        user3.setFirstName("Peter");

        List<User> users = new ArrayList<>();
        users.add(user1);
        users.add(user2);
        users.add(user3);

        mCardStackContainer = (CardStackContainer) view.findViewById(R.id.card_stack);
        CardAdapter cardAdapter = new CardAdapter(getActivity(), users);
        mCardStackContainer.setCardAdapter(cardAdapter);

        Button nahButton = (Button) view.findViewById(R.id.nah_button);
        nahButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCardStackContainer.swipeLeft();
            }
        });

        Button yeahButton = (Button) view.findViewById(R.id.yeah_button);
        yeahButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCardStackContainer.swipeRight();
            }
        });

        return view;
    }
}
