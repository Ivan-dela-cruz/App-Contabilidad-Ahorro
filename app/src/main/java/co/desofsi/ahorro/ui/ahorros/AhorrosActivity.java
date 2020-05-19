package co.desofsi.ahorro.ui.ahorros;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Calendar;

import co.desofsi.ahorro.*;
import co.desofsi.ahorro.adaptadores.AhorrosGridAdapter;
import co.desofsi.ahorro.entidades.CategoriaAhorro;
import co.desofsi.ahorro.entidades.ImageManagerCustom;
import co.desofsi.ahorro.entidades.Ingresos;

public class AhorrosActivity extends AppCompatActivity {

    private GridView gridView;
    private AhorrosGridAdapter adapter;

    private Spinner spinner;


    private String nombreCategoria;
    private int id_cate = 0;
    private ImageView img_ahorro;
    private EditText txt_ahorro_valor;
    private TextView txt_descrip_ahorro, txt_ingreso_calculado, txt_mensual, txt_meta, txt_anio_estimado, txt_ahorro_total;
    private FloatingActionButton floatingActionButton;


    private ArrayList<Ingresos> lista_ingresos;
    private double total_ingreso;
    final double ANIO = 1 / 12;
    //TEMAS APP
    private Window window;
    String primaryDark = "#89c29a";
    String primary = "#B9F6CA";
    String background = "#FFFFFF";


    DecimalFormatSymbols separador;
    DecimalFormat decimalFormat;

    ImageManagerCustom imageManagerCustom;
    ///VALORES ALMACENADOS
    Calendar calendar;
    ///fechas
    private int dia, mes, anio;


