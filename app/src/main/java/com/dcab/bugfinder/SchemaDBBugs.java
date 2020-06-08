package com.dcab.bugfinder;

import android.provider.BaseColumns;

import java.sql.Date;

public class SchemaDBBugs
{

    public SchemaDBBugs(){}


    public static abstract class Tavola implements BaseColumns
    {
        public static final String TABLE_NAME = "report";
        public static final String COLUMN_ID = "_id";
        public static final String COLUMN_IMAGE = "image";
        public static final String COLUMN_TYPE = "specie";
        public static final String COLUMN_LOCATION = "localita";
        public static final String COLUMN_DATE = "data";
        public static final String COLUMN_NOTE = "note";
    }

}
