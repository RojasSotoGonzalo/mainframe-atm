package bo.edu.ucb.sis213.bl;

import java.math.BigDecimal;
import bo.edu.ucb.sis213.dao.Userdao;
import bo.edu.ucb.sis213.util.ATMException;

public class funcionabl {
  private static int intentosRestantes = 3;
  public static User validarPIN( String alias, char[] pinChars) {
    return Userdao.validarPIN(alias, pinChars);
 }
  public static void realizarRetiro(User usuario, BigDecimal retiro) {
    if (retiro.compareTo(BigDecimal.ZERO)<= 0 || retiro.compareTo(usuario.getSaldo()) > 0) {
      throw new ATMException("Cantidad no valida");
    }else{
      BigDecimal nuevoSaldo = usuario.getSaldo().subtract(retiro);
      usuario.setSaldo(nuevoSaldo);
      Userdao.realizarDepositooRetiro(usuario,"retiro",nuevoSaldo);
    }
  }
  public static void realizarDeposito(User usuario,BigDecimal deposito) {
      if (deposito.compareTo(BigDecimal.ZERO) <= 0 ) {
          throw new ATMException("Cantidad no valida");
      }else{
      BigDecimal nuevoSaldo = usuario.getSaldo().add(deposito);
      usuario.setSaldo(nuevoSaldo);
      Userdao.realizarDepositooRetiro(usuario,"depósito",nuevoSaldo);
      }
    }
  public static void cambiarPIN(User usuario,int pinActual,int nuevoPIN,int confirmarPIN) {
      if (pinActual == usuario.getPin()) {
        if (nuevoPIN == confirmarPIN) {
          Userdao.cambiarPIN(usuario,nuevoPIN);
        }else{
          throw new ATMException("Los PINs no coinciden. Cambio de PIN cancelado.");
        }
      }else{
        throw new ATMException("PIN incorrecto. Cambio de PIN cancelado.");
      }
  }
  public static int getIntentosRestantes() {
    return intentosRestantes;
  }
  public static void decrementarIntentos() {
    intentosRestantes--;
  }   
}
