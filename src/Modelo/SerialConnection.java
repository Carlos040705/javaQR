package Modelo;

import com.fazecast.jSerialComm.SerialPort;
import com.fazecast.jSerialComm.SerialPortEvent;
import com.fazecast.jSerialComm.SerialPortDataListener;

public class SerialConnection {

    private SerialPort serialPort;
    private StringBuilder qrData = new StringBuilder();
    private SerialDataListener dataListener;
    private static SerialConnection instance;

    public SerialConnection(String portName, int baudRate) {
        serialPort = SerialPort.getCommPort(portName);
        serialPort.setBaudRate(baudRate);
    }

    public static SerialConnection getInstance(String portName, int baudRate) {
        if (instance == null) {
            instance = new SerialConnection(portName, baudRate);
        }
        return instance;
    }

    public boolean openConnection() {
        if (serialPort.openPort()) {
            System.out.println("Conexión establecida en el puerto: " + serialPort.getSystemPortName());
            return true;
        } else {
            System.out.println("No se pudo abrir el puerto.");
            return false;
        }
    }

    public void closeConnection() {
        if (serialPort.isOpen()) {
            serialPort.closePort();
            System.out.println("Conexión cerrada.");
        }
    }

    public void addDataListener(SerialDataListener listener) {
        this.dataListener = listener;

        serialPort.addDataListener(new SerialPortDataListener() {
            @Override
            public int getListeningEvents() {
                return SerialPort.LISTENING_EVENT_DATA_AVAILABLE;
            }

            @Override
            public void serialEvent(SerialPortEvent event) {
                if (event.getEventType() == SerialPort.LISTENING_EVENT_DATA_AVAILABLE) {
                    byte[] buffer = new byte[serialPort.bytesAvailable()];
                    serialPort.readBytes(buffer, buffer.length);

                    for (byte b : buffer) {
                        char c = (char) b;
                        if (c == '\n' || c == '\r') { // Detectar fin de línea
                            if (qrData.length() > 0) { // Verificar que no esté vacío
                                if (dataListener != null) {
                                    dataListener.onDataReceived(qrData.toString());
                                }
                                qrData.setLength(0); // Limpiar el acumulador
                            }
                        } else {
                            qrData.append(c); // Acumular caracteres
                        }
                    }
                }
            }
        });
    }
}
