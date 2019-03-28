package model;


import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class PlayerTest {

        private Player player;

        @Before
        public void setUp(){
               player=new Player();
        }

        @Test
        public void validInitialArmies(){
            int[] initial_armies = {40, 35, 30, 25, 20};
                for(int i=2;i<=6;i++){
                        int armies=player.calculateInitialArmies(i);
                        int initial_army=initial_armies[i-2];

                        assertEquals(armies, initial_army);
                }

        }

        @Test
        public void invalidInitialArmies(){
            int[] initial_armies = {-1, 8, 1000, -100, 0};
                for(int i=2;i<=6;i++){
                        int armies=player.calculateInitialArmies(i);
                        int initial_army=initial_armies[i-2];

                        assertNotEquals(armies, initial_army);
                }
        }

        @Test
        public void validReinforcementArmies(){

            int[] total_no_of_countries = {99, 98, 55, 66, 43};
            int[] reinforcement_armies = {33, 32, 18, 22, 14};
                for(int i=0;i<total_no_of_countries.length;i++){
                        int armies=Player.calculateReinforcementArmies(total_no_of_countries[i]);
                        assertEquals(armies, reinforcement_armies[i]);
                }

        }

        @Test
        public void invalidReinforcementArmies(){

            int[] total_no_of_countries = {99, 98, 55, 66, 43};
            int[] reinforcement_armies = {34, 33, 19, 23, 15};
                for(int i=0;i<total_no_of_countries.length;i++){
                        int armies=Player.calculateReinforcementArmies(total_no_of_countries[i]);
                        assertNotEquals(armies, reinforcement_armies[i]);
                }

        }

}