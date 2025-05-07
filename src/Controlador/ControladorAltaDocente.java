package Controlador;

import Vista.AltaDocente;

public class ControladorAltaDocente {

    AltaDocente panelAltaDocente;
    ControladorScanner controladorScanner;

    public ControladorAltaDocente(AltaDocente panelAltaDocente, ControladorScanner controladorScanner) {
        this.panelAltaDocente = panelAltaDocente;
        this.controladorScanner = controladorScanner;
    }
}
