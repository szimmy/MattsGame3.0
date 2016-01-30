package gui;

import javax.swing.*;

import characters.MainPlayer;

import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

/**
 * GUI class for the start menu
 * @author Matthew
 *
 */
public class StartGUI extends JFrame {

	private JButton exit;
	private JButton settings;
	private JButton play;
	private JButton credits;
	private JPanel southPanel;
	private MainPlayer matthew;
	private JButton loadSave;
	
	public StartGUI() {
		matthew = new MainPlayer("Matthew");
		southPanel = new JPanel();
		southPanel.setLayout(new FlowLayout());
		
		loadSave = new JButton("Load Previous Save");
		loadSave.addActionListener(event -> {
			//MainPlayer player = MainPlayer.deserialize();
			this.dispose();
		});
		
		exit = new JButton("Exit");
		exit.addActionListener(event -> {
			this.dispose();
		});
		
		settings = new JButton("Settings");
		settings.addActionListener(event -> {
			//new SettingsGUI(null);
		});
		
		play = new JButton("Temporary play button until I add save files to choose");
		play.addActionListener(event -> {
			this.dispose();
		});
		
		credits = new JButton("Credits");
		credits.addActionListener(event -> new CreditsGUI());
		
		southPanel.add(play);
		southPanel.add(loadSave);
		southPanel.add(settings);
		southPanel.add(credits);
		
		this.addWindowListener(new StartListener());
		
		this.setLayout(new BorderLayout());
		this.add(southPanel, BorderLayout.SOUTH);
		this.setTitle("Test");
		this.pack();
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setVisible(true);
	}
	
	private class StartListener implements WindowListener {

		@Override
		public void windowActivated(WindowEvent arg0) {
			
		}

		@Override
		public void windowClosed(WindowEvent arg0) {
			
		}

		@Override
		public void windowClosing(WindowEvent arg0) {
			
		}

		@Override
		public void windowDeactivated(WindowEvent arg0) {
			
		}

		@Override
		public void windowDeiconified(WindowEvent arg0) {
			
		}

		@Override
		public void windowIconified(WindowEvent arg0) {
			
		}

		@Override
		public void windowOpened(WindowEvent arg0) {
			Runtime.getRuntime().gc();
		}
		
	}
	
}
