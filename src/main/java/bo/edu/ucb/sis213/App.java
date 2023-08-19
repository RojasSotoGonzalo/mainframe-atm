package bo.edu.ucb.sis213;

import java.sql.Connection;
import javax.swing.JOptionPane;

/**
 * Hello world!
 *
 */
public class App {
   
    public static void main(String[] args) { 
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
