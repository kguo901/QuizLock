package com.example.guo.quizlock.LockScreen;

import android.app.Activity;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import com.example.guo.quizlock.DatabaseHelper;
import com.example.guo.quizlock.R;

import java.util.Random;

public class LockScreenActivity extends Activity {

    //initialize default card
    private String[] card = {"Press Unlock", ""};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Set lock screen to full screen
        makeFullScreen();

        setContentView(R.layout.activity_lock_screen);

        //get database data
        DatabaseHelper myDb = new DatabaseHelper(getApplication(), "database.db", null, 1);
        myDb.getReadableDatabase();
        Cursor cursor = myDb.getAllData();

        //pick random card from database
        TextView term = (TextView) findViewById(R.id.question);
        term.setText("");
        if(cursor.getCount() > 0){
            int rand = new Random().nextInt(cursor.getCount());
            while(rand > 0){
                cursor.moveToNext();
                rand--;
            }
            //get term
            card[0] = cursor.getString(1);
            //get definition
            card[1] = cursor.getString(2);
        }

        //display term in lock screen
        term.setText(card[0]);

        //close cursor
        cursor.close();
    }

    /**
     * A simple method that sets the screen to fullscreen.  It removes the Notifications bar,
     *   the Actionbar and the virtual keys (if they are on the phone)
     */
    public void makeFullScreen() {
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        if(Build.VERSION.SDK_INT < 19) { //View.SYSTEM_UI_FLAG_IMMERSIVE is only on API 19+
            this.getWindow().getDecorView()
                    .setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        } else {
            this.getWindow().getDecorView()
                    .setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE);
        }
    }

    @Override
    public void onBackPressed() {
        return; //Do nothing on back press
    }

    //TODO: Block home and recent apps button press

    /**
     * Checks to see if input is correct before unlocking screen
     * @param view
     */
    public void unlockScreen(View view) {
        //Instead of using finish(), this totally destroys the process
        //finish() ends current activity whereas destroying process ends all activity associated with this application
        EditText def = (EditText) findViewById(R.id.answer);
        //Compare user input to definition
        if(card[1].compareTo(def.getText().toString()) == 0) {
            android.os.Process.killProcess(android.os.Process.myPid());
        }
    }
}