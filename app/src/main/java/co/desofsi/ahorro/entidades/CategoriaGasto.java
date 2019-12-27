package co.desofsi.ahorro.entidades;

import java.io.Serializable;

public class CategoriaGasto implements Serializable {
    private int id;
    private String nombre;
    private byte[] image;
    private int estado;
    private double presupuesto;
    private double gasto_mensual;

    public CategoriaGasto(int id, String nombre, byte[] image, double presupuesto, int estado) {
        this.id = id;
        this.nombre = nombre;
        this.image = image;
        this.estado = estado;
        this.presupuesto = presupuesto;
    }

    public CategoriaGasto(int id, String nombre, byte[] image, double presupuesto, int estado, double gasto_mensual) {
        this.id = id;
        this.nombre = nombre;
        this.image = image;
        this.estado = estado;
        this.presupuesto = presupuesto;
        this.gasto_mensual = gasto_mensual;
    }

    public double getGasto_mensual() {
        return gasto_mensual;
    }

    public void setGasto_mensual(double gasto_mensual) {
        this.gasto_mensual = gasto_mensual;
    }

    public double getPresupuesto() {
        return presupuesto;
    }

    public void setPresupuesto(double presupuesto) {
        this.presupuesto = presupuesto;
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

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }
}
