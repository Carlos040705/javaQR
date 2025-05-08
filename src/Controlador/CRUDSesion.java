package Controlador;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.SQLException;

import Modelo.CRUD;
import Modelo.ConnectDataBase;
import Modelo.Sesion;

public class CRUDSesion implements CRUD {

    private ConnectDataBase objConexion;
    private Sesion sesion;

    public CRUDSesion() {
        objConexion = ConnectDataBase.getInstance(); // Obtiene la instancia única
    }

    @Override
    public void create() {
        Statement st = null;
        try {
            st = objConexion.getConnection().createStatement();
            st.execute("INSERT INTO Registro (ID_usuario) VALUES (" + sesion.getIdUsuario() + ")");
        } catch (SQLException ex) {
            Logger.getLogger(CRUDSesion.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (st != null && !st.isClosed()) {
                    st.close(); // Cierra el Statement
                }
            } catch (SQLException ex) {
                Logger.getLogger(CRUDSesion.class.getName()).log(Level.SEVERE, "Error al cerrar el Statement", ex);
            }
        }
    }

    @Override
    public ArrayList<Sesion> read() {
        ArrayList<Sesion> listaSesiones = new ArrayList<>();
        Statement st = null;
        ResultSet resultado = null;
        try {
            st = objConexion.getConnection().createStatement();
            resultado = st.executeQuery("SELECT * FROM Registro");
            while (resultado.next()) {
                Sesion sesion = new Sesion();
                sesion.setIdRegistro(resultado.getInt("ID_registro"));
                sesion.setFecha(resultado.getDate("Fecha").toString());
                sesion.setHoraEntrada(resultado.getTime("Hora_entrada").toString());
                if (resultado.getTime("Hora_salida") == null) {
                    sesion.setHoraSalida(null);
                } else {
                    sesion.setHoraSalida(resultado.getTime("Hora_salida").toString());
                }
                sesion.setIdUsuario(resultado.getInt("ID_usuario"));
                listaSesiones.add(sesion);
            }
        } catch (SQLException ex) {
            Logger.getLogger(CRUDSesion.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (resultado != null && !resultado.isClosed()) {
                    resultado.close(); // Cierra el ResultSet
                }
                if (st != null && !st.isClosed()) {
                    st.close(); // Cierra el Statement
                }
            } catch (SQLException ex) {
                Logger.getLogger(CRUDSesion.class.getName()).log(Level.SEVERE, "Error al cerrar recursos", ex);
            }
        }
        return listaSesiones;
    }

    @Override
    public void update() {
        Statement st = null;
        try {
            st = objConexion.getConnection().createStatement();
            st.executeUpdate(
                    "UPDATE Registro SET Hora_salida = CURRENT_TIME WHERE ID_registro = " + sesion.getIdRegistro());
        } catch (SQLException ex) {
            Logger.getLogger(CRUDSesion.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (st != null && !st.isClosed()) {
                    st.close(); // Cierra el Statement
                }
            } catch (SQLException ex) {
                Logger.getLogger(CRUDSesion.class.getName()).log(Level.SEVERE, "Error al cerrar el Statement", ex);
            }
        }
    }

    @Override
    public void delete() {
        Statement st = null;
        try {
            st = objConexion.getConnection().createStatement();
            st.execute("DELETE FROM Registro WHERE ID_registro = " + sesion.getIdRegistro());
        } catch (SQLException ex) {
            Logger.getLogger(CRUDSesion.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (st != null && !st.isClosed()) {
                    st.close(); // Cierra el Statement
                }
            } catch (SQLException ex) {
                Logger.getLogger(CRUDSesion.class.getName()).log(Level.SEVERE, "Error al cerrar el Statement", ex);
            }
        }
    }

    public int obtenerMaxID() {
        Statement st = null;
        ResultSet resultado = null;
        int id = 0;
        try {
            st = objConexion.getConnection().createStatement();
            resultado = st.executeQuery("SELECT MAX(ID_registro) FROM Registro");
            if (resultado.next()) {
                id = resultado.getInt(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(CRUDSesion.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (resultado != null && !resultado.isClosed()) {
                    resultado.close(); // Cierra el ResultSet
                }
                if (st != null && !st.isClosed()) {
                    st.close(); // Cierra el Statement
                }
            } catch (SQLException ex) {
                Logger.getLogger(CRUDSesion.class.getName()).log(Level.SEVERE, "Error al cerrar recursos", ex);
            }
        }
        return id + 1;
    }

    public void setSesion(Sesion sesion) {
        this.sesion = sesion;
    }
}
