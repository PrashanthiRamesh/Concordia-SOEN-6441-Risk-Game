package PlayerPackgae;

public class PlayerClass {

	private String playername;
	private int initialarmies;
	
	
	public PlayerClass(String playername, int initialarmies) {
		this.playername = playername;
		this.initialarmies = initialarmies;
	}
	public String getPlayername() {
		return playername;
	}
	public void setPlayername(String playername) {
		this.playername = playername;
	}
	public int getInitialarmies() {
		return initialarmies;
	}
	public void setInitialarmies(int initialarmies) {
		this.initialarmies = initialarmies;
	}
	
	
	
	
}
