package gui;

import javax.swing.JButton;

import main.GameController;

public class GameButton extends JButton {
	
	public GameButton() {
		this.setFont(GameController.GAME_FONT);
		this.setForeground(GameController.BUTTON_TEXT_COLOR);
		this.setBackground(GameController.BUTTON_COLOR_THEME);
		this.setOpaque(true);
		this.setRolloverEnabled(false);
	}
	
	public GameButton(String text) {
		this();
		this.setText(text);
	}
	
}
