package com.mehuljoisar.lockscreen.db;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.mehuljoisar.lockscreen.base.ApplicationClass;

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
                instance.db= ApplicationClass.getInstance().openOrCreateDatabase("lockscreenapp", Context.MODE_PRIVATE, null);
            } catch (Exception e) {
                try{
                    instance.db = instance.context.getApplicationContext().openOrCreateDatabase("lockscreenapp", Context.MODE_PRIVATE, null);
                }catch (Exception ex){
                    try{
                        instance.db = instance.context.openOrCreateDatabase("lockscreenapp", Context.MODE_PRIVATE, null);
                    }catch (Exception exe){
                        exe.printStackTrace();
                    }
                }
            }
            instance.db.execSQL("CREATE TABLE IF NOT EXISTS PATTERN(id INTEGER PRIMARY KEY,pattern VARCHAR);");
        }
        return instance;
    }
    public void setOrUpdatePattern(String pattern){
        ArrayList<String> array_list=new ArrayList<>();
        Cursor res =  db.rawQuery( "select * from PATTERN", null );
        res.moveToFirst();
        while(!res.isAfterLast()){
            array_list.add(res.getString(res.getColumnIndex("pattern")));
            res.moveToNext();
        }
        ContentValues contentValues=new ContentValues();
        contentValues.put("pattern", pattern);
        if(array_list.size()>0)
            db.execSQL("UPDATE PATTERN SET pattern='"+pattern+"' WHERE id=1 ");
        else
            db.insert("PATTERN", null, contentValues);
        res.close();
    }
    public String getPattern(){
        ArrayList<String> array_list=new ArrayList<>();
        Cursor res =  db.rawQuery( "select * from PATTERN", null );
        res.moveToFirst();
        while(!res.isAfterLast()){
            array_list.add(res.getString(res.getColumnIndex("pattern")));
            res.moveToNext();
        }
        res.close();
        if(array_list.isEmpty())
            return null;
        return array_list.get(0).toString();
    }
}