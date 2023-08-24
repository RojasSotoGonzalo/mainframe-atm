package bo.edu.ucb.sis213.dao;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.swing.JOptionPane;


public class historicaldao {
  //Permite la conexion con la Base de datos atm 
  public static void insertHistoricalRecord(int userId, String operationType, BigDecimal amount) {
    try {
        Connection connection = Conexion.obtenerConexion(); // Asegúrate de que Conexion.obtenerConexion() funcione correctamente
        
        if (connection != null) {
            //CODIGO ENCARGADO DE ACTUALIZAR HISTORICO
            String insertHistoricoQuery = "INSERT INTO historico (usuario_id, tipo_operacion, cantidad) VALUES (?, ?, ?)";
            PreparedStatement insertHistoricoStatement = connection.prepareStatement(insertHistoricoQuery);
            insertHistoricoStatement.setInt(1, userId);
            insertHistoricoStatement.setString(2, operationType);
            insertHistoricoStatement.setBigDecimal(3, amount);
            insertHistoricoStatement.executeUpdate();
            
            connection.close();
        } else {
            JOptionPane.showMessageDialog(null, "No se pudo establecer la conexión", null, JOptionPane.ERROR_MESSAGE, null);
        }
    } catch (Exception e) {
        // Manejar la excepción adecuadamente
    }

} 
}
