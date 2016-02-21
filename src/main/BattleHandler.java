package main;

import java.util.Random;

import gui.BattleGUI;
import javax.swing.Timer;

import characters.Enemy;
import characters.MainPlayer;


/**
 * BattleHandler class does all of the math for combat.
 * Takes the burden off of the BattleGUI and increases use of the MVC pattern
 * @author Matthew Gimbut
 *
 */
public class BattleHandler {

	private static final int critChance = 100;
	private double damageMultiplier;
	private Random rand;
	private BattleGUI battle;
	private Timer t; 
	private Timer xp;
	private int damageCounter;
	private int xpCounter;
	
	//Will only be true when the timers for changing the stat bars are running.
	//This is to prevent timers from being reactivated while still running.
	//This happened a few times in testing and usually results in infinite timers which I don't want.
	private boolean isProcessing;
	

	/**
	 * Only constructor for the battlehandler takes a BattleGUI object
	 *  in order to adjust the GUI as needed
	 * @param battle The BattleGUI where the battle is taking place
	 */
	public BattleHandler(BattleGUI battle) {
		damageMultiplier = 1.0;
		rand = new Random();
		this.battle = battle;
		damageCounter = 0;
		xpCounter = 0;
		isProcessing = false;
	}
	
	/**
	 * The main method for attacking. 
	 * @param attacker The attacking character
	 * @param defender The defending character
	 */
	public void attack(characters.Character attacker, characters.Character defender) {
		isProcessing = true;
		boolean isCrit = determineCritDamage();
				
		int damage = determineDamage(attacker.getAtk(), defender.getDef())*3;
		
		if(damage < 0) {
			battle.soundPlayer("Sounds\\Attack\\Sword Clang " + rand.nextInt(4) + ".mp3", GameController.effectVolume);
		}
		
		t = new Timer(GameController.TIMER_CONTROLLER, event -> {
			if(damageCounter >= -damage) { //Normally, the damage stat is negative to subtract health. Negated to make it pos.
				t.stop();
				damageCounter = 0;
				checkForDeath(attacker, defender);
				isProcessing = false;
			}
			else {
				checkForDeath(attacker, defender);
				defender.modifyCurrentHP(-1); //Modifying the health by -1 each tick allows the HP bar to appear to drain
				adjustHealthBar(defender);
				battle.repaint();
				damageCounter++;
			}
		});
		t.start();
		
		alertBuilder(attacker.getName(), defender.getName(), damage, isCrit);
		
	}
	
	/**
	 * Sets the new value of the defender's health bar to its new health value.
	 * Pretty much just updates the defender's health bar.
	 * @param defender The defender 
	 */
	private void adjustHealthBar(characters.Character defender) {
		if(defender instanceof MainPlayer) {
			battle.getPlayerHealthBar().setBarValue(defender.getCurrentHP(), defender.getMaxHP());
		}
		else {
			int enemyIndex = battle.getEnemies().indexOf(defender);
			try {
				//Gets an array containing all of the enemies health bars. 
				//Tries to set the value at a certain index, if that index does not exist
				//the enemy is dead and the exception is handled appropriately.
				battle.getEnemyHealthBars()[enemyIndex].setBarValue(defender.getCurrentHP(), defender.getMaxHP());
			}
			catch (ArrayIndexOutOfBoundsException e) {}
		}
	}
	
	/**
	 * Checks to see if an attacker has killed a defender and handles the event accordingly.
	 * @param attacker The character who is attacking.
	 * @param defender The character who is defending. 
	 */
	private void checkForDeath(characters.Character attacker, characters.Character defender) {
		if(defender.getCurrentHP() == 0) {
			damageCounter = 0;
			defender.setDead(true);
			alertBuilder(defender.getName() + " has been defeated.");
			if(defender instanceof Enemy) {
				handleEnemyDeath(attacker, defender);
				battle.updateGUI();
			}
			else if(defender instanceof MainPlayer) {
				handlePlayerDeath(attacker, defender);
				battle.updateGUI();
			}
			
			t.stop();
		}
	}
	
