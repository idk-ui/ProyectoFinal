//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

import java.awt.Component;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class GameFrame extends JFrame {
    public GameFrame() {
        this.setTitle("Polygon dash");
        this.setDefaultCloseOperation(3);
        this.setResizable(false);
        PresentationScreen presentacion = new PresentationScreen();
        this.setContentPane(presentacion);
        this.pack();
        this.setLocationRelativeTo((Component)null);
        this.setVisible(true);
        SwingUtilities.invokeLater(() -> {
            presentacion.setFocusable(true);
            presentacion.requestFocusInWindow();
            presentacion.addKeyListener(new KeyAdapter() {
                public void keyPressed(KeyEvent e) {
                    if (e.getKeyCode() == 10) {
                        GamePanel gamePanel = new GamePanel();
                        GameFrame.this.setContentPane(gamePanel);
                        GameFrame.this.revalidate();
                        gamePanel.requestFocusInWindow();
                    }

                }
            });
        });
    }
}
