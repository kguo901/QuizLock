package com.example.guo.quizlock;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.provider.Telephony;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;

import com.example.guo.quizlock.LockScreen.LockScreenService;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class MainActivity extends AppCompatActivity {
    //Declare database helper
    DatabaseHelper myDb;
    List<Card> set = new ArrayList<>();
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

        ListView listView = (ListView) findViewById(R.id.sets);
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
                                           int pos, long id) {
                deleteAll();
                return true;
            }
        });

        populateCardsList();
        displaySet();
    }

    private void populateCardsList(){
        //TODO: declare database this in another service to speed up app
        //initialize database helper and get all data from database
        myDb = new DatabaseHelper(getApplicationContext(), "database.db", null, 1);

        myDb.insertData("Term","Definition");
        SQLiteDatabase db = myDb.getReadableDatabase();

        Cursor cursor1 = myDb.getAllData();
        if(cursor1.getCount() > 0){
            while(cursor1.moveToNext()){
                set.add(new Card(cursor1.getString(1), cursor1.getString(2)));
            }
        }
    }

    public void deleteAll(){
        //prompt delete all option
        final ListView linearLayout = (ListView) findViewById(R.id.sets);
        Snackbar bar = Snackbar.make(linearLayout, "The answer was wrong.", Snackbar.LENGTH_LONG)
                .setAction("Delete All", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        myDb.clearAllData();
                        refresh(linearLayout);
                    }
                });

        bar.show();
    }

    public void refresh(View view){
        finish();
        startActivity(getIntent());
    }

    private void displaySet(){
        ArrayAdapter<Card> arrayAdapter = new MyListAdapter();
        ListView listView = (ListView) findViewById(R.id.sets);
        listView.setAdapter(arrayAdapter);
    }



    private class MyListAdapter extends ArrayAdapter<Card>{
        public MyListAdapter(){
            super(MainActivity.this, R.layout.set, set);
        }

        @Override
        public int getCount() {
            return set.size();
        }

        @Nullable
        @Override
        public Card getItem(int position) {
            return super.getItem(position);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View itemView = convertView;
            if(itemView == null){
                itemView = getLayoutInflater().inflate(R.layout.set, parent, false);
            }

            Card currentC = set.get(position);

            TextView term = (TextView) itemView.findViewById(R.id.term);
            term.setText(currentC.getTerm());
            TextView def = (TextView) itemView.findViewById(R.id.definition);
            def.setText(currentC.getDef());
            return itemView;
        }


    }
}
