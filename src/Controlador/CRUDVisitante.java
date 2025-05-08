package Controlador;

import Modelo.CRUD;
import Modelo.ConnectDataBase;
import Modelo.Visitante;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CRUDVisitante implements CRUD {

    ConnectDataBase objConexion;
    Visitante visitante;

    public CRUDVisitante() {
        objConexion = ConnectDataBase.getInstance(); // Obtiene la instancia única
    }

    @Override
    public void create() {
        System.out.println("Insertando Visitante");
        Statement st = null;
        try {
            st = objConexion.getConnection().createStatement();
            st.execute("INSERT INTO visitante VALUES(" + visitante.getId() + ", "
                    + visitante.getInstitucion() + ")");
        } catch (SQLException ex) {
            Logger.getLogger(CRUDVisitante.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (st != null && !st.isClosed()) {
                    st.close(); // Cierra el Statement
                }
            } catch (SQLException ex) {
                Logger.getLogger(CRUDVisitante.class.getName()).log(Level.SEVERE, "Error al cerrar el Statement", ex);
            }
        }
    }

    @Override
    public ArrayList<Visitante> read() {
        ArrayList<Visitante> arregloVisitante = new ArrayList<>();
        Statement st = null;
        ResultSet resultado = null;
        try {
            st = objConexion.getConnection().createStatement();
            resultado = st.executeQuery("SELECT * FROM visitante");
            while (resultado.next()) {
                Visitante visitante = new Visitante();
                visitante.setId(resultado.getInt("id"));
                visitante.setInstitucion(resultado.getInt("id_escuela"));
                visitante.setTipo("Visitante");
                arregloVisitante.add(visitante);
            }
        } catch (SQLException ex) {
            Logger.getLogger(CRUDVisitante.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (resultado != null && !resultado.isClosed()) {
                    resultado.close(); // Cierra el ResultSet
                }
                if (st != null && !st.isClosed()) {
                    st.close(); // Cierra el Statement
                }
            } catch (SQLException ex) {
                Logger.getLogger(CRUDVisitante.class.getName()).log(Level.SEVERE, "Error al cerrar recursos", ex);
            }
        }
        return arregloVisitante;
    }

    @Override
    public void update() {
        Statement st = null;
        try {
            st = objConexion.getConnection().createStatement();
            st.execute("UPDATE visitante SET id_escuela = " + visitante.getInstitucion()
                    + " WHERE id = " + visitante.getId());
        } catch (SQLException ex) {
            Logger.getLogger(CRUDVisitante.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (st != null && !st.isClosed()) {
                    st.close(); // Cierra el Statement
                }
            } catch (SQLException ex) {
                Logger.getLogger(CRUDVisitante.class.getName()).log(Level.SEVERE, "Error al cerrar el Statement", ex);
            }
        }
    }

    @Override
    public void delete() {
    }

    public void setVisitante(Visitante visitante) {
        this.visitante = visitante;
    }

    public static void main(String[] args) {
        CRUDVisitante x = new CRUDVisitante();
        for (Visitante vis : x.read()) {
            System.out.println(vis.getNombre());
        }
    }
}