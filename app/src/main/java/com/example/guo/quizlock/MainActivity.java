package com.example.guo.quizlock;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import com.example.guo.quizlock.LockScreen.LockScreenService;

public class MainActivity extends AppCompatActivity {
    //Declare database helper
    DatabaseHelper myDb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setTitle("");
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //begin AddSetActivity when button is clicked
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddSetActivity.class);
                startActivity(intent);
                //startActivityForResult(intent, 1);
            }
        });

        //lock screen can be turned on and off
        final Intent i = new Intent(this,LockScreenService.class);
        Switch swt = (Switch) findViewById(R.id.main_switch);
        swt.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            //Check if swich is on or off
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    //start lock screen service
                    startService(i);
                } else {
                    //stop lock screen service
                    stopService(i);
                }
            }
        });

        displayData();
    }

    private void displayData(){
        //TODO: declare database this in another service to speed up app
        //initialize database helper and get all data from database
        myDb = new DatabaseHelper(getApplicationContext(), "database.db", null, 1);
        SQLiteDatabase db = myDb.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from cardset", null);

        //Display database contents
        TextView textView = (TextView) findViewById(R.id.sets);
        Cursor cursor1 = myDb.getAllData();
        if(cursor1.getCount()==0){
            textView.setText("Database Empty");
        }else {
            textView.setText("");
            while(cursor1.moveToNext()){
                textView.append("ID: " + cursor1.getString(0) + "\n");
                textView.append("Term: " + cursor1.getString(1) + "\n");
                textView.append("Definition: " + cursor1.getString(2) + "\n");
            }
        }
    }
}