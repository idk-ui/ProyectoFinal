import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;

public class GamePanel extends JPanel implements ActionListener, KeyListener {
    Timer timer;
    Player player;
    ArrayList<Obstacle> obstacles;
    boolean upPressed = false;
    ArrayList<Point> trail;

    boolean isDead = false;
    ArrayList<Particle> deathParticles;
    int deathAnimFrames = 0;
    final int MAX_DEATH_FRAMES = 60;

    Random rand = new Random();

    private static final int SCREEN_HEIGHT = 600;
    private static final int OBSTACLE_HEIGHT = 40;
    private static final int Y_TOP_MARGIN = 30;
    private static final int Y_BOTTOM_MARGIN = 30;

    private static final int MIN_OBSTACLE_Y_FOR_TOP_BAR = Y_TOP_MARGIN; // La Y más baja para el obstáculo superior
    private static final int MAX_OBSTACLE_Y_FOR_TOP_BAR = SCREEN_HEIGHT - OBSTACLE_HEIGHT - Y_BOTTOM_MARGIN - (OBSTACLE_HEIGHT + 120); // 120 es el pathGap


    int segmentLength = 200;
    int currentObstacleSpawnX;

    int obstacleSize = 40;
    int pathGap = 120;

    ArrayList<BackgroundParticle> bgParticles;
    ArrayList<NeonLine> neonLines;

    public GamePanel() {
        setPreferredSize(new Dimension(800, 600));
        setBackground(new Color(10, 10, 40));
        setFocusable(true);
        addKeyListener(this);

        player = new Player(100, 300);
        obstacles = new ArrayList<>();
        trail = new ArrayList<>();
        deathParticles = new ArrayList<>();

        currentObstacleSpawnX = player.getX() + 800;

        bgParticles = new ArrayList<>();
        for (int i = 0; i < 150; i++) {
            bgParticles.add(new BackgroundParticle(rand.nextInt(800), rand.nextInt(600)));
        }

        neonLines = new ArrayList<>();
        for (int i = 0; i < 15; i++) {
            neonLines.add(new NeonLine(rand.nextInt(800), rand.nextInt(600), 2 + rand.nextInt(5)));
        }

        timer = new Timer(15, this);
        timer.start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (isDead) {
            deathAnimFrames++;
            for (int i = 0; i < deathParticles.size(); i++) {
                Particle p = deathParticles.get(i);
                p.update();
                if (p.life <= 0) {
                    deathParticles.remove(i);
                    i--;
                }
            }
            if (deathAnimFrames >= MAX_DEATH_FRAMES) {
                JOptionPane.showMessageDialog(this, "¡Perdiste!");
                System.exit(0);
            }
            repaint();
            return;
        }

        if (upPressed) {
            player.moveUp();
        } else {
            player.moveDown();
        }
        player.update();


        if (player.getX() + 800 > currentObstacleSpawnX) {
            int topObstacleY = rand.nextInt(MAX_OBSTACLE_Y_FOR_TOP_BAR - MIN_OBSTACLE_Y_FOR_TOP_BAR + 1) + MIN_OBSTACLE_Y_FOR_TOP_BAR;

            int bottomObstacleY = topObstacleY + OBSTACLE_HEIGHT + pathGap;

            obstacles.add(new Obstacle(currentObstacleSpawnX, topObstacleY, obstacleSize));

            obstacles.add(new Obstacle(currentObstacleSpawnX, bottomObstacleY, obstacleSize));

            currentObstacleSpawnX += segmentLength;
        }

        obstacles.removeIf(obs -> obs.x < player.getX() - 100);

        Rectangle playerBounds = player.getBounds();
        for (Obstacle obs : obstacles) {
            if (obs.collidesWith(playerBounds, player.getX())) {
                startDeathAnimation();
                break;
            }
        }

        trail.add(new Point(player.getX(), player.getY()));
        if (trail.size() > 20) trail.remove(0);

        for (BackgroundParticle bp : bgParticles) bp.update();
        for (NeonLine nl : neonLines) nl.update();

        repaint();
    }

