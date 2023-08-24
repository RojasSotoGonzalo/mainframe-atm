package bo.edu.ucb.sis213.view;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

import bo.edu.ucb.sis213.bl.User;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuForm extends JFrame {
    public MenuForm(User usuario) {
        setTitle("ATM");
        setSize(700, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        JLabel nameLabel = new JLabel("Nombre: " + usuario.getNombre());
        nameLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        nameLabel.setBorder(new EmptyBorder(20, 0, 0, 0)); // Añadir espacio superior
        panel.add(nameLabel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(2, 2, 20, 20));
         
        JButton saldoButton = new JButton("Consultar Saldo");
        JButton depositoButton = new JButton("Realizar Depósito");
        JButton retiroButton = new JButton("Realizar Retiro");
        JButton pinButton = new JButton("Cambiar PIN");
        JButton exitButton = new JButton("Salir");
        exitButton.setBorder(new EmptyBorder(20, 20, 20, 20));
        // Código de ActionListener para cada botón (sin cambios)
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
                DepositoForm asc=new DepositoForm(usuario);
                dispose();
                asc.setVisible(true);
            }
        });

        retiroButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Lógica para realizar un retiro
                RetiroForm asd=new RetiroForm(usuario);
                dispose();
                asd.setVisible(true);
            }
        });

        pinButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Lógica para cambiar el PIN
                CambiarPINForm ab=new CambiarPINForm(usuario);
                dispose();
                ab.setVisible(true);
            }
        });

        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Gracias por usar el cajero. ¡Hasta luego!");
                dispose();
                System.exit(0);
            }
        });
        // Agregar un borde vacío para separar los botones
        saldoButton.setBorder(new EmptyBorder(20, 20, 20, 20));
        depositoButton.setBorder(new EmptyBorder(20, 20, 20, 20));
        retiroButton.setBorder(new EmptyBorder(20, 20, 20, 20));
        pinButton.setBorder(new EmptyBorder(20, 20, 20, 20));

        buttonPanel.add(saldoButton);
        buttonPanel.add(depositoButton);
        buttonPanel.add(retiroButton);
        buttonPanel.add(pinButton);

        panel.add(buttonPanel, BorderLayout.CENTER);
         // Ajustar el margen del botón Salir
        panel.add(exitButton, BorderLayout.SOUTH);

        add(panel);
        setLocationRelativeTo(null);
        setVisible(true);
    }
}