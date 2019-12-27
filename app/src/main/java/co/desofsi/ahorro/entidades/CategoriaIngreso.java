package co.desofsi.ahorro.entidades;

import java.io.Serializable;

public class CategoriaIngreso implements Serializable {
    private int id;
    private String nombre;
    private byte[] image;
    private int estado;

    public CategoriaIngreso() {
    }

    public CategoriaIngreso(int id, String nombre, byte[] image, int estado) {
        this.id = id;
        this.nombre = nombre;
        this.image = image;
        this.estado = estado;
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
