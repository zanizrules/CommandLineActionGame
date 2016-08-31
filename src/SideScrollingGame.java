import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
/**
 * The SideScrollingGame class is used to run the game. It handles the users decisions, and reacts accordingly.
 * @author Shane Birdsall
 */
public class SideScrollingGame {
	private static Random rand = new Random();
	private static List<HighScore> highScores;
	private static Scanner userInput = new Scanner(System.in);
	private static Difficulty difficulty;
	
	public static void main(String[] args) {
		System.out.println("Welcome to Shane's Side Scrolling Game!\n");
		int numInput = 0;
		highScores = new ArrayList<HighScore>();
		try { // Read in high score table
			Scanner input = new Scanner(new FileReader("highscoreTable.txt"));
			input.useDelimiter(",");
			while(input.hasNext()) {
				highScores.add(new HighScore(input.next(),Integer.parseInt(input.next())));
			}
			input.close();
		} catch (FileNotFoundException e) {
			System.err.println("ERROR: There is no HighScore Table");
		}
		while(numInput != 4) { // Main menu - Gives 4 options to choose from
			System.out.println("Please select from the following: ");
			System.out.println("1. Play Game\n2. View High Scores\n3. View Instructions\n4. Quit");
			numInput = queryIntegerUserInput(1,4);
			if(numInput > 0) {
				menuSelection(numInput);
			}
		}
	}
	public static void menuSelection(int selection) { // Do something depending on the users decision
		if(selection == 1) {
			startGame(); // User wants to start up the game
		} else if(selection == 2) {
			displayHighscores(); // User wants to view the high scores
		} else if(selection == 3) {
			displayInstructions(); // User wants to view the instructions
		} else if(selection == 4) { // Exit the program
			System.out.println("👴: Thanks for playing!");  
		} 
	}
	public static void displayInstructions() {
		// Print out the instruction screen
		System.out.println("-----------Instructions-----------");
		Character instructionCharacter = new UserCharacter(Difficulty.MEDIUM);
		instructionCharacter.printCharacter();
		System.out.println("👴 : Why hello, this is you... What's the knife for you ask?\n");
		((UserCharacter) instructionCharacter).printCharacterInteraction(new EnemyCharacter(Difficulty.MEDIUM));
		System.out.println("👴 : The knife is to protect you from these guys!");
		System.out.println("👴 : When you encounter an enemy in the battlefield you will have the option to attack or heal 💉");
		System.out.println("👴 : After you, the enemy will attack. Let's hope for your sake they miss!\n");
		instructionCharacter = new DoctorCharacter(Difficulty.MEDIUM);
		instructionCharacter.printCharacter();
		System.out.println("👴 : Ahh, and this is me. I follow you around waiting for you to die so I can use your body parts in illegal operations...");
		System.out.println("👴 : But if you ever need to be healed or would like one of my special pills 💊, I'll be sure to be of assistance.");
		System.out.println("👴 : I can heal " +(-1*instructionCharacter.damagePerHit)+"LP if you play a Medium game, and my pills can give you +" +DoctorCharacter.PILL_BUFF 
				+" damage, but they don't always work for some reason\n");
		instructionCharacter = new Bomb(Difficulty.MEDIUM);
		instructionCharacter.printCharacter();
		System.out.println("👴 : You are likely to encounter bombs throughout the battlefield, quickly type 'DEFUSE' to stop them from exploding!");
		System.out.println("👴 : But you want to be quick! You only have a couple of seconds before the bomb will go off!!!\n");
		System.out.println("👴 : Score a high enough score and see yourself on the high score tables! Have fun playing...\n");
	}
	public static void displayHighscores() {
		// Print out the high score screen
		Collections.sort(highScores); // sort high scores
		System.out.println("NAME: SCORE");
		System.out.println("-----------");
		for(int i = 0; i < highScores.size(); i++) {
			System.out.println(highScores.get(i).getName().toUpperCase() +": " +highScores.get(i).getScore());
		} System.out.println();
	}
	public static void startGame() {
		difficulty = selectDifficulty(); // Make user select a difficulty 
		int randomNum;
		User user1 = new User(getUsername()); // Make user input their name 
		UserCharacter user1Char = new UserCharacter(difficulty);
		System.out.println("Welcome "+ user1.getName()+"..."); // From this point the game begins
		user1Char.printCharacter();
		System.out.println("You have spawned in the battlefield, you currently have 100LP.");
		Boolean doubleDoc = false; // stops the doctor appearing twice in a row
		while(!user1Char.isDead) {
			System.out.println("You slowly advance further into the battlefield");
			haltGame(3); // wait 3 seconds
			randomNum = rand.nextInt(100); //Randomly generate a scenario... Give each scenario a range
			if(randomNum < 35) { // 35% chance of a bomb
				user1.addToScore(bombThreat(user1Char));
				doubleDoc = false;
			} else if(randomNum > 80 && !doubleDoc) { // 20% chance of a doctor if he wasn't the previous scenario
				doctorAppears(user1Char);
				doubleDoc = true;
			} else { // This leaves a 45% chance normally, and a 65% chance after a doctor appears for a enemy to appear.
				user1.addToScore(enemyApproaches(user1Char));
				doubleDoc = false;
			}	
		} // Loop exits when player is dead
		System.out.println("👴 : R.I.P " +user1.getName() +", even I can't fix this.");
		user1.applyDifficultyToScore(difficulty); // Adjust score according to difficulty
		System.out.print(user1);
		if(user1.getScore() > highScores.get(HighScore.TABLE_SIZE-1).getScore()) { // Check to see if score is good enough to be a high score
			System.out.println(" You are now on the high score table!!!");
		} else System.out.println();
		ArrayList<HighScore> tempList = new ArrayList<HighScore>(highScores);
		tempList.add(new HighScore(user1.getName(), user1.getScore())); // add score
		Collections.sort(tempList); // sort high score table
		highScores.clear();
		for(int i = 0; i < HighScore.TABLE_SIZE; i++) { // add new updated high score list
			highScores.add(tempList.get(i));
		}
		try { // Save high scores to .txt file
			PrintWriter out = new PrintWriter(new FileWriter("highscoreTable.txt"));
			for(int i = 0; i < highScores.size(); i++) {
				out.write(highScores.get(i).getName()+",");
				out.write(highScores.get(i).getScore()+",");
			}
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static Difficulty selectDifficulty() {
		int numInput = 0;
		while(numInput < 1) { // Select a difficulty 
			System.out.println("Please Select a difficulty:");
			System.out.println("1. VERY EASY");
			System.out.println("2. EASY");
			System.out.println("3. MEDIUM");
			System.out.println("4. HARD");
			System.out.println("5. GODLIKE");
			numInput = queryIntegerUserInput(1, 5);
		} // Difficulty selected
		if(numInput == 1) { 
			return Difficulty.VERY_EASY;
		} else if(numInput == 2) {
			return Difficulty.EASY;
		} else if(numInput == 3) {
			return Difficulty.MEDIUM;
		} else if(numInput == 4) {
			return Difficulty.HARD;
		} else return Difficulty.GODLIKE; //numInput == 5
	}
	public static void haltGame(int seconds) { // pause game for x amount of seconds
		try {
			Thread.sleep(seconds*1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	public static String getUsername() { // Get users name
		System.out.println("Please enter a name: ");
		return userInput.nextLine();
	}
	public static int enemyApproaches(UserCharacter user) { // Handles enemy appearing scenario
		EnemyCharacter enemy = new EnemyCharacter(difficulty); // create new enemy
		user.printCharacterInteraction(enemy); // Start scenario from here on
		System.out.println("An enemy has approached you!");
		while(!enemy.isDead && !user.isDead) {
			System.out.println("You have: " +user +", Enemy has: " +enemy);	
			int input = 0;
			while(input < 1) { // Choose to attack or heal
				System.out.println("Press 1 to attack, or 2 to heal");
				input = queryIntegerUserInput(1, 2);
			}
			if(input == 1) { // Attack
				if(user.attack(enemy)) 
					System.out.println("Attack was succesful!");
				else 
					System.out.println("Enemy dodged your attack");
			} else if(input == 2) { // Heal
				healUp(user);
			}
			if(!enemy.isDead) { //Stops enemy from attacking if you just killed them.
				if(enemy.attack(user)) 
					System.out.println("Argh! Enemy hit you");
				else 
					System.out.println("Enemy tried to attack but missed");
			}	
		} // stop loop once enemy or user is dead
		if(enemy.isDead) { // Add points if enemy is dead
			System.out.println("Enemy has fallen!");
			return EnemyCharacter.POINTS_FOR_KILLING;
		} else return 0; // User is dead, dont return any points
	}
	public static int bombThreat(UserCharacter user) {
		Bomb boom =  new Bomb(difficulty); // Create bomb
		user.printCharacterInteraction(boom); // Start bomb scenario from here
		System.out.println("#@!* it's a bomb!");
		Boolean defused = boom.defuseMe();
		if(defused) {
			return Bomb.POINTS_FOR_DEFUSING; // Add points if defused
		} else {
			boom.attack(user); // Otherwise player takes damage
			if(!user.isDead) {
				System.out.println("You now have: " +user); // Show health
			}	return 0;
		}
	}
	public static void doctorAppears(UserCharacter user) {
		System.out.println("👴 : Hello, would you like to be healed or would you prefer a special pill?");
		int input = 0;
		while(input < 1) { // Choose heal or pill from doctor
			System.out.println("Press 1 for heal or 2 for pill");
			input = queryIntegerUserInput(1, 2);
		}
		if(input == 1) 
			healUp(user); // Heal user
		else if(input == 2) {
			getPill(user); // Give pill buff
		}
	}
	private static void getPill(UserCharacter user) { // Pill scenario
		new DoctorCharacter("💊", difficulty).printCharacter();
		if(rand.nextInt(100) < 50) { // 50% chance pill will be successful 
			System.out.println("You take the pill Dr.Nick gave you and instantly gain +"+DoctorCharacter.PILL_BUFF +" damage");
			user.damagePerHit += DoctorCharacter.PILL_BUFF;
		} else { // 50% chance pill will be unsuccessful 
			System.out.print("You take the pill Dr.Nick gave you and start to feel sick");
			if(user.damagePerHit - DoctorCharacter.PILL_BUFF > 0) { //prevent user from having 0 damage per hit
				System.out.println(", you now deal -"+DoctorCharacter.PILL_BUFF +" damage");
				user.damagePerHit -= DoctorCharacter.PILL_BUFF;
			} else System.out.println();
		}
	}
	public static void healUp(UserCharacter user) { // Heal scenario
		DoctorCharacter doc = new DoctorCharacter(difficulty);
		doc.printCharacter();
		doc.healUser(user); // Heal amount depends on difficulty
		System.out.println("Dr.Nick has healed you up! You now have: "+user);
	}
	public static int queryIntegerUserInput(int lowerLimit, int upperLimit) {
		// Method used to get integer input within a given range.
		// The input will be returned, and if not within the range -1 will be returned.
		int input = 0;	
		try {	
			input = userInput.nextInt();
			userInput.nextLine();
			if(input >= lowerLimit && input <= upperLimit) {
				return input;
			} else {
				System.err.println("Unexpected Input Error! Please Try Again...\n");
			}
		} catch(InputMismatchException e) {
			userInput.nextLine();
			System.err.println("Unexpected Input Error! Please Try Again...\n");
		}
		return -1;
	}
}