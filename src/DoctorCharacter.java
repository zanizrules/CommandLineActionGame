/**
 * The DoctorCharacter class is one of the Sub-Classes of the Character class.
 * It specifies values specific to a the doctor.
 * The doctor supplies both pill buffs and health.
 * @author Shane Birdsall
 */
public class DoctorCharacter extends Character {
	public static final int PILL_BUFF = 10; // How effective the pill is (+10 damage, or -10 if unlucky)
	public DoctorCharacter(String equipment, Difficulty difficulty) {
		super((-5*difficulty.healM),"  👴","/|\\"+equipment,"/ \\", difficulty); // -50 represents healing 50LP
		this.health = 999; // Don't try kill the doctor, he is your friend :(
	}
	public DoctorCharacter(Difficulty difficulty) {
		this("💉", difficulty); //Default is healing.
	}
	public void healUser(UserCharacter user) { 
		user.health -= this.damagePerHit;
	}
}