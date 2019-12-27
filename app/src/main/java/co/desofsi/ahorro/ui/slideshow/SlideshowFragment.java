package co.desofsi.ahorro.ui.slideshow;

import android.app.DatePickerDialog;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.core.app.NotificationCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import co.desofsi.ahorro.MainActivity;
import co.desofsi.ahorro.R;
import co.desofsi.ahorro.adaptadores.PresupuestoSlideShowGastosGridAdapter;
import co.desofsi.ahorro.adaptadores.RecyclerPresuSlideShowListaGastosAdapter;
import co.desofsi.ahorro.entidades.CategoriaGasto;
import co.desofsi.ahorro.ui.home.Home_Gastos_Activity;

public class SlideshowFragment extends Fragment {

    private SlideshowViewModel slideshowViewModel;
    private GridView gridView;
    private PresupuestoSlideShowGastosGridAdapter adapter;
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
    private TextView etconcatenar, etProceso ;
           // , etProceso;
    private double numero1, numero2, resultado;
    private String operador;

    DecimalFormatSymbols separador;
    DecimalFormat decimalFormat;

    final double MAX_NUMBER_INPUT = 1000000;

    ///VALORES ALMACENADOS
    Calendar calendar;
    private String nombreCategoria;
    private double presu_cat;
    private int id_cate;

    private ArrayList<CategoriaGasto>lista_categoria_gastos;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        slideshowViewModel =
                ViewModelProviders.of(this).get(SlideshowViewModel.class);
        final View root = inflater.inflate(R.layout.fragment_slideshow, container, false);


        calendar = Calendar.getInstance();
        String currentDate = DateFormat.getDateInstance().format(calendar.getInstance().getTime());

        separador = new DecimalFormatSymbols();
        separador.setDecimalSeparator('.');
        decimalFormat = new DecimalFormat("#.00", separador);
        init(root);
        llenarGridView(root);

        btn_fecha.setText(getFechaHoy());




        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                seccion_number.setVisibility(View.VISIBLE);
                CategoriaGasto cate = lista_categoria_gastos.get(position);
                nombreCategoria = cate.getNombre();
                presu_cat = cate.getPresupuesto();
                id_cate = cate.getId();
                byte[] cat_img = cate.getImage();
                Bitmap bitmap = BitmapFactory.decodeByteArray(cat_img, 0, cat_img.length);

                img_ingreso.setImageBitmap(bitmap);
                text_descrip.setText(nombreCategoria);
                etProceso.setText(String.valueOf(presu_cat));


