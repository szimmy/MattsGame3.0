package items.Weapons;

import items.Item;
import items.Rarity;

/**
 * Unique weapon class for the rusty knife weapon
 * @author Matthew
 *
 */
public class RustyKnife extends Weapon {
	
	public RustyKnife() {
		super(5, 0, 1, 2, 0, 10, Item.randomRareness(), WeaponType.iron);
		this.setImageLocation("Images\\Weapons\\RustyKnife\\RustyKnife.png");
	}
	
	@Override
	public String getSimpleName() {
		return "rusty knife";
	}

	@Override
	public String getItemToolTipText() {
		return "A rusty knife. Add more text here.";	
		}
	
}
