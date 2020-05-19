package co.desofsi.ahorro.ui.home;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Calendar;

import co.desofsi.ahorro.*;
import co.desofsi.ahorro.entidades.Gasto;
import co.desofsi.ahorro.entidades.ImageManagerCustom;

public class EditHomeGastosActivity extends AppCompatActivity {


    //TEMAS APP
    private Window window;
    String primaryDark = "#89c29a";
    String primary = "#B9F6CA";
    String background = "#FFFFFF";


    private ImageView imageView;
    private EditText txt_nombre, txt_presupuesto,txt_fecha;
    private TextView txt_position;
    private Button btn_actualizar, btn_eliminar;
    DecimalFormat decimalFormat;
    DecimalFormatSymbols separador;
    private int id;
    private ImageManagerCustom imageManagerCustom;
    Calendar calendar;
    ///fechas
    private int dia, mes, anio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_home_gastos);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Editar Gasto");
        window = this.getWindow();
        insertColor();
        calendar = Calendar.getInstance();
        init();
        separador = new DecimalFormatSymbols();
        separador.setDecimalSeparator('.');
        decimalFormat = new DecimalFormat("#.00", separador);
        final Gasto cate = (Gasto) getIntent().getExtras().getSerializable("Gasto");

        id = cate.getId();
        txt_position.setText(cate.getDescripcion());
        imageView.setImageBitmap(imageManagerCustom.decodificar(cate.getImagen()));
        txt_nombre.setText(cate.getDescripcion());
        txt_presupuesto.setText(String.valueOf(cate.getValor()));
        txt_fecha.setText(String.valueOf(cate.getFecha()));





        btn_eliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder aletBuilder = new AlertDialog.Builder(EditHomeGastosActivity.this);

                aletBuilder.setMessage("Se borrarán todos los gastos relacionados a está categoría");
                aletBuilder.setTitle("Eliminar categoría");
                aletBuilder.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String sql = "DELETE FROM gastos WHERE id = '" + id + "'";
                        MainActivity.sqLiteHelper.deletedDataTable(sql);

                        Intent intent = new Intent(EditHomeGastosActivity.this, MainActivity.class);
                        startActivity(intent);
                    }
                });
                aletBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                AlertDialog dialog = aletBuilder.create();
                dialog.show();

            }
        });

        btn_actualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {




                AlertDialog.Builder aletBuilder = new AlertDialog.Builder(EditHomeGastosActivity.this);
                aletBuilder.setTitle("Actualizar categoría");
                aletBuilder.setMessage("¿Está seguro de modificar está categoría");

                aletBuilder.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        double valor_choose = Double.parseDouble(txt_presupuesto.getText().toString());
                        MainActivity.sqLiteHelper.updateDataTableGastos(cate.getId(),txt_nombre.getText().toString(),Double.parseDouble(decimalFormat.format(valor_choose)),txt_fecha.getText().toString());

                        Intent intent = new Intent(EditHomeGastosActivity.this, MainActivity.class);
                        startActivity(intent);
                    }
                });
                aletBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                AlertDialog dialog = aletBuilder.create();
                dialog.show();
            }
        });


        txt_fecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dia = calendar.get(Calendar.DAY_OF_MONTH);
                mes = calendar.get(Calendar.MONTH);
                anio = calendar.get(Calendar.YEAR);

                DatePickerDialog datePickerDialog = new DatePickerDialog(EditHomeGastosActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        if(dayOfMonth<10){

                            if((month+1)<10){
                                txt_fecha.setText("0"+dayOfMonth + "/0" + (month + 1) + "/" + year);
                            }else{
                                txt_fecha.setText("0"+dayOfMonth + "/" + (month + 1) + "/" + year);
                            }

                        }else {
                            if((month+1)<10){
                                txt_fecha.setText(dayOfMonth + "/0" + (month + 1) + "/" + year);
                            }else{
                                txt_fecha.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
                            }

                        }
                    }
                }, anio, mes, dia);
                datePickerDialog.show();
            }
        });


    }

    public void init() {
        btn_actualizar = (Button) findViewById(R.id.btn_edit_gasto_acutualizar);
        btn_eliminar = (Button) findViewById(R.id.btn_edit_gasto_eliminar);
        txt_position = (TextView) findViewById(R.id.text_position);
        imageView = (ImageView) findViewById(R.id.txt_edit_gasto_imagen);
        txt_nombre = (EditText) findViewById(R.id.txt_edit_gasto_description);
        txt_presupuesto = (EditText) findViewById(R.id.txt_edit_gasto_valor);
        txt_fecha = (EditText) findViewById(R.id.txt_edit_gasto_fecha);
        imageManagerCustom = new ImageManagerCustom();
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

}
