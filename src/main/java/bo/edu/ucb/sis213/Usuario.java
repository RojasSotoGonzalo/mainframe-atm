package bo.edu.ucb.sis213;

public class Usuario {
    private int id;
    private String nombre;
    private int pin;
    private double saldo;
    public Usuario() {
    }
    public Usuario(int id, String nombre, int pin, double saldo) {
        this.id = id;
        this.nombre = nombre;
        this.pin = pin;
        this.saldo = saldo;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public int getPin() {
        return pin;
    }
    public void setPin(int pin) {
        this.pin = pin;
    }
    public double getSaldo() {
        return saldo;
    }
    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }
    @Override
    public String toString() {
        return "usuario [id=" + id + ", nombre=" + nombre + ", pin=" + pin + ", saldo=" + saldo + "]";
    }         
}
