package com.example.tm__mt.ecoquiz;

/**
 * Created by tm__mt
 *
 * Holds a structure of Lang table and provides basic functions/queries to that table.
 */
public class TLang {
    //table structure: table and column names
    private static final String TABLE_NAME          = "Lang";
    private static final String COL_ID              = "ID";
    private static final String COL_CODE            = "CODE";

    private int id = -1;
    private String code = "";

    private static final String CREATE_QUERY = "CREATE TABLE IF NOT EXISTS " + getTableName() + " ( "
            + " ID      INTEGER NOT NULL "
            + ",CODE    TEXT NOT NULL "
            + ",PRIMARY KEY (ID)"
            + ");";
    private static final String INSERT_QUERY = "INSERT INTO " + getTableName() + " VALUES "
            + " (1, 'en')"
            + ",(2, 'pl')"
            + ";";
    private static final String DROP_QUERY = "DROP " + getTableName() + " TABLE;";

    public TLang(int id, String code) {
        this.id = id;
        this.code = code;
    }

    public String insertQuery() {
        if (id == -1 || code.equals(""))
            return null;

        return "INSERT INTO " + getTableName() + " VALUES " + " ("
                + getId()
                + ", '" + getCode() + "'"
                + ");";
    }

    public static String getTableName() {
        return TABLE_NAME;
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
