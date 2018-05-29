package com.example.android.miwok;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class FamilyActivity extends AppCompatActivity {
    final ArrayList<Word> words = new ArrayList<>();
    private MediaPlayer mMediaPlayer;
    private MediaPlayer.OnCompletionListener mOnComplete;

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
        words.add(new Word("әpә","father", R.raw.family_father,R.drawable.family_father));
        words.add(new Word("әṭa","mother", R.raw.family_mother,R.drawable.family_mother));
        words.add(new Word("angsi","son", R.raw.family_son,R.drawable.family_son));
        words.add(new Word("tune","daughter", R.raw.family_daughter,R.drawable.family_daughter));
        words.add(new Word("taachi","older brother", R.raw.family_older_brother,R.drawable.family_older_brother));
        words.add(new Word("chalitti","younger brother", R.raw.family_younger_brother,R.drawable.family_younger_brother));
        words.add(new Word("teṭe","older sister", R.raw.family_older_sister,R.drawable.family_older_sister));
        words.add(new Word("kolliti","younger sister", R.raw.family_younger_sister,R.drawable.family_younger_sister));
        words.add(new Word("ama","grandmother", R.raw.family_grandmother,R.drawable.family_grandmother));
        words.add(new Word("paapa","grandfather", R.raw.family_grandfather,R.drawable.family_grandfather));

        WordAdapter wordAdapter = new WordAdapter(this, words, R.color.category_family);

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

                if(result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED){
                    mMediaPlayer = MediaPlayer.create(FamilyActivity.this,word.getAudioResourceID());
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
