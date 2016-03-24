package gui;

import java.awt.*;
import java.text.DecimalFormat;

import javax.swing.*;
import items.Item;
import items.Armor.Armor;
import items.Consumables.Consumable;
import items.Consumables.Potion;
import items.Weapons.Weapon;
import main.GameController;

/**
 * Item panel generated for each item, contains item stats and info
 * @author Matthew
 *
 */
public class ItemPanel extends JPanel {
	
	private JLabel itemName;
	private ImageIcon itemImage;
	private JLabel atkStat;
	private JLabel defStat;
	private JLabel weight;
	private JLabel value;
	private JLabel imageLabel;
	private JLabel amount;
	private JLabel healthStat;
	private JPanel northPanel;
	private JPanel eastPanel;
	private JPanel southPanel;
	private JPanel centerPanel;
	private JPanel centerWestPanel;
	private JPanel centerEastPanel;
	private JPanel westPanel;
	private DecimalFormat numFormat;

	/**
	 * Default constructor for ItemPanels, creates a blank panel 
	 * to have something shown if no item exists
	 * EX: in inventory when no item is equipped in a slot
	 */
	public ItemPanel() {
		firstSetUp();
		itemName = new JLabel("Empty");
		itemName.setFont(GameController.GAME_FONT_SMALL);
		itemName.setOpaque(false);
		northPanel.add(itemName);
		
		itemImage = new ImageIcon("Images\\Blank.png");
		atkStat = new JLabel("Atk: 0");
		atkStat.setFont(GameController.GAME_FONT_SMALL);
		defStat = new JLabel("Def: 0");
		defStat.setFont(GameController.GAME_FONT_SMALL);
		weight = new JLabel("Weight: 0");
		weight.setFont(GameController.GAME_FONT_SMALL);
		value = new JLabel("Gold: 0");
		value.setFont(GameController.GAME_FONT_SMALL);
		imageLabel = new JLabel(itemImage);
		secondSetUp();
	}
	
	public ItemPanel(String type) {
		firstSetUp();
		
		if (type.equals("placeholder")) {
			itemImage = new ImageIcon("Images\\Blank.png");
		}
		
		imageLabel = new JLabel(itemImage);
		secondSetUp();
	}
	
	
	/**
	 * Same as above, except takes an item as a parameter and builds the panel around it.
	 * @param i The item to be displayed
	 */
	public ItemPanel(Weapon w) {	
		equippableSetUp(w);
	}
	
	public ItemPanel(Armor a) {
		equippableSetUp(a);
	}
	
	
	public ItemPanel(Potion p) {
		firstSetUp();		
		itemName = new JLabel(p.getSimpleName());
		itemName.setFont(GameController.GAME_FONT_SMALL);
		itemName.setBackground(GameController.BUTTON_COLOR_THEME);
		northPanel.add(itemName);
		
		itemImage = new ImageIcon(p.getImageLocation());
		
		healthStat = new JLabel("Hp: " + p.getAmount());
		healthStat.setFont(GameController.GAME_FONT_SMALL);
		amount = new JLabel("Amt: 1");
		amount.setFont(GameController.GAME_FONT_SMALL);
		weight = new JLabel("Wgt: " + numFormat.format(p.getWeight()));
		weight.setFont(GameController.GAME_FONT_SMALL);
		value = new JLabel("Gold: " + p.getValue());
		value.setFont(GameController.GAME_FONT_SMALL);
		imageLabel = new JLabel(itemImage, SwingConstants.CENTER);
		imageLabel.setToolTipText(p.getItemToolTipText());
		
		centerEastPanel.add(healthStat);
		centerEastPanel.add(amount);
		centerEastPanel.add(weight);
		centerEastPanel.add(value);
		
		secondSetUp();
	}
	
	private void equippableSetUp(Item i) {
		firstSetUp();
		itemName = new JLabel(i.getSimpleName());
		itemName.setFont(GameController.GAME_FONT_SMALL);
		itemName.setBackground(GameController.BUTTON_COLOR_THEME);
		northPanel.add(itemName);
		
		setRarityColor(i);
		
		itemImage = new ImageIcon(i.getImageLocation());
		atkStat = new JLabel("Atk: " + i.getAtk());
		atkStat.setFont(GameController.GAME_FONT_SMALL);
		defStat = new JLabel("Def: " + i.getDef());
		defStat.setFont(GameController.GAME_FONT_SMALL);
		weight = new JLabel("Wgt: " + numFormat.format(i.getWeight()));
		weight.setFont(GameController.GAME_FONT_SMALL);
		value = new JLabel("Gold: " + i.getValue());
		value.setFont(GameController.GAME_FONT_SMALL);
		imageLabel = new JLabel(itemImage, SwingConstants.CENTER);
		imageLabel.setToolTipText(i.getItemToolTipText());

		centerEastPanel.add(atkStat);
		centerEastPanel.add(defStat);
		centerEastPanel.add(weight);
		centerEastPanel.add(value);
		
		secondSetUp();
	}
	
	/**
	 * Method to set the rarity color of the name label of the item
	 * @param i The item to be checked
	 */
	private void setRarityColor(Item i) {
		switch(i.getHowRare()) {
		case JUNK: 
			itemName.setForeground(Color.GRAY);
			break;
		case COMMON: 
			itemName.setForeground(Color.BLACK);
			break;
		case UNCOMMON: 
			itemName.setForeground(Color.GREEN);
			break;
		case RARE: 
			itemName.setForeground(Color.BLUE);
			break;
		case VERY_RARE: 
			itemName.setForeground(Color.MAGENTA);
			break;
		case LEGENDARY: 
			itemName.setForeground(Color.ORANGE); //THIS IS ORANGE BECAUSE ROB SAID SO
			break;
		}
	}
	
	/**
	 * Beginning part of set up for the panel.
	 * Moved to a separate method to follow DRY
	 * since it's used in both constructors. 
	 */
	private void firstSetUp() {
		this.setLayout(new BorderLayout());
		westPanel = new JPanel();
		northPanel = new JPanel();
		northPanel.setOpaque(false);
		centerPanel = new JPanel();		
		centerEastPanel = new JPanel();
		centerEastPanel.setLayout(new GridLayout(2, 2));
		numFormat = new DecimalFormat("#.#");
	}
	
	/**
	 * Second part of set up for the panel.
	 * Moved to a separate method to follow DRY
	 * since it's used in both constructors. 
	 */
	private void secondSetUp() {
		centerPanel.setLayout(new GridBagLayout());
		centerPanel.setOpaque(false);
		eastPanel = new JPanel();
		centerEastPanel.setOpaque(false);
		centerWestPanel = new JPanel();
		centerWestPanel.setLayout(new GridBagLayout());
		centerWestPanel.setOpaque(false);
		
		southPanel = new JPanel();
		southPanel.setLayout(new FlowLayout());
		
		centerWestPanel.add(imageLabel);
		centerPanel.add(centerWestPanel);
		southPanel.add(centerEastPanel);
		westPanel.setOpaque(false);
		
		
		eastPanel.setOpaque(false);
		centerPanel.setOpaque(false);
		southPanel.setOpaque(false);
		
		JPanel inner = new JPanel();
		inner.add(centerEastPanel);
		
		this.add(northPanel, BorderLayout.NORTH);
		this.add(westPanel, BorderLayout.WEST);
		this.add(eastPanel, BorderLayout.EAST);
		this.add(centerPanel, BorderLayout.CENTER);
		this.add(inner, BorderLayout.SOUTH);
		this.setBorder(BorderFactory.createLineBorder(Color.BLACK));
	}

}
