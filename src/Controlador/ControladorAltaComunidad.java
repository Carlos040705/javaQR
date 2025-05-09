package Controlador;

import Vista.AltaAlumno;
import Vista.AltaComunidad;
import Vista.AltaVisitante;
import Vista.AltaDocente;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.SwingUtilities;

public class ControladorAltaComunidad implements ActionListener {

    AltaComunidad panelAltaComunidad;
    AltaAlumno panelAltaAlumno;
    AltaDocente panelAltaDocente;
    AltaVisitante panelAltaVisitante;
    Verificador verificador;
    ControladorScanner controladorScanner;

    public ControladorAltaComunidad(AltaComunidad panelAltaComunidad, ControladorScanner controladorScanner) {
        this.panelAltaComunidad = panelAltaComunidad;
        this.controladorScanner = controladorScanner;
        initObj();
        addPanel();
        addAL();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(panelAltaComunidad.comboTipo)) {
            switchCombo(panelAltaComunidad.comboTipo.getSelectedIndex());
        }
    }

    private void initObj() {
        panelAltaAlumno = new AltaAlumno();
        panelAltaAlumno.setConfirmacion(panelAltaComunidad.etiquetaConfirmacion);
        panelAltaDocente = new AltaDocente();
        panelAltaVisitante = new AltaVisitante();
        verificador = new Verificador();
    }

    private void addAL() {
        panelAltaComunidad.comboTipo.addActionListener(this);
    }

    private void switchCombo(int indice) {
        switch (indice) {
            case 0:
                verificador.LimpiarComboBox(panelAltaAlumno);
                verificador.limpiarDatos(panelAltaAlumno);
                switchPanel("altaAlumno");
                panelAltaComunidad.etiquetaIcono.setIcon(new ImageIcon(ControladorAltaComunidad.class.getResource("/Vista/Recursos/icons8-graduate-100.png")));
                limpiarPanel();
                controladorScanner.setActiveTextField(panelAltaAlumno.cajaMatricula);
                break;
            case 1:
                verificador.LimpiarComboBox(panelAltaDocente);
                verificador.limpiarDatos(panelAltaDocente);
                switchPanel("altaDocente");
                panelAltaComunidad.etiquetaIcono.setIcon(new ImageIcon(ControladorAltaComunidad.class.getResource("/Vista/Recursos/icons8-teacher-100.png")));
                limpiarPanel();
                controladorScanner.setActiveTextField(panelAltaDocente.cajaNoPlaza);
                break;
            case 2:
                verificador.LimpiarComboBox(panelAltaVisitante);
                verificador.limpiarDatos(panelAltaVisitante);
                switchPanel("altaVisitante");
                panelAltaComunidad.etiquetaIcono.setIcon(new ImageIcon(ControladorAltaComunidad.class.getResource("/Vista/Recursos/icons8-visitor-100.png")));
                limpiarPanel();
                break;
            default:
                throw new AssertionError();
        }
    }

    private void addPanel() {
        panelAltaComunidad.panelDinamico.add(panelAltaAlumno, "altaAlumno");
        panelAltaComunidad.panelDinamico.add(panelAltaDocente, "altaDocente");
        panelAltaComunidad.panelDinamico.add(panelAltaVisitante, "altaVisitante");
    }

    private void switchPanel(String referencia) {
        panelAltaComunidad.vistaDinamica.show(panelAltaComunidad.panelDinamico, referencia);
        SwingUtilities.updateComponentTreeUI(panelAltaComunidad);
        panelAltaComunidad.repaint();
    }

    private void limpiarPanel() {
        panelAltaComunidad.panelDinamico.revalidate();
        panelAltaComunidad.panelDinamico.repaint();
    }

}
