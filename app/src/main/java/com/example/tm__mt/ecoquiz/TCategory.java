package com.example.tm__mt.ecoquiz;

/**
 * Created by tm__mt
 *
 * Holds a structure of Category table and provides basic functions/queries to that table.
 */
public class TCategory {
    //table structure: table and column names
    private static final String TABLE_NAME          = "Category";
    private static final String COL_ID              = "ID";
    private static final String COL_NAME            = "NAME";

    private int id = -1;
    private String name = "";

    private static final String CREATE_QUERY = "CREATE TABLE IF NOT EXISTS " + getTableName() + " ( "
            + " ID      INTEGER NOT NULL "
            + ",NAME    TEXT NOT NULL "
            + ",PRIMARY KEY (ID)"
            + ");";
    private static final String INSERT_QUERY = "INSERT INTO " + getTableName() + " VALUES "
            + " (1,'Food')"
            + ",(2,'Environment')"
            + ",(3,'Organizations')"
            + ";";
    private static final String DROP_QUERY = "DROP " + getTableName() + " TABLE;";

    public TCategory(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public String insertQuery() {
        if (this.id == -1 || this.name.equals(""))
            return null;

        return "INSERT INTO " + getTableName() + " VALUES "+ " ("
                + getId()
                + ", '" + getName() + "'"
                +");";
    }

    public static String getCreateQuery() {
        return CREATE_QUERY;
    }

    public static String getInsertQuery() {
        return INSERT_QUERY;
    }

    public static String getDropQuery() {
        return DROP_QUERY;
    }

    public static String getTableName() {
        return TABLE_NAME;
    }

    public static String getColId() {
        return COL_ID;
    }

    public static String getColName() {
        return COL_NAME;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
