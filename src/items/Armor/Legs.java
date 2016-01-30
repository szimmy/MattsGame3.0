package items.Armor;

import items.Item;
import items.Rarity;

/**
 * Legs armor class
 * @author Matthew Gimbut
 *
 */
public class Legs extends Armor {

	public Legs() {
		super(0, 5, 2, 5, 0, 75, Item.randomRareness(), Armor.getRandomArmorType());
		this.setImageLocation("Images\\Armors\\" + this.getArmorType().toString() + "\\Down\\Legs.png");
	}

	@Override
	public String getItemToolTipText() {
		return "A pair of " + this.getArmorType() + " leggings.";
	}

	@Override
	public String getSimpleName() {
		return this.getArmorType() + " legs";
	}
}
