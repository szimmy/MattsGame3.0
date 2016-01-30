package items.Armor;

import items.Item;
import items.Rarity;

/**
 * Gloves armor class
 * @author Matthew Gimbut
 *
 */
public class Gloves extends Armor {

	public Gloves() {
		super(0, 2, 1, 2, 0, 50, Item.randomRareness(), Armor.getRandomArmorType());
		this.setImageLocation("Images\\Armors\\" + this.getArmorType().toString() + "\\Down\\Gloves.png");
	}

	@Override
	public String getItemToolTipText() {
		if(this.getArmorType().equals(ArmorType.steel) 
				|| this.getArmorType().equals(ArmorType.bronze)
				|| this.getArmorType().equals(ArmorType.iron)
				|| this.getArmorType().equals(ArmorType.wood)) {
			return "A pair of " + this.getArmorType() + " gauntlets";
		}
		else {
			return "A pair of " + this.getArmorType() + " gloves";
		}
	}

	/**
	 * Changes the name based on what type of armor it is.
	 * Steel gloves would be a bit uncomfortable
	 * so I changed them to gauntlets.
	 */
	@Override
	public String getSimpleName() {
		if(this.getArmorType().equals(ArmorType.steel) 
				|| this.getArmorType().equals(ArmorType.bronze)
				|| this.getArmorType().equals(ArmorType.iron)
				|| this.getArmorType().equals(ArmorType.wood)) {
			return this.getArmorType() + " gauntlets";
		}
		else {
			return this.getArmorType() + " gloves";
		}
	}
}
