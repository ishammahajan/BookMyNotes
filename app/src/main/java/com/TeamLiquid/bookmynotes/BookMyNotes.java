package com.TeamLiquid.bookmynotes;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.google.gson.Gson;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class BookMyNotes extends Activity {

    Button addSubject;
    EditText entersubname;
    ListView subjectList;
    ArrayAdapter<String> createSubjectListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        addSubject = (Button) findViewById(R.id.addsubject);
        entersubname = (EditText) findViewById(R.id.entersubname);
        subjectList = (ListView) findViewById(R.id.lv1);

        ArrayList subjectNames = new ArrayList();
        //Extracting sharedprefs files for subject names
        try {
            SharedPreferences subjects = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            Gson gsonSUBJECTS = new Gson();
            String jsonSUBJECTS = subjects.getString("subject names", "");
            subjectNames = gsonSUBJECTS.fromJson(jsonSUBJECTS, ArrayList.class);
        } catch(Exception e) {
            e.printStackTrace();
        }


        createSubjectListAdapter = new ArrayAdapter<String>(this, R.layout.listview, subjectNames);
        createSubjectListAdapter.setNotifyOnChange(true);
        subjectList.setAdapter(createSubjectListAdapter);

        final ArrayList finalSubjectNames = subjectNames;
        addSubject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String str = entersubname.getText().toString();
                createSubjectListAdapter.add(str);
                //adding entered subjects to shared prefs
                SharedPreferences subjects = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                SharedPreferences.Editor editor = subjects.edit();
                Gson gsonSUBJECTS = new Gson();
                String jsonSUBJECTS = gsonSUBJECTS.toJson(finalSubjectNames);
                editor.putString("subject names", jsonSUBJECTS);
                editor.commit();
                entersubname.setText("");
            }
        });

        final ArrayList finalSubjectNames1 = subjectNames;
        subjectList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                createSubjectListAdapter.remove(finalSubjectNames1.get(i).toString());
                SharedPreferences subjects = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                SharedPreferences.Editor editor = subjects.edit();
                Gson gsonSUBJECTS = new Gson();
                String jsonSUBJECTS = gsonSUBJECTS.toJson(finalSubjectNames);
                editor.putString("subject names", jsonSUBJECTS);
                editor.commit();
                return false;
            }
        });

        subjectList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                startActivity(new Intent(BookMyNotes.this, notesGridView.class));
            }
        });
    }




}
