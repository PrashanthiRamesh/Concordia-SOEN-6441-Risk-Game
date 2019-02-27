package country;

/**
 * @author Mutesham Country Class
 */
public class Country {

	String countryName;
	String belongsTo;
	int armies;

	public Country(String countryName, String belongsTo, int armies) {
		this.countryName = countryName;
		this.belongsTo = belongsTo;
		this.armies = armies;
	}

	public String getCountryName() {
		return countryName;
	}

	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}

	public String getBelongsTo() {
		return belongsTo;
	}

	public void setBelongsTo(String belongsTo) {
		this.belongsTo = belongsTo;
	}

	public int getArmies() {
		return armies;
	}

	public void setArmies(int armies) {
		this.armies = armies;
	}

	@Override
	public String toString() {
		return "CountryClass [countryName=" + countryName + ", belongsTo=" + belongsTo + ", armies=" + armies + "]";
	}

}
