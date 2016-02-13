package gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;
import javax.swing.*;
import characters.Enemy;
import characters.MainPlayer;
import items.Item;
import main.GameController;
import sprites.GenericObstacle;
import sprites.Lootable;
import sprites.Player;
import sprites.Save;
import sprites.Sprite;

public class ViewPanel extends JPanel implements ActionListener {

	private Timer timer;
	private Player player;
	private boolean ingame;
	public static final int PLAYER_X = 44;
	public static final int PLAYER_Y = 39;
	public static final int B_WIDTH = 1280;
	public static final int B_HEIGHT = 1024;
	private final int DELAY = 15;
	private ArrayList<Lootable> lootables;
	private ArrayList<GenericObstacle> genericObstacles;
	private ArrayList<Sprite> obstacles;
	private GameController control;
	private boolean menuCurrentlyDisplayed;
	private boolean battleCurrentlyDisplayed;
	private boolean statsCurrentlyDisplayed;
	private boolean inventoryCurrentlyDisplayed;
	private boolean lootCurrentlyDisplayed;
	private boolean settingsCurrentlyDisplayed;
	private boolean messageCurrentlyDisplayed;
	private MenuPanel menu;
	private final static String FILE_NAME = "Saves\\save01.ser";
	private final static boolean DO_NOT_APPEND = false;

	public ViewPanel(GameController control) {
		initFlags();
		this.control = control;
		initCollections();
		initPanel();
	}

	public ViewPanel(GameController control, Player player) {
		this.control = control;
		this.player = player;
		initFlags();
		initCollections();
		initPanel();
	}

	public ViewPanel(GameController control, ArrayList<Sprite> sprites) {
		this.control = control;
		this.player = (Player) sprites.get(0);
		sprites.remove(0);

		obstacles = new ArrayList<Sprite>();
		genericObstacles = new ArrayList<GenericObstacle>();
		lootables = new ArrayList<Lootable>();

		for (Sprite sprite : sprites) {
			if (sprite instanceof GenericObstacle) {
				genericObstacles.add((GenericObstacle) sprite);
			} else if (sprite instanceof Lootable) {
				lootables.add((Lootable) sprite);
			} else {
				obstacles.add(sprite);
			}
		}

		initFlags();
		initPanel();
		this.revalidate();
	}

	private void initFlags() {
		menuCurrentlyDisplayed = false;
		battleCurrentlyDisplayed = false;
		statsCurrentlyDisplayed = false;
		inventoryCurrentlyDisplayed = false;
		lootCurrentlyDisplayed = false;
		settingsCurrentlyDisplayed = false;
		messageCurrentlyDisplayed = false;
	}

	private void initCollections() {
		obstacles = new ArrayList<Sprite>();
		genericObstacles = new ArrayList<GenericObstacle>();
		lootables = new ArrayList<Lootable>();
		lootables.add(new Lootable(500, 500));
		lootables.add(new Lootable(300, 300));
		genericObstacles.add(new GenericObstacle(550, 200));
		genericObstacles.add(new GenericObstacle(200, 200));
		genericObstacles.add(new GenericObstacle(400, 400, "Images\\testgif.gif"));
		obstacles.add(new Save(200, 600));

	}

	private void initPanel() {
		setOpaque(false);
		addKeyListener((KeyListener) new TAdapter());
		setFocusable(true);
		setBackground(Color.BLACK);
		ingame = true;
		menu = new MenuPanel(this);

		setPreferredSize(new Dimension(B_WIDTH, B_HEIGHT));

		if (player == null) {
			player = new Player(500, 100, new MainPlayer("Matthew"), this);
		}

		this.setLayout(null);
		determineObstacles();

		timer = new Timer(DELAY, this);
		timer.start();
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		if (ingame) {

			drawObjects(g);

		} else {

			drawGameOver(g);
		}

		Toolkit.getDefaultToolkit().sync();
	}

	private void determineObstacles() {
		for (Lootable loot : lootables) {
			if (loot.isObstacle() && loot.isVisible()) {
				obstacles.add(loot);
			}
		}
		for (GenericObstacle obst : genericObstacles) {
			if (obst.isVisible()) {
				obstacles.add(obst);
			}
		}
	}

	private void drawObjects(Graphics g) {

		if (player.isVisible()) {
			g.drawImage(player.getImage(), player.getX(), player.getY(), this);
		}

		for (Sprite sprite : obstacles) {
			if (sprite.isVisible()) {
				g.drawImage(sprite.getImage(), sprite.getX(), sprite.getY(), this);
			}
		}

		g.setColor(Color.WHITE);
	}

