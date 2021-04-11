package com.gsmmoz.maguandza.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.gsmmoz.maguandza.dominio.entidades.DataActual;
import com.gsmmoz.maguandza.dominio.repositorio.DataActualRepositorio;

public class DadosOpenHelper extends SQLiteOpenHelper {


    public DadosOpenHelper(@Nullable Context context) {

        super(context, "BaseDados.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //db.execSQL(ScriptDLL.getCreateTableDataHora());
        String criarTabela = "CREATE TABLE DATAHORA (ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "MES INTEGER)";

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    public boolean addOnDataBase(DataActualRepositorio dataActualRepositorio){


        DataActual dataActual = new DataActual();
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put("MES", dataActual.mes);

        long dataho = db.insert("DATAHORA", null, cv);
        if (dataho == -1)
            return false;
        else
            return true;

    }
}
