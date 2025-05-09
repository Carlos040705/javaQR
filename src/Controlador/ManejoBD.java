package Controlador;

import Modelo.Facultad;
import Modelo.Institucion;
import Modelo.Licenciatura;
import Modelo.Semestre;
import Modelo.Sexo;
import java.util.ArrayList;
import javax.swing.JComboBox;

public class ManejoBD {

    private CargarBD carga;

    public ManejoBD() {
        carga = new CargarBD();
    }

    public ManejoBD(CargarBD carga) {
        this.carga = carga;
    }

    public void actualizaFacultades(JComboBox combo) {
        for (Facultad facultad : (ArrayList<Facultad>) carga.cargarFacultades()) {
            combo.addItem(facultad);
            System.out.println(facultad.getNombre());
        }
    }

    public void actualizaLicenciaturas(JComboBox combo) {
        for (Licenciatura licenciatura : (ArrayList<Licenciatura>) carga.cargarLicenciaturas()) {
            combo.addItem(licenciatura);
        }
    }

    public void actualizaInstituciones(JComboBox combo) {
        for (Institucion institucion : (ArrayList<Institucion>) carga.cargarInstituciones()) {
            combo.addItem(institucion);
            System.out.println(institucion.getNombre());
        }
    }

    public void actualizarSexo(JComboBox combo) {
        for (Sexo sexo : (ArrayList<Sexo>) carga.cargarSexos()) {
            combo.addItem(sexo);
        }
    }

    public void actualizarSemestres(JComboBox combo) {
        for (Semestre object : (ArrayList<Semestre>) carga.cargarSemestre()) {
            combo.addItem(object);
        }
    }

    public Facultad busquedaFacultad(int id) {
        Facultad facultad = new Facultad();
        for (Facultad facu : (ArrayList<Facultad>) carga.cargarFacultades()) {
            if (facu.getId() == id) {
                facultad = facu;
                return facultad;
            }
        }
        return facultad;
    }

    public Licenciatura buscarLicenciatura(int id) {
        Licenciatura licenciatura = new Licenciatura();
        for (Licenciatura lic : (ArrayList<Licenciatura>) carga.cargarLicenciaturas()) {
            if (lic.getId() == id) {
                licenciatura = lic;
                return licenciatura;
            }
        }
        return licenciatura;
    }

    public Institucion buscarInstitucion(int id) {
        Institucion institucion = new Institucion();
        for (Institucion ins : (ArrayList<Institucion>) carga.cargarInstituciones()) {
            if (ins.getId() == id) {
                institucion = ins;
                return institucion;
            }
        }
        return institucion;
    }

    public Sexo buscarSex0(int id) {
        Sexo sexo = null;
        for (Sexo sexito : (ArrayList<Sexo>) carga.cargarSexos()) {
            if (sexito.getId() == id) {
                sexo = sexito;
                return sexo;
            }
        }
        return sexo;
    }

    public Semestre buscarSemestre(int id) {
        Semestre sexo = null;
        for (Semestre sexito : (ArrayList<Semestre>) carga.cargarSemestre()) {
            if (sexito.getId() == id) {
                sexo = sexito;
                return sexo;
            }
        }
        return sexo;
    }

    public void subirAño(JComboBox combo) {
        for (int i = 2010; i > 1930; i--) {
            combo.addItem(String.valueOf(i));
        }
    }

    public void setCarga(CargarBD carga) {
        this.carga = carga;
    }

    public static void main(String[] args) {
        ManejoBD x = new ManejoBD();
    }

}
