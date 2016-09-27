package com.findmeout.android.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by umesh0492 on 23/07/16.
 */
public class GcmModel {

    @JsonProperty ("type")

    short type;
    /**
     * 0--> Insert Category
     * 1--> Update Category
     * 2--> Delete Category
     * <p>
     * 3--> Insert Meaning
     * 4--> Update Meaning
     * 5--> Delete Meaning
     * <p>
     * 6--> Insert Word
     * 7--> Update Word
     * 8--> Delete Word
     * <p>
     * 9--> Casual Message (priority {image,text}), if url is there navigate
     **/


    @JsonProperty ("category_id")

    String categoryId;

    @JsonProperty ("category_name")

    String categoryName;

    @JsonProperty ("meaning_id")

    String meaningId;

    @JsonProperty ("meaning")

    String meaning;

    @JsonProperty ("meaning_usage")

    String meaningUsage;

    @JsonProperty ("word_id")

    String wordId;

    @JsonProperty ("word")

    String word;

    @JsonProperty ("phonetic")

    String phonetic;

    @JsonProperty ("phonetic_sound")

    String phoneticSound;

    @JsonProperty ("title")

    String title;

    @JsonProperty ("message")

    String message;

    @JsonProperty ("image_url")

    String imageUrl;

    @JsonProperty ("url")

    String url;

    @JsonProperty ("updated_on")

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
