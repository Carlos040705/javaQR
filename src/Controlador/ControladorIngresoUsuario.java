package Controlador;

import Modelo.Alumno;
import Modelo.Docente;
import Modelo.Persona;
import Modelo.Visitante;
import Vista.IngresoUsuario;
import java.awt.event.ActionEvent;
import javax.swing.JOptionPane;
import Modelo.Sesion;
import Vista.BienvenidoUsuario;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

public class ControladorIngresoUsuario implements KeyListener, ActionListener {

    private IngresoUsuario menuSesion;
    private BusquedaSQL busq;
    private CRUDSesion crudSesion;
    private BienvenidoUsuario panelBienvenida;

    public ControladorIngresoUsuario(IngresoUsuario menu) {
        this.menuSesion = menu;
        crudSesion = new CRUDSesion();
        initObj();
        addAL();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(menuSesion.comboPersona)) {
            switch (menuSesion.comboPersona.getSelectedIndex()) {
                case 0 ->
                    menuSesion.etiquetaMatricula.setText("Matrícula: ");
                case 1 ->
                    menuSesion.etiquetaMatricula.setText("No.Plaza: ");
                case 2 ->
                    menuSesion.etiquetaMatricula.setText("ID: ");
            }
        }
    }

    private void rellenarDatos(Persona objPersona) {
        if (objPersona == null) {
            JOptionPane.showMessageDialog(menuSesion, "No se encontró la persona con el identificador proporcionado.");
            return;
        } else {
            if (objPersona instanceof Alumno) {
                Alumno alumno = (Alumno) objPersona;
                panelBienvenida = new BienvenidoUsuario();
                panelBienvenida.etiquetaNombre.setText(alumno.getNombre().toUpperCase() + " " + alumno.getApPaterno().toUpperCase() + " " + alumno.getApMaterno().toUpperCase());
                panelBienvenida.etiquetaMatricula.setText("Matrícula: " + alumno.getMatricula().toUpperCase());
                panelBienvenida.setVisible(true);

            } else if (objPersona instanceof Docente) {
                Docente alumno = (Docente) objPersona;
                panelBienvenida = new BienvenidoUsuario();
                panelBienvenida.etiquetaNombre.setText(alumno.getNombre().toUpperCase() + " " + alumno.getApPaterno().toUpperCase() + " " + alumno.getApMaterno().toUpperCase());
                panelBienvenida.etiquetaMatricula.setText("No.Plaza: " + alumno.getNoDePlaza());
                panelBienvenida.setVisible(true);
            } else if (objPersona instanceof Visitante) {
                Visitante alumno = (Visitante) objPersona;
                panelBienvenida = new BienvenidoUsuario();
                panelBienvenida.etiquetaNombre.setText(alumno.getNombre().toUpperCase() + " " + alumno.getApPaterno().toUpperCase() + " " + alumno.getApMaterno().toUpperCase());
                panelBienvenida.etiquetaMatricula.setText("ID: " + alumno.getId());
                panelBienvenida.setVisible(true);
            }
        }
    }

    private void initObj() {
        busq = new BusquedaSQL();
    }

    private void addAL() {
        this.menuSesion.comboPersona.addActionListener(this);
        this.menuSesion.cajaMatricula.addKeyListener(this);
    }

    private boolean tieneSesionActiva(int idUsuario) {
        ArrayList<Sesion> sesiones = crudSesion.read(); // Obtén todas las sesiones
        for (Sesion sesion : sesiones) {
            if (sesion.getIdUsuario() == idUsuario && sesion.getHoraSalida() == null) {
                return true; // Existe una sesión activa sin hora de salida
            }
        }
        return false; // No hay sesiones activas sin hora de salida
    }

//    private void setupSerialPort() {
//        String puertoArduino = "COM5";
//        int baudRate = 9600;
//
//        SerialPort[] ports = SerialPort.getCommPorts();
//        for (SerialPort availablePort : ports) {
//            if (availablePort.getSystemPortName().equals(puertoArduino)) {
//                port = availablePort;
//                break;
//            }
//        }
//        if (port != null) {
//            port.setBaudRate(baudRate);
//            if (port.openPort()) {
//                port.addDataListener(new com.fazecast.jSerialComm.SerialPortDataListener() {
//                    @Override
//                    public int getListeningEvents() {
//                        return SerialPort.LISTENING_EVENT_DATA_AVAILABLE;
//                    }
//
//                    @Override
//                    public void serialEvent(com.fazecast.jSerialComm.SerialPortEvent event) {
//                        if (event.getEventType() == SerialPort.LISTENING_EVENT_DATA_AVAILABLE) {
//                            byte[] buffer = new byte[port.bytesAvailable()];
//                            port.readBytes(buffer, buffer.length);
//                            String data = new String(buffer).trim();
//                            if (data.matches("\\d{9}")) {
//                                menuSesion.cajaMatricula.setText(data);
//                            }
//                        }
//                    }
//                });
//            }
//        }
//    }
    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getSource().equals(menuSesion.cajaMatricula)) {
            String identificador = this.menuSesion.cajaMatricula.getText();

            switch (this.menuSesion.comboPersona.getSelectedIndex()) {
                case 0 -> {
                    Alumno objAlumno = busq.buscarAlumno(identificador);
                    if (objAlumno == null) {
                        return;
                    }
                    if (tieneSesionActiva(objAlumno.getId())) {
                        JOptionPane.showMessageDialog(menuSesion, "El alumno ya tiene una sesión activa");
                        return;
                    }
                    rellenarDatos(objAlumno);
                    Sesion sesion = new Sesion();
                    sesion.setIdUsuario(objAlumno.getId());
                    crudSesion.setSesion(sesion);
                    crudSesion.create();
                }
                case 1 -> {
                    Docente objDocente = busq.buscarDocente(identificador);
                    if (objDocente == null) {
                        return;
                    }
                    if (tieneSesionActiva(objDocente.getId())) {
                        JOptionPane.showMessageDialog(menuSesion, "El docente ya tiene una sesión activa.");
                        return;
                    }
                    rellenarDatos(objDocente);
                    Sesion sesion = new Sesion();
                    sesion.setIdUsuario(objDocente.getId());
                    crudSesion.setSesion(sesion);
                    crudSesion.create();
                }
                case 2 -> {
                    try {
                        Visitante objVisitante = busq.buscarVisitante(identificador);
                        if (objVisitante == null) {
                            return;
                        }
                        if (tieneSesionActiva(objVisitante.getId())) {
                            JOptionPane.showMessageDialog(menuSesion, "El visitante ya tiene una sesión activa.");
                            return;
                        }
                        rellenarDatos(objVisitante);
                        Sesion sesion = new Sesion();
                        sesion.setIdUsuario(objVisitante.getId());
                        crudSesion.setSesion(sesion);
                        crudSesion.create();
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(menuSesion, "El ID debe ser un número válido.");
                    }
                }
            }
        }
    }
}
