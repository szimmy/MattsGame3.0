package main;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import sprites.Sprite;

public interface SaveManager {

	public final static boolean DO_NOT_APPEND = false;
	
	public static void serialize(ArrayList<Sprite> currentMap) {
		try(FileOutputStream fs = new FileOutputStream("Saves\\save01.ser", DO_NOT_APPEND);
				ObjectOutputStream os = new ObjectOutputStream(fs)) {
			os.writeObject(currentMap);
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static ArrayList<Sprite> deserialize() {
		try(FileInputStream fs = new FileInputStream("Saves\\save01.ser");
				ObjectInputStream os = new ObjectInputStream(fs)) {
			return (ArrayList<Sprite>) os.readObject();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		//If the deserialize fails, return null
		return null;
		}
	
}
