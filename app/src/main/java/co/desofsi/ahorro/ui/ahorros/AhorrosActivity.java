package co.desofsi.ahorro.ui.ahorros;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import co.desofsi.ahorro.*;
import co.desofsi.ahorro.adaptadores.AhorrosGridAdapter;
import co.desofsi.ahorro.entidades.CategoriaAhorro;
import co.desofsi.ahorro.entidades.Ingresos;

public class AhorrosActivity extends AppCompatActivity {

    private GridView gridView;
    private AhorrosGridAdapter adapter;

    private Spinner spinner;


    private String nombreCategoria;
    private int id_cate;
    private ImageView img_ahorro;
    private EditText txt_ahorro_valor;
    private TextView txt_descrip_ahorro, txt_ingreso_calculado, txt_mensual, txt_meta, txt_anio_estimado, txt_ahorro_total;


    private ArrayList<Ingresos> lista_ingresos;
    private double total_ingreso;
    final double ANIO = 1 / 12;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ahorros);

        init();
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
                Toast.makeText(parent.getContext(), "" + parent.getItemAtPosition(position), Toast.LENGTH_SHORT).show();

                if (txt_ahorro_valor.getText().toString().equals("")) {
                    Toast.makeText(parent.getContext(), "No existe un valor definido", Toast.LENGTH_SHORT).show();
                } else {


                    try {
                        double porcen = getPorcentaje(parent.getItemAtPosition(position).toString());
                        double valor = Double.parseDouble(txt_ahorro_valor.getText().toString());

                        double porcentaje_ingreso = porcen * valor;
                        double meses = valor / porcentaje_ingreso;


                        txt_mensual.setText(String.valueOf(porcentaje_ingreso));
                        txt_meta.setText(String.valueOf(meses));
                        txt_anio_estimado.setText(String.valueOf(ANIO*meses));


                    } catch (Exception e) {
                        Toast.makeText(parent.getContext(), "El valor ingresado no es correcto", Toast.LENGTH_SHORT).show();
                    }
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


    }

    public void init() {
        img_ahorro = (ImageView) findViewById(R.id.btn_guardar_ahorro);
        txt_ahorro_valor = (EditText) findViewById(R.id.txt_valor_ahorro);
        txt_descrip_ahorro = (TextView) findViewById(R.id.txt_describ_ahorro);
        txt_ingreso_calculado = (TextView) findViewById(R.id.txt_ingreso_calculado);
        txt_mensual = (TextView) findViewById(R.id.txt_mensual);
        txt_meta = (TextView) findViewById(R.id.txt_meta);
        txt_anio_estimado = (TextView) findViewById(R.id.txt_anio_estimado);
        txt_ahorro_total = (TextView) findViewById(R.id.txt_ahorro_total);

    }

    public void LoadSpinner() {
        spinner = (Spinner) findViewById(R.id.spin_porcentajes);
        ArrayAdapter<CharSequence> arrayAdapter = ArrayAdapter.createFromResource(this, R.array.porcentajes, android.R.layout.simple_spinner_item);
        spinner.setAdapter(arrayAdapter);
    }

    public double getPorcentaje(String porcentaje) {
        double valor = 0;

        switch (porcentaje) {
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


        txt_ingreso_calculado.setText(String.valueOf(total_ingreso));

    }
}
