package Controlador;

import Vista.AltaVisitante;
import Modelo.Visitante;
import Modelo.Institucion;
import Modelo.Sexo;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ControladorAltaVisitante implements ActionListener {

    private Visitante objVisitante;
    private AltaVisitante altaVisitante;
    private CRUDPersona CRUDPersona;
    private Verificador verificador;
    private ManejoBD manejo;

    public ControladorAltaVisitante(AltaVisitante altaVisitante) {
        this.altaVisitante = altaVisitante;
        initObj();
        addAL();
        manejo.actualizaInstituciones(altaVisitante.comboInstitucion);
        manejo.actualizarSexo(altaVisitante.comboSexo);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(altaVisitante.guardarVisitante)) {
            guardarVisitante();
        } else if (e.getSource().equals(altaVisitante.comboInstitucion)) {
            //actualizarInstituciones();
        }
    }

    private void guardarVisitante() {
        if (verificador.verificarCajaVacia(altaVisitante) && verificador.verificarComboBox(altaVisitante)) {

            objVisitante.setId(CRUDPersona.obtenerMaxID());
            objVisitante.setNombre(altaVisitante.cajaNombre.getText());
            objVisitante.setApPaterno(altaVisitante.cajaApPaterno.getText());
            objVisitante.setApMaterno(altaVisitante.cajaApMaterno.getText());
            objVisitante.setInstitucion(((Institucion) altaVisitante.comboInstitucion.getSelectedItem()).getId());
            objVisitante.setAñoNacimiento(Integer.parseInt(((String) altaVisitante.comboAño.getSelectedItem())));
            objVisitante.setSexo(altaVisitante.comboSexo.getSelectedIndex() != 0 ? ((Sexo) altaVisitante.comboSexo.getSelectedItem()).getId() : 3);
            verificador.limpiarDatos(altaVisitante);
            verificador.LimpiarComboBox(altaVisitante);
            CRUDPersona.setPersona(objVisitante);
            CRUDPersona.create();
        }
    }

    private void actualizarInstituciones() {
        Institucion institucion = (Institucion) altaVisitante.comboInstitucion.getSelectedItem();
        altaVisitante.comboInstitucion.setEnabled(institucion != null);
    }

    private void initObj() {
        this.objVisitante = new Visitante();
        this.CRUDPersona = new CRUDPersona();
        this.verificador = new Verificador();
        manejo = new ManejoBD();
    }

    private void addAL() {
        altaVisitante.guardarVisitante.addActionListener(this);
        altaVisitante.comboInstitucion.addActionListener(this);
    }
}
