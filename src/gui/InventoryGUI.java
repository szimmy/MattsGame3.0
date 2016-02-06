package gui;

import javax.imageio.ImageIO;
import javax.swing.*;

import characters.MainPlayer;
import items.Item;
import items.Armor.Armor;
import items.Consumables.Consumable;
import items.Consumables.Potion;
import items.Weapons.Weapon;
import main.GameController;
import sprites.Player;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * GUI class that builds the inventory screen
 * @author Matthew Gimbut
 *
 */
public class InventoryGUI extends JPanel {
	
	private JButton exit;
	private JPanel centerPanel;
	private JPanel southPanel;
	private JPanel eastPanel;
	private JPanel westPanel;
	private JPanel northPanel;
	private JPanel itemButtonGroupPanel;
	private MainPlayer player;
	private JButton drop;
	private JButton equip;
	private JButton consume;
	private ViewPanel currentView;
	private JLabel title;
	
	/**
	 * Constructor for the inventory GUI, must always send it the corresponding player
	 * @param player
	 */
	public InventoryGUI(MainPlayer player, ViewPanel currentView) {
		
		this.player = player;
		this.currentView = currentView;
		
		eastPanel = new JPanel();
		eastPanel.setOpaque(false);

		westPanel = new JPanel();
		westPanel.setOpaque(false);
		northPanel = new JPanel();
		northPanel.setOpaque(false);
		
		title = new JLabel(player.getName() + "'s Inventory");
		title.setFont(GameController.GAME_FONT);
		northPanel.add(title);
		centerPanel = new JPanel();
		centerPanel.setOpaque(false);
		centerPanel.setLayout(new GridLayout(0, 4, 25, 15));
		southPanel = new JPanel();
		southPanel.setOpaque(false);

		exit = new JButton("Exit");
		exit.setFont(GameController.GAME_FONT);
		exit.setBackground(GameController.BUTTON_COLOR_THEME);
		exit.addActionListener(event -> {
			currentView.removeInventoryPanel(this);
		});
		
		southPanel.add(exit);
		
		redrawInventory();
		this.setBackground(GameController.BACKGROUND_COLOR_THEME);		
		this.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
		this.setVisible(true);
		this.setSize(new Dimension(750,450));
		this.setMinimumSize(new Dimension (750, 300));
		this.repaint();
	}	
	
	/*
	 * After every time the inventory is changed it is redrawn. This is to prevent items from staying visible
	 * after they've been equipped or dropped. 
	 */
	public void redrawInventory() {
		centerPanel.removeAll();
		/*
		 * Super long for-each loop.
		 * Involves adding pictures and buttons for each item.
		 * Each button has its own Action Listener added here as well.
		 * ActionListeners added as lambdas which increases the length
		 * of the loop.
		 */
		for(Item i: player.getInventory()) {
			itemButtonGroupPanel = new JPanel();
			itemButtonGroupPanel.setLayout(new GridLayout(0,1));
			itemButtonGroupPanel.setOpaque(false);

			JPanel itemPanel = new JPanel();
			itemPanel.setOpaque(false);

			JPanel btnPanel = new JPanel();
			btnPanel.setOpaque(false);

			itemPanel.setLayout(new GridLayout(0, 1));
			
			JPanel formatPanel = new JPanel();
			formatPanel.setOpaque(false);
			
			btnPanel.setLayout(new GridLayout(1,2));
			if(i instanceof Weapon) {
				itemPanel.add(new ItemPanel((Weapon) i));
			}
			else if(i instanceof Armor) {
				itemPanel.add(new ItemPanel((Armor) i));
			}
			else if(i instanceof Potion) {
				itemPanel.add(new ItemPanel((Potion) i));
			}

			if(i instanceof Armor || i instanceof Weapon) {
				/*
				 * Block for all weapons and armor AKA equippables
				 */
				equip = new JButton("Equip");
				equip.setFont(GameController.GAME_FONT);
				equip.setBackground(GameController.BUTTON_COLOR_THEME);
				equip.addActionListener(event -> {
					if (i instanceof Weapon) {
						player.equip((Weapon) i);
					} else if (i instanceof Armor) {
						player.equip((Armor) i);
					}
					redrawInventory();
					currentView.displayMessagePanel("Equipped " + i.getSimpleName() + ". ");
				});
				
				btnPanel.add(equip);

			}
			else if(i instanceof Consumable) {
				consume = new JButton("Consume");
				consume.setFont(GameController.GAME_FONT);
				consume.setBackground(GameController.BUTTON_COLOR_THEME);
				consume.addActionListener(event -> {
					if (i instanceof Potion) {
						player.consume((Potion) i);
						currentView.displayMessagePanel("Restored " + ((Potion) i).getAmount() + " health.");
					} 					
					redrawInventory();
				});

				btnPanel.add(consume);
			}

			drop = new JButton("Drop");
			drop.setFont(GameController.GAME_FONT);
			drop.setBackground(GameController.BUTTON_COLOR_THEME);
			drop.addActionListener(event -> {
				if (JOptionPane.showConfirmDialog(null,
						"Are you sure you want to permanently drop this item?") == JOptionPane.YES_OPTION) {
					player.removeSingleItem(i);
					equip.setEnabled(false);
					drop.setEnabled(false);
					redrawInventory();
				}
			});

			btnPanel.add(drop);
			formatPanel.add(btnPanel);
			itemButtonGroupPanel.add(itemPanel);
			itemButtonGroupPanel.add(formatPanel);
			centerPanel.add(itemButtonGroupPanel);
			this.revalidate();
			this.repaint();
		}
		

		
		this.setMinimumSize(new Dimension(400, 40));
		this.setLayout(new BorderLayout());
		this.add(northPanel, BorderLayout.NORTH);
		this.add(eastPanel, BorderLayout.EAST);
		this.add(westPanel, BorderLayout.WEST);
		this.add(centerPanel, BorderLayout.CENTER);
		this.add(southPanel, BorderLayout.SOUTH);
		this.setMinimumSize(new Dimension(700,253));
		this.repaint();
	}
}
