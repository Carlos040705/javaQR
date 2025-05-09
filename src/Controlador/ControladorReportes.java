package Controlador;

import Modelo.ConnectDataBase;
import Vista.Reporte;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ControladorReportes implements ActionListener {

    private Reporte vista;
    private ConnectDataBase conexion;
    private String[][] datosReporteActual;
    private String[] columnasReporteActual;

    public ControladorReportes(Reporte vista) {
        this.vista = vista;
        this.conexion = ConnectDataBase.getInstance();
        this.vista.DescargarARCHIVO.addActionListener(this);
        this.vista.DescargarFOLDER.addActionListener(this);

        // Agregar listeners para fechaInicio y fechaFin
        vista.fechaInicio.addPropertyChangeListener(evt -> {
            if ("date".equals(evt.getPropertyName())) {
                manejarCambioDeFechas();
            }
        });

        vista.FechaFin.addPropertyChangeListener(evt -> {
            if ("date".equals(evt.getPropertyName())) {
                manejarCambioDeFechas();
            }
        });
    }

    private void manejarCambioDeFechas() {
        String fechaInicioStr = vista.fechaInicio.getDate() != null
                ? new SimpleDateFormat("yyyy-MM-dd").format(vista.fechaInicio.getDate())
                : null;
        String fechaFinStr = vista.FechaFin.getDate() != null
                ? new SimpleDateFormat("yyyy-MM-dd").format(vista.FechaFin.getDate())
                : null;

        // Solo procesar si ambas fechas están presentes
        if (fechaInicioStr != null && fechaFinStr != null) {
            // Actualizar la tabla de archivos
            mostrarArchivosPorRango(fechaInicioStr, fechaFinStr);

            // Mostrar automáticamente los datos y la gráfica del primer reporte
            String reportePredeterminado = "ReporteTotalPersonas"; // Cambia esto si deseas otro reporte por defecto
            mostrarDatosReportePorRango(fechaInicioStr, fechaFinStr, reportePredeterminado);
        } else if (fechaInicioStr == null && fechaFinStr != null) {
            // Mostrar mensaje solo si se selecciona fecha fin sin fecha inicio
            JOptionPane.showMessageDialog(null, "Por favor, selecciona la fecha de inicio.");
        }
    }

    private void mostrarArchivosPorRango(String fechaInicio, String fechaFin) {
        DefaultTableModel modeloArchivos = new DefaultTableModel();
        modeloArchivos.setColumnIdentifiers(new String[]{"Nombre del Reporte"});

        String[] reportes = {
            "ReporteTotalPersonas",
            "ReportePorCarrera",
            "ReportePorTipoUsuario",
            "ReportePorGeneroYCarrera",
            "ReportePorSemestreYCarrera",
            "ReportePorTurno"
        };

        for (String reporte : reportes) {
            modeloArchivos.addRow(new Object[]{reporte});
        }

        vista.tablaArchivos.setModel(modeloArchivos);

        vista.tablaArchivos.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int selectedRow = vista.tablaArchivos.getSelectedRow();
                if (selectedRow >= 0 && selectedRow < modeloArchivos.getRowCount()) {
                    String reporteSeleccionado = (String) modeloArchivos.getValueAt(selectedRow, 0);

                    // Obtener las fechas actuales directamente de los componentes de fecha
                    String fechaInicioActual = vista.fechaInicio.getDate() != null
                            ? new SimpleDateFormat("yyyy-MM-dd").format(vista.fechaInicio.getDate())
                            : null;
                    String fechaFinActual = vista.FechaFin.getDate() != null
                            ? new SimpleDateFormat("yyyy-MM-dd").format(vista.FechaFin.getDate())
                            : null;

                    if (fechaInicioActual != null && fechaFinActual != null) {
                        mostrarDatosReportePorRango(fechaInicioActual, fechaFinActual, reporteSeleccionado);
                    } else {
                        JOptionPane.showMessageDialog(null, "Por favor, selecciona ambas fechas: inicio y fin.");
                    }
                }
            }
        });
    }

    private void mostrarDatosReportePorRango(String fechaInicio, String fechaFin, String reporte) {
        String query = obtenerQueryReportePorRango(fechaInicio, fechaFin, reporte);
        DefaultTableModel modeloDetalles = new DefaultTableModel();

        try (Statement st = conexion.getConnection().createStatement(); ResultSet rs = st.executeQuery(query)) {
            modeloDetalles.setColumnIdentifiers(columnasReporteActual);

            while (rs.next()) {
                String[] fila = new String[columnasReporteActual.length];
                for (int i = 0; i < columnasReporteActual.length; i++) {
                    fila[i] = rs.getString(i + 1);
                }
                modeloDetalles.addRow(fila);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        vista.tablaDetalles.setModel(modeloDetalles);

        // Generar gráfica después de mostrar los datos
        generarGrafica(reporte);
    }

    private void generarGrafica(String reporte) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        for (int row = 0; row < vista.tablaDetalles.getRowCount(); row++) {
            String categoria = vista.tablaDetalles.getValueAt(row, 0).toString();
            for (int col = 1; col < vista.tablaDetalles.getColumnCount(); col++) {
                String columna = vista.tablaDetalles.getColumnName(col);
                Object valor = vista.tablaDetalles.getValueAt(row, col);
                try {
                    if (valor != null && valor.toString().matches("-?\\d+(\\.\\d+)?")) {
                        double numero = Double.parseDouble(valor.toString());
                        dataset.addValue(numero, columna, categoria);
                    }
                } catch (NumberFormatException e) {
                    System.err.println("Error al convertir el valor en la fila " + row + ", columna " + col + ": " + valor);
                }
            }
        }

        JFreeChart chart = ChartFactory.createBarChart(
                "Gráfica de " + reporte,
                "Categoría",
                "Valor",
                dataset
        );

        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(vista.panelGraficas.getSize());
        vista.panelGraficas.removeAll();
        vista.panelGraficas.add(chartPanel);
        vista.panelGraficas.revalidate();
        vista.panelGraficas.repaint();
    }

    private String obtenerQueryReportePorRango(String fechaInicio, String fechaFin, String reporte) {
        switch (reporte) {
            case "ReporteTotalPersonas":
                columnasReporteActual = new String[]{"TotalPersonas", "Hombre", "Mujer", "NoBinario"};
                return """
                        SELECT
                            COUNT(DISTINCT Usuario.ID) AS TotalPersonas,
                            COUNT(DISTINCT CASE WHEN Sexo.Tipo_sexo = 'Hombre' THEN Usuario.ID END) AS Hombre,
                            COUNT(DISTINCT CASE WHEN Sexo.Tipo_sexo = 'Mujer' THEN Usuario.ID END) AS Mujer,
                            COUNT(DISTINCT CASE WHEN Sexo.Tipo_sexo = 'No Binario' THEN Usuario.ID END) AS NoBinario
                        FROM Registro
                        JOIN Usuario ON Registro.ID_usuario = Usuario.ID
                        JOIN Sexo ON Usuario.Sexo = Sexo.ID_sexo
                        WHERE Registro.Fecha BETWEEN '%s' AND '%s'
                        """.formatted(fechaInicio, fechaFin);
            case "ReportePorCarrera":
                columnasReporteActual = new String[]{"Carrera", "Total"};
                return """
                        SELECT
                            Carrera.Nombre_carrera AS Carrera,
                            COUNT(DISTINCT Alumno.ID) AS Total
                        FROM Alumno
                        JOIN Carrera ON Alumno.ID_carrera = Carrera.ID_carrera
                        JOIN Registro ON Alumno.ID = Registro.ID_usuario
                        WHERE Registro.Fecha BETWEEN '%s' AND '%s'
                        GROUP BY Carrera.Nombre_carrera
                        """.formatted(fechaInicio, fechaFin);
            case "ReportePorTipoUsuario":
                columnasReporteActual = new String[]{"TipoUsuario", "Total"};
                return """
                        SELECT
                            CASE
                                WHEN Alumno.ID IS NOT NULL THEN 'Alumno'
                                WHEN Personal.ID IS NOT NULL THEN 'Docente'
                                WHEN Visitante.ID IS NOT NULL THEN 'Visitante'
                                ELSE 'Otros'
                            END AS TipoUsuario,
                            COUNT(DISTINCT Usuario.ID) AS Total
                        FROM Usuario
                        LEFT JOIN Alumno ON Usuario.ID = Alumno.ID
                        LEFT JOIN Personal ON Usuario.ID = Personal.ID
                        LEFT JOIN Visitante ON Usuario.ID = Visitante.ID
                        JOIN Registro ON Usuario.ID = Registro.ID_usuario
                        WHERE Registro.Fecha BETWEEN '%s' AND '%s'
                        GROUP BY TipoUsuario
                        """.formatted(fechaInicio, fechaFin);
            case "ReportePorGeneroYCarrera":
                columnasReporteActual = new String[]{"Carrera", "Masculino", "Femenino", "NoBinario"};
                return """
                        SELECT
                            Carrera.Nombre_carrera AS Carrera,
                            COUNT(DISTINCT CASE WHEN Sexo.Tipo_sexo = 'Hombre' THEN Usuario.ID END) AS Masculino,
                            COUNT(DISTINCT CASE WHEN Sexo.Tipo_sexo = 'Mujer' THEN Usuario.ID END) AS Femenino,
                            COUNT(DISTINCT CASE WHEN Sexo.Tipo_sexo = 'No Binario' THEN Usuario.ID END) AS NoBinario
                        FROM Alumno
                        JOIN Usuario ON Alumno.ID = Usuario.ID
                        JOIN Sexo ON Usuario.Sexo = Sexo.ID_sexo
                        JOIN Carrera ON Alumno.ID_carrera = Carrera.ID_carrera
                        JOIN Registro ON Usuario.ID = Registro.ID_usuario
                        WHERE Registro.Fecha BETWEEN '%s' AND '%s'
                        GROUP BY Carrera.Nombre_carrera
                        """.formatted(fechaInicio, fechaFin);
            case "ReportePorSemestreYCarrera":
                columnasReporteActual = new String[]{"Carrera", "1er", "2do", "3er", "4to", "5to", "6to", "7mo", "8vo", "9no"};
                return """
                        SELECT
                            Carrera.Nombre_carrera AS Carrera,
                            SUM(CASE WHEN Alumno.Semestre = 1 THEN 1 ELSE 0 END) AS "1er",
                            SUM(CASE WHEN Alumno.Semestre = 2 THEN 1 ELSE 0 END) AS "2do",
                            SUM(CASE WHEN Alumno.Semestre = 3 THEN 1 ELSE 0 END) AS "3er",
                            SUM(CASE WHEN Alumno.Semestre = 4 THEN 1 ELSE 0 END) AS "4to",
                            SUM(CASE WHEN Alumno.Semestre = 5 THEN 1 ELSE 0 END) AS "5to",
                            SUM(CASE WHEN Alumno.Semestre = 6 THEN 1 ELSE 0 END) AS "6to",
                            SUM(CASE WHEN Alumno.Semestre = 7 THEN 1 ELSE 0 END) AS "7mo",
                            SUM(CASE WHEN Alumno.Semestre = 8 THEN 1 ELSE 0 END) AS "8vo",
                            SUM(CASE WHEN Alumno.Semestre = 9 THEN 1 ELSE 0 END) AS "9no"
                        FROM Alumno
                        JOIN Carrera ON Alumno.ID_carrera = Carrera.ID_carrera
                        JOIN Registro ON Alumno.ID = Registro.ID_usuario
                        WHERE Registro.Fecha BETWEEN '%s' AND '%s'
                        GROUP BY Carrera.Nombre_carrera
                        """.formatted(fechaInicio, fechaFin);
            case "ReportePorTurno":
                columnasReporteActual = new String[]{"TotalRegistros", "Matutino", "Vespertino"};
                return """
                        SELECT
                            COUNT(*) AS TotalRegistros,
                            SUM(CASE WHEN Registro.Hora_entrada BETWEEN '07:00:00' AND '15:00:00' THEN 1 ELSE 0 END) AS Matutino,
                            SUM(CASE WHEN Registro.Hora_entrada BETWEEN '15:00:01' AND '20:00:00' THEN 1 ELSE 0 END) AS Vespertino
                        FROM Registro
                        WHERE Registro.Fecha BETWEEN '%s' AND '%s'
                        """.formatted(fechaInicio, fechaFin);
            default:
                return "";
        }
    }

    private void generarCarpetaPorRangoFechas(String fechaInicio, String fechaFin) {
        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("Seleccionar ubicación para guardar la carpeta");
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

        if (chooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
            File carpeta = new File(chooser.getSelectedFile(), "Reportes_" + fechaInicio + "_a_" + fechaFin);
            if (!carpeta.exists() && !carpeta.mkdir()) {
                JOptionPane.showMessageDialog(null, "No se pudo crear la carpeta para los reportes.");
                return;
            }

            String[] reportes = {
                "ReporteTotalPersonas",
                "ReportePorCarrera",
                "ReportePorTipoUsuario",
                "ReportePorGeneroYCarrera",
                "ReportePorSemestreYCarrera",
                "ReportePorTurno"
            };

            for (String reporte : reportes) {
                String query = obtenerQueryReportePorRango(fechaInicio, fechaFin, reporte);
                DefaultTableModel modeloDetalles = new DefaultTableModel();
                Statement st = null;
                ResultSet rs = null;

                try {
                    st = conexion.getConnection().createStatement();
                    rs = st.executeQuery(query);

                    modeloDetalles.setColumnIdentifiers(columnasReporteActual);

                    while (rs.next()) {
                        String[] fila = new String[columnasReporteActual.length];
                        for (int i = 0; i < columnasReporteActual.length; i++) {
                            fila[i] = rs.getString(i + 1);
                        }
                        modeloDetalles.addRow(fila);
                    }

                    File archivoExcel = new File(carpeta, reporte + ".xls");
                    Workbook libro = new HSSFWorkbook();
                    try (FileOutputStream archivo = new FileOutputStream(archivoExcel)) {
                        Sheet hoja = libro.createSheet("Datos");

                        Row filaEncabezado = hoja.createRow(0);
                        for (int c = 0; c < modeloDetalles.getColumnCount(); c++) {
                            Cell celda = filaEncabezado.createCell(c);
                            celda.setCellValue(modeloDetalles.getColumnName(c));
                        }

                        for (int f = 0; f < modeloDetalles.getRowCount(); f++) {
                            Row fila = hoja.createRow(f + 1);
                            for (int c = 0; c < modeloDetalles.getColumnCount(); c++) {
                                Cell celda = fila.createCell(c);
                                Object valor = modeloDetalles.getValueAt(f, c);
                                if (valor instanceof Number) {
                                    celda.setCellValue(Double.parseDouble(valor.toString()));
                                } else {
                                    celda.setCellValue(valor != null ? valor.toString() : "");
                                }
                            }
                        }

                        libro.write(archivo);
                    }
                } catch (SQLException | IOException ex) {
                    Logger.getLogger(ControladorReportes.class.getName()).log(Level.SEVERE, null, ex);
                    JOptionPane.showMessageDialog(null, "Error al generar el reporte: " + reporte);
                } finally {
                    try {
                        if (rs != null && !rs.isClosed()) {
                            rs.close();
                        }
                        if (st != null && !st.isClosed()) {
                            st.close();
                        }

                    } catch (SQLException ex) {
                        Logger.getLogger(ControladorReportes.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }

            JOptionPane.showMessageDialog(null, "Carpeta con reportes generada exitosamente en: " + carpeta.getAbsolutePath());
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(vista.DescargarARCHIVO)) {
            try {
                exportarExcel(vista.tablaDetalles);
            } catch (IOException ex) {
                Logger.getLogger(ControladorReportes.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if (e.getSource().equals(vista.DescargarFOLDER)) {
            String fechaInicioStr = vista.fechaInicio.getDate() != null
                    ? new SimpleDateFormat("yyyy-MM-dd").format(vista.fechaInicio.getDate())
                    : null;
            String fechaFinStr = vista.FechaFin.getDate() != null
                    ? new SimpleDateFormat("yyyy-MM-dd").format(vista.FechaFin.getDate())
                    : null;

            if (fechaInicioStr != null && fechaFinStr != null) {
                generarCarpetaPorRangoFechas(fechaInicioStr, fechaFinStr);
            } else {
                JOptionPane.showMessageDialog(null, "Por favor, selecciona ambas fechas: inicio y fin.");
            }
        }
    }

    public void exportarExcel(JTable tabla) throws IOException {
        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("Guardar archivo Excel");
        chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

        if (chooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
            File archivoExcel = chooser.getSelectedFile();
            if (!archivoExcel.getName().endsWith(".xls")) {
                archivoExcel = new File(archivoExcel.getAbsolutePath() + ".xls");
            }

            Workbook libro = new HSSFWorkbook();
            try (FileOutputStream archivo = new FileOutputStream(archivoExcel)) {
                Sheet hoja = libro.createSheet("Datos");

                Row filaEncabezado = hoja.createRow(0);
                for (int c = 0; c < tabla.getColumnCount(); c++) {
                    Cell celda = filaEncabezado.createCell(c);
                    celda.setCellValue(tabla.getColumnName(c));
                }

                for (int f = 0; f < tabla.getRowCount(); f++) {
                    Row fila = hoja.createRow(f + 1);
                    for (int c = 0; c < tabla.getColumnCount(); c++) {
                        Cell celda = fila.createCell(c);
                        Object valor = tabla.getValueAt(f, c);
                        if (valor instanceof Number) {
                            celda.setCellValue(Double.parseDouble(valor.toString()));
                        } else {
                            celda.setCellValue(valor != null ? valor.toString() : "");
                        }
                    }
                }

                libro.write(archivo);
            }

            JOptionPane.showMessageDialog(null, "Archivo Excel exportado exitosamente en: " + archivoExcel.getAbsolutePath());
        }
    }
}
