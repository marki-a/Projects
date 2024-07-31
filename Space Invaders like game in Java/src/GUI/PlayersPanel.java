package GUI;

import java.awt.*;
import javax.swing.*;

public class PlayersPanel extends JPanel {
	private static final int PANEL_WIDTH = 500;
	private static final int PANEL_HEIGHT = 500;
	private Image background;
	private PlayerProfiles profiles;
	private static MenuButton menuBtn;
	
	public PlayersPanel(){
		//background = new ImageIcon("space.png").getImage();
		this.setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
		this.setLayout(new BorderLayout());
		this.setBackground(Color.black);
		profiles = new PlayerProfiles();
		menuBtn = new MenuButton();
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.add(profiles, BorderLayout.CENTER);
		this.add(menuBtn, BorderLayout.SOUTH);
		this.setVisible(true);
	}
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		Graphics2D g2D = (Graphics2D) g;
	}

}
