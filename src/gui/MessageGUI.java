package gui;

import javax.swing.*;

import characters.MainPlayer;
import main.GameController;
import sprites.Player;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

/**
 * GUI class for custom formatted messages
 * @author Matthew Gimbut
 *
 */
public class MessageGUI extends JPanel {
 
	private static final long serialVersionUID = -8661521249193047627L;
	private JLabel message;
	private JButton exit;
	private JPanel centerPanel;
	private JPanel southPanel;
	private JPanel spacing;
	private ViewPanel currentView;
	
	/**
	 * Constructor for MessageGUIs that takes a String to be displayed
	 * as a message
	 * @param message The message to be displayed
	 */
	public MessageGUI(String message, MainPlayer player, ViewPanel currentView) {
		this.setLayout(new BorderLayout());
		this.currentView = currentView;
		
		spacing = new JPanel();
		spacing.setBackground(GameController.BACKGROUND_COLOR_THEME);
		
		centerPanel = new JPanel();
		centerPanel.setLayout(new FlowLayout());
		centerPanel.setBackground(GameController.BACKGROUND_COLOR_THEME);
		
		southPanel = new JPanel();
		southPanel.setLayout(new FlowLayout());
		southPanel.setBackground(GameController.BACKGROUND_COLOR_THEME);
				
		this.message = new JLabel(message);
		this.message.setFont(new Font("Gentium Book Basic", Font.PLAIN, 28));
		this.message.setOpaque(false);
		this.message.setBackground(GameController.BACKGROUND_COLOR_THEME);
		
		exit = new JButton("Close");
		exit.setFont(GameController.GAME_FONT);
		exit.setBackground(GameController.BUTTON_COLOR_THEME);
		exit.addActionListener(event -> currentView.removeMessagePanel(this));
		
		southPanel.add(exit);
		centerPanel.add(this.message);
				
		this.add(centerPanel, BorderLayout.CENTER);
		this.add(southPanel, BorderLayout.SOUTH);
		this.add(spacing, BorderLayout.NORTH);
		this.add(spacing, BorderLayout.WEST);
		this.add(spacing, BorderLayout.EAST);
		this.setBackground(GameController.BACKGROUND_COLOR_THEME);
		this.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
		this.repaint();
		this.setSize(new Dimension(1264, 178));
		this.setVisible(true);
	}
}
