package co.desofsi.ahorro.ui.home;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;

import java.util.ArrayList;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import co.desofsi.ahorro.*;
import co.desofsi.ahorro.adaptadores.RecyclerHomeListaIngresosAdapter;
import co.desofsi.ahorro.entidades.Ingresos;

public class DetailingresosHomeActivity extends AppCompatActivity {

    private ArrayList<Ingresos> arrayList;
    private RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailingresos_home);

        ActionBar actionBar = getSupportActionBar();

        Ingresos cate = (Ingresos) getIntent().getExtras().getSerializable("Ingreso");
        String mes = (String) getIntent().getExtras().getSerializable("mes");
        actionBar.setTitle(cate.getDescripcion());



        recyclerView = (RecyclerView) findViewById(R.id.recycler_home_item_ingresos);
        recyclerView.addItemDecoration(new DividerItemDecoration(DetailingresosHomeActivity.this, DividerItemDecoration.HORIZONTAL));
        recyclerView.setLayoutManager(new LinearLayoutManager(DetailingresosHomeActivity.this, LinearLayoutManager.VERTICAL, false));
        arrayList = new ArrayList<>();


        //obteer datos de la base de datos

        String sql2 = "SELECT * FROM ingresos WHERE id_cat = '"+cate.getId_cat()+"' AND  SUBSTR(ingresos.fecha, 4, 2) = '"+mes+"'";
        Cursor cursor = MainActivity.sqLiteHelper.getDataTable(sql2);
        arrayList.clear();

        while (cursor.moveToNext()) {

            int id = cursor.getInt(0);
            String desripcion = cursor.getString(1);
            String fecha = cursor.getString(2);
            byte[] image = cursor.getBlob(3);
            double valor = cursor.getDouble(4);
            int id_cat = cursor.getInt(5);
            arrayList.add(new Ingresos(id, desripcion, fecha, image, valor, id_cat));

        }

        RecyclerHomeListaIngresosAdapter adapterHomeIngresos = new RecyclerHomeListaIngresosAdapter(arrayList);
        recyclerView.setAdapter(adapterHomeIngresos);


    }
}
