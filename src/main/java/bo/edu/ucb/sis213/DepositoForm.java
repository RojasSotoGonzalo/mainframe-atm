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
        setSize(700, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(7, 2,10,10));
        JLabel titleLabel = new JLabel("DEPOSITO");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20)); // Tamaño de fuente y estilo
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(titleLabel);

        JLabel saldoLabel = new JLabel("Su saldo actual es de: " + usuario.getSaldo() + " Bs");
        saldoLabel.setFont(new Font("Arial", Font.BOLD,15));
        panel.add(saldoLabel);

        JLabel depositoLabel = new JLabel("Cuánto desea depositar: ");
        depositoLabel.setFont(new Font("Arial", Font.BOLD,15));
        panel.add(depositoLabel);

        depositoField = new JTextField();
        panel.add(depositoField);

        JButton depositarButton = new JButton("Realizar Depósito");
        depositarButton.setMargin(new Insets(20, 20, 20, 20)); // Margen de 10 píxeles
        JButton exitButton = new JButton("Cancelar");
        exitButton.setMargin(new Insets(20, 20, 20, 20)); // Margen de 10 píxeles
        
        depositarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                realizarDeposito(connection, usuario);
            }
        });
        panel.add(depositarButton);
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                abrirMenu(connection, usuario);
            }
            
        });
        panel.add(exitButton);
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
