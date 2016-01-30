package items.Consumables;

import items.Rarity;

public class Potion extends Consumable {

	private String type;
	private int amount;
	


	public Potion(int atk, int def, int speedModifier, double weight, int hpBoost, int value, Rarity howRare, String type) {
		super(atk, def, speedModifier, weight, hpBoost, value, howRare);
		this.type = type;
		this.setImageLocation("Images\\Consumables\\Potions\\" + type + "Potion.png");
	}

	public Potion(String type, int amount) {
		super(0, 0, 0, .1, 0, 100, Rarity.COMMON);
		this.type = type;
		this.amount = amount;
		this.setImageLocation("Images\\Consumables\\Potions\\" + type + "Potion.png");
	}
	
	@Override
	public String getItemToolTipText() {
		return "A " + type + " potion.";
	}

	@Override
	public String getSimpleName() {
		return type + " potion";
	}

	public int getAmount() {
		return amount;
	}
	
}
