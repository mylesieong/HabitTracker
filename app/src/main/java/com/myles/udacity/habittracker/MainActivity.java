package com.myles.udacity.habittracker;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import com.myles.udacity.habittracker.data.HabitTrackerContract.HabitEntry;
import com.myles.udacity.habittracker.data.HabitTrackerDbHelper;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /* Database instances initialization and manipulation */
        HabitTrackerDbHelper habitTrackerDbHelper = new HabitTrackerDbHelper(this);

        //show total count of database
        this.displayCount(habitTrackerDbHelper, HabitEntry.TABLE_NAME);

        //insert new row of data
        ContentValues valuesInsert = new ContentValues();
        valuesInsert.put(HabitEntry.COLUMN_ITEM_NAME, "SWIMMING");
        valuesInsert.put(HabitEntry.COLUMN_FREQUENCY, 4);
        valuesInsert.put(HabitEntry.COLUMN_DURATION, 30);
        valuesInsert.put(HabitEntry.COLUMN_STARTDATE, 20160101);
        this.insert(habitTrackerDbHelper, HabitEntry.TABLE_NAME, valuesInsert);

        //update existing data
        ContentValues valuesUpdate = new ContentValues();
        valuesUpdate.put(HabitEntry.COLUMN_ITEM_NAME, "POOL-SWIMMING");
        String selectionUpdate = HabitEntry.COLUMN_ITEM_NAME + " LIKE ?";
        String[] selectionArgsUpdate = {"SWIMMING"};
        this.update(habitTrackerDbHelper, HabitEntry.TABLE_NAME, valuesUpdate, selectionUpdate, selectionArgsUpdate);

        //read existing data
        String[] projectionRead = {HabitEntry._ID, HabitEntry.COLUMN_ITEM_NAME, HabitEntry.COLUMN_STARTDATE};
        String selectionRead = HabitEntry.COLUMN_ITEM_NAME + " = ?";
        String[] selectionArgsRead = {"POOL-SWIMMING"};
        String sortOrderRead =HabitEntry.COLUMN_STARTDATE + " DESC";
        Cursor cursorRead = this.read(habitTrackerDbHelper, HabitEntry.TABLE_NAME, projectionRead, selectionRead, selectionArgsRead, sortOrderRead);

        //iterate all result from cursor
        try {
            cursorRead.moveToFirst();
            Log.v("MylesDebug", "The first record _id:" + cursorRead.getLong(cursorRead.getColumnIndexOrThrow(HabitEntry._ID)));
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
