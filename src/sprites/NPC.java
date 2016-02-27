package sprites;

import characters.MainPlayer;

public class NPC extends Sprite {
	
	private characters.Character chara;

	public NPC(int x, int y, characters.Character chara) {
		super(x, y);
		this.chara = chara;
		initNPC();
	}

    private void initNPC() {
    	isObstacle = true;
        loadImage(chara.getImage());
        getImageDimensions();
    }
 
    public characters.Character getNPC() {
    	return chara;
    }
  
}
