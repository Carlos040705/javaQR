package Controlador;

import Vista.AltaComunidad;
import Vista.Hogar;
import Vista.PantallaPrincipal;
import Vista.ConsultaUsuario;
import Vista.Historial;
import Vista.IngresoUsuario;
import Vista.Reporte;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.SwingUtilities;

public class ControladorPantallaPrincipal implements ActionListener {

    PantallaPrincipal framePrincipal;
    Hogar panelHogar;
    AltaComunidad panelAltaUsuario;
    ConsultaUsuario panelConsultaUsuario;
    IngresoUsuario panelIngresoUsuario;
    Historial panelHistorial;
    Reporte panelReporte;

    ControladorScanner controladorScanner;

    public ControladorPantallaPrincipal(PantallaPrincipal framePrincipal) {
        this.framePrincipal = framePrincipal;
        initObj();
        addPanel();
        addAL();
        framePrincipal.btnHogar.doClick();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(framePrincipal.btnMenu)) {
            framePrincipal.sp.onSideMenu();
            if (!framePrincipal.sp.getIsOpen()) {
                framePrincipal.sp.setIsOpen(true);
            } else {
                framePrincipal.sp.setIsOpen(false);
            }

        } else if (e.getSource().equals(framePrincipal.btnHogar)) {
            switchPanel("Hogar");
            resetSideMenu();

        } else if (e.getSource().equals(framePrincipal.btnAlta)) {
            switchPanel("Alta");
            resetSideMenu();
        } else if (e.getSource().equals(framePrincipal.btnConsulta)) {
            switchPanel("Consulta");
            resetSideMenu();
        } else if (e.getSource().equals(framePrincipal.btnIngresoUsuario)) {
            switchPanel("IngresoUsuario");
            resetSideMenu();
        } else if (e.getSource().equals(framePrincipal.btnHistorial)) {
            switchPanel("Historial");
            resetSideMenu();
        } else if (e.getSource().equals(framePrincipal.btnReportes)) {
            switchPanel("Reportes");
            resetSideMenu();
        }
    }

    private void initObj() {
        controladorScanner = new ControladorScanner("COM3", 9600);
        panelHogar = new Hogar();
        panelAltaUsuario = new AltaComunidad(controladorScanner);
        panelConsultaUsuario = new ConsultaUsuario(controladorScanner);
        panelIngresoUsuario = new IngresoUsuario();
        panelHistorial = new Historial();
        panelReporte = new Reporte();
    }

    private void addAL() {
        framePrincipal.btnMenu.addActionListener(this);
        framePrincipal.btnHogar.addActionListener(this);
        framePrincipal.btnAlta.addActionListener(this);
        framePrincipal.btnConsulta.addActionListener(this);
        framePrincipal.btnIngresoUsuario.addActionListener(this);
        framePrincipal.btnHistorial.addActionListener(this);
        framePrincipal.btnReportes.addActionListener(this);
    }

    private void addPanel() {
        framePrincipal.panelDinamico.add(panelHogar, "Hogar");
        framePrincipal.panelDinamico.add(panelAltaUsuario, "Alta");
        framePrincipal.panelDinamico.add(panelConsultaUsuario, "Consulta");
        framePrincipal.panelDinamico.add(panelIngresoUsuario, "IngresoUsuario");
        framePrincipal.panelDinamico.add(panelHistorial, "Historial");
        framePrincipal.panelDinamico.add(panelReporte, "Reportes");
    }

    private void switchPanel(String referencia) {
        framePrincipal.vistaDinamica.show(framePrincipal.panelDinamico, referencia);
        SwingUtilities.updateComponentTreeUI(framePrincipal);
        limpiarPanel();
    }

    private void limpiarPanel() {
        framePrincipal.panelDinamico.revalidate();
        framePrincipal.panelDinamico.repaint();
    }

    private void resetSideMenu() {
        if (framePrincipal.sp.getIsOpen()) {
            framePrincipal.sp.onSideMenu();
            framePrincipal.sp.setIsOpen(false);
        }
    }
}
