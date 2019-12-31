package co.desofsi.ahorro.ui.home;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.media.RingtoneManager;
import android.os.Bundle;
import android.view.View;
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

import java.io.ByteArrayOutputStream;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.regex.Pattern;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import co.desofsi.ahorro.*;
import co.desofsi.ahorro.adaptadores.HomeGastosGridAdapter;
import co.desofsi.ahorro.entidades.CategoriaGasto;

import static co.desofsi.ahorro.entidades.App.CHANNEL_1_ID;

public class Home_Gastos_Activity extends AppCompatActivity {


    private GridView gridView;
    private HomeGastosGridAdapter adapter;
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
    private ArrayList<CategoriaGasto> arrayList;



    ///NOTIFICACIONES
    private NotificationManagerCompat notificationManagerCompat;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_home__gastos_);

        notificationManagerCompat = NotificationManagerCompat.from(Home_Gastos_Activity.this);


        init();
        calendar = Calendar.getInstance();
        String currentDate = DateFormat.getDateInstance().format(calendar.getInstance().getTime());

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Gastos");


        separador = new DecimalFormatSymbols();
        separador.setDecimalSeparator('.');
        decimalFormat = new DecimalFormat("#.00", separador);

        llenarCategoriaGastos();

        btn_fecha.setText(getFechaHoy());


        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                seccion_number.setVisibility(View.VISIBLE);
                CategoriaGasto cate = arrayList.get(position);
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

        btn_guadar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (etProceso.getText().toString().trim().equals("")) {
                    Toast.makeText(getApplicationContext(), "El valor esta vacío", Toast.LENGTH_SHORT).show();
                } else {
                    try {
                        double new_valor = Double.parseDouble(etProceso.getText().toString());

                        if (text_descrip.getText().toString().trim().equals("")) {
                            MainActivity.sqLiteHelper.insertDataGastos(
                                    nombreCategoria,
                                    btn_fecha.getText().toString().trim(),
                                    imageViewToByte(img_ingreso),
                                    new_valor, id_cate,MainActivity.id_user

                            );

                            gridView.clearAnimation();
                            llenarCategoriaGastos();

                        } else {
                            MainActivity.sqLiteHelper.insertDataGastos(
                                    text_descrip.getText().toString().trim(),
                                    btn_fecha.getText().toString().trim(),
                                    imageViewToByte(img_ingreso),
                                    new_valor, id_cate,MainActivity.id_user

                            );
                            gridView.clearAnimation();
                            llenarCategoriaGastos();
                        }

                        Toast.makeText(getApplicationContext(), "Agregado exitosamente!", Toast.LENGTH_SHORT).show();
                        text_descrip.setText("");
                        etProceso.setText("");
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

                DatePickerDialog datePickerDialog = new DatePickerDialog(Home_Gastos_Activity.this, new DatePickerDialog.OnDateSetListener() {
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

    private void llenarCategoriaGastos() {

       arrayList = new ArrayList<>();


        //obteer datos de la base de datos
        Cursor cursor = MainActivity.sqLiteHelper.getDataTable("SELECT * FROM categoria_gasto WHERE id_user = '"+MainActivity.id_user+"'");

        int cont =0;
        arrayList.clear();
        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String nombre = cursor.getString(1);
            byte[] image = cursor.getBlob(2);
            double pre = cursor.getDouble(3);
            int estado = cursor.getInt(4);

            double total = 0;
            Cursor mensual = MainActivity.sqLiteHelper.getDataTable("SELECT  SUM(gastos.valor) AS total FROM categoria_gasto LEFT JOIN gastos ON  (gastos.id_cat = categoria_gasto.id) AND (categoria_gasto.estado = 1) AND categoria_gasto.id = '"+id+"'  AND categoria_gasto.id_user = '"+MainActivity.id_user+"' AND SUBSTR(gastos.fecha, 4, 2) = '12' ");
            if(mensual.moveToFirst()){
                total = mensual.getDouble(0);
            }
            if (total > pre) {
                cont++;
            }


            arrayList.add(new CategoriaGasto(id, nombre, image, pre, estado,total));

        }
        adapter = new HomeGastosGridAdapter(this, arrayList);
        gridView.setAdapter(adapter);

        if (cont >= 1) {
           // sendChannel1();
        }

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
        gridView = (GridView) findViewById(R.id.grid_ingresos);
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

    public void sendChannel1() {

        Intent miIntencion = new Intent(Home_Gastos_Activity.this.getApplicationContext(),MainActivity.class);
        PendingIntent pendiente = PendingIntent.getActivity(Home_Gastos_Activity.this.getApplicationContext(),0,miIntencion,PendingIntent.FLAG_UPDATE_CURRENT);

        Notification notification = new NotificationCompat.Builder(Home_Gastos_Activity.this,CHANNEL_1_ID)
                .setSmallIcon(R.drawable.carrito)
                .setContentTitle("Presupuesto excedido")
                .setContentText("Ha sobrepasado el presupuesto establecido en los gastos")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .setContentIntent(pendiente)
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .build();



        notificationManagerCompat.notify(1,notification);

    }


}
