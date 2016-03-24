package gui;

import javax.swing.*;

import characters.MainPlayer;
import main.GameController;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SettingsPanel extends JPanel {

	private GameButton exit, save;
	private String[] textSpeed = { "Fast", "Medium", "Slow", "Mentally retarded" };
	private JComboBox<String> textSpeedBox;
	private JLabel textSpeedLabel;
	private JPanel centerPanel, northPanel, southPanel, eastPanel, westPanel;
	private MainPlayer player;
	
	public SettingsPanel(MainPlayer player, ViewPanel currentView) {
		this.player = player;
		this.setLayout(new BorderLayout());
		centerPanel = new JPanel();
		centerPanel.setOpaque(false);
		southPanel = new JPanel();
		southPanel.setOpaque(false);
		northPanel = new JPanel();
		northPanel.setOpaque(false);
		eastPanel = new JPanel();
		eastPanel.setOpaque(false);
		westPanel = new JPanel();
		westPanel.setOpaque(false);
		
		textSpeedBox = new JComboBox<String>(textSpeed);
		textSpeedLabel = new JLabel("Text speed: ");
		
		centerPanel.setLayout(new GridLayout(0,2));
		centerPanel.add(textSpeedLabel);
		centerPanel.add(textSpeedBox);
		
		save = new GameButton("Save settings");
		save.addActionListener(new SaveSettings());
		
		exit = new GameButton("Exit");
		exit.addActionListener(event -> {
			currentView.removeSettingsPanel(this);
		});
		
		southPanel.add(save);
		southPanel.add(exit);
		
		textSpeedBox.setSelectedIndex(determineDefault());

		this.add(northPanel, BorderLayout.NORTH);
		this.add(eastPanel, BorderLayout.EAST);
		this.add(westPanel, BorderLayout.WEST);
		this.add(centerPanel, BorderLayout.CENTER);
		this.add(southPanel, BorderLayout.SOUTH);
		this.setBackground(GameController.BACKGROUND_COLOR_THEME);
		this.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
		this.repaint();
		this.setVisible(true);
	}
	
	private int determineDefault() {
		switch (player.getTextScrollingSpeed()) {
		case 20: return 0;
		case 35: return 1;
		case 50: return 2;
		case 150: return 3;
		default: return 1;
		}		
	}
	
	private class SaveSettings implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent event) {
			switch ((String) textSpeedBox.getSelectedItem()) {
			case "Fast":
				player.setTextScrollingSpeed(20);
				break;
			case "Medium":
				player.setTextScrollingSpeed(35);
				break;
			case "Slow":
				player.setTextScrollingSpeed(50);
				break;
			case "Mentally retarded":
				player.setTextScrollingSpeed(150);
			}
		}
		
	}
	
}
