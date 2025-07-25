//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;

public class Player {
    private int x;
    private int y;
    private int size = 20;
    private int speedY = 9;
    private double tiltAngle = (double)0.0F;
    private Color colorCursor = new Color(8573299);

    public Player(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void moveUp() {
        this.y -= this.speedY;
        this.tiltAngle = -0.6;
    }

    public void moveDown() {
        this.y += this.speedY;
        this.tiltAngle = 0.6;
    }

    public void draw(Graphics g) {
        Graphics2D g2d = (Graphics2D)g;
        g2d.setColor(this.colorCursor);
        AffineTransform old = g2d.getTransform();
        int centerX = 100 + this.size / 2;
        int centerY = this.y + this.size / 2;
        g2d.translate(centerX, centerY);
        g2d.rotate(this.tiltAngle);
        int[] xPoints = new int[]{-this.size / 2, this.size / 2, -this.size / 2};
        int[] yPoints = new int[]{-this.size / 2, 0, this.size / 2};
        g2d.fillPolygon(xPoints, yPoints, 3);
        g2d.setTransform(old);
    }

    public Rectangle getBounds() {
        return new Rectangle(100, this.y, this.size, this.size);
    }

    public Polygon getPolygon() {
        int centerX = 100 + this.size / 2;
        int centerY = this.y + this.size / 2;
        int[] xPoints = new int[]{centerX - this.size / 2, centerX + this.size / 2, centerX - this.size / 2};
        int[] yPoints = new int[]{centerY - this.size / 2, centerY, centerY + this.size / 2};
        return new Polygon(xPoints, yPoints, 3);
    }

    public int getY() {
        return this.y;
    }

    public int getSize() {
        return this.size;
    }

    public void update() {
        this.tiltAngle *= 0.9;
    }
}
