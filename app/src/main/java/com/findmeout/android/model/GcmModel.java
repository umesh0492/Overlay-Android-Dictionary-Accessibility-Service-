package com.findmeout.android.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by umesh0492 on 23/07/16.
 */
public class GcmModel {

    @SerializedName ("type")
    @Expose
    short type;
    /**
     0--> Insert Category
     1--> Update Category
     2--> Delete Category

     3--> Insert Meaning
     4--> Update Meaning
     5--> Delete Meaning

     6--> Insert Word
     7--> Update Word
     8--> Delete Word

     9--> Casual Message (priority {image,text}), if url is there navigate
     **/


    @SerializedName ("category_id")
    @Expose
    String categoryId;

    @SerializedName ("category_name")
    @Expose
    String categoryName;

    @SerializedName ("meaning_id")
    @Expose
    String meaningId;

    @SerializedName ("meaning")
    @Expose
    String meaning;

    @SerializedName ("meaning_usage")
    @Expose
    String meaningUsage;

    @SerializedName ("word_id")
    @Expose
    String wordId;

    @SerializedName ("word")
    @Expose
    String word;

    @SerializedName ("phonetic")
    @Expose
    String phonetic;

    @SerializedName ("phonetic_sound")
    @Expose
    String phoneticSound;

    @SerializedName ("title")
    @Expose
    String title;

    @SerializedName ("message")
    @Expose
    String message;

    @SerializedName ("image_url")
    @Expose
    String imageUrl;

    @SerializedName ("url")
    @Expose
    String url;

    @SerializedName ("updated_on")
    @Expose
    String updatedOn;

    public String getUpdatedOn () {
        return updatedOn;
    }

    public void setUpdatedOn (String updatedOn) {
        this.updatedOn = updatedOn;
    }

    public String getUrl () {
        return url;
    }

    public void setUrl (String url) {
        this.url = url;
    }

    public String getCategoryId () {
        return categoryId;
    }

    public void setCategoryId (String categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName () {
        return categoryName;
    }

    public void setCategoryName (String categoryName) {
        this.categoryName = categoryName;
    }

    public String getImageUrl () {
        return imageUrl;
    }

    public void setImageUrl (String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getMeaningId () {
        return meaningId;
    }

    public void setMeaningId (String meaningId) {
        this.meaningId = meaningId;
    }

    public String getMeaning () {
        return meaning;
    }

    public void setMeaning (String meaning) {
        this.meaning = meaning;
    }

    public String getMeaningUsage () {
        return meaningUsage;
    }

    public void setMeaningUsage (String meaningUsage) {
        this.meaningUsage = meaningUsage;
    }

    public String getMessage () {
        return message;
    }

    public void setMessage (String message) {
        this.message = message;
    }

    public String getPhonetic () {
        return phonetic;
    }

    public void setPhonetic (String phonetic) {
        this.phonetic = phonetic;
    }

    public String getPhoneticSound () {
        return phoneticSound;
    }

    public void setPhoneticSound (String phoneticSound) {
        this.phoneticSound = phoneticSound;
    }

    public String getTitle () {
        return title;
    }

    public void setTitle (String title) {
        this.title = title;
    }

    public short getType () {
        return type;
    }

    public void setType (short type) {
        this.type = type;
    }

    public String getWord () {
        return word;
    }

    public void setWord (String word) {
        this.word = word;
    }

    public String getWordId () {
        return wordId;
    }

    public void setWordId (String wordId) {
        this.wordId = wordId;
    }

}
