package gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import javax.imageio.ImageIO;
import javax.swing.*;
import characters.Enemy;
import characters.MainPlayer;
import characters.Neutral;
import items.Item;
import main.GameController;
import main.MapParser;
import main.SaveManager;
import sprites.DisplayItem;
import sprites.Exit;
import sprites.Lootable;
import sprites.NPC;
import sprites.Player;
import sprites.Save;
import sprites.Sprite;
import sprites.UnderLayer;


/**
 * This is the main panel class that holds the game.
 * All objects, menus, windows, and the player are held within this panel. 
 * @author Matthew Gimbut
 *
 */
@SuppressWarnings("serial")
public class ViewPanel extends JPanel {

	private Timer timer;
	private Player player;
	public static final int PLAYER_X = 44;
	public static final int PLAYER_Y = 39;
	public static final int B_WIDTH = 1056;
	public static final int B_HEIGHT = 730;
	private final int DELAY = 15; //DONT CHANGE THIS EVER
	private ArrayList<Sprite> mapItems;
	private ArrayList<Sprite> underLayer;
	private ArrayList<Sprite> overLayer;
	private ArrayList<Sprite> collisions;
	private GameController control;
	private boolean menuCurrentlyDisplayed;
	private boolean battleCurrentlyDisplayed;
	private boolean statsCurrentlyDisplayed;
	private boolean inventoryCurrentlyDisplayed;
	private boolean lootCurrentlyDisplayed;
	private boolean settingsCurrentlyDisplayed;
	private boolean messageCurrentlyDisplayed;
	private boolean hostile;
	private MenuPanel menu;
	private GridBagLayout gridbag;
	private GridBagConstraints centerConstraints;
	private String currentMapFile;
	private MapParser mapParser;
	private Image image;
	
	/**
	 * Constructor that takes a GameController object only.
	 * @param control The GameController JFrame that holds the ViewPanel.
	 */
	public ViewPanel(GameController control) {
		initFlags();
		this.control = control;
		mapParser = new MapParser(this);
		initCollections();
		initPanel();
	}

	/**
	 * Constructor that takes both a GameController object and a player to create the ViewPanel.
	 * @param control The GameController JFrame that holds the ViewPanel.
	 * @param player The player's sprite object that is displayed on the ViewPanel.
	 */
	public ViewPanel(GameController control, Player player) {
		this.control = control;
		this.player = player;
		mapParser = new MapParser(this);
		initFlags();
		initCollections();
		initPanel();
	}

	/**
	 * Constructor that takes a GameController object and an ArrayList of sprites to display on the map.
	 * This was used a while ago for loading saves, but is not needed anymore.
	 * @param control The GameController JFrame that holds the ViewPanel
	 * @param sprites An ArrayList of sprite objects that are to be displayed in the map, including the player's sprite at index 0.
	 */
	@Deprecated
	public ViewPanel(GameController control, ArrayList<Sprite> sprites) {
		this.control = control;
		this.player = (Player) sprites.get(0);
		sprites.remove(0);

		mapItems = new ArrayList<Sprite>();
		underLayer = new ArrayList<Sprite>();
		overLayer = new ArrayList<Sprite>();

		initFlags();
		initPanel();
	}

	/**
	 * Initializes all of the boolean flags used to keep track of the player's activity and the currently opened menus.
	 */
	private void initFlags() {
		menuCurrentlyDisplayed = false;
		battleCurrentlyDisplayed = false;
		statsCurrentlyDisplayed = false;
		inventoryCurrentlyDisplayed = false;
		lootCurrentlyDisplayed = false;
		settingsCurrentlyDisplayed = false;
		messageCurrentlyDisplayed = false;
	}

