package GUI;

import Base.*;
import GUI.PlayerProfiles.PlayerProfile;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class MenuButton extends JButton implements ActionListener {
	
	public MenuButton() {
		this.setEnabled(true);
		this.setPreferredSize(new Dimension(70, 26));
		this.setAlignmentX(Component.CENTER_ALIGNMENT);
		this.setText("MENU");
		this.addActionListener(this);
		this.setFocusable(false);
		this.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		PlayerProfiles.setName();
		Player.save();
		Game.toMenu();
	}

}
