package co.desofsi.ahorro.entidades;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;

import java.io.ByteArrayOutputStream;
import java.io.Serializable;

import co.desofsi.ahorro.MainActivity;
import co.desofsi.ahorro.R;

public class CategoriaGasto implements Serializable {
    private int id;
    private String nombre;
    private byte[] image;
    private int estado;
    private double presupuesto;
    private double gasto_mensual;
    private String id_user;


    private Bitmap icon;



    public CategoriaGasto() {

    }


    public CategoriaGasto(int id, String nombre, byte[] image, double presupuesto, int estado,String id_user) {
        this.id = id;
        this.nombre = nombre;
        this.image = image;
        this.estado = estado;
        this.presupuesto = presupuesto;
        this.id_user = id_user;
    }

    public CategoriaGasto(int id, String nombre, byte[] image, double presupuesto, int estado, double gasto_mensual) {
        this.id = id;
        this.nombre = nombre;
        this.image = image;
        this.estado = estado;
        this.presupuesto = presupuesto;
        this.gasto_mensual = gasto_mensual;
    }

    public String getId_user() {
        return id_user;
    }

    public void setId_user(String id_user) {
        this.id_user = id_user;
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


    public void loadCategoryGasto(MainActivity mainActivity) {
        icon = BitmapFactory.decodeResource(mainActivity.getResources(), R.drawable.agua);
        mainActivity.sqLiteHelper.insertDataCategoriaGastos("Agua", imageViewToByte(icon), 0, 1,MainActivity.id_user);



        icon = BitmapFactory.decodeResource(mainActivity.getResources(), R.drawable.abrigo);
        mainActivity.sqLiteHelper.insertDataCategoriaGastos("Abrigo", imageViewToByte(icon), 0, 1,MainActivity.id_user);


        icon = BitmapFactory.decodeResource(mainActivity.getResources(), R.drawable.ambulancia);
        mainActivity.sqLiteHelper.insertDataCategoriaGastos("Ambulancia", imageViewToByte(icon), 0, 1,MainActivity.id_user);
    }


    private byte[] imageViewToByte(Bitmap bitmap_image) {
        Bitmap bitmap = bitmap_image;
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }
}