	/**
	 * Initializes ArrayList<Sprite> mapItems to an ArrayList read from file to load in the maps.
	 * Then calls the updateLayers() method to add the different layers of the map to their appropriate collections.
	 */
	private void initCollections() {
		try {
			String map = "Saves\\Save01\\Maps\\Map0-1.map";
			mapItems = mapParser.parseMap(map);
			setCurrentMapFile(map);
			updateLayers();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Initializes/updates values for the top and lower layers of the map objects. 
	 */
	void updateLayers() {
		underLayer = (ArrayList<Sprite>) mapItems.stream()
				.filter(sprite -> sprite instanceof UnderLayer)
				.filter(Sprite::isVisible)
				.collect(Collectors.toList());
		
		overLayer = (ArrayList<Sprite>) mapItems.stream()
				.filter(sprite -> !(sprite instanceof UnderLayer))
				.filter(Sprite::isVisible)
				.collect(Collectors.toList());
		
		collisions = (ArrayList<Sprite>) mapItems.stream()
				.filter(Sprite::isObstacle)
				.collect(Collectors.toList());
	}
	
	public void setBackground(String file) throws IOException {
		image = ImageIO.read(new File(file));
	}
	
	/**
	 * Initializes most of the settings for the ViewPanel.
	 * Also adds the player to the view. 
	 */
	private void initPanel() {
		this.setOpaque(false);
		this.addKeyListener((KeyListener) new TAdapter());
		this.setFocusable(true);
		menu = new MenuPanel(this); //TODO: Not sure why this line is here? Check later
			
		gridbag = new GridBagLayout();
		centerConstraints = new GridBagConstraints();
		centerConstraints.fill = GridBagConstraints.CENTER;
		gridbag.setConstraints(this, centerConstraints);
		this.setLayout(gridbag);
		this.setPreferredSize(new Dimension(B_WIDTH, B_HEIGHT));
		
		
		if (player == null) {
			player = new Player(500, 100, new MainPlayer("Matthew Gimbut"));
		}

		timer = new Timer(DELAY, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {	
				updatePlayer();
				updateNPC(); //Does nothing yet
				repaint();
			}
		});
		
		timer.start();
	}

	@Override
	/**
	 * Overridden paintComponent method to draw objects onto the screen
	 */
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(image, 0, 0, this);
		drawLayers(g);
		Toolkit.getDefaultToolkit().sync();
	}

	/**
	 * Method that does the actual drawing on the screen. 
	 * This method should only be called from within the overridden paintComponent() method.
	 * @param g The graphics object from the paintComponent method. 
	 */
	private void drawLayers(Graphics g) {
		Consumer<Sprite> draw = sprite -> g.drawImage(sprite.getImage(), sprite.getX(), sprite.getY(), this);
		underLayer.forEach(draw);
		if (player.isVisible()) g.drawImage(player.getImage(), player.getX(), player.getY(), this);
		overLayer.forEach(draw);
	}
	
	/**
	 * Method used for moving sprites. 
	 * Right now, only the player can move but in the future that will hopefully be different.
	 * @param sprite The sprite to be moved around the map.
	 */
	public void move(Sprite sprite) {

		sprite.modifyX(sprite.getDX());
		sprite.modifyY(sprite.getDY());
		
		//If the player tries moving out of bounds to the left.
		if (sprite.getX() < 1) sprite.setX(1); 
		//If the player tries moving out of bounds upwards.
		if (sprite.getY() < 1) sprite.setY(1); 
		//If the player tries moving out of bounds to the right.
		if (sprite.getX() > ViewPanel.B_WIDTH - 50) sprite.setX(ViewPanel.B_WIDTH - 50); 
		//If the player tries moving out of bounds downwards.
		if (sprite.getY() > ViewPanel.B_HEIGHT - 80) sprite.setY(ViewPanel.B_HEIGHT - 80); 

		if (collision()) {
			sprite.modifyX(-sprite.getDX());
			sprite.modifyY(-sprite.getDY());
		}
	}

	/**
	 * Only updates the player's location if they aren't currently engaged in a menu and are visible.
	 */
	private void updatePlayer() {
		if (player.isVisible() && !engaged()) {
			move(player);
		}
	}

	/**
	 * Updates npcs after they move around.
	 * Method is empty right now because that feature hasn't been implemented yet. 
	 * But this is where it would go if it were.
	 */
	private void updateNPC() {
		//TODO
	}
	
