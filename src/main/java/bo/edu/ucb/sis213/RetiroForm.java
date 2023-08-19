package bo.edu.ucb.sis213;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class RetiroForm extends JFrame{
    private JTextField retiroField;
    public RetiroForm(Connection connection, Usuario usuario) {
        setTitle("ATM - Realizar Retiro");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 1));

        JLabel saldoLabel = new JLabel("Su saldo actual es de: " + usuario.getSaldo() + " Bs");
        panel.add(saldoLabel);

        JLabel retiroLabel = new JLabel("Cuánto desea retirar: ");
        panel.add(retiroLabel);

        retiroField = new JTextField();
        panel.add(retiroField);

        JButton retirarButton = new JButton("Realizar Retiro");
        retirarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                realizarRetiro(connection, usuario);
            }
        });
        panel.add(retirarButton);

        add(panel);
        setVisible(true);
    }
    private void realizarRetiro(Connection connection, Usuario usuario) {
        double retiro = Double.parseDouble(retiroField.getText());
        if (retiro <= 0 || retiro > usuario.getSaldo()) {
            JOptionPane.showMessageDialog(null, "Monto inválido o saldo insuficiente.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        double nuevoSaldo = usuario.getSaldo() - retiro;
        usuario.setSaldo(nuevoSaldo);

        try {
            String updateSaldoQuery = "UPDATE usuarios SET saldo = ? WHERE id = ?";
            PreparedStatement updateSaldoStatement = connection.prepareStatement(updateSaldoQuery);
            updateSaldoStatement.setDouble(1, nuevoSaldo);
            updateSaldoStatement.setInt(2, usuario.getId());
            updateSaldoStatement.executeUpdate();

            String insertHistoricoQuery = "INSERT INTO historico (usuario_id, tipo_operacion, cantidad) VALUES (?, ?, ?)";
            PreparedStatement insertHistoricoStatement = connection.prepareStatement(insertHistoricoQuery);
            insertHistoricoStatement.setInt(1, usuario.getId());
            insertHistoricoStatement.setString(2, "retiro");
            insertHistoricoStatement.setDouble(3, retiro);
            insertHistoricoStatement.executeUpdate();

            JOptionPane.showMessageDialog(null, "Retiro exitoso.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            abrirMenu(connection, usuario);
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al realizar el retiro.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    private void abrirMenu(Connection connection, Usuario usuario) {
        dispose(); // Cierra la ventana actual
        // Crea y muestra la ventana 
        MenuForm menuPrincipalForm = new MenuForm(connection, usuario);
        menuPrincipalForm.setVisible(true);
    }  
}
