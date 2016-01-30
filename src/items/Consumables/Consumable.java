package items.Consumables;

import items.Item;
import items.Rarity;

public abstract class Consumable extends Item {

	public Consumable(int atk, int def, int speedModifier, double weight, int hpBoost, int value, Rarity howRare) {
		super(atk, def, speedModifier, weight, hpBoost, value, howRare);
	}	
	
}
