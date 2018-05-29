package com.example.android.miwok;

import android.content.Context;
import android.content.res.Resources;
import android.media.AudioManager;
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


    // establishing the need for audio service
    private AudioManager mAudioManager;

    /**
     * This listener gets triggered whenever the audio focus changes
     * (i.e., we gain or lose audio focus because of another app or device).
     */
    private AudioManager.OnAudioFocusChangeListener mOnAudioFocusChangeListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.word_list);
        // Getting audio service referenced
        mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

        // defining callback of what to do on Audio Focus Change
        mOnAudioFocusChangeListener = new AudioManager.OnAudioFocusChangeListener() {
            @Override
            public void onAudioFocusChange(int focusChange) {
                if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT ||
                        focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK) {
                    // The AUDIOFOCUS_LOSS_TRANSIENT case means that we've lost audio focus for a
                    // short amount of time. The AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK case means that
                    // our app is allowed to continue playing sound but at a lower volume. We'll treat
                    // both cases the same way because our app is playing short sound files.

                    // Pause playback and reset player to the start of the file. That way, we can
                    // play the word from the beginning when we resume playback.
                    mMediaPlayer.pause();
                    mMediaPlayer.seekTo(0);
                } else if (focusChange == AudioManager.AUDIOFOCUS_GAIN) {
                    // The AUDIOFOCUS_GAIN case means we have regained focus and can resume playback.
                    mMediaPlayer.start();
                } else if (focusChange == AudioManager.AUDIOFOCUS_LOSS) {
                    // The AUDIOFOCUS_LOSS case means we've lost audio focus and
                    // Stop playback and clean up resources
                    releaseMediaPlayer();
                }
            }
        };

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
                // Request audio focus so in order to play the audio file. The app needs to play a
                // short audio file, so we will request audio focus with a short amount of time
                // with AUDIOFOCUS_GAIN_TRANSIENT.
                int result = mAudioManager.requestAudioFocus(mOnAudioFocusChangeListener,
                        AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);
                if(result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                    mMediaPlayer = MediaPlayer.create(NumbersActivity.this, word.getAudioResourceID());
                    mMediaPlayer.start();
                    mMediaPlayer.setOnCompletionListener(mOnComplete);
                }
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
            // Regardless of whether or not we were granted audio focus, abandon it. This also
            // unregisters the AudioFocusChangeListener so we don't get anymore callbacks.
            mAudioManager.abandonAudioFocus(mOnAudioFocusChangeListener);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        releaseMediaPlayer();
    }
}
