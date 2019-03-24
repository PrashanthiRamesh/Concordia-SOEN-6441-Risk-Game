package view;

import controller.GamePlay;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;


public class CardExchange implements Observer {

    public void update(Observable obs, Object x) {
        float percentageMap = ((GamePlay) obs).getPercentageMap();
        int armies = ((GamePlay) obs).getTotalArmies();
        ArrayList continents = ((GamePlay) obs).getContinentsControlled();
        System.out.println("% of map controlled= " + percentageMap);
        System.out.println("Number of Armies owned= " + armies);
        System.out.println("Continents controlled= " + continents);

    }

}

