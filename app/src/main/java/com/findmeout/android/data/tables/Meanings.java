package com.findmeout.android.data.tables;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.findmeout.android.data.MyDatabase;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

/**
 * Created by umesh0492 on 27/09/16.
 */

@Table (database = MyDatabase.class)
public class Meanings extends BaseModel {

    @Column
    @PrimaryKey
    @JsonProperty ("id")
    int id;

    @Column
    @JsonProperty ("meaning")
    String meaning;

    @Column
    @JsonProperty ("word_id")
    int wordId;

    @Column
    @JsonProperty ("meaning_usage")
    String meaningUsage;

    @Column
    @JsonProperty ("category_id")
    int categoryId;

    @Column
    @JsonProperty ("createdAt")
    String createdOn;

    @Column
    @JsonProperty ("updatedAt")
    String updatedOn;

    public String getUpdatedOn () {
        return updatedOn;
    }

    public void setUpdatedOn (String updatedOn) {
        this.updatedOn = updatedOn;
    }

    public int getCategoryId () {
        return categoryId;
    }

    public void setCategoryId (int categoryId) {
        this.categoryId = categoryId;
    }

    public String getCreatedOn () {
        return createdOn;
    }

    public void setCreatedOn (String createdOn) {
        this.createdOn = createdOn;
    }

    public int getId () {
        return id;
    }

    public void setId (int id) {
        this.id = id;
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

    public int getWordId () {
        return wordId;
    }

    public void setWordId (int wordId) {
        this.wordId = wordId;
    }

}
