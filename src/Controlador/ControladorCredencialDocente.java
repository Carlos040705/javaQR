/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Vista.CredencialDocente;
import Vista.PantallaPrincipal;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

/**
 *
 * @author varga
 */
public class ControladorCredencialDocente implements WindowListener {

    private CredencialDocente detalle;
    private PantallaPrincipal pantalla;

    public ControladorCredencialDocente(CredencialDocente detalle) {
        this.detalle = detalle;
        this.detalle.addWindowListener(this);
    }

    @Override
    public void windowOpened(WindowEvent e) {
        pantalla.setEnabled(false);
    }

    @Override
    public void windowClosing(WindowEvent e) {

    }

    @Override
    public void windowClosed(WindowEvent e) {
        pantalla.setEnabled(true);
        pantalla.toFront();
        pantalla.requestFocus();
    }

    @Override
    public void windowIconified(WindowEvent e) {
    }

    @Override
    public void windowDeiconified(WindowEvent e) {
    }

    @Override
    public void windowActivated(WindowEvent e) {
    }

    @Override
    public void windowDeactivated(WindowEvent e) {
    }

    public void setPantallaPrincipal(PantallaPrincipal pantalla) {
        this.pantalla = pantalla;
    }

}
