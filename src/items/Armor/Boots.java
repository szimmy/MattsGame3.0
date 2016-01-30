package items.Armor;

import items.Item;
import items.Rarity;

/**
 * Armor class for boots
 * @author Matthew Gimbut
 *
 */
public class Boots extends Armor {

	public Boots() {
		super(0, 3, 1, 3.0, 0, 65, Item.randomRareness(), Armor.getRandomArmorType());
		this.setImageLocation("Images\\Armors\\" + this.getArmorType().toString() + "\\Down\\Boots.png");
	}

	@Override
	public String getItemToolTipText() {
		return "A pair of " + this.getArmorType() + " boots.";
	}

	@Override
	public String getSimpleName() {
		return this.getArmorType() + " boots";
	}
}
