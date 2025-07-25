//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Polygon;
import java.awt.Rectangle;

public class Obstacle {
    int x;
    int height;
    boolean isTop;
    int width = 600;
    private Color colorSpike = new Color(853552);

    public Obstacle(int x, int height, boolean isTop) {
        this.x = x;
        this.height = height;
        this.isTop = isTop;
    }

    public void draw(Graphics g, int playerX) {
        int drawX = this.x - playerX;
        int[] xPoints = new int[]{drawX, drawX + this.width / 2, drawX + this.width};
        int[] yPoints;
        if (this.isTop) {
            yPoints = new int[]{0, this.height, 0};
        } else {
            int bottom = 600;
            yPoints = new int[]{bottom, bottom - this.height, bottom};
        }

        g.setColor(this.colorSpike);
        g.fillPolygon(xPoints, yPoints, 3);
    }

    public boolean collidesWith(Rectangle playerBounds, int playerX) {
        return this.getPolygon(playerX).intersects(playerBounds);
    }

    public Polygon getPolygon(int playerX) {
        int drawX = this.x - playerX;
        int[] xPoints = new int[]{drawX, drawX + this.width / 2, drawX + this.width};
        int[] yPoints;
        if (this.isTop) {
            yPoints = new int[]{0, this.height, 0};
        } else {
            int yBase = 600;
            yPoints = new int[]{yBase, yBase - this.height, yBase};
        }

        return new Polygon(xPoints, yPoints, 3);
    }
}
