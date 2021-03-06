package main;

import java.awt.Dimension;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;

import characters.Enemy;
import characters.Neutral;
import gui.ViewPanel;
import items.Item;
import sprites.DisplayItem;
import sprites.Exit;
import sprites.GenericObstacle;
import sprites.Lootable;
import sprites.NPC;
import sprites.OverLayer;
import sprites.Save;
import sprites.Sprite;
import sprites.UnderLayer;

/**
 * MapParser interface to read through each custom .map file to produce custom map.
 * @author Matthew
 *
 */
public class MapParser {
	
	private ViewPanel currentView;
	
	public MapParser(ViewPanel currentView) {
		this.currentView = currentView;
	}
	
	/**
	 * Only method is a public static method that takes two parameters to determine the map file.
	 * Different values read from the file are separated by a comma and loaded into a string array.
	 * info[0] is always the string containing the type of sprite.
	 * info[1] is the x coordinate of the sprite and info[2] is the y coordinate.
	 * Depending on the type of sprite, the length of the info array and values will change.
	 * @param x The x coordinate of the location on the map
	 * @param y The y coordinate of the location on the map
	 * @return An ArrayList<Sprite> that contains all of the sprite objects to be displayed on the map.
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public ArrayList<Sprite> parseMap(String mapLocation) throws FileNotFoundException, IOException {
		ArrayList<Sprite> objects = new ArrayList<Sprite>();
		try(BufferedReader br = new BufferedReader(new FileReader(mapLocation))) {
			String line = br.readLine();
			while (line != null) {
				String[] info = line.split(",");
				switch(info[0]) {
				case "tree":
					objects.add(new GenericObstacle(Integer.parseInt(info[1]), Integer.parseInt(info[2])));
					break;
					
				case "chest":
					objects.add(new Lootable(Integer.parseInt(info[1]), Integer.parseInt(info[2])));
					break;
					
				case "save":
					objects.add(new Save(Integer.parseInt(info[1]), Integer.parseInt(info[2])));
					break;
					
				case "customObst":
					//info[3] is the file location of the image
					objects.add(new GenericObstacle(Integer.parseInt(info[1]), Integer.parseInt(info[2]), info[3]));
					break;
					
				case "enemy":
					//info[3] is the name, info[4] is the level, info[5] is the health.
					//info[6] is the atk, info[7] is the def, info[8] is the spd.
					//info[9] is the image location, info[10] is the music location.
					//info[11]+ is any additional dialogue that the enemy has before fighting.
					String[] enemyMessage = new String[10];
					int i = 11;
					int enemyMessageCounter = 0;
					while(i < info.length) {
						enemyMessage[enemyMessageCounter] = info[i];
						i++;
						enemyMessageCounter++;
					}
					objects.add(new NPC(Integer.parseInt(info[1]), Integer.parseInt(info[2]),
							new Enemy(info[3], Integer.parseInt(info[4]), Integer.parseInt(info[5]),
									Integer.parseInt(info[6]), Integer.parseInt(info[7]), 
									Integer.parseInt(info[8]), info[9], info[10]), enemyMessage));
					break;
					
				case "neutral":
					//info[5]+ is any additional dialogue that the enemy has before fighting.
					String[] neutralMessage = new String[10];
					int j = 5;
					int neutralMessageCounter = 0;
					while(j < info.length) {
						neutralMessage[neutralMessageCounter] = info[j];
						j++;
						neutralMessageCounter++;
					}
					objects.add(new NPC(Integer.parseInt(info[1]), Integer.parseInt(info[2]), 
							new Neutral(info[3], info[4]), neutralMessage));
					break;
					
				case "item":
					objects.add(new DisplayItem(Integer.parseInt(info[1]), Integer.parseInt(info[2]), Item.generateRandomItem()));
					break;
				
				case "exit":
					//info[1] and info[2] are coordinates of exit placement on map.
					//info[3] and info [4] are the coordinates of where the player will be placed in the next area
					//info[5] is the file location of the next map cell
					objects.add(new Exit(Integer.parseInt(info[1]), Integer.parseInt(info[2]),
							Integer.parseInt(info[3]), Integer.parseInt(info[4]), info[5]));
					break;
					
				case "over":
					objects.add(new OverLayer(Integer.parseInt(info[1]), Integer.parseInt(info[2]), info[3]));
					break;
					
				case "under":
					objects.add(new UnderLayer(Integer.parseInt(info[1]), Integer.parseInt(info[2]), info[3]));
					break;
					
				case "background":
					//currentView.getController().updateBackground(info[1], currentView);
					//currentView.requestFocus();
					currentView.setBackground(info[1]);
					break;
					
				case "hostile":
					currentView.setHostile(true);
					break;
					
				case "nonhostile":
					currentView.setHostile(false);
					break;
					
				default: 
					System.out.println("Removed successfully.");
					break;
				}
				
				line = br.readLine();
			}
		}
		return objects;
	}
	
	public static void removeItem(String tag, String currentMapFile, int x, int y) throws FileNotFoundException, IOException {
		boolean found = false;
		int charCounter = 0;
		try(RandomAccessFile raf = new RandomAccessFile(new File(currentMapFile), "rw")) {
			String line = raf.readLine();
			while (line != null && !found) {
				String[] info = line.split(",");
				if(info[0].equals(tag) && Integer.parseInt(info[1]) == x && Integer.parseInt(info[2]) == y) {
					found = true;
					for(String string : info) {
						charCounter += string.length();
					}
					raf.seek(raf.getFilePointer()-(charCounter+info.length));
					raf.writeChars("r");
				} else {
					line = raf.readLine();
				}
			}
		}		
	}
	
	public static void addItem(String tag, int x, int y) {
		
	}
	
}
