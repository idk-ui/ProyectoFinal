//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.Random;

public class Particle {
    float x;
    float y;
    float dx;
    float dy;
    int life;
    int size;
    Color color;
    private static final Random rand = new Random();
    private int startX;

    public Particle(int x, int y, Color color, int startX) {
        this.x = (float)x;
        this.y = (float)y;
        this.size = 4 + rand.nextInt(3);
        this.life = 90 + rand.nextInt(30);
        this.color = color;
        this.startX = startX;
        double angle = rand.nextDouble() * (double)2.0F * Math.PI;
        double speed = (double)4.0F + rand.nextDouble() * (double)4.0F;
        this.dx = (float)(Math.cos(angle) * speed);
        this.dy = (float)(Math.sin(angle) * speed);
    }

    public void update() {
        this.x += this.dx;
        this.y += this.dy;
        this.dx = (float)((double)this.dx * 0.95);
        this.dy = (float)((double)this.dy * 0.95);
        --this.life;
    }

    public void draw(Graphics2D g2, int playerX) {
        int screenX = (int)(this.x - (float)playerX + (float)this.startX);
        int alpha = Math.max(0, Math.min(255, this.life * 3));
        g2.setColor(new Color(this.color.getRed(), this.color.getGreen(), this.color.getBlue(), alpha));
        g2.fillOval(screenX - this.size / 2, (int)(this.y - (float)(this.size / 2)), this.size, this.size);
    }

    public boolean isAlive() {
        return this.life > 0;
    }
}
