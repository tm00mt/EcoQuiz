package com.example.tm__mt.ecoquiz;

public class RankingRow {
    public String name;
    public int score;
    public String time;
    public int attempt;

    public RankingRow(String n, int s, String t, int a) {
        super();
        this.name = n;
        this.score = s;
        this.time = t;
        this.attempt = a;
    }
}