                // Intent intent = new Intent(Ingresos_Activity.this, DetallesActivity.class);
                ///intent.putExtra("Categorias", cate);
                //startActivity(intent);
            }
        });






        btn_guadar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (etProceso.getText().toString().trim().equals("")) {
                    Toast.makeText(getActivity(), "El valor esta vacío", Toast.LENGTH_SHORT).show();
                } else {
                    try {
                        double new_valor = Double.parseDouble(etProceso.getText().toString());


                            MainActivity.sqLiteHelper.insertDataPresupuesoGastos(
                                    id_cate,new_valor

                            );

                        Toast.makeText(getActivity(), "Presupuesto actualizado exitosamente!", Toast.LENGTH_SHORT).show();

                        gridView.clearAnimation();
                        llenarGridView(root);
                        text_descrip.setText("");
                        etProceso.setText("");
                        img_ingreso.setImageResource(R.mipmap.ic_launcher);
                        seccion_number.setVisibility(View.GONE);
                    } catch (Exception e) {
                        Toast.makeText(getActivity(), "El valor no es correcto", Toast.LENGTH_SHORT).show();
                    }
                }


            }
        });




        ///*****************************EVENTOS CLICK BOTONES CALCULADORA ****************************************
        btnCero.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                double valor = 0;
                etconcatenar = (TextView) root.findViewById(R.id.txt_valor_gasto_presupuesto);
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
                etconcatenar = (TextView) root.findViewById(R.id.txt_valor_gasto_presupuesto);
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
                etconcatenar = (TextView) root.findViewById(R.id.txt_valor_gasto_presupuesto);
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
                etconcatenar = (TextView) root.findViewById(R.id.txt_valor_gasto_presupuesto);
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
                etconcatenar = (TextView) root.findViewById(R.id.txt_valor_gasto_presupuesto);
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
                etconcatenar = (TextView) root.findViewById(R.id.txt_valor_gasto_presupuesto);
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
                etconcatenar = (TextView) root.findViewById(R.id.txt_valor_gasto_presupuesto);
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
                etconcatenar = (TextView) root.findViewById(R.id.txt_valor_gasto_presupuesto);
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
                etconcatenar = (TextView) root.findViewById(R.id.txt_valor_gasto_presupuesto);
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
                etconcatenar = (TextView) root.findViewById(R.id.txt_valor_gasto_presupuesto);
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
                etconcatenar = (TextView) root.findViewById(R.id.txt_valor_gasto_presupuesto);
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
                etconcatenar = (TextView) root.findViewById(R.id.txt_valor_gasto_presupuesto);
                try {
                    numero1 = Double.parseDouble(etconcatenar.getText().toString());
                    total = numero1 * +1;
                    etProceso.setText("" + total);
                } catch (Exception e) {
                    Toast.makeText(getActivity(), "El valor no es válido", Toast.LENGTH_SHORT).show();
                }


            }
        });
        btnResta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double total = 0;
                operador = "-";
                etconcatenar = (TextView) root.findViewById(R.id.txt_valor_gasto_presupuesto);
                try {
                    numero1 = Double.parseDouble(etconcatenar.getText().toString());
                    total = numero1 * -1;
                    etProceso.setText("" + total);
                } catch (Exception e) {
                    Toast.makeText(getActivity(), "El valor no es válido", Toast.LENGTH_SHORT).show();
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

                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
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






        return root;
    }

    public void llenarGridView(View root){
        lista_categoria_gastos = new ArrayList<>();


        //obteer datos de la base de datos
        Cursor cursor = MainActivity.sqLiteHelper.getDataTable("SELECT categoria_gasto.id,categoria_gasto.nombre,categoria_gasto.imagen,categoria_gasto.presupuesto,categoria_gasto.estado, SUM(gastos.valor) AS total FROM categoria_gasto LEFT JOIN gastos ON  (gastos.id_cat = categoria_gasto.id) AND (categoria_gasto.estado = 1)  WHERE SUBSTR(gastos.fecha, 4, 2) = '12' group by categoria_gasto.nombre  ORDER BY  total DESC");

        lista_categoria_gastos.clear();
        int contador = 0;
        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String nombre = cursor.getString(1);
            byte[] image = cursor.getBlob(2);
            double pre = cursor.getDouble(3);
            int estado = cursor.getInt(4);
            double total = cursor.getDouble(5);
            if(total>pre){
                contador++;
            }
            lista_categoria_gastos.add(new CategoriaGasto(id, nombre, image, pre, estado,total));

        }


        adapter = new PresupuestoSlideShowGastosGridAdapter(getActivity(), lista_categoria_gastos);
        gridView.setAdapter(adapter);

        if(contador>=1){
           // Toast.makeText(getActivity(),"El presupuesto a sido sobrepaso",Toast.LENGTH_SHORT).show();
            NotificationCompat.Builder notifiaction_presupuesto = new NotificationCompat.Builder(getActivity().getApplicationContext());
            notifiaction_presupuesto.setSmallIcon(R.drawable.ic_notifications_active_black_24dp);
            notifiaction_presupuesto.setLargeIcon(((BitmapDrawable)getResources().getDrawable(R.drawable.carrito)).getBitmap());
            notifiaction_presupuesto.setContentTitle("Presupuesto excedido");
            notifiaction_presupuesto.setContentText("Ha sobrepado el presupuesto establecido en los gastos");
            notifiaction_presupuesto.setTicker("FinanAhorro info");
            notifiaction_presupuesto.setContentInfo("4");

            Intent miIntencion = new Intent(getActivity().getApplicationContext(),MainActivity.class);
            PendingIntent pendiente = PendingIntent.getActivity(getActivity().getApplicationContext(),0,miIntencion,PendingIntent.FLAG_UPDATE_CURRENT);
            notifiaction_presupuesto.setContentIntent(pendiente);

            NotificationManager notificationManager = (NotificationManager) getActivity().getSystemService(Context.NOTIFICATION_SERVICE);

            notificationManager.notify(1900,notifiaction_presupuesto.build());

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

    public void init( View root) {

        gridView = (GridView) root.findViewById(R.id.grid_gasto_presupuesto);


        img_ingreso = (ImageView) root.findViewById(R.id.img_ingreso_elejido_gasto_presupuesto);
        text_descrip = (EditText) root.findViewById(R.id.txt_nombre_gasto_presupuesto);
        btn_fecha = (Button) root.findViewById(R.id.btn_fecha_gasto_presupuesto);
        btn_guadar = (ImageButton) root.findViewById(R.id.btn_guardar_gasto_presupuesto);
        seccion_number = (LinearLayout) root.findViewById(R.id.secion_number_gasto_presupuesto);


        ///vincular botones calculadora

        btnLimpiar = (Button) root.findViewById(R.id.btn_clear_gasto_presupuesto);
        btnCero = (Button) root.findViewById(R.id.numbre_0_gasto_presupuesto);
        btnUno = (Button) root.findViewById(R.id.number_1_gasto_presupuesto);
        btnDos = (Button) root.findViewById(R.id.numbre_2_gasto_presupuesto);
        btnTres = (Button) root.findViewById(R.id.number_3_gasto_presupuesto);
        btnCuatro = (Button) root.findViewById(R.id.number_4_gasto_presupuesto);
        btnCinco = (Button) root.findViewById(R.id.numbre_5_gasto_presupuesto);
        btnSeis = (Button) root.findViewById(R.id.number_6_gasto_presupuesto);
        btnSiete = (Button) root.findViewById(R.id.number_7_gasto_presupuesto);
        btnOcho = (Button) root.findViewById(R.id.numbre_8_gasto_presupuesto);
        btnNueve = (Button) root.findViewById(R.id.number_9_gasto_presupuesto);
        btnPunto = (Button) root.findViewById(R.id.number_punto_gasto_presupuesto);
        btnSuma = (Button) root.findViewById(R.id.btn_mas_gasto_presupuesto);
        btnResta = (Button) root.findViewById(R.id.btn_menos_gasto_presupuesto);
        etProceso = (TextView) root.findViewById(R.id.txt_valor_gasto_presupuesto);
    }

    private byte[] imageViewToByte(ImageView imageView) {
        Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }

}