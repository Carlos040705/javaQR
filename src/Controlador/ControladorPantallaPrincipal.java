package Controlador;

import Vista.AltaComunidad;
import Vista.Hogar;
import Vista.PantallaPrincipal;
import Vista.ConsultaUsuario;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.SwingUtilities;

public class ControladorPantallaPrincipal implements ActionListener {

    PantallaPrincipal framePrincipal;
    Hogar panelHogar;
    AltaComunidad panelAltaUsuario;
    ConsultaUsuario panelConsultaUsuario;
    
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
        }
    }

    private void initObj() {
        controladorScanner = new ControladorScanner("COM3", 9600);
        panelHogar = new Hogar();
        panelAltaUsuario = new AltaComunidad(controladorScanner);
        panelConsultaUsuario = new ConsultaUsuario(controladorScanner);
    }

    private void addAL() {
        framePrincipal.btnMenu.addActionListener(this);
        framePrincipal.btnHogar.addActionListener(this);
        framePrincipal.btnAlta.addActionListener(this);
        framePrincipal.btnConsulta.addActionListener(this);
    }

    private void addPanel() {
        framePrincipal.panelDinamico.add(panelHogar, "Hogar");
        framePrincipal.panelDinamico.add(panelAltaUsuario, "Alta");
        framePrincipal.panelDinamico.add(panelConsultaUsuario, "Consulta");
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
