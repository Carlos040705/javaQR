package Controlador;

import Modelo.CRUD;
import Modelo.ConnectDataBase;
import Modelo.Docente;
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
public class CRUDDocente implements CRUD {

    ConnectDataBase objConexion;
    Docente docente;

    public CRUDDocente() {
        objConexion = ConnectDataBase.getInstance(); // Obtiene la instancia única
    }

    @Override
    public void create() {
        System.out.println("Insertando Docente");
        Statement st = null;
        try {
            st = objConexion.getConnection().createStatement();
            st.execute("INSERT INTO personal VALUES(" + docente.getId() + ", '"
                    + docente.getNoDePlaza() + "')");
        } catch (SQLException ex) {
            Logger.getLogger(CRUDDocente.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (st != null && !st.isClosed()) {
                    st.close(); // Cierra el Statement
                }
            } catch (SQLException ex) {
                Logger.getLogger(CRUDDocente.class.getName()).log(Level.SEVERE, "Error al cerrar el Statement", ex);
            }
            
        }
    }

    @Override
    public ArrayList<Docente> read() {
        ArrayList<Docente> arregloDocente = new ArrayList<>();
        Statement st = null;
        ResultSet resultado = null;
        try {
            st = objConexion.getConnection().createStatement();
            resultado = st.executeQuery("SELECT * FROM personal");
            while (resultado.next()) {
                Docente docente = new Docente();
                docente.setId(resultado.getInt("id"));
                docente.setNoDePlaza(resultado.getString("num_plaza"));
                docente.setTipo("Docente");
                arregloDocente.add(docente);
            }
        } catch (SQLException ex) {
            Logger.getLogger(CRUDDocente.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (resultado != null && !resultado.isClosed()) {
                    resultado.close(); // Cierra el ResultSet
                }
                if (st != null && !st.isClosed()) {
                    st.close(); // Cierra el Statement
                }
            } catch (SQLException ex) {
                Logger.getLogger(CRUDDocente.class.getName()).log(Level.SEVERE, "Error al cerrar recursos", ex);
            }
        }
        return arregloDocente;
    }

    @Override
    public void update() {
        Statement st = null;
        try {
            st = objConexion.getConnection().createStatement();
            st.execute("UPDATE personal SET num_plaza = '" + docente.getNoDePlaza()
                    + "' WHERE id = " + docente.getId());
        } catch (SQLException ex) {
            Logger.getLogger(CRUDDocente.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (st != null && !st.isClosed()) {
                    st.close(); // Cierra el Statement
                }
            } catch (SQLException ex) {
                Logger.getLogger(CRUDDocente.class.getName()).log(Level.SEVERE, "Error al cerrar el Statement", ex);
            }
        }
    }

    @Override
    public void delete() {
    }

    public void setDocente(Docente docente) {
        this.docente = docente;
    }
}