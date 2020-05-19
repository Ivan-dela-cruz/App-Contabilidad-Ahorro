package co.desofsi.ahorro.ui.ahorros;

import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;
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
import co.desofsi.ahorro.MainActivity;
import co.desofsi.ahorro.R;
import co.desofsi.ahorro.adaptadores.RecyclerPresupuestoListaGastosAdapter;
import co.desofsi.ahorro.entidades.CategoriaGasto;

public class AhorrosFragment extends Fragment {

    private AhorrosViewModel ahorroViewModel;


    private FloatingActionButton refrescar, aniadir, aniadir_cate;
    private FloatingActionsMenu menu_botones;
    private RecyclerView recyclerView;
    private BarChart barChart;
    private ArrayList<CategoriaGasto> lista_categoria_gastos;
    ///fechas
    private int dia, mes, anio;
    ///VALORES ALMACENADOS
    Calendar calendar;
    ArrayList<BarEntry> barEntries;
    ArrayList<String> months;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ahorroViewModel =
                ViewModelProviders.of(this).get(AhorrosViewModel.class);
        View root = inflater.inflate(R.layout.ahorros_fragment, container, false);

        init(root);
        getFechaHoy();
        llenar_lista_presupuesto();
        crear_grafico();

        aniadir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AhorrosActivity.class);
                startActivity(intent);
                menu_botones.collapse();
            }
        });

        aniadir_cate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), CategoriaAhorroActivity.class);
                startActivity(intent);
            }
        });


        return root;
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

        //obtner categorias ahorro

        Cursor cursor_ahorro = MainActivity.sqLiteHelper.getDataTable("SELECT * FROM categoria_ahorro WHERE id_user = '" + MainActivity.id_user + "' AND estado = 1 ORDER BY  nombre ASC");

        lista_categoria_gastos.clear();
        while (cursor_ahorro.moveToNext()) {
            String nom_cat = cursor_ahorro.getString(1);
            //obteer datos de la base de datos
            Cursor cursor = MainActivity.sqLiteHelper.getDataTable("SELECT * FROM categoria_gasto WHERE id_user = '" + MainActivity.id_user + "' AND estado = 1 AND nombre = '" + nom_cat + "' ");

            if (cursor.moveToFirst()) {
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

        }


         RecyclerPresupuestoListaGastosAdapter adapterTools = new RecyclerPresupuestoListaGastosAdapter(lista_categoria_gastos);

         recyclerView.setAdapter(adapterTools);
    }


    public void init(View view) {

        barChart = (BarChart) view.findViewById(R.id.grafico_barras_ahorro);
        calendar = Calendar.getInstance();
        refrescar = (FloatingActionButton) view.findViewById(R.id.flt_refrescar);
        aniadir = (FloatingActionButton) view.findViewById(R.id.flt_aniadir_ahorro);
        aniadir_cate = (FloatingActionButton) view.findViewById(R.id.flt_aniadir_categoria);
        menu_botones = (FloatingActionsMenu) view.findViewById(R.id.grupoFlotante);

        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_ahorros_fragent);
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.HORIZONTAL));
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));

        barEntries = new ArrayList<>();
        months = new ArrayList<>();

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

}
