package co.desofsi.ahorro.ui.home;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.ByteArrayOutputStream;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.regex.Pattern;

import co.desofsi.ahorro.*;
import co.desofsi.ahorro.adaptadores.HomeIngresosGridAdapter;
import co.desofsi.ahorro.entidades.CategoriaIngreso;
import co.desofsi.ahorro.ui.tools.CategoriaIngresosActivity;

public class Home_Ingresos_Activity extends AppCompatActivity {

    private FloatingActionButton floting;
    private GridView gridView;
    private HomeIngresosGridAdapter adapter;
    private ImageView img_ingreso;
    private EditText text_fecha;
    private EditText text_descrip;
    private Button btn_fecha;
    private ImageButton btn_guadar;
    private LinearLayout seccion_number;

    ///fechas
    private int dia, mes, anio;

    //CALCULADORA

    private Button btnCero, btnUno, btnDos, btnTres, btnCuatro, btnCinco, btnSeis, btnSiete, btnOcho,
            btnNueve, btnPunto, btnIgual, btnSuma, btnResta, btnMulti, btnDiv, btnLimpiar;
    private TextView etProceso, etconcatenar;
    private double numero1, numero2, resultado;
    private String operador;

    DecimalFormatSymbols separador;
    DecimalFormat decimalFormat;

    final double MAX_NUMBER_INPUT = 1000000;

    ///VALORES ALMACENADOS
    Calendar calendar;
    private String nombreCategoria;
    private int id_cate;