	/**
	 * The method used to display loot whenever a player opens a chest or defeats a boss, etc.
	 * @param items The LinkedList that contains all of the items to be displayed.
	 */
	public void displayLootPanel(LinkedList<Item> items) {
		LootGUI loot = new LootGUI(items, player.getMainPlayer(), this);
		if (!lootCurrentlyDisplayed) {
			lootCurrentlyDisplayed = true;
			addPanel(loot, null);
			loot.setLocation(B_WIDTH / 3, B_HEIGHT / 3);
		} 
	}
	
	/**
	 * Removes a previously added LootGUI from the screen.
	 * @param loot The loot panel to be removed.
	 */
	public void removeLootPanel(LootGUI loot) {
		lootCurrentlyDisplayed = false;
		removePanel(loot);
	}

	/**
	 * Toggles the general options menu in the top left of the screen. 
	 */
	public void toggleMenu() {
		if (!menuCurrentlyDisplayed && !engaged()) {
			menuCurrentlyDisplayed = true;
			GridBagConstraints c = new GridBagConstraints();
			c = new GridBagConstraints();
			c.anchor = GridBagConstraints.NORTHWEST;
			c.insets = new Insets(6,6,6,6);
			c.weightx = 1;
			c.weighty = 1;
			addPanel(menu, c);
			menu.setLocation(0, 0);
		} else {
			menuCurrentlyDisplayed = false;
			removePanel(menu);
		}
	}

	/**
	 * Displays the battle panel when a player gets into a fight. 
	 * @param enemies The ArrayList of enemies that the player is fighting against. 
	 * @param mapSprite The Sprite object on the map that is used to display the enemy. Will be null if the battle is a random encounter.
	 */
	public void displayBattlePanel(ArrayList<Enemy> enemies, Sprite mapSprite) {
		BattleGUI battlePanel = new BattleGUI(player.getMainPlayer(), enemies, this, mapSprite);
		if (!battleCurrentlyDisplayed) {
			battleCurrentlyDisplayed = true;
			addPanel(battlePanel, null);
			battlePanel.setLocation(280, 290);
		}
	}

	/**
	 * Removes a previously added battle panel from the screen.
	 * @param panel The battle panel to be removed. 
	 */
	public void removeBattlePanel(BattleGUI panel) {
		battleCurrentlyDisplayed = false;
		removePanel(panel);
	}

	/**
	 * Displays the contents of the player's inventory on the screen. 
	 * @param inventory A LinkedList containing all of the items in the player's inventory.
	 */
	public void displayInventoryPanel(LinkedList<Item> inventory) {
		TabbedInventory invPanel = new TabbedInventory(player.getMainPlayer(), this);
		if (!inventoryCurrentlyDisplayed) {
			inventoryCurrentlyDisplayed = true;
			addPanel(invPanel, null);
			invPanel.setLocation(225, 240);
		}
	}
	
	/**
	 * Removes a previously added inventory panel from the screen. 
	 * @param panel The inventory panel to be removed. 
	 */
	public void removeInventoryPanel(TabbedInventory panel) {
		inventoryCurrentlyDisplayed = false;
		removePanel(panel);
	}

	/**
	 * Displays the main options menu for the game, allowing the user to pick and choose between different settings. 
	 * @param player The MainPlayer object (not its sprite) that is the player's character. 
	 */
	public void displaySettingsPanel(MainPlayer player) {
		SettingsPanel settings = new SettingsPanel(player, this);
		if (!settingsCurrentlyDisplayed) {
			settingsCurrentlyDisplayed = true;
			addPanel(settings, null);
			settings.setLocation(375, 300);
		}
	}
	
	/**
	 * Removes a previously added settings panel from the screen. 
	 * @param settings The settings panel to be removed. 
	 */
	public void removeSettingsPanel(SettingsPanel settings) {
		settingsCurrentlyDisplayed = false;
		removePanel(settings);
	}

	/**
	 * Displays the stats panel for the main character. 
	 * @param player The MainPlayer object (not its sprite) that is the player's character.
	 */
	public void displayStatsPanel(MainPlayer player) {
		PlayerInfoGUI info = new PlayerInfoGUI(player, this);
		if (!statsCurrentlyDisplayed) {
			statsCurrentlyDisplayed = true;
			addPanel(info, null);
			info.setLocation(310, 250);
		}
	}
	
