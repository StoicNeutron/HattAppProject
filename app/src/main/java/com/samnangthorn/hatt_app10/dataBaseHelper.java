package com.samnangthorn.hatt_app10;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class dataBaseHelper extends SQLiteOpenHelper {

    private Context context;
    private static final String DataBase_Name = "OffLine_DB";
    private static final int DataBase_Version = 1;
    private static final String TABLE_NAME = "attribute";

    private static final String COLUMN_ID = "_ID";
    private static final String COLUMN_1 = "exercise_name";
    private static final String COLUMN_2 = "main_muscle_target";
    private static final String COLUMN_3 = "sub_muscle_target";
    private static final String COLUMN_4 = "exercise_dis";

    public dataBaseHelper(@Nullable Context context) {
        super(context, DataBase_Name, null, DataBase_Version);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = " CREATE TABLE " + TABLE_NAME + " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_1 + " TEXT, " + COLUMN_2 + " TEXT, " + COLUMN_3 + " TEXT, " + COLUMN_4 + " TEXT); ";
        db.execSQL(query);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " +  TABLE_NAME);
        onCreate(db);
    }

    public boolean addExercise(String exerciseName, String mainTarget, String subTarget, String dis){
        boolean success;
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_1, exerciseName);
        cv.put(COLUMN_2, mainTarget);
        cv.put(COLUMN_3, subTarget);
        cv.put(COLUMN_4, dis);
        long result = db.insert(TABLE_NAME, null, cv);
        if(result == -1){
            success = false;
        }else{
            success = true;
        }
        return success;
    }

    public void deleteThisExercise(String eName){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM " + TABLE_NAME + " WHERE " + COLUMN_1 + " = '" + eName + "'";
        db.execSQL(query);
    }

    Cursor readAllAtr(){
        String query = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }
}
