package co.desofsi.ahorro.entidades;

import com.github.mikephil.charting.components.Description;

import java.io.Serializable;

public class Ahorro implements Serializable {

    private int id;
    private double ingreso;
    private double mensual;
    private double saldo;
    private int porcentaje;
    private int meses;
    private double anios;
    private String fecha;
    private String Description;
    private int id_cat_ahorro;
    private byte[] imagen;

    public Ahorro(int id, double ingreso, double mensual, double saldo, int porcentaje, int meses, double anios, String fecha, String description, int id_cat_ahorro, byte[] imagen) {
        this.id = id;
        this.ingreso = ingreso;
        this.mensual = mensual;
        this.saldo = saldo;
        this.porcentaje = porcentaje;
        this.meses = meses;
        this.anios = anios;
        this.fecha = fecha;
        Description = description;
        this.id_cat_ahorro = id_cat_ahorro;
        this.imagen = imagen;
    }

    public byte[] getImagen() {
        return imagen;
    }

    public void setImagen(byte[] imagen) {
        this.imagen = imagen;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getIngreso() {
        return ingreso;
    }

    public void setIngreso(double ingreso) {
        this.ingreso = ingreso;
    }

    public double getMensual() {
        return mensual;
    }

    public void setMensual(double mensual) {
        this.mensual = mensual;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    public int getPorcentaje() {
        return porcentaje;
    }

    public void setPorcentaje(int porcentaje) {
        this.porcentaje = porcentaje;
    }

    public int getMeses() {
        return meses;
    }

    public void setMeses(int meses) {
        this.meses = meses;
    }

    public double getAnios() {
        return anios;
    }

    public void setAnios(double anios) {
        this.anios = anios;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public int getId_cat_ahorro() {
        return id_cat_ahorro;
    }

    public void setId_cat_ahorro(int id_cat_ahorro) {
        this.id_cat_ahorro = id_cat_ahorro;
    }
}
