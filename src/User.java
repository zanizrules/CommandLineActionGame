/**
 * The User class represents a human user, including their name and score.
 * @author Shane Birdsall
 */
public class User {
	private String name;
	private int score;
	
	public User() {
		this("Unknown");
	}
	public User(String username) {
		this.score = 0;
		setName(username);
	}
	public void applyDifficultyToScore(Difficulty difficulty) {
		score = score*difficulty.scoreM; // Adjust score according to selected difficulty
	}
	public int getScore() {
		return score;
	}
	public void addToScore(int score) {
		this.score += score;
	}
	public String getName() {
		return name;
	}
	public void setName(String n) { 
		if(n != null) {
			n = n.trim(); // Don't want whitespace in name
			if(n.length() > 15) { // Don't want name to be really long
				n = n.substring(0, 15);
			}
			if(n.isEmpty()) { // Don;t want name to be empty
				name = "UNKNOWN";
			} else {
				name = n.toUpperCase(); // Make it upper case
			}
		} else {
			name = "UNKNOWN"; 
		}
	}
	public String currentScore() {
		return "Current Score: "+this.score;
	}
	public String toString() {
		return name + ", you scored " +score +" points!";
	}
}