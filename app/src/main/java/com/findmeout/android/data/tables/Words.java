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
public class Words extends BaseModel {
    @Column
    @PrimaryKey
    @JsonProperty ("id")
    int id;

    @Column
    @JsonProperty ("word")
    String word;


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

    public String getWord () {
        return word;
    }

    public void setWord (String word) {
        this.word = word;
    }


}
