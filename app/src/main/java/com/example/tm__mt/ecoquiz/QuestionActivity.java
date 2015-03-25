package com.example.tm__mt.ecoquiz;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class QuestionActivity extends ActionBarActivity {
    private static final String DEBUG_TAG = "QuestionActivity";
    private static final int LOGOS_NUM = 6;

    private LogoDownloader[] logos = new LogoDownloader[LOGOS_NUM];
    private boolean logosLoaded = false;
    private boolean answerClicked = false;
    private boolean isLastQuestion = false;

    TextView tvQuestion;
    ImageView ivLogo1, ivLogo2, ivLogo3, ivLogo4, ivLogo5, ivLogo6;
    NextQuestion currentQuestion, nextQuestion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        Log.d(DEBUG_TAG, "Creating QuestionActivity...");
        
        //prepare ImageViews to display logos; make them clickable
        ivLogo1 = (ImageView) findViewById(R.id.ivLogo1);
        ivLogo1.setOnClickListener(logoClickListener);
        ivLogo2 = (ImageView) findViewById(R.id.ivLogo2);
        ivLogo2.setOnClickListener(logoClickListener);
        ivLogo3 = (ImageView) findViewById(R.id.ivLogo3);
        ivLogo3.setOnClickListener(logoClickListener);
        ivLogo4 = (ImageView) findViewById(R.id.ivLogo4);
        ivLogo4.setOnClickListener(logoClickListener);
        ivLogo5 = (ImageView) findViewById(R.id.ivLogo5);
        ivLogo5.setOnClickListener(logoClickListener);
        ivLogo6 = (ImageView) findViewById(R.id.ivLogo6);
        ivLogo6.setOnClickListener(logoClickListener);

        //prepare TextView to display question
        tvQuestion = (TextView) findViewById(R.id.tvQuestion);

        //get question data from previous Activity
        Intent intent = getIntent();
        currentQuestion = intent.getParcelableExtra("questionData");

        //display question on a screen
        tvQuestion.setText(currentQuestion.getQuestion());

        //display logos on a screen
        ivLogo1.setImageBitmap(BitmapFactory.decodeFile(currentQuestion.getBitmapPath(0)));
        ivLogo2.setImageBitmap(BitmapFactory.decodeFile(currentQuestion.getBitmapPath(1)));
        ivLogo3.setImageBitmap(BitmapFactory.decodeFile(currentQuestion.getBitmapPath(2)));
        ivLogo4.setImageBitmap(BitmapFactory.decodeFile(currentQuestion.getBitmapPath(3)));
        ivLogo5.setImageBitmap(BitmapFactory.decodeFile(currentQuestion.getBitmapPath(4)));
        ivLogo6.setImageBitmap(BitmapFactory.decodeFile(currentQuestion.getBitmapPath(5)));

        //prepare next question
        nextQuestion = new NextQuestion(currentQuestion.getQuestionNumber()+1,
                currentQuestion.getCategory(),
                currentQuestion.getLang(),
                currentQuestion.getLogoSize());

        //check if next question exists
        if (nextQuestion.prepare(this)) {
            //download logos bitmaps from the server, save them in the internal memory
            for (int i=0; i<LOGOS_NUM; i++) {
                logos[i] = new LogoDownloader(i+1, this);
                logos[i].execute(nextQuestion.getLogoLink(i));
            }

            new LogoStatusChecker().execute();
        } else {
            isLastQuestion = true;
        }
    }

    private View.OnClickListener logoClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            answerClicked = true;

            //todo zapis do tabeli udzielonej odpowiedzi

            switch (v.getId()) {
                case R.id.ivLogo1:
                    break;
                case R.id.ivLogo2:
                    break;
                case R.id.ivLogo3:
                    break;
                case R.id.ivLogo4:
                    break;
                case R.id.ivLogo5:
                    break;
                case R.id.ivLogo6:
                    break;
            }

            if (isLastQuestion) {
                Intent i = new Intent(getApplicationContext(), ResultActivity.class);
                //i.putExtra("questionData", nextQuestion);
                startActivity(i);
                finish();
            } else if (logosLoaded) {
                Intent i = new Intent(getApplicationContext(), QuestionActivity.class);
                i.putExtra("questionData", nextQuestion);
                startActivity(i);
                finish();
            }
        }
    };

    private class LogoStatusChecker extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            Log.d(DEBUG_TAG, "doInBackground2 ");

            while (true) {
                int n = 0;
                for (int i=0; i<LOGOS_NUM; i++) {
                    if (logos[i].isLogoSaved())
                        n++;
                }

                Log.d(DEBUG_TAG, "while loop..." + n);
                if (n >= 6) {
                    logosLoaded = true;
                    break;
                }
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            Log.d(DEBUG_TAG, "onPostExecute2 ");
            for (int i = 0; i < LOGOS_NUM; i++)
                nextQuestion.setBitmapPath(logos[i].getLogoPath(), i);

            if (logosLoaded && answerClicked) {
                Intent intent = new Intent(getApplicationContext(), QuestionActivity.class);
                intent.putExtra("questionData", nextQuestion);
                startActivity(intent);
                finish();
            }
        }
    }
}
