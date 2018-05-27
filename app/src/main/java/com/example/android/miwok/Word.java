package com.example.android.miwok;

/**
 * {@link Word} Represents a vocabulary word.
 * Contains default and Miwok translation.
 */
public class Word {
    private String miwok_word;
    private String default_word;


    public Word(String miwok_word, String default_word){
        this.miwok_word     = miwok_word;
        this.default_word   = default_word;
    }

    /**
     * Get default translation.
     */
    public String getMiwok_word(){
        return miwok_word;
    }

    /**
     * Get miwok translation.
     */
    public String getDefault_word(){
        return default_word;
    }
}
