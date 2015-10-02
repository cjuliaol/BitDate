package com.example.thewizard.cjuliaol.bitdate;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class MatchesFragment extends Fragment
        implements ActionDataSource.actionDataCallbacks, UserDataSource.UserDataCallback, AdapterView.OnItemClickListener

{

    private static final String TAG = "MatchesFragmentLog";
    private ArrayList<User> mUsers;
    private MatchesAdapter mAdapter;

    public MatchesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ActionDataSource.getMatches(this);


        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_matches, container, false);
        ListView listView = (ListView) view.findViewById(R.id.matches_list);

        mUsers = new ArrayList<>();
        mAdapter = new MatchesAdapter(mUsers);
        listView.setAdapter(mAdapter);
        listView.setOnItemClickListener(this);


        return view;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
     User user = (User)   mUsers.get(position);
        Intent intent = new Intent(getActivity(),ChatActivity.class);
        intent.putExtra(ChatActivity.USER_EXTRA,user);
        startActivity(intent);
    }

    @Override
    public void onFetchedMatches(List<String> matchesIds) {
        UserDataSource.getUsersIn(matchesIds, this);
    }

    @Override
    public void onUserFetched(List<User> users) {
       mUsers.clear();
        mUsers.addAll(users);
        mAdapter.notifyDataSetChanged();
    }

    public class MatchesAdapter extends ArrayAdapter<User> {
        MatchesAdapter(List<User> users){
            super(MatchesFragment.this.getActivity(),android.R.layout.simple_list_item_1,users);

        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            TextView v = (TextView) super.getView(position, convertView, parent);
             v.setText(getItem(position).getFirstName());
            return v;
        }
    }

}
