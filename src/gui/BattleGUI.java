package gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.TitledBorder;

import characters.Enemy;
import characters.MainPlayer;
import main.BattleHandler;
import main.GameController;
import sprites.Player;
import sprites.Sprite;
import sprites.NPC;
import javafx.embed.swing.JFXPanel;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

/**
 * The main GUI for combat scenarios with the player. 
 * @author Matthew Gimbut
 *
 */
@SuppressWarnings("serial")
public class BattleGUI extends JPanel {

	private MainPlayer player;
	private ArrayList<Enemy> enemies; //All enemies in the fight
	private ArrayList<Enemy> fastEnemies; //Enemies which are faster than the player
	private ArrayList<Enemy> slowEnemies; //Enemies which are slow than the player
	//Enemies are sorted into appropriate collections when the player attacks. 
	
	private GameButton attack;
	private GameButton special;
	private GameButton inventory;
	private GameButton stats;
	private GameButton flee;
	private JPanel playerOptions;
	private JPanel battlePanel;
	private ArrayList<JComponent> enemyImages;
	private JPanel spacing;
	private JPanel enemyPanel;
	private JPanel playerPanel;
	private BattleHandler battleHandler;
	private Enemy selectedEnemy;
	private JPanel playerFormatter;
	private JButton leave;
	private Random rand;
	private Timer attackDisable;
	private Timer enemyAttackCounter;
	private Timer enemyAttackFastCounter;
	private Timer enemyAttackSlowCounter;
	private int enemyIndex;
	private int enemyFastIndex;
	private int enemySlowIndex;
	private StatusBarPanel[] enemyHealthBars;
	private JLabel info;
	private JPanel northPanel;
	private StatusBarPanel playerHealthBar;
	private StatusBarPanel playerXPBar;
	private String battleMusic;
	private Media backgroundMusic;
	private Media soundEffect;
	private MediaPlayer musicPlayer;
	private MediaPlayer soundEffectPlayer;
	private ViewPanel currentView;
	private Sprite mapSprite;
	private final int ENEMY_INTERVAL = 1000; //Maximum time interval between enemy attacks.
											 //This is to prevent times from being activated too quickly in succession.
	