    //variables calculos
    double porcen = 0;
    double porcentaje_ingreso = 0;
    int meta_meses = 0;
    int dias = 0;
    double ingresos_obtenido = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ahorros);
        window = this.getWindow();
        insertColor();
        init();
        separador = new DecimalFormatSymbols();
        separador.setDecimalSeparator('.');
        decimalFormat = new DecimalFormat("#.00", separador);


        LoadSpinner();
        QueryIngresos();

        final ArrayList<CategoriaAhorro> arrayList = new ArrayList<>();


        //obteer datos de la base de datos
        Cursor cursor = MainActivity.sqLiteHelper.getDataTable("SELECT * FROM categoria_ahorro");

        arrayList.clear();
        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String nombre = cursor.getString(1);
            byte[] image = cursor.getBlob(2);
            int estado = cursor.getInt(3);
            arrayList.add(new CategoriaAhorro(id, nombre, image, estado));

        }


        gridView = (GridView) findViewById(R.id.grid_ahorros);
        adapter = new AhorrosGridAdapter(this, arrayList);
        gridView.setAdapter(adapter);


        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                CategoriaAhorro cate = arrayList.get(position);
                nombreCategoria = cate.getNombre();
                id_cate = cate.getId();
                byte[] cat_img = cate.getImage();
                Bitmap bitmap = BitmapFactory.decodeByteArray(cat_img, 0, cat_img.length);

                img_ahorro.setImageBitmap(bitmap);

                txt_descrip_ahorro.setText(cate.getNombre());


                // Intent intent = new Intent(Ingresos_Activity.this, DetallesActivity.class);
                ///intent.putExtra("Categorias", cate);
                //startActivity(intent);
            }
        });


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //  Toast.makeText(parent.getContext(), "" + parent.getItemAtPosition(position), Toast.LENGTH_SHORT).show();

                String val_compa = String.valueOf(parent.getItemAtPosition(position));
                if (!val_compa.equals("Seleccione")) {
                    if (txt_ahorro_valor.getText().toString().equals("")) {
                        Toast.makeText(parent.getContext(), "No existe un valor definido", Toast.LENGTH_SHORT).show();
                    } else {

                        porcen = getPorcentaje(parent.getItemAtPosition(position).toString());
                         ingresos_obtenido = Double.parseDouble(txt_ingreso_calculado.getText().toString());
                        double ingresos = Double.parseDouble(decimalFormat.format(ingresos_obtenido));

                        //valor para obtener el tiempo
                        double valor = Double.parseDouble(txt_ahorro_valor.getText().toString());

                        //pago mensual
                        porcentaje_ingreso = Double.parseDouble(decimalFormat.format((porcen * ingresos)));

                        // valor en meses
                        double meses = valor / porcentaje_ingreso;
                        // double meses_format = Double.parseDouble(decimalFormat.format(meses));


                        meta_meses = (int) meses;
                        float dias_cal = (float) meses - (float) meta_meses;
                        dias = (int) (dias_cal * 30);

                        txt_mensual.setText(String.valueOf(porcentaje_ingreso));
                        if (dias > 0) {
                            txt_meta.setText("" + meta_meses + " meses con " + dias + " días");
                        } else {
                            txt_meta.setText("" + meta_meses + " meses");
                        }

                        if ((meta_meses / 12) >= 2) {
                            txt_anio_estimado.setText(String.valueOf(meta_meses / 12) + " años");
                        } else if ((meta_meses / 12) < 1) {
                            txt_anio_estimado.setText(String.valueOf(meta_meses / 12) + " años");
                        } else {
                            txt_anio_estimado.setText(String.valueOf(meta_meses / 12) + " año");
                        }

                    }
                } else {

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        txt_ahorro_valor.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                return false;
            }
        });
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GuardarAhorro();
            }
        });


    }

    public void init() {
        calendar = Calendar.getInstance();
        floatingActionButton = (FloatingActionButton) findViewById(R.id.flt_guardar_ahorro);
        img_ahorro = (ImageView) findViewById(R.id.btn_guardar_ahorro);
        txt_ahorro_valor = (EditText) findViewById(R.id.txt_valor_ahorro);
        txt_descrip_ahorro = (TextView) findViewById(R.id.txt_describ_ahorro);
        txt_ingreso_calculado = (TextView) findViewById(R.id.txt_ingreso_calculado);
        txt_mensual = (TextView) findViewById(R.id.txt_mensual);
        txt_meta = (TextView) findViewById(R.id.txt_meta);
        txt_anio_estimado = (TextView) findViewById(R.id.txt_anio_estimado);
        txt_ahorro_total = (TextView) findViewById(R.id.txt_ahorro_total);
        imageManagerCustom = new ImageManagerCustom();
    }

    public void LoadSpinner() {
        spinner = (Spinner) findViewById(R.id.spin_porcentajes);
        ArrayAdapter<CharSequence> arrayAdapter = ArrayAdapter.createFromResource(this, R.array.porcentajes, android.R.layout.simple_spinner_item);
        spinner.setAdapter(arrayAdapter);
    }

    public double getPorcentaje(String porcentaje) {
        double valor = 0;

        switch (porcentaje) {
            case "Seleccione":
                valor = 0;
                break;
            case "5 %":
                valor = 0.05;
                break;
            case "10 %":
                valor = 0.10;
                break;
            case "15 %":
                valor = 0.15;
                break;
            case "20 %":
                valor = 0.20;
                break;
            case "25 %":
                valor = 0.25;
                break;
            case "30 %":
                valor = 0.30;
                break;
            case "35 %":
                valor = 0.35;
                break;
            case "40 %":
                valor = 0.40;
                break;
            case "45 %":
                valor = 0.45;
                break;
            case "50 %":
                valor = 0.50;
                break;
            case "55 %":
                valor = 0.55;
                break;
            case "60 %":
                valor = 0.60;
                break;
            case "65 %":
                valor = 0.65;
                break;
            case "70 %":
                valor = 0.70;
                break;
            case "75 %":
                valor = 0.75;
                break;
            case "80 %":
                valor = 0.80;
                break;
            case "85 %":
                valor = 0.85;
                break;
            case "90 %":
                valor = 0.90;
                break;
            case "95 %":
                valor = 0.95;
                break;
            case "100 %":
                valor = 1;
                break;
        }

        return valor;
    }

    public void QueryIngresos() {

        String sql = "SELECT categoria_ingreso.id,categoria_ingreso.nombre,ingresos.fecha,categoria_ingreso.imagen, SUM(ingresos.valor) AS total,categoria_ingreso.id FROM categoria_ingreso LEFT JOIN ingresos ON  ingresos.id_cat = categoria_ingreso.id  WHERE SUBSTR(ingresos.fecha, 4, 2) = '12' group by categoria_ingreso.nombre";
        //obteer datos de la base de datos
        Cursor cursor_ingresos = MainActivity.sqLiteHelper.getDataTable(sql);
        total_ingreso = 0;
        while (cursor_ingresos.moveToNext()) {
            /*int id_in = cursor_ingresos.getInt(0);
            String desripcion_in = cursor_ingresos.getString(1);
            String fecha_in = cursor_ingresos.getString(2);
            byte[] image_in = cursor_ingresos.getBlob(3);
            int id_cat_in = cursor_ingresos.getInt(5);
            */

            double valor_in = cursor_ingresos.getDouble(4);
            total_ingreso += valor_in;
        }


        // txt_ingreso_calculado.setText(String.valueOf(total_ingreso));

    }

    public void insertColor() {
        Cursor cursor = MainActivity.sqLiteHelper.getDataTable("SELECT * FROM colors WHERE id_user = '" + MainActivity.id_user + "'");
        if (cursor.moveToFirst()) {
            primaryDark = cursor.getString(2);
            primary = cursor.getString(1);
            background = cursor.getString(3);
            cambiarColor(primaryDark, primary, background);
        }
    }

    private void cambiarColor(String primaryDark, String primary, String background) {

        window.setStatusBarColor(Color.parseColor(primaryDark));
        this.getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor(primary)));
        window.setBackgroundDrawable(new ColorDrawable(Color.parseColor(background)));
        window.setNavigationBarColor(Color.parseColor(primary));

    }


    private void GuardarAhorro() {
        try {
            String descripcion = txt_descrip_ahorro.getText().toString();
            double valor = Double.parseDouble(txt_ahorro_valor.getText().toString());

            if (descripcion.isEmpty() || String.valueOf(valor).isEmpty()) {
                Toast.makeText(AhorrosActivity.this, "Existen campos vacios", Toast.LENGTH_SHORT).show();
            } else {
                byte[] imagen = imageManagerCustom.imageViewToByte(img_ahorro);
                String fecha = getFechaHoy();
                double porcentaje = porcen;
                double mensual = porcentaje_ingreso;
                int dias_estimado = dias;
                int meses = meta_meses;
                int anio = (meta_meses / 12);
                int estado = 1;

                MainActivity.sqLiteHelper.insertDataAhorros(descripcion, imagen, valor, fecha, porcentaje, mensual, dias_estimado, meses, anio, estado, id_cate, MainActivity.id_user);
                MainActivity.sqLiteHelper.insertDataCategoriaGastos(descripcion,imagen,ingresos_obtenido,1,MainActivity.id_user);
                limpiar();
                Toast.makeText(AhorrosActivity.this, "Ahorro creado satisfactoriamente", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            Toast.makeText(AhorrosActivity.this, "Error al guardar", Toast.LENGTH_SHORT).show();
        }

    }

    public void limpiar() {
        txt_descrip_ahorro.setText("");
        txt_ahorro_valor.setText("");
        txt_ingreso_calculado.setText("");
        txt_ahorro_total.setText("");
        txt_anio_estimado.setText("");
        txt_mensual.setText("");
        txt_meta.setText("");
        porcen = 0;
        porcentaje_ingreso = 0;
        meta_meses = 0;
        dias = 0;
        spinner.setSelection(0);
        ingresos_obtenido = 0;
    }

    public String getFechaHoy() {
        String fecha_dia = "";
        dia = calendar.get(Calendar.DAY_OF_MONTH);
        mes = calendar.get(Calendar.MONTH) + 1;
        anio = calendar.get(Calendar.YEAR);
        if (dia < 10) {
            if (mes < 10) {
                fecha_dia = "0" + dia + "/0" + mes + "/" + anio;
            } else {
                fecha_dia = "0" + dia + "/" + mes + "/" + anio;
            }

        } else {
            if (mes < 10) {
                fecha_dia = dia + "/0" + mes + "/" + anio;
            } else {
                fecha_dia = dia + "/" + mes + "/" + anio;
            }

        }

        return fecha_dia;
    }
}
