/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Modelo.Docente;
import Modelo.Sexo;
import Vista.EdicionDocente;
import Vista.PantallaPrincipal;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

/**
 *
 * @author varga
 */
public class ControladorEdicionDocente implements ActionListener, WindowListener {
    
    EdicionDocente edicion;
    Docente objDocente;
    CRUDPersona crudPersona;
    PantallaPrincipal pantallaPrincipal;
    
    public ControladorEdicionDocente(EdicionDocente edicion) {
        this.edicion = edicion;
        crudPersona = new CRUDPersona();
        addAL();
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(edicion.guardarDocente)) {
            objDocente.setNombre(edicion.cajaNombre.getText());
            objDocente.setApPaterno(edicion.cajaApPaterno.getText());
            objDocente.setApMaterno(edicion.cajaApMaterno.getText());
            objDocente.setNoDePlaza(edicion.cajaNoPlaza.getText());
            objDocente.setAñoNacimiento(Integer.parseInt((String) edicion.comboAño.getSelectedItem()));
            objDocente.setSexo(((Sexo) edicion.comboSexo.getSelectedItem()).getId());
            crudPersona.setPersona(objDocente);
            crudPersona.update();
            edicion.dispose();
            
        }
        
    }
    
    public void addAL() {
        edicion.guardarDocente.addActionListener(this);
    }
    
    public void setDocente(Docente objdoDocente) {
        this.objDocente = objdoDocente;
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
