package com.gsmmoz.maguandza.database;

public class ScriptDLL {

    public static String getCreateTableDataHora(){

        StringBuilder sql = new StringBuilder();

        sql.append(" CREATE TABLE IF NOT EXISTS DATAHORA ( ");
        //sql.append(" DIA INTEGER,  ");
        sql.append(" id INTEGER PRIMARY KEY AUTOINCREMENT ");
        sql.append(" MES INTEGER  ");
        //sql.append(" ANO INTEGER )");

        return sql.toString();

    }
}
