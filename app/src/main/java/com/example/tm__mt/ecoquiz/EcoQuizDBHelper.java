package com.example.tm__mt.ecoquiz;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * The MySQLiteHelper class provides access to operations on internal DB
 * used in EcoQuiz application.
 */
public class EcoQuizDBHelper extends SQLiteOpenHelper {

    private static final String DEBUG_TAG = "MySQLiteHelper";

    private static final String DB_NAME = "EcoQuizDB";
    private static final int DB_VERSION = 1;
    private SQLiteDatabase DB;

    public EcoQuizDBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d(DEBUG_TAG, "Database " + DB_NAME + " ver. " + DB_VERSION + " creating...");

        db.execSQL(Lang.getCreateQuery());
        db.execSQL(Lang.getInsertQuery());
        Log.d(DEBUG_TAG, "Table " + Lang.getTbName() + " created.");

        db.execSQL(Category.getCreateQuery());
        db.execSQL(Category.getInsertQuery());
        Log.d(DEBUG_TAG, "Table " + Category.getTbName() + " created.");

        db.execSQL(Question.getCreateQuery());
        db.execSQL(Question.getInsertQuery());
        Log.d(DEBUG_TAG, "Table " + Question.getTbName() + " created.");

        db.execSQL(QuestionT.getCreateQuery());
        db.execSQL(QuestionT.getInsertQuery());
        Log.d(DEBUG_TAG, "Table " + QuestionT.getTbName() + " created.");

        db.execSQL(Answer.getCreateQuery());
        Log.d(DEBUG_TAG, "Table " + Answer.getTbName() + " created.");

        db.execSQL(Ranking.getCreateQuery());
        Log.d(DEBUG_TAG, "Table " + Ranking.getTbName() + " created.");

        Log.d(DEBUG_TAG, "Database " + DB_NAME + " created!!!");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        switch (oldVersion){
            case 1:
                //migration to 2nd version
            case 2:
                //migration to 3rd version
                //etc.
                Log.d(DEBUG_TAG, "Database updating to " + newVersion + "ver. ...");
                break;
            default:
                //ERROR
        }


        //db.execSQL(TB_QUIZ_DROP);



        //onCreate(db);
    }

    public String getFirstLang() {
        Log.d(DEBUG_TAG, "getting data1");
        DB = this.getReadableDatabase();
        String r = "";
        Cursor c = DB.query(Lang.getTbName(), new String[]{"ID", "CODE"}, null,null,null,null,null);
        c.moveToFirst();
        Log.d(DEBUG_TAG, "getting data4");
        while (!c.isAfterLast()) {
            r += c.getString(0) + " ";
            r += c.getString(1) + " ";
            c.moveToNext();
        }
        c.close();
        return r;
    }

    public String[] getLogoLinks(int category, int questionNumber, int langId) {
        Log.d(DEBUG_TAG, "Getting logo links from DB");
        String[] logoLinks = new String[6];

        DB = this.getReadableDatabase();
        Cursor c = DB.rawQuery("SELECT AD1PATH, AD2PATH, AD3PATH, AD4PATH, AD5PATH, AD6PATH FROM " + QuestionT.getTbName()
                + " WHERE " + QuestionT.getColLangId() + " = ? AND " + QuestionT.getColQuestionId()
                + " = ( SELECT " + Question.getColId() + " FROM " + Question.getTbName()
                + " WHERE " + Question.getColCategoryId() + " = ? AND " + Question.getColQuestionNum() + " = ? )"
                , new String[] { Integer.toString(langId), Integer.toString(category), Integer.toString(questionNumber) });

        c.moveToFirst();
        while (!c.isAfterLast() && c.getCount() > 0) {
            logoLinks[0] = c.getString(0);
            logoLinks[1] = c.getString(1);
            logoLinks[2] = c.getString(2);
            logoLinks[3] = c.getString(3);
            logoLinks[4] = c.getString(4);
            logoLinks[5] = c.getString(5);
            c.moveToNext();
        }
        c.close();
        return logoLinks;
    }

    public String getQuestion(int category, int questionNumber, int langId) {
        Log.d(DEBUG_TAG, "Getting question from DB");
        String question = "";

        String query = "SELECT " + QuestionT.getColQuestion() + " FROM " + QuestionT.getTbName()
                + " WHERE " + QuestionT.getColLangId() + " = " + Integer.toString(langId) + " AND "
                + QuestionT.getColQuestionId() + " = ( SELECT " + Question.getColId() + " FROM " + Question.getTbName()
                + " WHERE " + Question.getColCategoryId() + " = " + Integer.toString(category) + " AND " + Question.getColQuestionNum() + " = " + Integer.toString(questionNumber) + " )";

        DB = this.getReadableDatabase();
        Cursor c = DB.rawQuery("SELECT " + QuestionT.getColQuestion() + " FROM " + QuestionT.getTbName()
                + " WHERE " + QuestionT.getColLangId() + " = ? AND "
                + QuestionT.getColQuestionId() + " = ( SELECT " + Question.getColId() + " FROM " + Question.getTbName()
                + " WHERE " + Question.getColCategoryId() + " = ? AND " + Question.getColQuestionNum() + " = ? )"
                , new String[] { Integer.toString(langId), Integer.toString(category), Integer.toString(questionNumber) });

        Log.d(DEBUG_TAG, "Question: " + query);

        c.moveToFirst();
        if (c.getCount() > 0)
            question = c.getString(0);
        c.close();

        return question;
    }

    public int getCorrectAnswer(int category, int questionNumber) {
        Log.d(DEBUG_TAG, "Getting correct answer from DB");
        int correctAnswer = 0;

        DB = this.getReadableDatabase();
        Cursor c = DB.rawQuery("SELECT " + Question.getColCorrectAnswer() + " FROM " + Question.getTbName()
                + " WHERE " + Question.getColCategoryId() + " = ? AND " + Question.getColQuestionNum() + " = ?"
                , new String[] { Integer.toString(category), Integer.toString(questionNumber) });

        c.moveToFirst();
        if (c.getCount() > 0)
            correctAnswer = c.getInt(0);
        c.close();

        return correctAnswer;
    }

    public boolean saveAnswer(int attemptCntr, int category, int questionNumber, int answerGiven, String time) {
        Log.d(DEBUG_TAG, "Saving given answer to DB");

        DB = this.getWritableDatabase();
        String query = "INSERT INTO " + Answer.getTbName() + " VALUES ("
                +        attemptCntr
                + ", " + category
                + ", " + questionNumber
                + ", " + answerGiven
                + ", '" + time + "'"
                + ")";
        DB.execSQL(query);

        Log.d(DEBUG_TAG, "Saving answer; query: " + query);

        return true;
    }

    public int getAttemptsCntr(int category) {
        Log.d(DEBUG_TAG, "Getting attempts counter from DB");
        int cntr = 0;

        DB = this.getReadableDatabase();
        Cursor c = DB.rawQuery("SELECT MAX(" + Answer.getColAttemptCntr() + ") FROM " + Answer.getTbName()
                + " WHERE " + Answer.getColCategoryId() + " = ?"
                , new String[] { Integer.toString(category) });

        c.moveToFirst();
        if (c.getCount() > 0)
            cntr = c.getInt(0);
        c.close();

        return cntr;
    }
}
