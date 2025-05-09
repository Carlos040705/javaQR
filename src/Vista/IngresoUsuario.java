/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package Vista;

import Controlador.ControladorIngresoUsuario;

/**
 *
 * @author varga
 */
public class IngresoUsuario extends javax.swing.JPanel {

    /**
     * Creates new form IngresoUsuario
     */
    ControladorIngresoUsuario controlador;

    public IngresoUsuario() {
        initComponents();
        controlador = new ControladorIngresoUsuario(this);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        kGradientPanel1 = new keeptoo.KGradientPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        cajaMatricula = new javax.swing.JTextField();
        etiquetaMatricula = new javax.swing.JLabel();
        comboPersona = new javax.swing.JComboBox<>();
        etiquetaMatricula1 = new javax.swing.JLabel();

        setLayout(new java.awt.GridBagLayout());

        kGradientPanel1.setkEndColor(new java.awt.Color(68, 184, 194));
        kGradientPanel1.setkStartColor(new java.awt.Color(66, 132, 188));

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("O ID PARA INICIAR SESIÓN");
        jLabel3.setMinimumSize(new java.awt.Dimension(200, 150));
        jLabel3.setName(""); // NOI18N
        jLabel3.setPreferredSize(new java.awt.Dimension(200, 150));

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("PUEDES USAR TU MATRÍCULA, NO. PLAZA ");
        jLabel4.setMinimumSize(new java.awt.Dimension(200, 150));
        jLabel4.setName(""); // NOI18N
        jLabel4.setPreferredSize(new java.awt.Dimension(200, 150));

        jLabel5.setFont(new java.awt.Font("Roboto Black", 2, 24)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(200, 221, 235));
        jLabel5.setText("INICIO DE SESION");
        jLabel5.setMinimumSize(new java.awt.Dimension(200, 150));
        jLabel5.setName(""); // NOI18N
        jLabel5.setPreferredSize(new java.awt.Dimension(200, 150));

        cajaMatricula.setFont(new java.awt.Font("Microsoft JhengHei", 0, 18)); // NOI18N
        cajaMatricula.setMaximumSize(new java.awt.Dimension(100, 30));
        cajaMatricula.setMinimumSize(new java.awt.Dimension(100, 30));
        cajaMatricula.setPreferredSize(new java.awt.Dimension(100, 30));

        etiquetaMatricula.setFont(new java.awt.Font("Microsoft JhengHei UI", 1, 18)); // NOI18N
        etiquetaMatricula.setForeground(new java.awt.Color(200, 221, 235));
        etiquetaMatricula.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        etiquetaMatricula.setText("Matrícula:");
        etiquetaMatricula.setMaximumSize(new java.awt.Dimension(90, 30));
        etiquetaMatricula.setMinimumSize(new java.awt.Dimension(90, 30));
        etiquetaMatricula.setPreferredSize(new java.awt.Dimension(90, 30));

        comboPersona.setBackground(new java.awt.Color(210, 231, 255));
        comboPersona.setFont(new java.awt.Font("Lucida Sans Typewriter", 0, 18)); // NOI18N
        comboPersona.setForeground(new java.awt.Color(102, 100, 200));
        comboPersona.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Alumno", "Docente", "Visitante" }));
        comboPersona.setBorder(null);
        comboPersona.setEditor(null);
        comboPersona.setMinimumSize(new java.awt.Dimension(200, 30));
        comboPersona.setPreferredSize(new java.awt.Dimension(200, 30));
        comboPersona.setRenderer(null);

        etiquetaMatricula1.setFont(new java.awt.Font("Microsoft JhengHei UI", 1, 18)); // NOI18N
        etiquetaMatricula1.setForeground(new java.awt.Color(200, 221, 235));
        etiquetaMatricula1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        etiquetaMatricula1.setText("Tipo:");
        etiquetaMatricula1.setToolTipText("");
        etiquetaMatricula1.setMaximumSize(new java.awt.Dimension(90, 30));
        etiquetaMatricula1.setMinimumSize(new java.awt.Dimension(90, 30));
        etiquetaMatricula1.setPreferredSize(new java.awt.Dimension(90, 30));

        javax.swing.GroupLayout kGradientPanel1Layout = new javax.swing.GroupLayout(kGradientPanel1);
        kGradientPanel1.setLayout(kGradientPanel1Layout);
        kGradientPanel1Layout.setHorizontalGroup(
            kGradientPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(kGradientPanel1Layout.createSequentialGroup()
                .addGap(44, 44, 44)
                .addGroup(kGradientPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 287, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(kGradientPanel1Layout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addGroup(kGradientPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 287, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 287, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(222, 222, 222)
                .addGroup(kGradientPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(etiquetaMatricula1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(kGradientPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(cajaMatricula, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(comboPersona, 0, 236, Short.MAX_VALUE)
                        .addComponent(etiquetaMatricula, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(338, Short.MAX_VALUE))
        );
        kGradientPanel1Layout.setVerticalGroup(
            kGradientPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(kGradientPanel1Layout.createSequentialGroup()
                .addGroup(kGradientPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(kGradientPanel1Layout.createSequentialGroup()
                        .addGap(252, 252, 252)
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, kGradientPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(etiquetaMatricula1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(comboPersona, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(etiquetaMatricula, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(kGradientPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cajaMatricula, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(187, Short.MAX_VALUE))
        );

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        add(kGradientPanel1, gridBagConstraints);
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JTextField cajaMatricula;
    public javax.swing.JComboBox<String> comboPersona;
    public javax.swing.JLabel etiquetaMatricula;
    public javax.swing.JLabel etiquetaMatricula1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private keeptoo.KGradientPanel kGradientPanel1;
    // End of variables declaration//GEN-END:variables
}
