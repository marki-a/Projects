package BoardPieces;

import java.awt.*;
import java.awt.event.*;
import javax.swing.ImageIcon;

import Base.*;
import GUI.*;

public class Spaceship extends BoardPiece {
	private boolean shield;
	private int health;
	private double attackSpeed;
	private static Image image = new ImageIcon("graphics/spaceship1.png").getImage();
	public static Image imageShield = new ImageIcon("graphics/spaceship1_shield.png").getImage();
	
	public Spaceship(int x, int y) {
		super(x, y);
		health = 3;
		shield = false;
		super.setWidth(image.getWidth(null));
		super.setHeight(image.getHeight(null));
		super.setDirection(Direction.NULL);
	}
	
	public Image getImage() {
		if(shield) {
			return imageShield;
		} else {
			return image;
		}
	}
	
	public void setHealth(int i) { health = i; }
	
	public int getHealth() { return health; }
	
	public void setShield(boolean b) { shield = b; }
	
	public boolean getShield() { return shield; }
	
	public void setAttackSpeed(double d) { attackSpeed = d; }
	
	public double getAttackSpeed() { return attackSpeed; }
	
	public void setHitBox(Rectangle r) { super.setHitBox(r); }
	
	public Rectangle getHitBox() { return super.getHitBox(); }
	
	public boolean outOfBoundsLeft() {
		if((int)hitBox.getX() <= 0) {
			return true;
		} else {
			return false;
		}
	}
	
	public boolean outOfBoundsUp() {
		if((int)hitBox.getY() <= 0) {
			return true;
		} else {
			return false;
		}
	}
	
	public boolean outOfBoundsRight() {
		if((int)hitBox.getX() + width >= GameFrame.getGamePanel().getWidth()) {
			return true;
		} else {
			return false;
		}
	}
	
	public boolean outOfBoundsDown() {
		if((int)hitBox.getY() + height >= GameFrame.getGamePanel().getHeight()) {
			return true;
		} else {
			return false;
		}
	}
	
	@Override
	public void move() {
		switch(direction) {
		case LEFT: 
			if(!outOfBoundsLeft()) { setHitBox(new Rectangle((int)hitBox.getX() - 10, 
					(int)hitBox.getY(), width, height)); }
			break;
		case UP:
			if(!outOfBoundsUp()) { setHitBox(new Rectangle((int)hitBox.getX(), 
					(int)hitBox.getY() - 10, width, height)); }
			break;
		case RIGHT:
			if(!outOfBoundsRight()) { setHitBox(new Rectangle((int)hitBox.getX() + 10, 
					(int)hitBox.getY(), width, height)); }
			break;
		case DOWN:
			if(!outOfBoundsDown()) { setHitBox(new Rectangle((int)hitBox.getX(), 
					(int)hitBox.getY() + 10, width, height)); }
			break;
		default: break;
		}
	}
	
	public void fire() {
		GameController.getProjectiles().add(new Projectile(
				(int)hitBox.getX() + (image.getWidth(null) - Projectile.getImagePlayer().getWidth(null)) / 2, 
				(int)hitBox.getY() - Projectile.getImagePlayer().getHeight(null), 
				ProjectileType.PLAYER));
	}
	
	public void hit() {
		if(shield) { shield = false; }
		else {
			health -= 1;
			if(health == 0) { Game.endGame(); }
		}
	}
	
}
