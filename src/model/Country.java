package model;

/**
 * Country Class represents a country
 *
 * @author Mutesham
 * @version 1.1
 * @since 1.0
 */
public class Country {

	/**
	 * Holds the name of the country
	 */
	private String countryName;

	/**
	 * Holds the name of the player the country belongs to
	 */
	private String belongsTo;
	
	/**
	 * Holds the number of armies that the country has
	 */
	private int armies;

	/**
	 * Creates a country
	 */
	public Country() { }

	/**
	 * Creates a country with a specified country name, player name and number of armies
	 * @param countryName country name
	 * @param belongsTo player name who owns the country
	 * @param armies number of armies in the country
	 */
	public Country(String countryName, String belongsTo, int armies) {
		this.countryName = countryName;
		this.belongsTo = belongsTo;
		this.armies = armies;
	}
	/**
	 * Getter method to get the name of the country
	 * @return country name
	 */
	String getCountryName() {
		return countryName;
	}

	/**
	 * Setter method to assign a string value to the country name
	 * @param countryName country name
	 */
	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}

	/**
	 * Getter method to get the name of player who owns the country
	 * @return player name
	 */
	String getBelongsTo() {
		return belongsTo;
	}

	/**
	 * Setter method to assign a string value to the player who owns the country
	 * @param belongsTo player name
	 */
	void setBelongsTo(String belongsTo) {
		this.belongsTo = belongsTo;
	}

	/**
	 * Getter method to get the number of armies in the country
	 * @return number of armies
	 */
	public int getArmies() {
		return armies;
	}

	/**
	 * Setter method to assign an integer value to the number of armies in the country
	 * @param armies number of armies in the country
	 */
	public void setArmies(int armies) {
		this.armies = armies;
	}

	/**
	 * To get a string representation of country
	 * @return concatenation of country name, player who owns it and number of armies in the country
	 */
	@Override
	public String toString() {
		return "CountryClass [countryName=" + countryName + ", belongsTo=" + belongsTo + ", armies=" + armies + "]";
	}

}
