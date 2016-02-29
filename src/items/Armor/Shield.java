package items.Armor;

import items.Item;
import items.Rarity;

/**
 * Shield armor class
 * @author Matthew Gimbut
 *
 */
public class Shield extends Armor {

	public Shield() {
		super(2, 6, 2, 4, 0, 100, Item.randomRareness(), Armor.getRandomArmorType());
		this.setImageLocation("Images\\Armors\\" + this.getArmorType().toString() +  "\\Shield.png");
	}

	@Override
	public String getItemToolTipText() {
		return "A " + this.getArmorType() + " shield.";
	}

	@Override
	public String getSimpleName() {
		return this.getArmorType() + " shield";
	}

}
