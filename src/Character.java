import java.util.Random;
/**
 * This abstract class serves as a framework for game characters yet to be implemented.
 * Each 'Character' has a difficulty setting, health, damage and a boolean indicating if they are dead.
 * Each Character also has a head, body and legs.
 * @author Shane Birdsall
 */
public abstract class Character {
	protected static Random rand = new Random();
	protected Difficulty difficulty; 
	protected int health, damagePerHit;
	protected boolean isDead;
	protected String head, body, legs;

	public Character(int damage, String head, String body, String legs, Difficulty difficulty) {
		this.difficulty = difficulty;
		this.health = 100;
		this.damagePerHit = damage;
		this.isDead = false;
		this.head = head;
		this.body = body;
		this.legs = legs;
	}
	public void damageTaken(int damage) {
		this.health -= damage;
		if(this.health <=0) { // No health remaining so the character is now dead
			this.isDead = true;
		}
	}
	public boolean attack(Character beingAttacked) {
		if(rand.nextInt(100) > 25) { // Randomise the attack, 25% chance of missing, 75% chance of hitting.
			beingAttacked.damageTaken(this.damagePerHit);
			return true;
		} else return false;	
	}
	public void printCharacter() { // Prints character to command line
		System.out.println("\n"+head);
		System.out.println(body);
		System.out.println(legs);
	}
	public String toString() {
		return health+"LP"; // toString method shows the Characters Life Points
	}
}