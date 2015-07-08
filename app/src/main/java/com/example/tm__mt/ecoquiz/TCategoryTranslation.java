package com.example.tm__mt.ecoquiz;

/**
 * Created by tm__mt
 *
 * Holds a structure of CategoryTranslation table and provides basic functions/queries to that table.
 */
public class TCategoryTranslation {
    //table structure: table and column names
    private static final String TABLE_NAME          = "CategoryTranslation";
    private static final String COL_CATEGORY_ID     = "CATEGORY_ID";
    private static final String COL_LANG_ID         = "LANG_ID";
    private static final String COL_NAME            = "NAME";

    private int categoryId = -1;
    private int langId = -1;
    private String name = "";

    private static final String CREATE_QUERY = "CREATE TABLE IF NOT EXISTS " + getTableName() + " ( "
            + " CATEGORY_ID       INTEGER NOT NULL "
            + ",LANG_ID           INTEGER NOT NULL "
            + ",NAME              TEXT NOT NULL "
            + ",PRIMARY KEY(CATEGORY_ID,LANG_ID) "
            + ",FOREIGN KEY(`CATEGORY_ID`) REFERENCES Category(ID) "
            + ",FOREIGN KEY(`LANG_ID`) REFERENCES Lang(ID) "
            + ");";
    private static final String INSERT_QUERY = "INSERT INTO " + getTableName() + " VALUES "
            + " (1,1,'Food')"
            + ",(1,2,'Jedzenie')"
            + ",(2,1,'Environment')"
            + ",(2,2,'Środowisko')"
            + ",(3,1,'Organizations')"
            + ",(3,2,'Organizacje')"
            + ";";
    private static final String DROP_QUERY = "DROP " + getTableName() + " TABLE;";

    public TCategoryTranslation(int categoryId, int langId, String name) {
        this.categoryId = categoryId;
        this.langId = langId;
        this.name = name;
    }

    public String insertQuery() {
        if (this.categoryId == -1 || this.langId == -1 || this.name.equals(""))
            return null;

        return "INSERT INTO " + getTableName() + " VALUES " + " ("
                + getCategoryId()
                + ", " + getLangId()
                + ", '" + getName() + "'"
                + ");";
    }

    public static String getColCategoryId() {
        return COL_CATEGORY_ID;
    }

    public static String getColLangId() {
        return COL_LANG_ID;
    }

    public static String getColName() {
        return COL_NAME;
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

    //public static String getInsertQuery() {
    //    return INSERT_QUERY;
    //}

    public int getCategoryId() {
        return this.categoryId;
    }

    public int getLangId() {
        return this.langId;
    }

    public String getName() {
        return this.name;
    }
}
