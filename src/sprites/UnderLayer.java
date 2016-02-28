package sprites;

public class UnderLayer extends Sprite {

	public UnderLayer(int x, int y, String image) {
		super(x, y);
		isObstacle = false;
		loadImage(image);
		getImageDimensions();
	}

}
