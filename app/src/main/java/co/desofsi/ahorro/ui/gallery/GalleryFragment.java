package co.desofsi.ahorro.ui.gallery;

import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import android.text.SpannableString;
import android.widget.Toast;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import co.desofsi.ahorro.MainActivity;
import co.desofsi.ahorro.R;
import co.desofsi.ahorro.adaptadores.RecyclerGalleryListaGastosAdapter;
import co.desofsi.ahorro.adaptadores.RecyclerGalleryListaIngresosAdapter;
import co.desofsi.ahorro.entidades.Gasto;
import co.desofsi.ahorro.entidades.Ingresos;

public class GalleryFragment extends Fragment implements View.OnClickListener {

    private GalleryViewModel galleryViewModel;
    PieChart pieChart, pieCharIngreso;
    CardView cardView2, cardView3, cardViewInresos, cardViewGasto;

    private Button text_pie, text_pie_ingresos;
    private ArrayList<Gasto> lista_gastos;
    private ArrayList<Ingresos> lista_ingresos;

    private RecyclerView recyclerView;
    private RecyclerView recyclerViewIngresos;

    private ArrayList<Ingresos> list_ingresos_recycler;
    private ArrayList<Gasto> lista_gastosrecycler;


    //ELEMENTOS CALENDARIO
    private Button enero, febrero, marzo, abril, mayo, junio, julio, agosto, septiembre, octubre, noviembre, diciembre;
    private ImageButton anio_mensos, anio_mas, showcalendar, hidecalendar;
    private TextView anio_texto, mes_selection, encabezado_gastos, encabezado_ingresos;
    private LinearLayout box_calendar;

    Calendar calendar;

    ///FECHAS
    private int dia, mes, anio;
    private int anio_re;
    private String mes_re, mes_anterior = "", mes_selecionado = "";
    int cont;

    private double total_porcentaje, total_porcentaje_ingresos;
    private double total_porcentaje_ingresos1;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        galleryViewModel =
                ViewModelProviders.of(this).get(GalleryViewModel.class);
        View root = inflater.inflate(R.layout.fragment_gallery, container, false);

        init(root);


        //////************************CALENDAR EVENTOS *******************************

        calendar = Calendar.getInstance();
        String currentDate = DateFormat.getDateInstance().format(calendar.getInstance().getTime());

        anio_texto.setText(String.valueOf(getAnio()));
        anio = getAnio();

        mes_selection.setText(getMesActual(getMes()));

        encabezado_ingresos.setText("Ingresos / " + getMesActual(getMes()));
        encabezado_gastos.setText("Gastos / " + getMesActual(getMes()));


        ////*********************   RECYCLER VIEW GASTOS  **************************************

