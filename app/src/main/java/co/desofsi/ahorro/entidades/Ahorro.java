package co.desofsi.ahorro.entidades;


import java.io.Serializable;

public class Ahorro implements Serializable {


    private int id;
    private String descripcion;
    private byte[] imagen;
    private double valor;
    private String fecha;
    private double porcentaje;
    private double mensual;
    private int dias;
    private int meses;
    private int anio;
    private int estado;
    private int id_cat;
    private String id_user;

    public Ahorro(int id, String descripcion, byte[] imagen, double valor, String fecha, double porcentaje, double mensual, int dias, int meses, int anio, int estado, int id_cat, String id_user) {
        this.id = id;
        this.descripcion = descripcion;
        this.imagen = imagen;
        this.valor = valor;
        this.fecha = fecha;
        this.porcentaje = porcentaje;
        this.mensual = mensual;
        this.dias = dias;
        this.meses = meses;
        this.anio = anio;
        this.estado = estado;
        this.id_cat = id_cat;
        this.id_user = id_user;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public byte[] getImagen() {
        return imagen;
    }

    public void setImagen(byte[] imagen) {
        this.imagen = imagen;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public double getPorcentaje() {
        return porcentaje;
    }

    public void setPorcentaje(double porcentaje) {
        this.porcentaje = porcentaje;
    }

    public double getMensual() {
        return mensual;
    }

    public void setMensual(double mensual) {
        this.mensual = mensual;
    }

    public int getDias() {
        return dias;
    }

    public void setDias(int dias) {
        this.dias = dias;
    }

    public int getMeses() {
        return meses;
    }

    public void setMeses(int meses) {
        this.meses = meses;
    }

    public int getAnio() {
        return anio;
    }

    public void setAnio(int anio) {
        this.anio = anio;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public int getId_cat() {
        return id_cat;
    }

    public void setId_cat(int id_cat) {
        this.id_cat = id_cat;
    }

    public String getId_user() {
        return id_user;
    }

    public void setId_user(String id_user) {
        this.id_user = id_user;
    }
}
