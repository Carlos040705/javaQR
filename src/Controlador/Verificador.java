package Controlador;

import java.awt.Component;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.util.ArrayList;
import java.util.regex.Pattern;
import javax.swing.JComboBox;

public class Verificador {

    ObtenerComponentes chamba;
    JPanel panel;

    public Verificador() {
        chamba = new ObtenerComponentes();
    }

    public void limpiarDatos(JPanel panel) {
        ArrayList<JTextField> cajasTexto = chamba.obtenerTextFields(panel);
        for (JTextField cajaTexto : cajasTexto) {
            cajaTexto.setText("");
        }
    }

    public void LimpiarComboBox(JPanel panel) {
        ArrayList<JComboBox> combos = chamba.obtenerCombos(panel);
        for (JComboBox combo : combos) {
            combo.setSelectedIndex(0);
        }
    }

    public boolean verificarCajaVacia(JPanel panel) {
        ArrayList<JTextField> cajasTexto = chamba.obtenerTextFields(panel);
        for (JTextField cajaTexto : cajasTexto) {
            if (cajaTexto.getText().isBlank()) {
                String dep = "Falta información ";
                JOptionPane.showMessageDialog(null, dep);
                return false;
            }
        }
        return true;
    }

    public boolean verificarComboBox(JPanel panel) {
        ArrayList<JComboBox> combos = chamba.obtenerCombos(panel);
        for (JComboBox combo : combos) {
            if (combo.getSelectedIndex() == 0) {
                String dep = "Falta información ";
                JOptionPane.showMessageDialog(null, dep);
                return false;
            }
        }
        return true;
    }

    public JTextField campoLleno(JPanel panel) {
        ArrayList<JTextField> cajasTexto = chamba.obtenerTextFields(panel);
        for (JTextField cajaTexto : cajasTexto) {
            if (!(cajaTexto.getText().isBlank())) {
                return cajaTexto;
            }
        }
        Component[] componentes = panel.getComponents();
        for (Component componente : componentes) {
            if (componente instanceof JTextField) {
                JTextField cajaTexto = ((JTextField) componente);
                if (!(cajaTexto.getText().isBlank())) {
                    return cajaTexto;
                }
            }
        }
        return null;
    }

    public String validarMatricula(String matricula) {
        String res = null;
        String regex = "^[A-Za-z0-9]+$";
        long cantidadLetras = matricula.chars()
                .filter(Character::isLetter)
                .count();
        if (matricula.length() == 0) {
            res = "";
            return res;
        } else if (matricula.length() != 7 && matricula.length() != 9) {
            res = "<html><center>La matrícula solo puede componerse <p> por 8 o 10 caracteres.";
            return res;
        } else if (cantidadLetras > 1) {
            res = "<html><center>La matrícula solo puede tener <p> una letra como máximo.";
            return res;
        } else if (!Pattern.matches(regex, matricula)) {
            res = "<html><center>La matrícula solo puede contener <p> letras y números.";
            return res;
        }
        return res;
    }

    public String validarNoPlaza(String noPlaza) {
        String res = null;
        long cantidadLetras = noPlaza.chars()
                .filter(Character::isLetter)
                .count();
        if (noPlaza.length() == 0) {
            res = "";
            return res;
        } else if (cantidadLetras > 0) {
            return res = "No debe tener letras";
        } else if (noPlaza.length() != 4) {
            return res = "<html><center>El tamaño permitido <p> es de 4 caracteres";
        }
        return res;
    }

    public boolean validarEntero(String text) {
        try {
            Integer.parseInt(text);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

}
