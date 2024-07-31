package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import Base.Player;

public class PlayerProfiles extends JPanel {
	private static final int PANEL_WIDTH = 420;
	private static final int PANEL_HEIGHT = 420;
	private static PlayerProfile p0;
	private static PlayerProfile p1;
	private static PlayerProfile p2;
	
	public class PlayerProfile extends JPanel {
		static final int PP_PANEL_WIDTH = 420;
		static final int PP_PANEL_HEIGHT = 140;
		JTextField name;
		String highScore;
		int score;
		
		PlayerProfile(int i) {
			name = new JTextField(20);
			name.setText(Player.getProfiles().get(i)[0]);
			name.setFont(new Font("Consolas", Font.BOLD, 20));
			name.setBackground(Color.darkGray);
			name.setForeground(Color.green);
			name.setAlignmentX(Component.LEFT_ALIGNMENT);
			highScore = "HIGHSCORE:";
			score = Integer.parseInt(Player.getProfiles().get(i)[1]);
			this.setBackground(Color.black);
			this.setPreferredSize(new Dimension(PP_PANEL_WIDTH, PP_PANEL_HEIGHT));
			this.setSize(getPreferredSize());
			this.setLayout(new BorderLayout());
			this.add(name, BorderLayout.WEST);
			this.setAlignmentX(Component.CENTER_ALIGNMENT);
		}
		
		public String[] setName(String[] temp) {
			temp[0] = name.getText();
			return temp;
		}
		
		@Override
		public void paint(Graphics g) {
			super.paint(g);
			Graphics2D g2D = (Graphics2D) g;
			g2D.setPaint(Color.green);
			g2D.setFont(new Font("Consolas", Font.BOLD, 20));
			//g2D.drawString(name, 20, 80);
			g2D.drawString(highScore, 250, 80);
			g2D.drawString(String.valueOf(score), 360, 80);
		}
	}
	
	public static PlayerProfile getProfile(int i) {
		switch(i) {
		case 0: return p0;
		case 1: return p1;
		default: return p2;
		}
	}
	
	public PlayerProfiles() {
		this.setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
		this.setSize(getPreferredSize());
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		p0 = new PlayerProfile(0);
		p1 = new PlayerProfile(1);
		p2 = new PlayerProfile(2);
		this.add(p0);
		this.add(p1);
		this.add(p2);
		/*
		for(int i = 0; i < 3; i++) {
			this.add(new PlayerProfile(i));
		}
		*/
	}
	
	public static void setName() {
		String[] temp0 = Player.getProfiles().get(0);
		String[] temp1 = Player.getProfiles().get(1);
		String[] temp2 = Player.getProfiles().get(2);
		Player.getProfiles().remove(temp0);
		Player.getProfiles().remove(temp1);
		Player.getProfiles().remove(temp2);
		p0.setName(temp0);
		p1.setName(temp1);
		p2.setName(temp2);
		Player.getProfiles().add(temp0);
		Player.getProfiles().add(temp1);
		Player.getProfiles().add(temp2);
	}

}
