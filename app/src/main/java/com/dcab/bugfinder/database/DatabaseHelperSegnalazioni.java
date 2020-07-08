package com.dcab.bugfinder.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelperSegnalazioni extends SQLiteOpenHelper
{

    private static final int DATABASE_VERSION = 1;
    private final Context context;


    static final String[] columns = {
            SchemaDBSegnalazioni.Tavola.COLUMN_ID,
            SchemaDBSegnalazioni.Tavola.COLUMN_IMAGE,
            SchemaDBSegnalazioni.Tavola.COLUMN_TYPE,
            SchemaDBSegnalazioni.Tavola.COLUMN_LOCATION,
            SchemaDBSegnalazioni.Tavola.COLUMN_DATE,
            SchemaDBSegnalazioni.Tavola.COLUMN_NOTE,
            SchemaDBSegnalazioni.Tavola.COLUMN_USER
    };


    private static final String CREATE_CMD =
                    "CREATE TABLE "+SchemaDBSegnalazioni.Tavola.TABLE_NAME+" ("
                    + SchemaDBSegnalazioni.Tavola._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + SchemaDBSegnalazioni.Tavola.COLUMN_IMAGE + " BITMAP NOT NULL, "
                    + SchemaDBSegnalazioni.Tavola.COLUMN_TYPE + " TEXT NOT NULL,"
                    + SchemaDBSegnalazioni.Tavola.COLUMN_DATE + " TEXT NOT NULL, "
                    + SchemaDBSegnalazioni.Tavola.COLUMN_NOTE + " TEXT NOT NULL, "
                    + SchemaDBSegnalazioni.Tavola.COLUMN_USER + " INT NOT NULL);";


    public DatabaseHelperSegnalazioni(Context context)
    {
        super(context, SchemaDBSegnalazioni.Tavola.TABLE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL(CREATE_CMD);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) { /*necessary override*/   }

    public void deleteDatabase()
    {
        Log.d("DEBUG","Deleting database "+ SchemaDBSegnalazioni.Tavola.TABLE_NAME);
        context.deleteDatabase(SchemaDBSegnalazioni.Tavola.TABLE_NAME);
    }
}
