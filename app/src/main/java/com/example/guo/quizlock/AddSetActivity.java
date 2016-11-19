package com.example.guo.quizlock;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

public class AddSetActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("");
        setContentView(R.layout.activity_add_set);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        ImageView fab = (ImageView) findViewById(R.id.back);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    public void parseFile(View view){
        //TODO: Place this with actual input options using InputTextActivity and ImportActivity
        DatabaseHelper myDb = new DatabaseHelper(getApplication(), "database.db", null, 1);
        //TODO: be able to detect errors in file formatting
        myDb.insertData("What is 1 + 1?", "2");
        myDb.insertData("What color is grass?", "green");
        myDb.insertData("What shape is Earth?", "sphere");
        //TODO: refresh main activity content after inserting data
        //finishActivity(1);
       // startActivity(new Intent(AddSetActivity.this, MainActivity.class));
        finish();
       // android.os.Process.killProcess(android.os.Process.myPid());
    }
}
