package co.desofsi.ahorro.ui.home;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import co.desofsi.ahorro.*;
import co.desofsi.ahorro.adaptadores.RecyclerHomeDetailGastoAdapter;
import co.desofsi.ahorro.entidades.Gasto;

public class HomeItemGastosActivity extends AppCompatActivity {


    private ArrayList<Gasto> arrayList;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_item_gastos);

        ActionBar actionBar = getSupportActionBar();

        Gasto cate = (Gasto) getIntent().getExtras().getSerializable("Gasto");
        String mes = (String) getIntent().getExtras().getSerializable("mes");
        actionBar.setTitle(cate.getDescripcion());

        recyclerView = (RecyclerView) findViewById(R.id.recycler_home_item_gastos);
        recyclerView.addItemDecoration(new DividerItemDecoration(HomeItemGastosActivity.this, DividerItemDecoration.HORIZONTAL));
        recyclerView.setLayoutManager(new LinearLayoutManager(HomeItemGastosActivity.this, LinearLayoutManager.VERTICAL, false));
        arrayList = new ArrayList<>();

        //obteer datos de la base de datos

        String sql2 = "SELECT * FROM gastos WHERE id_cat = '"+cate.getId_cat()+"' AND  SUBSTR(gastos.fecha, 4, 2) = '"+mes+"'";
        Cursor cursor = MainActivity.sqLiteHelper.getDataTable(sql2);
        arrayList.clear();

        while (cursor.moveToNext()) {

            int id = cursor.getInt(0);
            String desripcion = cursor.getString(1);
            String fecha = cursor.getString(2);
            byte[] image = cursor.getBlob(3);
            double valor = cursor.getDouble(4);
            int id_cat = cursor.getInt(5);
            arrayList.add(new Gasto(id, desripcion, fecha, image, valor, id_cat,MainActivity.id_user));

        }

        RecyclerHomeDetailGastoAdapter adapterTools = new RecyclerHomeDetailGastoAdapter(arrayList);

        recyclerView.setAdapter(adapterTools);


    }
}
