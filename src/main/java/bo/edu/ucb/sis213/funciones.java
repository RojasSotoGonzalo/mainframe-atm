package bo.edu.ucb.sis213;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class funciones {
    private static Scanner scanner1=new Scanner(System.in);
    public static Usuario validarPIN(Connection connection, int intentosRestantes) throws SQLException {
        if (intentosRestantes == 0) {
            return null; // No quedan intentos, devuelve null
        }
        System.out.print("Introduce tu PIN: ");
        int pinIngresado = scanner1.nextInt();
        String query = "SELECT * FROM usuarios WHERE pin = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, pinIngresado);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            int id = resultSet.getInt("id");
            String nombre = resultSet.getString("nombre");
            int pinDB = resultSet.getInt("pin");
            double saldo = resultSet.getDouble("saldo");
            Usuario usuario = new Usuario(id, nombre, pinDB, saldo);
            resultSet.close();
            preparedStatement.close();
            return usuario;
        }
        resultSet.close();
        preparedStatement.close();   
        System.out.println("PIN incorrecto. Intentos restantes: " + (intentosRestantes - 1));
        return validarPIN(connection, intentosRestantes - 1); // Llamada recursiva con intentos reducidos
    }
    public static void Retiro(Connection connection,Usuario usu) throws SQLException{
        System.out.println("Su saldo actual es de: "+ usu.getSaldo()+ " Bs");
        System.out.println("Cuanto desea  Retira: ");
        double retiro = scanner1.nextDouble();
        if (retiro <= 0 || retiro > usu.getSaldo()) {
            System.out.println("Monto inválido o saldo insuficiente.");
            return;
        }
        double nuevoSaldo = usu.getSaldo() - retiro;
        usu.setSaldo(nuevoSaldo);
        // Actualizar saldo en la tabla usuarios
        String updateSaldoQuery = "UPDATE usuarios SET saldo = ? WHERE id = ?";
        PreparedStatement updateSaldoStatement = connection.prepareStatement(updateSaldoQuery);
        updateSaldoStatement.setDouble(1, nuevoSaldo);
        updateSaldoStatement.setInt(2, usu.getId());
        updateSaldoStatement.executeUpdate();
        // Registrar la operación en la tabla historico
        String insertHistoricoQuery = "INSERT INTO historico (usuario_id, tipo_operacion, cantidad) VALUES (?, ?, ?)";
        PreparedStatement insertHistoricoStatement = connection.prepareStatement(insertHistoricoQuery);
        insertHistoricoStatement.setInt(1, usu.getId());
        insertHistoricoStatement.setString(2, "retiro");
        insertHistoricoStatement.setDouble(3, retiro);
        insertHistoricoStatement.executeUpdate();
        System.out.println("Retiro exitoso.");       

    }
    public static void Deposito(Connection connection,Usuario usu) throws SQLException{
        System.out.println("Su saldo actual es de: "+ usu.getSaldo()+ " Bs");
        System.out.println("Cuanto desea  Depositar: ");
        double deposito = scanner1.nextDouble();
        if (deposito <= 0) {
            System.out.println("Monto inválido.");
            return;
        }
        double nuevoSaldo = usu.getSaldo()+ deposito;
        usu.setSaldo(nuevoSaldo);
        // Actualizar saldo en la tabla usuarios
        String updateSaldoQuery = "UPDATE usuarios SET saldo = ? WHERE id = ?";
        PreparedStatement updateSaldoStatement = connection.prepareStatement(updateSaldoQuery);
        updateSaldoStatement.setDouble(1, nuevoSaldo);
        updateSaldoStatement.setInt(2, usu.getId());
        updateSaldoStatement.executeUpdate();
        // Registrar la operación en la tabla historico
        String insertHistoricoQuery = "INSERT INTO historico (usuario_id, tipo_operacion, cantidad) VALUES (?, ?, ?)";
        PreparedStatement insertHistoricoStatement = connection.prepareStatement(insertHistoricoQuery);
        insertHistoricoStatement.setInt(1, usu.getId());
        insertHistoricoStatement.setString(2, "depósito");
        insertHistoricoStatement.setDouble(3, deposito);
        insertHistoricoStatement.executeUpdate();
        System.out.println("Depósito exitoso.");    
    }
    public static void cambiarPIN(Connection connection,Usuario usu) throws SQLException{
       //Verificacion del Pin ACTUAL
        System.out.println("Ingrese El pin Actual: ");
       int pinActual = scanner1.nextInt();
       if(pinActual==usu.getPin()){
           System.out.println("Ingrese El nuevo pin: ");
           int nuevopin = scanner1.nextInt();
           System.out.println("Vuelva Ingrese El nuevo pin: ");
           int nuevopin1 = scanner1.nextInt();
           if(nuevopin==nuevopin1){
              // Actualizar pin en la tabla usuarios
              String updateSaldoQuery = "UPDATE usuarios SET pin = ? WHERE id = ?";
              PreparedStatement updateSaldoStatement = connection.prepareStatement(updateSaldoQuery);
              updateSaldoStatement.setInt(1, nuevopin);
              updateSaldoStatement.setInt(2, usu.getId());
              updateSaldoStatement.executeUpdate();
              System.out.println("PIN actualizado exitosamente.");
           }else{
              System.out.println("Los PINs no coinciden. Cambio de PIN cancelado.");
              return;
           }
       }else{
        System.out.println("PIN incorrecto. Cambio de PIN cancelado.");
        return;
       }
    } 
}
