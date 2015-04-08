package com.example.tm__mt.ecoquiz;

/**
 * Created by tm__mt on 04.03.15.
 */
public class Question {
    private int questionNum;
    private int categoryId;
    private int id;
    private int correctAnswer;

    private static final String COL_CATEGORY_ID = "CATEGORY_ID";
    private static final String COL_QUESTION_NUM = "QUESTION_NUM";
    private static final String COL_ID = "ID";
    private static final String COL_CORRECT_ANSWER = "CORRECT_ANSWER";

    private static final String TB_NAME = "Question";
    private static final String CREATE_QUERY = "CREATE TABLE IF NOT EXISTS " + getTbName() + " ( "
            + " CATEGORY_ID       INTEGER NOT NULL "
            + ",QUESTION_NUM      INTEGER NOT NULL "
            + ",ID                INTEGER NOT NULL UNIQUE "
            + ",CORRECT_ANSWER    INTEGER NOT NULL "
            + ",PRIMARY KEY (QUESTION_NUM, CATEGORY_ID) "
            + ",FOREIGN KEY(`CATEGORY_ID`) REFERENCES Category(ID) "
            + ");";
    private static final String INSERT_QUERY = "INSERT INTO " + getTbName() + " VALUES "
            + " (1, 1, 1, 5)"
            + ",(1, 2, 2, 2)"
            + ",(1, 3, 3, 1)"
            + ",(2, 1, 4, 6)"
            + ",(2, 2, 5, 4)"
            + ",(2, 3, 6, 3)"
            + ";";
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

    public static String getColCategoryId() {
        return COL_CATEGORY_ID;
    }

    public static String getColQuestionNum() {
        return COL_QUESTION_NUM;
    }

    public static String getColId() {
        return COL_ID;
    }

    public static String getColCorrectAnswer() {
        return COL_CORRECT_ANSWER;
    }

    public int getQuestionNum() {
        return questionNum;
    }

    public void setQuestionNum(int questionNum) {
        this.questionNum = questionNum;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(int correctAnswer) {
        this.correctAnswer = correctAnswer;
    }
}
