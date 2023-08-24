package bo.edu.ucb.sis213.dao;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import bo.edu.ucb.sis213.bl.User;

public class Userdao {
     private static Connection connection = Conexion.obtenerConexion();
     public static User validarPIN(String alias, char[] pinChars) {
        try{
        String pinIngresado = new String(pinChars);
        String query = "SELECT * FROM usuarios WHERE alias = ? AND pin = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, alias);
        preparedStatement.setString(2, pinIngresado);
        ResultSet resultSet = preparedStatement.executeQuery();  
        if (resultSet.next()) {
            int id = resultSet.getInt("id");
            String nombre = resultSet.getString("nombre");
            int pinDB = resultSet.getInt("pin");
            BigDecimal saldo = resultSet.getBigDecimal("saldo");
            String usuarioAlias = resultSet.getString("alias");
            User usuario = new User(id, nombre, pinDB, saldo, usuarioAlias);
            resultSet.close();
            preparedStatement.close();
            return usuario;
        }       
        resultSet.close();
        preparedStatement.close();} catch (Exception e){

        }
        return null;
    }
    public static void realizarDepositooRetiro(User usuario,String a,BigDecimal nuevoSaldo) {
        try {
            String updateSaldoQuery = "UPDATE usuarios SET saldo = ? WHERE id = ?";
            PreparedStatement updateSaldoStatement = connection.prepareStatement(updateSaldoQuery);
            updateSaldoStatement.setBigDecimal(1, nuevoSaldo);
            updateSaldoStatement.setInt(2, usuario.getId());
            updateSaldoStatement.executeUpdate();
            historicaldao.insertHistoricalRecord(usuario.getId(),a, nuevoSaldo);     
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al realizar el retiro.", "Error", JOptionPane.ERROR_MESSAGE);   
        }
    }
    public static void cambiarPIN(User usuario,int nuevoPIN){
        try {
            String updatePINQuery = "UPDATE usuarios SET pin = ? WHERE id = ?";
            PreparedStatement updatePINStatement = connection.prepareStatement(updatePINQuery);
            updatePINStatement.setInt(1, nuevoPIN);
            updatePINStatement.setInt(2, usuario.getId());
            updatePINStatement.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al actualizar el PIN.", "Error", JOptionPane.ERROR_MESSAGE);
        }        
    }

}
