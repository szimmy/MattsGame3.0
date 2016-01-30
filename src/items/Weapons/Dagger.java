package items.Weapons;

import items.Item;
import items.Rarity;

/**
 * Generic weapon class for daggers
 * @author Matthew Gimbut
 *
 */
public class Dagger extends Weapon {

	public Dagger(int atk, int def, int speedModifier, double weight, int hpBoost, int value, Rarity howRare, WeaponType weaponType) {
		super(atk, def, speedModifier, weight, hpBoost, value, howRare, weaponType);
		System.out.println(this.getWeaponType());
		this.setImageLocation("Images\\Weapons\\" + this.getWeaponType().toString() + "\\dagger\\Dagger.png");
	}
	
	public Dagger() {
		super(6, 0, 1, 2, 0, 75, Item.randomRareness(), Weapon.getRandomWeaponType());
		this.setImageLocation("Images\\Weapons\\" + this.getWeaponType().toString() + "\\dagger\\Dagger.png");
	}

	@Override
	public String getSimpleName() {
		return this.getWeaponType() + " dagger";
	}

	@Override
	public String getItemToolTipText() {
		return "A rusty knife. Add more text here.";
	}
	
	
}
