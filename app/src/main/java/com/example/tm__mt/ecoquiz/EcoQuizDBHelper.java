package com.example.tm__mt.ecoquiz;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
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

        db.execSQL(TLang.getCreateQuery());
        Log.d(DEBUG_TAG, "Table " + TLang.getTableName() + " created!");

        db.execSQL(TCategory.getCreateQuery());
        Log.d(DEBUG_TAG, "Table " + TCategory.getTableName() + " created.");

        db.execSQL(TCategoryTranslation.getCreateQuery());
        Log.d(DEBUG_TAG, "Table " + TCategoryTranslation.getTableName() + " created.");

        db.execSQL(TQuestion.getCreateQuery());
        Log.d(DEBUG_TAG, "Table " + TQuestion.getTableName() + " created.");

        db.execSQL(TQuestionTranslation.getCreateQuery());
        Log.d(DEBUG_TAG, "Table " + TQuestionTranslation.getTableName() + " created.");

        db.execSQL(TQuestionWebPath.getCreateQuery());
        Log.d(DEBUG_TAG, "Table " + TQuestionWebPath.getTableName() + " created.");

        //db.execSQL(TQuestionLocalPath.getCreateQuery());
        //Log.d(DEBUG_TAG, "Table " + TQuestionLocalPath.getTableName() + " created.");

        db.execSQL(TAnswer.getCreateQuery());
        Log.d(DEBUG_TAG, "Table " + TAnswer.getTableName() + " created.");

        db.execSQL(TRanking.getCreateQuery());
        Log.d(DEBUG_TAG, "Table " + TRanking.getTableName() + " created.");

        if (!initializeTablesContent(db)) {
            Log.e(DEBUG_TAG, "Error. Not all data are inserted into tables!!!");
        } else {
            Log.d(DEBUG_TAG, "Database " + DB_NAME + " created!!!");
        }

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
    }

    public String[] getLogoLinks(int category, int questionNumber, int langId) {
        Log.d(DEBUG_TAG, "Getting logo links from DB");
        String[] logoLinks = new String[6];

        String query;

        if (true) {
            //gets logo links from web
            query = "SELECT "
                    +              TQuestionWebPath.getColPath1()
                    + ", "       + TQuestionWebPath.getColPath2()
                    + ", "       + TQuestionWebPath.getColPath3()
                    + ", "       + TQuestionWebPath.getColPath4()
                    + ", "       + TQuestionWebPath.getColPath5()
                    + ", "       + TQuestionWebPath.getColPath6()
                    + " FROM "
                    +              TQuestionWebPath.getTableName() + " t1 "
                    + ", "       + TQuestion.getTableName() + " t2 "
                    + " WHERE "
                    +     " t2." + TQuestion.getColId() + "=t1." + TQuestionWebPath.getColQuestionId()
                    + " AND t2." + TQuestion.getColCategoryId() + "=?"
                    + " AND t2." + TQuestion.getColQuestionNum() + "=?";
        }

        DB = this.getReadableDatabase();
        //Cursor c = DB.rawQuery("SELECT AD1PATH, AD2PATH, AD3PATH, AD4PATH, AD5PATH, AD6PATH FROM " + TQuestionTranslation.getTableName()
        //        + " WHERE " + TQuestionTranslation.getColLangId() + " = ? AND " + TQuestionTranslation.getColQuestionId()
        //        + " = ( SELECT " + TQuestion.getColId() + " FROM " + TQuestion.getTableName()
        //        + " WHERE " + TQuestion.getColCategoryId() + " = ? AND " + TQuestion.getColQuestionNum() + " = ? )"
        //        , new String[] { Integer.toString(langId), Integer.toString(category), Integer.toString(questionNumber) });
        Cursor c = DB.rawQuery(query, new String[] { Integer.toString(category), Integer.toString(questionNumber) });

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

        String query = "SELECT " + TQuestionTranslation.getColQuestion() + " FROM " + TQuestionTranslation.getTableName()
                + " WHERE " + TQuestionTranslation.getColLangId() + " = " + Integer.toString(langId) + " AND "
                + TQuestionTranslation.getColQuestionId() + " = ( SELECT " + TQuestion.getColId() + " FROM " + TQuestion.getTableName()
                + " WHERE " + TQuestion.getColCategoryId() + " = " + Integer.toString(category) + " AND " + TQuestion.getColQuestionNum() + " = " + Integer.toString(questionNumber) + " )";

        DB = this.getReadableDatabase();
        Cursor c = DB.rawQuery("SELECT " + TQuestionTranslation.getColQuestion() + " FROM " + TQuestionTranslation.getTableName()
                + " WHERE " + TQuestionTranslation.getColLangId() + " = ? AND "
                + TQuestionTranslation.getColQuestionId() + " = ( SELECT " + TQuestion.getColId() + " FROM " + TQuestion.getTableName()
                + " WHERE " + TQuestion.getColCategoryId() + " = ? AND " + TQuestion.getColQuestionNum() + " = ? )"
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
        Cursor c = DB.rawQuery("SELECT " + TQuestion.getColCorrectAnswer() + " FROM " + TQuestion.getTableName()
                + " WHERE " + TQuestion.getColCategoryId() + " = ? AND " + TQuestion.getColQuestionNum() + " = ?"
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
        String query =   "INSERT INTO " + TAnswer.getTableName() + " VALUES ("
                +         attemptCntr
                + ", "  + category
                + ", "  + questionNumber
                + ", "  + answerGiven
                + ", '" + time + "'"
                + ")";
        DB.execSQL(query);

        return true;
    }

    public int getAttemptsCntr(int category) {
        Log.d(DEBUG_TAG, "Getting attempts counter from DB");
        int cntr = 0;

        DB = this.getReadableDatabase();
        Cursor c = DB.rawQuery(
                "SELECT MAX(" + TAnswer.getColAttemptCntr() + ") "
              +  " FROM " + TAnswer.getTableName()
              + " WHERE " + TAnswer.getColCategoryId() + " = ?"
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
                "SELECT TQ." + TQuestion.getColQuestionNum()
                   + ", TA." + TAnswer.getColGivenAnswer()
                   + ", TQ." + TQuestion.getColCorrectAnswer()
                   + ", TA." + TAnswer.getColAnswerTime()
                + " FROM "
                             + TAnswer.getTableName() + " TA "
                   + ", "    + TQuestion.getTableName() + " TQ"
                + " WHERE "
                       + " TA." + TAnswer.getColCategoryId() + " = ?"
                   + " AND TA." + TAnswer.getColAttemptCntr() + " = ?"
                   + " AND TA." + TAnswer.getColCategoryId() + " = TQ." + TQuestion.getColCategoryId()
                   + " AND TA." + TAnswer.getColQuestionNum() + " = TQ." + TQuestion.getColQuestionNum()
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
                "SELECT "      + TCategoryTranslation.getColCategoryId() + " "
                        + ", " + TCategoryTranslation.getColName() + " "
              + "FROM "
                               + TCategoryTranslation.getTableName() + " "
              + "WHERE "
                               + TCategoryTranslation.getColLangId() + " = ?"
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
                           + TRanking.getColName()
                    + ", " + TRanking.getColScore()
                    + ", " + TRanking.getColTime()
                    + ", " + TRanking.getColAttemptCntr()
              + " FROM "
                           + TRanking.getTableName()
              + " WHERE "
                        + TRanking.getColCategoryId() + " = ?"
              + " ORDER BY " + TRanking.getColScore() + " DESC, " + TRanking.getColTime() + " ASC"
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
                       + TQuestion.getTableName() + " TQ "
                + ", " + TAnswer.getTableName() + " TA "
              + "WHERE "
                    + "TQ." + TQuestion.getColCategoryId() + " = TA." + TAnswer.getColCategoryId() + " "
                + "AND TQ." + TQuestion.getColQuestionNum() + " = TA." + TAnswer.getColQuestionNum() + " "
                + "AND TA." + TAnswer.getColCategoryId() + " = ? "
                + "AND TA." + TAnswer.getColAttemptCntr() + " = ? "
                + "AND TQ." + TQuestion.getColCorrectAnswer() + " = TA." + TAnswer.getColGivenAnswer();

        Cursor c = DB.rawQuery(query, new String[] { Integer.toString(categoryId), Integer.toString(attemptNr) });
        c.moveToFirst();
        if (c.getCount() > 0) {
            score = c.getInt(0);
        }
        c.close();

        String time = "00:00:00.000";
        query =
                "SELECT MAX(" + TAnswer.getColAnswerTime() + ") "
              + "FROM "
                       + TAnswer.getTableName() + " "
              + "WHERE "
                       + TAnswer.getColCategoryId() + " = ? "
              + "AND " + TAnswer.getColAttemptCntr() + " = ?";
        c = DB.rawQuery(query, new String[] { Integer.toString(categoryId), Integer.toString(attemptNr) });
        c.moveToFirst();
        if (c.getCount() > 0) {
            time = c.getString(0);
        }
        c.close();

        DB = this.getWritableDatabase();
        query = "INSERT INTO " + TRanking.getTableName() + " VALUES ("
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
                "SELECT TCT." + TCategoryTranslation.getColName() + " "
                   + ", TCT." + TCategoryTranslation.getColCategoryId() + " "
              + "FROM "
                          + TCategoryTranslation.getTableName() + " TCT "
                   + ", " + TCategory.getTableName() + " TC "
                   + ", " + TLang.getTableName() + " TL "
              + "WHERE "
                   +     "TCT." + TCategoryTranslation.getColCategoryId() + " = TC." + TCategory.getColId() + " "
                   + "AND TCT." + TCategoryTranslation.getColLangId() + " = TL." + TLang.getColId() + " "
                   + "AND TCT." + TCategoryTranslation.getColLangId() + " = ? ";

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

    private boolean initializeTablesContent(SQLiteDatabase db) {
        String query;
        int i;
        int cntr;
        //--------------------------------------------------------------------------------Lang table
        int langNumber = 2;
        String langContent[] = {"en", "pl"};
        i = 1;
        TLang tl;
        for (; i<=langNumber && langContent.length==langNumber; i++) {
            tl = new TLang(i, langContent[i-1]);
            query = tl.insertQuery();
            if (query == null)
                break;

            db.execSQL(query);
        }
        if ( i <= langNumber ) {
            Log.e(DEBUG_TAG, "Error while inserting values into " + TLang.getTableName() + " table!");
            return false;
        } else
            Log.d(DEBUG_TAG, "Values inserted into " + TLang.getTableName() + " table!");
        //----------------------------------------------------------------------------Category table
        int categoryNumber = 3;
        String categoryContent[] = {"Food", "Environment", "Organizations"};
        i = 1;
        TCategory tc;
        for (; i<=categoryNumber && categoryContent.length==categoryNumber; i++) {
            tc = new TCategory(i, categoryContent[i-1]);
            query = tc.insertQuery();
            if (query == null)
                break;

            db.execSQL(query);
        }
        if ( i <= categoryNumber ) {
            Log.e(DEBUG_TAG, "Error while inserting values into " + TCategory.getTableName() + " table!");
            return false;
        } else
            Log.d(DEBUG_TAG, "Values inserted into " + TCategory.getTableName() + " table!");
        //-----------------------------------------------------------------CategoryTranslation table
        int categoryTranslationNumber = categoryNumber * langNumber;
        String categoryTranslationContent[][] = {
                 {"Food",           "Jedzenie"}
                ,{"Environment",    "Środowisko"}
                ,{"Organizations",  "Organizacje"}
        };
        i = 1;
        cntr = 0;
        TCategoryTranslation tct;
        for (; i<=categoryNumber; i++) {
            for (int j=1; j<=langNumber; j++) {
                tct = new TCategoryTranslation(i, j, categoryTranslationContent[i-1][j-1]);
                query = tct.insertQuery();
                Log.d(DEBUG_TAG, query);
                if (query == null)
                    break;

                db.execSQL(query);
                cntr++;
            }
        }
        if ( cntr != categoryTranslationNumber ) {
            Log.e(DEBUG_TAG, "Error while inserting values into " + TCategoryTranslation.getTableName() + " table!");
            return false;
        } else
            Log.d(DEBUG_TAG, "Values inserted into " + TCategoryTranslation.getTableName() + " table!");
        //----------------------------------------------------------------------------Question table
        int questionNumber = TQuestion.getValuesNumber();
        i = 1;
        for (; i<=questionNumber; i++) {
                query = TQuestion.insertQuery(i-1);
                if (query == null)
                    break;

                db.execSQL(query);
        }
        if ( i <= questionNumber ) {
            Log.e(DEBUG_TAG, "Error while inserting values into " + TQuestion.getTableName() + " table!");
            return false;
        } else
            Log.d(DEBUG_TAG, "Values inserted into " + TQuestion.getTableName() + " table!");
        //-----------------------------------------------------------------QuestionTranslation table
        int questionTranslationNumber = TQuestionTranslation.getValuesNumber();
        if ( questionTranslationNumber != questionNumber*langNumber ) {
            Log.e(DEBUG_TAG, "Wrong number of records to be inserted into " + TQuestion.getTableName() + " table!");
            return false;
        }
        i = 1;
        for (; i<=questionTranslationNumber; i++) {
            query = TQuestionTranslation.insertQuery(i-1);
            if (query == null)
                break;

            db.execSQL(query);
        }
        if ( i <= questionTranslationNumber ) {
            Log.e(DEBUG_TAG, "Error while inserting values into " + TQuestionTranslation.getTableName() + " table!");
            return false;
        } else
            Log.d(DEBUG_TAG, "Values inserted into " + TQuestionTranslation.getTableName() + " table!");
        //-----------------------------------------------------------------QuestionTranslation table
        int questionWebPathNumber = TQuestionWebPath.getValuesNumber();
        if ( questionWebPathNumber != questionNumber ) {
            Log.e(DEBUG_TAG, "Wrong number of records to be inserted into " + TQuestionWebPath.getTableName() + " table!");
            return false;
        }
        i = 1;
        for (; i<=questionWebPathNumber; i++) {
            query = TQuestionWebPath.insertQuery(i-1);
            if (query == null)
                break;

            db.execSQL(query);
        }
        if ( i <= questionWebPathNumber ) {
            Log.e(DEBUG_TAG, "Error while inserting values into " + TQuestionWebPath.getTableName() + " table!");
            return false;
        } else
            Log.d(DEBUG_TAG, "Values inserted into " + TQuestionWebPath.getTableName() + " table!");


        return true;
    }
}