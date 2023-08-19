package bo.edu.ucb.sis213;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DepositoForm extends JFrame{
    private JTextField depositoField;
    public DepositoForm(Connection connection, Usuario usuario) {
        setTitle("ATM - Realizar Depósito");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 1));

        JLabel saldoLabel = new JLabel("Su saldo actual es de: " + usuario.getSaldo() + " Bs");
        panel.add(saldoLabel);

        JLabel depositoLabel = new JLabel("Cuánto desea depositar: ");
        panel.add(depositoLabel);

        depositoField = new JTextField();
        panel.add(depositoField);

        JButton depositarButton = new JButton("Realizar Depósito");
        depositarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                realizarDeposito(connection, usuario);
            }
        });
        panel.add(depositarButton);

        add(panel);
        setVisible(true);
    }
    private void realizarDeposito(Connection connection, Usuario usuario) {
        double deposito = Double.parseDouble(depositoField.getText());
        if (deposito <= 0) {
            JOptionPane.showMessageDialog(null, "Monto inválido.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        double nuevoSaldo = usuario.getSaldo() + deposito;
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
            insertHistoricoStatement.setString(2, "depósito");
            insertHistoricoStatement.setDouble(3, deposito);
            insertHistoricoStatement.executeUpdate();

            JOptionPane.showMessageDialog(null, "Depósito exitoso.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            abrirMenu(connection, usuario);
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al realizar el depósito.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    private void abrirMenu(Connection connection, Usuario usuario) {
        dispose(); // Cierra la ventana actual
        // Crea y muestra la ventana 
        MenuForm menuPrincipalForm = new MenuForm(connection, usuario);
        menuPrincipalForm.setVisible(true);
    } 
}
