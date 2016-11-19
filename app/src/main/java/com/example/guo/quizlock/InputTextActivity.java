package com.example.guo.quizlock;

import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.view.View;
import android.widget.EditText;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import java.lang.reflect.Array;

public class InputTextActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_text);

        Button doneButton = (Button)findViewById(R.id.doneButton);
        doneButton.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                // Get title name
                EditText title = (EditText) findViewById(R.id.titleName);
                String titleName = title.getText().toString();

                // Get cards
                EditText card = (EditText) findViewById(R.id.cardContent);
                String input = card.getText().toString();
                saveCard(input);
            }
        });

        ImageView fab = (ImageView) findViewById(R.id.back);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    public void saveCard(String inputText)
    {
        DatabaseHelper myDb = new DatabaseHelper(getApplicationContext(), "database.db", null, 1);;
        String[] splitLine = inputText.split("\n");
        for(int i=0; i<splitLine.length; i++)
        {
            String[] splitCard = splitLine[i].split(";");
            myDb.insertData(splitCard[0],splitCard[1]);
        }
    }
}