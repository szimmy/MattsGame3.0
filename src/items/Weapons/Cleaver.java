package items.Weapons;

import items.Item;
import items.Rarity;

/**
 * Unique weapon class for the cleaver
 * @author Matthew Gimbut
 *
 */
public class Cleaver extends Weapon {
	
	public Cleaver() {
		super(5, 0, 2, 4, 0, 15, Item.randomRareness(), WeaponType.iron);
		this.setImageLocation("Images\\Weapons\\Cleaver\\Cleaver.png");
	}

	@Override
	public String getSimpleName() {
		return "cleaver";
	}

	@Override
	public String getItemToolTipText() {
		return "A rusty knife. Add more text here.";
	}


}
