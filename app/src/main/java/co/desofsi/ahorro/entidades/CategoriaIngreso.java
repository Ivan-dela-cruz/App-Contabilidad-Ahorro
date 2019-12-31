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

    private byte[] imageViewToByte(Bitmap bitmap_image) {
        Bitmap bitmap = bitmap_image;
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }













    public void loadCategoryIngreso(MainActivity mainActivity) {
        icon = BitmapFactory.decodeResource(mainActivity.getResources(), R.drawable.billetera_in);
        mainActivity.sqLiteHelper.insertDataCategoriaIngresos("Salario", imageViewToByte(icon), 1,MainActivity.id_user);



        icon = BitmapFactory.decodeResource(mainActivity.getResources(), R.drawable.bolsa);
        mainActivity.sqLiteHelper.insertDataCategoriaIngresos("Premios", imageViewToByte(icon),  1,MainActivity.id_user);


        icon = BitmapFactory.decodeResource(mainActivity.getResources(), R.drawable.regalo);
        mainActivity.sqLiteHelper.insertDataCategoriaIngresos("Subsidio", imageViewToByte(icon), 1,MainActivity.id_user);


        icon = BitmapFactory.decodeResource(mainActivity.getResources(), R.drawable.ventas);
        mainActivity.sqLiteHelper.insertDataCategoriaIngresos("Venta", imageViewToByte(icon), 1,MainActivity.id_user);




        icon = BitmapFactory.decodeResource(mainActivity.getResources(), R.drawable.alquiler_in);
        mainActivity.sqLiteHelper.insertDataCategoriaIngresos("Alquiler", imageViewToByte(icon), 1,MainActivity.id_user);


        icon = BitmapFactory.decodeResource(mainActivity.getResources(), R.drawable.rembolso_in);
        mainActivity.sqLiteHelper.insertDataCategoriaIngresos("Rembolso", imageViewToByte(icon),  1,MainActivity.id_user);


        icon = BitmapFactory.decodeResource(mainActivity.getResources(), R.drawable.cupones_in);
        mainActivity.sqLiteHelper.insertDataCategoriaIngresos("Cupones", imageViewToByte(icon),  1,MainActivity.id_user);


        icon = BitmapFactory.decodeResource(mainActivity.getResources(), R.drawable.maquina);
        mainActivity.sqLiteHelper.insertDataCategoriaIngresos("Lotería", imageViewToByte(icon), 1,MainActivity.id_user);

        icon = BitmapFactory.decodeResource(mainActivity.getResources(), R.drawable.line_chart);
        mainActivity.sqLiteHelper.insertDataCategoriaIngresos("Dividendos", imageViewToByte(icon), 1,MainActivity.id_user);

        icon = BitmapFactory.decodeResource(mainActivity.getResources(), R.drawable.inversion_in);
        mainActivity.sqLiteHelper.insertDataCategoriaIngresos("Inveriones", imageViewToByte(icon), 1,MainActivity.id_user);


        icon = BitmapFactory.decodeResource(mainActivity.getResources(), R.drawable.otros_in);
        mainActivity.sqLiteHelper.insertDataCategoriaIngresos("Otros", imageViewToByte(icon),  1,MainActivity.id_user);











    }



}
