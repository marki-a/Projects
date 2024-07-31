package GUI;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import Base.Game;
import Base.GameController;

public class GamePanel extends JPanel {
	private static final int PANEL_WIDTH = 750;
	private static final int PANEL_HEIGHT =  1000;
	public static Image background = new ImageIcon("graphics/space.png").getImage();

	public GamePanel(){
		this.setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
		this.setVisible(true);
	}
	
	@Override
	public void paint(Graphics g) {
		Graphics2D g2D = (Graphics2D) g;
		g2D.drawImage(background, 0, 0, null);
		g2D.drawImage(GameController.getSpaceship().getImage(), 
				(int)GameController.getSpaceship().getHitBox().getX(), 
				(int)GameController.getSpaceship().getHitBox().getY(), null);
		for(int i = 0; i < GameController.getAliens().size(); i++) {
			if(GameController.getAliens().get(i).getVisible()) {
				g2D.drawImage(GameController.getAliens().get(i).getImage(), 
						(int)GameController.getAliens().get(i).getHitBox().getX(), 
						(int)GameController.getAliens().get(i).getHitBox().getY(), null);
			}
		} for(int i = 0; i < GameController.getProjectiles().size(); i++) {
			if(GameController.getProjectiles().get(i).getVisible()) {
				g2D.drawImage(GameController.getProjectiles().get(i).getImage(), 
						(int)GameController.getProjectiles().get(i).getHitBox().getX(), 
						(int)GameController.getProjectiles().get(i).getHitBox().getY(), null);
			}
		} for(int i = 0; i < GameController.getOrbs().size(); i++) {
			if(GameController.getOrbs().get(i).getVisible()) {
				g2D.drawImage(GameController.getOrbs().get(i).getImage(), 
						(int)GameController.getOrbs().get(i).getHitBox().getX(), 
						(int)GameController.getOrbs().get(i).getHitBox().getY(), null);
			}
		}
	}

}