	/**
	 * Removes a previously added stats panel from the screen.
	 * @param info The stats panel that is to be removed.
	 */
	public void removeStatsPanel(PlayerInfoGUI info) {
		statsCurrentlyDisplayed = false;
		removePanel(info);
	}

	/**
	 * Adds a scrolling text message panel to the screen.
	 * This version of the method is for displaying only a single String.
	 * @param message The String to be displayed by the message panel.
	 */
	public void displayMessagePanel(String message) {
		MessageGUI messagePanel = new MessageGUI(message, player.getMainPlayer(), this);
		messageInfo(messagePanel);
	}

	/**
	 * Adds a scrolling text message panel to the screen.
	 * This version of the method is for displaying several lines of dialogue.
	 * @param message An array of Strings with each location of the array being another line of dialogue to be displayed by the panel.
	 * @param npc The NPC object which is the source for the dialogue. 
	 */
	public void displayMessagePanel(String[] message, NPC npc) {
		MessageGUI messagePanel = new MessageGUI(message, player.getMainPlayer(), this, npc);
		messageInfo(messagePanel);
	}
	
	/**
	 * Initializes settings and adds the message panel to the screen. 
	 * A GridBagLayout is used to appropriately position the dialogue boxes on the screen. 
	 * This also allows us to fix the size of the panel accordingly.
	 * @param messagePanel The message panel that is to be displayed.
	 */
	private void messageInfo(MessageGUI messagePanel) {
		if (!messageCurrentlyDisplayed) {
			messageCurrentlyDisplayed = true;
			GridBagConstraints c = new GridBagConstraints();
			c = new GridBagConstraints();
			c.ipadx = 900;
			c.ipady = 0;
			c.insets = new Insets(12, 12, 22, 22);
			c.weightx = 1;
			c.weighty = 1;
			if(player.getY() > ViewPanel.B_HEIGHT/2) {
				c.anchor = GridBagConstraints.NORTH;
				c.ipady = 0;
			} else {
				c.insets = new Insets(550, 12, 22, 22);
			}
			addPanel(messagePanel, c);
			messagePanel.setLocation(0, 960);
			messagePanel.setFocusable(true);
			messagePanel.requestFocusInWindow();
		}
	}
	
	/**
	 * Removes a previously added message panel from the screen. 
	 * @param message The message panel to be removed. 
	 */
	public void removeMessagePanel(MessageGUI message) {
		messageCurrentlyDisplayed = false;
		removePanel(message);
		this.setFocusable(true);
		this.requestFocusInWindow();
	}

	/**
	 * General remove panel method.
	 * Whenever you are going to remove a panel, call this method.
	 * It automatically repaints and revalidates the ViewPanel upon modification.
	 * @param panel The panel to be removed from the view.
	 */
	private void removePanel(JPanel panel) {
		this.remove(panel);
		this.repaint();
		this.revalidate();
	}

	/**
	 * General add panel method.
	 * Whenever you are trying to add a panel, this method should be used.
	 * It automatically repaint and revalidates the ViewPanel, as well as handling any GridBagConstraints that may be associated with it.
	 * @param panel The panel to be added to the view.
	 * @param c The GridBadConstraints that tell the ViewPanel where to place it. Null if panel is to be placed in the center of the screen. 
	 */
	private void addPanel(JPanel panel, GridBagConstraints c) {
		if(c != null) {
			this.add(panel, c);
		} else {
			this.add(panel);
		}
		this.repaint();
		this.revalidate();
	}

	/**
	 * Checks to see if the player is currently engaged in some menu.
	 * @return Whether or not the player is engaged. 
	 */
	public boolean engaged() {
		return (menuCurrentlyDisplayed || battleCurrentlyDisplayed || statsCurrentlyDisplayed
				|| inventoryCurrentlyDisplayed || lootCurrentlyDisplayed || settingsCurrentlyDisplayed
				|| messageCurrentlyDisplayed);
	}

	/**
	 * Getter method for the current items on the map.
	 * @return
	 */
	public ArrayList<Sprite> getObstacles() {
		return mapItems;
	}

	/**
	 * Getter method for the JFrame that currently holds the ViewPanel.
	 * @return
	 */
	public GameController getController() {
		return control;
	}

