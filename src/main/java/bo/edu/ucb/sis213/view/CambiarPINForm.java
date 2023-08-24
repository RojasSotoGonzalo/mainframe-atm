package bo.edu.ucb.sis213.view;


import java.awt.*;
import javax.swing.*;

import bo.edu.ucb.sis213.bl.User;
import bo.edu.ucb.sis213.bl.funcionabl;
import bo.edu.ucb.sis213.util.ATMException;


public class CambiarPINForm extends JFrame {
    private JPasswordField pinActualField;
    private JPasswordField nuevoPINField;
    private JPasswordField confirmarPINField;

    public CambiarPINForm(User usuario) {
        setTitle("ATM - Cambiar PIN");
        setSize(700, 400); // Ajustado el alto para evitar superposiciones
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL; // Rellenar horizontalmente

        JLabel titleLabel = new JLabel("Cambiar PIN");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(titleLabel, gbc);

        addLabelAndField(panel, gbc, "Ingrese el PIN actual:", pinActualField = new JPasswordField());
        addLabelAndField(panel, gbc, "Ingrese el nuevo PIN:", nuevoPINField = new JPasswordField());
        addLabelAndField(panel, gbc, "Vuelva a ingresar el nuevo PIN:", confirmarPINField = new JPasswordField());

        JButton cambiarPINButton = new JButton("Cambiar PIN");
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(20, 10, 10, 10);
        panel.add(cambiarPINButton, gbc);

        JButton exitButton = new JButton("Cancelar");
        gbc.gridx = 1;
        panel.add(exitButton, gbc);

        cambiarPINButton.addActionListener(e -> {
            char[] pinActualChars = pinActualField.getPassword();
            int pinActual = Integer.parseInt(new String(pinActualChars));
            char[] nuevoPINChars = nuevoPINField.getPassword();
            int nuevoPIN = Integer.parseInt(new String(nuevoPINChars));
            char[] confirmarPINChars = confirmarPINField.getPassword();
            int confirmarPIN = Integer.parseInt(new String(confirmarPINChars));
            try {
               funcionabl.cambiarPIN(usuario,pinActual,nuevoPIN,confirmarPIN);
               JOptionPane.showMessageDialog(null, "PIN actualizado exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
               LoginForm();                
            } catch (ATMException ex) {
                // Errores dentro de funciones
                JOptionPane.showMessageDialog(null,  ex.getMessage());
            }

        });

        exitButton.addActionListener(e -> abrirMenu(usuario));

        add(panel);
        setLocationRelativeTo(null); // Centrar la ventana en la pantalla
        setVisible(true);
    }

    private void addLabelAndField(JPanel panel, GridBagConstraints gbc, String labelText, JComponent field) {
        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Arial", Font.BOLD, 15));
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 1;
        panel.add(label, gbc);

        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL; // Rellenar horizontalmente
        panel.add(field, gbc);
    }
    //funcion grafica encargada de abrir el inicio
    private void LoginForm() {
        dispose();
        LoginForm loginForm = new LoginForm();
        loginForm.setVisible(true);
    }
    //funcion encargada de abrir menu
    private void abrirMenu(User usuario) {
        dispose();
        MenuForm menuPrincipalForm = new MenuForm(usuario);
        menuPrincipalForm.setVisible(true);
    }
}