package GUI;

import java.awt.*;
import javax.swing.*;

public class MenuPanel extends JPanel {
	private static final int PANEL_WIDTH = 300;
	private static final int PANEL_HEIGHT =  500;
	private static Image background;
	private JPanel btnHolder;
	private static StartButton startBtn;
	public static PlayersButton playersBtn;

	public MenuPanel(){
		//background = new ImageIcon("space.png").getImage();
		this.setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
		this.setBackground(Color.black);
		btnHolder = new JPanel();
		btnHolder.setLayout(new BoxLayout(btnHolder, BoxLayout.Y_AXIS));
		startBtn = new StartButton();
		playersBtn = new PlayersButton();
		btnHolder.add(startBtn);
		btnHolder.add(playersBtn);
		btnHolder.setBackground(Color.black);
		this.add(btnHolder);
		this.setVisible(true);
	}
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		Graphics2D g2D = (Graphics2D) g;
		g2D.setBackground(Color.black);
	}

	public static StartButton getStartButton() { return startBtn; }

}
