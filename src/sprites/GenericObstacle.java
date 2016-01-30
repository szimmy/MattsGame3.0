package sprites;

public class GenericObstacle extends Sprite {

	public GenericObstacle(int x, int y) {
		super(x, y);
		isObstacle = true;
		loadImage("Images\\TreeArea.png");
		getImageDimensions();
	}
	
	public GenericObstacle(int x, int y, String imageLocation) {
		super(x, y);
		isObstacle = true;
		loadImage(imageLocation);
		getImageDimensions();
	}

}