    //TEMAS APP
    private Window window;
    String primaryDark = "#89c29a";
    String primary = "#B9F6CA";
    String background = "#FFFFFF";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_home__ingresos_);


        calendar = Calendar.getInstance();
        String currentDate = DateFormat.getDateInstance().format(calendar.getInstance().getTime());

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Ingresos");

        window = this.getWindow();
        insertColor();
        separador = new DecimalFormatSymbols();
        separador.setDecimalSeparator('.');
        decimalFormat = new DecimalFormat("#.00", separador);

        final ArrayList<CategoriaIngreso> arrayList = new ArrayList<>();


        //obteer datos de la base de datos
        Cursor cursor = MainActivity.sqLiteHelper.getDataTable("SELECT * FROM categoria_ingreso WHERE id_user = '"+MainActivity.id_user+"' AND estado = 1");

        arrayList.clear();
        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String nombre = cursor.getString(1);
            byte[] image = cursor.getBlob(2);
            int estado = cursor.getInt(3);
            arrayList.add(new CategoriaIngreso(id, nombre, image,estado,MainActivity.id_user));

        }


        gridView = (GridView) findViewById(R.id.grid_ingresos);
        adapter = new HomeIngresosGridAdapter(this, arrayList);
        gridView.setAdapter(adapter);

        init();
        btn_fecha.setText(getFechaHoy());

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                seccion_number.setVisibility(View.VISIBLE);
                CategoriaIngreso cate = arrayList.get(position);
                nombreCategoria = cate.getNombre();
                id_cate = cate.getId();
                byte[] cat_img = cate.getImage();
                Bitmap bitmap = BitmapFactory.decodeByteArray(cat_img, 0, cat_img.length);

                img_ingreso.setImageBitmap(bitmap);


                // Intent intent = new Intent(Ingresos_Activity.this, DetallesActivity.class);
                ///intent.putExtra("Categorias", cate);
                //startActivity(intent);
            }
        });

        floting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Home_Ingresos_Activity.this, CategoriaIngresosActivity.class);
                startActivity(intent);
            }
        });

        btn_guadar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (etProceso.getText().toString().trim().equals("")) {
                    Toast.makeText(getApplicationContext(), "El valor esta vacío", Toast.LENGTH_SHORT).show();
                } else {
                    try {
                        double new_valor = Double.parseDouble(etProceso.getText().toString());

                        if (text_descrip.getText().toString().trim().equals("")) {
                            MainActivity.sqLiteHelper.insertDataIngresos(
                                    nombreCategoria,
                                    btn_fecha.getText().toString().trim(),
                                    imageViewToByte(img_ingreso),
                                    new_valor,id_cate,MainActivity.id_user

                            );
                        } else {
                            MainActivity.sqLiteHelper.insertDataIngresos(
                                    text_descrip.getText().toString().trim(),
                                    btn_fecha.getText().toString().trim(),
                                    imageViewToByte(img_ingreso),
                                    new_valor,id_cate,MainActivity.id_user

                            );

                        }

                        Toast.makeText(getApplicationContext(), "Agregado exitosamente!", Toast.LENGTH_SHORT).show();
                        text_descrip.setText("");
                        etProceso.setText("");
                        btn_fecha.setText(getFechaHoy());
                        img_ingreso.setImageResource(R.mipmap.ic_launcher);
                        seccion_number.setVisibility(View.GONE);
                    } catch (Exception e) {
                        Toast.makeText(getApplicationContext(), "El valor no es correcto", Toast.LENGTH_SHORT).show();
                    }
                }


            }
        });

        ///*****************************EVENTOS CLICK BOTONES CALCULADORA ****************************************
        btnCero.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                double valor = 0;
                etconcatenar = (TextView) findViewById(R.id.txt_valor_ingresos);
                String cadena = etconcatenar.getText().toString().trim();
                String concatenar = cadena + "0";

                if (concatenar.contains(".")) {
                    String[] parts = concatenar.split(Pattern.quote("."));
                    String part2 = parts[1];

                    if (part2.length() <= 2) {
                        etProceso.setText(concatenar);
                    } else {
                        etProceso.setText(cadena);
                    }

                } else {

                    try {
                        valor = Double.parseDouble(concatenar);
                        if (valor > 0) {
                            if (valor < MAX_NUMBER_INPUT) {
                                etProceso.setText(concatenar);
                            } else {
                                etProceso.setText(cadena);
                            }

                        } else {
                            etProceso.setText("0");
                        }
                    } catch (Exception e) {

                    }


                }


            }
        });
        btnUno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double valor = 0;
                etconcatenar = (TextView) findViewById(R.id.txt_valor_ingresos);
                String cadena = etconcatenar.getText().toString().trim();
                String concatenar = cadena + "1";

                if (concatenar.contains(".")) {
                    String[] parts = concatenar.split(Pattern.quote("."));
                    String part1 = parts[0];
                    String part2 = parts[1];

                    if (part2.length() <= 2) {
                        etProceso.setText(concatenar);
                    } else {
                        etProceso.setText(cadena);
                    }
                } else {
                    try {
                        valor = Double.parseDouble(concatenar);
                        if (valor < MAX_NUMBER_INPUT) {
                            etProceso.setText(concatenar);
                        } else {
                            etProceso.setText(cadena);
                        }
                    } catch (Exception e) {
                    }
                }
            }
        });
        btnDos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double valor = 0;
                etconcatenar = (TextView) findViewById(R.id.txt_valor_ingresos);
                String cadena = etconcatenar.getText().toString().trim();
                String concatenar = cadena + "2";

                if (concatenar.contains(".")) {
                    String[] parts = concatenar.split(Pattern.quote("."));
                    String part1 = parts[0];
                    String part2 = parts[1];

                    if (part2.length() <= 2) {
                        etProceso.setText(concatenar);
                    } else {
                        etProceso.setText(cadena);
                    }
                } else {
                    try {
                        valor = Double.parseDouble(concatenar);
                        if (valor < MAX_NUMBER_INPUT) {
                            etProceso.setText(concatenar);
                        } else {
                            etProceso.setText(cadena);
                        }
                    } catch (Exception e) {
                    }
                }
            }
        });
        btnTres.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double valor = 0;
                etconcatenar = (TextView) findViewById(R.id.txt_valor_ingresos);
                String cadena = etconcatenar.getText().toString().trim();
                String concatenar = cadena + "3";

                if (concatenar.contains(".")) {
                    String[] parts = concatenar.split(Pattern.quote("."));
                    String part1 = parts[0];
                    String part2 = parts[1];

                    if (part2.length() <= 2) {
                        etProceso.setText(concatenar);
                    } else {
                        etProceso.setText(cadena);
                    }
                } else {
                    try {
                        valor = Double.parseDouble(concatenar);
                        if (valor < MAX_NUMBER_INPUT) {
                            etProceso.setText(concatenar);
                        } else {
                            etProceso.setText(cadena);
                        }
                    } catch (Exception e) {
                    }
                }
            }
        });
        btnCuatro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double valor = 0;
                etconcatenar = (TextView) findViewById(R.id.txt_valor_ingresos);
                String cadena = etconcatenar.getText().toString().trim();
                String concatenar = cadena + "4";

                if (concatenar.contains(".")) {
                    String[] parts = concatenar.split(Pattern.quote("."));
                    String part1 = parts[0];
                    String part2 = parts[1];

                    if (part2.length() <= 2) {
                        etProceso.setText(concatenar);
                    } else {
                        etProceso.setText(cadena);
                    }
                } else {
                    try {
                        valor = Double.parseDouble(concatenar);
                        if (valor < MAX_NUMBER_INPUT) {
                            etProceso.setText(concatenar);
                        } else {
                            etProceso.setText(cadena);
                        }
                    } catch (Exception e) {
                    }
                }
            }
        });
        btnCinco.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double valor = 0;
                etconcatenar = (TextView) findViewById(R.id.txt_valor_ingresos);
                String cadena = etconcatenar.getText().toString().trim();
                String concatenar = cadena + "5";

                if (concatenar.contains(".")) {
                    String[] parts = concatenar.split(Pattern.quote("."));
                    String part1 = parts[0];
                    String part2 = parts[1];

                    if (part2.length() <= 2) {
                        etProceso.setText(concatenar);
                    } else {
                        etProceso.setText(cadena);
                    }
                } else {
                    try {
                        valor = Double.parseDouble(concatenar);
                        if (valor < MAX_NUMBER_INPUT) {
                            etProceso.setText(concatenar);
                        } else {
                            etProceso.setText(cadena);
                        }
                    } catch (Exception e) {
                    }
                }
            }
        });
        btnSeis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double valor = 0;
                etconcatenar = (TextView) findViewById(R.id.txt_valor_ingresos);
                String cadena = etconcatenar.getText().toString().trim();
                String concatenar = cadena + "6";

                if (concatenar.contains(".")) {
                    String[] parts = concatenar.split(Pattern.quote("."));
                    String part1 = parts[0];
                    String part2 = parts[1];

                    if (part2.length() <= 2) {
                        etProceso.setText(concatenar);
                    } else {
                        etProceso.setText(cadena);
                    }
                } else {
                    try {
                        valor = Double.parseDouble(concatenar);
                        if (valor < MAX_NUMBER_INPUT) {
                            etProceso.setText(concatenar);
                        } else {
                            etProceso.setText(cadena);
                        }
                    } catch (Exception e) {
                    }
                }
            }
        });
        btnSiete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double valor = 0;
                etconcatenar = (TextView) findViewById(R.id.txt_valor_ingresos);
                String cadena = etconcatenar.getText().toString().trim();
                String concatenar = cadena + "7";

                if (concatenar.contains(".")) {
                    String[] parts = concatenar.split(Pattern.quote("."));
                    String part1 = parts[0];
                    String part2 = parts[1];

                    if (part2.length() <= 2) {
                        etProceso.setText(concatenar);
                    } else {
                        etProceso.setText(cadena);
                    }
                } else {
                    try {
                        valor = Double.parseDouble(concatenar);
                        if (valor < MAX_NUMBER_INPUT) {
                            etProceso.setText(concatenar);
                        } else {
                            etProceso.setText(cadena);
                        }
                    } catch (Exception e) {
                    }
                }
            }
        });
        btnOcho.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double valor = 0;
                etconcatenar = (TextView) findViewById(R.id.txt_valor_ingresos);
                String cadena = etconcatenar.getText().toString().trim();
                String concatenar = cadena + "8";

                if (concatenar.contains(".")) {
                    String[] parts = concatenar.split(Pattern.quote("."));
                    String part1 = parts[0];
                    String part2 = parts[1];

                    if (part2.length() <= 2) {
                        etProceso.setText(concatenar);
                    } else {
                        etProceso.setText(cadena);
                    }
                } else {
                    try {
                        valor = Double.parseDouble(concatenar);
                        if (valor < MAX_NUMBER_INPUT) {
                            etProceso.setText(concatenar);
                        } else {
                            etProceso.setText(cadena);
                        }
                    } catch (Exception e) {
                    }
                }
            }
        });
        btnNueve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double valor = 0;
                etconcatenar = (TextView) findViewById(R.id.txt_valor_ingresos);
                String cadena = etconcatenar.getText().toString().trim();
                String concatenar = cadena + "9";

                if (concatenar.contains(".")) {
                    String[] parts = concatenar.split(Pattern.quote("."));
                    String part1 = parts[0];
                    String part2 = parts[1];

                    if (part2.length() <= 2) {
                        etProceso.setText(concatenar);
                    } else {
                        etProceso.setText(cadena);
                    }
                } else {
                    try {
                        valor = Double.parseDouble(concatenar);
                        if (valor < MAX_NUMBER_INPUT) {
                            etProceso.setText(concatenar);
                        } else {
                            etProceso.setText(cadena);
                        }
                    } catch (Exception e) {
                    }
                }
            }
        });
        btnPunto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etconcatenar = (TextView) findViewById(R.id.txt_valor_ingresos);
                String cadena = etconcatenar.getText().toString().trim();
                if (cadena.contains(".")) {

                } else {
                    etProceso.setText(cadena + ".");
                }


            }
        });


