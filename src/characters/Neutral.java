package characters;

public class Neutral extends Character {

	private String[] dialogue;
	
	public Neutral(String name, int lvl, int currentHP, int maxHP, int atk, int def, int speed, int currentCarry, int carryCap) {
		super(name, lvl, currentHP, maxHP, atk, def, speed, currentCarry, carryCap);
	}
	
	public Neutral(String name, String image) {
		super(name, 1, 100, 100, 1, 1, 1, 0, 10);
		this.setImage(image);
	}
	
	public Neutral() {
		super("GenericNeutralName", 1, 100, 100, 1, 1, 1, 0, 10);
		//TODO have some sort of default neutral image here
		this.setImage("");
	}
	
	public Neutral(String name) {
		super(name, 1, 100, 100, 1, 1, 1, 0, 10);
		//TODO have some sort of default neutral image here
		this.setImage("");
	}


}
