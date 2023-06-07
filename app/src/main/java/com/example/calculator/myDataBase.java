package com.example.calculator;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class myDataBase extends SQLiteOpenHelper {

    private Context context ;
    private static final String DATABASE_NAME = "result.db" ;
    private static final int DATABASE_VERSION  = 1 ;
    private static final String TABLE_NAME = "results";
    private static final String COLUMN_ID = "_id" ;
    private static final String COLUMN_RESULT = "result" ;
    public myDataBase (@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_RESULT + " REAL) ;" ;
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    void addResult(String name){
        SQLiteDatabase db = this.getWritableDatabase() ;

        ContentValues cv = new ContentValues() ;

        cv.put(COLUMN_RESULT , name);

        long result = db.insert(TABLE_NAME , null , cv) ;
        if(result == -1){
            Toast.makeText(context , "Feild" , Toast.LENGTH_SHORT).show();

        }
        else{
            Toast.makeText(context , "Added succefully" , Toast.LENGTH_SHORT).show();

        }
    }
    Cursor readAllData(){
        String query = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null ;
        if(db != null){
            cursor = db.rawQuery(query , null);
        }
        return cursor ;
    }
}