package com.example.tm__mt.ecoquiz;

public class CategoryT {
    private int categoryId;
    private int langId;
    private String name;

    private static final String COL_CATEGORY_ID = "CATEGORY_ID";
    private static final String COL_LANG_ID = "LANG_ID";
    private static final String COL_NAME = "NAME";

    private static final String TB_NAME = "CategoryT";
    private static final String CREATE_QUERY = "CREATE TABLE IF NOT EXISTS " + getTbName() + " ( "
            + " CATEGORY_ID       INTEGER NOT NULL "
            + ",LANG_ID           INTEGER NOT NULL "
            + ",NAME              TEXT NOT NULL "
            + ",PRIMARY KEY(CATEGORY_ID,LANG_ID) "
            + ",FOREIGN KEY(`CATEGORY_ID`) REFERENCES Category(ID) "
            + ",FOREIGN KEY(`LANG_ID`) REFERENCES Lang(ID) "
            + ");";
    private static final String INSERT_QUERY = "INSERT INTO " + getTbName() + " VALUES "
            + " (1,1,'Food')"
            + ",(1,2,'Jedzenie')"
            + ",(2,1,'Environment')"
            + ",(2,2,'Środowisko')"
            + ";";
    private static final String DROP_QUERY = "DROP " + getTbName() + " TABLE;";

    public static String getColCategoryId() {
        return COL_CATEGORY_ID;
    }

    public static String getColLangId() {
        return COL_LANG_ID;
    }

    public static String getColName() {
        return COL_NAME;
    }

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

    public int getCategoryId() {
        return categoryId;
    }

    public int getLangId() {
        return langId;
    }

    public String getName() {
        return name;
    }
}
