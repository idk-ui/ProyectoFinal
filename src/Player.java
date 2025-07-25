import java.awt.*;

public class Player {
    private int x, y;
    private int size = 20;
    private int speedY = 5;

    public Player(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void moveUp() {
        y -= speedY;
    }

    public void moveDown() {
        y += speedY;
    }

    public void update() {
        if (y < 0) y = 0;
        if (y > 580) y = 580;
    }

    public void draw(Graphics g) {
        g.setColor(Color.CYAN);
        int[] xPoints = {x, x + size, x};
        int[] yPoints = {y, y + size / 2, y + size};
        g.fillPolygon(xPoints, yPoints, 3);
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, size, size);
    }
}
