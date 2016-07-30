package com.findmeout.android.data.provider.sql;

/**
 * Created by umesh0492 on 15/02/16.
 */

import java.util.List;

public interface SQLHelper {

    String getTableCreateDDL (String tableName, List<SQLColumn> columns);

    String getTableDropDDL (String tableName);

    String getSQLTypeInteger ();

    String getSQLTypeString ();

    String getSQLTypeText ();


}
