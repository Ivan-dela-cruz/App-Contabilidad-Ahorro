package co.desofsi.ahorro.ui.slideshow;

import androidx.appcompat.app.AppCompatActivity;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Window;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import java.util.ArrayList;
import java.util.Calendar;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import co.desofsi.ahorro.*;
import co.desofsi.ahorro.adaptadores.RecyclerPresupuestoListaGastosAdapter;
import co.desofsi.ahorro.entidades.CategoriaGasto;

public class PresupuestosSlideShowActivity extends AppCompatActivity {


    private BarChart barChart;
    private ArrayList<CategoriaGasto> lista_categoria_gastos;
    ///fechas
    private int dia, mes, anio;
    ///VALORES ALMACENADOS
    Calendar calendar;
    ArrayList<BarEntry> barEntries;
    ArrayList<String> months;
    private RecyclerView recyclerView;

    //TEMAS APP
    private Window window;
    String primaryDark = "#89c29a";
    String primary = "#B9F6CA";
    String background = "#FFFFFF";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_presupuestos_slide_show);
        window = this.getWindow();
        insertColor();
        init();
        barChart = (BarChart) findViewById(R.id.grafico_barras_presupuesto);
        calendar = Calendar.getInstance();
        getFechaHoy();

        llenar_lista_presupuesto();
        crear_grafico();


    }

    public void init() {

        ////*********************   RECYCLER VIEW GASTOS  **************************************

        recyclerView = (RecyclerView) findViewById(R.id.recycler_presupuesto);
        recyclerView.addItemDecoration(new DividerItemDecoration(PresupuestosSlideShowActivity.this, DividerItemDecoration.HORIZONTAL));
        recyclerView.setLayoutManager(new LinearLayoutManager(PresupuestosSlideShowActivity.this, LinearLayoutManager.VERTICAL, false));

        barEntries = new ArrayList<>();
        months = new ArrayList<>();
    }

    public void crear_grafico() {

        int contador = 0;
        for (CategoriaGasto categoriaGasto : lista_categoria_gastos) {

            if (categoriaGasto.getGasto_mensual() > 0) {
                barEntries.add(new BarEntry(contador, (float) categoriaGasto.getGasto_mensual()));
                months.add(categoriaGasto.getNombre());
                contador++;
            }

        }


        BarDataSet barDataSet = new BarDataSet(barEntries, "Presupuesto");
        barDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        BarData data = new BarData(barDataSet);

        barChart.getXAxis().setValueFormatter(new IndexAxisValueFormatter(months));
        barChart.getXAxis().setGranularity(1f);
        barChart.setData(data);
        barChart.animateY(4000);


        Description description = new Description();
        description.setText("Gráfica estados de presupuestos ");
        barChart.setDescription(description);
    }

    public void llenar_lista_presupuesto() {
        lista_categoria_gastos = new ArrayList<>();
        String mes_re = "";
        if (mes < 10) {
            mes_re = "0" + mes;
        } else {
            mes_re = "" + mes;
        }

        //obteer datos de la base de datos
        Cursor cursor = MainActivity.sqLiteHelper.getDataTable("SELECT * FROM categoria_gasto WHERE id_user = '" + MainActivity.id_user + "' AND estado = 1 ORDER BY  nombre ASC");
        lista_categoria_gastos.clear();
        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String nombre = cursor.getString(1);
            byte[] image = cursor.getBlob(2);
            double pre = cursor.getDouble(3);
            int estado = cursor.getInt(4);
            double total = 0;
            Cursor mensual = MainActivity.sqLiteHelper.getDataTable("SELECT  SUM(gastos.valor) AS total FROM categoria_gasto LEFT JOIN gastos ON  (gastos.id_cat = categoria_gasto.id)  AND categoria_gasto.id = '" + id + "'  AND categoria_gasto.id_user = '" + MainActivity.id_user + "' AND SUBSTR(gastos.fecha, 4, 2) = '" + mes_re + "' AND categoria_gasto.estado = 1");
            if (mensual.moveToFirst()) {
                total = mensual.getDouble(0);
            }
            lista_categoria_gastos.add(new CategoriaGasto(id, nombre, image, pre, estado, total));

        }

        RecyclerPresupuestoListaGastosAdapter adapterTools = new RecyclerPresupuestoListaGastosAdapter(lista_categoria_gastos);

        recyclerView.setAdapter(adapterTools);
    }

    public void getFechaHoy() {
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
