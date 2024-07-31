package BoardPieces;

import Base.*;
import GUI.GameFrame;

import java.awt.*;
import javax.swing.*;

public class BoardPiece {
	protected int width;
	protected int height;
	protected Rectangle hitBox;
	protected Direction direction;
	protected boolean visible;
	
	public void setWidth(int i) { width = i; }
	
	public int getWidth() { return width; }
	
	public void setHeight(int i) { height = i; }
	
	public int getHeight() { return height; }
	
	public void setHitBox(Rectangle r) { hitBox = r; }
	
	public Rectangle getHitBox() { return hitBox; }
	
	public void setDirection(Direction d) { direction = d; }
	
	public void setVisible(boolean b) { visible = b; }
	
	public boolean getVisible() { return visible; }
	
	public BoardPiece(int x, int y) {
		hitBox = new Rectangle(x, y, width, height);
		direction = Direction.RIGHT;
		visible = true;
	}
	
	public boolean outOfBounds() {
		if((int)hitBox.getX() + width >= GameFrame.getGamePanel().getWidth()) {
			return true;
		} else if((int)hitBox.getX() < 0) {
			return true;
		} else if((int)hitBox.getY() + height >= GameFrame.getGamePanel().getHeight()) {
			return true;
		} else if((int)hitBox.getY() < 0) {
			return true;
		} else {
			return false;
		}
	}
	
	public void move() {
		switch(direction) {
		case LEFT: setHitBox(new Rectangle((int)hitBox.getX() - 6, (int)hitBox.getY(), width, height));
			break;
		case UP: setHitBox(new Rectangle((int)hitBox.getX(), (int)hitBox.getY() - 6, width, height));
			break;
		case RIGHT: setHitBox(new Rectangle((int)hitBox.getX() + 6, (int)hitBox.getY(), width, height));
			break;
		case DOWN: setHitBox(new Rectangle((int)hitBox.getX(), (int)hitBox.getY() + 6, width, height));
			break;
		default: break;
		}
		if(outOfBounds()) {
			switch(direction) {
			case LEFT: setDirection(Direction.RIGHT);
				break;
			case UP: setDirection(Direction.DOWN);
				break;
			case RIGHT: setDirection(Direction.LEFT);
				break;
			case DOWN: setDirection(Direction.UP);
				break;
			}
		}
	}
	
}
