package main;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import characters.Enemy;
import characters.Neutral;
import sprites.GenericObstacle;
import sprites.Lootable;
import sprites.NPC;
import sprites.Save;
import sprites.Sprite;

/**
 * MapParser interface to read through each custom .map file to produce custom map.
 * @author Matthew
 *
 */
public interface MapParser {

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
	public static ArrayList<Sprite> parseMap(int x, int y) throws FileNotFoundException, IOException {
		ArrayList<Sprite> objects = new ArrayList<Sprite>();
		String fileLocation = "Maps\\Map" + x + "-" + y + ".map";
		try(BufferedReader br = new BufferedReader(new FileReader(fileLocation))) {
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
				case "enemy":
					objects.add(new NPC(Integer.parseInt(info[1]), Integer.parseInt(info[2]),
							new Enemy(info[3], Integer.parseInt(info[4]), Integer.parseInt(info[5]),
									Integer.parseInt(info[6]), Integer.parseInt(info[7]), 
									Integer.parseInt(info[8]), info[9], info[10])));
					break;
				case "neutral":
					objects.add(new NPC(Integer.parseInt(info[1]), Integer.parseInt(info[2]), new Neutral(info[3], info[4])));
					break;
				}
				
				line = br.readLine();
			}
		}
		return objects;
	}
	
}
