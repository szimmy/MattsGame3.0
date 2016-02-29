package gui;

import javax.imageio.ImageIO;
import javax.swing.*;

import main.GameController;
import main.SaveManager;
import sprites.Player;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class MenuPanel extends JPanel {

	private JButton options;
	private JButton stats; 
	private JButton inventory;
	private JButton exit;
	private JButton quit;
	private JPanel innerPanel;
	private ViewPanel currentView;
	private JButton load;
	private static final int BUTTON_DISTANCE = 5;
	private static final int BUTTON_LR_SIZE = 6;
	private static final int BUTTON_UD_SIZE = 3;
	
	public MenuPanel(ViewPanel currentView) {
		this.currentView = currentView;
		
		inventory = new JButton("Inventory");
		inventory.setFont(GameController.GAME_FONT);
		inventory.setForeground(GameController.BUTTON_TEXT_COLOR);
		inventory.setOpaque(false);
		inventory.setContentAreaFilled(false);
		inventory.setBorderPainted(false);	
		//inventory.setBackground(GameController.BUTTON_COLOR_THEME);
		inventory.setBorder(BorderFactory.createEmptyBorder(BUTTON_UD_SIZE, BUTTON_LR_SIZE, BUTTON_UD_SIZE, BUTTON_LR_SIZE));
		inventory.addActionListener(event -> {
				currentView.displayInventoryPanel(currentView.getPlayer().getMainPlayer().getInventory());
				currentView.toggleMenu();
		});
		
		exit = new JButton("Exit");
		exit.setForeground(GameController.BUTTON_TEXT_COLOR);
		exit.setOpaque(false);
		exit.setContentAreaFilled(false);
		exit.setBorderPainted(false);
		//exit.setBackground(GameController.BUTTON_COLOR_THEME);
		exit.setFont(GameController.GAME_FONT);
		exit.setBorder(BorderFactory.createEmptyBorder(BUTTON_UD_SIZE, BUTTON_LR_SIZE, BUTTON_UD_SIZE, BUTTON_LR_SIZE));
		exit.addActionListener(event -> {
			currentView.toggleMenu();
		});
		
		stats = new JButton("Stats");
		stats.setForeground(GameController.BUTTON_TEXT_COLOR);
		stats.setOpaque(false);
		stats.setContentAreaFilled(false);
		stats.setBorderPainted(false);
		//stats.setBackground(GameController.BUTTON_COLOR_THEME);
		stats.setFont(GameController.GAME_FONT);
		stats.setBorder(BorderFactory.createEmptyBorder(BUTTON_UD_SIZE, BUTTON_LR_SIZE, BUTTON_UD_SIZE, BUTTON_LR_SIZE));
		stats.addActionListener(event -> {
			currentView.displayStatsPanel(currentView.getPlayer().getMainPlayer());
			currentView.toggleMenu();
		});
		
		options = new JButton("Options");
		options.setForeground(GameController.BUTTON_TEXT_COLOR);
		options.setOpaque(false);
		options.setContentAreaFilled(false);
		options.setBorderPainted(false);
		//options.setBackground(GameController.BUTTON_COLOR_THEME);
		options.setFont(GameController.GAME_FONT);
		options.setBorder(BorderFactory.createEmptyBorder(BUTTON_UD_SIZE, BUTTON_LR_SIZE, BUTTON_UD_SIZE, BUTTON_LR_SIZE));
		options.addActionListener(event ->  {
			currentView.displaySettingsPanel(currentView.getPlayer().getMainPlayer());
			currentView.toggleMenu();
		});
		
		load = new JButton("Load");
		load.setForeground(GameController.BUTTON_TEXT_COLOR);
		load.setOpaque(false);
		load.setContentAreaFilled(false);
		load.setBorderPainted(false);
		//load.setBackground(GameController.BUTTON_COLOR_THEME);
		load.setFont(GameController.GAME_FONT);
		load.setBorder(BorderFactory.createEmptyBorder(BUTTON_UD_SIZE, BUTTON_LR_SIZE, BUTTON_UD_SIZE, BUTTON_LR_SIZE));
		load.addActionListener(event -> {
			currentView.toggleMenu();
			currentView.setMapItems(SaveManager.deserialize());			
		});
		
		quit = new JButton("Quit");
		// quit.setBackground(GameController.BUTTON_COLOR_THEME);
		quit.setForeground(GameController.BUTTON_TEXT_COLOR);
		quit.setOpaque(false);
		quit.setContentAreaFilled(false);
		quit.setBorderPainted(false);
		quit.setFont(GameController.GAME_FONT);
		quit.setBorder(BorderFactory.createEmptyBorder(BUTTON_UD_SIZE, BUTTON_LR_SIZE, BUTTON_UD_SIZE, BUTTON_LR_SIZE));
		quit.addActionListener(event -> {
			currentView.getController().dispose();
			System.exit(0);
		});
		
		innerPanel = new JPanel();
		innerPanel.setOpaque(false);
		innerPanel.setLayout(new GridLayout(0, 1));	
		innerPanel.add(Box.createVerticalStrut(MenuPanel.BUTTON_DISTANCE));
		innerPanel.add(stats);
		innerPanel.add(Box.createVerticalStrut(MenuPanel.BUTTON_DISTANCE));
		innerPanel.add(inventory);
		innerPanel.add(Box.createVerticalStrut(MenuPanel.BUTTON_DISTANCE));
		innerPanel.add(options);
		innerPanel.add(Box.createVerticalStrut(MenuPanel.BUTTON_DISTANCE));
		innerPanel.add(load);
		innerPanel.add(Box.createVerticalStrut(MenuPanel.BUTTON_DISTANCE));
		innerPanel.add(exit);
		innerPanel.add(Box.createVerticalStrut(MenuPanel.BUTTON_DISTANCE));
		innerPanel.add(quit);
		innerPanel.add(Box.createVerticalStrut(MenuPanel.BUTTON_DISTANCE));
		
		
		
		this.setBackground(GameController.BACKGROUND_COLOR_THEME);	
		this.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
		this.add(innerPanel);
		this.setSize(100, 320);
		this.setVisible(true);
		this.repaint();
	}
	
	
	/*@Override
	  protected void paintComponent(Graphics g) {
	    super.paintComponent(g);
	    BufferedImage myImage = null;
	    	try {
				myImage = ImageIO.read(new File("Images\\MenuSmall.png"));
			} catch (IOException e) {
				e.printStackTrace();
			}
	        g.drawImage(myImage, 0, 0, null);
	}*/
}
