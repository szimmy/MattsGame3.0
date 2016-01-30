package items.Consumables;

import items.Rarity;

public class Drink extends Consumable {

	public Drink(int atk, int def, int speedModifier, double weight, int hpBoost, int value, Rarity howRare) {
		super(atk, def, speedModifier, weight, hpBoost, value, howRare);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String getItemToolTipText() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getSimpleName() {
		// TODO Auto-generated method stub
		return null;
	}
}
