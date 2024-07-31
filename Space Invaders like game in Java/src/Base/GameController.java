package Base;
import javax.swing.Timer;

import BoardPieces.*;
import GUI.*;

import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class GameController implements ActionListener {
	private boolean running;
	private int round;
	private boolean asteroids;
	private static Timer timer;
	private static ArrayList<Alien> aliens;
	private static ArrayList<Projectile> projectiles;
	private static ArrayList<UpgradeOrb> orbs;
	private static Spaceship spaceship;
	
	public void setRunning(boolean b) { running = b; }
	
	public boolean getRunning() { return running; }
	
	public void setRound(int i) { round = i; }
	
	public int getRound() { return round; }
	
	public void setAsteroids(boolean b) { asteroids = b; }
	
	public boolean getAsteroids() { return asteroids; }
	
	public void setTimer(Timer t) { timer = t; }
	
	public static Timer getTimer() { return timer; }
	
	public void setAliens(ArrayList<Alien> al) { aliens = al; }
	
	public static ArrayList<Alien> getAliens() { return aliens; }
	
	public void setProjectiles(ArrayList<Projectile> al) { projectiles = al; }
	
	public static ArrayList<Projectile> getProjectiles() { return projectiles; }
	
	public void setOrbs(ArrayList<UpgradeOrb> al) { orbs = al; }
	
	public static ArrayList<UpgradeOrb> getOrbs() { return orbs; }
	
	public void setSpaceship(Spaceship ss) { spaceship = ss; }
	
	public static Spaceship getSpaceship() { return spaceship; }
	
	public GameController() {
		running = false;
		round = 1;
		asteroids = false;
		timer = new Timer(20, new TimerActionListener());
		aliens = new ArrayList<Alien>();
		projectiles = new ArrayList<Projectile>();
		orbs = new ArrayList<UpgradeOrb>();
		spaceship = new Spaceship(345, 900);
	}
	
	public void nextRound() {
		round += 1;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == MenuPanel.getStartButton()) {
			timer.start();
		}
	}
	
	private class TimerActionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			for(int i = 0; i < aliens.size(); i++) {
				if(aliens.get(i).getVisible()) {
					aliens.get(i).move();
					aliens.get(i).fire();
				}
			} for(int i = 0; i < projectiles.size(); i++) {
				if(projectiles.get(i).getVisible()) { projectiles.get(i).move(); }
			} for(int i = 0; i < orbs.size(); i++) {
				if(orbs.get(i).getVisible()) { orbs.get(i).move(); }
			} 
			spaceship.move();
			for(Projectile projectile : projectiles) {
				for(Alien alien : aliens) {
					if(projectile.getVisible() && alien.getVisible() && alien.getHitBox().intersects(projectile.getHitBox())) {
						switch(projectile.getType()) {
						case PLAYER: 
							alien.hit();
							projectile.hit();
							break;
						default: break;
						}
					} if(projectile.getVisible() && spaceship.getHitBox().intersects(projectile.getHitBox())) {
						switch(projectile.getType()) {
						case GREEN: 
							spaceship.hit();
							projectile.hit();
							break;
						default: break;
						}
					}
				}
			} for(UpgradeOrb orb : orbs) {
				if(orb.getVisible() && orb.getHitBox().intersects(spaceship.getHitBox())) { orb.hit(); }
			}
			int aliensAlive = aliens.size();
			for(Alien alien : aliens) {
				if(!alien.getVisible()) { aliensAlive -= 1; }
			} if(aliensAlive == 0) { Game.endGame(); }
			GameFrame.getGamePanel().revalidate();
			GameFrame.getGamePanel().repaint();
		}
	}

}
