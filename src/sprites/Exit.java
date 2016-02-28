package sprites;

public class Exit extends Sprite {

	private String nextMapLocation;
	private int nextMapX;
	private int nextMapY;
	
	public Exit(int x, int y, int nextMapX, int nextMapY) {
		super(x, y);
		this.nextMapX = nextMapX;
		this.nextMapY = nextMapY;
		initExit();
	}

	public Exit(int x, int y, String nextMapLocation) {
		super(x, y);
		this.nextMapLocation = nextMapLocation;
		initExit();
	}
	
    private void initExit() {
    	isObstacle = true;
        loadImage("Images\\Exit.png");
        getImageDimensions();
    }
	
	public String getNextMapLocation() {
		return nextMapLocation;
	}

	public void setNextMapLocation(String nextMapLocation) {
		this.nextMapLocation = nextMapLocation;
	}

	public int getNextMapX() {
		return nextMapX;
	}

	public void setNextMapX(int nextMapX) {
		this.nextMapX = nextMapX;
	}

	public int getNextMapY() {
		return nextMapY;
	}

	public void setNextMapY(int nextMapY) {
		this.nextMapY = nextMapY;
	}

}
