package model;

/**
 * Card Class represents a country
 *
 * @author Maqsood
 * @version 1.2
 * @since 1.1
 */
public class Card {

    /**
     * Stores the name of the card
     */
    private String name;

    /**
     * Stores the number of the type of the card
     */
    private int typeNumber;

    /**
     * Creates a card with the specified name and card type number
     * @param name name of the card
     * @param typeNumber card type number
     */
    public Card(String name, int typeNumber) {
        this.name = name;
        this.typeNumber = typeNumber;
    }

    /**
     * getter method to return the name of the card
     * @return name of the card
     */
    public String getName() {
        return name;
    }

    /**
     * Setter method to assign a name for the card
     * @param name name of the card
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Getter method to return the type number of card
     * @return card type number
     */
    public int getTypeNumber() {
        return typeNumber;
    }

    /**
     * Setter method to assign a value to the type of card
     * @param typeNumber card type number
     */
    public void setTypeNumber(int typeNumber) {
        this.typeNumber = typeNumber;
    }

    /**
     * Method to get the name of the card by passing the card type number
     * @param typeNumber card type number
     * @return name of the card
     */
    static String getNameByTypeNumber(int typeNumber) {
        String name = "";
        switch (typeNumber) {
            case 1:
                name = "Infantry";
                break;
            case 2:
                name = "Cavalry";
                break;
            case 3:
                name = "Cannon";
                break;
            default:
                break;
        }
        return name;
    }
}
