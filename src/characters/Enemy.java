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
	private String customMusic;
	private boolean isSelected;
	
	public Enemy(String name, int lvl, int currentHP, int maxHP, int atk, int def, int speed, int currentCarry,
			int carryCap) {
		super(name, lvl, currentHP, maxHP, atk, def, speed, currentCarry, carryCap);
		this.isSelected = false;
	}
	
	public Enemy(String name) {
		super(name , 1, 100, 100, 4, 4, GameController.getRandomSpeed(), 0, 10);
		this.isSelected = false;
		this.setImage("Images\\Enemies\\Enemy0.png");
		this.customMusic = "";
	}
	
	public Enemy(String name, String image) {
		super(name , 1, 100, 100, 4, 4, GameController.getRandomSpeed(), 0, 10);
		this.isSelected = false;
		this.setImage(image);
		this.customMusic = "";
	}
	
	public Enemy(String name, String image, String customMusic) {
		super(name , 1, 100, 100, 4, 4, GameController.getRandomSpeed(), 0, 10);
		this.isSelected = false;
		this.setImage(image);
		this.customMusic = customMusic;
	}

	public Enemy(String name, int lvl, int hp, int atk, int def, int speed, String image, String customMusic) {
		super(name , lvl, hp, hp, atk, def, speed, 0, 100);
		this.isSelected = false;
		this.setImage(image);
		this.customMusic = customMusic;
	}
	
	public void setSelected(boolean isSelected) {
		this.isSelected = isSelected;
	}
	
	public boolean getSelected() {
		return isSelected;
	}

	public String getCustomMusic() {
		return customMusic;
	}

	public void setCustomMusic(String customMusic) {
		this.customMusic = customMusic;
	}
		
}
