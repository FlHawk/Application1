package com.swufe.application1.DB;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
    private static final int VERSION = 1;
    private static final String DB_name = "myrate.db";
    public static final String TB_NAME = "tb_rates";

    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory,
                    int version){
        super(context,name,factory,version);
    }
//
//    public DBHelper(Context context) {
//        super(context,DB_name,);
//    }


    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    


}
