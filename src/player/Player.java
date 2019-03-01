package player;

import country.Country;

import java.util.ArrayList;

/**
 * Player Class
 *
 * @author Manasa
 */
public class Player {

    /**
     *
     */
    private String player_name;

    /**
     *
     */
    private int initial_armies;

    /**
     *
     */
    private int no_of_countries;

    /**
     *
     */
    public Player() {

    }

    /**
     * @param playername    fsd
     * @param initialarmies fsd
     */
    public Player(String playername, int initialarmies) {
        this.player_name = playername;
        this.initial_armies = initialarmies;
    }

    public String getPlayer_name() {
        return player_name;
    }

    public void setPlayer_name(String player_name) {
        this.player_name = player_name;
    }

    public int getInitial_armies() {
        return initial_armies;
    }

    public void setInitial_armies(int initial_armies) {
        this.initial_armies = initial_armies;
    }

    public int getNo_of_countries() {
        return no_of_countries;
    }

    public void setNo_of_countries(int no_of_countries) {
        this.no_of_countries = no_of_countries;
    }

    public void calculateArmies(ArrayList<Player> players, ArrayList<Country> countries) {
        for (Player player : players) {
            int player_countries_count=0;
            for (Country country : countries) {
                if (country.getBelongsTo().equals(player.player_name)) {
                    player_countries_count++;
                }
            }
            player.initial_armies=(int) Math.floor(player_countries_count / 3.0);
            player.no_of_countries=player_countries_count;
            System.out.println();
            System.out.println("**** Player- "+player.player_name+" ****");
            System.out.println("Countries: "+player.no_of_countries);
            System.out.println("Armies: "+player.initial_armies);
        }

    }

}
