package com.example.thewizard.cjuliaol.bitdate;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by cjuliaol on 12-Sep-15.
 */
public class CardAdapter extends ArrayAdapter<User> {



    public CardAdapter(Context context, List<User> users) {
        super(context, R.layout.card, R.id.name, users);
    }

    @Override
    public CardView  getView(int position, View convertView, ViewGroup parent) {
        CardView v = (CardView) super.getView(position, convertView, parent);

        TextView nameView = (TextView) v.findViewById(R.id.name);
        nameView.setText(getItem(position).getFirstName());

        ImageView imageView = (ImageView) v.findViewById(R.id.profile_photo);

        Picasso.with(getContext()).load(  getItem(position).getPictureUrl()).into(imageView);



        return v;
    }
}
