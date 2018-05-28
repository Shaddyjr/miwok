package com.example.android.miwok;

import android.content.res.Resources;
import android.media.MediaPlayer;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class NumbersActivity extends AppCompatActivity {

    private MediaPlayer mMediaPlayer;
    private MediaPlayer.OnCompletionListener mOnComplete;
    final ArrayList<Word> words = new ArrayList<Word>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.word_list);


        mOnComplete = new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                releaseMediaPlayer();
            }
        };

        words.add(new Word("lutte","one", R.raw.number_one,R.drawable.number_one));
        words.add(new Word("otiiko","two", R.raw.number_two,R.drawable.number_two));
        words.add(new Word("tolookosu","three", R.raw.number_three,R.drawable.number_three));
        words.add(new Word("oyyisa","four", R.raw.number_four,R.drawable.number_four));
        words.add(new Word("massokka","five", R.raw.number_five,R.drawable.number_five));
        words.add(new Word("temmokka","six", R.raw.number_six,R.drawable.number_six));
        words.add(new Word("kenekaku","seven", R.raw.number_seven,R.drawable.number_seven));
        words.add(new Word("kawinta","eight", R.raw.number_eight,R.drawable.number_eight));
        words.add(new Word("wo'e","nine", R.raw.number_nine,R.drawable.number_nine));
        words.add(new Word("na'aacha","ten", R.raw.number_ten,R.drawable.number_ten));

        WordAdapter wordAdapter = new WordAdapter(this, words, R.color.category_numbers);

        ListView listView = (ListView) findViewById(R.id.list);

        listView.setAdapter(wordAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Word word  = words.get(position);
                releaseMediaPlayer();
                mMediaPlayer = MediaPlayer.create(NumbersActivity.this,word.getAudioResourceID());
                mMediaPlayer.start();
                mMediaPlayer.setOnCompletionListener(mOnComplete);
            }
        });
    }

    /**
     * Clean up the media player by releasing its resources.
     */
    private void releaseMediaPlayer() {
        // If the media player is not null, then it may be currently playing a sound.
        if (mMediaPlayer != null) {
            // Regardless of the current state of the media player, release its resources
            // because we no longer need it.
            mMediaPlayer.release();

            // Set the media player back to null. For our code, we've decided that
            // setting the media player to null is an easy way to tell that the media player
            // is not configured to play an audio file at the moment.
            mMediaPlayer = null;
        }
    }
}
