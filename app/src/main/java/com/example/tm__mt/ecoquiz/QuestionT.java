package com.example.tm__mt.ecoquiz;

public class QuestionT {
    private int questionId;
    private int langId;
    private String question;
    private String ad1Path;
    private String ad2Path;
    private String ad3Path;
    private String ad4Path;
    private String ad5Path;
    private String ad6Path;

    private static final String COL_QUESTION_ID = "QUESTION_ID";
    private static final String COL_LANG_ID = "LANG_ID";
    private static final String COL_QUESTION = "QUESTION";

    private static final String TB_NAME = "QuestionT";
    private static final String CREATE_QUERY = "CREATE TABLE IF NOT EXISTS " + getTbName() + " ( "
            + " QUESTION_ID       INTEGER NOT NULL "
            + ",LANG_ID           INTEGER NOT NULL "
            + ",QUESTION          TEXT NOT NULL "
            + ",AD1PATH           TEXT NOT NULL "
            + ",AD2PATH           TEXT NOT NULL "
            + ",AD3PATH           TEXT NOT NULL "
            + ",AD4PATH           TEXT NOT NULL "
            + ",AD5PATH           TEXT NOT NULL "
            + ",AD6PATH           TEXT NOT NULL "
            + ",PRIMARY KEY (QUESTION_ID, LANG_ID) "
            + ",FOREIGN KEY(`QUESTION_ID`) REFERENCES Question(ID) "
            + ",FOREIGN KEY(`LANG_ID`) REFERENCES Lang(ID) "
            + ");";
    private static final String INSERT_QUERY = "INSERT INTO " + getTbName() + " VALUES "
            + " (1, 1, 'Question1, Category1', 'http://i.imgur.com/Vg9lXiZ.png', 'http://i.imgur.com/Vfm7NoC.png', 'http://i.imgur.com/vHUcxB7.png', 'http://i.imgur.com/JaqXqmG.png', 'http://i.imgur.com/Hmr3yTw.png', 'http://i.imgur.com/rvXv9NR.png')"
            + ",(2, 1, 'Question2, Category1', 'http://i.imgur.com/OqyALVR.png', 'http://i.imgur.com/mRHSBcz.png', 'http://i.imgur.com/X7lzPJL.png', 'http://i.imgur.com/2ecrpIr.png', 'http://i.imgur.com/JaqXqmG.png', 'http://i.imgur.com/rvXv9NR.png')"
            + ",(3, 1, 'Question3, Category1', 'http://i.imgur.com/AEc3k3Z.png', 'http://i.imgur.com/OqyALVR.png', 'http://i.imgur.com/X7lzPJL.png', 'http://i.imgur.com/AUmSHa3.png', 'http://i.imgur.com/vHUcxB7.png', 'http://i.imgur.com/Hmr3yTw.png')"
            + ",(4, 1, 'Question1, Category2', 'http://i.imgur.com/AEc3k3Z.png', 'http://i.imgur.com/X7lzPJL.png', 'http://i.imgur.com/mRHSBcz.png', 'http://i.imgur.com/AUmSHa3.png', 'http://i.imgur.com/JaqXqmG.png', 'http://i.imgur.com/rvXv9NR.png')"
            + ",(5, 1, 'Question2, Category2', 'http://i.imgur.com/X7lzPJL.png', 'http://i.imgur.com/OqyALVR.png', 'http://i.imgur.com/2ecrpIr.png', 'http://i.imgur.com/mRHSBcz.png', 'http://i.imgur.com/Vfm7NoC.png', 'http://i.imgur.com/JaqXqmG.png')"
            + ",(6, 1, 'Question3, Category2', 'http://i.imgur.com/AEc3k3Z.png', 'http://i.imgur.com/AUmSHa3.png', 'http://i.imgur.com/vHUcxB7.png', 'http://i.imgur.com/2ecrpIr.png', 'http://i.imgur.com/JaqXqmG.png', 'http://i.imgur.com/Vfm7NoC.png')"
            + ",(1, 2, 'Pytanie1, Kategoria1', 'http://i.imgur.com/Vg9lXiZ.png', 'http://i.imgur.com/Vfm7NoC.png', 'http://i.imgur.com/vHUcxB7.png', 'http://i.imgur.com/JaqXqmG.png', 'http://i.imgur.com/Hmr3yTw.png', 'http://i.imgur.com/rvXv9NR.png')"
            + ",(2, 2, 'Pytanie2, Kategoria1', 'http://i.imgur.com/OqyALVR.png', 'http://i.imgur.com/mRHSBcz.png', 'http://i.imgur.com/X7lzPJL.png', 'http://i.imgur.com/2ecrpIr.png', 'http://i.imgur.com/JaqXqmG.png', 'http://i.imgur.com/rvXv9NR.png')"
            + ",(3, 2, 'Pytanie3, Kategoria1', 'http://i.imgur.com/AEc3k3Z.png', 'http://i.imgur.com/OqyALVR.png', 'http://i.imgur.com/X7lzPJL.png', 'http://i.imgur.com/AUmSHa3.png', 'http://i.imgur.com/vHUcxB7.png', 'http://i.imgur.com/Hmr3yTw.png')"
            + ",(4, 2, 'Pytanie1, Kategoria2', 'http://i.imgur.com/AEc3k3Z.png', 'http://i.imgur.com/X7lzPJL.png', 'http://i.imgur.com/mRHSBcz.png', 'http://i.imgur.com/AUmSHa3.png', 'http://i.imgur.com/JaqXqmG.png', 'http://i.imgur.com/rvXv9NR.png')"
            + ",(5, 2, 'Pytanie2, Kategoria2', 'http://i.imgur.com/X7lzPJL.png', 'http://i.imgur.com/OqyALVR.png', 'http://i.imgur.com/2ecrpIr.png', 'http://i.imgur.com/mRHSBcz.png', 'http://i.imgur.com/Vfm7NoC.png', 'http://i.imgur.com/JaqXqmG.png')"
            + ",(6, 2, 'Pytanie3, Kategoria2', 'http://i.imgur.com/AEc3k3Z.png', 'http://i.imgur.com/AUmSHa3.png', 'http://i.imgur.com/vHUcxB7.png', 'http://i.imgur.com/2ecrpIr.png', 'http://i.imgur.com/JaqXqmG.png', 'http://i.imgur.com/Vfm7NoC.png')"
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

    public static String getColQuestionId() {
        return COL_QUESTION_ID;
    }

    public static String getColLangId() {
        return COL_LANG_ID;
    }

    public static String getColQuestion() {
        return COL_QUESTION;
    }

    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    public int getLangId() {
        return langId;
    }

    public void setLangId(int langId) {
        this.langId = langId;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAd1Path() {
        return ad1Path;
    }

    public void setAd1Path(String ad1Path) {
        this.ad1Path = ad1Path;
    }

    public String getAd2Path() {
        return ad2Path;
    }

    public void setAd2Path(String ad2Path) {
        this.ad2Path = ad2Path;
    }

    public String getAd3Path() {
        return ad3Path;
    }

    public void setAd3Path(String ad3Path) {
        this.ad3Path = ad3Path;
    }

    public String getAd4Path() {
        return ad4Path;
    }

    public void setAd4Path(String ad4Path) {
        this.ad4Path = ad4Path;
    }

    public String getAd5Path() {
        return ad5Path;
    }

    public void setAd5Path(String ad5Path) {
        this.ad5Path = ad5Path;
    }

    public String getAd6Path() {
        return ad6Path;
    }

    public void setAd6Path(String ad6Path) {
        this.ad6Path = ad6Path;
    }
}