	/**
	 * Method to handle the game if the player dies. 
	 * @param attacker The enemy attacking the player.
	 * @param defender The player who has died.
	 */
	private void handlePlayerDeath(characters.Character attacker, characters.Character defender) {
		battle.soundPlayer("Sounds\\Death\\PlayerDead " + rand.nextInt(2) + ".mp3", GameController.voiceVolume);
		
		defender.setDead(true);
	
			if (battle.getEnemyAttackCounter().isRunning()) {
				battle.getEnemyAttackCounter().stop();
			}
			if (battle.getEnemyAttackFastCounter().isRunning()) {
				battle.getEnemyAttackFastCounter().stop();
			}
			if (battle.getEnemyAttackSlowCounter().isRunning()) {
				battle.getEnemyAttackSlowCounter().stop(); 
			}
		battle.getInfoLabel().setText("Game over!");
		battle.getLeave().setEnabled(true);
	}
	
	/**
	 * Method to handle the game if the enemy being attacked dies. 
	 * @param attacker The player that is currently attacking. 
	 * @param defender The enemy who has died. 
	 */
	private void handleEnemyDeath(characters.Character attacker, characters.Character defender) {
		battle.soundPlayer("Sounds\\Death\\Scream " + rand.nextInt(20) + ".mp3", GameController.voiceVolume);
		battle.getEnemies().remove(defender);
		battle.resetSelectedEnemy();
		int xpGain = (defender.getLvl() + defender.getAtk() + defender.getDef() + defender.getSpeed())*3;
		modifyXPValue((MainPlayer) attacker, xpGain);
		defender = null;
	}
	
	/**
	 * Like with health bars, a timer is used to raise xp by 1 until it reaches
	 * the cap of xp gained at that time to achieve the illusion of animation.
	 * spooky stuff, huh?
	 * @param player The current player of the game.
	 * @param xpGain The total amount of xp gained from defeating an enemy.
	 */
	private void modifyXPValue(MainPlayer player, int xpGain) {
		isProcessing = true;

		xp = new Timer(GameController.TIMER_CONTROLLER, event -> {
			if (xpCounter == xpGain) {
				xpCounter = 0;
				battle.updateGUI();
				isProcessing = false;			
				xp.stop();
			}
			else {
				//Enemies do not have xp, so this was a bit more straightforward that the hp bars
				player.increaseXP(1);
				battle.getPlayerXPBar().setBarValue(player.getXp(), 100);
				battle.repaint();
				xpCounter++;
			}
		});
		xp.start();
	}
	
	/**
	 * Uses the static final critChance variable to see if an attack is a crit.
	 * Sets damage multiplier to 2.0 instead of 1.0 if the 3/100 chance is successful.
	 * @return True if the attack is a crit, false if not. 
	 */
	private boolean determineCritDamage() {
		int crit = rand.nextInt(critChance);
		if (crit < 3) {
			damageMultiplier = 2.0;
			return true;
		}
		else {
			damageMultiplier = 1.0;
			return false;
		}
	}
	
	/**
	 * Builds a string to be displayed on the BattleGUI based off of the current attack.
	 * @param attackerName Name of the attacking character.
	 * @param defenderName Name of the defending character. 
	 * @param damage Damage dealt by the attacker. 
	 * @param isCrit Whether or not it was a critical hit.
	 */
	private void alertBuilder(String attackerName, String defenderName, int damage, boolean isCrit) {
		if (damage == 0) {
			battle.alert(attackerName + "'s attack on " + defenderName + " missed!");
		}
		else {
			if(isCrit) {
				battle.alert(attackerName + " landed a critical hit, dealing " + -damage + " damage to " + defenderName + "!");
			}
			else {
				battle.alert(attackerName + " has dealt " + -damage + " damage to " + defenderName + "!");
			}
		}

	}
	
	/**
	 * Sends a specified string to the BattleGUI to be displayed.
	 * Rather useless overloaded version of the alertBuilder method as it is no
	 * different than just calling the alert method on the BattleGUI object.
	 * Left here for future development.
	 * @param message The message to be displayed in the BattleGUI.
	 */
	private void alertBuilder(String message) {
		battle.alert(message);
	}
	
	private int determineDamage(int atk, int def) {
		int damage = 0;
		
		damage = (int) -Math.round(((atk / def)) + rand.nextInt(5) * damageMultiplier);
		
		return damage;
	}

	/**
	 * Standard getter method for the isProcessing boolean. 
	 * @return True if the attack is processing, false if it has finished. 
	 */
	public boolean isProcessing() {
		return isProcessing;
	}
	
}
