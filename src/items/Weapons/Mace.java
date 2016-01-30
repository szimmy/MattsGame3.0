package items.Weapons;

import items.Item;
import items.Rarity;

/**
 * Generic weapon class for maces
 * @author Matthew Gimbut
 *
 */
public class Mace extends Weapon {

	public Mace(int atk, int def, int speedModifier, double weight, int hpBoost, int value, Rarity howRare,
			WeaponType weaponType) {
		super(atk, def, speedModifier, weight, hpBoost, value, howRare, weaponType);
		this.setImageLocation("Images\\Weapons\\" + this.getWeaponType().toString() + "\\mace\\Mace.png");
	}
	
	public Mace() {
		super(12, 0, 4, 7, 0, 178, Item.randomRareness(), Weapon.getRandomWeaponType());
		this.setImageLocation("Images\\Weapons\\" + this.getWeaponType().toString() + "\\mace\\Mace.png");
	}

	@Override
	public String getSimpleName() {
		return this.getWeaponType() + " mace";
		}

	@Override
	public String getItemToolTipText() {
		return "A rusty knife. Add more text here.";	
		}

	
}
