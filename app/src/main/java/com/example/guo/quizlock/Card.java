package com.example.guo.quizlock;

/**
 * Created by Guo on 11/19/2016.
 */

public class Card {
    private String term;
    private String def;

    public Card(){
        term = "";
        def = "";
    }

    public Card(String t, String d){
        term = t;
        def = d;
    }

    public String getTerm(){
        return term;
    }

    public String getDef(){
        return def;
    }
}
