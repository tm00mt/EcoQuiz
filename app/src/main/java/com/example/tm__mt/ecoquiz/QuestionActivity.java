package com.example.tm__mt.ecoquiz;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.NinePatchDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by tm__mt
 *
 * Displays single question on a screen and prepares next question data for next QuestionActivity
 *
 */
public class QuestionActivity extends Activity {
    private static final String DEBUG_TAG = "QuestionActivity";

    private static final int LOGOS_NUM = 6;

    private LogoDownloader[] logos = new LogoDownloader[LOGOS_NUM];
    private boolean logosLoaded    = false;
    private boolean answerClicked  = false;
    private boolean isLastQuestion = false;

    private long startTime   = 0;
    //private long endTime     = 0;
    private long elapsedTime = 0;

    TextView    tvQuestion;
    FrameLayout flBorder;
    boolean     doHighlight;
    int         answerGiven;
    ImageView ivLogo1, ivLogo2, ivLogo3, ivLogo4, ivLogo5, ivLogo6;
    SingleQuestionData currentQuestion, nextQuestion;

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
        //do a lot of stuff to display 9patch logos on a screen
        NinePatchDrawable npd;
        Bitmap bm;
        bm = BitmapFactory.decodeFile(currentQuestion.getBitmapPath(0));
        npd = NinePatchBitmapFactory.createNinePatchDrawable(getResources(), bm);
        ivLogo1.setBackground(npd);
        bm = BitmapFactory.decodeFile(currentQuestion.getBitmapPath(1));
        npd = NinePatchBitmapFactory.createNinePatchDrawable(getResources(), bm);
        ivLogo2.setBackground(npd);
        bm = BitmapFactory.decodeFile(currentQuestion.getBitmapPath(2));
        npd = NinePatchBitmapFactory.createNinePatchDrawable(getResources(), bm);
        ivLogo3.setBackground(npd);
        bm = BitmapFactory.decodeFile(currentQuestion.getBitmapPath(3));
        npd = NinePatchBitmapFactory.createNinePatchDrawable(getResources(), bm);
        ivLogo4.setBackground(npd);
        bm = BitmapFactory.decodeFile(currentQuestion.getBitmapPath(4));
        npd = NinePatchBitmapFactory.createNinePatchDrawable(getResources(), bm);
        ivLogo5.setBackground(npd);
        bm = BitmapFactory.decodeFile(currentQuestion.getBitmapPath(5));
        npd = NinePatchBitmapFactory.createNinePatchDrawable(getResources(), bm);
        ivLogo6.setBackground(npd);

        //set starting time of timer
        startTime = SystemClock.elapsedRealtime();

        //prepare next question
        nextQuestion = new SingleQuestionData(
                currentQuestion.getQuestionNumber()+1,
                currentQuestion.getCategory(),
                currentQuestion.getLang(),
                currentQuestion.getLogoSize());
        nextQuestion.setAttemptCntr(currentQuestion.getAttemptCntr());

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
            long endTime = SystemClock.elapsedRealtime();

            answerClicked = true;
            answerGiven = 0;
            elapsedTime = currentQuestion.getPrevQuestionTime() + endTime - startTime;

            switch (v.getId()) {
                case R.id.ivLogo1:
                    answerGiven = 1;
                    flBorder = (FrameLayout) findViewById(R.id.flLogoContainer1);
                    break;
                case R.id.ivLogo2:
                    answerGiven = 2;
                    flBorder = (FrameLayout) findViewById(R.id.flLogoContainer2);
                    break;
                case R.id.ivLogo3:
                    answerGiven = 3;
                    flBorder = (FrameLayout) findViewById(R.id.flLogoContainer3);
                    break;
                case R.id.ivLogo4:
                    answerGiven = 4;
                    flBorder = (FrameLayout) findViewById(R.id.flLogoContainer4);
                    break;
                case R.id.ivLogo5:
                    answerGiven = 5;
                    flBorder = (FrameLayout) findViewById(R.id.flLogoContainer5);
                    break;
                case R.id.ivLogo6:
                    answerGiven = 6;
                    flBorder = (FrameLayout) findViewById(R.id.flLogoContainer6);
                    break;
            }

            //save given answer in DB
            currentQuestion.saveAnswer(answerGiven, elapsedTime, QuestionActivity.this);

            //show message if answer is (not) correct
            if (currentQuestion.getCorrectAnswer() == answerGiven) {
                Toast.makeText(QuestionActivity.this, R.string.string_answer_correct, Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(QuestionActivity.this, R.string.string_answer_wrong, Toast.LENGTH_SHORT).show();
            }

            // blink a border of clicked logo to indicate if it was good or bad answer...
            int timeInMilis = 300;
            int howManyBlinks = 5;
            doHighlight = true;
            blinkABorder(howManyBlinks, timeInMilis);

            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                public void run() {
                    // ... and then start next activity
                    if (isLastQuestion) {
                        Log.d(DEBUG_TAG, "ResultActivity started when logo was clicked");
                        startResultActivity();
                    } else if (logosLoaded) {
                        Log.d(DEBUG_TAG, "Next QuestionActivity started when logo was clicked");
                        startNextQuestionActivity();
                    }
                }
            }, howManyBlinks * timeInMilis);
        }

        void blinkABorder(int howManyTimes, int timeInMilis) {
            if (howManyTimes == 0)
                return;

            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                public void run() {
                    if (doHighlight) {
                        if (currentQuestion.getCorrectAnswer() == answerGiven) {
                            flBorder.setBackground(getResources().getDrawable(R.drawable.question_logo_border_ok));
                        } else {
                            flBorder.setBackground(getResources().getDrawable(R.drawable.question_logo_border_nok));
                        }
                    } else {
                        flBorder.setBackground(getResources().getDrawable(R.drawable.question_logo_border));
                    }
                    flBorder.invalidate();

                    doHighlight = !doHighlight;
                }
            }, howManyTimes * timeInMilis);

            blinkABorder(howManyTimes - 1, timeInMilis);
        }
    };

    private class LogoStatusChecker extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {

            while (true) {
                int n = 0;
                for (int i=0; i<LOGOS_NUM; i++) {
                    //todo zrobić podobnie jak w PreQuestionActivity
                    if (logos[i].isLogoSaved(false))
                        n++;
                }

                Log.d(DEBUG_TAG, "Logos downloaded: " + n);
                if (n >= 6) {
                    logosLoaded = true;
                    break;
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            for (int i = 0; i < LOGOS_NUM; i++)
                nextQuestion.setBitmapPath(logos[i].getLogoPath(), i);

            if (logosLoaded && answerClicked) {
                Log.d(DEBUG_TAG, "Next QuestionActivity started when downloading logos has been finished");
                startNextQuestionActivity();
            }
        }
    }

    private void startNextQuestionActivity() {
        nextQuestion.setPrevQuestionTime(elapsedTime);
        Intent intent = new Intent(getApplicationContext(), QuestionActivity.class);
        intent.putExtra("questionData", nextQuestion);
        startActivity(intent);
        finish();
    }

    private void startResultActivity() {
        Intent i = new Intent(getApplicationContext(), ResultActivity.class);
        i.putExtra("categoryNumber", currentQuestion.getCategory());
        i.putExtra("attemptNumber", currentQuestion.getAttemptCntr());
        startActivity(i);
        finish();
    }
}
