package pong_game;

import java.awt.*;

public class Ball {
    public static final int SIZE = 20;
    private int x, y;
    private int xVelocity = 4, yVelocity = 4;

    public Ball(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void draw(Graphics g) {
        g.setColor(Color.WHITE);
        g.fillOval(x, y, SIZE, SIZE);
    }

    public void move() {
        x += xVelocity;
        y += yVelocity;
    }

    public void bounceVertical() {
        yVelocity = -yVelocity;
    }

    public void bounceHorizontal() {
        xVelocity = -xVelocity;
    }

    public void resetPosition() {
        x = 400;
        y = 300;
        xVelocity = -xVelocity; // Change direction after scoring
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, SIZE, SIZE);
    }
}
