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
import javax.swing.table.DefaultTableModel;

public class ControladorPantallaPrincipal implements ActionListener {

    PantallaPrincipal framePrincipal;
    Verificador verificador;
    Hogar panelHogar;
    AltaComunidad panelAltaUsuario;
    ConsultaUsuario panelConsultaUsuario;
    IngresoUsuario panelIngresoUsuario;
    Historial panelHistorial;
    Reporte panelReporte;
    DefaultTableModel modelo;
    CRUDPersona crudPersona;
    CRUDSesion crudSesiones;

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
            verificador.limpiarDatos(panelAltaUsuario);
            verificador.LimpiarComboBox(panelAltaUsuario);
            switchPanel("Alta");
            resetSideMenu();
        } else if (e.getSource().equals(framePrincipal.btnConsulta)) {
            verificador.limpiarDatos(panelConsultaUsuario);
            verificador.LimpiarComboBox(panelConsultaUsuario);
            panelConsultaUsuario.setPantallaPrincipal(framePrincipal);
            panelConsultaUsuario.llenarTabla(crudPersona.read(), modelo);
            switchPanel("Consulta");
            controladorScanner.setActiveTextField(panelConsultaUsuario.cajaBuscarClave);
            resetSideMenu();
        } else if (e.getSource().equals(framePrincipal.btnIngresoUsuario)) {
            verificador.limpiarDatos(panelIngresoUsuario);
            verificador.LimpiarComboBox(panelIngresoUsuario);
            switchPanel("IngresoUsuario");
            controladorScanner.setActiveTextField(panelIngresoUsuario.cajaMatricula);
            resetSideMenu();
        } else if (e.getSource().equals(framePrincipal.btnHistorial)) {
             verificador.limpiarDatos(panelHistorial);
            verificador.LimpiarComboBox(panelHistorial);
            panelHistorial.llenarTabla(crudSesiones.read());
            switchPanel("Historial");
            controladorScanner.setActiveTextField(panelHistorial.cajaBuscarClave);
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
        panelConsultaUsuario = new ConsultaUsuario();
        panelIngresoUsuario = new IngresoUsuario();
        panelHistorial = new Historial();
        panelReporte = new Reporte();
        modelo = (DefaultTableModel) panelConsultaUsuario.tablaPersonas.getModel();
        crudPersona = new CRUDPersona();
        crudSesiones = new CRUDSesion();
        verificador = new Verificador();
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
