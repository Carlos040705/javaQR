package Controlador;

import Vista.AltaAlumno;
import Modelo.Alumno;
import Modelo.Licenciatura;
import Modelo.Facultad;
import Modelo.Semestre;
import Modelo.Sexo;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import javax.swing.JLabel;

public class ControladorAltaAlumno implements ActionListener, KeyListener {

    private Alumno objAlumno;
    private AltaAlumno altaAlumno;
    private CRUDPersona CRUDPersona;
    private Verificador verificador;
    private ObtenerComponentes obtenerComponentes;
    private CargarBD cargarBD;
    private ManejoBD manejo;
    private BusquedaSQL busquedaSQL;
    private boolean valMatricula;
    private JLabel etiquetaConfirmacion;

    public ControladorAltaAlumno(AltaAlumno altaAlumno) {
        this.altaAlumno = altaAlumno;
        initObj();
        addAL();
        manejo.actualizaFacultades(altaAlumno.comboFacultad1);
        manejo.subirAño(altaAlumno.comboAño);
        manejo.actualizarSexo(altaAlumno.comboSexo);
        manejo.actualizarSemestres(altaAlumno.comboSemestre);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(altaAlumno.guardarAlumno)) {
            guardarAlumno();
        } else if (e.getSource().equals(altaAlumno.comboFacultad1)) {
            if (altaAlumno.comboFacultad1.getSelectedIndex() == 0) {
                altaAlumno.comboLicenciatura.removeAllItems();
                altaAlumno.comboLicenciatura.addItem("Seleccionar");
            } else {
                actualizarLicenciaturas();
            }
        }
    }

    private void guardarAlumno() {
        if (verificador.verificarCajaVacia(altaAlumno) && verificador.verificarComboBox(altaAlumno)) {
            objAlumno.setId(CRUDPersona.obtenerMaxID());
            objAlumno.setNombre(altaAlumno.cajaNombre.getText());
            objAlumno.setApPaterno(altaAlumno.cajaApPaterno.getText());
            objAlumno.setApMaterno(altaAlumno.cajaApMaterno.getText());
            objAlumno.setLicenciatura(((Licenciatura) altaAlumno.comboLicenciatura.getSelectedItem()).getId());
            objAlumno.setMatricula(altaAlumno.cajaMatricula.getText());
            objAlumno.setAñoNacimiento(Integer.parseInt((String) altaAlumno.comboAño.getSelectedItem()));
            objAlumno.setSexo(altaAlumno.comboSexo.getSelectedIndex() != 0 ? ((Sexo) altaAlumno.comboSexo.getSelectedItem()).getId() : 3);
            objAlumno.setSemestre(((Semestre) altaAlumno.comboSemestre.getSelectedItem()).getId());
            verificador.limpiarDatos(altaAlumno);
            verificador.LimpiarComboBox(altaAlumno);
            CRUDPersona.setPersona(objAlumno);
            CRUDPersona.create();
            etiquetaConfirmacion.setText("<html><center>El usuario: " + objAlumno.getNombre()
                    + " " + objAlumno.getApPaterno() + "<p> se ha unido con exito al sistema");
            valMatricula = false;
        } else {

        }
    }

    private void actualizarLicenciaturas() {
        Facultad facultad = (Facultad) altaAlumno.comboFacultad1.getSelectedItem();
        var item = (String) altaAlumno.comboLicenciatura.getItemAt(0);
        altaAlumno.comboLicenciatura.removeAllItems();
        altaAlumno.comboLicenciatura.addItem(item);
        int seleccion = facultad.getId();
        altaAlumno.comboLicenciatura.setEnabled(true);
        altaAlumno.comboLicenciatura.setFocusable(true);
        for (Licenciatura licenciatura : (ArrayList<Licenciatura>) cargarBD.cargarLicenciaturas()) {
            if (licenciatura.getFacultad() == seleccion) {
                altaAlumno.comboLicenciatura.addItem(licenciatura);
            }
        }
        System.out.println(altaAlumno.comboLicenciatura.getItemCount());
        if (altaAlumno.comboLicenciatura.getItemCount() == 1) {
            altaAlumno.comboLicenciatura.setEnabled(false);
            altaAlumno.comboLicenciatura.setFocusable(false);
        }
    }

    public void initObj() {
        this.objAlumno = new Alumno();
        this.cargarBD = new CargarBD();
        manejo = new ManejoBD();
        obtenerComponentes = new ObtenerComponentes();
        CRUDPersona = new CRUDPersona();
        verificador = new Verificador();
        busquedaSQL = new BusquedaSQL();
        valMatricula = false;
    }

    private void addAL() {
        altaAlumno.guardarAlumno.addActionListener(this);
        altaAlumno.comboFacultad1.addActionListener(this);
        altaAlumno.cajaMatricula.addKeyListener(this);
    }

    public void setConfirmacion(JLabel etiquetaConfirmacion) {
        this.etiquetaConfirmacion = etiquetaConfirmacion;
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getSource().equals(altaAlumno.cajaMatricula)) {
            String validacion = verificador.validarMatricula(altaAlumno.cajaMatricula.getText());
            if (validacion != null) {
                altaAlumno.etiquetaValidacion.setForeground(Color.red);
                altaAlumno.etiquetaValidacion.setText(validacion);
                valMatricula = false;
            } else {
                Alumno alumno = busquedaSQL.buscarAlumno(altaAlumno.cajaMatricula.getText());
                if (alumno != null) {
                    altaAlumno.etiquetaValidacion.setForeground(Color.red);
                    altaAlumno.etiquetaValidacion.setText("<html><center>Esta matrícula ya se encuentra registrada");
                    valMatricula = false;
                } else {
                    altaAlumno.etiquetaValidacion.setForeground(Color.green);
                    altaAlumno.etiquetaValidacion.setText("<html><center>Matrícula válida");
                    valMatricula = true;
                }
            }
        }
    }
}
