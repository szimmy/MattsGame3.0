package sprites;

import java.util.LinkedList;

import items.Item;


public class Lootable extends Sprite {

	private LinkedList<Item> items;
	
	
	public Lootable(int x, int y) {
		super(x, y);
		isObstacle = true;
		loadImage("Images\\ChestArea.png");
		getImageDimensions();
		this.items = new LinkedList<Item>();
		items = Item.generateRandomItem(3);
	}
	
	//TODO: Make constructor for different images
	
	public Lootable(int x, int y, LinkedList<Item> items) {
		super(x, y);
		loadImage("Images\\ChestArea.png");
		getImageDimensions();
		this.items = items;
	}
	
	/**
	 * Gets the items in the chest
	 * @return A LinkedList<Item> containing the items in the chest
	 */
	public LinkedList<Item> getItems() {
		return items;
	}	
	
	/**
	 * Removes all items from a chest
	 */
	public void removeAllItems() {
		this.items.clear();
	}

}
