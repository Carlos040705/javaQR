package Controlador;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JToggleButton;

public class ControladorDiseñoBoton implements MouseListener {

    private Color colorBoton;

    public ControladorDiseñoBoton(ArrayList<AbstractButton> botones) {
        for (AbstractButton boton : botones) {
            boton.addMouseListener(this);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        if (e.getSource() instanceof JButton) {
            JButton boton = (JButton) (e.getSource());
            colorBoton = boton.getBackground();
            int r, g, b;
            r = colorBoton.getRed() + 25;
            g = colorBoton.getGreen() + 25;
            b = colorBoton.getBlue() + 25;
            boton.setBackground(new Color(r, g, b));
            // boton.setForeground(new Color(252, 154, 3));
            //  boton.setBorderPainted(true);

        } else if (e.getSource() instanceof JToggleButton) {
            JToggleButton boton = (JToggleButton) (e.getSource());
            // boton.setForeground(new Color(252, 154, 3));
            boton.setBorderPainted(true);
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {
        if (e.getSource() instanceof JButton) {
            JButton boton = (JButton) (e.getSource());
            colorBoton = boton.getBackground();
            int r, g, b;
            r = colorBoton.getRed() - 25;
            g = colorBoton.getGreen() - 25;
            b = colorBoton.getBlue() - 25;
            boton.setBackground(new Color(r, g, b));
            // boton.setForeground(new Color(240, 240, 240));
            // boton.setBorderPainted(false);
        } else if (e.getSource() instanceof JToggleButton) {
            JToggleButton boton = (JToggleButton) (e.getSource());
            boton.setForeground(new Color(240, 240, 240));
            boton.setBorderPainted(false);
        }
    }

}
