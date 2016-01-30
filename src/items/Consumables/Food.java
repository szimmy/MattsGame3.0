package items.Consumables;

import items.Rarity;

public class Food extends Consumable {

	public Food(int atk, int def, int speedModifier, double weight, int hpBoost, int value, Rarity howRare) {
		super(atk, def, speedModifier, weight, hpBoost, value, howRare);
	}

	@Override
	public String getItemToolTipText() {
		return null;
	}

	@Override
	public String getSimpleName() {
		return null;
	}
}
