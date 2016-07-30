package com.findmeout.android.data.provider.sql;

/**
 * Created by umesh0492 on 15/02/16.
 */
public final class SQLColumn {

    public boolean isAutoIncrement = false;
    private String columnName;
    private String columnType;
    private boolean primaryKey = false;

    public SQLColumn (String columnName, String columnType, boolean primaryKey) {
        this (columnName, columnType, primaryKey, false);
    }

    public SQLColumn (String columnName, String columnType, boolean primaryKey, boolean isAutoIncrement) {
        this.columnName = columnName;
        this.columnType = columnType;
        this.primaryKey = primaryKey;
        this.isAutoIncrement = isAutoIncrement;
    }

    public SQLColumn (String columnName, String columnType) {
        this (columnName, columnType, false, false);
    }

    public String getColumnName () {
        return columnName;
    }

    public String getColumnType () {
        return columnType;
    }

    public boolean isPrimaryKey () {
        return primaryKey;
    }
}
