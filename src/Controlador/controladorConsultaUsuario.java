package Controlador;

import Vista.ConsultaUsuario;

public class controladorConsultaUsuario {
    ConsultaUsuario panelConsultaUsuario;
    ControladorScanner controladorScanner;
    public controladorConsultaUsuario(ConsultaUsuario panelConsultaUsuario, ControladorScanner controladorScanner) {
        this.panelConsultaUsuario = panelConsultaUsuario;
        this.controladorScanner = controladorScanner;
        initScanner();
    }
    
    public void initScanner(){
        controladorScanner.setActiveTextField(panelConsultaUsuario.cajaBuscarClave);
    }

}
