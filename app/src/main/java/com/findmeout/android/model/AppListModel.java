package com.findmeout.android.model;

import android.graphics.drawable.Drawable;

/**
 * Created by umesh0492 on 19/07/16.
 */
public class AppListModel {

    String app_name;
    Drawable app_icon;
    boolean is_active;
    String package_name;
    String updated_on;

    public String getUpdated_on () {
        return updated_on;
    }

    public void setUpdated_on (String updated_on) {
        this.updated_on = updated_on;
    }

    public String getPackage_name () {
        return package_name;
    }

    public void setPackage_name (String package_name) {
        this.package_name = package_name;
    }

    public Drawable getApp_icon () {
        return app_icon;
    }

    public void setApp_icon (Drawable app_icon) {
        this.app_icon = app_icon;
    }

    public String getApp_name () {
        return app_name;
    }

    public void setApp_name (String app_name) {
        this.app_name = app_name;
    }

    public boolean getIs_active () {
        return is_active;
    }

    public void setIs_active (boolean is_active) {
        this.is_active = is_active;
    }
}
