package com.example.tm__mt.ecoquiz;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.DisplayMetrics;

import java.util.Locale;

/**
 * Created by tm__mt
 *
 * By this class you can get and set general application settings like:
 * - language id of displayed questions
 * - place id from which logos will be (down)loaded in QuestionActivity
 * - screen density id
 *
 */
public class ApplicationSettings {
    private static Context ctx;

    private static String APP_PREFS = "ecoquiz.app_prefs";
    private static String PATH_PREFS = "PATH_PREFS";

    public static final int PATH_SRC_WEB   = 1;
    public static final int PATH_SRC_LOCAL = 2;

    public ApplicationSettings(Context ctx) {
        this.ctx = ctx;
    }

    public static int getLanguage() {
        // language in which question is displayed in QuestionActivity
        // 1 - english (default)
        // 2 - polish

        switch (Locale.getDefault().getISO3Language()) {
            case "eng": return 1;
            case "pol": return 2;
            default:    return 1;
        }
    }

    public static int getPathSource() {
        // the place from which logos will be loaded
        // 1 - web (default)
        // 2 - local storage

        SharedPreferences prefs = ctx.getSharedPreferences(APP_PREFS, Context.MODE_PRIVATE);

        return prefs.getInt(PATH_PREFS, 1);
    }

    public static void setPathSource(int pathSrc) {
        SharedPreferences prefs = ctx.getSharedPreferences(APP_PREFS, Context.MODE_PRIVATE);

        prefs.edit().putInt(PATH_PREFS, pathSrc).apply();
    }

    public static int getScreenDensity() {
        // returns the screen density id of device (Data collected during a 7-day period ending on April 6, 2015.)
        // 1 - low density (4,8%, not supported)
        // 2 - medium density (16,8%)
        // 3 - high density (40,2%, default)
        // 4 - extra high density (20,7%)
        // 5 - extra extra high density (15,9%)
        // 6 - extra extra extra high density (???%, not supported)

        DisplayMetrics metrics = new DisplayMetrics();
        switch (metrics.densityDpi) {
            case DisplayMetrics.DENSITY_LOW:
            case DisplayMetrics.DENSITY_MEDIUM:
                return 2;
            case DisplayMetrics.DENSITY_HIGH:
                return 3;
            case DisplayMetrics.DENSITY_XHIGH:
                return 4;
            case DisplayMetrics.DENSITY_XXHIGH:
            case DisplayMetrics.DENSITY_XXXHIGH:
                return 5;
            default: return 3;
        }
    }
}
