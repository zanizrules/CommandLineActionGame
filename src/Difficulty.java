/**
 * The enum Difficulty is used to specify how challenging the game should be.
 * Each difficulty effects enemy damage, the bomb timer, heal amounts, player damage, and score.
 * @author Shane Birdsall
 */
public enum Difficulty {
	VERY_EASY(1, 10, 8, 4, 1), EASY(3, 5, 4, 3, 5), MEDIUM(5, 3, 4, 2, 10), HARD(5, 2, 2, 1, 20), GODLIKE(8, 1, 1, 1, 40);
	// M = Multiplier
	int enemyDamageM; // implemented
	int bombTimerM; // implemented
	int healM; //implemented
	int playerDamageM; // implemented
	int scoreM; // implemented
	
	Difficulty(int enemyDamageM, int bombTimerM, int healM, int playerDamageM, int scoreM) {
		this.enemyDamageM = enemyDamageM;
		this.bombTimerM = bombTimerM;
		this.healM = healM;
		this.playerDamageM = playerDamageM;
		this.scoreM = scoreM;
	}
}