package gui;

import javax.swing.*;

import main.GameController;
import sprites.Player;

import java.awt.*;

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
		inventory.setBackground(GameController.BUTTON_COLOR_THEME);
		inventory.setBorder(BorderFactory.createEmptyBorder(BUTTON_UD_SIZE, BUTTON_LR_SIZE, BUTTON_UD_SIZE, BUTTON_LR_SIZE));
		inventory.addActionListener(event -> {
				currentView.displayInventoryPanel(currentView.getPlayer().getMainPlayer().getInventory());
				currentView.toggleMenu();
		});
		
		exit = new JButton("Exit");
		exit.setBackground(GameController.BUTTON_COLOR_THEME);
		exit.setFont(GameController.GAME_FONT);
		exit.setBorder(BorderFactory.createEmptyBorder(BUTTON_UD_SIZE, BUTTON_LR_SIZE, BUTTON_UD_SIZE, BUTTON_LR_SIZE));
		exit.addActionListener(event -> {
			currentView.toggleMenu();
		});
		
		stats = new JButton("Stats");
		stats.setBackground(GameController.BUTTON_COLOR_THEME);
		stats.setFont(GameController.GAME_FONT);
		stats.setBorder(BorderFactory.createEmptyBorder(BUTTON_UD_SIZE, BUTTON_LR_SIZE, BUTTON_UD_SIZE, BUTTON_LR_SIZE));
		stats.addActionListener(event -> {
			currentView.displayStatsPanel(currentView.getPlayer().getMainPlayer());
			currentView.toggleMenu();
		});
		
		options = new JButton("Options");
		options.setBackground(GameController.BUTTON_COLOR_THEME);
		options.setFont(GameController.GAME_FONT);
		options.setBorder(BorderFactory.createEmptyBorder(BUTTON_UD_SIZE, BUTTON_LR_SIZE, BUTTON_UD_SIZE, BUTTON_LR_SIZE));
		options.addActionListener(event ->  {
			currentView.displaySettingsPanel(currentView.getPlayer().getMainPlayer());
			currentView.toggleMenu();
		});
		
		load = new JButton("Load");
		load.setBackground(GameController.BUTTON_COLOR_THEME);
		load.setFont(GameController.GAME_FONT);
		load.setBorder(BorderFactory.createEmptyBorder(BUTTON_UD_SIZE, BUTTON_LR_SIZE, BUTTON_UD_SIZE, BUTTON_LR_SIZE));
		load.addActionListener(event -> {
			currentView.toggleMenu();
			/*GameController control = currentView.getController();
			control.getContentPane().removeAll();
			control.getContentPane().revalidate();
			ViewPanel newView = new ViewPanel(control, ViewPanel.deserialize());
			newView.getPlayer().setMainView(newView);
			newView.getPlayer().refreshImage();
			control.getContentPane().add(newView);
			control.getContentPane().revalidate();*/
			
		});
		
		quit = new JButton("Quit");
		quit.setBackground(GameController.BUTTON_COLOR_THEME);
		quit.setFont(GameController.GAME_FONT);
		quit.setBorder(BorderFactory.createEmptyBorder(BUTTON_UD_SIZE, BUTTON_LR_SIZE, BUTTON_UD_SIZE, BUTTON_LR_SIZE));
		quit.addActionListener(event -> {
			currentView.getController().dispose();
		});
		
		innerPanel = new JPanel();
		innerPanel.setOpaque(false);
		innerPanel.setLayout(new GridLayout(0, 1));	
		innerPanel.add(Box.createVerticalStrut(0));
		innerPanel.add(stats);
		innerPanel.add(Box.createVerticalStrut(BUTTON_DISTANCE));
		innerPanel.add(inventory);
		innerPanel.add(Box.createVerticalStrut(BUTTON_DISTANCE));
		innerPanel.add(options);
		innerPanel.add(Box.createVerticalStrut(BUTTON_DISTANCE));
		innerPanel.add(load);
		innerPanel.add(Box.createVerticalStrut(BUTTON_DISTANCE));
		innerPanel.add(exit);
		innerPanel.add(Box.createVerticalStrut(BUTTON_DISTANCE));
		innerPanel.add(quit);
		
		this.setBackground(GameController.BACKGROUND_COLOR_THEME);		
		this.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
		this.add(innerPanel);
		this.setSize(100, 320);
		this.setOpaque(true);
		this.setVisible(true);
		this.repaint();
	}
	
}
