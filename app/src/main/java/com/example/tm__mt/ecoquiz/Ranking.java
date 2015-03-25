package com.example.tm__mt.ecoquiz;

public class Ranking {

    private int attemptCntr;
    private int categoryId;
    private String name;
    private int score;
    private String time;

    private static final String TB_NAME = "Ranking";
    private static final String CREATE_QUERY = "CREATE TABLE IF NOT EXISTS " + getTbName() + " ( "
            + " ATTEMPT_CNTR      INTEGER NOT NULL "
            + ",CATEGORY_ID       INTEGER NOT NULL "
            + ",NAME              TEXT NOT NULL"
            + ",SCORE             INTEGER NOT NULL"
            + ",TIME              TEXT NOT NULL"
            + ",PRIMARY KEY (ATTEMPT_CNTR, CATEGORY_ID)"
            + ",FOREIGN KEY(`CATEGORY_ID`) REFERENCES Category(ID)"
            + ");";
    private static final String INSERT_QUERY = "";
    private static final String DROP_QUERY = "DROP " + getTbName() + " TABLE;";

    public static String getTbName() {
        return TB_NAME;
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
