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


    public CategoriaGasto(int id, String nombre, byte[] image, double presupuesto, int estado, String id_user) {
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

    private byte[] imageViewToByte(Bitmap bitmap_image) {
        Bitmap bitmap = bitmap_image;
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }


    public void loadCategoryGasto(MainActivity mainActivity) {
        icon = BitmapFactory.decodeResource(mainActivity.getResources(), R.drawable.comida);
        mainActivity.sqLiteHelper.insertDataCategoriaGastos("Comida", imageViewToByte(icon), 100, 1, MainActivity.id_user);


        icon = BitmapFactory.decodeResource(mainActivity.getResources(), R.drawable.agua);
        mainActivity.sqLiteHelper.insertDataCategoriaGastos("Agua", imageViewToByte(icon), 100, 1, MainActivity.id_user);


        icon = BitmapFactory.decodeResource(mainActivity.getResources(), R.drawable.tren);
        mainActivity.sqLiteHelper.insertDataCategoriaGastos("Transporte", imageViewToByte(icon), 100, 1, MainActivity.id_user);


        icon = BitmapFactory.decodeResource(mainActivity.getResources(), R.drawable.home);
        mainActivity.sqLiteHelper.insertDataCategoriaGastos("Hogar", imageViewToByte(icon), 100, 1, MainActivity.id_user);


        ///chesmos mi ayudnate ñope


        icon = BitmapFactory.decodeResource(mainActivity.getResources(), R.drawable.autos);
        mainActivity.sqLiteHelper.insertDataCategoriaGastos("Automóvil", imageViewToByte(icon), 100, 1, MainActivity.id_user);


        icon = BitmapFactory.decodeResource(mainActivity.getResources(), R.drawable.joytick);
        mainActivity.sqLiteHelper.insertDataCategoriaGastos("Entretenimiento", imageViewToByte(icon), 100, 1, MainActivity.id_user);


        icon = BitmapFactory.decodeResource(mainActivity.getResources(), R.drawable.trajeta);
        mainActivity.sqLiteHelper.insertDataCategoriaGastos("Compras", imageViewToByte(icon), 100, 1, MainActivity.id_user);


        icon = BitmapFactory.decodeResource(mainActivity.getResources(), R.drawable.ropa);
        mainActivity.sqLiteHelper.insertDataCategoriaGastos("Ropa", imageViewToByte(icon), 100, 1, MainActivity.id_user);


        icon = BitmapFactory.decodeResource(mainActivity.getResources(), R.drawable.seguridad);
        mainActivity.sqLiteHelper.insertDataCategoriaGastos("Seguro", imageViewToByte(icon), 100, 1, MainActivity.id_user);


        icon = BitmapFactory.decodeResource(mainActivity.getResources(), R.drawable.documento_per);
        mainActivity.sqLiteHelper.insertDataCategoriaGastos("Impuesto", imageViewToByte(icon), 100, 1, MainActivity.id_user);


        icon = BitmapFactory.decodeResource(mainActivity.getResources(), R.drawable.telefono_ele);
        mainActivity.sqLiteHelper.insertDataCategoriaGastos("Teléfono", imageViewToByte(icon), 100, 1, MainActivity.id_user);


        icon = BitmapFactory.decodeResource(mainActivity.getResources(), R.drawable.cigarro);
        mainActivity.sqLiteHelper.insertDataCategoriaGastos("Cigarrillo", imageViewToByte(icon), 100, 1, MainActivity.id_user);


        icon = BitmapFactory.decodeResource(mainActivity.getResources(), R.drawable.hospital);
        mainActivity.sqLiteHelper.insertDataCategoriaGastos("Salud", imageViewToByte(icon), 100, 1, MainActivity.id_user);


        icon = BitmapFactory.decodeResource(mainActivity.getResources(), R.drawable.pesista);
        mainActivity.sqLiteHelper.insertDataCategoriaGastos("Deporte", imageViewToByte(icon), 100, 1, MainActivity.id_user);


        icon = BitmapFactory.decodeResource(mainActivity.getResources(), R.drawable.coche_fa);
        mainActivity.sqLiteHelper.insertDataCategoriaGastos("Bebe", imageViewToByte(icon), 100, 1, MainActivity.id_user);


        icon = BitmapFactory.decodeResource(mainActivity.getResources(), R.drawable.pez);
        mainActivity.sqLiteHelper.insertDataCategoriaGastos("Mascota", imageViewToByte(icon), 100, 1, MainActivity.id_user);


        icon = BitmapFactory.decodeResource(mainActivity.getResources(), R.drawable.labial);
        mainActivity.sqLiteHelper.insertDataCategoriaGastos("Belleza", imageViewToByte(icon), 100, 1, MainActivity.id_user);


        icon = BitmapFactory.decodeResource(mainActivity.getResources(), R.drawable.tv_ele);
        mainActivity.sqLiteHelper.insertDataCategoriaGastos("Electrónica", imageViewToByte(icon), 100, 1, MainActivity.id_user);


        icon = BitmapFactory.decodeResource(mainActivity.getResources(), R.drawable.papas);
        mainActivity.sqLiteHelper.insertDataCategoriaGastos("Comida", imageViewToByte(icon), 100, 1, MainActivity.id_user);


        icon = BitmapFactory.decodeResource(mainActivity.getResources(), R.drawable.coctel);
        mainActivity.sqLiteHelper.insertDataCategoriaGastos("Vino", imageViewToByte(icon), 100, 1, MainActivity.id_user);


        icon = BitmapFactory.decodeResource(mainActivity.getResources(), R.drawable.legunbres);
        mainActivity.sqLiteHelper.insertDataCategoriaGastos("Verduras", imageViewToByte(icon), 100, 1, MainActivity.id_user);


        icon = BitmapFactory.decodeResource(mainActivity.getResources(), R.drawable.helado);
        mainActivity.sqLiteHelper.insertDataCategoriaGastos("Aperitivo", imageViewToByte(icon), 100, 1, MainActivity.id_user);


        icon = BitmapFactory.decodeResource(mainActivity.getResources(), R.drawable.regalo);
        mainActivity.sqLiteHelper.insertDataCategoriaGastos("Regalo", imageViewToByte(icon), 100, 1, MainActivity.id_user);


        icon = BitmapFactory.decodeResource(mainActivity.getResources(), R.drawable.avion);
        mainActivity.sqLiteHelper.insertDataCategoriaGastos("Viajar", imageViewToByte(icon), 100, 1, MainActivity.id_user);


        icon = BitmapFactory.decodeResource(mainActivity.getResources(), R.drawable.estudio);
        mainActivity.sqLiteHelper.insertDataCategoriaGastos("Educacion", imageViewToByte(icon), 100, 1, MainActivity.id_user);


        icon = BitmapFactory.decodeResource(mainActivity.getResources(), R.drawable.manzana);
        mainActivity.sqLiteHelper.insertDataCategoriaGastos("Fruta", imageViewToByte(icon), 100, 1, MainActivity.id_user);


        icon = BitmapFactory.decodeResource(mainActivity.getResources(), R.drawable.libros);
        mainActivity.sqLiteHelper.insertDataCategoriaGastos("Libros", imageViewToByte(icon), 100, 1, MainActivity.id_user);


        icon = BitmapFactory.decodeResource(mainActivity.getResources(), R.drawable.otros_in);
        mainActivity.sqLiteHelper.insertDataCategoriaGastos("Otros", imageViewToByte(icon), 100, 1, MainActivity.id_user);


        icon = BitmapFactory.decodeResource(mainActivity.getResources(), R.drawable.escritorio_mu);
        mainActivity.sqLiteHelper.insertDataCategoriaGastos("Oficina", imageViewToByte(icon), 100, 1, MainActivity.id_user);

    }
}

