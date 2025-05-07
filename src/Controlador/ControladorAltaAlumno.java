package Controlador;

import Vista.AltaAlumno;

public class ControladorAltaAlumno {

    AltaAlumno panelAltaAlumno;
    ControladorScanner controladorScanner;

    public ControladorAltaAlumno(AltaAlumno panelAltaAlumno, ControladorScanner controladorScanner) {
        this.panelAltaAlumno = panelAltaAlumno;
        this.controladorScanner = controladorScanner;
    }
}
