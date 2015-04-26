package com.example.tm__mt.ecoquiz;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.Editable;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The MySQLiteHelper class provides access to operations on internal DB
 * used in EcoQuiz application.
 */
public class EcoQuizDBHelper extends SQLiteOpenHelper {
    private static final String DEBUG_TAG = "MySQLiteHelper";

    private static final String DB_NAME = "EcoQuizDB";
    private static final int DB_VERSION = 1;
    private SQLiteDatabase DB;

    private ArrayList<ResultRow> resultDataRR;
    private ArrayList<Integer> correctAnswers;
    private ArrayList<RankingRow> rankingDataRR;
    private List<String> categoryNames;
    private List<Integer> categoryIds;

    private Map categories;

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

        db.execSQL(CategoryT.getCreateQuery());
        db.execSQL(CategoryT.getInsertQuery());
        Log.d(DEBUG_TAG, "Table " + CategoryT.getTbName() + " created.");

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

    public int prepareResultData(int category, int attempt) {
        Log.d(DEBUG_TAG, "Preparing data from DB to display results");

        int cntr = 0;

        DB = this.getReadableDatabase();
        Cursor c = DB.rawQuery(
                "SELECT TQ." + Question.getColQuestionNum()
                   + ", TA." + Answer.getColGivenAnswer()
                   + ", TQ." + Question.getColCorrectAnswer()
                   + ", TA." + Answer.getColAnswerTime()
                + " FROM "
                             + Answer.getTbName() + " TA "
                   + ", "    + Question.getTbName() + " TQ"
                + " WHERE "
                       + " TA." + Answer.getColCategoryId() + " = ?"
                   + " AND TA." + Answer.getColAttemptCntr() + " = ?"
                   + " AND TA." + Answer.getColCategoryId() + " = TQ." + Question.getColCategoryId()
                   + " AND TA." + Answer.getColQuestionNum() + " = TQ." + Question.getColQuestionNum()
                , new String[] { Integer.toString(category), Integer.toString(attempt) });

        c.moveToFirst();
        if (c.getCount() > 0) {
            ResultRow rr;
            resultDataRR = new ArrayList<>();
            correctAnswers = new ArrayList<>();
            while (!c.isAfterLast()) {
                Log.d(DEBUG_TAG, "Cursor: " + c.getInt(0) + " " + c.getInt(1) + " " + c.getInt(2) + " " + c.getString(3));
                rr = new ResultRow(c.getInt(0), c.getInt(1), c.getString(3));
                resultDataRR.add(rr);
                correctAnswers.add(c.getInt(2));
                c.moveToNext();
                cntr++;
            }
        }
        c.close();

        return cntr;
    }

    public ResultRow getResultDataRR(int i) {
        return resultDataRR.get(i);
    }

    public int getResultDataCA(int i) {
        return correctAnswers.get(i);
    }

    public int prepareCategoryOptions(int lang) {
        Log.d(DEBUG_TAG, "Preparing available categories in DB...");

        int cntr = 0;

        DB = this.getReadableDatabase();
        Cursor c = DB.rawQuery(
                "SELECT "      + CategoryT.getColCategoryId() + " "
                        + ", " + CategoryT.getColName() + " "
              + "FROM "
                               + CategoryT.getTbName() + " "
              + "WHERE "
                               + CategoryT.getColLangId() + " = ?"
              , new String[] { Integer.toString(lang) });

        c.moveToFirst();
        if (c.getCount() > 0) {
            categories = new HashMap();
            while (!c.isAfterLast()) {
                Log.d(DEBUG_TAG, "Cursor: " + c.getInt(0) + " " + c.getInt(1));

                categories.put(c.getInt(0), c.getString(1));
                c.moveToNext();
                cntr++;
            }
        }
        c.close();

        return cntr;
    }

    public String getCategoryOption(int key) {
        return (String) categories.get(key);
    }

