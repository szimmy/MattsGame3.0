package gui;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import characters.MainPlayer;
import items.Item;
import items.Armor.Armor;
import items.Consumables.Consumable;
import items.Consumables.Potion;
import items.Weapons.Weapon;
import main.GameController;

import java.awt.*;

/**
 * 
 * A tabbed panel to display the users inventory.
 * Sorts the inventory into weapons, armor, consumables,
 * and miscellaneous items. 
 * 
 * Replaces InventoryGUI. Don't use that anymore, use this.
 * 
 * @author Matthew Gimbut
 *
 */
public class TabbedInventory extends JPanel {

	private JTabbedPane tabbedPane;
	private JPanel weapons;
	private JPanel armors;
	private JPanel consumables;
	private JPanel misc;
	private MainPlayer player;
	private ViewPanel currentView;
	private JLabel info;
	private JLabel gold;
	private GameButton exit;
	private int currentWeaponCount;
	private int currentArmorCount;
	private int currentConsumableCount;
	private int currentMiscCount;
	private final int ITEMS_PER_ROW = 4;
	private final int X_PADDING = 50;
	private final int Y_PADDING = 10;
	
	/**
	 * Constructor for TabbedInventory objects. 
	 * @param player The current player of the game.
	 * @param currentView The current ViewPanel object.
	 */
	public TabbedInventory(MainPlayer player, ViewPanel currentView) {
		this.player = player;
		this.currentView = currentView;
		
		exit = new GameButton("Exit");
		info = new JLabel("");
		gold = new JLabel("Current gold: " + player.getGold());
		weapons = new JPanel();
		weapons.setLayout(new GridLayout(0,ITEMS_PER_ROW,X_PADDING,Y_PADDING));
		weapons.setOpaque(false);
		armors = new JPanel();
		armors.setLayout(new GridLayout(0,ITEMS_PER_ROW,X_PADDING,Y_PADDING));
		armors.setOpaque(false);
		consumables = new JPanel();
		consumables.setLayout(new GridLayout(0,ITEMS_PER_ROW,X_PADDING,Y_PADDING));
		consumables.setOpaque(false);
		misc = new JPanel();
		misc.setLayout(new GridLayout(0,ITEMS_PER_ROW,X_PADDING,Y_PADDING));
		misc.setOpaque(false);
				
		initPane();
		
		exit.addActionListener(event -> currentView.removeInventoryPanel(this));
		JPanel southPanel = new JPanel();
		southPanel.setBackground(GameController.BACKGROUND_COLOR_THEME);
		southPanel.add(exit);
		JPanel centerPanel = new JPanel();
		centerPanel.setBackground(GameController.BACKGROUND_COLOR_THEME);
		centerPanel.add(info);
		centerPanel.add(gold);
		
		JPanel northPanel = new JPanel();
		northPanel.setBackground(GameController.BACKGROUND_COLOR_THEME);
		northPanel.add(tabbedPane);
		
		sortItems();		
		
		this.setOpaque(false);
		this.setLayout(new BorderLayout());
		this.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
		this.add(northPanel, BorderLayout.NORTH);
		this.add(centerPanel, BorderLayout.CENTER);
		this.add(southPanel, BorderLayout.SOUTH);
	}
	
