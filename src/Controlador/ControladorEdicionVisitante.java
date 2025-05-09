/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Modelo.Institucion;
import Modelo.Visitante;
import Vista.EdicionVisitante;
import Modelo.Sexo;
import Vista.PantallaPrincipal;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class ControladorEdicionVisitante implements ActionListener, WindowListener {

    private Visitante objVisitante;
    private ManejoBD manejo;
    private EdicionVisitante edicion;
    private CRUDPersona crudPersona;
    PantallaPrincipal pantallaPrincipal;

    public ControladorEdicionVisitante(EdicionVisitante edicion) {
        this.edicion = edicion;
        crudPersona = new CRUDPersona();
        this.edicion.guardarVisitante.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(edicion.guardarVisitante)) {
            objVisitante.setNombre(edicion.cajaNombre.getText());
            objVisitante.setApPaterno(edicion.cajaApPaterno.getText());
            objVisitante.setApMaterno(edicion.cajaApMaterno.getText());
            objVisitante.setInstitucion(((Institucion) edicion.comboInstitucion.getSelectedItem()).getId());
            objVisitante.setAñoNacimiento(Integer.parseInt(((String) edicion.comboAño.getSelectedItem())));
            objVisitante.setSexo(((Sexo) edicion.comboSexo.getSelectedItem()).getId());
            crudPersona.setPersona(objVisitante);
            crudPersona.update();
            edicion.dispose();
        }
    }

    public void setObjVisitante(Visitante objVisitante) {
        this.objVisitante = objVisitante;
    }

    public void setManejoBD(ManejoBD manejo) {
        this.manejo = manejo;
    }

    public void setPantallaPrincipal(PantallaPrincipal pantallaPrincipal) {
        this.pantallaPrincipal = pantallaPrincipal;
    }

    @Override
    public void windowOpened(WindowEvent e) {
        pantallaPrincipal.setEnabled(false);
    }

    @Override
    public void windowClosing(WindowEvent e) {
    }

    @Override
    public void windowClosed(WindowEvent e) {
        pantallaPrincipal.setEnabled(true);
        pantallaPrincipal.toFront();
        pantallaPrincipal.requestFocus();
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
}
