package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.ToolTipManager;
import javax.swing.UIManager;
import javax.swing.plaf.ColorUIResource;
import javax.swing.plaf.FontUIResource;

import characters.Enemy;
import gui.ImagePanel;
import gui.ViewPanel;
import javafx.embed.swing.JFXPanel;
import javafx.scene.layout.Border;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class GameController extends JFrame {

	public static final Color BUTTON_COLOR_THEME = Color.LIGHT_GRAY;
	public static final Color BACKGROUND_COLOR_THEME = Color.GRAY;
	public static final int TIMER_CONTROLLER = 40;
	public static double voiceVolume = .8;
	public static double effectVolume = .5;
	public static double musicVolume = .2;
	public static final Font GAME_FONT = new Font("Gentium Book Basic", Font.PLAIN, 14);
	
	private static Media sound;
	private static MediaPlayer soundPlayer;
	
	
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
	
    public GameController() {
    	UIManager.put("ToolTip.font", new FontUIResource(GAME_FONT));
    	UIManager.put("ToolTip.background", new ColorUIResource(BUTTON_COLOR_THEME));
    	javax.swing.border.Border border = BorderFactory.createLineBorder(new Color(0,0,0));
    	UIManager.put("ToolTip.border", border);
    	ToolTipManager.sharedInstance().setDismissDelay(15000);
        initUI();
    }
    
    private void initUI() {  
    	
		try {
			BufferedImage myImage = ImageIO.read(new File("Images\\GrassBackgroundBIG.png"));
			setContentPane(new ImagePanel(myImage));
		} catch (IOException e) {
			e.printStackTrace();
		}
    	
        this.add(new ViewPanel(this));
        
        setResizable(false);
        pack();
        
        setTitle("Collision");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(new Dimension(ViewPanel.B_WIDTH,ViewPanel.B_HEIGHT)); 	
    }
    
	public static ArrayList<Enemy> getRandomEnemies(int maxNumEnemies) {
		ArrayList<Enemy> enemies = new ArrayList<Enemy>();
		Random r = new Random();
		for(int i = 0; i < r.nextInt(maxNumEnemies)+1; i++) {
			if(r.nextInt(2) == 1) {
				enemies.add(new Enemy(MALE_FIRST_NAMES[r.nextInt(45)] + " " + LAST_NAMES[r.nextInt(45)]));
			}
			else {
				enemies.add(new Enemy(MALE_FIRST_NAMES[r.nextInt(45)] + " " + LAST_NAMES[r.nextInt(45)]));
			}
		}
		return enemies;
	}
	
	public static int getRandomSpeed() {
		Random rand = new Random();
		int random = rand.nextInt(2);
		
		//Definitely random
		//This totally makes sense
		if (random == 0) {
			return 5;
		}
		else {
			return 40;
		}
	}

	public static void playSound(String fileLocation) {
		JFXPanel panel = new JFXPanel();
		sound = new Media(Paths.get(fileLocation).toUri().toString());
		soundPlayer = new MediaPlayer(sound);
		soundPlayer.play();
		soundPlayer.setVolume(effectVolume);		
	}
}