	/**
	 * Initializes all of the settings for the tabbed pane.
	 * Each panel that contains items is added to another panel
	 * for padding to provide proper spacing because swing is a bitch.
	 */
	private void initPane() {
		tabbedPane = new JTabbedPane();
        //tabbedPane = new JTabbedPane(JTabbedPane.LEFT);
		JPanel weaponPadding = new JPanel();
		weaponPadding.setBackground(GameController.BUTTON_COLOR_THEME);
		weaponPadding.add(weapons);
		tabbedPane.addTab("Weapons", weaponPadding);
		JPanel armorPadding = new JPanel();
		armorPadding.setBackground(GameController.BUTTON_COLOR_THEME);
		armorPadding.add(armors);
		tabbedPane.addTab("Armor", armorPadding);
		JPanel consumablePadding = new JPanel();
		consumablePadding.add(consumables);
		consumablePadding.setBackground(GameController.BUTTON_COLOR_THEME);
		tabbedPane.addTab("Consumables", consumablePadding);
		JPanel miscPadding = new JPanel();
		miscPadding.add(misc);
		miscPadding.setBackground(GameController.BUTTON_COLOR_THEME);
		tabbedPane.addTab("Miscellaneous", miscPadding);
		tabbedPane.setOpaque(false);
		tabbedPane.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(), player.getName() + "'s inventory", TitledBorder.CENTER,
				TitledBorder.CENTER, GameController.GAME_FONT, Color.BLACK));
	}
	
	/**
	 * Removes all items from panels and sets item counts to zero.
	 * then goes through and adds each item to its proper tab.
	 * If any of the tabs are empty, the appropriate label is added.
	 */
	private void sortItems() {
		weapons.removeAll();
		armors.removeAll();
		consumables.removeAll();
		misc.removeAll();
		currentWeaponCount = 0;
		currentArmorCount = 0;
		currentConsumableCount = 0;
		currentMiscCount = 0;
		
		player.getInventory().forEach(item -> {
			if(item instanceof Weapon) {
				initWeapon((Weapon) item);
				currentWeaponCount++;
			} else if (item instanceof Armor) {
				initArmor((Armor) item);
				currentArmorCount++;
			} else if (item instanceof Consumable) {
				initConsumable((Consumable) item);
				currentConsumableCount++;
			} else if (item instanceof Item){ //All remaining items are miscellaneous at this point
				initMisc(item);
				currentMiscCount++;
			}
			this.revalidate();
			this.repaint();
		});
		checkEmpty();
	}
	
	/**
	 * Checks all of the item count variables to check if they are zero.
	 */
	private void checkEmpty() {
		if(currentWeaponCount == 0) {
			weapons.add(new JLabel("No weapons."));
		}
		if(currentArmorCount == 0) {
			armors.add(new JLabel("No armors."));
		}
		if(currentConsumableCount == 0) {
			consumables.add(new JLabel("No consumables."));
		}
		if(currentMiscCount == 0) {
			misc.add(new JLabel("No miscellaneous."));
		}
	}
	
	//TODO: Refactor these following methods to reduce similar code??
	
	/**
	 * Initializes an ItemPanel for a weapon in the weapon tab.
	 * @param w The current weapon object to be displayed.
	 */
	private void initWeapon(Weapon w) {
		JPanel itemButtonGroupPanel = new JPanel();
		itemButtonGroupPanel.setOpaque(false);
		itemButtonGroupPanel.setLayout(new GridLayout(0,1));
		
		JPanel weaponPanel = new JPanel();
		weaponPanel.setOpaque(false);

		JPanel btnPanel = new JPanel();
		btnPanel.setOpaque(false);

		weaponPanel.setLayout(new GridLayout(0, 1));

		JPanel formatPanel = new JPanel();
		formatPanel.setOpaque(false);

		btnPanel.setLayout(new GridLayout(1, 2));
		
		weaponPanel.add(new ItemPanel(w));
		
		GameButton equip = new GameButton("Equip");
		equip.setBackground(GameController.LIGHTER_GRAY);
		equip.addActionListener(event -> {
			player.equip(w);
			sortItems();
			info.setText("Equipped " + w.getSimpleName() + ". ");
		});

		btnPanel.add(equip);
		
		GameButton drop = new GameButton("Drop");
		drop.setBackground(GameController.LIGHTER_GRAY);
		drop.addActionListener(event -> {
			player.removeSingleItem(w);
			drop.setEnabled(false);
			info.setText("Dropped " + w.getSimpleName() + ".");
			sortItems();
		});
		
		btnPanel.add(drop);
		formatPanel.add(btnPanel);
		itemButtonGroupPanel.add(weaponPanel);
		itemButtonGroupPanel.add(formatPanel);
		weapons.add(itemButtonGroupPanel);
	}
	
	/**
	 * Initializes an ItemPanel for an armor in the armor tab.
	 * @param a The armor object to be displayed.
	 */
	private void initArmor(Armor a) {
		JPanel itemButtonGroupPanel = new JPanel();
		itemButtonGroupPanel.setOpaque(false);
		itemButtonGroupPanel.setLayout(new GridLayout(0,1));
		
		JPanel armorPanel = new JPanel();
		armorPanel.setOpaque(false);

		JPanel btnPanel = new JPanel();
		btnPanel.setOpaque(false);

		armorPanel.setLayout(new GridLayout(0, 1));

		JPanel formatPanel = new JPanel();
		formatPanel.setOpaque(false);

		btnPanel.setLayout(new GridLayout(1, 2));
		
		armorPanel.add(new ItemPanel(a));
		
		GameButton equip = new GameButton("Equip");
		equip.setBackground(GameController.LIGHTER_GRAY);
		equip.addActionListener(event -> {
			player.equip(a);
			GameController.playSound("Sounds\\Inventory\\Equip\\leather_inventory.mp3");
			sortItems();
			info.setText("Equipped " + a.getSimpleName() + ". ");
		});

		btnPanel.add(equip);
		
		GameButton drop = new GameButton("Drop");
		drop.setBackground(GameController.LIGHTER_GRAY);
		drop.addActionListener(event -> {
			player.removeSingleItem(a);
			drop.setEnabled(false);
			info.setText("Dropped " + a.getSimpleName() + ".");
			sortItems();
		});
		
		btnPanel.add(drop);
		formatPanel.add(btnPanel);
		itemButtonGroupPanel.add(armorPanel);
		itemButtonGroupPanel.add(formatPanel);
		armors.add(itemButtonGroupPanel);
	}
	
	/**
	 * Initializes an ItemPanel for consumable in the consumable tab.
	 * @param c The consumable object to be displayed.
	 */
	private void initConsumable(Consumable c) {
		JPanel itemButtonGroupPanel = new JPanel();
		itemButtonGroupPanel.setOpaque(false);
		itemButtonGroupPanel.setLayout(new GridLayout(0,1));
		
		JPanel consumablePanel = new JPanel();
		consumablePanel.setOpaque(false);

		JPanel btnPanel = new JPanel();
		btnPanel.setOpaque(false);

		consumablePanel.setLayout(new GridLayout(0, 1));

		JPanel formatPanel = new JPanel();
		formatPanel.setOpaque(false);

		btnPanel.setLayout(new GridLayout(1, 2));
		
		consumablePanel.add(new ItemPanel((Potion) c));
		
		GameButton consume = new GameButton("Drink");
		consume.setBackground(GameController.LIGHTER_GRAY);
		consume.addActionListener(event -> {
			player.consume((Potion) c);
			info.setText("Restored " + ((Potion) c).getAmount() + " health.");
			sortItems();
		});

		btnPanel.add(consume);
		
		GameButton drop = new GameButton("Drop");
		drop.setBackground(GameController.LIGHTER_GRAY);
		drop.addActionListener(event -> {
			player.removeSingleItem(c);
			drop.setEnabled(false);
			info.setText("Dropped " + c.getSimpleName() + ".");
			sortItems();
		});
		
		btnPanel.add(drop);
		formatPanel.add(btnPanel);
		itemButtonGroupPanel.add(consumablePanel);
		itemButtonGroupPanel.add(formatPanel);
		consumables.add(itemButtonGroupPanel);
	}
	
	/**
	 * Initializes an ItemPanel for a miscellaneous in the miscellaneous tab.
	 * @param i The misc object to be displayed.
	 */
	private void initMisc(Item i) {
		JPanel itemButtonGroupPanel = new JPanel();
		itemButtonGroupPanel.setOpaque(false);
		itemButtonGroupPanel.setLayout(new GridLayout(0,1));
		
		JPanel miscPanel = new JPanel();
		miscPanel.setOpaque(false);

		JPanel btnPanel = new JPanel();
		btnPanel.setOpaque(false);

		miscPanel.setLayout(new GridLayout(0, 1));

		JPanel formatPanel = new JPanel();
		formatPanel.setOpaque(false);

		miscPanel.add(new ItemPanel());
		
		GameButton drop = new GameButton("Drop");
		drop.addActionListener(event -> {
			player.removeSingleItem(i);
			drop.setEnabled(false);
			info.setText("Dropped " + i.getSimpleName() + ".");
			sortItems();
		});
		
		btnPanel.add(drop);
		formatPanel.add(btnPanel);
		itemButtonGroupPanel.add(miscPanel);
		itemButtonGroupPanel.add(formatPanel);
		misc.add(itemButtonGroupPanel);
	}
	
}
