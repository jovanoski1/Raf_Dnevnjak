package com.example.rafdnevnjak.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "rafdnevnjak.db";
    private static final int DATABASE_VERSION = 1;

    public DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_USER_TABLE = "CREATE TABLE "+ UserContract.UserEntry.TABLE_NAME+"( " +
                UserContract.UserEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                UserContract.UserEntry.COLUMN_EMAIL +" TEXT NOT NULL, " +
                UserContract.UserEntry.COLUMN_USERNAME + " TEXT NOT NULL, " +
                UserContract.UserEntry.COLUMN_PASSWORD + " TEXT NOT NULL)";

        sqLiteDatabase.execSQL(CREATE_USER_TABLE);


        String CREATE_OBLIGATION_TABLE = "CREATE TABLE " + ObligationContract.ObligationEntry.TABLE_NAME + "( " +
                ObligationContract.ObligationEntry._ID +" INTEGER PRIMARY KEY AUTOINCREMENT, " +
                ObligationContract.ObligationEntry.COLUMN_STARTTIME +" DATETIME NOT NULL, " +
                ObligationContract.ObligationEntry.COLUMN_ENDTIME +" DATETIME NOT NULL, " +
                ObligationContract.ObligationEntry.COLUMN_TITLE+ " TEXT NOT NULL, " +
                ObligationContract.ObligationEntry.COLUMN_DESC + " TEXT NOT NULL, " +
                ObligationContract.ObligationEntry.COLUMN_PRIORITY + " INTEGER NOT NULL, " +
                ObligationContract.ObligationEntry.COLUMN_USERID +  " INTEGER, " +
                "FOREIGN KEY (userId) REFERENCES users(id)" +
                ")";

        sqLiteDatabase.execSQL(CREATE_OBLIGATION_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+ UserContract.UserEntry.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+ ObligationContract.ObligationEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
