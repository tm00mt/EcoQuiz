package com.example.tm__mt.ecoquiz;

public class Lang {
    private int id;
    private String code;

    private static final String COL_ID = "ID";
    private static final String COL_CODE = "CODE";

    private static final String TB_NAME = "Lang";
    private static final String CREATE_QUERY = "CREATE TABLE IF NOT EXISTS " + getTbName() + " ( "
            + " ID      INTEGER NOT NULL "
            + ",CODE    TEXT NOT NULL "
            + ",PRIMARY KEY (ID)"
            + ");";
    private static final String INSERT_QUERY = "INSERT INTO " + getTbName() + " VALUES "
            + " (1, 'en')"
            + ",(2, 'pl')"
            + ";";
    private static final String DROP_QUERY = "DROP " + getTbName() + " TABLE;";

    public static String getTbName() {
        return TB_NAME;
    }

    public static String getCreateQuery() {
        return CREATE_QUERY;
    }

    public static String getDropQuery() {
        return DROP_QUERY;
    }

    public static String getInsertQuery() {
        return INSERT_QUERY;
    }

    public static String getColId() {
        return COL_ID;
    }

    public static String getColCode() {
        return COL_CODE;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
