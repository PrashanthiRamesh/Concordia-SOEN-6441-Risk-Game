package view;

import controller.GamePlay;

import java.util.Observable;
import java.util.Observer;

public class Phase implements Observer {

    public void update(Observable obs, Object x) {
        int phase = ((GamePlay) obs).getPhase();
        String player_name = ((GamePlay) obs).getCurrentPlayer();
        switch (phase){
            case 1:
                System.out.println("\n** StartUp Phase- Initial Deployment **");
                System.out.println("\n In this phase the actions that will take place:\n1. In round robin fashion, " +
                        "players place their armies on their own countries");
                break;
            case 2:
                System.out.println("\n** Reinforcement **");
                System.out.println("\n In this phase the actions that will take place:\n1. Calculate number " +
                        "of reinforcement armies for players\n2. Cards Exchange");
                break;
            case 3:
                System.out.println("\n** Attack **");
                System.out.println("\n In this phase the actions that will take place:\n1. Player declares attack by " +
                        "selecting attacker and attacking country\n2. Implementation of All Out Mode\n");
                break;
            case 4:
                System.out.println("\n** Fortification **");
                System.out.println("\n In this phase the actions that will take place:\n1. Valid move according to " +
                        "risk rules");
                break;
        }
        System.out.println("\n** Player- " + player_name + " **");
    }

    
    
}

