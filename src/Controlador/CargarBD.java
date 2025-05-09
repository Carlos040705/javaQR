package Controlador;

import Modelo.ConnectDataBase;
import Modelo.Facultad;
import Modelo.Institucion;
import Modelo.Licenciatura;
import Modelo.Semestre;
import Modelo.Sexo;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CargarBD {

    ConnectDataBase objConexion;

    public CargarBD() {
        objConexion = ConnectDataBase.getInstance(); // Obtiene la instancia única
    }

    public ArrayList<Licenciatura> cargarLicenciaturas() {
        ArrayList<Licenciatura> arregloLicenciaturas = new ArrayList<>();
        Statement st = null;
        ResultSet resultado = null;
        try {
            st = objConexion.getConnection().createStatement();
            resultado = st.executeQuery("SELECT * FROM carrera");
            while (resultado.next()) {
                Licenciatura licenciatura = new Licenciatura();
                licenciatura.setId(resultado.getInt("id_carrera"));
                licenciatura.setNombre(resultado.getString("nombre_carrera"));
                licenciatura.setFacultad(resultado.getInt("id_facultad"));
                arregloLicenciaturas.add(licenciatura);
            }
        } catch (SQLException ex) {
            Logger.getLogger(CargarBD.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (resultado != null && !resultado.isClosed()) {
                    resultado.close(); // Cierra el ResultSet
                }
                if (st != null && !st.isClosed()) {
                    st.close(); // Cierra el Statement
                }
            } catch (SQLException ex) {
                Logger.getLogger(CargarBD.class.getName()).log(Level.SEVERE, "Error al cerrar recursos", ex);
            }
        }
        return arregloLicenciaturas;
    }

    public ArrayList<Facultad> cargarFacultades() {
        ArrayList<Facultad> arregloFacultades = new ArrayList<>();
        Statement st = null;
        ResultSet resultado = null;
        try {
            st = objConexion.getConnection().createStatement();
            resultado = st.executeQuery("SELECT * FROM facultad");
            while (resultado.next()) {
                Facultad facultad = new Facultad();
                facultad.setId(resultado.getInt("id_facultad"));
                facultad.setNombre(resultado.getString("nombre_facultad"));
                arregloFacultades.add(facultad);
            }
        } catch (SQLException ex) {
            Logger.getLogger(CargarBD.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (resultado != null && !resultado.isClosed()) {
                    resultado.close(); // Cierra el ResultSet
                }
                if (st != null && !st.isClosed()) {
                    st.close(); // Cierra el Statement
                }
            } catch (SQLException ex) {
                Logger.getLogger(CargarBD.class.getName()).log(Level.SEVERE, "Error al cerrar recursos", ex);
            }
        }
        return arregloFacultades;
    }

    public ArrayList<Institucion> cargarInstituciones() {
        ArrayList<Institucion> arregloInstituciones = new ArrayList<>();
        Statement st = null;
        ResultSet resultado = null;
        try {
            st = objConexion.getConnection().createStatement();
            resultado = st.executeQuery("SELECT * FROM institucion");
            while (resultado.next()) {
                Institucion institucion = new Institucion();
                institucion.setId(resultado.getInt("id_institucion"));
                institucion.setNombre(resultado.getString("nombre_institucion"));
                arregloInstituciones.add(institucion);
            }
        } catch (SQLException ex) {
            Logger.getLogger(CargarBD.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (resultado != null && !resultado.isClosed()) {
                    resultado.close(); // Cierra el ResultSet
                }
                if (st != null && !st.isClosed()) {
                    st.close(); // Cierra el Statement
                }
            } catch (SQLException ex) {
                Logger.getLogger(CargarBD.class.getName()).log(Level.SEVERE, "Error al cerrar recursos", ex);
            }
        }
        return arregloInstituciones;
    }

    public ArrayList<Sexo> cargarSexos() {
        ArrayList<Sexo> arregloSexo = new ArrayList<>();
        Statement st = null;
        ResultSet resultado = null;
        try {
            st = objConexion.getConnection().createStatement();
            resultado = st.executeQuery("SELECT * FROM sexo");
            while (resultado.next()) {
                Sexo sexo = new Sexo();
                sexo.setId(resultado.getInt("id_sexo"));
                sexo.setNombre(resultado.getString("tipo_sexo"));
                arregloSexo.add(sexo);
            }
        } catch (SQLException ex) {
            Logger.getLogger(CargarBD.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (resultado != null && !resultado.isClosed()) {
                    resultado.close(); // Cierra el ResultSet
                }
                if (st != null && !st.isClosed()) {
                    st.close(); // Cierra el Statement
                }
            } catch (SQLException ex) {
                Logger.getLogger(CargarBD.class.getName()).log(Level.SEVERE, "Error al cerrar recursos", ex);
            }
        }
        return arregloSexo;
    }

    public ArrayList<Semestre> cargarSemestre() {
        ArrayList<Semestre> arregloSemestre = new ArrayList<>();
        Statement st = null;
        ResultSet resultado = null;
        try {
            st = objConexion.getConnection().createStatement();
            resultado = st.executeQuery("SELECT * FROM semestre");
            while (resultado.next()) {
                Semestre semestre = new Semestre();
                semestre.setId(resultado.getInt("id_semestre"));
                semestre.setNombre(resultado.getString("semestre"));
                arregloSemestre.add(semestre);
            }
        } catch (SQLException ex) {
            Logger.getLogger(CargarBD.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (resultado != null && !resultado.isClosed()) {
                    resultado.close(); // Cierra el ResultSet
                }
                if (st != null && !st.isClosed()) {
                    st.close(); // Cierra el Statement
                }
            } catch (SQLException ex) {
                Logger.getLogger(CargarBD.class.getName()).log(Level.SEVERE, "Error al cerrar recursos", ex);
            }
        }
        return arregloSemestre;
    }

    public static void main(String[] args) {
        CargarBD x = new CargarBD();
        for (Semestre semestre : x.cargarSemestre()) {
            System.out.println(semestre.getNombre());
        }
    }
}
