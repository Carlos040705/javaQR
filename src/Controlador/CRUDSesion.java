package Controlador;

import Modelo.CRUD;
import Modelo.ConnectDataBase;
import Modelo.Sesion;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

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

    public ArrayList<Sesion> readByDate(String fecha) {
        ArrayList<Sesion> listaSesiones = new ArrayList<>();
        Statement st = null;
        ResultSet resultado = null;
        try {
            st = objConexion.getConnection().createStatement();
            resultado = st.executeQuery("SELECT * FROM Registro WHERE Fecha = '" + fecha + "'");
            while (resultado.next()) {
                Sesion sesion = new Sesion();
                sesion.setIdRegistro(resultado.getInt("ID_registro"));
                sesion.setFecha(resultado.getDate("Fecha").toString());
                sesion.setHoraEntrada(resultado.getTime("Hora_entrada").toString());
                sesion.setHoraSalida(resultado.getTime("Hora_salida") != null ? resultado.getTime("Hora_salida").toString() : null);
                sesion.setIdUsuario(resultado.getInt("ID_usuario"));
                listaSesiones.add(sesion);
            }
        } catch (SQLException ex) {
            Logger.getLogger(CRUDSesion.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (resultado != null && !resultado.isClosed()) {
                    resultado.close();
                }
                if (st != null && !st.isClosed()) {
                    st.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(CRUDSesion.class.getName()).log(Level.SEVERE, "Error al cerrar recursos", ex);
            }
        }
        return listaSesiones;
    }

    public ArrayList<Sesion> buscarSesionPorCriterios(String textoClave, String textoCriterio, String fecha) {
        ArrayList<Sesion> listaSesiones = new ArrayList<>();
        Statement st = null;
        ResultSet resultado = null;
        try {
            String query = "SELECT r.ID_registro, r.Fecha, r.Hora_entrada, r.Hora_salida, r.ID_usuario, " +
                           "u.Nombre, u.Ap_paterno, u.Ap_materno, " +
                           "a.Matricula, p.Num_plaza " +
                           "FROM Registro r " +
                           "LEFT JOIN Usuario u ON r.ID_usuario = u.ID " +
                           "LEFT JOIN Alumno a ON u.ID = a.ID " +
                           "LEFT JOIN Personal p ON u.ID = p.ID " +
                           "WHERE 1=1";

            if (fecha != null) {
                query += " AND r.Fecha = '" + fecha + "'";
            }
            if (!textoClave.isEmpty()) {
                query += " AND (CAST(r.ID_usuario AS TEXT) LIKE '%" + textoClave + "%' " +
                         "OR a.Matricula LIKE '%" + textoClave + "%' " +
                         "OR p.Num_plaza LIKE '%" + textoClave + "%')";
            }
            if (!textoCriterio.isEmpty()) {
                query += " AND (u.Nombre LIKE '%" + textoCriterio + "%' " +
                         "OR u.Ap_paterno LIKE '%" + textoCriterio + "%' " +
                         "OR u.Ap_materno LIKE '%" + textoCriterio + "%')";
            }

            st = objConexion.getConnection().createStatement();
            resultado = st.executeQuery(query);
            while (resultado.next()) {
                Sesion sesion = new Sesion();
                sesion.setIdRegistro(resultado.getInt("ID_registro"));
                sesion.setFecha(resultado.getDate("Fecha").toString());
                sesion.setHoraEntrada(resultado.getTime("Hora_entrada").toString());
                sesion.setHoraSalida(resultado.getTime("Hora_salida") != null ? resultado.getTime("Hora_salida").toString() : null);
                sesion.setIdUsuario(resultado.getInt("ID_usuario"));
                listaSesiones.add(sesion);
            }
        } catch (SQLException ex) {
            Logger.getLogger(CRUDSesion.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (resultado != null && !resultado.isClosed()) {
                    resultado.close();
                }
                if (st != null && !st.isClosed()) {
                    st.close();
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