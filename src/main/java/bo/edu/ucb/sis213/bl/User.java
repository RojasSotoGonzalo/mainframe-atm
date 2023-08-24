package bo.edu.ucb.sis213.bl;

import java.math.BigDecimal;

public class User {
    private int id;
    private String nombre;
    private int pin;
    private BigDecimal saldo;
    private String  alias;
    public User() {
    }
    //usurario basico con get set y funcion deacuerdo POO
    public User(int id, String nombre, int pin, BigDecimal saldo, String alias) {
        this.id = id;
        this.nombre = nombre;
        this.pin = pin;
        this.saldo = saldo;
        this.alias = alias;
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
    public BigDecimal getSaldo() {
        return saldo;
    }
    public void setSaldo(BigDecimal saldo) {
        this.saldo = saldo;
    }
    public String getAlias() {
        return alias;
    }
    public void setAlias(String alias) {
        this.alias = alias;
    }
    @Override
    public String toString() {
        return "Usuario [id=" + id + ", nombre=" + nombre + ", pin=" + pin + ", saldo=" + saldo + ", alias=" + alias
                + "]";
    }           

  
}