        recyclerView = (RecyclerView) root.findViewById(R.id.recycler_gallery_gastos);
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.HORIZONTAL));
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));


        ////*********************   RECYCLER VIEW INGRESOS  **************************************

        recyclerViewIngresos = (RecyclerView) root.findViewById(R.id.recycler_gallery_ingresos);
        recyclerViewIngresos.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.HORIZONTAL));
        recyclerViewIngresos.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));


        ///*********************LIST VIEW GASTOS HOME ****************************
        llenarArrayGraficoGastos();
        llenarGastos();

        text_pie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Toast.makeText(getActivity(), "Hola pie!", Toast.LENGTH_SHORT).show();
                cardView2.setVisibility(View.GONE);
                cardView3.setVisibility(View.VISIBLE);
                text_pie.setVisibility(View.GONE);
                text_pie_ingresos.setVisibility(View.VISIBLE);
                cardViewGasto.setVisibility(View.GONE);
                cardViewInresos.setVisibility(View.VISIBLE);

                llenarArrayGraficoIngresos();

                llenarIngresos();


            }
        });

        text_pie_ingresos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                text_pie.setVisibility(View.VISIBLE);
                text_pie_ingresos.setVisibility(View.GONE);
                cardView2.setVisibility(View.VISIBLE);
                cardView3.setVisibility(View.GONE);

                cardViewGasto.setVisibility(View.VISIBLE);
                cardViewInresos.setVisibility(View.GONE);


                ///*********************LIST VIEW GASTOS HOME ****************************

                llenarArrayGraficoGastos();

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


    public void init(View root) {


        text_pie = (Button) root.findViewById(R.id.textView2);
        text_pie_ingresos = (Button) root.findViewById(R.id.textView3_ingresos);
        pieChart = root.findViewById(R.id.grafico_pastel_gastos);
        pieCharIngreso = root.findViewById(R.id.grafico_pastel_ingresos);
        cardView2 = root.findViewById(R.id.cardView2);
        cardView3 = root.findViewById(R.id.cardView3);
        cardViewGasto = root.findViewById(R.id.cardview_gasto_gallery);
        cardViewInresos = root.findViewById(R.id.cardview_ingreso_gallery);


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

        encabezado_gastos = (TextView) root.findViewById(R.id.text_home_titulo_gastos);
        encabezado_ingresos = (TextView) root.findViewById(R.id.text_home_titulo_ingresos);
    }


    public void llenarArrayGraficoGastos() {
        //vinculacion del list view
        lista_gastos = new ArrayList<>();
        String sql2 = "SELECT categoria_gasto.id,categoria_gasto.nombre,gastos.fecha,categoria_gasto.imagen, SUM(gastos.valor) AS total FROM categoria_gasto LEFT JOIN gastos ON  gastos.id_cat = categoria_gasto.id  AND SUBSTR(gastos.fecha, 7, 4) = '" + anio + "' WHERE SUBSTR(gastos.fecha, 4, 2) = '" + mes_re + "' group by categoria_gasto.nombre  ORDER BY  total DESC LIMIT 0,5";
        //obteer datos de la base de datos
        Cursor cursor2 = MainActivity.sqLiteHelper.getDataTable(sql2);
        while (cursor2.moveToNext()) {

            int id2 = cursor2.getInt(0);
            String descripcion2 = cursor2.getString(1);
            String fecha2 = cursor2.getString(2);
            byte[] image2 = cursor2.getBlob(3);
            double valor2 = cursor2.getDouble(4);
            lista_gastos.add(new Gasto(id2, descripcion2, fecha2, image2, valor2, id2));
        }
        crearGraficoPastel(lista_gastos);
    }

    public void llenarArrayGraficoIngresos() {
        //vinculacion del list view
        lista_ingresos = new ArrayList<>();
        String sql2 = "SELECT categoria_ingreso.id,categoria_ingreso.nombre,ingresos.fecha,categoria_ingreso.imagen, SUM(ingresos.valor) AS total FROM categoria_ingreso LEFT JOIN ingresos ON  ingresos.id_cat = categoria_ingreso.id  AND SUBSTR(ingresos.fecha, 7, 4) = '" + anio + "' WHERE SUBSTR(ingresos.fecha, 4, 2) = '" + mes_re + "' group by categoria_ingreso.nombre  ORDER BY  total DESC LIMIT 0,5";
        //obteer datos de la base de datos
        Cursor cursor2 = MainActivity.sqLiteHelper.getDataTable(sql2);
        while (cursor2.moveToNext()) {
            int id2 = cursor2.getInt(0);
            String descripcion2 = cursor2.getString(1);
            String fecha2 = cursor2.getString(2);
            byte[] image2 = cursor2.getBlob(3);
            double valor2 = cursor2.getDouble(4);
            lista_ingresos.add(new Ingresos(id2, descripcion2, fecha2, image2, valor2, id2));
        }

        crearGraficoPastelIngreso(lista_ingresos);
    }


    public void llenarGastos() {


        recyclerView.clearAnimation();
        ////*********************   RECYCLER VIEW GASTOS  **************************************
        lista_gastosrecycler = new ArrayList<>();
        //obteer datos de la base de datos

        String sql2 = "SELECT categoria_gasto.id,categoria_gasto.nombre,gastos.fecha,categoria_gasto.imagen, SUM(gastos.valor) AS total,categoria_gasto.id FROM categoria_gasto LEFT JOIN gastos ON  gastos.id_cat = categoria_gasto.id  AND SUBSTR(gastos.fecha, 7, 4) = '" + anio + "' WHERE SUBSTR(gastos.fecha, 4, 2) = '" + mes_re + "' group by categoria_gasto.nombre ORDER BY total DESC";

        Cursor cursor = MainActivity.sqLiteHelper.getDataTable(sql2);

        lista_gastosrecycler.clear();

        total_porcentaje = 0;


        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String desripcion = cursor.getString(1);
            String fecha = cursor.getString(2);
            byte[] image = cursor.getBlob(3);
            double valor = cursor.getDouble(4);
            int id_cat = cursor.getInt(5);
            total_porcentaje += valor;
            lista_gastosrecycler.add(new Gasto(id, desripcion, fecha, image, valor, id_cat));


        }

        RecyclerGalleryListaGastosAdapter adapterTools = new RecyclerGalleryListaGastosAdapter(lista_gastosrecycler, total_porcentaje);

        recyclerView.setAdapter(adapterTools);

        if (total_porcentaje == 0) {
            encabezado_gastos.setText("No hay registros para " + getMesActual(mes_re));

        } else {

        }


    }

    public void llenarIngresos() {
        recyclerViewIngresos.clearAnimation();
        ////*********************   RECYCLER VIEW INGRESOS  **************************************

        list_ingresos_recycler = new ArrayList<>();


        //obteer datos de la base de datos

        String sql = "SELECT categoria_ingreso.id,categoria_ingreso.nombre,ingresos.fecha,categoria_ingreso.imagen, SUM(ingresos.valor) AS total,categoria_ingreso.id FROM categoria_ingreso LEFT JOIN ingresos ON  ingresos.id_cat = categoria_ingreso.id  AND SUBSTR(ingresos.fecha, 7, 4) = '" + anio + "' WHERE SUBSTR(ingresos.fecha, 4, 2) = '" + mes_re + "' group by categoria_ingreso.nombre ORDER BY total DESC";

        Cursor cursor_ingresos = MainActivity.sqLiteHelper.getDataTable(sql);

        list_ingresos_recycler.clear();

        total_porcentaje_ingresos = 0;
        while (cursor_ingresos.moveToNext()) {
            int id_in = cursor_ingresos.getInt(0);
            String desripcion_in = cursor_ingresos.getString(1);
            String fecha_in = cursor_ingresos.getString(2);
            byte[] image_in = cursor_ingresos.getBlob(3);
            double valor_in = cursor_ingresos.getDouble(4);
            int id_cat_in = cursor_ingresos.getInt(5);
            list_ingresos_recycler.add(new Ingresos(id_in, desripcion_in, fecha_in, image_in, valor_in, id_cat_in));
            total_porcentaje_ingresos += valor_in;

        }
        RecyclerGalleryListaIngresosAdapter adapterHomeIngresos = new RecyclerGalleryListaIngresosAdapter(list_ingresos_recycler,total_porcentaje_ingresos);

        recyclerViewIngresos.setAdapter(adapterHomeIngresos);

        if (total_porcentaje_ingresos == 0) {
            encabezado_ingresos.setText("No hay registros para " + getMesActual(mes_re));

        } else {

        }

    }

    private void crearGraficoPastel(ArrayList<Gasto> lista_gastos) {

        pieChart.clear();
        pieChart.setUsePercentValues(true);
        pieChart.getDescription().setEnabled(false);
        pieChart.setExtraOffsets(15, 2, 2, 2);
        pieChart.setDragDecelerationFrictionCoef(0.95f);

        pieChart.setDrawCenterText(true);
        pieChart.setDrawHoleEnabled(true);
        pieChart.setHoleColor(Color.WHITE);
        pieChart.setTransparentCircleRadius(60f);


        Legend l = pieChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setDrawInside(false);
        l.setXEntrySpace(7f);
        l.setYEntrySpace(0f);
        l.setYOffset(0f);


        pieChart.setCenterText("Gastos");

        // Description description = new Description();
        // description.setText("");

        // pieChart.setDescription(description);


        ArrayList<PieEntry> pieEntries = new ArrayList<>();

        for (Gasto gasto : lista_gastos) {
            pieEntries.add(new PieEntry((int) gasto.getValor(), gasto.getDescripcion()));
        }

        //pieChart.animateY(1000, Easing.EaseInCubic);
        PieDataSet pieDataSet = new PieDataSet(pieEntries, "");
        pieDataSet.setColors(ColorTemplate.JOYFUL_COLORS);
        PieData pieData = new PieData(pieDataSet);

        pieChart.setData(pieData);
    }


    private void crearGraficoPastelIngreso(ArrayList<Ingresos> lista_ingreso) {

        pieCharIngreso.clear();
        pieCharIngreso.setUsePercentValues(true);
        pieCharIngreso.getDescription().setEnabled(false);
        pieCharIngreso.setExtraOffsets(15, 2, 2, 2);
        pieCharIngreso.setDragDecelerationFrictionCoef(0.95f);

        pieCharIngreso.setDrawCenterText(true);
        pieCharIngreso.setDrawHoleEnabled(true);
        pieCharIngreso.setHoleColor(Color.WHITE);
        pieCharIngreso.setTransparentCircleRadius(60f);


        Legend l = pieCharIngreso.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setDrawInside(false);
        l.setXEntrySpace(7f);
        l.setYEntrySpace(0f);
        l.setYOffset(0f);


        pieCharIngreso.setCenterText("Ingresos");

        // Description description = new Description();
        // description.setText("");

        // pieChart.setDescription(description);


        ArrayList<PieEntry> pieEntries = new ArrayList<>();

        for (Ingresos ingresos : lista_ingreso) {
            pieEntries.add(new PieEntry((int) ingresos.getValor(), ingresos.getDescripcion()));
        }

        //pieChart.animateY(1000, Easing.EaseInCubic);
        PieDataSet pieDataSet = new PieDataSet(pieEntries, "");
        pieDataSet.setColors(ColorTemplate.JOYFUL_COLORS);
        PieData pieData = new PieData(pieDataSet);

        pieCharIngreso.setData(pieData);
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
                encabezado_ingresos.setText("Ingresos / " + mes_selecionado);
                encabezado_gastos.setText("Gastos / " + mes_selecionado);
                mes_re = "01";

                llenarArrayGraficoGastos();
                llenarGastos();

                llenarArrayGraficoIngresos();
                llenarIngresos();
                break;
            case R.id.btn_febrero:
                despintar(mes_anterior);
                mes_selecionado = "Febrero";
                mes_anterior = "02";
                //Play voicefile
                febrero.setBackgroundResource(R.color.md_grey_300);
                mes_selection.setText(mes_selecionado);

                ////funcionalidades del calendario
                encabezado_ingresos.setText("Ingresos / " + mes_selecionado);
                encabezado_gastos.setText("Gastos / " + mes_selecionado);
                mes_re = "02";

                llenarArrayGraficoGastos();
                llenarGastos();

                llenarArrayGraficoIngresos();
                llenarIngresos();
                break;
            case R.id.btn_marzo:
                despintar(mes_anterior);
                mes_selecionado = "Marzo";
                mes_anterior = "03";
                //Play voicefile
                marzo.setBackgroundResource(R.color.md_grey_300);
                mes_selection.setText(mes_selecionado);


                ////funcionalidades del calendario
                encabezado_ingresos.setText("Ingresos / " + mes_selecionado);
                encabezado_gastos.setText("Gastos / " + mes_selecionado);
                mes_re = "03";

                llenarGastos();
                llenarArrayGraficoGastos();

                llenarArrayGraficoIngresos();
                llenarIngresos();
                break;
            case R.id.btn_abril:
                despintar(mes_anterior);
                mes_selecionado = "Abril";
                mes_anterior = "04";
                //Play voicefile
                abril.setBackgroundResource(R.color.md_grey_300);
                mes_selection.setText(mes_selecionado);


                ////funcionalidades del calendario
                encabezado_ingresos.setText("Ingresos / " + mes_selecionado);
                encabezado_gastos.setText("Gastos / " + mes_selecionado);
                mes_re = "04";

                llenarArrayGraficoGastos();
                llenarGastos();

                llenarArrayGraficoIngresos();
                llenarIngresos();
                break;
            case R.id.btn_mayo:
                despintar(mes_anterior);
                mes_selecionado = "Mayo";
                mes_anterior = "05";
                //Play voicefile
                mayo.setBackgroundResource(R.color.md_grey_300);
                mes_selection.setText(mes_selecionado);

                ////funcionalidades del calendario
                encabezado_ingresos.setText("Ingresos / " + mes_selecionado);
                encabezado_gastos.setText("Gastos / " + mes_selecionado);
                mes_re = "05";
                llenarArrayGraficoGastos();
                llenarGastos();

                llenarArrayGraficoIngresos();
                llenarIngresos();
                break;
            case R.id.btn_junio:
                despintar(mes_anterior);
                mes_selecionado = "Junio";
                mes_anterior = "06";
                //Play voicefile
                junio.setBackgroundResource(R.color.md_grey_300);
                mes_selection.setText(mes_selecionado);


                ////funcionalidades del calendario
                encabezado_ingresos.setText("Ingresos / " + mes_selecionado);
                encabezado_gastos.setText("Gastos / " + mes_selecionado);
                mes_re = "06";

                llenarArrayGraficoGastos();
                llenarGastos();

                llenarArrayGraficoIngresos();
                llenarIngresos();
                break;
            case R.id.btn_julio:
                despintar(mes_anterior);
                mes_selecionado = "Julio";
                mes_anterior = "07";
                //Play voicefile
                julio.setBackgroundResource(R.color.md_grey_300);
                mes_selection.setText(mes_selecionado);


                ////funcionalidades del calendario
                encabezado_ingresos.setText("Ingresos / " + mes_selecionado);
                encabezado_gastos.setText("Gastos / " + mes_selecionado);
                mes_re = "07";

                llenarArrayGraficoGastos();
                llenarGastos();

                llenarArrayGraficoIngresos();
                llenarIngresos();
                break;
            case R.id.btn_agosto:
                despintar(mes_anterior);
                mes_selecionado = "Agosto";
                mes_anterior = "08";
                //Play voicefile
                agosto.setBackgroundResource(R.color.md_grey_300);
                mes_selection.setText(mes_selecionado);


                ////funcionalidades del calendario
                encabezado_ingresos.setText("Ingresos / " + mes_selecionado);
                encabezado_gastos.setText("Gastos / " + mes_selecionado);
                mes_re = "08";
                llenarArrayGraficoGastos();
                llenarGastos();

                llenarArrayGraficoIngresos();
                llenarIngresos();
                break;
            case R.id.btn_septiembre:
                despintar(mes_anterior);
                mes_selecionado = "Septiembre";
                mes_anterior = "09";
                //Play voicefile
                septiembre.setBackgroundResource(R.color.md_grey_300);
                mes_selection.setText(mes_selecionado);


                ////funcionalidades del calendario
                encabezado_ingresos.setText("Ingresos / " + mes_selecionado);
                encabezado_gastos.setText("Gastos / " + mes_selecionado);
                mes_re = "09";
                llenarArrayGraficoGastos();
                llenarGastos();

                llenarArrayGraficoIngresos();
                llenarIngresos();
                break;
            case R.id.btn_octubre:
                despintar(mes_anterior);
                mes_selecionado = "Octubre";
                mes_anterior = "10";
                //Play voicefile
                octubre.setBackgroundResource(R.color.md_grey_300);
                mes_selection.setText(mes_selecionado);


                ////funcionalidades del calendario
                encabezado_ingresos.setText("Ingresos / " + mes_selecionado);
                encabezado_gastos.setText("Gastos / " + mes_selecionado);
                mes_re = "10";
                llenarArrayGraficoGastos();
                llenarGastos();

                llenarArrayGraficoIngresos();
                llenarIngresos();
                break;
            case R.id.btn_noviembre:
                despintar(mes_anterior);
                mes_selecionado = "Noviembre";
                mes_anterior = "11";
                //Play voicefile
                noviembre.setBackgroundResource(R.color.md_grey_300);
                mes_selection.setText(mes_selecionado);


                ////funcionalidades del calendario
                encabezado_ingresos.setText("Ingresos / " + mes_selecionado);
                encabezado_gastos.setText("Gastos / " + mes_selecionado);
                mes_re = "11";
                llenarArrayGraficoGastos();
                llenarGastos();

                llenarArrayGraficoIngresos();
                llenarIngresos();
                break;
            case R.id.btn_diciembre:
                despintar(mes_anterior);
                mes_selecionado = "Diciembre";
                mes_anterior = "12";
                //Play voicefile
                diciembre.setBackgroundResource(R.color.md_grey_300);
                mes_selection.setText(mes_selecionado);


                ////funcionalidades del calendario
                encabezado_ingresos.setText("Ingresos / " + mes_selecionado);
                encabezado_gastos.setText("Gastos / " + mes_selecionado);
                mes_re = "12";

                llenarArrayGraficoGastos();
                llenarGastos();

                llenarArrayGraficoIngresos();
                llenarIngresos();
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



/*
    private SpannableString generateCenterSpannableText() {

        SpannableString s = new SpannableString("MPAndroidChart\ndeveloped by Philipp Jahoda");
        s.setSpan(new RelativeSizeSpan(1.7f), 0, 14, 0);
        s.setSpan(new StyleSpan(Typeface.NORMAL), 14, s.length() - 15, 0);
        s.setSpan(new ForegroundColorSpan(Color.GRAY), 14, s.length() - 15, 0);
        s.setSpan(new RelativeSizeSpan(.8f), 14, s.length() - 15, 0);
        s.setSpan(new StyleSpan(Typeface.ITALIC), s.length() - 14, s.length(), 0);
        s.setSpan(new ForegroundColorSpan(ColorTemplate.getHoloBlue()), s.length() - 14, s.length(), 0);
        return s;
    }
    */

}