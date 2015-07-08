package com.example.tm__mt.ecoquiz;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.DisplayMetrics;
import android.util.Log;

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
public class ApplicationSettings extends Application {
    private static final String DEBUG_TAG = "ApplicationSettings";

    private static Context ctx = null;
    private static int densityId = 0;
    private static int langId = 0;

    private static String APP_PREFS = "ecoquiz.app_prefs";
    private static String PATH_PREFS = "PATH_PREFS";

    public static final int PATH_SRC_WEB   = 1;
    public static final int PATH_SRC_LOCAL = 2;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(DEBUG_TAG, "Creating ApplicationSettings....");

        this.ctx = getApplicationContext();
    }

    public static int getLanguage() {
        // language in which question is displayed in QuestionActivity
        // 1 - english (default)
        // 2 - polish

        switch (Locale.getDefault().getISO3Language()) {
            case "eng": langId = 1; break;
            case "pol": langId = 2; break;
            default:    langId = 1;
        }
        Log.d(DEBUG_TAG, "Language id of device: " + langId);

        return langId;
    }

    public static int getPathSource() {
        // the place from which logos will be loaded
        // 1 - web (default)
        // 2 - local storage

        SharedPreferences prefs = ctx.getSharedPreferences(APP_PREFS, Context.MODE_PRIVATE);
        int pathSrc = prefs.getInt(PATH_PREFS, 1);
        Log.d(DEBUG_TAG, "Logos path source id: " + pathSrc);

        return pathSrc;
    }

    public static void setPathSource(int pathSrc) {
        SharedPreferences prefs = ctx.getSharedPreferences(APP_PREFS, Context.MODE_PRIVATE);

        prefs.edit().putInt(PATH_PREFS, pathSrc).apply();
        Log.d(DEBUG_TAG, "New logos path source id: " + pathSrc);
    }

    public static int getScreenDensity() {
        // returns the screen density id of device
        // (Data collected during a 7-day period ending on April 6, 2015.):
        // 1 - low density (4,8%, not supported)
        // 2 - medium density (16,8%)
        // 3 - high density (40,2%, default)
        // 4 - extra high density (20,7%)
        // 5 - extra extra high density (15,9%)
        // 6 - extra extra extra high density (???%, not supported)

        if (densityId == 0) {
            DisplayMetrics metrics = new DisplayMetrics();

            switch (metrics.densityDpi) {
                //TODO odkomentować pozostałe przypadki, gdy loga dla nich będą wrzucone na serwer do odpowiednich folderów
            /*    case DisplayMetrics.DENSITY_LOW:
                case DisplayMetrics.DENSITY_MEDIUM:
                    densityId = 2; break;
            */    case DisplayMetrics.DENSITY_HIGH:
                    densityId = 3; break;
            /*    case DisplayMetrics.DENSITY_XHIGH:
                    densityId = 4; break;
                case DisplayMetrics.DENSITY_XXHIGH:
                case DisplayMetrics.DENSITY_XXXHIGH:
                    densityId = 5; break;
            */    default:
                    densityId = 3;
            }
        }
        Log.d(DEBUG_TAG, "Screen density id: " + densityId);

        return densityId;
    }
}