// <editor-fold defaultstate="collapsed" desc="OPERACIONES CALCULADORA">

        btnSuma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double total = 0;
                operador = "+";
                etconcatenar = (TextView) findViewById(R.id.txt_valor_ingresos);
                try {
                    numero1 = Double.parseDouble(etconcatenar.getText().toString());
                    total = numero1 * +1;
                    etProceso.setText("" + total);
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "El valor no es válido", Toast.LENGTH_SHORT).show();
                }


            }
        });
        btnResta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double total = 0;
                operador = "-";
                etconcatenar = (TextView) findViewById(R.id.txt_valor_ingresos);
                try {
                    numero1 = Double.parseDouble(etconcatenar.getText().toString());
                    total = numero1 * -1;
                    etProceso.setText("" + total);
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "El valor no es válido", Toast.LENGTH_SHORT).show();
                }

            }
        });

        btnLimpiar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String cadena_reducir = etProceso.getText().toString().trim();
                if (cadena_reducir.length() > 1) {
                    cadena_reducir = cadena_reducir.substring(0, cadena_reducir.length() - 1);
                    numero1 = 0;
                    numero2 = 0;
                    etProceso.setText(cadena_reducir);
                } else {
                    etProceso.setText("");
                }

            }
        });

        //<editor-fold>


        ///******************BOTON PARA LA FECHA ********************************
        btn_fecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dia = calendar.get(Calendar.DAY_OF_MONTH);
                mes = calendar.get(Calendar.MONTH);
                anio = calendar.get(Calendar.YEAR);

                DatePickerDialog datePickerDialog = new DatePickerDialog(Home_Ingresos_Activity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                        if(dayOfMonth<10){

                            if((month+1)<10){
                                btn_fecha.setText("0"+dayOfMonth + "/0" + (month + 1) + "/" + year);
                            }else{
                                btn_fecha.setText("0"+dayOfMonth + "/" + (month + 1) + "/" + year);
                            }

                        }else {
                            if((month+1)<10){
                                btn_fecha.setText(dayOfMonth + "/0" + (month + 1) + "/" + year);
                            }else{
                                btn_fecha.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
                            }

                        }


                    }
                }, anio, mes, dia);
                datePickerDialog.show();
            }
        });


    }


    public String getFechaHoy() {
        String fecha_dia = "";
        dia = calendar.get(Calendar.DAY_OF_MONTH);
        mes = calendar.get(Calendar.MONTH) + 1;
        anio = calendar.get(Calendar.YEAR);
        if(dia<10){
            if(mes<10){
                fecha_dia = "0"+dia + "/0" + mes + "/" + anio;
            }else{
                fecha_dia = "0"+dia + "/" + mes + "/" + anio;
            }

        }else {
            if(mes<10){
                fecha_dia = dia + "/0" + mes + "/" + anio;
            }else{
                fecha_dia = dia + "/" + mes + "/" + anio;
            }

        }


        return fecha_dia;
    }

    public void init() {
        floting = (FloatingActionButton) findViewById(R.id.floating_ingresos);
        img_ingreso = (ImageView) findViewById(R.id.img_ingreso_elejido);
        text_descrip = (EditText) findViewById(R.id.txt_describ_ingreso);
        btn_fecha = (Button) findViewById(R.id.btn_fecha);
        btn_guadar = (ImageButton) findViewById(R.id.btn_guardar_ingreso);
        seccion_number = (LinearLayout) findViewById(R.id.secion_number);


        ///vincular botones calculadora

        btnLimpiar = (Button) findViewById(R.id.btn_clear);
        btnCero = (Button) findViewById(R.id.numbre_0);
        btnUno = (Button) findViewById(R.id.number_1);
        btnDos = (Button) findViewById(R.id.numbre_2);
        btnTres = (Button) findViewById(R.id.number_3);
        btnCuatro = (Button) findViewById(R.id.number_4);
        btnCinco = (Button) findViewById(R.id.numbre_5);
        btnSeis = (Button) findViewById(R.id.number_6);
        btnSiete = (Button) findViewById(R.id.number_7);
        btnOcho = (Button) findViewById(R.id.numbre_8);
        btnNueve = (Button) findViewById(R.id.number_9);
        btnPunto = (Button) findViewById(R.id.number_punto);
        btnSuma = (Button) findViewById(R.id.btn_mas);
        btnResta = (Button) findViewById(R.id.btn_menos);
        etProceso = (TextView) findViewById(R.id.txt_valor_ingresos);
    }

    private byte[] imageViewToByte(ImageView imageView) {
        Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
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
