package com.mehuljoisar.lockscreen.db;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class DataBaseUtility {
    private SQLiteDatabase db;
    private Context context;
    private static DataBaseUtility instance = null;
//TODO: alarm id haro ezaafe kon o pak kon!
    public static DataBaseUtility getInstance(Context con) {
        if (instance == null) {
            instance = new DataBaseUtility();
            instance.context = con;
            try {
                instance.db = instance.context.getApplicationContext().openOrCreateDatabase("lockscreenapp", Context.MODE_PRIVATE, null);
            } catch (Exception e) {
                instance.db = instance.context.openOrCreateDatabase("lockscreenapp", Context.MODE_PRIVATE, null);
            }
            instance.db.execSQL("CREATE TABLE IF NOT EXISTS ALARMID(id INTEGER PRIMARY KEY,alarm_id VARCHAR);");
        }
        return instance;
    }
    public void addAlarmId(String alarmId){
        ContentValues contentValues=new ContentValues();
        contentValues.put("alarm_id", alarmId+"");
        db.insert("ALARMID", null, contentValues);
    }
}