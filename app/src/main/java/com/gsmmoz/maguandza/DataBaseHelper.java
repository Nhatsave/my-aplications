package com.gsmmoz.maguandza;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DataBaseHelper extends SQLiteOpenHelper {

    public static final String DATA_TABELA = "DATALOCAL";
    public static final String Coluna_ID = "ID";
    public static final String Coluna_MES = "MES";

    public DataBaseHelper(@Nullable Context context) {
        super(context, "DataLocal.db", null, 1);
    }

    // This is called the first time a database is accessed. There shuold be code in here to create a new database
    @Override
    public void onCreate(SQLiteDatabase db) {
        String criarDados;
        criarDados = "CREATE TABLE " + DATA_TABELA + " ( " + Coluna_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + Coluna_MES + " INTEGER )";
        db.execSQL(criarDados);

    }

    // This is called if the database  version number changes. It prevents previous users app from breaking when you change the database
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
    public boolean addOne(DataLocal dataLocal){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(Coluna_MES, dataLocal.getMes());

        long insert = db.insert(DATA_TABELA, null, cv);
        if (insert == -1)
            return false;
        else
            return true;
    }
    //_____________________________________   RECUPERAR DADOS APARTIR DA DATABASE ____________________________________________________________
    public List<DataLocal> getEveryone(){

        List<DataLocal> returnList = new ArrayList<>();
        List<String> retornList = new ArrayList<>();


        String queryString = " SELECT * FROM " + DATA_TABELA;

        SQLiteDatabase ddb = this.getReadableDatabase();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString, null);

        if (cursor.moveToFirst()){
            //   ____  Loop through the cursor (result set) and create new datalocal object. Put them into a return list
            do {
                //int dataID = cursor.getInt(0);
                int dataMes = cursor.getInt(1);

                DataLocal novaData = new DataLocal( dataMes);

                returnList.add(novaData);

            }while (cursor.moveToNext());
        }else{
            // Failure.do not add anything to the list
        }
        //___________________ CLose both the cursor and the db when done
        cursor.close();
        db.close();

        return returnList;
    }

}
