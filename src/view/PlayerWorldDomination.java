package view;

import controller.GamePlay;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

/**
 * CardExchange represents the observer pattern of card exchange during reinforcement phase
 *
 * @author Prashanthi
 * @version 1.1
 * @since 1.1
 */
public class PlayerWorldDomination implements Observer {

	/**
	 * This method is called whenever the observed object phase is changed.
	 * @param observable the observable GamePlay instance
	 * @param object object argument passed to the update method
	 */
	public void update(Observable observable, Object object) {
		float percentageMap=((GamePlay) observable).getPercentageMap();
		int armies=((GamePlay) observable).getTotalArmies();
		ArrayList continents=((GamePlay) observable).getContinentsControlled();
		System.out.println("% of map controlled= "+percentageMap);
		System.out.println("Number of Armies owned= "+armies);
		System.out.println("Continents controlled= "+continents);
		 
	}

}
