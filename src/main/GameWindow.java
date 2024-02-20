package main;

import javax.swing.JFrame;

public class GameWindow {
	private static final int Exit_On_Close = 0;
	private JFrame jframe;
	public GameWindow(GamePanel gamePanel) {
		jframe = new JFrame();
		jframe.setSize(400,400);
		jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jframe.add(gamePanel);
		jframe.setVisible(true);
	}
}
