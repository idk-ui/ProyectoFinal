import java.awt.*;
import java.awt.geom.AffineTransform;

public class Player {
    private int x, y;
    private int size = 20;
    private int speedY = 5;
    private double tiltAngle = 0;

    public Player(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void moveUp() {
        y -= speedY;
        tiltAngle = -0.4;
    }

    public void moveDown() {
        y += speedY;
        tiltAngle = 0.4;
    }

    public void update() {
        x += 4;
        if (y < 0) y = 0;
        if (y > 580) y = 580;
        tiltAngle *= 1;
    }

    public void draw(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.RED);

        AffineTransform old = g2d.getTransform();


        g2d.translate(100 + size / 2, y + size / 2);
        g2d.rotate(tiltAngle);


        int[] xPoints = {-size / 2, size / 2, -size / 2};
        int[] yPoints = {-size / 2, 0, size / 2};

        g2d.fillPolygon(xPoints, yPoints, 3);

        g2d.setTransform(old);
    }

    public Rectangle getBounds() {
        return new Rectangle(100, y, size, size);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
