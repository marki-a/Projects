package BoardPieces;

import Base.*;

import java.awt.*;
import java.util.*;
import javax.swing.*;

public class UpgradeOrb extends BoardPiece {
	private OrbType type;
	private Random rng;
	private static Image imageShield = new ImageIcon("graphics/upgrade_orb_shield.png").getImage();
	private static Image imageAtkspeed = new ImageIcon("graphics/upgrade_orb_attackspeed.png").getImage();
	
	public UpgradeOrb(int x, int y) {
		super(x, y);
		rng = new Random();
		if(rng.nextInt(3) < 2) { 
			type = OrbType.SHIELD;
			super.setWidth(imageShield.getWidth(null));
			super.setHeight(imageShield.getHeight(null));
		}
		else { 
			type = OrbType.ATK_SPEED;
			super.setWidth(imageAtkspeed.getWidth(null));
			super.setHeight(imageAtkspeed.getHeight(null));
		}
		GameController.getOrbs().add(this);
	}
	
	public Image getImage() {
		if(type == OrbType.SHIELD) {
			return imageShield;
		} else {
			return imageAtkspeed;
		}
	}
	
	@Override
	public void move() {
		setHitBox(new Rectangle((int)hitBox.getX(), (int)hitBox.getY() + 2, width, height));
		if(outOfBounds()) { setVisible(false); }
	}
	
	public void hit() {
		setVisible(false);
		switch(type) {
		case SHIELD: GameController.getSpaceship().setShield(true);
			break;
		case ATK_SPEED: 
			break;
		}
	}

}
