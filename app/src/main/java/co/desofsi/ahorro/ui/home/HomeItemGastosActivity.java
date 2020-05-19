package co.desofsi.ahorro.ui.home;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Window;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import co.desofsi.ahorro.*;
import co.desofsi.ahorro.adaptadores.RecyclerHomeDetailGastoAdapter;
import co.desofsi.ahorro.entidades.Gasto;

public class HomeItemGastosActivity extends AppCompatActivity implements RecyclerHomeDetailGastoAdapter.OnFiltroHomeClickListener {


    private ArrayList<Gasto> arrayList;
    private RecyclerView recyclerView;

    private LineChart chart;

    private Gasto cate;
    private String mes;
    private int anio;

    //TEMAS APP
    private Window window;
    String primaryDark = "#89c29a";
    String primary = "#B9F6CA";
    String background = "#FFFFFF";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_item_gastos);

        ActionBar actionBar = getSupportActionBar();
        window = this.getWindow();
        insertColor();

        cate = (Gasto) getIntent().getExtras().getSerializable("Gasto");
        mes = (String) getIntent().getExtras().getSerializable("mes");
        anio = (int) getIntent().getExtras().getSerializable("anio");
        actionBar.setTitle(cate.getDescripcion());
        initChart();
        recyclerView = (RecyclerView) findViewById(R.id.recycler_home_item_gastos);
        recyclerView.addItemDecoration(new DividerItemDecoration(HomeItemGastosActivity.this, DividerItemDecoration.HORIZONTAL));
        recyclerView.setLayoutManager(new LinearLayoutManager(HomeItemGastosActivity.this, LinearLayoutManager.VERTICAL, false));

        llenarListGastos();


    }


    public void llenarListGastos() {
        arrayList = new ArrayList<>();

        //obteer datos de la base de datos

        String sql2 = "SELECT * FROM gastos WHERE id_cat = '" + cate.getId_cat() + "'  AND gastos.id_user = '" + MainActivity.id_user + "'  AND SUBSTR(gastos.fecha, 7, 4) = '" + anio + "'  AND  SUBSTR(gastos.fecha, 4, 2) = '" + mes + "'";
        Cursor cursor = MainActivity.sqLiteHelper.getDataTable(sql2);
        arrayList.clear();

        while (cursor.moveToNext()) {

            int id = cursor.getInt(0);
            String desripcion = cursor.getString(1);
            String fecha = cursor.getString(2);
            byte[] image = cursor.getBlob(3);
            double valor = cursor.getDouble(4);
            int id_cat = cursor.getInt(5);
            arrayList.add(new Gasto(id, desripcion, fecha, image, valor, id_cat, MainActivity.id_user));

        }

        RecyclerHomeDetailGastoAdapter adapterTools = new RecyclerHomeDetailGastoAdapter(arrayList,this);

        recyclerView.setAdapter(adapterTools);
    }

    public void initChart() {
        LineChart chart = (LineChart) findViewById(R.id.chart1);

        ArrayList<Entry> values1 = new ArrayList<>();


        for (int i = 1; i < 13; i++) {

            String mes = "";
            double total = 0;
            if (i < 10) {
                mes = "0" + i;
            } else {
                mes = "" + i;
            }
            String sql = "SELECT valor FROM gastos WHERE id_cat = '" + cate.getId_cat() + "'  AND gastos.id_user = '" + MainActivity.id_user + "' AND SUBSTR(gastos.fecha, 7, 4) = '" + anio + "'  AND  SUBSTR(gastos.fecha, 4, 2) = '" + mes + "'";

            Cursor cursor = MainActivity.sqLiteHelper.getDataTable(sql);

            while (cursor.moveToNext()) {
                total += cursor.getDouble(0);
            }

            values1.add(new Entry(i, (int) total));
        }

        LineDataSet d1 = new LineDataSet(values1, "Gastos  " + cate.getDescripcion() + " (Enero - Diciembre)");
        d1.setLineWidth(2.5f);
        d1.setCircleRadius(4.5f);
        d1.setHighLightColor(Color.rgb(244, 117, 117));
        d1.setDrawValues(false);

        LineData lineData = new LineData(d1);
        chart.setData(lineData);
        chart.invalidate(); // refre
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

    @Override
    public void onFilroHomeClick(int position) {
        Intent intent = new Intent(HomeItemGastosActivity.this, EditHomeGastosActivity.class);

        Gasto cate = new Gasto(
                arrayList.get(position).getId(),
                arrayList.get(position).getDescripcion(),
                arrayList.get(position).getFecha(),
                arrayList.get(position).getImagen(),
                arrayList.get(position).getValor(),
                arrayList.get(position).getId_cat(),
                MainActivity.id_user

        );
        intent.putExtra("Gasto", cate);
        startActivity(intent);
    }
}
