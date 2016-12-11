package com.myles.udacity.habittracker;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import com.myles.udacity.habittracker.data.HabitTrackerContract.HabitEntry;
import com.myles.udacity.habittracker.data.HabitTrackerDbHelper;

/**
 * This class contains the project requirement interface methods which are:
 *   1. void displayCount(HabitTrackerDbHelper, String) : display total count of a given table in default datatbase
 *   2. void insert(HabitTrackerDbHelper, String, ContentValues): insert data to a given table
 *   3. void update(HabitTrackerDbHelper, String, ContentValues, String, String[]): update data of a given table with selections
 *   4. void delete(HabitTrackerDbHelper, String, String, String[]) : delete data from a given table with selections
 *   5. Cursor read(HabitTrackerDbHelper, String, String[], String, String[], String): read data from a given table with selections and return cursor
 *   Some of above method output logs to better demonstrate the behavior.
 *
 *   While in the onCreate method of the main activity, there are demos actions of above methods.
 *   */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /* Database instances initialization and manipulation */
        HabitTrackerDbHelper habitTrackerDbHelper = new HabitTrackerDbHelper(this);

        //show total count of database
        this.displayCount(habitTrackerDbHelper, HabitEntry.TABLE_NAME);

        //insert new row of data #1
        ContentValues valuesInsert = new ContentValues();
        valuesInsert.put(HabitEntry.COLUMN_ITEM_NAME, "SWIMMING");
        valuesInsert.put(HabitEntry.COLUMN_FREQUENCY, 4);
        valuesInsert.put(HabitEntry.COLUMN_DURATION, 30);
        valuesInsert.put(HabitEntry.COLUMN_STARTDATE, 20160101);
        this.insert(habitTrackerDbHelper, HabitEntry.TABLE_NAME, valuesInsert);

        //insert new row of data #2
        valuesInsert.put(HabitEntry.COLUMN_ITEM_NAME, "JOGGING");
        valuesInsert.put(HabitEntry.COLUMN_FREQUENCY, 3);
        valuesInsert.put(HabitEntry.COLUMN_DURATION, 60);
        valuesInsert.put(HabitEntry.COLUMN_STARTDATE, 20160101);
        this.insert(habitTrackerDbHelper, HabitEntry.TABLE_NAME, valuesInsert);

        //insert new row of data #3
        valuesInsert.put(HabitEntry.COLUMN_ITEM_NAME, "READING");
        valuesInsert.put(HabitEntry.COLUMN_FREQUENCY, 7);
        valuesInsert.put(HabitEntry.COLUMN_DURATION, 60);
        valuesInsert.put(HabitEntry.COLUMN_STARTDATE, 20100101);
        this.insert(habitTrackerDbHelper, HabitEntry.TABLE_NAME, valuesInsert);

        //update existing data
        ContentValues valuesUpdate = new ContentValues();
        valuesUpdate.put(HabitEntry.COLUMN_ITEM_NAME, "POOL-SWIMMING");
        String selectionUpdate = HabitEntry.COLUMN_ITEM_NAME + " LIKE ?";
        String[] selectionArgsUpdate = {"SWIMMING"};
        this.update(habitTrackerDbHelper, HabitEntry.TABLE_NAME, valuesUpdate, selectionUpdate, selectionArgsUpdate);

        //read existing data
        String[] projectionRead = {HabitEntry._ID, HabitEntry.COLUMN_ITEM_NAME, HabitEntry.COLUMN_STARTDATE};
        String selectionRead = HabitEntry.COLUMN_DURATION + " = ?";
        String[] selectionArgsRead = {"60"};
        String sortOrderRead =HabitEntry.COLUMN_STARTDATE + " DESC";
        Cursor cursorRead = this.read(habitTrackerDbHelper, HabitEntry.TABLE_NAME, projectionRead, selectionRead, selectionArgsRead, sortOrderRead);

        //iterate all result from cursor
        try {
            if (cursorRead.getCount()>0) {
                cursorRead.moveToFirst();
                Log.v("MylesDebug", HabitEntry.COLUMN_ITEM_NAME + " | " + HabitEntry.COLUMN_STARTDATE);
                Log.v("MylesDebug", cursorRead.getString(cursorRead.getColumnIndex(HabitEntry.COLUMN_ITEM_NAME)) + " | "
                      + cursorRead.getString(cursorRead.getColumnIndex(HabitEntry.COLUMN_STARTDATE))
                );
                while (!cursorRead.isLast()) {
                    cursorRead.moveToNext();
                    Log.v("MylesDebug", cursorRead.getString(cursorRead.getColumnIndex(HabitEntry.COLUMN_ITEM_NAME)) + " | "
                            + cursorRead.getString(cursorRead.getColumnIndex(HabitEntry.COLUMN_STARTDATE))
                    );
                }
            }
        }finally {
            cursorRead.close();
        }

        // delete existing data
        String selectionDelete = HabitEntry.COLUMN_ITEM_NAME + " LIKE ?";
        String[] selectionArgsDelete = {"POOL-SWIMMING"};
        this.delete(habitTrackerDbHelper, HabitEntry.TABLE_NAME, selectionDelete, selectionArgsDelete);

        //delete the whole database
        habitTrackerDbHelper.deleteDatabase();
    }

    private void displayCount(HabitTrackerDbHelper habitTrackerDbHelper, String tableName) {
        SQLiteDatabase db = habitTrackerDbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + tableName, null);
        try {
            Log.v("MylesDebug", "Number of rows in pets database table: " + cursor.getCount());
        } finally {
            cursor.close();
        }
    }

    private void insert(HabitTrackerDbHelper habitTrackerDbHelper, String tableName, ContentValues values) {
        SQLiteDatabase db = habitTrackerDbHelper.getWritableDatabase();
        long newRowId = db.insert(tableName, null, values);
        Log.v("MylesDebug", "The ID of new row inserted:" + newRowId);
    }

    private void update(HabitTrackerDbHelper habitTrackerDbHelper, String tableName, ContentValues values, String selection, String[] selectionArgs) {
        SQLiteDatabase db = habitTrackerDbHelper.getReadableDatabase();
        int count = db.update(tableName, values, selection, selectionArgs);
        Log.v("MylesDebug", "The update result:" + count);
    }

    private void delete(HabitTrackerDbHelper habitTrackerDbHelper, String tableName, String selection, String[] selectionArgs) {
        SQLiteDatabase db = habitTrackerDbHelper.getWritableDatabase();
        db.delete(tableName, selection, selectionArgs);
    }

    private Cursor read(HabitTrackerDbHelper habitTrackerDbHelper, String tableName, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        SQLiteDatabase db = habitTrackerDbHelper.getReadableDatabase();
        Cursor c = db.query(tableName, projection, selection, selectionArgs, null, null, sortOrder);
        return c;

    }

}
