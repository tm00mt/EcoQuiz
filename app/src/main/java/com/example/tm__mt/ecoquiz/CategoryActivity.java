package com.example.tm__mt.ecoquiz;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.Locale;

public class CategoryActivity extends ActionBarActivity {
    private static final String DEBUG_TAG = "CategoryActivity";

    TextView tvCategory1, tvCategory2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actegory);

        Log.d(DEBUG_TAG, "Creating CategoryActivity...");

        tvCategory1 = (TextView) findViewById(R.id.tvCategory1);
        tvCategory2 = (TextView) findViewById(R.id.tvCategory2);

        tvCategory1.setOnClickListener(categoryClickListener);
        tvCategory2.setOnClickListener(categoryClickListener);

    }

    private View.OnClickListener categoryClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {

            NextQuestion nextQuestion;

            int l = 1;
            if ( Locale.getDefault().getISO3Language().equals("pol") )
                l = 2;

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

            if (nextQuestion.prepare(CategoryActivity.this)) {
                Log.d(DEBUG_TAG, "categoryClickListener; Starting PreQuestionActivity...");
                Intent i = new Intent(CategoryActivity.this, PreQuestionActivity.class);
                i.putExtra("questionData", nextQuestion);
                startActivity(i);
            } else {
                //todo nie podano wszystkich danych do zapytania SQL
                //lub zapytanie zwróciło pusty wynik-> obsłużyć sytuację
            }
        }
    };
}