	/**
	 * Main constructor for the BattleGUI. 
	 * @param player The current player of the game.
	 * @param enemies The collection of enemies in the fight.
	 * @param currentGame The GameFrame object from where the battle spawned. 
	 * @param battleMusic The music to be played during the battle. If null, default music plays. 
	 */
	public BattleGUI(MainPlayer player, ArrayList<Enemy> enemies, ViewPanel currentView, Sprite mapSprite) {
		
		this.currentView = currentView;
		this.enemies = enemies;
		this.player = player;
		this.battleHandler = new BattleHandler(this);
		this.enemyIndex = 0;
		this.enemyFastIndex = 0;
		this.enemySlowIndex = 0;
		this.fastEnemies = new ArrayList<Enemy>();
		this.slowEnemies = new ArrayList<Enemy>();
		this.mapSprite = mapSprite;
		rand = new Random();
		
		@SuppressWarnings("unused")
		JFXPanel fxPanel = new JFXPanel(); //MUST create JFXPanel in order for JavaFX to work!!
										   //Some bullshit about not initializing the toolkit otherwise
				
		assignBattleMusic();
		
		/*
		 * The three main timers for enemy attacks are instantiated as the simplest timers possible.
		 * This is to prevent NPEs when checking to see if they're running.
		 * Doing it this way is a bit simpler than having to always worry about 
		 * catching the exception if I just left it the old way. 
		 */
		enemyAttackCounter = new Timer(0, event -> {});
		
		enemyAttackFastCounter = new Timer(0, event -> {});
		
		enemyAttackSlowCounter = new Timer(0, event -> {});
		
		backgroundMusic = new Media(Paths.get(this.battleMusic).toUri().toString());
		musicPlayer = new MediaPlayer(backgroundMusic);
		
		enemyHealthBars = new StatusBarPanel[enemies.size()];
		info = new JLabel("Select an enemy to begin combat.");
		info.setFont(GameController.GAME_FONT);
		info.setForeground(Color.BLACK);
		info.setOpaque(false);
		northPanel = new JPanel();
		northPanel.setOpaque(false);
				
		leave = new JButton("Leave");
		leave.setFont(GameController.GAME_FONT);
		leave.setEnabled(false);
		leave.setBackground(GameController.BUTTON_COLOR_THEME);
		leave.addActionListener(new LeaveListener());
		
		playerFormatter = new JPanel();
		enemyImages = new ArrayList<JComponent>();
		enemyPanel = new JPanel();
		playerPanel = new JPanel();
		enemyPanel.setLayout(new FlowLayout());
		playerPanel.setLayout(new GridLayout(2,0));
		attack = new GameButton("Attack");
		attack.addActionListener(new AttackListener());
		
		special = new GameButton("Special");
		special.addActionListener(event -> Runtime.getRuntime().gc());
		
		inventory = new GameButton("Inventory");
		inventory.addActionListener(event -> {
			currentView.displayInventoryPanel(player.getInventory());
		});
		
		stats = new GameButton("Stats");
		stats.addActionListener(event -> {
			currentView.displayStatsPanel(player);
		});
		
		flee = new GameButton("Flee");
		flee.addActionListener(new FleeListener());
				
		playerOptions = new JPanel();
		playerOptions.setLayout(new FlowLayout());
		spacing = new JPanel();
		
		spacing.setOpaque(false);
		enemyPanel.setOpaque(false);
		spacing.setOpaque(false);
		
		drawPlayer();
		drawEnemies();
				
		battlePanel = new JPanel();
		battlePanel.setLayout(new BorderLayout());
		battlePanel.setOpaque(false);
		battlePanel.add(spacing, BorderLayout.CENTER);
		battlePanel.add(spacing, BorderLayout.NORTH);
		
		enemyImages.forEach(enemyImage -> enemyPanel.add(enemyImage));
		
		battlePanel.add(enemyPanel, BorderLayout.EAST);
		battlePanel.add(playerFormatter, BorderLayout.WEST);

		
		playerOptions.setBorder(BorderFactory.createTitledBorder(null, "Player Options", TitledBorder.CENTER,
				TitledBorder.CENTER, GameController.GAME_FONT, Color.WHITE));
		playerOptions.add(attack);
		playerOptions.add(special);
		playerOptions.add(inventory);
		playerOptions.add(stats);
		playerOptions.add(flee);
		playerOptions.add(leave);
		playerOptions.setOpaque(false);
		
		northPanel.add(info);
		
		//Makes background music repeat
		musicPlayer.setOnEndOfMedia(new Runnable() {
			@Override
			public void run() {
				musicPlayer.seek(Duration.ZERO);
			} 
		});
		
		musicPlayer.play();
		musicPlayer.setVolume(GameController.musicVolume); 
		
		this.setBackground(GameController.BACKGROUND_COLOR_THEME);		
		this.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
		this.setLayout(new BorderLayout());
		this.add(northPanel, BorderLayout.CENTER);
		this.add(playerOptions, BorderLayout.SOUTH);
		this.add(battlePanel, BorderLayout.NORTH);
		this.setSize(new Dimension(700, 300));
		this.setVisible(true);
		this.repaint();
	}
	
	/*
	 * Initializes music to generic battle music, before checking to see
	 * if any of the enemies have custom tracks instead.
	 * If they have a custom track, reassigns battleMusic to that track.
	 */
	private void assignBattleMusic() {
		this.battleMusic = "Music\\StolenMusic" + rand.nextInt(2) + ".mp3";
		boolean foundMusic = false;
		int enemySize = enemies.size();
		for(int i = 0; i < enemySize && !foundMusic; i++) {
			String music = enemies.get(i).getCustomMusic();
			if(!music.isEmpty()) {
				this.battleMusic = music;
				foundMusic = true;
			}
		}
	}
	
	/**
	 * Redraws both the player's and enemy's panels on the GUI.
	 * Use sparingly as it is more resource intensive than is usually needed. 
	 */
	public void updateGUI() {
		drawPlayer();
		drawEnemies();
		this.repaint();
	}

