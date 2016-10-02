package com.findmeout.android.model;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.findmeout.android.data.tables.Categories;
import com.findmeout.android.data.tables.Meanings;
import com.findmeout.android.data.tables.Words;

import java.util.ArrayList;

/**
 * Created by umesh0492 on 19/07/16.
 */
public class DictionaryWordModel {

    @JsonProperty ("words")
    ArrayList<Words> words;
    @JsonProperty ("meanings")
    ArrayList<Meanings> meanings;
    @JsonProperty ("categories")
    ArrayList<Categories> categories;
    @JsonProperty ("isNext")
    boolean isNext;

    public boolean getIsNext () {
        return isNext;
    }

    public void setIsNext (boolean isNext) {
        this.isNext = isNext;
    }

    public ArrayList<Categories> getCategories () {
        return categories;
    }

    public void setCategories (ArrayList<Categories> categories) {
        this.categories = categories;
    }

    public ArrayList<Meanings> getMeanings () {
        return meanings;
    }

    public void setMeanings (ArrayList<Meanings> meanings) {
        this.meanings = meanings;
    }


    public ArrayList<Words> getWords () {
        return words;
    }

    public void setWords (ArrayList<Words> words) {
        this.words = words;
    }

}
