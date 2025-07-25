//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class PresentationScreen extends JPanel {
    private Image logoUTP;
    private Image logoFacultad;

    public PresentationScreen() {
        this.setPreferredSize(new Dimension(800, 600));
        this.setBackground(Color.BLACK);
        this.logoUTP = (new ImageIcon(this.getClass().getResource("/images/logo_utp.png"))).getImage();
        this.logoFacultad = (new ImageIcon(this.getClass().getResource("/images/logo_facultad.png"))).getImage();
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", 1, 28));
        g.drawString("UNIVERSIDAD TECNOLÓGICA DE PANAMÁ", 100, 60);
        g.setFont(new Font("Arial", 0, 22));
        g.drawString("Facultad de Ingeniería de Sistemas Computacionales", 60, 100);
        g.drawString("Licenciatura en Ingeniería de Software", 160, 140);
        g.drawString("Grupo: 1SF-125", 300, 180);
        g.drawImage(this.logoUTP, 100, 220, 150, 150, this);
        g.drawImage(this.logoFacultad, 550, 220, 150, 150, this);
        g.setFont(new Font("Arial", 0, 20));
        g.drawString("Integrantes:", 320, 400);
        g.drawString("-     Jair Arrocha      | 6-727-2055", 280, 430);
        g.drawString("-     Alejandro Fuentes | 8-1136-944", 280, 460);
        g.drawString("-     Eliber Oropeza    | 20-70-8022", 280, 490);
        g.drawString("-     Santiago Moneda   | E-8-228347", 280, 520);
        g.drawString("Profesor: Mgtr. Nombre del Profesor", 240, 550);
        g.drawString("Fecha de Entrega: 25 de Julio de 2025", 240, 580);
    }
}
