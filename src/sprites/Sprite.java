package sprites;

import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import javax.swing.ImageIcon;

public abstract class Sprite implements Serializable {

    protected int dx;
    protected int dy;
    protected int x;
    protected int y;
    protected int width;
    protected int height;
    protected boolean visible;
    protected transient Image image; //Image is not serializable so  has to be transient
    protected String imageLocation; //The image location is saved as well so we can reload the image on load
    protected boolean isObstacle;
    private ImageIcon i;
    protected String[] message;
    
    public Sprite(int x, int y) {
        this.x = x;
        this.y = y;
        visible = true;
    }

    
    public void getImageDimensions() {
        width = image.getWidth(null);
        height = image.getHeight(null);
    }

    public void loadImage(String imageName) {
    	this.imageLocation = imageName;
        this.i = new ImageIcon(imageName);
        this.image = i.getImage();
    }

    public Image getImage() {
        return image;
    }
    
    
   public void setDX(int dx) {
	   this.dx = dx;
   }
   
   public void setDY(int dy) {
	   this.dy = dy;
   }
   
   public int getDX() {
	   return dx;
   }
   
   public int getDY() {
	   return dy;
   }
   
   public void modifyX(int dx) {
	   x += dx;
   }
   
   public void modifyY(int dy) {
	   y += dy;
   }
   
   public void setX(int x) {
	   this.x = x;
   }
   
   public void setY(int y) {
	   this.y = y;
   }    

    public String getImageLocation() {
    	return imageLocation;
    }
    
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
    
    public void refreshImage() {
    	 image = i.getImage();
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }
    
    public boolean isObstacle() {
    	return isObstacle;
    }
    
    public void setObstacle(boolean isObstacle) {
    	this.isObstacle = isObstacle;
    }

	public String[] getMessage() {
		return message;
	}

	public void setMessage(String[] message) {
		this.message = message;
	}
}