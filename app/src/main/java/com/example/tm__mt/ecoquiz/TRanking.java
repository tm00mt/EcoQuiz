package com.example.tm__mt.ecoquiz;

/**
 * Created by tm__mt
 *
 * Holds a structure of Ranking table and provides basic functions/queries to that table.
 */
public class TRanking {
    //table structure: table and column names
    private static final String TABLE_NAME          = "Ranking";
    private static final String COL_ATTEMPT_CNTR    = "ATTEMPT_CNTR";
    private static final String COL_CATEGORY_ID     = "CATEGORY_ID";
    private static final String COL_NAME            = "NAME";
    private static final String COL_SCORE           = "SCORE";
    private static final String COL_TIME            = "TIME";

    private int attemptCntr;
    private int categoryId;
    private String name;
    private int score;
    private String time;


    private static final String CREATE_QUERY = "CREATE TABLE IF NOT EXISTS " + getTableName() + " ( "
            + " ATTEMPT_CNTR      INTEGER NOT NULL "
            + ",CATEGORY_ID       INTEGER NOT NULL "
            + ",NAME              TEXT NOT NULL"
            + ",SCORE             INTEGER NOT NULL"
            + ",TIME              TEXT NOT NULL"
            + ",PRIMARY KEY (ATTEMPT_CNTR, CATEGORY_ID)"
            + ",FOREIGN KEY(`CATEGORY_ID`) REFERENCES Category(ID)"
            + ");";
    private static final String INSERT_QUERY = "";
    private static final String DROP_QUERY = "DROP " + getTableName() + " TABLE;";

    public static String getTableName() {
        return TABLE_NAME;
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

    public static String getColAttemptCntr() {
        return COL_ATTEMPT_CNTR;
    }

    public static String getColCategoryId() {
        return COL_CATEGORY_ID;
    }

    public static String getColName() {
        return COL_NAME;
    }

    public static String getColScore() {
        return COL_SCORE;
    }

    public static String getColTime() {
        return COL_TIME;
    }

    public int getAttemptCntr() {
        return attemptCntr;
    }

    public void setAttemptCntr(int attemptCntr) {
        this.attemptCntr = attemptCntr;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
