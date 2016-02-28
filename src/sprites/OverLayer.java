package sprites;

public class OverLayer extends Sprite {

	public OverLayer(int x, int y, String image) {
		super(x, y);
		isObstacle = false;
		loadImage(image);
		getImageDimensions();
	}

}
