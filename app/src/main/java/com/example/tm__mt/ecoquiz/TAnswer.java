package com.example.tm__mt.ecoquiz;

/**
 * Created by tm__mt
 *
 * Holds a structure of Answer table and provides basic functions/queries to that table.
 */
public class TAnswer {
    //table structure: table and column names
    private static final String TABLE_NAME          = "Answer";
    private static final String COL_ATTEMPT_CNTR    = "ATTEMPT_CNTR";
    private static final String COL_CATEGORY_ID     = "CATEGORY_ID";
    private static final String COL_QUESTION_NUM    = "QUESTION_NUM";
    private static final String COL_GIVEN_ANSWER    = "GIVEN_ANSWER";
    private static final String COL_ANSWER_TIME     = "ANSWER_TIME";

    private int attemptCntr;
    private int categoryId;
    private int questionNum;
    private int givenAnswer;
    private String answerTime;

    private static final String CREATE_QUERY = "CREATE TABLE IF NOT EXISTS " + getTableName() + " ( "
            + " ATTEMPT_CNTR      INTEGER NOT NULL "
            + ",CATEGORY_ID       INTEGER NOT NULL "
            + ",QUESTION_NUM      TEXT NOT NULL"
            + ",GIVEN_ANSWER      TEXT NOT NULL"
            + ",ANSWER_TIME       TEXT NOT NULL"
            + ",PRIMARY KEY (ATTEMPT_CNTR, CATEGORY_ID, QUESTION_NUM)"
            + ",FOREIGN KEY(`CATEGORY_ID`) REFERENCES Category(ID)"
            + ",FOREIGN KEY(`QUESTION_NUM`) REFERENCES Question(ID)"
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

    public static String getColQuestionNum() {
        return COL_QUESTION_NUM;
    }

    public static String getColGivenAnswer() {
        return COL_GIVEN_ANSWER;
    }

    public static String getColAnswerTime() {
        return COL_ANSWER_TIME;
    }

    public int getAttemptCntr() {
        return this.attemptCntr;
    }

    public void setAttemptCntr(int attemptCntr) {
        this.attemptCntr = attemptCntr;
    }

    public int getCategoryId() {
        return this.categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public int getQuestionNum() {
        return this.questionNum;
    }

    public void setQuestionNum(int questionNum) {
        this.questionNum = questionNum;
    }

    public int getGivenAnswer() {
        return this.givenAnswer;
    }

    public void setGivenAnswer(int givenAnswer) {
        this.givenAnswer = givenAnswer;
    }

    public String getAnswerTime() {
        return this.answerTime;
    }

    public void setAnswerTime(String answerTime) {
        this.answerTime = answerTime;
    }
}
