package co.desofsi.ahorro.ui.tools;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import co.desofsi.ahorro.*;
import co.desofsi.ahorro.adaptadores.RecyclerToolsListaIngresosAdapter;
import co.desofsi.ahorro.entidades.CategoriaGasto;
import co.desofsi.ahorro.entidades.CategoriaIngreso;

public class ListToolsIngresosCate extends AppCompatActivity implements RecyclerToolsListaIngresosAdapter.OnItemClickListenerIngreso{

    private FloatingActionButton floting;
    private RecyclerView recyclerView;
    private ArrayList<CategoriaIngreso> arrayList;

    //TEMAS APP
    private Window window;
    String primaryDark = "#89c29a";
    String primary = "#B9F6CA";
    String background = "#FFFFFF";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_list_tools_ingresos_cate);


        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Categoría Ingresos");
        window = this.getWindow();
        insertColor();


        floting = (FloatingActionButton) findViewById(R.id.floating_cate_ingreso);


        recyclerView = (RecyclerView)findViewById(R.id.recycler_ingresos_tools);
        recyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.HORIZONTAL));
        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());


        arrayList = new ArrayList<>();


        //obteer datos de la base de datos
        Cursor cursor = MainActivity.sqLiteHelper.getDataTable("SELECT * FROM categoria_ingreso WHERE id_user = '"+MainActivity.id_user+"' ");

        arrayList.clear();
        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String nombre = cursor.getString(1);
            byte[] image = cursor.getBlob(2);
            int estado = cursor.getInt(3);
            arrayList.add(new CategoriaIngreso(id, nombre, image, estado,MainActivity.id_user));

        }
        RecyclerToolsListaIngresosAdapter adapterTools = new RecyclerToolsListaIngresosAdapter(arrayList,this);

        recyclerView.setAdapter(adapterTools);

        floting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ListToolsIngresosCate.this,CategoriaIngresosActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent(this, ToolsItemIngresoActivity.class);
        CategoriaIngreso cate = new CategoriaIngreso(
                arrayList.get(position).getId(),
                arrayList.get(position).getNombre(),
                arrayList.get(position).getImage(),
                arrayList.get(position).getEstado(),
                MainActivity.id_user

        );
        intent.putExtra("CategoriaIngreso", cate);
        startActivity(intent);
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
