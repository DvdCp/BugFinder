package com.dcab.bugfinder;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper
{

    private static final int DATABASE_VERSION = 1;
    private final Context context;


    static final String[] columns = {
            SchemaDB.Tavola._ID,
            SchemaDB.Tavola.COLUMN_NAME,
            SchemaDB.Tavola.COLUMN_SURNAME,
            SchemaDB.Tavola.COLUMN_EMAIL,
            SchemaDB.Tavola.COLUMN_USERNAME,
            SchemaDB.Tavola.COLUMN_PASSWORD
    };


    private static final String CREATE_CMD =
                    "CREATE TABLE "+SchemaDB.Tavola.TABLE_NAME+" ("
                    + SchemaDB.Tavola._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + SchemaDB.Tavola.COLUMN_NAME + " TEXT NOT NULL, "
                    + SchemaDB.Tavola.COLUMN_SURNAME + " TEXT NOT NULL,"
                    + SchemaDB.Tavola.COLUMN_EMAIL + " TEXT NOT NULL, "
                    + SchemaDB.Tavola.COLUMN_USERNAME + " TEXT NOT NULL, "
                    + SchemaDB.Tavola.COLUMN_PASSWORD + " TEXT NOT NULL);";


    public DatabaseHelper(Context context)
    {
        super(context, SchemaDB.Tavola.TABLE_NAME, null, DATABASE_VERSION);
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