    private void startDeathAnimation() {
        isDead = true;
        deathAnimFrames = 0;
        deathParticles.clear();

        for (int i = 0; i < 50; i++) {
            int px = player.getX();
            int py = player.getY();
            deathParticles.add(new Particle(px, py));
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        for (BackgroundParticle bp : bgParticles) bp.draw(g2);
        for (NeonLine nl : neonLines) nl.draw(g2);

        for (int i = 0; i < trail.size() - 1; i++) {
            Point p1 = trail.get(i);
            Point p2 = trail.get(i + 1);
            g2.setColor(new Color(0, 200, 255, 150));
            g2.setStroke(new BasicStroke(4));
            g2.drawLine(p1.x - player.getX() + 100, p1.y, p2.x - player.getX() + 100, p2.y);
        }

        for (Obstacle obs : obstacles) {
            obs.drawNeon(g2, player.getX());
        }

        if (!isDead) {
            player.draw(g2);
        }

        if (isDead) {
            for (Particle p : deathParticles) {
                p.draw(g2, player.getX());
            }
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE || e.getKeyCode() == KeyEvent.VK_UP) {
            upPressed = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE || e.getKeyCode() == KeyEvent.VK_UP) {
            upPressed = false;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    class Particle {
        double x, y;
        double vx, vy;
        int size;
        int life;
        Color color;

        public Particle(int startX, int startY) {
            x = startX;
            y = startY;
            vx = (rand.nextDouble() - 0.5) * 6;
            vy = (rand.nextDouble() - 0.5) * 6;
            size = 5 + rand.nextInt(5);
            life = 30 + rand.nextInt(30);
            color = new Color(255, 50 + rand.nextInt(205), 50 + rand.nextInt(205));
        }

        public void update() {
            x += vx;
            y += vy;
            vy += 0.2;
            life--;
        }

        public void draw(Graphics2D g2, int playerX) {
            g2.setColor(color);
            int drawX = (int) x - playerX + 100;
            int drawY = (int) y;
            g2.fillOval(drawX, drawY, size, size);
        }
    }

    class BackgroundParticle {
        float x, y;
        float vx, vy;
        float size;
        float brightness;

        public BackgroundParticle(float startX, float startY) {
            x = startX;
            y = startY;
            vx = (rand.nextFloat() - 0.5f) * 0.5f;
            vy = (rand.nextFloat() - 0.5f) * 0.5f;
            size = 1 + rand.nextFloat() * 2;
            brightness = 0.5f + rand.nextFloat() * 0.5f;
        }

        public void update() {
            x += vx;
            y += vy;

            if (x < 0) x = getWidth();
            if (x > getWidth()) x = 0;
            if (y < 0) y = getHeight();
            if (y > getHeight()) y = 0;

            brightness += (rand.nextFloat() - 0.5f) * 0.05f;
            if (brightness < 0.3f) brightness = 0.3f;
            if (brightness > 1.0f) brightness = 1.0f;
        }

        public void draw(Graphics2D g2) {
            Color c = new Color(0, 150, 255, (int)(brightness * 255));
            g2.setColor(c);
            g2.fillOval((int) x, (int) y, (int) size, (int) size);
        }
    }

    class NeonLine {
        float x, y;
        int width;
        float speed;

        public NeonLine(float startX, float startY, int width) {
            this.x = startX;
            this.y = startY;
            this.width = width;
            this.speed = 0.3f + rand.nextFloat() * 0.4f;
        }

        public void update() {
            x -= speed;
            if (x + width < 0) {
                x = getWidth();
                y = rand.nextInt(getHeight());
            }
        }

        public void draw(Graphics2D g2) {
            GradientPaint gp = new GradientPaint(x, y, new Color(0, 180, 255, 120),
                    x + width, y, new Color(0, 180, 255, 10));
            g2.setPaint(gp);
            g2.fillRect((int) x, (int) y, width, 2);
        }
    }
}