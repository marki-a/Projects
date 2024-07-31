package GUI;

import Base.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class PlayersButton extends JButton implements ActionListener {
	
	public PlayersButton() {
		this.setPreferredSize(new Dimension(87, 26));
		this.setAlignmentX(Component.CENTER_ALIGNMENT);
		this.setText("PLAYERS");
		this.addActionListener(this);
		this.setFocusable(false);
		this.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Game.toPlayers();
	}
	

}
