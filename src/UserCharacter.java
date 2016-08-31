/**
 * The UserCharacter class is one of the Sub-Classes of the Character class.
 * It specifies values specific to the user's character.
 * @author Shane Birdsall
 */
public class UserCharacter extends Character {
	public UserCharacter(Difficulty difficulty) { // Difficulty supplied by user
		super((25*difficulty.playerDamageM),"ಠ益ಠ" ,"/||\\🔪" ," /\\", difficulty);
	}
	public void printCharacterInteraction(Character char2) {
		//Prints User character scenario with the appropriate spacing
		System.out.println("\n"+this.head +"      " +char2.head);
		System.out.println(this.body +"   " +char2.body);
		System.out.println(this.legs +"    " +char2.legs);
	}
}