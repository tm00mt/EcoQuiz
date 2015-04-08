package com.example.tm__mt.ecoquiz;

public class ApplicationSettings {
    private static int LANGUAGE = 1;

    public static void setLanguage(String l) {
        switch (l) {
            case "pol": LANGUAGE = 2; break;
            default: LANGUAGE = 1;
        }
    }

    public static int getLanguage() {
        return LANGUAGE;
    }
}
