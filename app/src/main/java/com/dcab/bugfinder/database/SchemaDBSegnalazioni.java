package com.dcab.bugfinder.database;

import android.provider.BaseColumns;

public class SchemaDBSegnalazioni
{

    public SchemaDBSegnalazioni(){}

    public static abstract class Tavola implements BaseColumns
    {
        public static final String TABLE_NAME = "segnalazioni";
        public static final String COLUMN_ID = "_id";
        public static final String COLUMN_IMAGE = "image";
        public static final String COLUMN_TYPE = "specie";
        public static final String COLUMN_LOCATION = "localita";
        public static final String COLUMN_DATE = "data";
        public static final String COLUMN_NOTE = "note";
        public static final String COLUMN_STATUS = "status";
        public static final String COLUMN_USER = "user";
    }
}
