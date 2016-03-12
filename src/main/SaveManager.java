package main;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import sprites.Player;
import sprites.Sprite;

public interface SaveManager {

	public final static boolean DO_NOT_APPEND = false;
	
	public static void serialize(ArrayList<Sprite> currentMap, Player currentPlayer) {
		try(FileOutputStream fs = new FileOutputStream("Saves\\save01.ser", DO_NOT_APPEND);
				ObjectOutputStream os = new ObjectOutputStream(fs)) {
				ArrayList<Sprite> items = new ArrayList<Sprite>();
				items.add(0, currentPlayer);
				items.addAll(currentMap); 
			os.writeObject(items);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("unchecked")
	public static ArrayList<Sprite> deserialize() {
		try(FileInputStream fs = new FileInputStream("Saves\\save01.ser");
				ObjectInputStream os = new ObjectInputStream(fs)) {
				ArrayList<Sprite> map = (ArrayList<Sprite>) os.readObject();
				map.forEach(sprite -> sprite.loadImage(sprite.getImageLocation()));
			return map;
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return null;
	}
	
}
