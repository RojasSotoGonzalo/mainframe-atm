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
        setSize(700, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(7, 2,10,10));
        JLabel titleLabel = new JLabel("RETIRAR");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20)); // Tamaño de fuente y estilo
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(titleLabel);
        
        JLabel saldoLabel = new JLabel("Su saldo actual es de: " + usuario.getSaldo() + " Bs");
        saldoLabel.setFont(new Font("Arial", Font.BOLD,15));
        panel.add(saldoLabel);

        JLabel retiroLabel = new JLabel("Cuánto desea retirar: ");
        retiroLabel.setFont(new Font("Arial", Font.BOLD,15));
        panel.add(retiroLabel);

        retiroField = new JTextField();
        panel.add(retiroField);

        JButton retirarButton = new JButton("Realizar Retiro");
        retirarButton.setMargin(new Insets(20, 20, 20, 20));

        JButton exitButton = new JButton("Cancelar");
        exitButton.setMargin(new Insets(20, 20, 20, 20)); // Margen de 10 píxeles
        
        retirarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                realizarRetiro(connection, usuario);
            }
        });
        panel.add(retirarButton);
        
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
