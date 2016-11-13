package com.example.guo.quizlock;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

/**
 * Created by Guo on 11/12/2016.
 */
public class CardSet extends HashSet{
    private Set<Card> cardSet;

    public CardSet(){
        cardSet = new HashSet<Card>();
    }

    public Card getCard(){
        Card card = new Card();
        int rand = new Random().nextInt(cardSet.size());
        int i = 0;
        for(Card c : cardSet){
            if (i == rand) {
                card = c;
            }
            i++;
        }
        return card;
    }
}
