package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.ToolTipManager;
import javax.swing.UIManager;
import javax.swing.plaf.ColorUIResource;
import javax.swing.plaf.FontUIResource;

import com.sun.prism.Image;

import characters.Enemy;
import gui.ImagePanel;
import gui.ViewPanel;
import javafx.embed.swing.JFXPanel;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class GameController extends JFrame {

	public static final Color BUTTON_COLOR_THEME = Color.LIGHT_GRAY;
	public static final Color LIGHTER_GRAY = new Color(-1118482);
	public static final Color DARKER_GRAY = new Color(160,160,160);
	public static final Color BUTTON_TEXT_COLOR = Color.BLACK;
	public static final Color BACKGROUND_COLOR_THEME = Color.GRAY;
	public static final Color MESSAGE_PANEL_BACKGROUND = Color.BLACK;
	public static final int TIMER_CONTROLLER = 40;
	public static double voiceVolume = .8;
	public static double effectVolume = .5;
	public static double musicVolume = .2;
	public static final Font GAME_FONT = new Font("Cambria", Font.PLAIN, 14);
	public static final Font GAME_FONT_SMALL = new Font("Cambria", Font.PLAIN, 12);
	private static Media sound;
	private static MediaPlayer soundPlayer;
	private static Random rand = new Random();
	private static GameController game;
	
	
	public static final String[] FEMALE_FIRST_NAMES = { "Mary", "Elizabeth", "Jennifer", "Maria", "Nancy", "Michelle", "Sarah", "Kim",
			"Amy", "Melissa", "Jessica", "Anna", "Kathleen", "Amanda", "Stephanie", "Diana", "Heather", "Gloria",
			"Cheryl", "Katherine", "Ashley", "Nicole", "Theresa", "Tammy", "Sarah", "Julia", "Grace", "Victoria",
			"Sophia", "Emma", "Olivia", "Isabella", "Emily", "Madison", "Aubrey", "Aria", "Kaylee", "Riley", "Chloe" };

	public static final String[] MALE_FIRST_NAMES = { "James", "John", "Robert", "Mike", "William", "David", "Boris", "Richard",
			"Matt", "Charles", "Joe", "Tom", "Chris", "Daniel", "Paul", "Mark", "Donald", "George", "Ken", "Steve",
			"Ed", "Frank", "Jason", "Gary", "Tim", "Greg", "Jerry", "Dennis", "Andrew", "Walter", "Patrick", "Peter",
			"Justin", "Doug", "Harold", "Albert", "Jack", "Terry", "Ralph", "Nick", "Sam", "Adam", "Randy", "Carlos", 
			"Ryan" };

	public static final String[] LAST_NAMES = { "Smith", "Johnson", "Williams", "Brown", "Jones", "Miller", "Davis", "Garcia",
			"Rodriguez", "Wilson", "Martinez", "Anderson", "Taylor", "Hernandez", "Moore", "Martin", "Lee", "Clark",
			"Lewis", "Robinson", "Walker", "Hall", "Allen", "King", "Baker", "Green", "Turner", "Campbell", "Murphy",
			"Parker", "Morris", "Collins", "Cook", "Rivera", "Rogers", "Reed", "Bell", "Stewart", "Bailey", "Wood",
			"Jenkins", "Nufrio", "Leong", "Gimbut", "Trump" };
	
	public static GameController getInstance() {
		if (game == null) {
			game = new GameController();
			game.getContentPane().setLayout(null);
			game.setVisible(true);
		}
		return game;
	}
	
    private GameController() {
    	initUIManager();
        initUI();
    }
    
    private void initUIManager() {
    	UIManager.put("ToolTip.font", new FontUIResource(GAME_FONT));
    	UIManager.put("ToolTip.background", new ColorUIResource(BUTTON_COLOR_THEME));
    	UIManager.put("ToolTip.border", BorderFactory.createLineBorder(new Color(0,0,0)));
    	
    	UIManager.put("TabbedPane.background", BUTTON_COLOR_THEME);
    	UIManager.put("TabbedPane.font", GAME_FONT);
    	UIManager.put("TabbedPane.contentAreaColor", BUTTON_COLOR_THEME);
    	UIManager.put("TabbedPane.foreground", BUTTON_TEXT_COLOR);
        UIManager.put("TabbedPane.borderHightlightColor", Color.BLACK); 
        UIManager.put("TabbedPane.light", Color.BLACK);
        UIManager.put("TabbedPane.selectHighlight", Color.BLACK);
        UIManager.put("TabbedPane.darkShadow", Color.BLACK);
        UIManager.put("TabbedPane.focus", Color.YELLOW); //Only the inner border of the selected tab
        UIManager.put("TabbedPane.selected",  Color.YELLOW); //The inner, actual background color of the tab
        UIManager.put("TabbedPane.borderHighlightColor", Color.BLACK);
        UIManager.put("TabbedPane.tabsOverlapBorder", false);
        
        UIManager.put("Button.select", GameController.DARKER_GRAY);
        UIManager.put("Button.highlight", Color.BLACK);
        
        UIManager.put("Label.foreground", Color.BLACK);
        UIManager.put("Label.font", GAME_FONT);
        
    	ToolTipManager.sharedInstance().setDismissDelay(15000);
    }
    
    private void initUI() {  
		try {
			BufferedImage myImage = ImageIO.read(new File("Images\\GrassBackgroundBIG.png"));
			this.setContentPane(new ImagePanel(myImage));
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.setIconImage(new ImageIcon("Images\\Player\\PlayerSouth.png").getImage());
        this.add(new ViewPanel(this));
        this.setResizable(false);
        this.pack();
        this.setTitle("Matt's Game 3.0");
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(new Dimension(ViewPanel.B_WIDTH, ViewPanel.B_HEIGHT)); 	
    }
    
	public static ArrayList<Enemy> getRandomEnemies(int maxNumEnemies) {
		ArrayList<Enemy> enemies = new ArrayList<Enemy>();
		Random r = new Random();
		for(int i = 0; i < r.nextInt(maxNumEnemies)+1; i++) { //+1 to guarantee at least 1 enemy
			if(r.nextInt(2) == 1) {
				enemies.add(new Enemy(MALE_FIRST_NAMES[r.nextInt(45)] + " " + LAST_NAMES[r.nextInt(45)]));
			} else {
				enemies.add(new Enemy(FEMALE_FIRST_NAMES[r.nextInt(39)] + " " + LAST_NAMES[r.nextInt(45)]));
			}
		}
		return enemies;
	}
	
	public static int getRandomSpeed() {
		return rand.nextInt(50);
	}

	public static void playSound(String fileLocation) {
		JFXPanel panel = new JFXPanel();
		sound = new Media(Paths.get(fileLocation).toUri().toString());
		soundPlayer = new MediaPlayer(sound);
		soundPlayer.setVolume(effectVolume);		
		soundPlayer.play();
	}
}