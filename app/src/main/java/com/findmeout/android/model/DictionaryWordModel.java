package com.findmeout.android.model;


import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

/**
 * Created by umesh0492 on 19/07/16.
 */
public class DictionaryWordModel {

    @JsonProperty ("words")
    ArrayList<Word> words;
    @JsonProperty ("meanings")
    ArrayList<Meaning> meanings;
    @JsonProperty ("categories")
    ArrayList<Category> categories;
    @JsonProperty ("isNext")
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
        @JsonProperty ("id")

        String wordId;
        @JsonProperty ("word")

        String word;
       @JsonProperty ("phonetic")

        String phonetic;
       @JsonProperty ("phonetic_sound")

        String phoneticSound;
       @JsonProperty ("createdAt")

        String createdOn;
       @JsonProperty ("updatedAt")

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
       @JsonProperty ("id")

        String categoryID;
       @JsonProperty ("category_name")

        String categoryName;
       @JsonProperty ("createdAt")

        String createdOn;
       @JsonProperty ("updatedAt")

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
       @JsonProperty ("word_id")
        String wordId;
       @JsonProperty ("id")

        String meaningId;
       @JsonProperty ("meaning")

        String meaning;
       @JsonProperty ("meaning_usage")

        String meaningUsage;
       @JsonProperty ("category_id")

        String categoryId;
       @JsonProperty ("createdAt")

        String createdOn;
       @JsonProperty ("updatedAt")

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
