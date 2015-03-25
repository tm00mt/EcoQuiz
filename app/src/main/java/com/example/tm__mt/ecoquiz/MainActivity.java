package com.example.tm__mt.ecoquiz;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.Locale;

public class MainActivity extends ActionBarActivity {

    private static final String DEBUG_TAG = "EcoQuizMain";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d(DEBUG_TAG, "Creating MainActivity....");


        //test utworzenia bazy danych, do usunięcia!!!!!!!!!!!!!!!!!!!
        //tv = (TextView) findViewById(R.id.tv1);
        //EcoQuizDBHelper DBHelper = new EcoQuizDBHelper(this);
        //String[] logos = DBHelper.getLogoLinks(1,1,1);
        //tv.setText(logos[0] + "<>" + logos[1] + "<>" + logos[2]);
    }


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

    public void openQuestion(View view) {
        Intent i = new Intent(this, CategoryActivity.class);
        startActivity(i);
    }
}
