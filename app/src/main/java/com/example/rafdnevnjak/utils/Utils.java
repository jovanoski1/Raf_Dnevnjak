package com.example.rafdnevnjak.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.rafdnevnjak.db.DataBaseHelper;

public class Utils {
    public static void addObligation(String startTime, String endTime, String title, String description, int priority,int userId, Context context) {
        DataBaseHelper dbHelper = new DataBaseHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("start_time", startTime); // Use a string in ISO8601 format
        values.put("end_time", endTime);
        values.put("title", title);
        values.put("description", description);
        values.put("userId", userId);
        values.put("priority", priority);

        long result = db.insert("obligations", null, values);
        db.close();

        if (result == -1) {
            Log.d("Database", "Error inserting obligation");
        } else {
            Log.d("Database", "Obligation inserted successfully");
        }
    }
}
