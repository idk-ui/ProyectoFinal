import java.awt.*;

public class Obstacle {
    int x, height;
    boolean isTop;
    int width = 500;
    private Color colorSpike = new Color(0x0D0630);
    public Obstacle(int x, int height, boolean isTop) {
        this.x = x;
        this.height = height;
        this.isTop = isTop;
    }

    public void draw(Graphics g, int playerX) {
        int drawX = x - playerX;
        int[] xPoints = {drawX, drawX + width / 2, drawX + width};
        int[] yPoints;

        if (isTop) {
            yPoints = new int[]{0, height, 0};
        } else {
            int bottom = 600; // altura del panel
            yPoints = new int[]{bottom, bottom - height, bottom};
        }

        g.setColor(colorSpike);
        g.fillPolygon(xPoints, yPoints, 3);

    }

    public boolean collidesWith(Rectangle playerBounds, int playerX) {
        return getPolygon(playerX).intersects(playerBounds);
    }

    public Polygon getPolygon(int playerX) {
        int drawX = x - playerX;
        int[] xPoints = {drawX, drawX + width / 2, drawX + width};
        int[] yPoints;

        if (isTop) {
            yPoints = new int[]{0, height, 0};
        } else {
            int yBase = 600;
            yPoints = new int[]{yBase, yBase - height, yBase};
        }

        return new Polygon(xPoints, yPoints, 3);
    }
}