	/**
	 * Getter method for the current player's sprite object.
	 * @return
	 */
	public Player getPlayer() {
		return player;
	}

	/**
	 * Method for determining interactions. 
	 * Creates an invisible rectangle in the direction the player is facing.
	 * If that rectangle intersects an obstacle, the player interacts with it. 
	 * @param x The x coordinate of where to set the rectangle.
	 * @param y The y coordinate of where to set the rectangle. 
	 */
	private void interact(int x, int y) {
		Rectangle interactArea = new Rectangle(ViewPanel.PLAYER_X, ViewPanel.PLAYER_Y);
		DisplayItem remove = null;
		interactArea.setLocation(x, y);
		for (Sprite obstacle : mapItems) {
			if (interactArea.intersects(obstacle.getBounds())) {
				if (obstacle instanceof Lootable) {
					displayLootPanel(((Lootable) obstacle).getItems());
					repaint();
				} else if (obstacle instanceof Save) {
					SaveManager.serialize(mapItems, player);
					displayMessagePanel("Save succeeded!");
				} else if (obstacle instanceof NPC) {
					npcInteraction(obstacle);
				} else if (obstacle instanceof DisplayItem) {
					itemInteraction((DisplayItem) obstacle);
					remove = (DisplayItem) obstacle;
				}
			}
		}
		if (remove != null) { //Removes an item from the map if it is tagged for removal.
			mapItems.remove(remove);
			updateLayers();
		}
	}

	/**
	 * Method for a player interacting with an item on the ground. 
	 * Right now, the only action is to allow them to pick it up and add it to their inventory.
	 * @param item The Sprite of the item to be added to the inventory.
	 */
	private void itemInteraction(DisplayItem item) {
		player.setDX(0); //Sets the player's x and y movement to 0 so that they don't begin moving on their own after finished with dialogue.
		player.setDY(0);
		displayMessagePanel("You have picked up a " + item.getItem().getSimpleName() + ".");
		player.getMainPlayer().addItem(item.getItem());
		
		//Below segment is commented out for testing reasons.
		//It works perfectly, but I don't want items being removed from file while testing.
		
		/*try {
			MapParser.removeItem("item", currentMapFile, item.getX(), item.getY());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}*/
	}
	
	/**
	 * Method for a player interacting with NPCs. 
	 * Shows plain dialogue for non-hostile NPCs.
	 * Shows dialogue then a battle for hostiles.
	 * @param obstacle The sprite on the map that contains the NPC.
	 */
	private void npcInteraction(Sprite obstacle) {
		if(((NPC) obstacle).getNPC() instanceof Enemy) {
			if(((NPC) obstacle).getMessage() != null) {
				displayMessagePanel(((NPC) obstacle).getMessage(), (NPC) obstacle);
			} else {
				//TODO inefficient/pointless, find better way to do this
				ArrayList<Enemy> enemy = new ArrayList<Enemy>();
				enemy.add((Enemy) ((NPC) obstacle).getNPC());
				displayBattlePanel(enemy, obstacle);
			}
		} else if (((NPC) obstacle).getNPC() instanceof Neutral) {
			displayMessagePanel(((NPC) obstacle).getMessage(), (NPC) obstacle);
		}
	}
	
	/**
	 * Setter method for the map obstacles. 
	 * @param mapItems
	 */
	public void setMapItems(ArrayList<Sprite> mapItems) {
		this.mapItems = mapItems;
	}
	
	/**
	 *  Determines random enemy encounter. 
	 */
	private void enemyEncounter() {
		if (hostile) {
			Random rand = new Random();
			if (rand.nextInt(1000) < 2) {
				displayBattlePanel(GameController.getRandomEnemies(3), null);
			}
		}
	}

