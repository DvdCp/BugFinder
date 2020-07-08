package com.dcab.bugfinder.database;

import android.provider.BaseColumns;

public class SchemaDB
{

    public SchemaDB(){}

    public static abstract class Tavola implements BaseColumns
    {
        public static final String TABLE_NAME = "users";
        public static final String COLUMN_ID = "_id";
        public static final String COLUMN_NAME = "nome";
        public static final String COLUMN_SURNAME = "cognome";
        public static final String COLUMN_EMAIL = "email";
        public static final String COLUMN_USERNAME = "username";
        public static final String COLUMN_PASSWORD = "password";
    }
}
