package bo.edu.ucb.sis213.util;

public class ATMException extends RuntimeException {
    //manejo de errores y analizis de message
    public ATMException(){
    }
    public ATMException(String message){
        super(message);
    }
    public ATMException(String message,Throwable cause){
       super(message,cause);
    }
    public ATMException(Throwable cause){
        super(cause);
    }
}
