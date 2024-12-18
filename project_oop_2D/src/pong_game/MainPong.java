package pong_game;

import javax.swing.*;

public class MainPong extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private GamePanel gamePanel;

    public MainPong() {
        setTitle("Pong Game");
        setSize(800, 600);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gamePanel = new GamePanel();
        add(gamePanel);
        setVisible(true);
    }

    public static void main(String[] args) {
        new MainPong();
    }

}