package pong_game;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class GamePanel extends JPanel implements ActionListener, KeyListener {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Paddle leftPaddle;
    private Paddle rightPaddle;
    private Ball ball;
    private Timer timer;
    private int leftScore = 0;
    private int rightScore = 0;

    public GamePanel() {
        setBackground(Color.BLACK);
        leftPaddle = new Paddle(30, 250);
        rightPaddle = new Paddle(750, 250);
        ball = new Ball(400, 300);
        timer = new Timer(10, this);
        timer.start();
        addKeyListener(this);
        setFocusable(true);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        leftPaddle.draw(g);
        rightPaddle.draw(g);
        ball.draw(g);

        // Draw scores
        g.setColor(Color.WHITE);
        g.setFont(new Font("Courier", Font.BOLD, 40));
        g.drawString(String.valueOf(leftScore), 300, 50);
        g.drawString(String.valueOf(rightScore), 500, 50);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        ball.move();

        // Ball collision with walls
        if (ball.getY() <= 0 || ball.getY() >= getHeight() - Ball.SIZE) {
            ball.bounceVertical();
        }

        // Ball collision with paddles
        if (ball.getBounds().intersects(leftPaddle.getBounds()) ||
            ball.getBounds().intersects(rightPaddle.getBounds())) {
            ball.bounceHorizontal();
        }

        // Ball out of bounds
        if (ball.getX() <= 0) {
            rightScore++;
            ball.resetPosition();
        } else if (ball.getX() >= getWidth() - Ball.SIZE) {
            leftScore++;
            ball.resetPosition();
        }

        repaint();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_W) {
            leftPaddle.moveUp();
        } else if (e.getKeyCode() == KeyEvent.VK_S) {
            leftPaddle.moveDown();
        } else if (e.getKeyCode() == KeyEvent.VK_UP) {
            rightPaddle.moveUp();
        } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            rightPaddle.moveDown();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {}

    @Override
    public void keyTyped(KeyEvent e) {}
    
    
    // FOR QUITTING THE PONG GAME
    public void quitPongGame() {
    	Window window = SwingUtilities.getWindowAncestor(this);
    	if(window != null) {
    		window.dispose();
    	}
    }
    
}
