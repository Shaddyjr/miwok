package com.example.android.miwok;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class WordAdapter extends ArrayAdapter<Word>{

    private int mColorResourceID;

    public WordAdapter(Activity context, ArrayList<Word> words, int colorResourceID) {
        super(context, 0, words);
        mColorResourceID = colorResourceID;
    }

    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        // Check if the existing view is being reused, otherwise inflate the view
        // this will be the view that get's modified and populated with information from object at
        // ArrayList position
        View listItemView = convertView;
        if(listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        }

        // setting background
        RelativeLayout itemView = (RelativeLayout) listItemView.findViewById(R.id.main_background);
        // getting color
        int color = ContextCompat.getColor(getContext(),mColorResourceID);
        itemView.setBackgroundColor(color);

        // after view recycled or inflated, securing object at position
        Word wordObj = getItem(position);

        // now, filling in view with information from the object
        TextView miwokWord = (TextView) listItemView.findViewById(R.id.miwok_word);
        miwokWord.setText(wordObj.getMiwokWord());

        TextView defaultWord = (TextView) listItemView.findViewById(R.id.default_word);
        defaultWord.setText(wordObj.getDefaultWord());

        ImageView imageView = (ImageView) listItemView.findViewById(R.id.image);
        if(wordObj.hasImage()){
            imageView.setImageResource(wordObj.getImageResourceID());
            imageView.setVisibility(View.VISIBLE);
        }else{
            imageView.setVisibility(View.GONE);
        }

        return listItemView;
    }
}
