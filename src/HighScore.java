/**
 * The HighScore class represents a high score, including a name and score.
 * This class also specifies the size of the high score table.
 * @author Shane Birdsall
 */
public class HighScore implements Comparable<HighScore> {
	public static final int TABLE_SIZE = 10;
	private final String name;
	private final int score;
	public HighScore(String name, int score) {
		this.name = name;
		this.score = score;
	}
	public int compareTo(HighScore newScore) {
		return newScore.score - this.score;
	}
	public int getScore() {
		return this.score;
	}
	public String getName() {
		return this.name;
	}
}