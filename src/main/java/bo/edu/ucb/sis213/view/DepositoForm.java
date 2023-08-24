package bo.edu.ucb.sis213.view;

import javax.swing.*;

import bo.edu.ucb.sis213.bl.User;
import bo.edu.ucb.sis213.bl.funcionabl;
import bo.edu.ucb.sis213.util.ATMException;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;


public class DepositoForm extends JFrame{
    private JTextField depositoField;
    public DepositoForm(User usuario) {
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
                BigDecimal deposito= new BigDecimal(depositoField.getText());
                try {
                    funcionabl.realizarDeposito(usuario,deposito);
                    JOptionPane.showMessageDialog(null, "Depósito exitoso.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                    abrirMenu(usuario);
                } catch (ATMException ex) {
                    // Errores dentro de funciones
                    JOptionPane.showMessageDialog(null,  ex.getMessage());
                }

            }
        });
        panel.add(depositarButton);
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                abrirMenu(usuario);
            }
            
        });
        panel.add(exitButton);
        add(panel);
        setVisible(true);
    }
    //funcion encargada de abrir menu
    private void abrirMenu(User usuario) {
        dispose(); // Cierra la ventana actual
        // Crea y muestra la ventana 
        MenuForm menuPrincipalForm = new MenuForm(usuario);
        menuPrincipalForm.setVisible(true);
    } 
}
