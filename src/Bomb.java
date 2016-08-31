import java.util.Scanner;
/**
 * The Bomb class is one of the Sub-Classes of the Character class.
 * It specifies values specific to a bomb.
 * This class also implements Runnable. The thread for this class works as the bombs countdown timer.
 * @author Shane Birdsall
 */
public class Bomb extends Character implements Runnable {
	public static final int POINTS_FOR_DEFUSING = 200;
	private boolean defused;
	private static Scanner userInput = new Scanner(System.in);
	 
	public Bomb(Difficulty difficulty) {
		super(50, "", "", "💣", difficulty);
		defused = false;
	}
	@Override
	public boolean attack(Character beingAttacked) {
		// This method overrides the Character class implementation in order to remove the randomiser.
		// This is because a bomb can not miss an attack, once it explodes it must have a 100% chance to hit.
		beingAttacked.damageTaken(this.damagePerHit);
		return true;	
	}
	private void printExplosion(String scenario) { // Prints the explosion scenario
		System.out.println("\nBOOM! The bomb exploded! 🔥🔥🔥🔥🔥🔥");
		System.out.println(scenario);
	}
	@Override
	public void printCharacter() { //Overridden due to a bomb only having one image (legs).
		System.out.println(legs);
	}
	public boolean defuseMe() {
		Thread defuse = new Thread(this);
		defuse.start(); // start the count down
		if((userInput.nextLine()).toUpperCase().equals("DEFUSE")) { // Check to see input is correct
			 if(defuse.isAlive()) { // Check to see if input has been given within time limit
				 System.out.println("Nice Job! You managed to defuse the bomb just in time!");
				 this.defused = true; // Successful scenario
			 } else 
				 printExplosion("You didn't defuse the bomb fast enough!"); // Unsuccessful scenario 1(Not fast enough)
		} else 
			printExplosion("You didn't defuse the bomb correctly!"); // Unsuccessful scenario 2(Not the correct user input)
		return this.defused; // return whether the bomb was defused or not
	}
	public void run() {
		System.out.println("Quickly type 'DEFUSE' and hit enter!!!");
		try {
			Thread.sleep(1000*(difficulty.bombTimerM)); // Give 1 second * difficulty to defuse the bomb.
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}