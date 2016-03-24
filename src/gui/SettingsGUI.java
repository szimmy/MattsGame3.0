package gui;

import javax.swing.*;

import characters.MainPlayer;
import main.GameController;
import sprites.Player;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * GUI class for the settings menu
 * @author Matthew Gimbut
 *
 */
public class SettingsGUI extends JPanel {

	private JLabel gridSize;
	private JComboBox<String> gridSizeBox;
	private String[] gridSizes = {
			"Small", "Medium", "Large", "Huge", "This is too big don't use this one"
	};
	
	private JLabel gridWidth;
	private JSpinner spinner;
	private JPanel northPanel;
	private JPanel westPanel;
	private JPanel eastPanel;
	private SpinnerNumberModel widthSpinner;	
	private JButton saveSettings;
	private JButton exit;
	private JPanel southPanel;
	private JPanel centerPanel;
	private MainPlayer player;
	private ViewPanel currentView;
	
	/**
	 * Constructor for the SettingsGUI, takes a player as a parameter
	 * @param player The player whose settings are to be changed
	 */
	@Deprecated
	public SettingsGUI(MainPlayer player, ViewPanel currentView) {
		
		this.player = player;
		this.currentView = currentView;
		centerPanel = new JPanel();
		centerPanel.setLayout(new GridLayout(0, 2));
		centerPanel.setBackground(GameController.BACKGROUND_COLOR_THEME);
		southPanel = new JPanel();
		southPanel.setBackground(GameController.BACKGROUND_COLOR_THEME);
		northPanel = new JPanel(); 
		northPanel.setBackground(GameController.BACKGROUND_COLOR_THEME);
		eastPanel = new JPanel();
		eastPanel.setBackground(GameController.BACKGROUND_COLOR_THEME);
		westPanel = new JPanel();
		westPanel.setBackground(GameController.BACKGROUND_COLOR_THEME);
		
		saveSettings = new JButton("Save settings");
		saveSettings.setBackground(GameController.BUTTON_COLOR_THEME);
		
		exit = new JButton("Exit");
		exit.setBackground(GameController.BUTTON_COLOR_THEME);
		exit.addActionListener(event -> {
			//currentView.removeSettingsPanel(this);
		});
		
		gridSize = new JLabel("Select grid size: ");
		gridSizeBox = new JComboBox<String>(gridSizes);
		
		gridWidth = new JLabel("Extra map width: ");
		widthSpinner = new SpinnerNumberModel(0, 0, 20, 1);
		spinner = new JSpinner(widthSpinner);
		
		centerPanel.add(gridSize);
		centerPanel.add(gridSizeBox);
		centerPanel.add(gridWidth);
		centerPanel.add(spinner);
		
		southPanel.add(saveSettings);
		southPanel.add(exit);
		
		this.setLayout(new BorderLayout());
		this.add(northPanel, BorderLayout.NORTH);
		this.add(westPanel, BorderLayout.WEST);
		this.add(eastPanel, BorderLayout.EAST);
		this.add(centerPanel, BorderLayout.CENTER);
		this.add(southPanel, BorderLayout.SOUTH);

		
		this.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
		this.setSize(ViewPanel.B_WIDTH, ViewPanel.B_HEIGHT);
		this.repaint();
		this.setVisible(true);
	}
	
}
