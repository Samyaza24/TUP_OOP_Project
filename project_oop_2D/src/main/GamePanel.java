package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JFrame;
import javax.swing.JPanel;

import entity.Player;
import object.SuperObject;
import tile.TileManager;

import pong_game.MainPong;

public class GamePanel extends JPanel implements Runnable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// Screen settings
    final int originalTileSize = 16; // 16 x 16 tile
    final int scale = 3;
    
    public final int tileSize = originalTileSize * scale; // 48 x 48 tile
    public final int maxScreenCol = 16;
    public final int maxScreenRow = 12;
    public final int screenWidth = tileSize * maxScreenCol; // 768 pixels
    public final int screenHeight = tileSize * maxScreenRow; // 576 pixels

    public final int maxWorldCol = 50;
    public final int maxWorldRow = 50;

    public boolean gamePaused = false;
    
    boolean isPongRunning = false;
    
    // FPS
    int FPS = 60;

    // Game state variables to save the state before switching to PONG
    private int savedPlayerX, savedPlayerY;
    
    // SYSTEM
    TileManager tileM = new TileManager(this);
    KeyHandler keyH = new KeyHandler(this);
    
    Sound sound = new Sound();
    Sound music = new Sound();
    Sound se = new Sound();
   
    public CollisionChecker cChecker = new CollisionChecker(this);
    public AssetSetter aSetter = new AssetSetter(this);
    public UI ui = new UI(this);
    Thread gameThread;
    
    // ENTITY AND OBJECT
    public Player player = new Player(this, keyH);
    // CAN ONLY STORE 10 OBJECTS INSIDE YOUR PLAYER INVENT0RY
    public SuperObject obj[] = new SuperObject[10];
    
    
    
    // PLAYER'S DEFAULT POSITION
    int playerX = 100;
    int playY = 100;
    int playerSpeed = 4;

    // Constructor
    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);
    }
    
    public void gamePause() {
    	gamePaused = true;
    }
    
	public void setupGame() {
		aSetter.setObject();
		
		playMusic(0);	
		
	}
	
    public void startGameThread() {

    	if (gameThread == null || !gameThread.isAlive()) {
            gameThread = new Thread(this);
            setupGame();
            gameThread.start();
        }
        
    }

    @Override
    public void run() {
        // Game loop
        double drawInterval = 1000000000 / FPS; // 0.016666 secs
        double delta = 0;
        double lastTime = System.nanoTime();
        double currentTime;
        long timer = 0;
        int drawCount = 0;

        while (gameThread != null) {
            currentTime = System.nanoTime();
            timer += (currentTime - lastTime);
            delta += (currentTime - lastTime) / drawInterval;
            lastTime = currentTime;

            if (delta >= 1) {
            	
                // Update player and other game entities
            	if(!gamePaused) {
                	update();
            	}
                // Repaint the screen with updated information
                repaint();
                delta--;
                drawCount++;
            }

            // FPS counter
            if (timer >= 1000000000) {
                System.out.println("FPS: " + drawCount);
                drawCount = 0;
                timer = 0;
            }
        }
    }

    public void update() {
        // Update player movement
        player.update();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;

        //DRAW TILES
        tileM.draw(g2);
        
        // OBJECT
        for(int i = 0; i < obj.length;i++) {
        	if(obj[i] != null) {
        		obj[i].draw(g2, this);
        	}
        }
        // Draw the player
        player.draw(g2);
        
        // UI
        ui.draw(g2);
        
        g2.dispose();
    }
    	
    // Restore the saved game state after exiting PONG
    private void restoreGameState() {
    	 gamePaused = false; // Unpause the game
    	 player.setX(savedPlayerX);
    	 player.setY(savedPlayerY);
    	    
    	 keyH.resetKeys();
    	    
    	 if (gameThread == null || !gameThread.isAlive()) {
    		 startGameThread();
    	 }
    }
    
    public void startPongGame() {

    	 if (isPongRunning) {
    	        return;
    	    }

    	    isPongRunning = true;


    	    MainPong pongGame = new MainPong();

    	    // CHECK IF THE PONG GAME IS CLOSED
    	    pongGame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    	    pongGame.setVisible(true);

    	    gamePause(); // Pause the game while Pong is running

    	    pongGame.addWindowListener(new java.awt.event.WindowAdapter() {
    	        @Override
    	        public void windowClosed(java.awt.event.WindowEvent windowEvent) {
    	            isPongRunning = false;
    	            restoreGameState(); // Restore the game state after Pong is closed

    	            // Resuming the game
    	            gamePaused = false; // Ensure the game is no longer paused
    	            startGameThread();  // Restart the game thread
    	            requestFocusInWindow();  // Ensure the game window has focus for input
    	        }
    	    });
    }
    
    public void playMusic(int i) {
    	
    	music.setFile(i);
    	music.play();
    	music.loop();
    }
    
    public void stopMusic() {
    	
    	music.stop();
    }
    
    public void playSE(int i) {
    	
    	se.setFile(i);
    	se.play();
    }
    

}





















