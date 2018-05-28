package com.example.android.miwok;

/**
 * {@link Word} Represents a vocabulary word.
 * Contains default and Miwok translation.
 */
public class Word {
    private static final int NO_IMAGE = -1;
    private String mMiwokWord;
    private String mDefaultWord;
    private int mImageResourceID = NO_IMAGE;

    /**
     * Create a new Word object.
     *
     * @param mMiwokWord the Miwok word
     * @param mDefaultWord the default word
     */
    Word(String mMiwokWord, String mDefaultWord){
        this.mMiwokWord     = mMiwokWord;
        this.mDefaultWord   = mDefaultWord;
    }

    /**
     * Create a new Word object.
     *
     * @param mMiwokWord the Miwok word
     * @param mDefaultWord the default word
     * @param mImageResourceID the image resource ID
     */
    Word(String mMiwokWord, String mDefaultWord, int mImageResourceID){
        this.mMiwokWord       = mMiwokWord;
        this.mDefaultWord     = mDefaultWord;
        this.mImageResourceID = mImageResourceID;
    }

    /**
     * Get default translation.
     */
    public String getMiwokWord(){
        return mMiwokWord;
    }

    /**
     * Get miwok translation.
     */
    public String getDefaultWord(){
        return mDefaultWord;
    }

    /**
     * Get the image's resource ID.
     */
    public int getImageResourceID(){
        return mImageResourceID;
    }

    /**
     * True if word has an image, false otherwise.
     * @return boolean
     */
    public boolean hasImage(){
        return mImageResourceID != NO_IMAGE;
    }
}
