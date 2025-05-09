package Controlador;

import Modelo.Alumno;
import Modelo.ConnectDataBase;
import Modelo.Docente;
import Modelo.Persona;
import Modelo.Visitante;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.time.Year;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;

public class BusquedaSQL {

    private CRUDPersona crudPersona;
    private ManejoBD manejoBD;
    private ConnectDataBase objConexion;
    private Verificador verificador;

    public BusquedaSQL() {
        crudPersona = new CRUDPersona();
        manejoBD = new ManejoBD();
        objConexion = ConnectDataBase.getInstance();
        verificador = new Verificador();
    }

    public Alumno buscarAlumno(String matricula) {
        Alumno alumnoEncontrado = null;
        String sql = """
        SELECT Usuario.ID, Usuario.Nombre, Usuario.Ap_paterno, Usuario.Ap_materno, Usuario.A_nacimiento, Usuario.Sexo, Alumno.Matricula, Alumno.ID_carrera, Alumno.Semestre
        FROM Usuario
        JOIN Sexo ON Usuario.Sexo = Sexo.ID_sexo
        JOIN Alumno ON Usuario.ID = Alumno.ID
        WHERE Alumno.Matricula = ?;
    """;

        try (PreparedStatement st = objConexion.getConnection().prepareStatement(sql)) {
            st.setString(1, matricula);
            try (ResultSet resultado = st.executeQuery()) {
                if (resultado.next()) {
                    alumnoEncontrado = new Alumno();
                    alumnoEncontrado.setId(resultado.getInt("ID"));
                    alumnoEncontrado.setNombre(resultado.getString("Nombre"));
                    alumnoEncontrado.setApPaterno(resultado.getString("Ap_paterno"));
                    alumnoEncontrado.setApMaterno(resultado.getString("Ap_materno"));
                    alumnoEncontrado.setAñoNacimiento(resultado.getInt("A_nacimiento"));
                    alumnoEncontrado.setSexo(resultado.getInt("Sexo"));
                    alumnoEncontrado.setMatricula(resultado.getString("Matricula")); // Matrícula del alumno
                    alumnoEncontrado.setLicenciatura(resultado.getInt("ID_carrera")); // ID de la carrera
                    alumnoEncontrado.setSemestre(resultado.getInt("Semestre")); // Semestre del alumno
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(CRUDPersona.class.getName()).log(Level.SEVERE, "Error al buscar alumno por matrícula", ex);
        }
        return alumnoEncontrado;
    }

    public Alumno buscarAlumnoPorID(String clave) {
        if (!verificador.validarEntero(clave)) {
            return null;
        }
        int id = Integer.parseInt(clave);
        Alumno alumnoEncontrado = null;
        String sql = """
        SELECT Usuario.ID, Usuario.Nombre, Usuario.Ap_paterno, Usuario.Ap_materno, Usuario.A_nacimiento, Usuario.Sexo, Alumno.Matricula, Alumno.ID_carrera, Alumno.Semestre
        FROM Usuario
        JOIN Alumno ON Usuario.ID = Alumno.ID
        WHERE Usuario.ID = ?;
    """;

        try (PreparedStatement st = objConexion.getConnection().prepareStatement(sql)) {
            st.setInt(1, id);
            try (ResultSet resultado = st.executeQuery()) {
                if (resultado.next()) {
                    alumnoEncontrado = new Alumno();
                    alumnoEncontrado.setId(resultado.getInt("ID"));
                    alumnoEncontrado.setNombre(resultado.getString("Nombre"));
                    alumnoEncontrado.setApPaterno(resultado.getString("Ap_paterno"));
                    alumnoEncontrado.setApMaterno(resultado.getString("Ap_materno"));
                    alumnoEncontrado.setAñoNacimiento(resultado.getInt("A_nacimiento"));
                    alumnoEncontrado.setSexo(resultado.getInt("Sexo"));
                    alumnoEncontrado.setMatricula(resultado.getString("Matricula")); // Matrícula del alumno
                    alumnoEncontrado.setLicenciatura(resultado.getInt("ID_carrera")); // ID de la carrera
                    alumnoEncontrado.setSemestre(resultado.getInt("Semestre")); // Semestre del alumno
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(CRUDPersona.class.getName()).log(Level.SEVERE, "Error al buscar alumno por ID", ex);
        }
        return alumnoEncontrado;
    }

    public ArrayList busquedaFiltradaAlumno(String matricula) {
        ArrayList<Persona> personas = new ArrayList<>();
        for (Persona persona : (ArrayList<Persona>) crudPersona.read()) {
            if (persona instanceof Alumno) {
                Alumno alumno = (Alumno) persona;
                if (alumno.getMatricula().contains(matricula)) {
                    alumno.setTipo("Alumno");
                    personas.add(alumno);
                }
            }
        }
        return personas;
    }

    public ArrayList busquedaFiltradaDocente(String noPlaza) {
        ArrayList<Persona> personas = new ArrayList<>();
        for (Persona persona : (ArrayList<Persona>) crudPersona.read()) {
            if (persona instanceof Docente) {
                Docente docente = (Docente) persona;
                if (docente.getNoDePlaza().contains(noPlaza)) {
                    docente.setTipo("Docente");
                    personas.add(docente);
                }
            }
        }
        return personas;
    }

    public ArrayList<Persona> busquedaFiltradaVisitante(String textoInstitucion) {
        ArrayList<Persona> personas = new ArrayList<>();
        for (Persona persona : (ArrayList<Persona>) crudPersona.read()) {
            if (persona instanceof Visitante) {
                Visitante visitante = (Visitante) persona;
                if (textoInstitucion.isEmpty() || String.valueOf(visitante.getInstitucion()).contains(textoInstitucion)) {
                    personas.add(visitante);
                }
            }
        }
        return personas;
    }

    public Docente buscarDocente(String numPlaza) {
        Docente personalEncontrado = null;
        String sql = """
        SELECT Usuario.ID, Usuario.Nombre, Usuario.Ap_paterno, Usuario.Ap_materno, Usuario.A_nacimiento, Usuario.Sexo, Personal.Num_plaza
        FROM Usuario
        JOIN Sexo ON Usuario.Sexo = Sexo.ID_sexo
        JOIN Personal ON Usuario.ID = Personal.ID
        WHERE Personal.Num_plaza = ?;
    """;

        try (PreparedStatement st = objConexion.getConnection().prepareStatement(sql)) {
            st.setString(1, numPlaza);
            try (ResultSet resultado = st.executeQuery()) {
                if (resultado.next()) {
                    personalEncontrado = new Docente();
                    personalEncontrado.setId(resultado.getInt("ID"));
                    personalEncontrado.setNombre(resultado.getString("Nombre"));
                    personalEncontrado.setApPaterno(resultado.getString("Ap_paterno"));
                    personalEncontrado.setApMaterno(resultado.getString("Ap_materno"));
                    personalEncontrado.setAñoNacimiento(resultado.getInt("A_nacimiento"));
                    personalEncontrado.setSexo(resultado.getInt("Sexo"));
                    personalEncontrado.setNoDePlaza(resultado.getString("Num_plaza")); // Número de plaza del personal
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(CRUDPersona.class.getName()).log(Level.SEVERE, "Error al buscar personal por número de plaza", ex);
        }
        return personalEncontrado;
    }

    public Docente buscarDocentePorID(String clave) {
        if (!verificador.validarEntero(clave)) {
            return null;
        }
        int id = Integer.parseInt(clave);
        Docente personalEncontrado = null;
        String sql = """
        SELECT Usuario.ID, Usuario.Nombre, Usuario.Ap_paterno, Usuario.Ap_materno, Usuario.A_nacimiento, Usuario.Sexo, Personal.Num_plaza
        FROM Usuario
        JOIN Personal ON Usuario.ID = Personal.ID
        WHERE Usuario.ID = ?;
    """;

        try (PreparedStatement st = objConexion.getConnection().prepareStatement(sql)) {
            st.setInt(1, id);
            try (ResultSet resultado = st.executeQuery()) {
                if (resultado.next()) {
                    personalEncontrado = new Docente();
                    personalEncontrado.setId(resultado.getInt("ID"));
                    personalEncontrado.setNombre(resultado.getString("Nombre"));
                    personalEncontrado.setApPaterno(resultado.getString("Ap_paterno"));
                    personalEncontrado.setApMaterno(resultado.getString("Ap_materno"));
                    personalEncontrado.setAñoNacimiento(resultado.getInt("A_nacimiento"));
                    personalEncontrado.setSexo(resultado.getInt("Sexo")); // Sexo como ID
                    personalEncontrado.setNoDePlaza(resultado.getString("Num_plaza")); // Número de plaza del personal
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(CRUDPersona.class.getName()).log(Level.SEVERE, "Error al buscar personal por ID", ex);
        }
        return personalEncontrado;
    }

    public Visitante buscarVisitante(String clave) {
        if (!verificador.validarEntero(clave)) {
            return null;
        }
        int id = Integer.parseInt(clave);
        Visitante visitanteEncontrado = null;
        String sql = """
        SELECT Usuario.ID, Usuario.Nombre, Usuario.Ap_paterno, Usuario.Ap_materno, Usuario.A_nacimiento, Usuario.Sexo, Visitante.ID_escuela
        FROM Usuario
        JOIN Sexo ON Usuario.Sexo = Sexo.ID_sexo
        JOIN Visitante ON Usuario.ID = Visitante.ID
        WHERE Usuario.ID = ?;
    """;

        try (PreparedStatement st = objConexion.getConnection().prepareStatement(sql)) {
            st.setInt(1, id);
            try (ResultSet resultado = st.executeQuery()) {
                if (resultado.next()) {
                    visitanteEncontrado = new Visitante();
                    visitanteEncontrado.setId(resultado.getInt("ID"));
                    visitanteEncontrado.setNombre(resultado.getString("Nombre"));
                    visitanteEncontrado.setApPaterno(resultado.getString("Ap_paterno"));
                    visitanteEncontrado.setApMaterno(resultado.getString("Ap_materno"));
                    visitanteEncontrado.setAñoNacimiento(resultado.getInt("A_nacimiento"));
                    visitanteEncontrado.setSexo(resultado.getInt("Sexo"));
                    visitanteEncontrado.setInstitucion(resultado.getInt("ID_escuela")); // ID de la institución
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(CRUDPersona.class.getName()).log(Level.SEVERE, "Error al buscar visitante por ID", ex);
        }
        return visitanteEncontrado;
    }

    public int AlumnoBuscarPor(String buscarTexto, DefaultTableModel modeloTabla) {
        modeloTabla.setRowCount(0);
        Persona persona = new Persona();
        int filasEncontradas = 0; // Contador de filas encontradas
        String sql = """
        SELECT Usuario.ID, Usuario.Nombre, Usuario.Ap_paterno, Usuario.Ap_materno, Usuario.A_nacimiento, Sexo.Tipo_sexo
        FROM Usuario
        JOIN Sexo ON Usuario.Sexo = Sexo.ID_sexo
        JOIN Alumno ON Usuario.ID = Alumno.ID
        WHERE Usuario.Nombre ILIKE '%' || ? || '%'
           OR Usuario.Ap_paterno ILIKE '%' || ? || '%'
           OR Usuario.Ap_materno ILIKE '%' || ? || '%'
           OR (EXTRACT(YEAR FROM CURRENT_DATE) - Usuario.A_nacimiento) = ?
           OR Sexo.Tipo_sexo ILIKE '%' || ? || '%';
    """;

        try (PreparedStatement st = objConexion.getConnection().prepareStatement(sql)) {
            // Validar si el texto es un entero
            int entero = verificador.validarEntero(buscarTexto) ? Integer.parseInt(buscarTexto) : -1;

            // Asignar parámetros al PreparedStatement
            st.setString(1, buscarTexto); // Para Nombre
            st.setString(2, buscarTexto); // Para Ap_paterno
            st.setString(3, buscarTexto); // Para Ap_materno
            st.setInt(4, entero);         // Para la edad
            st.setString(5, buscarTexto); // Para el tipo de sexo

            try (ResultSet resultado = st.executeQuery()) {
                while (resultado.next()) {
                    // Crear una nueva instancia de Persona para cada fila
                    Persona personaEncontrada = new Persona();
                    personaEncontrada.setId(resultado.getInt("ID"));
                    personaEncontrada.setNombre(resultado.getString("Nombre"));
                    personaEncontrada.setApPaterno(resultado.getString("Ap_paterno"));
                    personaEncontrada.setApMaterno(resultado.getString("Ap_materno"));
                    personaEncontrada.setAñoNacimiento(resultado.getInt("A_nacimiento"));

                    // Agregar la fila al modelo de tabla
                    modeloTabla.addRow(new Object[]{
                        personaEncontrada.getId(),
                        personaEncontrada.getNombre(),
                        personaEncontrada.getApPaterno(),
                        personaEncontrada.getApMaterno(),
                        String.valueOf(Year.now().getValue() - personaEncontrada.getAñoNacimiento()),
                        resultado.getString("Tipo_sexo"),
                        "Alumno"
                    });

                    filasEncontradas++;
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(CRUDPersona.class.getName()).log(Level.SEVERE, "Error al buscar alumnos", ex);
        }

        return filasEncontradas; // Retorna el número de filas encontradas
    }

    public int DocenteBuscarPor(String buscarTexto, DefaultTableModel modeloTabla) {
        modeloTabla.setRowCount(0);
        Persona persona = new Persona();
        int filasEncontradas = 0; // Contador de filas encontradas
        String sql = """
        SELECT Usuario.ID, Usuario.Nombre, Usuario.Ap_paterno, Usuario.Ap_materno, Usuario.A_nacimiento, Sexo.Tipo_sexo
        FROM Usuario
        JOIN Sexo ON Usuario.Sexo = Sexo.ID_sexo
        JOIN Personal ON Usuario.ID = Personal.ID
        WHERE Usuario.Nombre ILIKE '%' || ? || '%'
           OR Usuario.Ap_paterno ILIKE '%' || ? || '%'
           OR Usuario.Ap_materno ILIKE '%' || ? || '%'
           OR (EXTRACT(YEAR FROM CURRENT_DATE) - Usuario.A_nacimiento) = ?
           OR Sexo.Tipo_sexo ILIKE '%' || ? || '%';
    """;

        try (PreparedStatement st = objConexion.getConnection().prepareStatement(sql)) {
            // Validar si el texto es un entero
            int entero = verificador.validarEntero(buscarTexto) ? Integer.parseInt(buscarTexto) : -1;

            // Asignar parámetros al PreparedStatement
            st.setString(1, buscarTexto); // Para Nombre
            st.setString(2, buscarTexto); // Para Ap_paterno
            st.setString(3, buscarTexto); // Para Ap_materno
            st.setInt(4, entero);         // Para la edad
            st.setString(5, buscarTexto); // Para el tipo de sexo

            try (ResultSet resultado = st.executeQuery()) {
                while (resultado.next()) {
                    // Crear una nueva instancia de Persona para cada fila
                    Persona personaEncontrada = new Persona();
                    personaEncontrada.setId(resultado.getInt("ID"));
                    personaEncontrada.setNombre(resultado.getString("Nombre"));
                    personaEncontrada.setApPaterno(resultado.getString("Ap_paterno"));
                    personaEncontrada.setApMaterno(resultado.getString("Ap_materno"));
                    personaEncontrada.setAñoNacimiento(resultado.getInt("A_nacimiento"));

                    // Agregar la fila al modelo de tabla
                    modeloTabla.addRow(new Object[]{
                        personaEncontrada.getId(),
                        personaEncontrada.getNombre(),
                        personaEncontrada.getApPaterno(),
                        personaEncontrada.getApMaterno(),
                        String.valueOf(Year.now().getValue() - personaEncontrada.getAñoNacimiento()),
                        resultado.getString("Tipo_sexo"),
                        "Docente"
                    });

                    filasEncontradas++;
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(CRUDPersona.class.getName()).log(Level.SEVERE, "Error al buscar personal", ex);
        }

        return filasEncontradas; // Retorna el número de filas encontradas
    }

    public int visitanteBuscarPor(String buscarTexto, DefaultTableModel modeloTabla) {
        modeloTabla.setRowCount(0);
        Persona persona = new Persona();
        int filasEncontradas = 0; // Contador de filas encontradas
        String sql = """
        SELECT Usuario.ID, Usuario.Nombre, Usuario.Ap_paterno, Usuario.Ap_materno, Usuario.A_nacimiento, Sexo.Tipo_sexo
        FROM Usuario
        JOIN Sexo ON Usuario.Sexo = Sexo.ID_sexo
        JOIN Visitante ON Usuario.ID = Visitante.ID
        WHERE Usuario.Nombre ILIKE '%' || ? || '%'
           OR Usuario.Ap_paterno ILIKE '%' || ? || '%'
           OR Usuario.Ap_materno ILIKE '%' || ? || '%'
           OR (EXTRACT(YEAR FROM CURRENT_DATE) - Usuario.A_nacimiento) = ?
           OR Sexo.Tipo_sexo ILIKE '%' || ? || '%';
    """;

        try (PreparedStatement st = objConexion.getConnection().prepareStatement(sql)) {
            // Validar si el texto es un entero
            int entero = verificador.validarEntero(buscarTexto) ? Integer.parseInt(buscarTexto) : -1;

            // Asignar parámetros al PreparedStatement
            st.setString(1, buscarTexto); // Para Nombre
            st.setString(2, buscarTexto); // Para Ap_paterno
            st.setString(3, buscarTexto); // Para Ap_materno
            st.setInt(4, entero);         // Para la edad
            st.setString(5, buscarTexto); // Para el tipo de sexo

            try (ResultSet resultado = st.executeQuery()) {
                while (resultado.next()) {
                    Persona personaEncontrada = new Persona();
                    personaEncontrada.setId(resultado.getInt("ID"));
                    personaEncontrada.setNombre(resultado.getString("Nombre"));
                    personaEncontrada.setApPaterno(resultado.getString("Ap_paterno"));
                    personaEncontrada.setApMaterno(resultado.getString("Ap_materno"));
                    personaEncontrada.setAñoNacimiento(resultado.getInt("A_nacimiento"));
                    modeloTabla.addRow(new Object[]{
                        personaEncontrada.getId(),
                        personaEncontrada.getNombre(),
                        personaEncontrada.getApPaterno(),
                        personaEncontrada.getApMaterno(),
                        String.valueOf(Year.now().getValue() - personaEncontrada.getAñoNacimiento()),
                        resultado.getString("Tipo_sexo"),
                        "Visitante"
                    });

                    filasEncontradas++;
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(CRUDPersona.class.getName()).log(Level.SEVERE, "Error al buscar visitantes", ex);
        }

        return filasEncontradas; // Retorna el número de filas encontradas
    }

    public Persona buscarID(String clave) {
        if (!verificador.validarEntero(clave)) {
            return null;
        }
        int id = Integer.parseInt(clave);
        Persona personaEncontrada = null;
        String sql = """
        SELECT * FROM Usuario
        WHERE ID = ?;
    """;
        try (PreparedStatement st = objConexion.getConnection().prepareStatement(sql)) {
            st.setInt(1, id);
            try (ResultSet resultado = st.executeQuery()) {
                if (resultado.next()) {
                    personaEncontrada = new Persona();
                    personaEncontrada.setId(resultado.getInt("ID"));
                    personaEncontrada.setNombre(resultado.getString("Nombre"));
                    personaEncontrada.setApPaterno(resultado.getString("Ap_paterno"));
                    personaEncontrada.setApMaterno(resultado.getString("Ap_materno"));
                    personaEncontrada.setAñoNacimiento(resultado.getInt("A_nacimiento"));
                    personaEncontrada.setSexo(resultado.getInt("Sexo"));
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(CRUDPersona.class.getName()).log(Level.SEVERE, "Error al buscar usuario por ID", ex);
        }
        return personaEncontrada;
    }

    public int buscarPor(String buscarTexto, DefaultTableModel modeloTabla) {
        modeloTabla.setRowCount(0);
        Persona persona = new Persona();
        int filasEncontradas = 0;
        String sql = """
        SELECT Usuario.ID, Usuario.Nombre, Usuario.Ap_paterno, Usuario.Ap_materno, Usuario.A_nacimiento, Sexo.Tipo_sexo, Usuario.Sexo
        FROM Usuario
        JOIN Sexo ON Usuario.Sexo = Sexo.ID_sexo
        WHERE Nombre ILIKE '%' || ? || '%'
           OR Ap_paterno ILIKE '%' || ? || '%'
           OR Ap_materno ILIKE '%' || ? || '%'
           OR (EXTRACT(YEAR FROM CURRENT_DATE) - A_nacimiento) = ?
           OR Sexo.Tipo_sexo ILIKE '%' || ? || '%';
    """;
        try (PreparedStatement st = objConexion.getConnection().prepareStatement(sql)) {
            int entero = verificador.validarEntero(buscarTexto) ? Integer.parseInt(buscarTexto) : -1;
            st.setString(1, buscarTexto);
            st.setString(2, buscarTexto);
            st.setString(3, buscarTexto);
            st.setInt(4, entero);
            st.setString(5, buscarTexto);
            try (ResultSet resultado = st.executeQuery()) {
                while (resultado.next()) {
                    Persona personaEncontrada = new Persona();
                    personaEncontrada.setId(resultado.getInt("ID"));
                    personaEncontrada.setNombre(resultado.getString("Nombre"));
                    personaEncontrada.setApPaterno(resultado.getString("Ap_paterno"));
                    personaEncontrada.setApMaterno(resultado.getString("Ap_materno"));
                    personaEncontrada.setAñoNacimiento(resultado.getInt("A_nacimiento"));
                    saberTipo(personaEncontrada);
                    modeloTabla.addRow(new Object[]{
                        personaEncontrada.getId(),
                        personaEncontrada.getNombre(),
                        personaEncontrada.getApPaterno(),
                        personaEncontrada.getApMaterno(),
                        String.valueOf(Year.now().getValue() - personaEncontrada.getAñoNacimiento()),
                        resultado.getString("Tipo_sexo"),
                        personaEncontrada.getTipo()
                    });

                    filasEncontradas++;
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(CRUDPersona.class.getName()).log(Level.SEVERE, "Error al buscar usuarios", ex);
        }

        return filasEncontradas;
    }

    public void setCrudPersona(CRUDPersona crudPersona) {
        this.crudPersona = crudPersona;

    }

    public void saberTipo(Persona persona) {
        if (buscarAlumnoPorID(String.valueOf(persona.getId())) != null) {
            persona.setTipo("Alumno");
        } else if (buscarVisitante(String.valueOf(persona.getId())) != null) {
            persona.setTipo("Visitante");
        } else if (buscarDocentePorID(String.valueOf(persona.getId())) != null) {
            persona.setTipo("Docente");
        }
    }

//           buscarTexto = buscarTexto.toUpperCase();
//        modeloTabla.setRowCount(0);
//        for (Persona persona : (ArrayList<Persona>) crudPersona.read()) {
//            if (persona.getNombre().toUpperCase().contains(buscarTexto)
//                    || persona.getApPaterno().toUpperCase().contains(buscarTexto)
//                    || persona.getApMaterno().toUpperCase().contains(buscarTexto)
//                    || String.valueOf(persona.getAñoNacimiento()).toUpperCase().contains(buscarTexto)
//                    || String.valueOf(persona.getAñoNacimiento()).toUpperCase().contains(buscarTexto)
//                    || (manejoBD.buscarSex0(persona.getSexo()).getNombre()).toUpperCase().contains(buscarTexto)) {
//                x = 1;
//                modeloTabla.addRow(new Object[]{persona.getId(), persona.getNombre(),
//                    persona.getApPaterno(), persona.getApMaterno(), String.valueOf(persona.getAñoNacimiento()), (manejoBD.buscarSex0(persona.getSexo())).getNombre(), persona.getTipo()});
//            }
//        }
}
