package view;

import controller.GamePlay;
import controller.TournamentGamePlay;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

/**
 * CardExchange represents the observer pattern of card exchange during reinforcement phase
 *
 * @author Prashanthi
 * @version 1.2
 * @since 1.1
 */
public class TournamentPlayerWorldDomination implements Observer {

	/**
	 * This method is called whenever the observed object phase is changed.
	 * @param observable the observable GamePlay instance
	 * @param object object argument passed to the update method
	 */
	public void update(Observable observable, Object object) {
		float percentageMap=((TournamentGamePlay) observable).getPercentageMap();
		int armies=((TournamentGamePlay) observable).getTotalArmies();
		ArrayList continents=((TournamentGamePlay) observable).getContinentsControlled();
		System.out.println("% of map controlled= "+percentageMap);
		System.out.println("Number of Armies owned= "+armies);
		System.out.println("Continents controlled= "+continents);
		 
	}

}
