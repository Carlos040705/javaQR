package Controlador;

import Vista.AltaDocente;
import Modelo.Docente;
import Modelo.Sexo;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class ControladorAltaDocente implements ActionListener, KeyListener {

    private Docente objDocente;
    private AltaDocente altaDocente;
    private CRUDPersona CRUDPersona;
    private Verificador verificador;
    private ObtenerComponentes obtenerComponentes;
    private ManejoBD manejo;
    private boolean validaPlaza;
    private BusquedaSQL busquedaSQL;

    public ControladorAltaDocente(AltaDocente altaDocente) {
        this.altaDocente = altaDocente;
        validaPlaza = false;
        initObj();
        addAL();

        if (altaDocente.comboAño != null) {
            manejo.subirAño(altaDocente.comboAño);
        } else {
            System.out.println("El problema esta en docente");
        }
        manejo.actualizarSexo(altaDocente.comboSexo);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        obtenerComponentes = new ObtenerComponentes();
        CRUDPersona = new CRUDPersona();
        verificador = new Verificador();

        if (e.getSource().equals(altaDocente.guardarDocente)) {
            guardarDocente();
        }
    }

    private void guardarDocente() {
        if (verificador.verificarCajaVacia(altaDocente) && verificador.verificarComboBox(altaDocente)) {
            objDocente.setId(CRUDPersona.obtenerMaxID());
            objDocente.setNombre(altaDocente.cajaNombre.getText());
            objDocente.setApPaterno(altaDocente.cajaApPaterno.getText());
            objDocente.setApMaterno(altaDocente.cajaApMaterno.getText());
            objDocente.setNoDePlaza(altaDocente.cajaNoPlaza.getText());
            objDocente.setAñoNacimiento(Integer.parseInt(((String) altaDocente.comboAño.getSelectedItem())));
            objDocente.setSexo(altaDocente.comboSexo.getSelectedIndex() != 0 ? ((Sexo) altaDocente.comboSexo.getSelectedItem()).getId() : 1);
            verificador.limpiarDatos(altaDocente);
            verificador.LimpiarComboBox(altaDocente);
            CRUDPersona.setPersona(objDocente);
            CRUDPersona.create();
            validaPlaza = false;
        }
    }

    public void initObj() {
        this.objDocente = new Docente();
        this.CRUDPersona = new CRUDPersona();
        this.verificador = new Verificador();
        this.obtenerComponentes = new ObtenerComponentes();
        manejo = new ManejoBD();
        busquedaSQL = new BusquedaSQL();
    }

    private void addAL() {
        altaDocente.guardarDocente.addActionListener(this);
        altaDocente.cajaNoPlaza.addKeyListener(this);
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getSource().equals(altaDocente.cajaNoPlaza)) {
            String validacion = verificador.validarNoPlaza(altaDocente.cajaNoPlaza.getText());
            if (validacion != null) {
                altaDocente.etiquetaValidacion.setForeground(Color.red);
                altaDocente.etiquetaValidacion.setText(validacion);
                validaPlaza = false;
            } else {
                Docente docente = busquedaSQL.buscarDocente(altaDocente.cajaNoPlaza.getText());
                if (docente != null) {
                    altaDocente.etiquetaValidacion.setForeground(Color.red);
                    altaDocente.etiquetaValidacion.setText("<html><center>Este No. de plaza ya se encuentra registrado");
                    validaPlaza = false;
                } else {
                    altaDocente.etiquetaValidacion.setForeground(Color.green);
                    altaDocente.etiquetaValidacion.setText("<html><center>No. de plaza válido");
                    validaPlaza = true;
                }
            }
        }
    }
}
