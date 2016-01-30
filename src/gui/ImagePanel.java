package gui;

import java.awt.*;
import javax.swing.*;

public class ImagePanel extends JPanel {

	private Image image;
	
	public ImagePanel(Image image) {
		this.image = image;
	}
	
	public ImagePanel() {
		
	}

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image, 0, 0, this);
    }
	
}