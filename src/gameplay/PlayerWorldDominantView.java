package gameplay;

import java.util.Observable;
import java.util.Observer;

public class PlayerWorldDominantView implements Observer {

	
	public void update(Observable obs, Object x) {
		
		
		float percentage_map=((GamePlay) obs).getPercentage_map();
		int armies=((GamePlay) obs).getTotal_armies();
		System.out.println(percentage_map+" map percentage  "+ armies+ " armies");
		 
	}
	
	

}
