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
import co.desofsi.ahorro.adaptadores.RecyclerToolsListaIngresosAdapter;
import co.desofsi.ahorro.entidades.CategoriaIngreso;

public class ListToolsIngresosCate extends AppCompatActivity {

    private Button btn_ingreso;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_list_tools_ingresos_cate);


        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Categoría Ingresos");

        btn_ingreso = (Button) findViewById(R.id.btn_new_cat_ingresos);





        recyclerView = (RecyclerView)findViewById(R.id.recycler_ingresos_tools);
        recyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.HORIZONTAL));
        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());


        final ArrayList<CategoriaIngreso> arrayList = new ArrayList<>();


        //obteer datos de la base de datos
        Cursor cursor = MainActivity.sqLiteHelper.getDataTable("SELECT * FROM categoria_ingreso");

        arrayList.clear();
        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String nombre = cursor.getString(1);
            byte[] image = cursor.getBlob(2);
            int estado = cursor.getInt(3);
            arrayList.add(new CategoriaIngreso(id, nombre, image, estado));

        }
        RecyclerToolsListaIngresosAdapter adapterTools = new RecyclerToolsListaIngresosAdapter(arrayList);

        recyclerView.setAdapter(adapterTools);








        btn_ingreso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ListToolsIngresosCate.this,CategoriaIngresosActivity.class);
                startActivity(intent);
            }
        });
    }
}
