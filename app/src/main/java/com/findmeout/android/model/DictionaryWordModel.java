package com.findmeout.android.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by umesh0492 on 19/07/16.
 */
public class DictionaryWordModel {

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

    @SerializedName ("words")
    @Expose
    ArrayList<Word> words;

    @SerializedName ("next_id")
    @Expose
    String nextId;

    public String getNextId () {
        return nextId;
    }

    public void setNextId (String nextId) {
        this.nextId = nextId;
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
        String id;
        @SerializedName ("word")
        @Expose
        String word;
        @SerializedName ("meanings")
        @Expose
        ArrayList<String> meanings;
        @SerializedName ("word_type")
        @Expose
        String wordType;
        @SerializedName ("pronunciation")
        @Expose
        String pronunciation;
        @SerializedName ("pronunciation_sound")
        @Expose
        String pronunciationSound;
        @SerializedName ("updated_on")
        @Expose
        String updatedOn;

        public String getUpdatedOn () {
            return updatedOn;
        }

        public void setUpdatedOn (String updatedOn) {
            this.updatedOn = updatedOn;
        }

        public String getId () {
            return id;
        }

        public void setId (String id) {
            this.id = id;
        }

        public ArrayList<String> getMeanings () {
            return meanings;
        }

        public void setMeanings (ArrayList<String> meanings) {
            this.meanings = meanings;
        }

        public String getPronunciation () {
            return pronunciation;
        }

        public void setPronunciation (String pronunciation) {
            this.pronunciation = pronunciation;
        }

        public String getPronunciationSound () {
            return pronunciationSound;
        }

        public void setPronunciationSound (String pronunciationSound) {
            this.pronunciationSound = pronunciationSound;
        }

        public String getWord () {
            return word;
        }

        public void setWord (String word) {
            this.word = word;
        }

        public String getWordType () {
            return wordType;
        }

        public void setWordType (String wordType) {
            this.wordType = wordType;
        }
    }
}
