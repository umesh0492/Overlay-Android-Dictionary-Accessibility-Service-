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
        statements.add (getCreateDictionaryMeaningTableDDL ());
        statements.add (getCreateDictionaryMeaningCategoryTableDDL ());

        return statements;
    }


    private static String getCreateAppTableDDL () {
        SQLHelper sqlHelper = new SQLiteHelper ();

        List<SQLColumn> columns = new ArrayList<> ();
        columns.add (new SQLColumn (DataContract.AppList.COLUMN_NAME_APP_PACKAGE,
                sqlHelper.getSQLTypeString ()));
        columns.add (new SQLColumn (DataContract.AppList.COLUMN_NAME_IS_ACTIVE,
                sqlHelper.getSQLTypeString ()));
        columns.add (new SQLColumn (DataContract.AppList.COLUMN_NAME_CREATED_ON,
                sqlHelper.getSQLTypeString ()));
        columns.add (new SQLColumn (DataContract.AppList.COLUMN_NAME_UPDATED_ON,
                sqlHelper.getSQLTypeString ()));

        return sqlHelper.getTableCreateDDL (DataContract.AppList.TABLE_NAME, columns);
    }

    private static String getCreateDictionaryTableDDL () {
        SQLHelper sqlHelper = new SQLiteHelper ();

        List<SQLColumn> columns = new ArrayList<> ();
        columns.add (new SQLColumn (DataContract.DictionaryWords._ID,
                sqlHelper.getSQLTypeInteger (), true));
        columns.add (new SQLColumn (DataContract.DictionaryWords.COLUMN_NAME_WORD,
                sqlHelper.getSQLTypeString ()));
        columns.add (new SQLColumn (DataContract.DictionaryWords.COLUMN_NAME_PHONETIC,
                sqlHelper.getSQLTypeString ()));
        columns.add (new SQLColumn (DataContract.DictionaryWords.COLUMN_NAME_PHONETIC_SOUND,
                sqlHelper.getSQLTypeString ()));
        columns.add (new SQLColumn (DataContract.DictionaryWords.COLUMN_NAME_CREATED_ON,
                sqlHelper.getSQLTypeString ()));
        columns.add (new SQLColumn (DataContract.DictionaryWords.COLUMN_NAME_UPDATED_ON,
                sqlHelper.getSQLTypeString ()));

        return sqlHelper.getTableCreateDDL (DataContract.DictionaryWords.TABLE_NAME, columns);
    }

    private static String getCreateDictionaryMeaningTableDDL () {
        SQLHelper sqlHelper = new SQLiteHelper ();

        List<SQLColumn> columns = new ArrayList<> ();
        columns.add (new SQLColumn (DataContract.DictionaryMeanings._ID,
                sqlHelper.getSQLTypeInteger (), true));
        columns.add (new SQLColumn (DataContract.DictionaryMeanings.COLUMN_NAME_MEANING,
                sqlHelper.getSQLTypeString ()));
        columns.add (new SQLColumn (DataContract.DictionaryMeanings.COLUMN_NAME_MEANING_USAGE,
                sqlHelper.getSQLTypeString ()));
        columns.add (new SQLColumn (DataContract.DictionaryMeanings.COLUMN_NAME_WORD_ID,
                sqlHelper.getSQLTypeString ()));
        columns.add (new SQLColumn (DataContract.DictionaryMeanings.COLUMN_NAME_CATEGORY_ID,
                sqlHelper.getSQLTypeString ()));
        columns.add (new SQLColumn (DataContract.DictionaryMeanings.COLUMN_NAME_CREATED_ON,
                sqlHelper.getSQLTypeString ()));
        columns.add (new SQLColumn (DataContract.DictionaryMeanings.COLUMN_NAME_UPDATED_ON,
                sqlHelper.getSQLTypeString ()));

        return sqlHelper.getTableCreateDDL (DataContract.DictionaryMeanings.TABLE_NAME, columns);
    }

    private static String getCreateDictionaryMeaningCategoryTableDDL () {
        SQLHelper sqlHelper = new SQLiteHelper ();

        List<SQLColumn> columns = new ArrayList<> ();
        columns.add (new SQLColumn (DataContract.DictionaryMeaningCategories._ID,
                sqlHelper.getSQLTypeInteger (), true));
        columns.add (new SQLColumn (DataContract.DictionaryMeaningCategories.COLUMN_NAME_CATEGORY_NAME,
                sqlHelper.getSQLTypeString ()));
        columns.add (new SQLColumn (DataContract.DictionaryMeaningCategories.COLUMN_NAME_CREATED_ON,
                sqlHelper.getSQLTypeString ()));
        columns.add (new SQLColumn (DataContract.DictionaryMeaningCategories.COLUMN_NAME_UPDATED_ON,
                sqlHelper.getSQLTypeString ()));

        return sqlHelper.getTableCreateDDL (DataContract.DictionaryMeaningCategories.TABLE_NAME, columns);
    }

}
