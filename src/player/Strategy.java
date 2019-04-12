package player;

import model.Player;
import model.RiskMap;

/** Interface that implements the different strategies of the players
 * @author Prashanthi
 * @version 1.2
 * @since 1.2
 */

public interface Strategy {

	/**
	 * This the abstract method for which implementation will be provided in the
	 * classes which implement this interface. This method is for the reinforcement
	 * of the countries owned by the player.
	 * 
	 * @param player We pass the player object who is currently playing the game.
	 * @param map We pass the map object where the map is loaded
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
	 * @param mapPlayer We pass the mapPlayer object
	 * @return enriched player object is returned
	 */
	public Player attack(Player player, Player mapPlayer);

	/**
	 * This the abstract method for which implementation will be provided in the
	 * classes which implement this interface. This method is for the fortification
	 * in which
	 * 
	 * @param player We pass the player object who is currently playing the game.
	 * @param map We pass the map object where the map is loaded
	 * @return enriched player object is returned
	 */
	public Player fortification(Player player, RiskMap map);
	
	
	/**
	 * This the abstract method for which implementation will be provided in the
	 * classes which implement this interface. This method is for the deploying armies
	 * in the startup and reinforcement phases
	 * 
	 * @param player We pass the player object who is currently playing the game.
	 * @param map We pass the map object where the map is loaded
	 * @return enriched player object is returned
	 */
	public Player deployArmies(Player player, RiskMap map);
	
}
