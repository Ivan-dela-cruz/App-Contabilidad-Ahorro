package co.desofsi.ahorro.entidades;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;
import java.io.Serializable;

import co.desofsi.ahorro.MainActivity;
import co.desofsi.ahorro.R;

public class CategoriaIngreso implements Serializable {
    private int id;
    private String nombre;
    private byte[] image;
    private int estado;
    private String id_user;

    private Bitmap icon;

    public CategoriaIngreso() {
    }

    public CategoriaIngreso(int id, String nombre, byte[] image, int estado,String id_user) {
        this.id = id;
        this.nombre = nombre;
        this.image = image;
        this.estado = estado;
        this.id_user = id_user;
    }

    public String getId_user() {
        return id_user;
    }

    public void setId_user(String id_user) {
        this.id_user = id_user;
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


    public void loadCategoryIngreso(MainActivity mainActivity) {
        icon = BitmapFactory.decodeResource(mainActivity.getResources(), R.drawable.garaje);
        mainActivity.sqLiteHelper.insertDataCategoriaIngresos("Garage", imageViewToByte(icon), 1,MainActivity.id_user);



        icon = BitmapFactory.decodeResource(mainActivity.getResources(), R.drawable.bici);
        mainActivity.sqLiteHelper.insertDataCategoriaIngresos("Bicicleta", imageViewToByte(icon), 1,MainActivity.id_user);


        icon = BitmapFactory.decodeResource(mainActivity.getResources(), R.drawable.tren);
        mainActivity.sqLiteHelper.insertDataCategoriaIngresos("Tren", imageViewToByte(icon), 1,MainActivity.id_user);
    }


    private byte[] imageViewToByte(Bitmap bitmap_image) {
        Bitmap bitmap = bitmap_image;
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }

}
