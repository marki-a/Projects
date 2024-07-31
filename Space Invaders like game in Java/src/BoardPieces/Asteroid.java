package BoardPieces;

import java.awt.*;
import javax.swing.*;

public class Asteroid extends BoardPiece {
	private int size;
	private Image imageSmall;
	private Image imageMedium;
	private Image imageLarge;
	
	public Asteroid(int x, int y) { //random?
		super(x, y);
		imageSmall = new ImageIcon("graphics/asteroid_small.png").getImage();
		imageMedium = new ImageIcon("graphics/asteroid_medium.png").getImage();
		imageLarge = new ImageIcon("graphics/asteroid_large.png").getImage();
	}
	
	public void setSize(int i) { size = i; }
	
	public int getSize() { return size; }

}
