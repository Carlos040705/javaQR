package Controlador;

import Modelo.Alumno;
import Modelo.Docente;
import Modelo.Persona;
import Modelo.Sesion;
import Vista.Historial;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ControladorHistorial implements ActionListener, KeyListener {

    private CRUDSesion crudSesion;
    private Historial consulta;
    private BusquedaSQL busquedaPersona;

    public ControladorHistorial(Historial consulta) {
        this.consulta = consulta;
        this.crudSesion = new CRUDSesion();
        this.busquedaPersona = new BusquedaSQL();
        this.consulta.botonEditar.addActionListener(this);
        this.consulta.botonDescargarExcel.addActionListener(this); // Listener para el botón de descarga
        this.consulta.fecha.addPropertyChangeListener("date", evt -> actualizarTablaPorFecha()); // Listener para el calendario
        consulta.cajaBuscarClave.addKeyListener(this);
        consulta.cajaBuscarPor.addKeyListener(this);

        reiniciarTabla();
    }

    public void reiniciarTabla() {
        Date fechaHoy = new Date();
        consulta.fecha.setDate(fechaHoy);

        consulta.cajaBuscarClave.setText("");
        consulta.cajaBuscarPor.setText("");

        actualizarTablaPorFecha();
    }

    public void actualizarTablaPorFecha() {
        // Obtener la fecha seleccionada y los valores de los campos de texto
        Date fechaSeleccionada = consulta.fecha.getDate();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String textoClave = consulta.cajaBuscarClave.getText().trim();
        String textoCriterio = consulta.cajaBuscarPor.getText().trim();

        // Si todo está vacío (campos de texto y fecha), usar la fecha actual
        if (textoClave.isEmpty() && textoCriterio.isEmpty() && fechaSeleccionada == null) {
            fechaSeleccionada = new Date(); // Usar la fecha actual
            consulta.fecha.setDate(fechaSeleccionada); // Actualizar el calendario a la fecha actual
        }

        // Convertir la fecha seleccionada a formato de texto
        String fecha = (fechaSeleccionada != null) ? sdf.format(fechaSeleccionada) : null;

        // Buscar sesiones con los criterios actuales
        ArrayList<Sesion> sesiones = crudSesion.buscarSesionPorCriterios(textoClave, textoCriterio, fecha);

        // Actualizar la tabla con los resultados
        DefaultTableModel modelo = (DefaultTableModel) consulta.tablaPersonas.getModel();
        modelo.setRowCount(0); // Limpiar la tabla
        for (Sesion sesion : sesiones) {
            modelo.addRow(new Object[]{
                (busquedaPersona.buscarID(String.valueOf(sesion.getIdUsuario()))).getNombre(),
                sesion.getIdRegistro(),
                sesion.getHoraEntrada(),
                sesion.getHoraSalida() != null ? sesion.getHoraSalida() : "No existe",
                sesion.getFecha()
            });
        }

        // Actualizar el estado del botón de descarga
    }

  

    public void exportarTablaAExcel() {
        // Verificar si la tabla está vacía
        DefaultTableModel modelo = (DefaultTableModel) consulta.tablaPersonas.getModel();
        if (modelo.getRowCount() == 0) {
            JOptionPane.showMessageDialog(consulta, "No hay datos para exportar.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("Guardar archivo Excel");
        chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

        // Obtener los valores actuales de los criterios
        Date fechaSeleccionada = consulta.fecha.getDate();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String textoClave = consulta.cajaBuscarClave.getText().trim();
        String textoCriterio = consulta.cajaBuscarPor.getText().trim();

        // Determinar el nombre del archivo según los criterios seleccionados
        String nombreArchivo = "Sesiones";
        if (fechaSeleccionada == null) {
            fechaSeleccionada = new Date(); // Usar la fecha actual si no hay fecha seleccionada
        }
        String fecha = sdf.format(fechaSeleccionada);

        if (!textoClave.isEmpty()) {
            nombreArchivo += "-" + fecha + "-" + textoClave;
        } else if (!textoCriterio.isEmpty()) {
            nombreArchivo += "-" + fecha + "-" + textoCriterio;
        } else {
            nombreArchivo += "-" + fecha;
        }

        chooser.setSelectedFile(new File(nombreArchivo + ".xls"));

        if (chooser.showSaveDialog(consulta) == JFileChooser.APPROVE_OPTION) {
            try {
                File archivoExcel = chooser.getSelectedFile();

                // Asegurarse de que el archivo tenga la extensión .xls
                if (!archivoExcel.getName().endsWith(".xls")) {
                    archivoExcel = new File(archivoExcel.getAbsolutePath() + ".xls");
                }

                Workbook libro = new HSSFWorkbook();
                try (FileOutputStream archivo = new FileOutputStream(archivoExcel)) {
                    Sheet hoja = libro.createSheet("Datos");

                    // Crear encabezados
                    Row filaEncabezado = hoja.createRow(0);
                    for (int c = 0; c < modelo.getColumnCount(); c++) {
                        Cell celda = filaEncabezado.createCell(c);
                        celda.setCellValue(modelo.getColumnName(c));
                    }

                    // Llenar datos
                    for (int f = 0; f < modelo.getRowCount(); f++) {
                        Row fila = hoja.createRow(f + 1);
                        for (int c = 0; c < modelo.getColumnCount(); c++) {
                            Object valor = modelo.getValueAt(f, c);
                            Cell celda = fila.createCell(c);
                            if (valor instanceof Number) {
                                celda.setCellValue(Double.parseDouble(valor.toString()));
                            } else {
                                celda.setCellValue(valor != null ? valor.toString() : "");
                            }
                        }
                    }

                    libro.write(archivo);
                }

                JOptionPane.showMessageDialog(consulta, "Archivo Excel exportado exitosamente en: " + archivoExcel.getAbsolutePath());
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(consulta, "Error al exportar el archivo.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(consulta.botonEditar)) {
            int id = obtenerUsuarioId();
            Sesion sesion = buscarSesionPorId(id);

            if (sesion != null && sesion.getHoraSalida() != null) {
                JOptionPane.showMessageDialog(consulta, 
                    "No se puede editar esta sesión porque ya tiene una hora de salida registrada.", 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
                return;
            }

            sesion = new Sesion();
            sesion.setIdRegistro(id);
            crudSesion.setSesion(sesion);
            crudSesion.update();
            actualizarTablaPorFecha(); // Mantener la tabla actualizada con la fecha seleccionada
        } else if (e.getSource().equals(consulta.botonDescargarExcel)) {
            exportarTablaAExcel();
        }
    }

    public int obtenerUsuarioId() {
        int id;
        return id = (int) consulta.tablaPersonas.getValueAt(consulta.tablaPersonas.getSelectedRow(), 1);
    }



    public Persona buscarAlumMaestro(String clave) {
        Persona persona = null;
        Docente docente = busquedaPersona.buscarDocente(clave);
        Alumno alumno = busquedaPersona.buscarAlumno(clave);
        if (alumno != null) {
            persona = alumno;
        } else if (docente != null) {
            persona = docente;
        }
        return persona;
    }

    public ArrayList<Sesion> buscarSesion(String clave) {
        int id = -1;
        try {
            id = Integer.parseInt(clave);
        } catch (NumberFormatException e) {
        }
        ArrayList<Sesion> sesiones = new ArrayList<>();
        for (Sesion sesion : crudSesion.read()) {
            if (id == sesion.getIdUsuario()) {
                sesiones.add(sesion);
            }
        }
        return sesiones;
    }

    public ArrayList<Sesion> buscarSesionPorCriterio(String texto) {
        ArrayList<Sesion> sesiones = new ArrayList<>();
        for (Sesion sesion : crudSesion.read()) {
            Persona persona = busquedaPersona.buscarID(String.valueOf(sesion.getIdUsuario()));
            if (persona != null && (persona.getNombre().toLowerCase().contains(texto.toLowerCase())
                    || // Buscar por
                    // nombre
                    String.valueOf(sesion.getIdRegistro()).contains(texto)
                    || // Buscar por ID de sesión
                    sesion.getHoraEntrada().contains(texto)
                    || // Buscar por hora de entrada
                    (sesion.getHoraSalida() != null && sesion.getHoraSalida().contains(texto))
                    || // Buscar por hora de
                    // salida
                    sesion.getFecha().contains(texto) // Buscar por fecha
                    )) {
                sesiones.add(sesion);
            }
        }
        return sesiones;
    }

    public Sesion buscarSesionPorId(int idRegistro) {
        for (Sesion sesion : crudSesion.read()) {
            if (sesion.getIdRegistro() == idRegistro) {
                return sesion;
            }
        }
        return null; // Si no se encuentra la sesión
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getSource().equals(consulta.cajaBuscarClave) || e.getSource().equals(consulta.cajaBuscarPor)) {
            String textoClave = consulta.cajaBuscarClave.getText().trim();
            String textoCriterio = consulta.cajaBuscarPor.getText().trim();
            Date fechaSeleccionada = consulta.fecha.getDate();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

            // Si los campos de texto están vacíos, usar la fecha actual
            if (textoClave.isEmpty() && textoCriterio.isEmpty()) {
                if (fechaSeleccionada == null) {
                    fechaSeleccionada = new Date(); // Usar la fecha actual si no hay fecha seleccionada
                    consulta.fecha.setDate(fechaSeleccionada); // Actualizar el calendario
                }
            }

            // Convertir la fecha seleccionada a formato de texto
            String fecha = (fechaSeleccionada != null) ? sdf.format(fechaSeleccionada) : null;

            // Buscar sesiones con los criterios actuales
            ArrayList<Sesion> sesiones = crudSesion.buscarSesionPorCriterios(textoClave, textoCriterio, fecha);
            llenarTabla(sesiones);
        }
    }

    public  void llenarTabla(ArrayList<Sesion> sesiones) {
        DefaultTableModel modelo = (DefaultTableModel) consulta.tablaPersonas.getModel();
        modelo.setRowCount(0); // Limpiar la tabla
        for (Sesion sesion : sesiones) {
            modelo.addRow(new Object[]{
                (busquedaPersona.buscarID(String.valueOf(sesion.getIdUsuario()))).getNombre(),
                sesion.getIdRegistro(),
                sesion.getHoraEntrada(),
                sesion.getHoraSalida() != null ? sesion.getHoraSalida() : "No existe",
                sesion.getFecha()
            });
        }
    }
}
