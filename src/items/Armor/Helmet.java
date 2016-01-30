package items.Armor;

import items.Item;
import items.Rarity;

/**
 * Helmet armor class
 * @author Matthew Gimbut
 *
 */
public class Helmet  extends Armor {
	
	public Helmet() {
		super(0, 6, 1, 3, 0, 80, Item.randomRareness(), Armor.getRandomArmorType());
		this.setImageLocation("Images\\Armors\\" + this.getArmorType().toString() + "\\Down\\Helmet.png");
	}

	@Override
	public String getItemToolTipText() {
		if(this.getArmorType().equals(ArmorType.cloth)) {
			return "A " + this.getArmorType() + " hood.";
		}
		else {
			return "A " + this.getArmorType() + " helmet.";
		}
	}

	@Override
	public String getSimpleName() {
		if(this.getArmorType().equals(ArmorType.cloth)) {
			return this.getArmorType() + " hood";
		}
		else {
			return this.getArmorType() + " helmet";
		}
	}
}
