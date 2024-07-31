package BoardPieces;

import java.awt.*;
import javax.swing.*;
import java.util.*;

import Base.*;
import GUI.*;

public class Alien extends BoardPiece {
	private Image image;
	private int health;
	private Random rng;
	
	public Alien(int x, int y) {
		super(x, y);
		image = new ImageIcon("graphics/alien_green.png").getImage();
		super.setWidth(image.getWidth(null));
		super.setHeight(image.getHeight(null));
		health = 2;
		rng = new Random();
	}
	
	public Image getImage() { return image; }
	
	public int getHealth() { return health; }

	public Rectangle getHitBox() { return super.getHitBox(); }
	
	@Override
	public void move() {
		switch(direction) {
		case LEFT: setHitBox(new Rectangle((int)hitBox.getX() + 6, (int)hitBox.getY(), width, height));
			break;
		case UP: setHitBox(new Rectangle((int)hitBox.getX() - 6, (int)hitBox.getY() - 4, width, height));
			break;
		case RIGHT: setHitBox(new Rectangle((int)hitBox.getX() + 6, (int)hitBox.getY(), width, height));
			break;
		case DOWN: setHitBox(new Rectangle((int)hitBox.getX() - 6, (int)hitBox.getY() + 4, width, height));
			break;
		default: break;
		}
		if(outOfBounds()) {
			switch(direction) {
			case LEFT: setDirection(Direction.UP);
				break;
			case UP: setDirection(Direction.RIGHT);
				break;
			case RIGHT: setDirection(Direction.DOWN);
				break;
			case DOWN: setDirection(Direction.LEFT);
				break;
			default: break;
			}
		}
	}
	
	public void hit() {
		health -= 1;
		if(health == 0) { 
			Player.increaseScore(10);
			setVisible(false);
			if(rng.nextInt(10) % 10 == 0) {
				GameController.getOrbs().add(new UpgradeOrb((int)hitBox.getX(),
					(int)hitBox.getY()));
			}
		}
	}
	
	public void fire() {
		int r = rng.nextInt(500);
		if(r == 0) {
			GameController.getProjectiles().add(new Projectile(
				(int)hitBox.getX() + (image.getWidth(null) - Projectile.getImageGreen().getWidth(null)) / 2, 
				(int)hitBox.getY() - Projectile.getImageGreen().getHeight(null), 
				ProjectileType.GREEN));
		}
	}

}
