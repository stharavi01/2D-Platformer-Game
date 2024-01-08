package main;

import javax.swing.JFrame;

public class Game extends JFrame {
	
	private GameScreen gameScreen; //variable declaration of type GameScreen but not yet initialized

	public Game() {
		setSize(400,400);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		gameScreen = new GameScreen(); //Creates new instance of GameScreen object and assigns to gameScren variable
		add(gameScreen);
	}
	
	public static void main(String[] args) {
		System.out.println("Start of my platformer game");
		
		Game game = new Game();
	}

}
