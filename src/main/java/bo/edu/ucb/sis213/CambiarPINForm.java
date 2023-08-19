package bo.edu.ucb.sis213;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CambiarPINForm extends JFrame{
    private JPasswordField pinActualField;
    private JPasswordField nuevoPINField;
    private JPasswordField confirmarPINField;
    public CambiarPINForm(Connection connection, Usuario usuario) {
        setTitle("ATM - Cambiar PIN");
        setSize(400, 250);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 1));

        JLabel pinActualLabel = new JLabel("Ingrese el PIN actual:");
        panel.add(pinActualLabel);

        pinActualField = new JPasswordField();
        panel.add(pinActualField);

        JLabel nuevoPINLabel = new JLabel("Ingrese el nuevo PIN:");
        panel.add(nuevoPINLabel);

        nuevoPINField = new JPasswordField();
        panel.add(nuevoPINField);

        JLabel confirmarPINLabel = new JLabel("Vuelva a ingresar el nuevo PIN:");
        panel.add(confirmarPINLabel);

        confirmarPINField = new JPasswordField();
        panel.add(confirmarPINField);

        JButton cambiarPINButton = new JButton("Cambiar PIN");
        cambiarPINButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cambiarPIN(connection,usuario);
            }
        });
        panel.add(cambiarPINButton);

        add(panel);
        setVisible(true);
    }
    private void cambiarPIN(Connection connection, Usuario usuario) {
        char[] pinActualChars = pinActualField.getPassword();
        int pinActual = Integer.parseInt(new String(pinActualChars));

        if (pinActual == usuario.getPin()) {
            char[] nuevoPINChars = nuevoPINField.getPassword();
            int nuevoPIN = Integer.parseInt(new String(nuevoPINChars));

            char[] confirmarPINChars = confirmarPINField.getPassword();
            int confirmarPIN = Integer.parseInt(new String(confirmarPINChars));

            if (nuevoPIN == confirmarPIN) {
                try {
                    String updatePINQuery = "UPDATE usuarios SET pin = ? WHERE id = ?";
                    PreparedStatement updatePINStatement = connection.prepareStatement(updatePINQuery);
                    updatePINStatement.setInt(1, nuevoPIN);
                    updatePINStatement.setInt(2, usuario.getId());
                    updatePINStatement.executeUpdate();

                    JOptionPane.showMessageDialog(null, "PIN actualizado exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                    LoginForm();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Error al actualizar el PIN.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(null, "Los PINs no coinciden. Cambio de PIN cancelado.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(null, "PIN incorrecto. Cambio de PIN cancelado.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    private void LoginForm() throws SQLException {
        dispose(); // Cierra la ventana actual de LoginForm
        try {
            //Permite la conexion con la Base de datos atm  
            Connection connection = Conexion.obtenerConexion();
            //Verifica la conexion
            if(connection != null) { 
              LoginForm loginForm = new LoginForm(connection);
              loginForm.setVisible(true);
            }else{
              JOptionPane.showMessageDialog(null,"No se pudo establecer la Conexion",null, JOptionPane.ERROR_MESSAGE, null);
            }
          } catch (Exception e) {
            //Ensode Errores
            e.printStackTrace();
          } 
    }
}