	private void drawGameOver(Graphics g) {

		String msg = "Game Over";
		Font small = new Font("Helvetica", Font.BOLD, 14);
		FontMetrics fm = getFontMetrics(small);

		g.setColor(Color.white);
		g.setFont(small);
		g.drawString(msg, (B_WIDTH - fm.stringWidth(msg)) / 2, B_HEIGHT / 2);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		inGame();
		updatePlayer();
		updateNPC();
		repaint();
	}
	
	private void inGame() {
		if (!ingame) {
			timer.stop();
		}
	}
	
	public void move(Sprite sprite) {

		sprite.modifyX(sprite.getDX());
		sprite.modifyY(sprite.getDY());

		if (sprite.getX() < 1) {
			sprite.setX(1);
		}

		if (sprite.getY() < 1) {
			sprite.setY(1);
		}

		if (sprite.getX() > ViewPanel.B_WIDTH - 50) {
			sprite.setX(ViewPanel.B_WIDTH - 50);
		}

		if (sprite.getY() > ViewPanel.B_HEIGHT - 80) {
			sprite.setY(ViewPanel.B_HEIGHT - 80);
		}

		if (collision()) {
			sprite.modifyX(-sprite.getDX());
			sprite.modifyY(-sprite.getDY());
		}
	}

	private void updatePlayer() {
		if (player.isVisible() && !engaged()) {
			move(player);
		}
	}

	private void updateNPC() {
		
	}
	
	public void displayLootPanel(LinkedList<Item> items) {
		LootGUI loot = new LootGUI(items, player.getMainPlayer(), this);
		if (!lootCurrentlyDisplayed) {
			lootCurrentlyDisplayed = true;
			addPanel(loot);
			loot.setLocation(B_WIDTH / 3, B_HEIGHT / 3);
		} 
	}
	
	public void removeLootPanel(LootGUI loot) {
		lootCurrentlyDisplayed = false;
		removePanel(loot);
	}

	public void toggleMenu() {
		if (!menuCurrentlyDisplayed && !engaged()) {
			menuCurrentlyDisplayed = true;
			addPanel(menu);
			menu.setLocation(0, 0);
		} else {
			menuCurrentlyDisplayed = false;
			removePanel(menu);
		}
	}

	public void displayBattlePanel(ArrayList<Enemy> enemies) {
		BattleGUI battlePanel = new BattleGUI(player.getMainPlayer(), enemies, this, null);
		if (!battleCurrentlyDisplayed) {
			battleCurrentlyDisplayed = true;
			addPanel(battlePanel);
			battlePanel.setLocation(100, 100);
		}
	}
	
	public void removeBattlePanel(BattleGUI panel) {
		battleCurrentlyDisplayed = false;
		removePanel(panel);
	}

	public void displayInventoryPanel(LinkedList<Item> inventory) {
		InventoryGUI invPanel = new InventoryGUI(player.getMainPlayer(), this);
		if (!inventoryCurrentlyDisplayed) {
			inventoryCurrentlyDisplayed = true;
			addPanel(invPanel);
			invPanel.setLocation(300, 300);
		}
	}
	
	public void removeInventoryPanel(InventoryGUI panel) {
		inventoryCurrentlyDisplayed = false;
		removePanel(panel);
	}

	public void displaySettingsPanel(MainPlayer player) {
		SettingsGUI settings = new SettingsGUI(player, this);
		if (!settingsCurrentlyDisplayed) {
			settingsCurrentlyDisplayed = true;
			addPanel(settings);
			settings.setLocation(300, 300);
		}
	}
	
	public void removeSettingsPanel(SettingsGUI settings) {
		settingsCurrentlyDisplayed = false;
		removePanel(settings);
	}

	public void displayStatsPanel(MainPlayer player) {
		PlayerInfoGUI info = new PlayerInfoGUI(player, this);
		if (!statsCurrentlyDisplayed) {
			statsCurrentlyDisplayed = true;
			addPanel(info);
			info.setLocation(300, 300);
		}
	}
	
	public void removeStatsPanel(PlayerInfoGUI info) {
		statsCurrentlyDisplayed = false;
		removePanel(info);
	}

	public void displayMessagePanel(String message) {
		MessageGUI messagePanel = new MessageGUI(message, player.getMainPlayer(), this);
		if (!messageCurrentlyDisplayed) {
			messageCurrentlyDisplayed = true;
			addPanel(messagePanel);
			messagePanel.setLocation(0, 800);
		}
	}
	
	public void removeMessagePanel(MessageGUI message) {
		messageCurrentlyDisplayed = false;
		removePanel(message);
	}

	private void removePanel(JPanel panel) {
		this.remove(panel);
		this.repaint();
		this.revalidate();
	}

