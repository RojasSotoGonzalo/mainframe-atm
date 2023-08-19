package bo.edu.ucb.sis213;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.*;

public class LoginForm extends JFrame{
    private JTextField aliasField;
    private JPasswordField pinField;
    int intentosRestantes = 3; // Número de intentos restantes

    public LoginForm(Connection connection) throws SQLException{
        setTitle("ATM");
        setSize(500, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 6));

        JLabel aliasLabel = new JLabel("Alias:");
        aliasField = new JTextField();
        JLabel pinLabel = new JLabel("PIN:");
        pinField = new JPasswordField();

        JButton loginButton = new JButton("Login");
        JButton exitButton = new JButton("Exit");

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                //lógica de verificación del alias y el PIN
                try {
                    // Aqui se configura los valores del alias y Pin
                    String alias = aliasField.getText();
                    char[] pinChars = pinField.getPassword();
                    //Entrada de Usuario exitosa o no 
                    Usuario usuario = validarPIN(connection, alias, pinChars);
                    if (usuario != null) {
                        JOptionPane.showMessageDialog(null,"PIN correcto. Acceso permitido.");
                        abrirMenu(connection,usuario);
                    } else {
                        JOptionPane.showMessageDialog(null, "Alias o PIN incorrecto. Intentos restantes: " + (intentosRestantes - 1), null, JOptionPane.WARNING_MESSAGE, null);
                        aliasField.setText("");
                        pinField.setText("");
                        intentosRestantes--;
                    }
                    if(intentosRestantes == 0) {
                        System.exit(0);
                    } 
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
                
            }
        });

        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        panel.add(aliasLabel);
        panel.add(aliasField);
        panel.add(pinLabel);
        panel.add(pinField);
        panel.add(loginButton);
        panel.add(exitButton);

        add(panel);
        setVisible(true);
    }
    public static Usuario validarPIN(Connection connection, String alias, char[] pinChars) throws SQLException {
        String pinIngresado = new String(pinChars);
        String query = "SELECT * FROM usuarios WHERE alias = ? AND pin = ? ";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, alias);
        preparedStatement.setString(2, pinIngresado);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            int id = resultSet.getInt("id");
            String nombre = resultSet.getString("nombre");
            int pinDB = resultSet.getInt("pin");
            double saldo = resultSet.getDouble("saldo");
            String usuarioAlias = resultSet.getString("alias");
            Usuario usuario = new Usuario(id, nombre, pinDB, saldo, usuarioAlias);
            resultSet.close();
            preparedStatement.close();
            return usuario;
        }
        resultSet.close();
        preparedStatement.close();
        // No hay llamada recursiva aquí, simplemente regresamos y dejamos que la interfaz de usuario maneje el siguiente intento
        return null; // Llamada recursiva con intentos reducidos
    }
    private void abrirMenu(Connection connection, Usuario usuario) {
        dispose(); // Cierra la ventana actual de LoginForm
        
        // Crea y muestra la ventana del menú principal
        MenuForm menuPrincipalForm = new MenuForm(connection, usuario);
        menuPrincipalForm.setVisible(true);
    }
}

