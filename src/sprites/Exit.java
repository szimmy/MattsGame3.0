package sprites;

public class Exit extends Sprite {

	private String nextMapLocation;
	private int nextX;
	private int nextY;
	private int nextMapX;
	private int nextMapY;
	
	@Deprecated
	public Exit(int x, int y, int nextX, int nextY, int nextMapX, int nextMapY) {
		super(x, y);
		this.nextX = nextX;
		this.nextY = nextY;
		this.nextMapX = nextMapX;
		this.nextMapY = nextMapY;
		initExit();
	}

	public Exit(int x, int y, int nextX, int nextY, String nextMapLocation) {
		super(x, y);
		this.nextX = nextX;
		this.nextY = nextY;
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

	public int getNextX() {
		return nextX;
	}

	public void setNextX(int nextX) {
		this.nextX = nextX;
	}

	public int getNextY() {
		return nextY;
	}

	public void setNextY(int nextY) {
		this.nextY = nextY;
	}

}
