package Modelo;

import java.sql.*;

public class ConnectDataBase {

    private static ConnectDataBase instance;
    private Connection connection;

    private ConnectDataBase() {
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(
                    "jdbc:postgresql://127.0.0.1:5432/proyectopava",
                    "postgres", "bernapro190");
            System.out.println("Conexión establecida correctamente.");
        } catch (ClassNotFoundException | SQLException ex) {
            System.out.println("Error al conectar a la base de datos: " + ex.getMessage());
        }
    }

    // Método para obtener la instancia única
    public static ConnectDataBase getInstance() {
        if (instance == null) {
            synchronized (ConnectDataBase.class) {
                if (instance == null) {
                    instance = new ConnectDataBase();
                }
            }
        }
        return instance;
    }

    // Método para obtener la conexión
    public Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                connection = DriverManager.getConnection(
                        "jdbc:postgresql://127.0.0.1:5432/sistemaqr",
                        "postgres", "unach2025");
                System.out.println("Conexión restablecida.");
            }
        } catch (SQLException ex) {
            System.out.println("Error al verificar/restablecer la conexión: " + ex.getMessage());
        }
        return connection;
    }

    // Método para cerrar la conexión (opcional, si necesitas cerrarla manualmente)
//    public void cerrarConexion() {
//        try {
//            if (connection != null && !connection.isClosed()) {
//                connection.close();
//                System.out.println("Conexión cerrada correctamente.");
//            }
//        } catch (SQLException ex) {
//            System.out.println("Error al cerrar la conexión: " + ex.getMessage());
//        }
//    }
}
