package com.findmeout.android.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by umesh0492 on 19/07/16.
 */
public class DictionaryWordModel {

    @SerializedName ("words")
    @Expose
    ArrayList<Word> words;
    @SerializedName ("meanings")
    @Expose
    ArrayList<Meaning> meanings;
    @SerializedName ("categories")
    @Expose
    ArrayList<Category> categories;
    @SerializedName ("isNext")
    @Expose
    boolean isNext;

    public boolean getIsNext () {
        return isNext;
    }

    public void setIsNext (boolean isNext) {
        this.isNext = isNext;
    }

    public ArrayList<Category> getCategories () {
        return categories;
    }

    public void setCategories (ArrayList<Category> categories) {
        this.categories = categories;
    }

    public ArrayList<Meaning> getMeanings () {
        return meanings;
    }

    public void setMeanings (ArrayList<Meaning> meanings) {
        this.meanings = meanings;
    }


    public ArrayList<Word> getWords () {
        return words;
    }

    public void setWords (ArrayList<Word> words) {
        this.words = words;
    }


    public static class Word {
        @SerializedName ("id")
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
        @SerializedName ("createdAt")
        @Expose
        String createdOn;
        @SerializedName ("updatedAt")
        @Expose
        String updatedOn;

        public String getUpdatedOn () {
            return updatedOn;
        }

        public void setUpdatedOn (String updatedOn) {
            this.updatedOn = updatedOn;
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

    public static class Category {
        @SerializedName ("id")
        @Expose
        String categoryID;
        @SerializedName ("category_name")
        @Expose
        String categoryName;
        @SerializedName ("createdAt")
        @Expose
        String createdOn;
        @SerializedName ("updated_on")
        @Expose
        String updatedOn;

        public String getCategoryID () {
            return categoryID;
        }

        public void setCategoryID (String categoryID) {
            this.categoryID = categoryID;
        }

        public String getCategoryName () {
            return categoryName;
        }

        public void setCategoryName (String categoryName) {
            this.categoryName = categoryName;
        }

        public String getUpdatedOn () {
            return updatedOn;
        }

        public void setUpdatedOn (String updatedOn) {
            this.updatedOn = updatedOn;
        }
    }

    public static class Meaning {
        @SerializedName ("word_id")
        @Expose
        String wordId;
        @SerializedName ("id")
        @Expose
        String meaningId;
        @SerializedName ("meaning")
        @Expose
        String meaning;
        @SerializedName ("meaning_usage")
        @Expose
        String meaningUsage;
        @SerializedName ("category_id")
        @Expose
        String categoryId;
        @SerializedName ("createdAt")
        @Expose
        String createdOn;
        @SerializedName ("updated_on")
        @Expose
        String updatedOn;

        public String getUpdatedOn () {
            return updatedOn;
        }

        public void setUpdatedOn (String updatedOn) {
            this.updatedOn = updatedOn;
        }

        public String getCategoryId () {
            return categoryId;
        }

        public void setCategoryId (String categoryId) {
            this.categoryId = categoryId;
        }

        public String getMeaning () {
            return meaning;
        }

        public void setMeaning (String meaning) {
            this.meaning = meaning;
        }

        public String getMeaningId () {
            return meaningId;
        }

        public void setMeaningId (String meaningId) {
            this.meaningId = meaningId;
        }

        public String getMeaningUsage () {
            return meaningUsage;
        }

        public void setMeaningUsage (String meaningUsage) {
            this.meaningUsage = meaningUsage;
        }

        public String getWordId () {
            return wordId;
        }

        public void setWordId (String wordId) {
            this.wordId = wordId;
        }
    }
}


    /*

    {
        _id : "",
        word : "",
        meanings : {
            [
                {
                    cat_type_id : "",
                    meaning : "",
                    usage : ""
                },
                {
                    cat_type_id : "",
                    meaning : "",
                    usage : ""
                }
            ]

        },
        phonetic : "",
        audio : "",
        image : ["","",""],
        next_id : ""
    }

     */