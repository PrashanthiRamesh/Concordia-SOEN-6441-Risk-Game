package player;

import model.Player;
import model.RiskMap;

public interface Strategy {

	/**
	 * This the abstract method for which implementation will be provided in the
	 * classes which implement this interface. This method is for the reinforcement
	 * of the countries owned by the player.
	 * 
	 * @param player We pass the player object who is currently playing the game.
	 * @return enriched player object is returned
	 * @throws Exception if error
	 */
	public Player reinforcement(Player player, RiskMap map) throws Exception;

	/**
	 * This the abstract method for which implementation will be provided in the
	 * classes which implement this interface. This method is for the attack iin
	 * which player may decide to attack neighboring countries or not
	 * 
	 * @param player We pass the player object who is currently playing the game.
	 * @return enriched player object is returned
	 */
	public Player attack(Player player, RiskMap map);

	/**
	 * This the abstract method for which implementation will be provided in the
	 * classes which implement this interface. This method is for the fortification
	 * in which
	 * 
	 * @param player We pass the player object who is currently playing the game.
	 * @return enriched player object is returned
	 */
	public Player fortification(Player player, RiskMap map);
	
}
