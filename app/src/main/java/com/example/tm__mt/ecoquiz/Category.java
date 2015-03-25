package com.example.tm__mt.ecoquiz;

public class Category {
    private int id;

    private static final String TB_NAME = "Category";
    private static final String CREATE_QUERY = "CREATE TABLE IF NOT EXISTS " + getTbName() + " ( "
            + " ID      INTEGER NOT NULL "
            + ",NAME    TEXT NOT NULL "
            + ",PRIMARY KEY (ID)"
            + ");";
    private static final String INSERT_QUERY = "INSERT INTO " + getTbName() + " VALUES "
            + " (1,'Food')"
            + ",(2,'Environment')"
            + ";";
    private static final String DROP_QUERY = "DROP " + getTbName() + " TABLE;";

    public static String getCreateQuery() {
        return CREATE_QUERY;
    }

    public static String getInsertQuery() {
        return INSERT_QUERY;
    }

    public static String getDropQuery() {
        return DROP_QUERY;
    }

    public static String getTbName() {
        return TB_NAME;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}