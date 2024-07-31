package GUI;

import java.awt.BorderLayout;
import java.awt.Point;
import java.awt.event.*;
import javax.swing.*;
import Base.*;


public class GameFrame extends JFrame implements KeyListener {
public static MenuPanel menuPanel;
	private static GamePanel gamePanel;
public static PlayersPanel playersPanel;
	
	public static GamePanel getGamePanel() { return gamePanel; }

	public GameFrame() {
		menuPanel = new MenuPanel();
		gamePanel = new GamePanel();
		playersPanel = new PlayersPanel();
		//this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                Player.save();
            	System.exit(0);
            }
        });
		//this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.add(playersPanel);
		this.addKeyListener(this);
		this.setIconImage(new ImageIcon("graphics/alien_green.png").getImage());
		this.setVisible(true);
		this.pack();
	}
	
	public void startGame() {
		this.requestFocus();
		this.remove(menuPanel);
		this.add(gamePanel);
		GameController.getTimer().start();
		this.revalidate();
		this.repaint();
	}
	
	public void toMenu() {
		this.remove(playersPanel);
		this.add(menuPanel);
		this.revalidate();
		this.repaint();
	}
	
	public void toPlayers() {
		this.remove(menuPanel);
		this.add(playersPanel);
		this.revalidate();
		this.repaint();
	}
	
	public void endGame() {
		this.remove(gamePanel);
		this.add(menuPanel);
		GameController.getTimer().stop();
		this.revalidate();
		this.repaint();
	}

	@Override
	public void keyTyped(KeyEvent e) {
		
	}

	@Override
	public void keyPressed(KeyEvent e) {	
		switch(e.getKeyCode()) {
		case 37: GameController.getSpaceship().setDirection(Direction.LEFT);
			break;
		case 38: GameController.getSpaceship().setDirection(Direction.UP);
			break;
		case 39: GameController.getSpaceship().setDirection(Direction.RIGHT);
			break;
		case 40: GameController.getSpaceship().setDirection(Direction.DOWN);
			break;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		switch(e.getKeyCode()) {
		case 37: GameController.getSpaceship().setDirection(Direction.NULL);
			break;
		case 38: GameController.getSpaceship().setDirection(Direction.NULL);
			break;
		case 39: GameController.getSpaceship().setDirection(Direction.NULL);
			break;
		case 40: GameController.getSpaceship().setDirection(Direction.NULL);
			break;
		}
		switch(e.getKeyChar()) {
		case ' ': GameController.getSpaceship().fire();
			break;
		}
	}
}
