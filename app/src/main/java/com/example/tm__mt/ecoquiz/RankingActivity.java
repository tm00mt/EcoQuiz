package com.example.tm__mt.ecoquiz;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class RankingActivity extends ListActivity {
    private static final String DEBUG_TAG = "RankingActivity";

    Spinner sCategories;
    EcoQuizDBHelper DBHelper = new EcoQuizDBHelper(this);
    RankingAdapter ra;
    int attemptNr;
    int categoryId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking);

        Log.d(DEBUG_TAG, "Creating RankingActivity....");

        int lang = 1;
        if ( Locale.getDefault().getISO3Language().equals("pol") )
            lang = 2;

        Intent intent = getIntent();
        categoryId = intent.getIntExtra("categoryId", 0);
        attemptNr = intent.getIntExtra("attemptNr", 0);
        if (categoryId != 0 && attemptNr != 0) {
                //show dialog box to type user name
                LayoutInflater layoutInflater = LayoutInflater.from(this);
                View customDialogView = layoutInflater.inflate(R.layout.dialog_user_name, null);
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
                alertDialogBuilder.setView(customDialogView);
                final EditText input = (EditText) customDialogView.findViewById(R.id.etTypeUserName);
                //alertDialogBuilder.setMessage("Type your name");
                alertDialogBuilder.setPositiveButton("Save",
                        new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {
                                DBHelper.insertIntoRanking(categoryId, attemptNr, input.getText().toString());
                                populateAdapter();
                                ra.notifyDataSetChanged();
                                //finish();
                            }
                        });
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
        }

        if (categoryId == 0)
            categoryId = 1;

        final List<String> list=new ArrayList();
        Map categories;
        int cntr = DBHelper.prepareCategoryOptions(lang);
        if (cntr > 0) {
            int i = 0;
            categories = new HashMap();
            while (i < cntr) {
                list.add(DBHelper.getCategoryOption(++i));
                categories.put(list.get(i-1), i);
            }
        }

        sCategories = (Spinner) findViewById(R.id.sCategories);
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, list);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sCategories.setAdapter(spinnerAdapter);

        sCategories.setSelection(categoryId-1);

        ListView lv = getListView();
        View header = getLayoutInflater().inflate(R.layout.ranking_row_header, lv, false);
        lv.addHeaderView(header);

        populateAdapter();

        sCategories.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if ((position + 1) != categoryId) {
                    categoryId = position + 1;
                    attemptNr = 0;
                    sCategories.setSelection(categoryId-1);
                    populateAdapter();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    void populateAdapter() {
        int cntr = DBHelper.prepareRankingtData(categoryId);
        ra = new RankingAdapter(this);
        if (cntr > 0) {
            int i = 0;
            while (i < cntr) {
                if (DBHelper.getRankingDataRR(i).attempt == attemptNr) {
                    ra.addNewItem(DBHelper.getRankingDataRR(i));
                    if (i == 0) {
                        Toast.makeText(RankingActivity.this, "New record!!!", Toast.LENGTH_SHORT).show();
                    }
                } else
                    ra.addOldItem(DBHelper.getRankingDataRR(i));
                i++;
            }
        } else {
            //AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            //alertDialogBuilder.setMessage("No data to display.");
            //alertDialogBuilder.setPositiveButton("OK",
            //        new DialogInterface.OnClickListener() {
            //            @Override
            //            public void onClick(DialogInterface arg0, int arg1) {
            //                finish();
            //            }
            //        });
            //AlertDialog alertDialog = alertDialogBuilder.create();
            //alertDialog.show();
        }

        setListAdapter(ra);
    }
}
