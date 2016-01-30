package gui;

import javax.swing.*;

import characters.MainPlayer;
import items.Item;
import items.Armor.Armor;
import items.Consumables.Potion;
import items.Weapons.Weapon;
import main.GameController;
import sprites.Player;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.LinkedList;

/**
 * GUI class for all loot menus
 * @author Matthew Gimbut
 *
 */
public class LootGUI extends JPanel {
	
	private JButton takeAll;
	private JButton exit;
	private JPanel southPanel;
	private JPanel northPanel;
	private JPanel centerPanel;
	private int counter = 0;
	private LinkedList<Item> items;
	private ViewPanel currentView;
	
	/**
	 * Each LootGUI needs to take a chest and a player to display all info
	 * @param chest The chest containing loot
	 * @param player The player who opened the menu
	 */
	public LootGUI(LinkedList<Item> items, MainPlayer player, ViewPanel currentView) {
				
		this.items = items;
		this.currentView = currentView;
		this.setLayout(new BorderLayout());
		northPanel = new JPanel();
		northPanel.setBackground(GameController.BACKGROUND_COLOR_THEME);
		southPanel = new JPanel();
		southPanel.setBackground(GameController.BACKGROUND_COLOR_THEME);
		southPanel.setLayout(new FlowLayout());
		centerPanel = new JPanel();
		centerPanel.setBackground(GameController.BACKGROUND_COLOR_THEME);
		centerPanel.setLayout(new FlowLayout());
		
		takeAll = new JButton("Take all");
		takeAll.setBackground(GameController.BUTTON_COLOR_THEME);
		takeAll.addActionListener(event -> {
			for(Item i : items) {
				player.addItem(i);
			}
			items.removeAll(items);
			centerPanel.removeAll();
			southPanel.removeAll();
			centerPanel.add(new JLabel("This chest is empty."));
			currentView.removeLootPanel(this);
			this.repaint();
		});
				
		exit = new JButton("Exit");
		exit.setBackground(GameController.BUTTON_COLOR_THEME);
		exit.addActionListener(event -> currentView.removeLootPanel(this));
		southPanel.add(exit);
		
		items.parallelStream().forEach(i -> {
			
			JPanel itemSection = new JPanel();
			itemSection.setLayout(new BorderLayout());
			if(i instanceof Weapon) {
				itemSection.add(new ItemPanel((Weapon) i));
			}
			else if(i instanceof Armor) {
				itemSection.add(new ItemPanel((Armor) i));
			}
			else if(i instanceof Potion) {
				itemSection.add(new ItemPanel((Potion) i));
			}
			
			JButton take = new JButton("Take");
			take.setBackground(GameController.BUTTON_COLOR_THEME);
			take.addActionListener(event -> {
				if(!player.addItem(i)) {
					//TODO: //player.toggleMessagePanel(new MessageGUI("You cannot carry that.", player));
				}
				items.remove(i);
				take.setEnabled(false);
				checkIfEmpty();
			});
						
			itemSection.add(take, BorderLayout.SOUTH);
			centerPanel.add(itemSection);
			counter++;
			
		});
					
		checkIfEmpty();
		
		this.setBackground(GameController.BACKGROUND_COLOR_THEME);
		this.add(northPanel, BorderLayout.NORTH);
		this.add(centerPanel, BorderLayout.CENTER);
		this.add(southPanel, BorderLayout.SOUTH);		
		this.setMinimumSize(new Dimension(500,200));
		this.setSize(new Dimension(600,350));
		this.repaint();
		this.setVisible(true);
		
	}
	
	/**
	 * Checks to see if the chest is currently empty
	 */
	public void checkIfEmpty() {
		if(counter == 0 || items.size() == 0) {
			centerPanel.add(new JLabel("This chest is empty."));
			this.takeAll.setEnabled(false);
			
		}
		else {
			southPanel.add(takeAll);
		}
		
	}
}
