package Controlador;

import java.awt.Component;
import java.util.ArrayList;
import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class ObtenerComponentes {

    public ObtenerComponentes() {
    }

    public ArrayList<AbstractButton> obtenerBotones(JPanel panelX) {
        ArrayList<AbstractButton> botones = new ArrayList<>();
        this.buscarBotones(panelX, botones);
        return botones;
    }

    private void buscarBotones(Component panelXBoton, ArrayList<AbstractButton> botones) {
        if (panelXBoton instanceof JButton) {
            botones.add((JButton) panelXBoton);
        } else if (panelXBoton instanceof JPanel) {
            for (Component componente : ((JPanel) panelXBoton).getComponents()) {
                buscarBotones(componente, botones);
            }
        }
    }

    public ArrayList<JTextField> obtenerTextFields(JPanel panelX) {
        ArrayList<JTextField> textFields = new ArrayList<>();
        this.buscarTextFields(panelX, textFields);
        return textFields;
    }

    private void buscarTextFields(Component panelXTextField, ArrayList<JTextField> textFields) {
        if (panelXTextField instanceof JTextField) {
            textFields.add((JTextField) panelXTextField);
        } else if (panelXTextField instanceof JPanel) {
            for (Component componente : ((JPanel) panelXTextField).getComponents()) {
                buscarTextFields(componente, textFields);
            }
        }
    }

    public ArrayList<JComboBox> obtenerCombos(JPanel panelX) {
        ArrayList<JComboBox> combos = new ArrayList<>();
        this.buscarCombos(panelX, combos);
        return combos;
    }

    private void buscarCombos(Component panelXCombo, ArrayList<JComboBox> combos) {
        if (panelXCombo instanceof JComboBox) {
            combos.add((JComboBox) panelXCombo);
        } else if (panelXCombo instanceof JPanel) {
            for (Component componente : ((JPanel) panelXCombo).getComponents()) {
                buscarCombos(componente, combos);
            }
        }
    }
}
