package com.myles.udacity.habittracker.data;

import android.provider.BaseColumns;

/**
 * Created by asus on 11/12/2016.
 */

public final class HabitTrackerContract {

    public final static class HabitEntry implements BaseColumns {

        public static final String TABLE_NAME = "habit";

        public static final String _ID = BaseColumns._ID;
        public static final String COLUMN_ITEM_NAME = "hbnm";
        public static final String COLUMN_FREQUENCY = "hbfrq";
        public static final String COLUMN_STARTDATE = "hbstrdte";
        public static final String COLUMN_DURATION = "hbdur";
    }
}
