package com.example.tm__mt.ecoquiz;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import java.util.List;

/**
 * Created by tm__mt
 *
 * Displays all available categories.
 *
 * If any category was clicked then quiz should be started (first question is shown on a screen)
 */
public class CategoryActivity extends ListActivity {
    private static final String DEBUG_TAG = "CategoryActivity";

    private EcoQuizDBHelper DBHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        Log.d(DEBUG_TAG, "Creating CategoryActivity...");

        DBHelper = new EcoQuizDBHelper(this);
        List<String> categoriesList  = DBHelper.getCategories(ApplicationSettings.getLanguage());

        ArrayAdapter<String> myAdapter = new ArrayAdapter <>(this,
                R.layout.category_row_item, R.id.tvCategoryName, categoriesList);
        setListAdapter(myAdapter);
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        //Log.d(DEBUG_TAG, position + ". list item was clicked");

        int questionNumber = 1;
        SingleQuestionData nextQuestion = new SingleQuestionData(questionNumber,
                                                     DBHelper.getCategoryId(position),
                                                     ApplicationSettings.getLanguage(),
                                                     ApplicationSettings.getScreenDensity());

        if (nextQuestion.prepareFirst(CategoryActivity.this)) {
            Intent i;
            if (ApplicationSettings.getPathSource() == ApplicationSettings.PATH_SRC_LOCAL)
                i = new Intent(CategoryActivity.this, QuestionActivity.class);
            else
                i = new Intent(CategoryActivity.this, PreQuestionActivity.class);

            i.putExtra("questionData", nextQuestion);
            startActivity(i);
        } else {
            Toast.makeText(CategoryActivity.this, R.string.category_without_questions, Toast.LENGTH_SHORT).show();
        }
    }
}
