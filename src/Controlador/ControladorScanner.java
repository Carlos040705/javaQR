package Controlador;

import javax.swing.SwingUtilities;
import Modelo.SerialConnection;
import javax.swing.JTextField;

public class ControladorScanner {

    private SerialConnection connection;
    private JTextField activeTextField; 

    public ControladorScanner(String portName, int baudRate) {
        connection = new SerialConnection(portName, baudRate);

        if (connection.openConnection()) {
            System.out.println("hola");
            connection.addDataListener(data -> {
                if (activeTextField != null) {
                    SwingUtilities.invokeLater(() -> activeTextField.setText(data.trim()));
                    System.out.println("entrando al metodo");
                }
            });
        } else {
            System.out.println("No se pudo abrir el puerto serial.");
        }
    }
    public void setActiveTextField(JTextField textField) {
        this.activeTextField = textField;
        System.out.println("textField actualizado");
    }

    /**
     * Cerrar la conexión serial.
     */
    public void closeConnection() {
        if (connection != null) {
            connection.closeConnection();
        }
    }
}
