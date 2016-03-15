package gui;

import javax.imageio.ImageIO;
import javax.swing.*;

import main.GameController;
import main.SaveManager;
import sprites.Player;
import sprites.Sprite;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class MenuPanel extends JPanel {

	private GameButton options;
	private GameButton stats; 
	private GameButton inventory;
	private GameButton exit;
	private GameButton quit;
	private JPanel innerPanel;
	private JButton load;
	private static final int BUTTON_DISTANCE = 5; 
	private static final int BUTTON_LR_SIZE = 6; 
	private static final int BUTTON_UD_SIZE = 3;
	
	public MenuPanel(ViewPanel currentView) {
		
		inventory = new GameButton("Inventory");
		//inventory.setContentAreaFilled(false);
		//inventory.setBorderPainted(false);	
		inventory.setBorder(BorderFactory.createEmptyBorder(BUTTON_UD_SIZE, BUTTON_LR_SIZE, BUTTON_UD_SIZE, BUTTON_LR_SIZE));
		inventory.addActionListener(event -> {
				currentView.displayInventoryPanel(currentView.getPlayer().getMainPlayer().getInventory());
				currentView.toggleMenu();
		});
		
		exit = new GameButton("Exit");
		//exit.setContentAreaFilled(false);
		//exit.setBorderPainted(false);
		exit.setBorder(BorderFactory.createEmptyBorder(BUTTON_UD_SIZE, BUTTON_LR_SIZE, BUTTON_UD_SIZE, BUTTON_LR_SIZE));
		exit.addActionListener(event -> {
			currentView.toggleMenu();
		});
		
		stats = new GameButton("Stats");
		//stats.setContentAreaFilled(false);
		//stats.setBorderPainted(false);
		stats.setBorder(BorderFactory.createEmptyBorder(BUTTON_UD_SIZE, BUTTON_LR_SIZE, BUTTON_UD_SIZE, BUTTON_LR_SIZE));
		stats.addActionListener(event -> {
			currentView.displayStatsPanel(currentView.getPlayer().getMainPlayer());
			currentView.toggleMenu();
		});
		
		options = new GameButton("Options");
		//options.setContentAreaFilled(false);
		//options.setBorderPainted(false);
		options.setBorder(BorderFactory.createEmptyBorder(BUTTON_UD_SIZE, BUTTON_LR_SIZE, BUTTON_UD_SIZE, BUTTON_LR_SIZE));
		options.addActionListener(event ->  {
			currentView.displaySettingsPanel(currentView.getPlayer().getMainPlayer());
			currentView.toggleMenu();
		});
		
		load = new GameButton("Load");
		//load.setContentAreaFilled(false);
		//load.setBorderPainted(false);
		load.setBorder(BorderFactory.createEmptyBorder(BUTTON_UD_SIZE, BUTTON_LR_SIZE, BUTTON_UD_SIZE, BUTTON_LR_SIZE));
		load.addActionListener(event -> {
			currentView.toggleMenu();
			ArrayList<Sprite> newMap = SaveManager.deserialize();
			currentView.setPlayer((Player) newMap.get(0)); //Player sprite is at index 0 of saved array. 
			newMap.remove(0); 							   //Removes from saved array 
			currentView.setMapItems(newMap);	
			currentView.updateLayers();
		});
		
		quit = new GameButton("Quit");
		//quit.setContentAreaFilled(false);
		//quit.setBorderPainted(false);
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
