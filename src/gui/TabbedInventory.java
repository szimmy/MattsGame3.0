package gui;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import characters.MainPlayer;
import items.Item;
import items.Armor.Armor;
import items.Consumables.Consumable;
import items.Consumables.Potion;
import items.Weapons.Weapon;
import main.GameController;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

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
	private JPanel weapons, armors, consumables, misc;
	private GameButton nextPage, prevPage;
	private MainPlayer player;
	private JLabel info, gold, page;
	private GameButton exit;
	private int currentWeaponIndex, currentArmorIndex, currentConsumableIndex, currentMiscIndex;
	private int wepPages, armorPages, consumablePages, miscPages;
	private int currentWepPage = 1, currentArmorPage = 1, currentConsumablePage = 1, currentMiscPage = 1;
	private final int ITEMS_PER_ROW = 4;
	private final int X_PADDING = 50;
	private final int Y_PADDING = 10;
	private ArrayList<Weapon> weaponList;
	private ArrayList<Armor> armorList;
	private ArrayList<Consumable> consumableList;
	private ArrayList<Item> miscList;
	
	/**
	 * Constructor for TabbedInventory objects. 
	 * @param player The current player of the game.
	 * @param currentView The current ViewPanel object.
	 */
	public TabbedInventory(MainPlayer player, ViewPanel currentView) {
		this.player = player;
		exit = new GameButton("Exit");
		info = new JLabel("");
		gold = new JLabel("Current gold: " + player.getGold());
		page = new JLabel();
		weaponList = new ArrayList<Weapon>();
		weapons = new JPanel();
		weapons.setLayout(new GridLayout(0,ITEMS_PER_ROW,X_PADDING,Y_PADDING));
		weapons.setOpaque(false);
		armorList = new ArrayList<Armor>();
		armors = new JPanel();
		armors.setLayout(new GridLayout(0,ITEMS_PER_ROW,X_PADDING,Y_PADDING));
		armors.setOpaque(false);
		consumableList = new ArrayList<Consumable>(0);
		consumables = new JPanel();
		consumables.setLayout(new GridLayout(0,ITEMS_PER_ROW,X_PADDING,Y_PADDING));
		consumables.setOpaque(false);
		miscList = new ArrayList<Item>();
		misc = new JPanel();
		misc.setLayout(new GridLayout(0,ITEMS_PER_ROW,X_PADDING,Y_PADDING));
		misc.setOpaque(false);
				
		initPane();
		
		exit.addActionListener(event -> currentView.removeInventoryPanel(this));
		
		nextPage = new GameButton(">>");
		nextPage.addActionListener(event -> {
			switch (tabbedPane.getSelectedIndex()) {
			case 0: // Weapons tab
				currentWeaponIndex += 8;
				currentWepPage++;
				displayWeapons();
				break;
			case 1: // Armor tab
				currentArmorIndex += 8;
				currentArmorPage++;
				displayArmors();
				break;
			case 2: // Consumables tab
				currentConsumableIndex += 8;
				currentConsumablePage++;
				displayConsumables();
				break;
			case 3: // Miscellaneous tab
				currentMiscIndex += 8;
				currentMiscPage++;
				displayMisc();
				break;
			}
			checkItemIndices();
			updatePageNumbers();
		});
		
		prevPage = new GameButton("<<");
		prevPage.addActionListener(event -> {
			switch (tabbedPane.getSelectedIndex()) {
			case 0: //Weapons tab
				currentWeaponIndex -= 8;
				currentWepPage--;
				displayWeapons();
				break;
			case 1: //Armor tab
				currentArmorIndex -= 8;
				currentArmorPage--;
				displayArmors();
				break;
			case 2: //Consumables tab
				currentConsumableIndex -= 8;
				currentConsumablePage--;
				displayConsumables();
				break;
			case 3: //Miscellaneous tab
				currentMiscIndex -= 8;
				currentMiscPage--;
				displayMisc();
				break;
			}
			checkItemIndices();
			updatePageNumbers();
		});
		
		
		JPanel southPanel = new JPanel();
		southPanel.setBackground(GameController.BACKGROUND_COLOR_THEME);
		southPanel.add(prevPage);
		southPanel.add(exit);
		southPanel.add(nextPage);
		JPanel centerPanel = new JPanel();
		centerPanel.setBackground(GameController.BACKGROUND_COLOR_THEME);
		centerPanel.add(info);
		centerPanel.add(page);
		
		JPanel northPanel = new JPanel();
		northPanel.setBackground(GameController.BACKGROUND_COLOR_THEME);
		northPanel.add(tabbedPane);
		
		sortItems();		
		displayItems();
		checkItemIndices();
		updatePageNumbers();
		
		this.setOpaque(false);
		this.setLayout(new BorderLayout());
		this.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
		this.add(northPanel, BorderLayout.NORTH);
		this.add(centerPanel, BorderLayout.CENTER);
		this.add(southPanel, BorderLayout.SOUTH);
	}
	
	private void updatePageNumbers() {
		switch (tabbedPane.getSelectedIndex()) {
		case 0: //Weapons tab
				if (wepPages != 0) {
					page.setText("Page " + currentWepPage + " of " + wepPages);
				} else {
					page.setText("No weapons to display.");
				}
			break;
		case 1: //Armor tab
			if (armorPages != 0) {
				page.setText("Page " + currentArmorPage + " of " + armorPages);
			} else {
				page.setText("No armor to display.");
			}
			break;
		case 2: //Consumables tab
			if (consumablePages != 0) {
				page.setText("Page " + currentConsumablePage + " of " + consumablePages);
			} else {
				page.setText("No consumables to display.");
			}
			break;
		case 3: //Miscellaneous tab
			if(miscPages != 0) {
				page.setText("Page " + currentMiscPage + " of " + miscPages);
			} else {
				page.setText("No miscellaneous items to display.");
			}
			break;
		}
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
		tabbedPane.addChangeListener(new PaneChangeListener());
	}
	

	private void sortItems() {
		weaponList.removeAll(weaponList);
		armorList.removeAll(armorList);
		consumableList.removeAll(consumableList);
		miscList.removeAll(miscList);
		player.getInventory().forEach(item -> {
			if (item instanceof Weapon) {
				weaponList.add((Weapon) item);
			} else if (item instanceof Armor) {
				armorList.add((Armor) item);
			} else if (item instanceof Consumable) {
				consumableList.add((Consumable) item);
			} else {
				miscList.add(item);
			}
		});
		wepPages = (int) Math.ceil(weaponList.size()/8.0);
		armorPages = (int) Math.ceil(armorList.size()/8.0);
		consumablePages = (int) Math.ceil(consumableList.size()/8.0);
		miscPages = (int) Math.ceil(miscList.size()/8.0);
	}
	
	private void displayItems() {
		displayWeapons();
		displayArmors();
		displayConsumables();
		displayMisc();
		checkEmpty();
	}
	
	private void displayWeapons() {
		weapons.removeAll();
		for(int i = currentWeaponIndex; i < currentWeaponIndex+8; i++) {
			try {
				initWeapon(weaponList.get(i));
			} catch (IndexOutOfBoundsException e) {
				//weapons.add(new ItemPanel("placeholder"));
			}
		}
	}
	
	private void displayArmors() {
		armors.removeAll();
		for(int i = currentArmorIndex; i < currentArmorIndex+8; i++) {
			try {
				initArmor(armorList.get(i));
			} catch (IndexOutOfBoundsException e) {
				//armors.add(new ItemPanel());
			}
		}
	}
	
	private void displayConsumables() {
		consumables.removeAll();
		for(int i = currentConsumableIndex; i < currentConsumableIndex+8; i++) {
			try {
				initConsumable(consumableList.get(i));
			} catch (IndexOutOfBoundsException e) {
				//consumables.add(new ItemPanel());
			}
		}
	}
	
	private void displayMisc() {
		misc.removeAll();
		for(int i = currentMiscIndex; i < currentMiscIndex+8; i++) {
			try {
				initMisc(miscList.get(i));
			} catch (IndexOutOfBoundsException e) {
				//misc.add(new ItemPanel());
			}
		}
	}
	
	/**
	 * Checks all of the item count variables to check if they are zero.
	 */
	private void checkEmpty() {
		if(weaponList.size() == 0) {
			weapons.add(new JLabel("No weapons."));
		}
		if(armorList.size() == 0) {
			armors.add(new JLabel("No armors."));
		}
		if(consumableList.size() == 0) {
			consumables.add(new JLabel("No consumables."));
		}
		if(miscList.size() == 0) {
			misc.add(new JLabel("No miscellaneous."));
		}
	}
		
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
		equip.addActionListener(new EquipListener(w));

		btnPanel.add(equip);
		
		GameButton drop = new GameButton("Drop");
		drop.setBackground(GameController.LIGHTER_GRAY);
		drop.addActionListener(new DropListener(w));
		
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
		equip.addActionListener(new EquipListener(a));

		btnPanel.add(equip);
		
		GameButton drop = new GameButton("Drop");
		drop.setBackground(GameController.LIGHTER_GRAY);
		drop.addActionListener(new DropListener(a));
		
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
			updateInventory();
			displayConsumables();
			checkForEmptyConsumable();
		});

		btnPanel.add(consume);
		
		GameButton drop = new GameButton("Drop");
		drop.setBackground(GameController.LIGHTER_GRAY);
		drop.addActionListener(new DropListener(c));
		
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
		drop.addActionListener(new DropListener(i));
		
		btnPanel.add(drop);
		formatPanel.add(btnPanel);
		itemButtonGroupPanel.add(miscPanel);
		itemButtonGroupPanel.add(formatPanel);
		misc.add(itemButtonGroupPanel);
	}
	
	private void checkItemIndices() {
		switch (tabbedPane.getSelectedIndex()) {
		case 0: //Weapons tab
			try {
				weaponList.get(currentWeaponIndex+8);
				nextPage.setEnabled(true);
			} catch (IndexOutOfBoundsException e1) {
				nextPage.setEnabled(false);
			}
			try {
				weaponList.get(currentWeaponIndex-1);
				prevPage.setEnabled(true);
			} catch (IndexOutOfBoundsException e2) {
				prevPage.setEnabled(false);
			}
			
			break;
		case 1: //Armor tab
			try {
				armorList.get(currentArmorIndex+8);
				nextPage.setEnabled(true);
			} catch (IndexOutOfBoundsException e1) {
				nextPage.setEnabled(false);
			}
			try {
				armorList.get(currentArmorIndex-1);
				prevPage.setEnabled(true);
			} catch (IndexOutOfBoundsException e2) {
				prevPage.setEnabled(false);
			}
			break;
		case 2: //Consumables tab
			try {
				consumableList.get(currentConsumableIndex+8);
				nextPage.setEnabled(true);
			} catch (IndexOutOfBoundsException e1) {
				nextPage.setEnabled(false);
			}
			try {
				consumableList.get(currentConsumableIndex-1);
				prevPage.setEnabled(true);
			} catch (IndexOutOfBoundsException e2) {
				prevPage.setEnabled(false);
			}
			break;
		case 3: //Miscellaneous tab
			try {
				miscList.get(currentMiscIndex+8);
				nextPage.setEnabled(true);
			} catch (IndexOutOfBoundsException e1) {
				nextPage.setEnabled(false);
			}
			try {
				miscList.get(currentMiscIndex-1);
				prevPage.setEnabled(true);
			} catch (IndexOutOfBoundsException e2) {
				prevPage.setEnabled(false);
			}
			break;
		}
	}
	
	private void updateInventory() {
		sortItems();
		checkItemIndices();
		updatePageNumbers();
	}
	
	private void checkForEmptyConsumable() {
		try {
			consumableList.get(currentConsumableIndex);
		} catch (IndexOutOfBoundsException e1) {
			prevPage.doClick();
		}
	}
	
	private void checkForEmptyWeapon() {
		try {
			weaponList.get(currentWeaponIndex);
		} catch (IndexOutOfBoundsException e1) {
			prevPage.doClick();
		}
	}
	
	private void checkForEmptyArmor() {
		try {
			armorList.get(currentArmorIndex);
		} catch (IndexOutOfBoundsException e1) {
			prevPage.doClick();
		}
	}
	
	private void checkForEmptyMisc() {
		try {
			miscList.get(currentMiscIndex);
		} catch (IndexOutOfBoundsException e1) {
			prevPage.doClick();
		}
	}
	
	private class PaneChangeListener implements ChangeListener {

		@Override
		public void stateChanged(ChangeEvent e) {
			checkItemIndices();
			updatePageNumbers();
		}
	}
	
	private class EquipListener implements ActionListener {

		private Item equip;
		
		public EquipListener(Item equip) {
			this.equip = equip;
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			if(equip instanceof Armor) {
				player.equip((Armor) equip);
				GameController.playSound("Sounds\\Inventory\\Equip\\leather_inventory.mp3");
				updateInventory();
				displayArmors();
				checkForEmptyArmor();
			} else {
				player.equip((Weapon) equip);
				updateInventory();
				displayWeapons();
				checkForEmptyWeapon();
			}
			info.setText("Equipped " + equip.getSimpleName() + ". ");
		}
		
	}
	
	private class DropListener implements ActionListener {

		//TODO change page if drop last item on page
		
		private Item toDrop;
		
		public DropListener(Item i) {
			this.toDrop = i;
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			player.removeSingleItem(toDrop);
			((JButton) e.getSource()).setEnabled(false);
			info.setText("Dropped " + toDrop.getSimpleName() + ".");
			if (toDrop instanceof Weapon) {
				weaponList.remove(toDrop);
				displayWeapons();
				checkForEmptyWeapon();
			} else if (toDrop instanceof Armor) {
				armorList.remove(toDrop);
				displayArmors();
				checkForEmptyArmor();
			} else if (toDrop instanceof Consumable) {
				consumableList.remove(toDrop);
				displayConsumables();
				checkForEmptyConsumable();

			} else if (toDrop instanceof Item) {
				miscList.remove(toDrop);
				displayMisc();
				checkForEmptyMisc();
			}
			updateInventory();
		}
		
	}
	
}
