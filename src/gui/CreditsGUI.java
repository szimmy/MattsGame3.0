package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

/**
 * GUI for the credits screen, still a WIP 
 * Not gonna document everything yet because it still has
 * a loooooooooooooooooooooooooooooooooooooooooo
 * ooooooooooooooooooooooooooooooooooooooooooooooo
 * oooooooooooooooooooong way to go
 * @author Matthew Gimbut
 *
 */
public class CreditsGUI extends JFrame {

	private JLabel art;
	private JLabel leadDev;
	private JLabel projectManager;
	private JPanel creditsPanel;
	
	public CreditsGUI() {
		
		creditsPanel = new JPanel();
		leadDev = new JLabel("Lead developer: ");
		projectManager = new JLabel("Project manager: ");
		art = new JLabel("Artwork: ");
		
		JPanel leadPanel = new JPanel();
		leadPanel.setLayout(new GridLayout(2, 1));
		leadPanel.add(leadDev);
		leadPanel.add(new JLabel("Matthew Gimbut"));
		creditsPanel.add(leadPanel);
		
		JPanel prjmanPanel = new JPanel();
		prjmanPanel.setLayout(new GridLayout(2,1));
		prjmanPanel.add(projectManager);
		prjmanPanel.add(new JLabel("Matthew Gimbut"));
		creditsPanel.add(prjmanPanel);
		
		JPanel artPanel = new JPanel();
		artPanel.setLayout(new GridLayout(0,1));
		artPanel.add(art);
		artPanel.add(new JLabel("Ryan Nufrio"));
		artPanel.add(new JLabel("Connor Leong"));
		creditsPanel.add(artPanel);
		
		 KeyboardFocusManager manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
	     manager.addKeyEventDispatcher(new KeyEventDispatcher() {
			@Override
			public boolean dispatchKeyEvent(KeyEvent event) {
				if (event.getKeyCode() == KeyEvent.VK_ESCAPE) {
					dispose();
				}
				return false;
			} 
	     });
		
		this.add(creditsPanel);
		this.pack();
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}
	
}
