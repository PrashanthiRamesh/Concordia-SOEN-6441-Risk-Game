package view;

import controller.GamePlay;

import java.util.Observable;
import java.util.Observer;

public class Phase implements Observer {

    public void update(Observable obs, Object x) {
        int phase = ((GamePlay) obs).getPhase();
        String player_name = ((GamePlay) obs).getCurrent_player();

        if (phase == 1) {
            System.out.println("\n** Reinforcement **");
            System.out.println("\n** Player- " + player_name + " **");
        } else if (phase == 2) {
            System.out.println("\n** Fortification **");
            System.out.println("\n** Player- " + player_name + " **");
        }
    }

}
