package com.gsmmoz.maguandza;

public class DataLocal {
    private int id;
    private int mes;

    // CONSTRUTOR
    public DataLocal(int id, int mes) {
        this.id = id;
        this.mes = mes;
    }

    public DataLocal() {
    }
    public DataLocal(int mes){
        this.mes = mes;
    }

    @Override
    public String toString() {
        return "DataLocal{" +
                "id=" + id +
                ", mes=" + mes +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMes() {
        return mes;
    }

    public void setMes(int mes) {
        this.mes = mes;
    }
}
