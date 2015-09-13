package com.example.thewizard.cjuliaol.bitdate;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class ChoosingFragment extends Fragment {

    public ChoosingFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_main, container, false);


        User user = new User();
        user.setFirstName("Veronica");

        List<User> users = new ArrayList<>();
        users.add(user);

        CardStackContainer cardStackContainer = (CardStackContainer) view.findViewById(R.id.card_stack);
        CardAdapter cardAdapter = new CardAdapter(getActivity(),users);
        cardStackContainer.setCardAdapter(cardAdapter);

        return view;
    }
}
