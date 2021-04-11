package com.gsmmoz.maguandza.dominio.repositorio;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.gsmmoz.maguandza.dominio.entidades.DataActual;

import java.util.ArrayList;
import java.util.List;

public class DataActualRepositorio {

    private SQLiteDatabase conexao;

    public DataActualRepositorio(SQLiteDatabase conexao)
    {
        this.conexao = conexao;
    }

    public boolean inserir(DataActual dataActual){
        ContentValues contentValues = new ContentValues();
        //contentValues.put("DIA", dataActual.dia);
        contentValues.put("MES", dataActual.mes);
        //contentValues.put("ANO", dataActual.ano);

        long conexao = this.conexao.insertOrThrow("DATAHORA", null, contentValues);
        if (conexao == -1){
            return false;
        }else
            return true;
    }

    private void excluir(int data){

    }
    public void alterar(DataActual dataActual){
        ContentValues contentValues = new ContentValues();
       // contentValues.put("DIA", dataActual.dia);
        contentValues.put("MES", dataActual.mes);
        //contentValues.put("ANO", dataActual.ano);

       // conexao.update("DATAHORA", contentValues,);

    }
    public List<DataActual> buscarTodos(){
        List<DataActual> dataActual = new ArrayList<DataActual>();

        StringBuilder sql = new StringBuilder();
        sql.append("SELECT DIA, MES, ANO");
        sql.append("FROM DATAHORA");
        Cursor resultado = conexao.rawQuery(sql.toString(),null);
        if (resultado.getCount() > 0){
            resultado.moveToFirst();

            do {
                DataActual dt = new DataActual();
                //dt.dia = resultado.getInt(resultado.getColumnIndexOrThrow("DIA"));
                dt.mes = resultado.getInt(resultado.getColumnIndexOrThrow("MES"));
                //dt.ano = resultado.getInt(resultado.getColumnIndexOrThrow("ANO"));

                dataActual.add(dt);

            }while (resultado.moveToFirst());
        }

        return dataActual;
    }
    public DataActual buscarData(int mes){

        DataActual dta = new DataActual();
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT DIA, MES, ANO");
        sql.append("FROM DATAHORA");
        sql.append("WHERE MES = ?");

        String[]  parametros = new String[1];
        parametros[0] = String.valueOf(mes);

        Cursor resultado = conexao.rawQuery(sql.toString(), parametros);
        if (resultado.getCount() > 0) {
            resultado.moveToFirst();

            //dta.dia = resultado.getInt(resultado.getColumnIndexOrThrow("DIA"));
            dta.mes = resultado.getInt(resultado.getColumnIndexOrThrow("MES"));
            //dta.ano = resultado.getInt(resultado.getColumnIndexOrThrow("ANO"));

            return dta;
        }

        return null;
    }
}
