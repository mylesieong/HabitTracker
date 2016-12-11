package com.myles.udacity.habittracker.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.myles.udacity.habittracker.data.HabitTrackerContract.HabitEntry;

import org.xml.sax.HandlerBase;

/**
 * Created by asus on 11/12/2016.
 */

public class HabitTrackerDbHelper extends SQLiteOpenHelper {

    public final static int HABIT_TRACKER_DB_VERSION = 1;
    public final static String HABIT_TRACKER_DB_NAME = "habit_tracker.db";

    public HabitTrackerDbHelper(Context context) {
        super(context, null, null, HABIT_TRACKER_DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String createSQL = "CREATE TABLE " + HabitEntry.TABLE_NAME + "("
                + HabitEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + HabitEntry.COLUMN_ITEM_NAME + " TEXT NOT NULL,"
                + HabitEntry.COLUMN_FREQUENCY + " INTEGER,"
                + HabitEntry.COLUMN_STARTDATE + " INTEGER,"
                + HabitEntry.COLUMN_DURATION + " INTEGER"
                + ");";
        sqLiteDatabase.execSQL(createSQL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        String eraseSQL = "DROP TABLE IF EXISTS" + HabitEntry.TABLE_NAME + ";";
        sqLiteDatabase.execSQL(eraseSQL);
        this.onCreate(sqLiteDatabase);
    }
}
