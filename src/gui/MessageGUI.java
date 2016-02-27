package gui;

import javax.swing.*;

import characters.MainPlayer;
import main.GameController;
import sprites.Player;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * GUI class for custom formatted messages
 * @author Matthew Gimbut
 *
 */
public class MessageGUI extends JPanel {
 
	private static final long serialVersionUID = -8661521249193047627L;
	private JLabel message;
	private JPanel centerPanel;
	private JPanel southPanel;
	private JPanel spacing;
	private ViewPanel currentView;
	private JButton exit;
	
	/**
	 * Constructor for MessageGUIs that takes a String to be displayed
	 * as a message
	 * @param message The message to be displayed
	 */
	public MessageGUI(String message, MainPlayer player, ViewPanel currentView) {
		this.setLayout(new FlowLayout(FlowLayout.LEFT));
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
		this.message.setFont(new Font("Cambria", Font.PLAIN, 28));
		this.message.setOpaque(false);
		this.message.setBackground(GameController.BACKGROUND_COLOR_THEME);
		
		centerPanel.add(this.message);
		
		this.addKeyListener(new MessageListener());
		this.add(centerPanel, BorderLayout.CENTER);
		this.add(southPanel, BorderLayout.SOUTH);
		this.add(spacing, BorderLayout.NORTH);
		this.add(spacing, BorderLayout.WEST);
		this.add(spacing, BorderLayout.EAST);
		this.setBackground(GameController.BACKGROUND_COLOR_THEME);
		this.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
		this.repaint();
		this.setSize(new Dimension(1400, 960));
		this.setVisible(true);
	}
	
	private MessageGUI getInstance() {
		return this;
	}
	
	private class MessageListener implements KeyListener {
		@Override
		public void keyPressed(KeyEvent event) {
			if (event.getKeyCode() == KeyEvent.VK_E) {
				currentView.removeMessagePanel(getInstance());
			}
		}
		@Override
		public void keyReleased(KeyEvent event) {}
		@Override
		public void keyTyped(KeyEvent event) {
			if (event.getKeyCode() == KeyEvent.VK_E) {
				currentView.removeMessagePanel(getInstance());
			}
		}		
	}
}
