package GUI;

import Base.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class StartButton extends JButton implements ActionListener {
	
	public StartButton() {
		this.setPreferredSize(new Dimension(108, 26));
		this.setAlignmentX(Component.CENTER_ALIGNMENT);
		this.setText("START GAME");
		this.addActionListener(this);
		this.setFocusable(false);
		this.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Game.startGame();
	}
	

}
