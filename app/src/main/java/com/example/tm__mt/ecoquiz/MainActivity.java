package com.example.tm__mt.ecoquiz;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends Activity {
    private static final String DEBUG_TAG = "EcoQuizMain";

    TextView tvStartQuiz, tvResults;
    View decorView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d(DEBUG_TAG, "Creating MainActivity....");

        //new ApplicationSettings(getApplicationContext());

        //ApplicationSettings.setLanguage(Locale.getDefault().getISO3Language());

        tvStartQuiz = (TextView) findViewById(R.id.tvStartQuiz);
        tvResults = (TextView) findViewById(R.id.tvResults);

        tvStartQuiz.setOnClickListener(optionClickListener);
        tvResults.setOnClickListener(optionClickListener);

        //------------------------------------------
        decorView = getWindow().getDecorView();
        // Remember that you should never show the action bar if the
        // status bar is hidden, so hide that too if necessary.
        //ActionBar actionBar = getActionBar();
        //actionBar.hide();
        //-------------------------------------------
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Hide the status bar.
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
    }
    //-------------------------------------
    //@Override
    //public void onConfigurationChanged(Configuration newConfig) {
    //    super.onConfigurationChanged(newConfig);
    //
    //    ApplicationSettings.setLanguage(Locale.getDefault().getISO3Language());
    //}

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private View.OnClickListener optionClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            Intent i;
            switch (v.getId()) {
                case R.id.tvStartQuiz:
                    i = new Intent(MainActivity.this, CategoryActivity.class);
                    break;
                case R.id.tvResults:
                    i = new Intent(MainActivity.this, RankingActivity.class);
                    break;
                default:
                    i = null;
            }
            if (i != null)
                startActivity(i);
        }
    };
}
