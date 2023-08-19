package bo.edu.ucb.sis213;

import java.sql.Connection;
import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuForm extends JFrame{
     public MenuForm(Connection connection, Usuario usuario) {

        setTitle("ATM");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        JLabel nameLabel = new JLabel("Nombre: " + usuario.getNombre());
        panel.add(nameLabel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(2, 2));

        JButton saldoButton = new JButton("Consultar Saldo");
        JButton depositoButton = new JButton("Realizar Depósito");
        JButton retiroButton = new JButton("Realizar Retiro");
        JButton pinButton = new JButton("Cambiar PIN");

        saldoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Su saldo actual es de: " + usuario.getSaldo() + " Bs");
            }
        });

        depositoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Lógica para realizar un depósito
                abrir(connection, usuario,1);
            }
        });

        retiroButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Lógica para realizar un retiro
                abrir(connection, usuario,2);
            }
        });

        pinButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Lógica para cambiar el PIN
                abrir(connection, usuario,3);
            }
        });

        buttonPanel.add(saldoButton);
        buttonPanel.add(depositoButton);
        buttonPanel.add(retiroButton);
        buttonPanel.add(pinButton);

        panel.add(buttonPanel, BorderLayout.CENTER);

        JButton exitButton = new JButton("Salir");
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Gracias por usar el cajero. ¡Hasta luego!");
                dispose();
                System.exit(0);
            }
        });

        panel.add(exitButton, BorderLayout.SOUTH);

        add(panel);
        setVisible(true);
    
    }
    private void abrir(Connection connection, Usuario usuario,int i){
        dispose(); // Cierra la ventana actual
        switch (i) {
            case 1:
                // Crea y muestra la ventana
                DepositoForm asc=new DepositoForm(connection,usuario);
                asc.setVisible(true); 
                break;
            case 2:
                // Crea y muestra la ventana 
                RetiroForm asd=new RetiroForm(connection,usuario);
                asd.setVisible(true);
                break;
            case 3:
                // Crea y muestra la ventana
                CambiarPINForm ab=new CambiarPINForm(connection,usuario);
                ab.setVisible(true);
                break;    
            default:
                break;
        }
    }

}