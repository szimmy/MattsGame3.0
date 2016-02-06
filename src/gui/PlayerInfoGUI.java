package gui;

import java.awt.*;
import java.awt.event.KeyEvent;

import javax.swing.*;

import characters.MainPlayer;
import items.Item;
import items.Weapons.Weapon;
import main.GameController;
import sprites.Player;

/**
 * GUI class for the player's stats menu
 * Not gonna bother documenting this, I don't even understand
 * half of what I did and it's probably all gonna be 
 * scrapped anyways. 
 * @author Matthew Gimbut
 *
 */
public class PlayerInfoGUI extends JPanel {

	private JPanel centerPanel;
		private JPanel bootsWestPanel;
		private JPanel glovesEastPanel;
		private JPanel legsSouthPanel;
		private JPanel helmetNorthPanel;
		private JPanel chestCenterPanel;
		
	private StatusBarPanel healthbar;
	private StatusBarPanel xpBar;
	private JPanel northPanel;
	private JPanel southPanel;
	private JPanel eastPanel;
	private JPanel westPanel;
	private MainPlayer player;
	private JLabel userName;
	private JButton exit;
	
	private JLabel health;
	private JLabel atkStat;
	private JLabel defStat;
	private JLabel invWeight;
	private JLabel totalGold;
	private JLabel speed;
	private JLabel level;
	private JLabel xp;
	private ViewPanel currentView;
	
	public PlayerInfoGUI(MainPlayer player, ViewPanel currentView) {
		
		this.player = player;
		this.currentView = currentView;
		
		centerPanel = new JPanel();
		centerPanel.setBackground(GameController.BACKGROUND_COLOR_THEME);
		centerPanel.setLayout(new BorderLayout());
		
		bootsWestPanel = new JPanel();
		bootsWestPanel.setBackground(GameController.BACKGROUND_COLOR_THEME);
		
		glovesEastPanel = new JPanel();
		glovesEastPanel.setBackground(GameController.BACKGROUND_COLOR_THEME);
		
		legsSouthPanel = new JPanel();
		legsSouthPanel.setBackground(GameController.BACKGROUND_COLOR_THEME);
		
		helmetNorthPanel = new JPanel();
		helmetNorthPanel.setBackground(GameController.BACKGROUND_COLOR_THEME);
		
		chestCenterPanel = new JPanel();
		chestCenterPanel.setBackground(GameController.BACKGROUND_COLOR_THEME);
		
		northPanel = new JPanel();
		northPanel.setBackground(GameController.BACKGROUND_COLOR_THEME);
		
		southPanel = new JPanel();
		southPanel.setBackground(GameController.BACKGROUND_COLOR_THEME);
		
		westPanel = new JPanel();
		westPanel.setBackground(GameController.BACKGROUND_COLOR_THEME);
		
		eastPanel = new JPanel();
		eastPanel.setBackground(GameController.BACKGROUND_COLOR_THEME);
		eastPanel.setLayout(new GridLayout(0,1));
		
		userName = new JLabel(player.getName());
		userName.setOpaque(false);
		userName.setBackground(GameController.BACKGROUND_COLOR_THEME);
		
		exit = new JButton("Exit");
		exit.setFont(GameController.GAME_FONT);
		exit.setBackground(GameController.BUTTON_COLOR_THEME);
		exit.addActionListener(event -> {
			currentView.removeStatsPanel(this);
		});
		
		healthbar = new StatusBarPanel("Health", player.getCurrentHP(), player.getMaxHP());
		xpBar = new StatusBarPanel("XP", player.getXp(), MainPlayer.MAX_XP);
		northPanel.add(userName);
		southPanel.add(healthbar);
		southPanel.add(xpBar);
		southPanel.add(exit);
		
		generateItemPanel();
		this.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
		this.repaint();
		this.setSize(new Dimension(550,400));
		this.repaint();
		this.setVisible(true);
	}
	
	private JPanel getFormattedItemPanel(JPanel inner) {
		JPanel spacing = new JPanel();
		spacing.add(inner);
		return spacing;
	}
	
	private JPanel getNewFormattedPanel() {
		JPanel item = new JPanel();
		item.setLayout(new GridLayout(0,1));
		item.setBackground(GameController.BACKGROUND_COLOR_THEME);
		return item;
	}
	
