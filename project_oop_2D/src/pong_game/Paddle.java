package pong_game;

import java.awt.*;

public class Paddle {
    private static final int WIDTH = 10, HEIGHT = 100;
    private int x, y;
    private int speed = 30;

    public Paddle(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void draw(Graphics g) {
        g.setColor(Color.WHITE);
        g.fillRect(x, y, WIDTH, HEIGHT);
    }

    public void moveUp() {
        if (y - speed >= 0) {
            y -= speed;
        }
    }

    public void moveDown() {
        if (y + speed <= 500) { // ADJUSTING FOR THE PANEL'S HEIGHT
            y += speed;
        }
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, WIDTH, HEIGHT);
    }
}
