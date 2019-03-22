package view;

import controller.GamePlay;

import java.util.Observable;
import java.util.Observer;

public class PlayerWorldDomination implements Observer {

	public void update(Observable obs, Object x) {
		float percentage_map=((GamePlay) obs).getPercentage_map();
		int armies=((GamePlay) obs).getTotal_armies();
		System.out.println(percentage_map+" map percentage  "+ armies+ " armies");
		 
	}

}
