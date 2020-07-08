package com.dcab.bugfinder.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelperBugs extends SQLiteOpenHelper
{
    private static final int DATABASE_VERSION = 1;
    private final Context context;


    static final String[] columns = {
            SchemaDBBugs.Tavola._ID,
            SchemaDBBugs.Tavola.COLUMN_IMAGE,
            SchemaDBBugs.Tavola.COLUMN_TYPE,
            SchemaDBBugs.Tavola.COLUMN_LOCATION,
            SchemaDBBugs.Tavola.COLUMN_DATE,
            SchemaDBBugs.Tavola.COLUMN_NOTE
    };


    private static final String CREATE_CMD =
                    "CREATE TABLE "+SchemaDBBugs.Tavola.TABLE_NAME+" ("
                    + SchemaDBBugs.Tavola._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + SchemaDBBugs.Tavola.COLUMN_IMAGE + " BLOB NOT NULL, "
                    + SchemaDBBugs.Tavola.COLUMN_TYPE+ " TEXT NOT NULL,"
                    + SchemaDBBugs.Tavola.COLUMN_LOCATION+ " TEXT NOT NULL, "
                    + SchemaDBBugs.Tavola.COLUMN_DATE + " TEXT NOT NULL, "
                    + SchemaDBBugs.Tavola.COLUMN_NOTE + " TEXT NOT NULL);";


    public DatabaseHelperBugs(Context context)
    {
        super(context, SchemaDBBugs.Tavola.TABLE_NAME, null, DATABASE_VERSION);
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
        Log.d("DEBUG","Deleting database "+ SchemaDB.Tavola.TABLE_NAME);
        context.deleteDatabase(SchemaDB.Tavola.TABLE_NAME);
    }
}
