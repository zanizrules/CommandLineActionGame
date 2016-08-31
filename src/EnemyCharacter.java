/**
 * The EnemyCharacter class is one of the Sub-Classes of the Character class.
 * It specifies values specific to an enemy, including the points rewarded for killing an enemy.
 * @author Shane Birdsall
 */
public class EnemyCharacter extends Character {
	public static final int POINTS_FOR_KILLING = 50; // +50 points per kill
	public EnemyCharacter(Difficulty difficulty) {
		super((5*difficulty.enemyDamageM),"  ◣_◢","🔫||\\"," /\\", difficulty);
	}
	/* Unicode resources
	 * System.out.println("\uD83D\uDD2B"); Unicode for Gun
	 * System.out.println("\uD83D\uDD2A"); Unicode for Knife
	 * Extras: System.out.println("🔫 💣 🔥 🔞 ☢ 💥 💊 💉 👽 💀 👹 👻 🎃 🐍 👴 🐧 👾");
	 */
}