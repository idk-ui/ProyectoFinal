import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Iterator;

public class GamePanel extends JPanel implements ActionListener, KeyListener {
    private Timer timer;
    private Player player;
    private ArrayList<Obstacle> obstacles;
    private boolean movingUp = false;
    private final int PANEL_WIDTH = 800;
    private final int PANEL_HEIGHT = 600;

    public GamePanel() {
        setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
        setBackground(Color.BLACK);
        setFocusable(true);
        addKeyListener(this);

        player = new Player(100, PANEL_HEIGHT / 2);
        obstacles = new ArrayList<>();
        timer = new Timer(20, this);
        timer.start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (movingUp) {
            player.moveUp();
        } else {
            player.moveDown();
        }

        player.update();
        spawnObstacles();
        updateObstacles();
        checkCollisions();
        repaint();
    }

    private void spawnObstacles() {
        if (Math.random() < 0.02) {
            obstacles.add(new Obstacle(PANEL_WIDTH, (int)(Math.random() * (PANEL_HEIGHT - 100)), 20, 100));
        }
    }

    private void updateObstacles() {
        Iterator<Obstacle> it = obstacles.iterator();
        while (it.hasNext()) {
            Obstacle obs = it.next();
            obs.move();
            if (obs.getX() + obs.getWidth() < 0) {
                it.remove();
            }
        }
    }

    private void checkCollisions() {
        Rectangle playerBounds = player.getBounds();
        for (Obstacle obs : obstacles) {
            if (playerBounds.intersects(obs.getBounds())) {
                timer.stop();
                JOptionPane.showMessageDialog(this, "¡Game Over!");
                System.exit(0);
            }
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        player.draw(g);
        for (Obstacle obs : obstacles) {
            obs.draw(g);
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            movingUp = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            movingUp = false;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {}
}
