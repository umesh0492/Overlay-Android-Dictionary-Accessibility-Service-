package com.findmeout.android.data.provider;

import com.findmeout.android.data.provider.sql.SQLColumn;
import com.findmeout.android.data.provider.sql.SQLHelper;
import com.findmeout.android.data.provider.sql.SQLiteHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by umesh0492 on 15/02/16.
 */


public final class SQLDDL {

    private SQLDDL () {
    }

    public static List<String> getSQLTableCreateStatements () {
        List<String> statements = new ArrayList<> ();
        statements.add (getCreateAppTableDDL ());
        statements.add (getCreateDictionaryTableDDL ());

        return statements;
    }


    private static String getCreateAppTableDDL () {
        SQLHelper sqlHelper = new SQLiteHelper ();

        List<SQLColumn> columns = new ArrayList<> ();
        columns.add (new SQLColumn (DataContract.AppList.COLUMN_NAME_APP_PACKAGE,
                sqlHelper.getSQLTypeString ()));
        columns.add (new SQLColumn (DataContract.AppList.COLUMN_NAME_IS_ACTIVE,
                sqlHelper.getSQLTypeString ()));
        columns.add (new SQLColumn (DataContract.AppList.COLUMN_NAME_UPDATED_ON,
                sqlHelper.getSQLTypeString ()));

        return sqlHelper.getTableCreateDDL (DataContract.AppList.TABLE_NAME, columns);
    }

    private static String getCreateDictionaryTableDDL () {
        SQLHelper sqlHelper = new SQLiteHelper ();

        List<SQLColumn> columns = new ArrayList<> ();
        columns.add (new SQLColumn (DataContract.Dictionary._ID,
                sqlHelper.getSQLTypeInteger (), true));
        columns.add (new SQLColumn (DataContract.Dictionary.COLUMN_NAME_WORD,
                sqlHelper.getSQLTypeString ()));
        columns.add (new SQLColumn (DataContract.Dictionary.COLUMN_NAME_MEANINGS,
                sqlHelper.getSQLTypeString ()));
        columns.add (new SQLColumn (DataContract.Dictionary.COLUMN_NAME_PRONUNCIATION,
                sqlHelper.getSQLTypeString ()));
        columns.add (new SQLColumn (DataContract.Dictionary.COLUMN_NAME_PRONUNCIATION_SOUND,
                sqlHelper.getSQLTypeString ()));
        columns.add (new SQLColumn (DataContract.Dictionary.COLUMN_NAME_UPDATED_ON,
                sqlHelper.getSQLTypeString ()));

        return sqlHelper.getTableCreateDDL (DataContract.Dictionary.TABLE_NAME, columns);
    }


}
