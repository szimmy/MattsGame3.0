package characters;

import javax.swing.JPanel;

import gui.StatusBarPanel;
import main.GameController;

/**
 * Enemy subclass for all enemies to be implemented
 * @author Matthew Gimbut
 *
 */
public class Enemy extends Character {

	private String currentImage = EnemyTypes.TestEnemy.toString();
	private boolean isSelected;
	
	public Enemy(String name, int lvl, int currentHP, int maxHP, int atk, int def, int speed, int currentCarry,
			int carryCap) {
		super(name, lvl, currentHP, maxHP, atk, def, speed, currentCarry, carryCap);
		this.isSelected = false;
	}
	
	public Enemy(String name) {
		super(name , 1, 100, 100, 4, 4, GameController.getRandomSpeed(), 0, 10);
		this.isSelected = false;
	}

	public void setSelected(boolean isSelected) {
		this.isSelected = isSelected;
	}
	
	public boolean getSelected() {
		return isSelected;
	}
		
}
