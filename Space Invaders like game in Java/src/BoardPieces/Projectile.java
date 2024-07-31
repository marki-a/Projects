package BoardPieces;

import Base.*;

import java.awt.*;
import javax.swing.*;

public class Projectile extends BoardPiece {
	private ProjectileType type;
	private static Image imagePlayer = new ImageIcon("graphics/spaceship1_projectile.png").getImage();
	private static Image imageGreen = new ImageIcon("graphics/alien_green_projectile.png").getImage();
	
	public ProjectileType getType() { return type; }
	
	public static Image getImagePlayer() { return imagePlayer; }
	
	public static Image getImageGreen() { return imageGreen; }
	
	public Image getImage() {
		switch(type) {
		case PLAYER: return imagePlayer;
		case GREEN: return imageGreen;
		default: return null;
		}
	}

	public Rectangle getHitBox() { return super.getHitBox(); }
	
	public Projectile(int x, int y, ProjectileType t) {
		super(x, y);
		super.setDirection(Direction.UP);
		type = t;
		switch(type) {
		case PLAYER:
			super.setWidth(imagePlayer.getWidth(null));
			super.setHeight(imagePlayer.getHeight(null));
			break;
		case GREEN:
			super.setWidth(imageGreen.getWidth(null));
			super.setHeight(imageGreen.getHeight(null));
			break;
		}
	}
	
	@Override
	public void move() {
		switch(type) {
		case PLAYER:
			setHitBox(new Rectangle((int)hitBox.getX(), (int)hitBox.getY() - 20, width, height));
			if(outOfBounds()) { GameController.getProjectiles().remove(this); }
			break;
		case GREEN:
			setHitBox(new Rectangle((int)hitBox.getX(), (int)hitBox.getY() + 20, width, height));
			if(outOfBounds()) { GameController.getProjectiles().remove(this); }
			break;
		}
	}
	
	public void hit() {
		//GameController.getProjectiles().remove(this);
		setVisible(false);
	}
	
}