    public int prepareRankingtData(int categoryId) {
        Log.d(DEBUG_TAG, "Preparing data from DB to display ranking");

        int cntr = 0;

        DB = this.getReadableDatabase();
        Cursor c = DB.rawQuery(
                "SELECT "
                           + Ranking.getColName()
                    + ", " + Ranking.getColScore()
                    + ", " + Ranking.getColTime()
                    + ", " + Ranking.getColAttemptCntr()
              + " FROM "
                           + Ranking.getTbName()
              + " WHERE "
                        + Ranking.getColCategoryId() + " = ?"
              + " ORDER BY " + Ranking.getColScore() + " DESC, " + Ranking.getColTime() + " ASC"
              , new String[] { Integer.toString(categoryId) });

        c.moveToFirst();
        if (c.getCount() > 0) {
            RankingRow rr;
            rankingDataRR = new ArrayList<>();
            while (!c.isAfterLast()) {
                Log.d(DEBUG_TAG, "Cursor: " + c.getString(0) + " " + c.getInt(1) + " " + c.getString(2) + " " + c.getInt(3));
                rr = new RankingRow(c.getString(0), c.getInt(1), c.getString(2), c.getInt(3));
                rankingDataRR.add(rr);
                c.moveToNext();
                cntr++;
            }
        }
        c.close();

        return cntr;
    }

    public RankingRow getRankingDataRR(int i) {
        return rankingDataRR.get(i);
    }

    public void insertIntoRanking(int categoryId, int attemptNr, String name) {
        Log.d(DEBUG_TAG, "Saving new row in Ranking table");

        int score = 0;
        DB = this.getReadableDatabase();
        String query =
                "SELECT COUNT(*) "
              + "FROM "
                       + Question.getTbName() + " TQ "
                + ", " + Answer.getTbName() + " TA "
              + "WHERE "
                    + "TQ." + Question.getColCategoryId() + " = TA." + Answer.getColCategoryId() + " "
                + "AND TQ." + Question.getColQuestionNum() + " = TA." + Answer.getColQuestionNum() + " "
                + "AND TA." + Answer.getColCategoryId() + " = ? "
                + "AND TA." + Answer.getColAttemptCntr() + " = ? "
                + "AND TQ." + Question.getColCorrectAnswer() + " = TA." + Answer.getColGivenAnswer();

        Cursor c = DB.rawQuery(query, new String[] { Integer.toString(categoryId), Integer.toString(attemptNr) });
        c.moveToFirst();
        if (c.getCount() > 0) {
            score = c.getInt(0);
        }
        c.close();

        String time = "00:00:00.000";
        query =
                "SELECT MAX(" + Answer.getColAnswerTime() + ") "
              + "FROM "
                       + Answer.getTbName() + " "
              + "WHERE "
                       + Answer.getColCategoryId() + " = ? "
              + "AND " + Answer.getColAttemptCntr() + " = ?";
        c = DB.rawQuery(query, new String[] { Integer.toString(categoryId), Integer.toString(attemptNr) });
        c.moveToFirst();
        if (c.getCount() > 0) {
            time = c.getString(0);
        }
        c.close();

        DB = this.getWritableDatabase();
        query = "INSERT INTO " + Ranking.getTbName() + " VALUES ("
                +         attemptNr
                + ", "  + categoryId
                + ", '" + name + "'"
                + ", "  + score
                + ", '" + time + "'"
                + ")";
        DB.execSQL(query);

        Log.d(DEBUG_TAG, "Saving new Ranking row; query: " + query);
    }

    public List<String> getCategories(int language) {
        categoryNames =  new ArrayList<>();
        categoryIds = new ArrayList<>();

        DB = this.getReadableDatabase();
        String query =
                "SELECT TCT." + CategoryT.getColName() + " "
                   + ", TCT." + CategoryT.getColCategoryId() + " "
              + "FROM "
                          + CategoryT.getTbName() + " TCT "
                   + ", " + Category.getTbName() + " TC "
                   + ", " + Lang.getTbName() + " TL "
              + "WHERE "
                   +     "TCT." + CategoryT.getColCategoryId() + " = TC." + Category.getColId() + " "
                   + "AND TCT." + CategoryT.getColLangId() + " = TL." + Lang.getColId() + " "
                   + "AND TCT." + CategoryT.getColLangId() + " = ? ";

        Cursor c = DB.rawQuery(query, new String[] { Integer.toString(language)});
        c.moveToFirst();
        if (c.getCount() > 0) {
            while (!c.isAfterLast()) {
                Log.d(DEBUG_TAG, "Category: " + c.getString(0));
                categoryNames.add(c.getString(0));
                categoryIds.add(c.getInt(1));
                c.moveToNext();
            }
        }
        c.close();

        return categoryNames;
    }

    public int getCategoryId(int position) {
        if (!categoryIds.isEmpty() && position >= 0 && position < categoryIds.size())
            return categoryIds.get(position);

        return -1;
    }
}