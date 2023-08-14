package bo.edu.ucb.sis213;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

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
            System.out.println("Conexión exitosa a la base de datos.");
            //Entrada de Usuario exitosa o no
            Usuario usuario = funciones.validarPIN(connection, 3);
            if(usuario != null) {
                System.out.println("PIN correcto. Acceso permitido.");
                mostrarMenu(connection,usuario);
            }else{
                System.out.println("Se agotaron los intentos. Acceso bloqueado.");
                }
          }else{
            System.out.println("No se pudo establecer la Conexion");
          }
        } catch (Exception e) {
          //Ensode Errores
          e.printStackTrace();
        } 
    }
    public static void mostrarMenu(Connection connection,Usuario usuario) throws SQLException {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\nMenú Principal:");
            System.out.println("1. Consultar saldo.");
            System.out.println("2. Realizar un depósito.");
            System.out.println("3. Realizar un retiro.");
            System.out.println("4. Cambiar PIN.");
            System.out.println("5. Salir.");
            System.out.print("Seleccione una opción: ");
            int opcion = scanner.nextInt();

            switch (opcion) {
                case 1:
                    System.out.println("Su saldo actual es de: "+ usuario.getSaldo()+ " Bs");
                    break;
                case 2:
                    funciones.Deposito(connection, usuario);
                    break;
                case 3:
                    funciones.Retiro(connection,usuario);
                    break;
                case 4:
                    funciones.cambiarPIN(connection, usuario);
                    break;
                case 5:
                    System.out.println("Gracias por usar el cajero. ¡Hasta luego!");
                    scanner.close();
                    System.exit(0);
                    break;
                default:
                    System.out.println("Opción no válida. Intente nuevamente.");
            }
        }
    }
  
}