	/**
	 * Fix the fuck out of this method
	 * This is so garbage and there is a better way to do it but i'm really tired
	 * so i'm just doing it like this because i'm too lazy to use any brain capacity
	 * and I just want to see results
	 */
	public void generateItemPanel() {
		
		chestCenterPanel.removeAll();
		legsSouthPanel.removeAll();
		bootsWestPanel.removeAll();
		glovesEastPanel.removeAll();
		helmetNorthPanel.removeAll();
		centerPanel.removeAll();
		eastPanel.removeAll();
		westPanel.removeAll();
		
		JPanel formatPanel = new JPanel();
		formatPanel.setBackground(GameController.BUTTON_COLOR_THEME);
		formatPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		JPanel statPanel = new JPanel();
		statPanel.setBackground(GameController.BUTTON_COLOR_THEME);
		statPanel.setLayout(new GridLayout(8, 1, 2, 2));
		
		level = new JLabel("Level: " + player.getLvl());
		level.setFont(GameController.GAME_FONT);
		level.setOpaque(false);
		level.setBackground(GameController.BUTTON_COLOR_THEME);
		statPanel.add(level);
		
		xp = new JLabel("Experience: " + player.getXp() + "/100");
		xp.setFont(GameController.GAME_FONT);
		xp.setOpaque(false);
		xp.setBackground(GameController.BUTTON_COLOR_THEME);
		statPanel.add(xp);
		
		health = new JLabel("Health: " + player.getCurrentHP() + "/" + player.getMaxHP());
		health.setFont(GameController.GAME_FONT);
		health.setOpaque(false);
		health.setBackground(GameController.BUTTON_COLOR_THEME);
		statPanel.add(health);
		
		atkStat = new JLabel("Attack: " + player.getAtk());
		atkStat.setFont(GameController.GAME_FONT);
		atkStat.setOpaque(false);
		atkStat.setBackground(GameController.BUTTON_COLOR_THEME);
		statPanel.add(atkStat);
		
		defStat = new JLabel("Defence: " + player.getDef());
		defStat.setFont(GameController.GAME_FONT);
		defStat.setOpaque(false);
		defStat.setBackground(GameController.BUTTON_COLOR_THEME);
		statPanel.add(defStat);
		
		speed = new JLabel("Speed: " + player.getSpeed());
		speed.setFont(GameController.GAME_FONT);
		speed.setOpaque(false);
		speed.setBackground(GameController.BUTTON_COLOR_THEME);
		statPanel.add(speed);
		
		invWeight = new JLabel("Carry weight: " + player.getCurrentCarry() + "/" + player.getCarryCap());
		invWeight.setFont(GameController.GAME_FONT);
		invWeight.setOpaque(false);
		invWeight.setBackground(GameController.BUTTON_COLOR_THEME);
		statPanel.add(invWeight);
		
		totalGold = new JLabel("Gold: " + player.getGold());
		totalGold.setFont(GameController.GAME_FONT);
		totalGold.setOpaque(false);
		totalGold.setBackground(GameController.BUTTON_COLOR_THEME);
		statPanel.add(totalGold);
		
		formatPanel.add(statPanel);
		westPanel.add(formatPanel);
		
		if(player.getWeaponHandR() != null) {
			JPanel item = getNewFormattedPanel();
			item.add(new ItemPanel(player.getWeaponHandR()));
			item.add(setUnequipInfo(player.getWeaponHandR()));
			eastPanel.add(getFormattedItemPanel(item));
		}
		else {
			eastPanel.add(new ItemPanel());		}
		
		if(player.getLeftHand() != null) {
			JPanel item = getNewFormattedPanel();
			item.add(new ItemPanel(player.getLeftHand()));
			item.add(setUnequipInfo(player.getLeftHand()));
			eastPanel.add(getFormattedItemPanel(item));
		}
		else {
			eastPanel.add(new ItemPanel());
		}
		
		if(player.getChestPiece() != null) {
			JPanel item = getNewFormattedPanel();
			item.add(new ItemPanel(player.getChestPiece()));
			item.add(setUnequipInfo(player.getChestPiece()));
			chestCenterPanel.add(item);
		}
		else {
			chestCenterPanel.add(new ItemPanel());
		}
		
		if(player.getLeggings() != null) {
			JPanel item = getNewFormattedPanel();
			item.add(new ItemPanel(player.getLeggings()));
			item.add(setUnequipInfo(player.getLeggings()));
			legsSouthPanel.add(item);
		}
		else {
			legsSouthPanel.add(new ItemPanel());
		}
		
		if(player.getBoots() != null) {
			JPanel item = getNewFormattedPanel();
			item.add(new ItemPanel(player.getBoots()));
			item.add(setUnequipInfo(player.getBoots()));
			bootsWestPanel.add(item);
		}
		else {
			bootsWestPanel.add(new ItemPanel());
		}
		
		if(player.getGloves() != null) {
			JPanel item = getNewFormattedPanel();
			item.add(new ItemPanel(player.getGloves()));
			item.add(setUnequipInfo(player.getGloves()));
			glovesEastPanel.add(item);
		}
		else {
			glovesEastPanel.add(new ItemPanel());
		}
		
		if(player.getHelmet() != null) {
			JPanel item = getNewFormattedPanel();
			item.add(new ItemPanel(player.getHelmet()));
			item.add(setUnequipInfo(player.getHelmet()));
			helmetNorthPanel.add(item);
		}
		else {
			helmetNorthPanel.add(new ItemPanel());
		}
		
		centerPanel.add(chestCenterPanel, BorderLayout.CENTER);
		centerPanel.add(bootsWestPanel, BorderLayout.WEST);
		centerPanel.add(glovesEastPanel, BorderLayout.EAST);
		centerPanel.add(helmetNorthPanel, BorderLayout.NORTH);
		centerPanel.add(legsSouthPanel, BorderLayout.SOUTH);
		
		
		this.setLayout(new BorderLayout());
		this.add(northPanel, BorderLayout.NORTH);
		this.add(eastPanel, BorderLayout.EAST);
		this.add(westPanel, BorderLayout.WEST);
		this.add(southPanel, BorderLayout.SOUTH);
		this.add(centerPanel, BorderLayout.CENTER);
		
		this.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
		this.repaint();
		this.repaint();
		this.setVisible(true);
		this.setSize(new Dimension(700,700));
		
	}	
	
	public JPanel setUnequipInfo(Item i) {
		JPanel panel = new JPanel();
		panel.setBackground(GameController.BACKGROUND_COLOR_THEME);
		JButton unequip = new JButton("Unequip");
		unequip.setFont(GameController.GAME_FONT);
		unequip.setBackground(GameController.BUTTON_COLOR_THEME);
		unequip.addActionListener(event -> {
			player.unequip(i);
			generateItemPanel();
		});
		panel.add(unequip);
		return panel;
	}
}
