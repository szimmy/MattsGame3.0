package gui;

import javax.swing.*;

import characters.Enemy;
import characters.MainPlayer;
import main.GameController;
import sprites.NPC;
import sprites.Player;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

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
	private JPanel northPanel;
	private JPanel spacing;
	private ViewPanel currentView;
	private JButton exit;
	private boolean multipleMessages;
	int messageCounter = 0;
	private String[] messages;
	private NPC npc;
	
	/**
	 * Constructor for MessageGUIs that takes a String to be displayed
	 * as a message
	 * @param message The message to be displayed
	 */
	public MessageGUI(String message, MainPlayer player, ViewPanel currentView) {
		this.setLayout(new FlowLayout(FlowLayout.LEFT));
		this.currentView = currentView;
		this.multipleMessages = false;
		
		initPanels();
		
		this.message = new JLabel(message);
		initMessage();
		
		centerPanel.add(this.message);
		
		initSettings();
	}
	
	public MessageGUI(String[] message, MainPlayer player, ViewPanel currentView, NPC npc) {
		this.setLayout(new BorderLayout());
		this.currentView = currentView;
		this.multipleMessages = true;
		this.messages = message;
		this.npc = npc;
		
		initPanels();
	
		if(npc != null) {
			JPanel northPanel = new JPanel();
			northPanel.setLayout(new FlowLayout());
			northPanel.setBackground(GameController.BACKGROUND_COLOR_THEME);
			JLabel nameLabel = new JLabel(npc.getNPC().getName());
			nameLabel.setFont(GameController.GAME_FONT);
			northPanel.add(nameLabel);
			this.northPanel.add(northPanel);
		}	
		
		this.message = new JLabel(messages[messageCounter]);
		centerPanel.add(this.message);
		
		initMessage();
		
		initSettings();
	}
	
	private void initMessage() {
		this.message.setFont(new Font("Cambria", Font.PLAIN, 28));
		this.message.setOpaque(false);
		this.message.setBackground(GameController.BACKGROUND_COLOR_THEME);
	}
	
	private void initPanels() {
		spacing = new JPanel();
		spacing.setBackground(GameController.BACKGROUND_COLOR_THEME);
		
		northPanel = new JPanel();
		northPanel.setLayout(new FlowLayout());
		northPanel.setBackground(GameController.BACKGROUND_COLOR_THEME);
		
		centerPanel = new JPanel();
		centerPanel.setLayout(new FlowLayout());
		centerPanel.setBackground(GameController.BACKGROUND_COLOR_THEME);
		
		southPanel = new JPanel();
		southPanel.setLayout(new FlowLayout());
		southPanel.setBackground(GameController.BACKGROUND_COLOR_THEME);
	}
	
	private void initSettings() {
		this.addKeyListener(new MessageListener());
		this.add(centerPanel, BorderLayout.CENTER);
		this.add(southPanel, BorderLayout.SOUTH);
		this.add(northPanel, BorderLayout.NORTH);
		this.add(spacing, BorderLayout.WEST);
		this.add(spacing, BorderLayout.EAST);
		this.setBackground(GameController.BACKGROUND_COLOR_THEME);
		this.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
		this.repaint();
		this.setSize(new Dimension(1400, 960));
		this.setVisible(true);
	}
	
	private void checkForBattle() {
		if(npc != null && npc.getNPC() instanceof Enemy) {
			//TODO inefficient/pointless, find better way to do this
			ArrayList<Enemy> enemy = new ArrayList<Enemy>();
			enemy.add((Enemy) npc.getNPC());
			currentView.displayBattlePanel(enemy, npc);
		}
	}
	
	private MessageGUI getInstance() {
		return this;
	}
	
	private class MessageListener implements KeyListener {
		@Override
		public void keyPressed(KeyEvent event) {
			if (event.getKeyCode() == KeyEvent.VK_E) {
				if (multipleMessages) {
					messageCounter++;
					if(messages[messageCounter] == null) {
						checkForBattle();
						currentView.removeMessagePanel(getInstance());
					} else {
						message.setText(messages[messageCounter]);
					}
				} else {
					checkForBattle();
					currentView.removeMessagePanel(getInstance());

				}
			}
		}
		@Override
		public void keyReleased(KeyEvent event) {}
		@Override
		public void keyTyped(KeyEvent event) {
			if (event.getKeyCode() == KeyEvent.VK_E) {
				if (multipleMessages) {
					messageCounter++;
					if(messages[messageCounter].isEmpty()) {
						checkForBattle();
						currentView.removeMessagePanel(getInstance());
					} else {
						message.setText(messages[messageCounter]);
					}
				} else {
					checkForBattle();
					currentView.removeMessagePanel(getInstance());
				}
			}
		}		
	}
}