	private void addPanel(JPanel panel) {
		this.add(panel);
		this.repaint();
		this.revalidate();
	}

	public boolean engaged() {
		return (menuCurrentlyDisplayed || battleCurrentlyDisplayed || statsCurrentlyDisplayed
				|| inventoryCurrentlyDisplayed || lootCurrentlyDisplayed || settingsCurrentlyDisplayed
				|| messageCurrentlyDisplayed);
	}

	public ArrayList<Sprite> getObstacles() {
		return obstacles;
	}

	public GameController getController() {
		return control;
	}

	public Player getPlayer() {
		return player;
	}

	public void serialize() {
		ArrayList<Sprite> toSave = new ArrayList<Sprite>();
		toSave.add(player);
		toSave.addAll(obstacles);

		try (FileOutputStream fs = new FileOutputStream(FILE_NAME, DO_NOT_APPEND);
				ObjectOutputStream os = new ObjectOutputStream(fs)) {
			os.writeObject(toSave);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static ArrayList<Sprite> deserialize() {
		try (FileInputStream fs = new FileInputStream(FILE_NAME); ObjectInputStream os = new ObjectInputStream(fs)) {
			return (ArrayList<Sprite>) os.readObject();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		// If the deserialize fails return null
		return null;
	}

	private void interact(int x, int y) {
		Rectangle interactArea = new Rectangle(ViewPanel.PLAYER_X, ViewPanel.PLAYER_Y);
		interactArea.setLocation(x, y);
		for (Sprite obstacle : obstacles) {
			if (interactArea.intersects(obstacle.getBounds())) {
				if (obstacle instanceof Lootable) {
					displayLootPanel(((Lootable) obstacle).getItems());
					repaint();
				} else if (obstacle instanceof Save) {
					serialize();
					displayMessagePanel("Save succeeded!");
				}
			}
		}
	}

	private void enemyEncounter() {
		Random rand = new Random();
		if (rand.nextInt(1000) < 25) {
			displayBattlePanel(GameController.getRandomEnemies(3));
		}

	}

	private boolean collision() {
		for (Sprite obstacle : obstacles) {
			if (player.getBounds().intersects(obstacle.getBounds()))
				return true;
		}
		return false;
	}

	private class TAdapter extends KeyAdapter {
		@Override
		public void keyReleased(KeyEvent e) {
			int key = e.getKeyCode();
			if (key == KeyEvent.VK_SHIFT) {
				toggleMenu();
			} else {
				switch (key) {

				case KeyEvent.VK_SPACE:
					player.setSpriteSpeed(2);
					break;
				case KeyEvent.VK_LEFT:
					player.setDX(0);
					break;
				case KeyEvent.VK_RIGHT:
					player.setDX(0);
					break;
				case KeyEvent.VK_UP:
					player.setDY(0);
					break;
				case KeyEvent.VK_DOWN:
					player.setDY(0);
					break;
				}
			}
		}

		@Override
		public void keyPressed(KeyEvent e) {
			int key = e.getKeyCode();

			if (!engaged()) {
				switch (key) {
				case KeyEvent.VK_ENTER:
					if (player.getImageLocation().equals(MainPlayer.FACING_NORTH)) {
						interact(player.getX(), player.getY() - ViewPanel.PLAYER_Y);
					} else if (player.getImageLocation().equals(MainPlayer.FACING_SOUTH)) {
						interact(player.getX(), player.getY() + ViewPanel.PLAYER_Y);
					} else if (player.getImageLocation().equals(MainPlayer.FACING_EAST)) {
						interact(player.getX() + ViewPanel.PLAYER_X, player.getY());
					} else if (player.getImageLocation().equals(MainPlayer.FACING_WEST)) {
						interact(player.getX() - ViewPanel.PLAYER_X, player.getY());
					}
					break;

				case KeyEvent.VK_SPACE:
					player.setSpriteSpeed(4);
					break;

				case KeyEvent.VK_LEFT:
					player.setDX(-player.getSpriteSpeed());
					player.loadImage(MainPlayer.FACING_WEST);
					enemyEncounter();
					break;

				case KeyEvent.VK_RIGHT:
					player.setDX(player.getSpriteSpeed());
					player.loadImage(MainPlayer.FACING_EAST);
					enemyEncounter();
					break;

				case KeyEvent.VK_UP:
					player.setDY(-player.getSpriteSpeed());
					player.loadImage(MainPlayer.FACING_NORTH);
					enemyEncounter();
					break;

				case KeyEvent.VK_DOWN:
					player.setDY(player.getSpriteSpeed());
					player.loadImage(MainPlayer.FACING_SOUTH);
					enemyEncounter();
					break;
				}
			}

		}
	}
}
