package bo.edu.ucb.sis213.view;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

import bo.edu.ucb.sis213.bl.User;
import bo.edu.ucb.sis213.bl.funcionabl;

public class LoginForm extends JFrame{
    private JTextField aliasField;
    private JPasswordField pinField;
    public LoginForm(){
        setTitle("ATM");
        setSize(700, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(7, 2, 10, 10)); 
        JLabel titleLabel = new JLabel("BIENVENIDO ATM");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24)); // Tamaño de fuente y estilo
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(titleLabel);

        JLabel aliasLabel = new JLabel("Alias:");
        aliasLabel.setFont(new Font("Arial", Font.BOLD,20)); // Tamaño
        aliasField = new JTextField();
                
        JLabel pinLabel = new JLabel("PIN:");
        pinLabel.setFont(new Font("Arial", Font.BOLD,20));
        pinField = new JPasswordField();
        
        JButton loginButton = new JButton("Login");
        loginButton.setMargin(new Insets(20, 20, 20, 20)); // Margen de 10 píxeles
        JButton exitButton = new JButton("Exit");
        exitButton.setMargin(new Insets(20, 20, 20, 20)); // Margen de 10 píxeles

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                //lógica de verificación del alias y el PIN
                // Aqui se configura los valores del alias y Pin
                String alias = aliasField.getText();
                char[] pinChars = pinField.getPassword();
                //Entrada de Usuario exitosa o no 
                User usuario = funcionabl.validarPIN(alias, pinChars);
                if (usuario != null) {
                    JOptionPane.showMessageDialog(null,"PIN correcto. Acceso permitido.");
                    abrirMenu(usuario);
                } else {
                    JOptionPane.showMessageDialog(null, "Alias o PIN incorrecto. Intentos restantes: " + funcionabl.getIntentosRestantes(), null, JOptionPane.WARNING_MESSAGE, null);
                    aliasField.setText("");
                    pinField.setText("");
                    funcionabl.decrementarIntentos();
                }
                if(funcionabl.getIntentosRestantes() == 0) {
                    System.exit(0);
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
    private void abrirMenu( User usuario) {
        dispose(); // Cierra la ventana actual de LoginForm
        // Crea y muestra la ventana del menú principal
        MenuForm menuPrincipalForm = new MenuForm(usuario);
        menuPrincipalForm.setVisible(true);
    }
}

