package com.example.tm__mt.ecoquiz;

public class Answer {

    private int attemptCntr;
    private int categoryId;
    private int questionNum;
    private int givenAnswer;
    private String answerTime;

    private static final String TB_NAME = "Answer";
    private static final String CREATE_QUERY = "CREATE TABLE IF NOT EXISTS " + getTbName() + " ( "
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

    public int getQuestionNum() {
        return questionNum;
    }

    public void setQuestionNum(int questionNum) {
        this.questionNum = questionNum;
    }

    public int getGivenAnswer() {
        return givenAnswer;
    }

    public void setGivenAnswer(int givenAnswer) {
        this.givenAnswer = givenAnswer;
    }

    public String getAnswerTime() {
        return answerTime;
    }

    public void setAnswerTime(String answerTime) {
        this.answerTime = answerTime;
    }
}
