package items.Armor;

import items.Item;
import items.Rarity;

/**
 * ChestPiece armor class 
 * @author Matthew Gimbut
 *
 */
public class ChestPiece extends Armor {

	public ChestPiece() {
		super(0, 8, 4, 9, 0, 150, Item.randomRareness(), Armor.getRandomArmorType());
		this.setImageLocation("Images\\Armors\\" + this.getArmorType().toString() + "\\Down\\Chest.png");
	}

	@Override
	public String getItemToolTipText() {
		return "A " + this.getArmorType() + " chestpiece.";
	}

	@Override
	public String getSimpleName() {
		return this.getArmorType() + " chestpiece";
	}

}
