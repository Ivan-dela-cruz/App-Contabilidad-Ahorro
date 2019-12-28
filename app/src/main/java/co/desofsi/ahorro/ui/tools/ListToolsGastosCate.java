package co.desofsi.ahorro.ui.tools;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import co.desofsi.ahorro.*;
import co.desofsi.ahorro.adaptadores.RecyclerToolsListaGastosAdapter;
import co.desofsi.ahorro.entidades.CategoriaGasto;

public class ListToolsGastosCate extends AppCompatActivity implements RecyclerToolsListaGastosAdapter.OnItemClickListener {

    private Button btn_gastos;
    private RecyclerView recyclerView;
    private ArrayList<CategoriaGasto> arrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_list_tools_gastos_cate);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Categoría Gastos");

        btn_gastos = (Button) findViewById(R.id.btn_new_cat_gastos);

        recyclerView = (RecyclerView)findViewById(R.id.recycler_gastos_tools);
        recyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.HORIZONTAL));
        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));

        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());


       arrayList = new ArrayList<>();


        //obteer datos de la base de datos
        Cursor cursor = MainActivity.sqLiteHelper.getDataTable("SELECT * FROM categoria_gasto WHERE id_user = '"+MainActivity.id_user+"'");

        arrayList.clear();
        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String nombre = cursor.getString(1);
            byte[] image = cursor.getBlob(2);
            double pre = cursor.getDouble(3);
            int estado = cursor.getInt(4);
            arrayList.add(new CategoriaGasto(id, nombre, image, pre, estado,MainActivity.id_user));

        }
        RecyclerToolsListaGastosAdapter adapterTools = new RecyclerToolsListaGastosAdapter(arrayList , this);

        recyclerView.setAdapter(adapterTools);







        btn_gastos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ListToolsGastosCate.this,CategoriaGastosActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onItemClick(int position) {

        Intent intent = new Intent(this, ToolsitemGastoActivity.class);
        CategoriaGasto cate = new CategoriaGasto(
                arrayList.get(position).getId(),
                arrayList.get(position).getNombre(),
                arrayList.get(position).getImage(),
                arrayList.get(position).getPresupuesto(),
                arrayList.get(position).getEstado(),
                MainActivity.id_user

        );
        intent.putExtra("CategoriaGasto", cate);
        startActivity(intent);
    }
}
