package Controlador;

import Modelo.ConnectDataBase;
import Modelo.Docente;
import Modelo.Persona;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import Modelo.Alumno;
import Modelo.Visitante;
import Modelo.CRUD;
import java.util.ArrayList;

public class CRUDPersona implements CRUD {

    ConnectDataBase objConexion;
    Persona persona;
    CRUDAlumno crudAlumno;
    CRUDVisitante crudVisitante;
    CRUDDocente crudDocente;

    public CRUDPersona() {
        objConexion = ConnectDataBase.getInstance(); // Obtiene la instancia única
        crudAlumno = new CRUDAlumno();
        crudDocente = new CRUDDocente();
        crudVisitante = new CRUDVisitante();
    }

    @Override
    public void create() {
        Statement st = null;
        try {
            st = objConexion.getConnection().createStatement();
            st.execute("INSERT INTO usuario VALUES (" + persona.getId() + ", '"
                    + persona.getNombre() + "', '" + persona.getApPaterno() + "', '"
                    + persona.getApMaterno() + "', " + persona.getAñoNacimiento()
                    + ", " + persona.getSexo() + ")");
        } catch (SQLException ex) {
            Logger.getLogger(CRUDPersona.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (st != null && !st.isClosed()) {
                    st.close(); // Cierra el Statement
                }
            } catch (SQLException ex) {
                Logger.getLogger(CRUDPersona.class.getName()).log(Level.SEVERE, "Error al cerrar el Statement", ex);
            }
        }

        // Manejo de subtipos de Persona
        if (persona instanceof Alumno) {
            Alumno alumno = (Alumno) persona;
            crudAlumno.setAlumno(alumno);
            crudAlumno.create();
        } else if (persona instanceof Docente) {
            Docente docente = (Docente) persona;
            crudDocente.setDocente(docente);
            crudDocente.create();
        } else if (persona instanceof Visitante) {
            Visitante visitante = (Visitante) persona;
            crudVisitante.setVisitante(visitante);
            crudVisitante.create();
        }
    }

    @Override
    public ArrayList<Persona> read() {
        ArrayList<Persona> personas = new ArrayList<>();
        personas.addAll(crudAlumno.read());
        personas.addAll(crudDocente.read());
        personas.addAll(crudVisitante.read());
        asignarDatos(personas);
        return personas;
    }

    @Override
    public void update() {
        Statement st = null;
        try {
            st = objConexion.getConnection().createStatement();
            String query = "UPDATE usuario SET nombre = '" + persona.getNombre()
                    + "', ap_paterno = '" + persona.getApPaterno()
                    + "', ap_materno = '" + persona.getApMaterno()
                    + "', a_nacimiento = " + persona.getAñoNacimiento()
                    + ", sexo= " + persona.getSexo()
                    + " WHERE id = " + persona.getId();
            st.executeUpdate(query);
        } catch (SQLException ex) {
            Logger.getLogger(CRUDPersona.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (st != null && !st.isClosed()) {
                    st.close(); // Cierra el Statement
                }
            } catch (SQLException ex) {
                Logger.getLogger(CRUDPersona.class.getName()).log(Level.SEVERE, "Error al cerrar el Statement", ex);
            }
            
        }

        // Manejo de subtipos de Persona
        if (persona instanceof Alumno) {
            Alumno alumno = (Alumno) persona;
            crudAlumno.setAlumno(alumno);
            crudAlumno.update();
        } else if (persona instanceof Docente) {
            Docente docente = (Docente) persona;
            crudDocente.setDocente(docente);
            crudDocente.update();
        } else if (persona instanceof Visitante) {
            Visitante visitante = (Visitante) persona;
            crudVisitante.setVisitante(visitante);
            crudVisitante.update();
        }
    }

    @Override
    public void delete() {
        Statement st = null;
        try {
            st = objConexion.getConnection().createStatement();
            st.execute("DELETE FROM usuario WHERE id = " + persona.getId());
        } catch (SQLException ex) {
            Logger.getLogger(CRUDPersona.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (st != null && !st.isClosed()) {
                    st.close(); // Cierra el Statement
                }
            } catch (SQLException ex) {
                Logger.getLogger(CRUDPersona.class.getName()).log(Level.SEVERE, "Error al cerrar el Statement", ex);
            }
        }
    }

    public void setPersona(Persona persona) {
        this.persona = persona;
    }

    public void asignarDatos(ArrayList<Persona> personas) {
        Statement st = null;
        ResultSet resultado = null;
        try {
            st = objConexion.getConnection().createStatement();
            for (Persona persona : personas) {
                String query = "SELECT nombre, ap_paterno, ap_materno, "
                        + "EXTRACT(YEAR FROM CURRENT_DATE) - a_nacimiento AS edad, sexo FROM usuario WHERE id = " + persona.getId();
                resultado = st.executeQuery(query);
                while (resultado.next()) {
                    persona.setNombre(resultado.getString("nombre"));
                    persona.setApPaterno(resultado.getString("ap_paterno"));
                    persona.setApMaterno(resultado.getString("ap_materno"));
                    persona.setAñoNacimiento(resultado.getInt("edad"));
                    persona.setSexo(resultado.getInt("sexo"));
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(CRUDPersona.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (resultado != null && !resultado.isClosed()) {
                    resultado.close(); // Cierra el ResultSet
                }
                if (st != null && !st.isClosed()) {
                    st.close(); // Cierra el Statement
                }
            } catch (SQLException ex) {
                Logger.getLogger(CRUDPersona.class.getName()).log(Level.SEVERE, "Error al cerrar recursos", ex);
            }
        }
    }

    public int obtenerMaxID() {
        Statement st = null;
        ResultSet resultado = null;
        int id = 0;
        try {
            st = objConexion.getConnection().createStatement();
            resultado = st.executeQuery("SELECT MAX(id) FROM usuario");
            if (resultado.next()) {
                id = resultado.getInt("max");
            }
        } catch (SQLException ex) {
            Logger.getLogger(CRUDPersona.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (resultado != null && !resultado.isClosed()) {
                    resultado.close(); // Cierra el ResultSet
                }
                if (st != null && !st.isClosed()) {
                    st.close(); // Cierra el Statement
                }
            } catch (SQLException ex) {
                Logger.getLogger(CRUDPersona.class.getName()).log(Level.SEVERE, "Error al cerrar recursos", ex);
            }
        }
        return id + 1;
    }
}