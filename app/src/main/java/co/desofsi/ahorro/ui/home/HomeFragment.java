package co.desofsi.ahorro.ui.home;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import co.desofsi.ahorro.MainActivity;
import co.desofsi.ahorro.R;
import co.desofsi.ahorro.adaptadores.RecyclerHomeItemGastosAdapter;
import co.desofsi.ahorro.adaptadores.RecyclerHomeItemIngresosAdapter;
import co.desofsi.ahorro.adaptadores.RecyclerHomeListaIngresosAdapter;
import co.desofsi.ahorro.entidades.Gasto;
import co.desofsi.ahorro.entidades.Ingresos;

public class HomeFragment extends Fragment implements
        RecyclerHomeItemGastosAdapter.OnItemHomeClickListener,
        RecyclerHomeItemIngresosAdapter.OnItemHomeIngresosClickListener, View.OnClickListener {


    Calendar calendar;
    TextView fecha_ingreso_home, fecha_gasto_home, txt_total_ingreos, txt_totalgastos, txt_total_saldos;
    private double total_ingreso, total_saldo, total_gastos;
    ///FECHAS
    private int dia, mes, anio;
    private int anio_re;
    private String mes_re, mes_anterior = "", mes_selecionado = "";


    private HomeViewModel homeViewModel;
    private RecyclerView recyclerView;
    private RecyclerView recyclerViewIngresos;


    private ArrayList<Ingresos> lista_ingresos;
    private ArrayList<Gasto> lista_gastos_recicler;


    //ELEMENTOS CALENDARIO
    private Button enero, febrero, marzo, abril, mayo, junio, julio, agosto, septiembre, octubre, noviembre, diciembre;
    private ImageButton anio_mensos, anio_mas, showcalendar, hidecalendar;
    private TextView anio_texto, mes_selection;
    private LinearLayout box_calendar;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        init(root);


        ////****************************VINCULACION ELEMENTOS XML *********************************

        ImageButton btn_next = (ImageButton) root.findViewById(R.id.btn_ingresos);
        ImageButton btn_next_gasto = (ImageButton) root.findViewById(R.id.btn_next_gasto);

        txt_total_ingreos = (TextView) root.findViewById(R.id.txt_total_ingresos_home);
        txt_totalgastos = (TextView) root.findViewById(R.id.txt_total_gastos);
        txt_total_saldos = (TextView) root.findViewById(R.id.txt_total_saldo);

        fecha_ingreso_home = (TextView) root.findViewById(R.id.text_home_titulo_ingresos);
        fecha_gasto_home = (TextView) root.findViewById(R.id.text_home_titulo_gastos);


        ////*********************   RECYCLER VIEW INGRESOS  **************************************

        recyclerViewIngresos = (RecyclerView) root.findViewById(R.id.recycler_home_ingresos);
        recyclerViewIngresos.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.HORIZONTAL));
        recyclerViewIngresos.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));

        ////*********************   RECYCLER VIEW GASTOS  **************************************

        recyclerView = (RecyclerView) root.findViewById(R.id.recycler_home_gastos);
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.HORIZONTAL));
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));


        //////************************CALENDAR EVENTOS *******************************

        calendar = Calendar.getInstance();
        String currentDate = DateFormat.getDateInstance().format(calendar.getInstance().getTime());

        anio_texto.setText(String.valueOf(getAnio()));
        anio = getAnio();
        mes_re = getMes();

        mes_selection.setText(getMesActual(getMes()));

        /*fecha_ingreso_home.setText("Ingresos / " + currentDate);
        fecha_gasto_home.setText("Gastos / " + currentDate);*/


        fecha_ingreso_home.setText("Ingresos / " + getMesActual(getMes()));
        fecha_gasto_home.setText("Gastos / " + getMesActual(getMes()));


        ////////TOTAL INGRESOS ****************GASTOS ***********************SALDO******
        total_gastos = 0;
        total_saldo = 0;


        ////*********************   RECYCLER VIEW GASTOS  **************************************
       llenarListaGastos();


        ////*********************   RECYCLER VIEW INGRESOS  **************************************
        llenarIngresos();


        /////*****************          CALCULO DE SALDO RESTANTES DEL DIA  ***************************
       calcular();


        /////*************************    EVENTOS BOTONES ****************************************+
        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Home_Ingresos_Activity.class);
                startActivity(intent);

            }
        });
        btn_next_gasto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Home_Gastos_Activity.class);
                startActivity(intent);

            }
        });


        /////****************************EVENTO BOTONES CALENDARIO  ************************************//////////
        enero.setOnClickListener(this);
        febrero.setOnClickListener(this);
        marzo.setOnClickListener(this);
        abril.setOnClickListener(this);
        mayo.setOnClickListener(this);
        junio.setOnClickListener(this);
        julio.setOnClickListener(this);
        agosto.setOnClickListener(this);
        septiembre.setOnClickListener(this);
        octubre.setOnClickListener(this);
        noviembre.setOnClickListener(this);
        diciembre.setOnClickListener(this);

        anio_mas.setOnClickListener(this);
        anio_mensos.setOnClickListener(this);

        showcalendar.setOnClickListener(this);
        hidecalendar.setOnClickListener(this);


        return root;
    }

    private void calcular() {
        total_saldo = total_ingreso - total_gastos;
        txt_total_ingreos.setText("$ " + total_ingreso);
        txt_totalgastos.setText("$ " + total_gastos);
        txt_total_saldos.setText("$ " + total_saldo);
    }


    public String getMes() {
        int mes_get = calendar.get(Calendar.MONTH) + 1;
        String mes_ac = "";

        if (mes_get < 10) {
            mes_re = "0" + mes_get;
            mes_ac = "0" + mes_get;
        } else {
            mes_re = "" + mes_get;
            mes_ac = "" + mes_get;
        }
        return mes_ac;
    }

    public int getAnio() {
        anio_re = calendar.get(Calendar.YEAR);
        return anio_re;
    }

    public String getFechaHoy() {
        String fecha_dia = "";
        dia = calendar.get(Calendar.DAY_OF_MONTH);
        mes = calendar.get(Calendar.MONTH) + 1;
        anio = calendar.get(Calendar.YEAR);
        fecha_dia = dia + "/" + mes + "/" + anio;

        return fecha_dia;
    }


    @Override
    public void onItemHomeClick(int position) {
        Intent intent = new Intent(getActivity(), HomeItemGastosActivity.class);

        String mes_put = mes_re;

        Gasto cate = new Gasto(
                lista_gastos_recicler.get(position).getId(),
                lista_gastos_recicler.get(position).getDescripcion(),
                lista_gastos_recicler.get(position).getFecha(),
                lista_gastos_recicler.get(position).getImagen(),
                lista_gastos_recicler.get(position).getValor(),
                lista_gastos_recicler.get(position).getId_cat()

        );
        intent.putExtra("Gasto", cate);
        intent.putExtra("mes", mes_put);
        startActivity(intent);
    }


    @Override
    public void onItemHomeIngresosClick(int position) {
        Intent intent = new Intent(getActivity(), DetailingresosHomeActivity.class);

        String mes_put = mes_re;
        Ingresos cate = new Ingresos(
                lista_ingresos.get(position).getId(),
                lista_ingresos.get(position).getDescripcion(),
                lista_ingresos.get(position).getFecha(),
                lista_ingresos.get(position).getImagen(),
                lista_ingresos.get(position).getValor(),
                lista_ingresos.get(position).getId_cat()

        );
        intent.putExtra("Ingreso", cate);
        intent.putExtra("mes", mes_put);
        startActivity(intent);
    }


    public void init(View root) {

        enero = (Button) root.findViewById(R.id.btn_enero);
        febrero = (Button) root.findViewById(R.id.btn_febrero);
        marzo = (Button) root.findViewById(R.id.btn_marzo);
        abril = (Button) root.findViewById(R.id.btn_abril);
        mayo = (Button) root.findViewById(R.id.btn_mayo);
        junio = (Button) root.findViewById(R.id.btn_junio);
        julio = (Button) root.findViewById(R.id.btn_julio);
        agosto = (Button) root.findViewById(R.id.btn_agosto);
        septiembre = (Button) root.findViewById(R.id.btn_septiembre);
        octubre = (Button) root.findViewById(R.id.btn_octubre);
        noviembre = (Button) root.findViewById(R.id.btn_noviembre);
        diciembre = (Button) root.findViewById(R.id.btn_diciembre);

        anio_mas = (ImageButton) root.findViewById(R.id.btn_mas_anio);
        anio_mensos = (ImageButton) root.findViewById(R.id.btn_menos_anio);

        showcalendar = (ImageButton) root.findViewById(R.id.show_calendar);
        hidecalendar = (ImageButton) root.findViewById(R.id.hide_calendar);

        anio_texto = (TextView) root.findViewById(R.id.txt_anio);
        mes_selection = (TextView) root.findViewById(R.id.mes_selected);

        box_calendar = (LinearLayout) root.findViewById(R.id.secion_meses);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btn_enero:
                despintar(mes_anterior);
                mes_selecionado = "Enero";
                mes_anterior = "01";
                //Play voicefile
                enero.setBackgroundResource(R.color.md_grey_300);
                mes_selection.setText(mes_selecionado);


                ////funcionalidades del calendario
                fecha_ingreso_home.setText("Ingresos / " + mes_selecionado);
                fecha_gasto_home.setText("Gastos / " + mes_selecionado);
                mes_re = "01";
                llenarListaGastos();
                llenarIngresos();
                calcular();


                break;
            case R.id.btn_febrero:
                despintar(mes_anterior);
                mes_selecionado = "Febrero";
                mes_anterior = "02";
                //Play voicefile
                febrero.setBackgroundResource(R.color.md_grey_300);
                mes_selection.setText(mes_selecionado);

                ////funcionalidades del calendario
                fecha_ingreso_home.setText("Ingresos / " + mes_selecionado);
                fecha_gasto_home.setText("Gastos / " + mes_selecionado);
                mes_re = "02";
                llenarListaGastos();
                llenarIngresos();
                calcular();
                break;
            case R.id.btn_marzo:
                despintar(mes_anterior);
                mes_selecionado = "Marzo";
                mes_anterior = "03";
                //Play voicefile
                marzo.setBackgroundResource(R.color.md_grey_300);
                mes_selection.setText(mes_selecionado);


                ////funcionalidades del calendario
                fecha_ingreso_home.setText("Ingresos / " + mes_selecionado);
                fecha_gasto_home.setText("Gastos / " + mes_selecionado);
                mes_re = "03";
                llenarListaGastos();
                llenarIngresos();
                calcular();
                break;
            case R.id.btn_abril:
                despintar(mes_anterior);
                mes_selecionado = "Abril";
                mes_anterior = "04";
                //Play voicefile
                abril.setBackgroundResource(R.color.md_grey_300);
                mes_selection.setText(mes_selecionado);


                ////funcionalidades del calendario
                fecha_ingreso_home.setText("Ingresos / " + mes_selecionado);
                fecha_gasto_home.setText("Gastos / " + mes_selecionado);
                mes_re = "04";
                llenarListaGastos();
                llenarIngresos();
                calcular();
                break;
            case R.id.btn_mayo:
                despintar(mes_anterior);
                mes_selecionado = "Mayo";
                mes_anterior = "05";
                //Play voicefile
                mayo.setBackgroundResource(R.color.md_grey_300);
                mes_selection.setText(mes_selecionado);

                ////funcionalidades del calendario
                fecha_ingreso_home.setText("Ingresos / " + mes_selecionado);
                fecha_gasto_home.setText("Gastos / " + mes_selecionado);
                mes_re = "05";
                llenarListaGastos();
                llenarIngresos();
                calcular();
                break;
            case R.id.btn_junio:
                despintar(mes_anterior);
                mes_selecionado = "Junio";
                mes_anterior = "06";
                //Play voicefile
                junio.setBackgroundResource(R.color.md_grey_300);
                mes_selection.setText(mes_selecionado);



                ////funcionalidades del calendario
                fecha_ingreso_home.setText("Ingresos / " + mes_selecionado);
                fecha_gasto_home.setText("Gastos / " + mes_selecionado);
                mes_re = "06";
                llenarListaGastos();
                llenarIngresos();
                calcular();
                break;
            case R.id.btn_julio:
                despintar(mes_anterior);
                mes_selecionado = "Julio";
                mes_anterior = "07";
                //Play voicefile
                julio.setBackgroundResource(R.color.md_grey_300);
                mes_selection.setText(mes_selecionado);



                ////funcionalidades del calendario
                fecha_ingreso_home.setText("Ingresos / " + mes_selecionado);
                fecha_gasto_home.setText("Gastos / " + mes_selecionado);
                mes_re = "07";
                llenarListaGastos();
                llenarIngresos();
                calcular();
                break;
            case R.id.btn_agosto:
                despintar(mes_anterior);
                mes_selecionado = "Agosto";
                mes_anterior = "08";
                //Play voicefile
                agosto.setBackgroundResource(R.color.md_grey_300);
                mes_selection.setText(mes_selecionado);


                ////funcionalidades del calendario
                fecha_ingreso_home.setText("Ingresos / " + mes_selecionado);
                fecha_gasto_home.setText("Gastos / " + mes_selecionado);
                mes_re = "08";
                llenarListaGastos();
                llenarIngresos();
                calcular();
                break;
            case R.id.btn_septiembre:
                despintar(mes_anterior);
                mes_selecionado = "Septiembre";
                mes_anterior = "09";
                //Play voicefile
                septiembre.setBackgroundResource(R.color.md_grey_300);
                mes_selection.setText(mes_selecionado);



                ////funcionalidades del calendario
                fecha_ingreso_home.setText("Ingresos / " + mes_selecionado);
                fecha_gasto_home.setText("Gastos / " + mes_selecionado);
                mes_re = "09";
                llenarListaGastos();
                llenarIngresos();
                calcular();
                break;
            case R.id.btn_octubre:
                despintar(mes_anterior);
                mes_selecionado = "Octubre";
                mes_anterior = "10";
                //Play voicefile
                octubre.setBackgroundResource(R.color.md_grey_300);
                mes_selection.setText(mes_selecionado);



                ////funcionalidades del calendario
                fecha_ingreso_home.setText("Ingresos / " + mes_selecionado);
                fecha_gasto_home.setText("Gastos / " + mes_selecionado);
                mes_re = "10";
                llenarListaGastos();
                llenarIngresos();
                calcular();
                break;
            case R.id.btn_noviembre:
                despintar(mes_anterior);
                mes_selecionado = "Noviembre";
                mes_anterior = "11";
                //Play voicefile
                noviembre.setBackgroundResource(R.color.md_grey_300);
                mes_selection.setText(mes_selecionado);



                ////funcionalidades del calendario
                fecha_ingreso_home.setText("Ingresos / " + mes_selecionado);
                fecha_gasto_home.setText("Gastos / " + mes_selecionado);
                mes_re = "11";
                llenarListaGastos();
                llenarIngresos();
                calcular();
                break;
            case R.id.btn_diciembre:
                despintar(mes_anterior);
                mes_selecionado = "Diciembre";
                mes_anterior = "12";
                //Play voicefile
                diciembre.setBackgroundResource(R.color.md_grey_300);
                mes_selection.setText(mes_selecionado);



                ////funcionalidades del calendario
                fecha_ingreso_home.setText("Ingresos / " + mes_selecionado);
                fecha_gasto_home.setText("Gastos / " + mes_selecionado);
                mes_re = "12";
                llenarListaGastos();
                llenarIngresos();
                calcular();
                break;
            case R.id.btn_mas_anio:
                anio++;
                anio_texto.setText(String.valueOf(anio));
                break;
            case R.id.btn_menos_anio:
                anio--;
                anio_texto.setText(String.valueOf(anio));
                break;
            case R.id.hide_calendar:
                box_calendar.setVisibility(View.GONE);
                hidecalendar.setVisibility(View.GONE);
                showcalendar.setVisibility(View.VISIBLE);
                break;
            case R.id.show_calendar:
                box_calendar.setVisibility(View.VISIBLE);
                hidecalendar.setVisibility(View.VISIBLE);
                showcalendar.setVisibility(View.GONE);
                break;

        }
    }

    public void despintar(String mes) {
        switch (mes) {
            case "01":
                enero.setBackgroundResource(R.color.colorBlanco);
                break;
            case "02":
                febrero.setBackgroundResource(R.color.colorBlanco);
                break;
            case "03":
                marzo.setBackgroundResource(R.color.colorBlanco);
                break;
            case "04":
                abril.setBackgroundResource(R.color.colorBlanco);
                break;
            case "05":
                mayo.setBackgroundResource(R.color.colorBlanco);
                break;
            case "06":
                junio.setBackgroundResource(R.color.colorBlanco);
                break;
            case "07":
                julio.setBackgroundResource(R.color.colorBlanco);
                break;
            case "08":
                agosto.setBackgroundResource(R.color.colorBlanco);
                break;
            case "09":
                septiembre.setBackgroundResource(R.color.colorBlanco);
                break;
            case "10":
                octubre.setBackgroundResource(R.color.colorBlanco);
                break;
            case "11":
                noviembre.setBackgroundResource(R.color.colorBlanco);
                break;
            case "12":
                diciembre.setBackgroundResource(R.color.colorBlanco);
                break;
        }
    }

    public String getMesActual(String number_mes) {

        String mes_Actual = "";

        switch (number_mes) {
            case "01":
                mes_Actual = "Enero";
                break;
            case "02":
                mes_Actual = "Febrero";
                break;
            case "03":
                mes_Actual = "Marzo";
                break;
            case "04":
                mes_Actual = "Abril";
                break;
            case "05":
                mes_Actual = "Mayo";
                break;
            case "06":
                mes_Actual = "Junio";
                break;
            case "07":
                mes_Actual = "Julio";
                break;
            case "08":
                mes_Actual = "Agosto";
                break;
            case "09":
                mes_Actual = "Septiembre";
                break;
            case "10":
                mes_Actual = "Octubre";
                break;
            case "11":
                mes_Actual = "Noviembre";
                break;
            case "12":
                mes_Actual = "Diciembre";
                break;
        }

        return mes_Actual;
    }



    public void llenarListaGastos(){
        ////*********************   RECYCLER VIEW GASTOS  **************************************
        recyclerView.clearAnimation();
        lista_gastos_recicler = new ArrayList<>();
        //obteer datos de la base de datos
        String sql2 = "SELECT categoria_gasto.id,categoria_gasto.nombre,gastos.fecha,categoria_gasto.imagen, SUM(gastos.valor) AS total,categoria_gasto.id FROM categoria_gasto LEFT JOIN gastos ON  gastos.id_cat = categoria_gasto.id  AND SUBSTR(gastos.fecha, 7, 4) = '" + anio + "' WHERE SUBSTR(gastos.fecha, 4, 2) = '" + mes_re + "' group by categoria_gasto.nombre";

        Cursor cursor = MainActivity.sqLiteHelper.getDataTable(sql2);

        lista_gastos_recicler.clear();
        total_gastos = 0;
        while (cursor.moveToNext()) {

            int id = cursor.getInt(0);
            String desripcion = cursor.getString(1);
            String fecha = cursor.getString(2);
            byte[] image = cursor.getBlob(3);
            double valor = cursor.getDouble(4);
            total_gastos += valor;
            int id_cat = cursor.getInt(5);
            lista_gastos_recicler.add(new Gasto(id, desripcion, fecha, image, valor, id_cat));

        }
        RecyclerHomeItemGastosAdapter adapterTools = new RecyclerHomeItemGastosAdapter(lista_gastos_recicler, this);

        recyclerView.setAdapter(adapterTools);

        if (total_gastos == 0) {
            fecha_gasto_home.setText("No hay gastos para " + getMesActual(mes_re)+" "+anio);

        } else {

        }
    }

    public void llenarIngresos(){

        lista_ingresos = new ArrayList<>();

        String sql = "SELECT categoria_ingreso.id,categoria_ingreso.nombre,ingresos.fecha,categoria_ingreso.imagen, SUM(ingresos.valor) AS total,categoria_ingreso.id FROM categoria_ingreso LEFT JOIN ingresos ON  ingresos.id_cat = categoria_ingreso.id  AND SUBSTR(ingresos.fecha, 7, 4) = '" + anio + "'  WHERE SUBSTR(ingresos.fecha, 4, 2) = '" + mes_re + "' group by categoria_ingreso.nombre";
        //obteer datos de la base de datos
        Cursor cursor_ingresos = MainActivity.sqLiteHelper.getDataTable(sql);

        lista_ingresos.clear();
        total_ingreso = 0;
        while (cursor_ingresos.moveToNext()) {
            int id_in = cursor_ingresos.getInt(0);
            String desripcion_in = cursor_ingresos.getString(1);
            String fecha_in = cursor_ingresos.getString(2);
            byte[] image_in = cursor_ingresos.getBlob(3);
            double valor_in = cursor_ingresos.getDouble(4);
            total_ingreso += valor_in;
            int id_cat_in = cursor_ingresos.getInt(5);
            lista_ingresos.add(new Ingresos(id_in, desripcion_in, fecha_in, image_in, valor_in, id_cat_in));

        }
        RecyclerHomeItemIngresosAdapter adapterHomeIngresos = new RecyclerHomeItemIngresosAdapter(lista_ingresos, this);

        recyclerViewIngresos.setAdapter(adapterHomeIngresos);

        if (total_ingreso == 0) {
            fecha_ingreso_home.setText("No hay ingresos para " + getMesActual(mes_re)+" "+anio);

        } else {

        }
    }
}