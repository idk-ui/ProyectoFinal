import java.awt.*;

public class Obstacle {
    int x, y, base;

    public Obstacle(int x, int y, int base) {
        this.x = x;
        this.y = y;
        this.base = base;
    }

    public void drawNeon(Graphics2D g2, int playerX) {
        int offsetX = x - playerX + 100;

        Polygon tri = new Polygon(
                new int[]{offsetX, offsetX + base / 2, offsetX + base},
                new int[]{y + base, y, y + base},
                3
        );

        for (int i = 8; i > 0; i--) {
            float alpha = 0.03f * i;
            g2.setColor(new Color(0, 180, 255, (int)(alpha * 255)));
            g2.setStroke(new BasicStroke(i * 2));
            g2.drawPolygon(tri);
        }

        GradientPaint gp = new GradientPaint(offsetX, y, new Color(0, 255, 255),
                offsetX + base, y + base, new Color(0, 100, 255));
        g2.setPaint(gp);
        g2.fillPolygon(tri);

        g2.setColor(new Color(0, 255, 255));
        g2.setStroke(new BasicStroke(2));
        g2.drawPolygon(tri);
    }

    public boolean collidesWith(Rectangle playerBounds, int playerX) {
        int offsetX = x - playerX + 100;

        Polygon tri = new Polygon(
                new int[]{offsetX, offsetX + base / 2, offsetX + base},
                new int[]{y + base, y, y + base},
                3
        );

        return tri.intersects(playerBounds);
    }
}


