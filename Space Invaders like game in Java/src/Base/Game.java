package Base;
import GUI.*;
import javax.swing.*;

import BoardPieces.*;

import java.awt.*;
import java.awt.event.*;

public class Game {
	private static GameController gameController;
	private static Player player;
public static GameFrame frame;
	
	
	public Game() {
		player = new Player();
		gameController = new GameController();
		frame = new GameFrame();
	}
	
	public void setGameController(GameController gc) { gameController = gc; }
	
	public static GameController getGameController() { return gameController; }
	
	public void setPlayer(Player p) { player = p; }
	
	public static Player getPlayer() { return player; }
	
	public void setGameFrame(GameFrame gf) { frame = gf; }
	
	public static GameFrame getGameFrame() { return frame; }
	
	public static void toMenu() {
		frame.toMenu();
		frame.pack();
		gameController.setRunning(false);
	}
	
	public static void startGame() {
		
		Alien alien1 = new Alien(40, 50);
		Alien alien2 = new Alien(100, 50);
		Alien alien3 = new Alien(160, 50);
		Alien alien4 = new Alien(220, 50);
		Alien alien5 = new Alien(280, 50);
		Alien alien6 = new Alien(340, 50);
		Alien alien7 = new Alien(400, 50);
		Alien alien8 = new Alien(460, 50);
		Alien alien21 = new Alien(40, 100);
		Alien alien22 = new Alien(100, 100);
		Alien alien23 = new Alien(160, 100);
		Alien alien24 = new Alien(220, 100);
		Alien alien25 = new Alien(280, 100);
		Alien alien26 = new Alien(340, 100);
		Alien alien27 = new Alien(400, 100);
		Alien alien28 = new Alien(460, 100);
		GameController.getAliens().add(alien1);
		GameController.getAliens().add(alien2);
		GameController.getAliens().add(alien3);
		GameController.getAliens().add(alien4);
		GameController.getAliens().add(alien5);
		GameController.getAliens().add(alien6);
		GameController.getAliens().add(alien7);
		GameController.getAliens().add(alien8);
		GameController.getAliens().add(alien21);
		GameController.getAliens().add(alien22);
		GameController.getAliens().add(alien23);
		GameController.getAliens().add(alien24);
		GameController.getAliens().add(alien25);
		GameController.getAliens().add(alien26);
		GameController.getAliens().add(alien27);
		GameController.getAliens().add(alien28);
		
		GameController.getSpaceship().setHealth(3);
		GameController.getSpaceship().setShield(false);
		GameController.getSpaceship().setHitBox(new Rectangle(345, 900, 
				GameController.getSpaceship().getWidth(),
				GameController.getSpaceship().getHeight()));
		
		frame.startGame();
		frame.pack();
		gameController.setRunning(true);
	}
	
	public static void toPlayers() {
		frame.toPlayers();
		frame.pack();
	}
	
	public static void endGame() {
		Player.newRecord();
		frame.endGame();
		frame.pack();
		gameController.setRunning(false);
		GameController.getAliens().removeAll(GameController.getAliens());
		GameController.getProjectiles().removeAll(GameController.getProjectiles());
		GameController.getOrbs().removeAll(GameController.getOrbs());
	}

	public static void main(String[] args) {
		Game game = new Game();
		/*
		Alien alien1 = new Alien(40, 50);
		Alien alien2 = new Alien(100, 50);
		Alien alien3 = new Alien(160, 50);
		Alien alien4 = new Alien(220, 50);
		Alien alien5 = new Alien(280, 50);
		Alien alien6 = new Alien(340, 50);
		Alien alien7 = new Alien(400, 50);
		Alien alien8 = new Alien(460, 50);
		Alien alien21 = new Alien(40, 100);
		Alien alien22 = new Alien(100, 100);
		Alien alien23 = new Alien(160, 100);
		Alien alien24 = new Alien(220, 100);
		Alien alien25 = new Alien(280, 100);
		Alien alien26 = new Alien(340, 100);
		Alien alien27 = new Alien(400, 100);
		Alien alien28 = new Alien(460, 100);
		GameController.getAliens().add(alien1);
		GameController.getAliens().add(alien2);
		GameController.getAliens().add(alien3);
		GameController.getAliens().add(alien4);
		GameController.getAliens().add(alien5);
		GameController.getAliens().add(alien6);
		GameController.getAliens().add(alien7);
		GameController.getAliens().add(alien8);
		GameController.getAliens().add(alien21);
		GameController.getAliens().add(alien22);
		GameController.getAliens().add(alien23);
		GameController.getAliens().add(alien24);
		GameController.getAliens().add(alien25);
		GameController.getAliens().add(alien26);
		GameController.getAliens().add(alien27);
		GameController.getAliens().add(alien28);
		*/
	}
	
}
