package Controlador;

import Vista.AltaAlumno;
import Vista.AltaComunidad;
import Vista.AltaVisitante;
import Vista.AltaDocente;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.SwingUtilities;

public class ControladorAltaComunidad implements ActionListener {

    AltaComunidad panelAltaComunidad;
    AltaAlumno panelAltaAlumno;
    AltaDocente panelAltaDocente;
    AltaVisitante panelAltaVisitante;

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
        panelAltaAlumno = new AltaAlumno(controladorScanner);
        panelAltaDocente = new AltaDocente(controladorScanner);
        panelAltaVisitante = new AltaVisitante();
    }

    private void addAL() {
        panelAltaComunidad.comboTipo.addActionListener(this);
    }

    private void switchCombo(int indice) {
        switch (indice) {
            case 0:
                switchPanel("altaAlumno");
                limpiarPanel();
                controladorScanner.setActiveTextField(panelAltaAlumno.cajaMatricula);
                break;
            case 1:
                switchPanel("altaDocente");
                limpiarPanel();
                controladorScanner.setActiveTextField(panelAltaDocente.cajaNoPlaza);
                break;
            case 2:
                switchPanel("altaVisitante");
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
