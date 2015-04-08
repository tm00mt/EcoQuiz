package com.example.tm__mt.ecoquiz;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import java.util.Locale;

public class CategoryActivity extends ActionBarActivity {
    private static final String DEBUG_TAG = "CategoryActivity";

    TextView tvCategory1, tvCategory2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        Log.d(DEBUG_TAG, "Creating CategoryActivity...");

        //todo content of this activity should be generated dynamicly
        //prepare SQL query to select all categories from DB

        tvCategory1 = (TextView) findViewById(R.id.tvCategory1);
        tvCategory2 = (TextView) findViewById(R.id.tvCategory2);

        tvCategory1.setOnClickListener(categoryClickListener);
        tvCategory2.setOnClickListener(categoryClickListener);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        ApplicationSettings.setLanguage(Locale.getDefault().getISO3Language());
    }

    private View.OnClickListener categoryClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {

            int l = ApplicationSettings.getLanguage();
            NextQuestion nextQuestion;

            switch (v.getId()) {
                case R.id.tvCategory1:
                    nextQuestion = new NextQuestion(1,1,l,1);
                    break;
                case R.id.tvCategory2:
                    nextQuestion = new NextQuestion(1,2,l,1);
                    break;
                default:
                    nextQuestion = new NextQuestion(1,1,1,1);
            }

            if (nextQuestion.prepareFirst(CategoryActivity.this)) {
                Intent i = new Intent(CategoryActivity.this, PreQuestionActivity.class);
                i.putExtra("questionData", nextQuestion);
                startActivity(i);
            } else {
                Toast.makeText(CategoryActivity.this, "No (more) data in DB.", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    };
}
