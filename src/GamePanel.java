//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.Area;
import java.awt.image.ImageObserver;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

public class GamePanel extends JPanel implements ActionListener, KeyListener {
    Timer timer;
    Player player;
    ArrayList<Obstacle> obstacles;
    ArrayList<Point> trail = new ArrayList();
    ArrayList<Particle> deathParticles = new ArrayList();
    int obstacleSpacing = 150;
    int playerX = 0;
    boolean nextIsTop = true;
    boolean upPressed = false;
    boolean isDead = false;
    boolean waitingAfterDeath = false;
    long deathTime = 0L;
    private Image fondo;
    boolean test2 = false;
    Random rand = new Random();
    private Color colorBG = new Color(3429585);

    public GamePanel() {
        this.setPreferredSize(new Dimension(800, 600));
        this.setBackground(this.colorBG);
        this.setFocusable(true);
        this.addKeyListener(this);
        this.fondo = (new ImageIcon(this.getClass().getResource("/images/test.png"))).getImage();
        this.startGame();
    }

    public void startGame() {
        this.player = new Player(100, 300);
        this.playerX = 0;
        this.isDead = false;
        this.test2 = false;
        this.waitingAfterDeath = false;
        this.trail.clear();
        this.deathParticles.clear();
        this.obstacles = new ArrayList();
        SoundPlayer.play("musica.wav");
        int currentX = 400;
        this.nextIsTop = true;

        for(int i = 0; i < 60; ++i) {
            int height = 275 + this.rand.nextInt(80);
            this.obstacles.add(new Obstacle(currentX, height, this.nextIsTop));
            currentX += this.obstacleSpacing;
            this.nextIsTop = !this.nextIsTop;
        }

        this.timer = new Timer(16, this);
        this.timer.start();
    }

    public void resetGame() {
        this.startGame();
    }

    public void createDeathExplosion() {
        int test = (new Random()).nextInt(10) + 1;
        SoundPlayer.stop();
        if (test == 8) {
            SoundPlayer.play("test.wav");
            this.test2 = true;
        } else {
            SoundPlayer.play("death.wav");
        }

        this.deathParticles.clear();
        this.trail.clear();

        for(int i = 0; i < 120; ++i) {
            this.deathParticles.add(new Particle(this.playerX + this.player.getSize() / 2, this.player.getY() + this.player.getSize() / 2, new Color(this.rand.nextInt(256), this.rand.nextInt(256), this.rand.nextInt(256)), 100));
        }

    }

    private boolean polygonsIntersect(Polygon p1, Polygon p2) {
        Area area1 = new Area(p1);
        area1.intersect(new Area(p2));
        return !area1.isEmpty();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (this.test2) {
            g.drawImage(this.fondo, 0, 0, this.getWidth(), this.getHeight(), (ImageObserver)null);
        }

        Graphics2D g2 = (Graphics2D)g;

        for(Obstacle obs : this.obstacles) {
            obs.draw(g2, this.playerX);
        }

        if (!this.isDead) {
            g2.setStroke(new BasicStroke(2.0F));
            g2.setColor(Color.CYAN);

            for(int i = 1; i < this.trail.size(); ++i) {
                Point p1 = (Point)this.trail.get(i - 1);
                Point p2 = (Point)this.trail.get(i);
                int x1 = p1.x - this.playerX + 100;
                int x2 = p2.x - this.playerX + 100;
                g2.drawLine(x1, p1.y, x2, p2.y);
            }
        }

        for(Particle p : this.deathParticles) {
            p.draw(g2, this.playerX);
        }

        if (!this.isDead) {
            this.player.draw(g2);
        }

        if (this.isDead && !this.waitingAfterDeath) {
            g2.setColor(Color.RED);
            g2.setFont(new Font("Arial", 1, 32));
            g2.drawString("¡Game Over!", this.getWidth() / 2 - 100, this.getHeight() / 2);
            g2.setFont(new Font("Arial", 0, 20));
            g2.setColor(Color.WHITE);
            g2.drawString("Presiona R para reintentar", this.getWidth() / 2 - 120, this.getHeight() / 2 + 40);
        }

    }

    public void actionPerformed(ActionEvent e) {
        if (!this.isDead) {
            if (this.upPressed) {
                this.player.moveUp();
            } else {
                this.player.moveDown();
            }

            this.player.update();
            this.playerX += 6;
            if (this.player.getY() <= 0 || this.player.getY() + this.player.getSize() >= this.getHeight()) {
                this.isDead = true;
                this.createDeathExplosion();
                this.deathTime = System.currentTimeMillis();
                this.waitingAfterDeath = true;
            }

            this.trail.add(new Point(this.playerX + this.player.getSize() / 2, this.player.getY() + this.player.getSize() / 2));
            if (this.trail.size() > 200) {
                this.trail.remove(0);
            }

            Polygon playerHitbox = this.player.getPolygon();

            for(Obstacle obs : this.obstacles) {
                Polygon spikeHitbox = obs.getPolygon(this.playerX);
                if (this.polygonsIntersect(playerHitbox, spikeHitbox)) {
                    this.isDead = true;
                    this.createDeathExplosion();
                    this.deathTime = System.currentTimeMillis();
                    this.waitingAfterDeath = true;
                    break;
                }
            }
        } else if (this.waitingAfterDeath) {
            long elapsed = System.currentTimeMillis() - this.deathTime;
            if (elapsed >= 2000L) {
                this.waitingAfterDeath = false;
                this.timer.stop();
            }
        }

        for(Particle p : this.deathParticles) {
            p.update();
        }

        this.deathParticles.removeIf((p) -> !p.isAlive());
        this.repaint();
    }

    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        if (!this.isDead && (key == 32 || key == 38)) {
            this.upPressed = true;
        }

        if (this.isDead && !this.waitingAfterDeath && key == 82) {
            this.resetGame();
        }

    }

    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == 32 || key == 38) {
            this.upPressed = false;
        }

    }

    public void keyTyped(KeyEvent e) {
    }
}
