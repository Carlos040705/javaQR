package Controlador;

import Modelo.Alumno;
import Modelo.CRUD;
import Modelo.ConnectDataBase;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author varga
 */
public class CRUDAlumno implements CRUD {

    ConnectDataBase objConexion;
    Alumno alumno;

    public CRUDAlumno() {
        objConexion = ConnectDataBase.getInstance(); // Obtiene la instancia única
    }

    @Override
    public void create() {
        System.out.println("Insertando Alumno");
        Statement st = null;
        try {
            st = objConexion.getConnection().createStatement();
            st.execute("INSERT INTO alumno VALUES(" + alumno.getId() + ", '"
                    + alumno.getMatricula() + "', " + alumno.getLicenciatura() + "," + alumno.getSemestre() + ")");
        } catch (SQLException ex) {
            Logger.getLogger(CRUDAlumno.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (st != null && !st.isClosed()) {
                    st.close(); // Cierra el Statement
                }
            } catch (SQLException ex) {
                Logger.getLogger(CRUDAlumno.class.getName()).log(Level.SEVERE, "Error al cerrar el Statement", ex);
            }
        }
    }

    @Override
    public ArrayList<Alumno> read() {
        ArrayList<Alumno> arregloAlumno = new ArrayList<>();
        Statement st = null;
        ResultSet resultado = null;
        try {
            st = objConexion.getConnection().createStatement();
            resultado = st.executeQuery("SELECT * FROM alumno");

            while (resultado.next()) {
                Alumno alumno = new Alumno();
                alumno.setId(resultado.getInt("id"));
                alumno.setMatricula(resultado.getString("matricula"));
                alumno.setLicenciatura(resultado.getInt("id_carrera"));
                alumno.setSemestre(resultado.getInt("semestre"));
                alumno.setTipo("Alumno");
                arregloAlumno.add(alumno);
            }
        } catch (SQLException ex) {
            Logger.getLogger(CRUDAlumno.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (resultado != null && !resultado.isClosed()) {
                    resultado.close(); // Cierra el ResultSet
                }
                if (st != null && !st.isClosed()) {
                    st.close(); // Cierra el Statement
                }
            } catch (SQLException ex) {
                Logger.getLogger(CRUDAlumno.class.getName()).log(Level.SEVERE, "Error al cerrar recursos", ex);
            }
        }
        return arregloAlumno;
    }

    @Override
    public void update() {
        Statement st = null;
        try {
            st = objConexion.getConnection().createStatement();
            st.execute("UPDATE alumno SET matricula = '" + alumno.getMatricula() + "', id_carrera = "
                    + alumno.getLicenciatura() + ", semestre = " + alumno.getSemestre()
                    + " WHERE id = " + alumno.getId());
        } catch (SQLException ex) {
            Logger.getLogger(CRUDAlumno.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (st != null && !st.isClosed()) {
                    st.close(); // Cierra el Statement
                }
            } catch (SQLException ex) {
                Logger.getLogger(CRUDAlumno.class.getName()).log(Level.SEVERE, "Error al cerrar el Statement", ex);
            }
        }
    }

    @Override
    public void delete() {

    }

    public void setAlumno(Alumno alumno) {
        this.alumno = alumno;
    }
}