	/**
	 * Closes the BattleGUI and reenables the normal map screen.
	 */
	private void closeBattleFrame() {
		musicPlayer.stop();
		if(mapSprite != null) {
			((Enemy) ((NPC) mapSprite).getNPC()).setSelected(false);
		}
		currentView.removeBattlePanel(this);
	}
	
	
	/**
	 * Only is called when all enemies are defeated, right now it just enabled the option to leave the battle.
	 * Will add more in the future. 
	 */
	private void endBattle() {
		leave.setEnabled(true);
		if(mapSprite != null) {
			currentView.getObstacles().remove(mapSprite);
		}
		musicPlayer.stop();
	}

	/**
	 * Empties the player's panel and redraws the player's image and stat bars. 
	 */
	private void drawPlayer() {
		playerPanel.removeAll();
		playerFormatter.setOpaque(false);
		playerPanel.setLayout(new GridLayout(0,1));
		playerPanel.setOpaque(false);
		JLabel name = new JLabel("Lvl " + player.getLvl() + " - " + player.getName(), SwingConstants.CENTER);
		name.setFont(GameController.GAME_FONT);
		playerPanel.add(name);
		playerPanel.add(new JLabel(new ImageIcon(MainPlayer.FACING_EAST)));
		playerHealthBar = new StatusBarPanel("Health", player.getCurrentHP(), player.getMaxHP());
		playerHealthBar.setFont(GameController.GAME_FONT);
		playerPanel.add(playerHealthBar);
		playerXPBar = new StatusBarPanel("XP", player.getXp(), 100);
		playerPanel.add(playerXPBar);
		playerFormatter.add(playerPanel);
		this.repaint();
		this.revalidate();
	}
	
	/**
	 * Empties the panel for the enemies and redraws them each.
	 * More resource intensive than the player's one, avoid if possible 
	 * by using the methods to grab certain components and change them 
	 * that way. 
	 */
	private void drawEnemies() {
		int enemiesLeft = 0;
		enemyPanel.removeAll();
				
		for(Enemy enemy : enemies) {
			if (!enemy.getIsDead()) {
			JPanel enemyFormatter = new JPanel();
			JPanel secondLevelFormatter = new JPanel();
			JLabel name = new JLabel("Lvl " + enemy.getLvl() + " - " + enemy.getName(), SwingConstants.CENTER);
			name.setFont(GameController.GAME_FONT);
			StatusBarPanel enemyHealth = new StatusBarPanel("Health", enemy.getCurrentHP(), enemy.getMaxHP());
			enemyHealthBars[enemiesLeft] = enemyHealth;
			enemyFormatter.setOpaque(false);
			secondLevelFormatter.setOpaque(false);
			enemyFormatter.setLayout(new GridLayout(3, 1));
			enemyFormatter.add(name);
			enemyFormatter.add(new JLabel(new ImageIcon(enemy.getImage())));
			enemyFormatter.add(enemyHealth);
			enemiesLeft++;
			if(enemy.getSelected()) {
				name.setForeground(Color.YELLOW);
			}
			
			/**
			 * Need to know when a player clicks on an enemy to select them. 
			 * Need MouseListener to do this.
			 * However, I DON'T need all of the million methods that the interface requires. 
			 */
			secondLevelFormatter.addMouseListener(new MouseListener() {
				@Override
				public void mouseClicked(MouseEvent event) {}
				@Override
				public void mouseEntered(MouseEvent event) {}
				@Override
				public void mouseExited(MouseEvent event) {}
				@Override
					public void mousePressed(MouseEvent event) {
						for (Enemy enemy : enemies) {
							enemy.setSelected(false);
						}
						enemy.setSelected(true);
						selectedEnemy = enemy;
						drawEnemies();
					}
				@Override
				public void mouseReleased(MouseEvent event) {}	
			});
			secondLevelFormatter.add(enemyFormatter);
			enemyPanel.add(secondLevelFormatter);
			this.repaint();
			this.revalidate();
			}
		}
		
		if(enemiesLeft == 0) {
			this.enemies.removeAll(enemies);
			selectedEnemy = null;
		}
		
		this.repaint();
		this.revalidate();
	}
	
