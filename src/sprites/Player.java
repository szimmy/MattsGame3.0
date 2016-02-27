package sprites;

import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Random;

import characters.MainPlayer;
import gui.BattleGUI;
import gui.InventoryGUI;
import gui.LootGUI;
import gui.MenuPanel;
import gui.MessageGUI;
import gui.ViewPanel;
import main.GameController;

public class Player extends Sprite {
	
	private MainPlayer player;
	
	public Player(int x, int y, MainPlayer player) {
		super(x, y);
		this.player = player;
		initPlayer();
	}

    private void initPlayer() {
    	isObstacle = false;
        loadImage("Images\\Player\\PlayerSouth.png");
        getImageDimensions();
    }
 
    public MainPlayer getMainPlayer() {
    	return player;
    }
}
    


