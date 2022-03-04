package com.example.sevenhalf;

import java.util.HashSet;

public class Card {

    public static HashSet<String> CARDS_DONE = new HashSet<String>();

    public int backgroundResource;
    public float realValue;

    public Card(MainActivity main) {

        String typeCard = "";
        int typeCardRandom;
        String result;
        int valueCard;

        do {
            valueCard = (int) (Math.random() * 12) + 1;
            typeCardRandom = (int) (Math.random() * 4) + 1;

            if (typeCardRandom == 1) typeCard = "clubs";
            if (typeCardRandom == 2) typeCard = "cups";
            if (typeCardRandom == 3) typeCard = "golds";
            if (typeCardRandom == 4) typeCard = "swords";

            result = typeCard + valueCard;
        } while (CARDS_DONE.contains(result));

        CARDS_DONE.add(result);

        if (valueCard < 8) realValue = valueCard;
        else realValue = 0.5f;

        if (valueCard >= 10) {
            backgroundResource = main.getResources().getIdentifier(typeCard + valueCard, "drawable", main.getPackageName());
        } else {
            backgroundResource = main.getResources().getIdentifier(typeCard + "0" + valueCard, "drawable", main.getPackageName());
        }
    }
}