	/**
	 * Gets the array containing the enemy health bars for simpler modification
	 * @return
	 */
	public StatusBarPanel[] getEnemyHealthBars() {
		return enemyHealthBars;
	}
	
	/**
	 * Disables the attack button for a certain amount of time.
	 * This prohibits the player from attacking or fleeing before the timers are complete.
	 * Prevents infinite timers and other weird combat glitches. 
	 */
	private void disableAttackButton() {
		attack.setEnabled(false);
		flee.setEnabled(false);
		leave.setEnabled(false);
		
		attackDisable = new Timer(500, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
					if (!enemyAttackSlowCounter.isRunning() && !enemyAttackFastCounter.isRunning() &&
							!enemyAttackCounter.isRunning() && !battleHandler.isProcessing()) {
						attack.setEnabled(true);
						if(enemies.isEmpty()) {
							endBattle();
							flee.setEnabled(false);
						}
						else {
							flee.setEnabled(true);
						}
						attackDisable.stop();
					}
				}
		});
		attackDisable.start();
	}
	
	/**
	 * This is the method for the player's attack on the selected enemy. After
	 * the battlehandler object takes care of the fight, the slower enemies are
	 * given the cue to attack.
	 */
	private void playerAttack() {
		if (selectedEnemy == null) {
			info.setText("You must select an enemy first!");
		} 
		else if(player.getCurrentHP() == 0) {
			currentView.displayMessagePanel("You have been defeated!");
		}
		else {
			battleHandler.attack(player, selectedEnemy);
			enemyAttackSlower();
		}
	}
	
	/**
	 * All enemies attack the player in order, regardless of their speed rating.
	 * Used when the player fails to flee from a fight. 
	 */
	private void enemyAttackAll() {
		enemyAttackCounter = new Timer(ENEMY_INTERVAL, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (!battleHandler.isProcessing()) {
					try {
						if (!enemies.get(enemyIndex).getIsDead()) {
							battleHandler.attack(enemies.get(enemyIndex), player);
						}
						enemyIndex++;
					} catch (IndexOutOfBoundsException e1) {
						enemyAttackCounter.stop();
						enemyIndex = 0;
					}
				}
			}
		});

		enemyAttackCounter.start();
	}
	
	/**
	 * Goes through the collection of enemies faster than the player on a timer, 
	 * having each one attack as the previous one finishes. 
	 */
	private void enemyAttackFaster() {
		enemyAttackFastCounter = new Timer(ENEMY_INTERVAL, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (!battleHandler.isProcessing()) {
					try {
						if (!fastEnemies.get(enemyFastIndex).getIsDead()) {
							battleHandler.attack(fastEnemies.get(enemyFastIndex), player);
						}
						enemyFastIndex++;
					} catch (IndexOutOfBoundsException e1) {
						enemyFastIndex = 0;
						playerAttack();
						enemyAttackFastCounter.stop();
					}
				}
			}
		});

		enemyAttackFastCounter.start();
	}
	
	/**
	 * Goes through the collection of enemies slower than the player on a timer, 
	 * having each one attack the player as the previous one finishes.
	 */
	private void enemyAttackSlower() {
		enemyAttackSlowCounter = new Timer(ENEMY_INTERVAL, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (!battleHandler.isProcessing()) {
					try {
						if (!slowEnemies.get(enemySlowIndex).getIsDead()) {
							battleHandler.attack(slowEnemies.get(enemySlowIndex), player);
						}
						enemySlowIndex++;
					} catch (IndexOutOfBoundsException e1) {
						enemySlowIndex = 0;
						enemyAttackSlowCounter.stop();
					}
				}
			}
		});

		enemyAttackSlowCounter.start();
	}
	
	/**
	 * Gets the player's health bar for easy modification.
	 * @return The player's health bar.
	 */
	public StatusBarPanel getPlayerHealthBar() {
		return playerHealthBar;
	}
	
	/**
	 * Gets the player's xp bar for easy modification.
	 * @return The player's xp bar.
	 */
	public StatusBarPanel getPlayerXPBar() {
		return playerXPBar;
	}
	
	/**
	 * Gets the info label displayed above the player options.
	 * @return The info JLabel.
	 */
	public JLabel getInfoLabel() {
		return info;
	}
	
	/**
	 * Sets the text of the info JLabel and packs the JFrame.
	 * @param alert The string to set the info label to.
	 */
	public void alert(String alert) {
		info.setText(alert);
		this.repaint();
	}
	
	/**
	 * Inner class to monitor the action of the leave button. 
	 * @author Matthew Gimbut
	 *
	 */
	private class LeaveListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (!player.getIsDead()) {
				closeBattleFrame();
			}
			else {
				currentView.getController().dispose();
				System.exit(0);
			}
		}
		
	}
	
	/**
	 * Inner class to monitor the action of the flee button.
	 * @author Matthew Gimbut
	 *
	 */
	private class FleeListener implements ActionListener {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			disableAttackButton();
			if (player.getIsDead()) {
				closeBattleFrame();
			}
			else if (enemies.size() != 0) {
				if (rand.nextInt(10) < 8) {
					closeBattleFrame();
				} else {
					info.setText("Failed to run away!");
					enemyAttackAll();
					repaint();
				}
			} else {
				selectedEnemy = null;
				for(Enemy enemy : enemies) {
					enemy.setSelected(false);
				}
				revalidate();
				repaint();
				closeBattleFrame();
			}

		}

	}
	
	/**
	 * Inner class to monitor the action of the attack button.
	 * @author Matthew Gimbut
	 *
	 */
	private class AttackListener implements ActionListener {

		
		@Override
		public void actionPerformed(ActionEvent e) {
			
			if(!enemies.isEmpty()) {
			disableAttackButton();
			
			//Removes all objects previously in the collections.
			fastEnemies.removeAll(fastEnemies);
			slowEnemies.removeAll(slowEnemies);
			
			//Goes through each enemy sorting them into the appropriate collection. 
			for(Enemy enemy : enemies) {
				if(enemy.getSpeed() > player.getSpeed()) {
					fastEnemies.add(enemy);
				}
				else {
					slowEnemies.add(enemy);
				}
			}
			
				// Gives the cue that the faster enemies can now attack.
				if (selectedEnemy != null) {
					enemyAttackFaster();
				} else {
					info.setText("Select an enemy");
				}
			}
			else {
				info.setText("No enemy selected.");
			}	
		}
	}

	/**
	 * Gets the ArrayList that contains all of the enemies involved in the fight
	 * @return An ArrayList containing all current enemies
	 */
	public ArrayList<Enemy> getEnemies() {
		return enemies;
	}
	
	/**
	 * Gets the timer for enemy attacks.
	 * @return The timer for enemy attacks. 
	 */
	public Timer getEnemyAttackCounter() {
		return enemyAttackCounter;
	}
	
	/**
	 * Gets the button for leaving fights.
	 * @return The JButton for leaving a fight. 
	 */
	public JButton getLeave() {
		return leave;
	}
	
	/**
	 * Gets the timer for fast enemies.
	 * @return The timer for fast enemies.
	 */
	public Timer getEnemyAttackFastCounter() {
		return enemyAttackFastCounter;
	}
	
	public ViewPanel getCurrentView() {
		return currentView;
	}
	
	/**
	 * Gets the timer for slow enemies
	 * @return
	 */
	public Timer getEnemyAttackSlowCounter() {
		return enemyAttackSlowCounter;
	}

	public Media getSoundEffect() {
		return soundEffect;
	}

	public void setSoundEffect(Media soundEffect) {
		this.soundEffect = soundEffect;
	}

	public MediaPlayer getSoundEffectPlayer() {
		return soundEffectPlayer;
	}
	
	public void setSoundEffectPlayer(MediaPlayer soundEffectPlayer) {
		this.soundEffectPlayer = soundEffectPlayer;
	}

	public void soundPlayer(String filepath, double volume) {
		soundEffect = new Media(Paths.get(filepath).toUri().toString());
		soundEffectPlayer = new MediaPlayer(soundEffect);
		soundEffectPlayer.setVolume(volume);
		this.repaint();
		soundEffectPlayer.play();
	}
	
	public void resetSelectedEnemy() {
		selectedEnemy = null;
	}
	
}
