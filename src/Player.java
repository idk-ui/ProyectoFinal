import java.awt.*;
import java.awt.geom.AffineTransform;

public class Player {
    private int x, y;
    private int size = 20;
    private int speedY = 8;
    private double tiltAngle = 0;
    private Color colorCursor = new Color(0x82D173);
    public Player(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void moveUp() {
        y -= speedY;
        tiltAngle = -0.6;
    }

    public void moveDown() {
        y += speedY;
        tiltAngle = 0.6;
    }

    public void draw(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(colorCursor);

        AffineTransform old = g2d.getTransform();
        int centerX = 100 + size / 2;
        int centerY = y + size / 2;

        g2d.translate(centerX, centerY);
        g2d.rotate(tiltAngle);

        int[] xPoints = {-size / 2, size / 2, -size / 2};
        int[] yPoints = {-size / 2, 0, size / 2};

        g2d.fillPolygon(xPoints, yPoints, 3);

        g2d.setColor(Color.RED);
        g2d.drawRect(-size / 2, -size / 2, size, size); // Hitbox visual (opcional)

        g2d.setTransform(old);
    }

    public Rectangle getBounds() {
        return new Rectangle(100, y, size, size);
    }

    public Polygon getPolygon() {
        int centerX = 100 + size / 2;
        int centerY = y + size / 2;

        int[] xPoints = {
                centerX - size / 2,
                centerX + size / 2,
                centerX - size / 2
        };

        int[] yPoints = {
                centerY - size / 2,
                centerY,
                centerY + size / 2
        };

        return new Polygon(xPoints, yPoints, 3);
    }

    public int getY() {
        return y;
    }

    public int getSize() {
        return size;
    }

    public void update() {
        tiltAngle *= 0.9;
    }
}