	/**
	 * Updates map items when the player moves to the next region of the map.
	 * @param exit The invisible Exit sprite that contains the next map and next player coordinates. 
	 */
	private void updateMapItems(Exit exit) {
		try {
			String nextMap = exit.getNextMapLocation();
			mapItems = mapParser.parseMap(nextMap);
			setCurrentMapFile(nextMap); //Sets the current map file and map items to the new map.
			updateLayers(); //Updates the upper and lower layers for the new map.
			player.setX(exit.getNextX()); //Sets the player to the appropriate coordinates in the new area.
			player.setY(exit.getNextY());
		} catch (FileNotFoundException e) {
			System.out.println("File not found???");
		} catch (IOException e) {
			System.out.println("Something broke I/O");
		}
	}
	
	/**
	 * Checks for player collisions upon movement. 
	 * @return
	 */
	private boolean collision() {
		for (Sprite obstacle : collisions) {
			if (player.getBounds().intersects(obstacle.getBounds())){
				if(obstacle instanceof Exit) { 
					updateMapItems((Exit) obstacle);	//Moves the player to the next area if they move on an exit.
				} else {
					return true;
				}
			}
		}
		return false;
	}
	
	public boolean isHostile() {
		return hostile;
	}

	public void setHostile(boolean hostile) {
		this.hostile = hostile;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	/**
	 * @return the currentMapFile
	 */
	public String getCurrentMapFile() {
		return currentMapFile;
	}

	/**
	 * @param currentMapFile the currentMapFile to set
	 */
	public void setCurrentMapFile(String currentMapFile) {
		this.currentMapFile = currentMapFile;
	}

	/**
	 * Inner class that handles all key presses and allows for player movement on the screen.
	 * @author Matthew Gimbut
	 *
	 */
	private class TAdapter extends KeyAdapter {
		@Override
		public void keyReleased(KeyEvent e) {
			int key = e.getKeyCode();
			switch (key) {
			case KeyEvent.VK_ESCAPE:
				toggleMenu();
				break;
			case KeyEvent.VK_SHIFT:
				player.setSpriteSpeed(2);
				break;
			case KeyEvent.VK_A:
				player.setDX(0);
				break;
			case KeyEvent.VK_D:
				player.setDX(0);
				break;
			case KeyEvent.VK_W:
				player.setDY(0);
				break;
			case KeyEvent.VK_S:
				player.setDY(0);
				break;
			}
		}

		@Override
		public void keyPressed(KeyEvent e) {
			int key = e.getKeyCode();
			if (!engaged()) {
				switch (key) {
				case KeyEvent.VK_E:
					/*
					 * Seemingly random values added/subtracted from the position
					 * are there for the purpose of proper placement of the rectangle
					 * that checks for interactions. 
					 * 
					 * Honestly I don't remember how I even decided on these values,
					 * but they work so yeah.
					 */
					if (player.getImageLocation().equals(MainPlayer.FACING_NORTH)) {
						interact(player.getX(), player.getY() - ViewPanel.PLAYER_Y + 20);
					} else if (player.getImageLocation().equals(MainPlayer.FACING_SOUTH)) {
						interact(player.getX(), player.getY() + ViewPanel.PLAYER_Y - 12);
					} else if (player.getImageLocation().equals(MainPlayer.FACING_EAST)) {
						interact(player.getX() + ViewPanel.PLAYER_X - 18, player.getY());
					} else if (player.getImageLocation().equals(MainPlayer.FACING_WEST)) {
						interact(player.getX() - ViewPanel.PLAYER_X + 18, player.getY());
					}
					break;

				case KeyEvent.VK_SHIFT:
					player.setSpriteSpeed(4);
					break;

				case KeyEvent.VK_A: //Moves player left
					player.setDX(-player.getSpriteSpeed());
					player.loadImage(MainPlayer.FACING_WEST);
					enemyEncounter();
					break;

				case KeyEvent.VK_D: //Moves player right
					player.setDX(player.getSpriteSpeed());
					player.loadImage(MainPlayer.FACING_EAST);
					enemyEncounter();
					break;

				case KeyEvent.VK_W: //Moves player up
					player.setDY(-player.getSpriteSpeed());
					player.loadImage(MainPlayer.FACING_NORTH);
					enemyEncounter();
					break;

				case KeyEvent.VK_S: //Moves player down
					player.setDY(player.getSpriteSpeed());
					player.loadImage(MainPlayer.FACING_SOUTH);
					enemyEncounter();
					break;
				}
			}

		}
	}
}
