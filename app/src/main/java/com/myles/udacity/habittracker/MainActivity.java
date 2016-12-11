package com.myles.udacity.habittracker;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.myles.udacity.habittracker.data.HabitTrackerContract;
import com.myles.udacity.habittracker.data.HabitTrackerContract.HabitEntry;
import com.myles.udacity.habittracker.data.HabitTrackerDbHelper;

import org.xml.sax.HandlerBase;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /* Database instances initialization and manipulation */
        HabitTrackerDbHelper habitTrackerDbHelper = new HabitTrackerDbHelper(this);

        this.displayCount(habitTrackerDbHelper);                  //show total count of database
        this.insert(habitTrackerDbHelper);                  //insert new row of data
        this.insert(habitTrackerDbHelper);                  //insert new row of data
        this.insert(habitTrackerDbHelper);                  //insert new row of data
        this.update(habitTrackerDbHelper);     //update existing data
        //this.delete(habitTrackerDbHelper);              //delete existing data
        this.displayCount(habitTrackerDbHelper);                  //show total count of database
        //this.read(habitTrackerDbHelper);                //read existing data
    }

    private void displayCount(HabitTrackerDbHelper habitTrackerDbHelper) {
        SQLiteDatabase db = habitTrackerDbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + HabitEntry.TABLE_NAME, null);
        try {
            Log.v("MylesDebug", "Number of rows in pets database table: " + cursor.getCount());
        } finally {
            cursor.close();
        }
    }

    private void insert(HabitTrackerDbHelper habitTrackerDbHelper) {
        SQLiteDatabase db = habitTrackerDbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(HabitEntry.COLUMN_ITEM_NAME, "SWIMMING");
        values.put(HabitEntry.COLUMN_FREQUENCY, 4);
        values.put(HabitEntry.COLUMN_DURATION, 30);
        values.put(HabitEntry.COLUMN_STARTDATE, 20160101);

        long newRowId = db.insert(HabitEntry.TABLE_NAME, null, values);
        Log.v("MylesDebug", "The ID of new row inserted:"+ newRowId);
    }

    private void update(HabitTrackerDbHelper habitTrackerDbHelper) {
        SQLiteDatabase db = habitTrackerDbHelper.getReadableDatabase();

        ContentValues values = new ContentValues();
        values.put(HabitEntry.COLUMN_ITEM_NAME, "POOL-SWIMMING");

        String selection = HabitEntry.COLUMN_ITEM_NAME + " LIKE ?";
        String[] selectionArgs = { "SWIMMING" };

        int count = db.update(
                HabitEntry.TABLE_NAME,
                values,
                selection,
                selectionArgs);

        Log.v("MylesDebug", "The update result:"+count);
    }

    private void delete(HabitTrackerDbHelper habitTrackerDbHelper) {
        SQLiteDatabase db = habitTrackerDbHelper.getWritableDatabase();
        String selection = HabitEntry.COLUMN_ITEM_NAME + " LIKE ?";
        String[] selectionArgs = { "SWIMMING" };
        db.delete(HabitEntry.TABLE_NAME, selection, selectionArgs);
    }
    private void read(HabitTrackerDbHelper habitTrackerDbHelper) {
    }

}
