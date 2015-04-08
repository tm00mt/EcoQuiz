package com.example.tm__mt.ecoquiz;

public class ResultRow {
    public int questionNumber;
    public int givenAnswer;
    public String answerTime;

    public ResultRow(int qn, int ga, String at) {
        super();
        this.questionNumber = qn;
        this.givenAnswer = ga;
        this.answerTime = at;
    }
}
