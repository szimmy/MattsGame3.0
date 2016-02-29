package items.Weapons;

import items.Item;
import items.Rarity;

/**
 * Unique weapon class for swords
 * @author Matthew Gimbut
 *
 */
public class Sword extends Weapon{

	public Sword(int atk, int def, int speedModifier, double weight, int hpBoost, int value, Rarity howRare,
			WeaponType weaponType) {
		super(atk, def, speedModifier, weight, hpBoost, value, howRare, weaponType);
		this.setImageLocation("Images\\Weapons\\" + this.getWeaponType().toString() + "\\Sword.png");
	}
	
	public Sword() {
		super(8, 0, 2, 4, 0, 175, Item.randomRareness(), Weapon.getRandomWeaponType());
		this.setImageLocation("Images\\Weapons\\" + this.getWeaponType().toString() + "\\sword\\Sword.png");
	}

	@Override
	public String getSimpleName() {
		return this.getWeaponType() + " sword";
	}

	@Override
	public String getItemToolTipText() {
		return "A " + this.getWeaponType() + " sword.";
	}
}
