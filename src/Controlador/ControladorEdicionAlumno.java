/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Modelo.Alumno;
import Modelo.Facultad;
import Modelo.Licenciatura;
import Modelo.Sexo;
import Vista.EdicionAlumno;
import Vista.PantallaPrincipal;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;

public class ControladorEdicionAlumno implements ActionListener, WindowListener {
    
    private Alumno objAlumno;
    private EdicionAlumno edicion;
    private CRUDPersona crudPersona;
    private CargarBD carga;
    PantallaPrincipal pantallaPrincipal;
    
    public ControladorEdicionAlumno(EdicionAlumno edicion) {
        this.edicion = edicion;
        crudPersona = new CRUDPersona();
        carga = new CargarBD();
        addAL();
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(edicion.guardarAlumno)) {
            objAlumno.setNombre(edicion.cajaNombre.getText());
            objAlumno.setApPaterno(edicion.cajaApPaterno.getText());
            objAlumno.setApMaterno(edicion.cajaApMaterno.getText());
            objAlumno.setLicenciatura(((Licenciatura) edicion.comboLicenciatura.getSelectedItem()).getId());
            objAlumno.setMatricula(edicion.cajaMatricula.getText());
            objAlumno.setAñoNacimiento(Integer.parseInt((String) edicion.comboAño.getSelectedItem()));
            objAlumno.setSexo(((Sexo) edicion.comboSexo.getSelectedItem()).getId());
            crudPersona.setPersona(objAlumno);
            crudPersona.update();
            edicion.dispose();
        } else if (e.getSource().equals(edicion.comboFacultad1)) {
            Facultad facultad = (Facultad) edicion.comboFacultad1.getSelectedItem();
            var item = (String) edicion.comboLicenciatura.getItemAt(0);
            edicion.comboLicenciatura.removeAllItems();
            edicion.comboLicenciatura.addItem(item);
            int seleccion = facultad.getId();
            edicion.comboLicenciatura.setEnabled(true);
            edicion.comboLicenciatura.setFocusable(true);
            for (Licenciatura licenciatura : (ArrayList<Licenciatura>) carga.cargarLicenciaturas()) {
                if (licenciatura.getFacultad() == seleccion) {
                    edicion.comboLicenciatura.addItem(licenciatura);
                }
            }
            System.out.println(edicion.comboLicenciatura.getItemCount());
            if (edicion.comboLicenciatura.getItemCount() == 1) {
                edicion.comboLicenciatura.setEnabled(false);
                edicion.comboLicenciatura.setFocusable(false);
            }
        }
    }
    
    private void addAL() {
        edicion.comboFacultad1.addActionListener(this);
        edicion.guardarAlumno.addActionListener(this);
        edicion.addWindowListener(this);
    }
    
    public void setObjAlumno(Alumno objAlumno) {
        this.objAlumno = objAlumno;
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
