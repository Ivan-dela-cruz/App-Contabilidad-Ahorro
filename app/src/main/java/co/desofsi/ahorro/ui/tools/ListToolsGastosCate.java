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
import co.desofsi.ahorro.adaptadores.RecyclerToolsListaGastosAdapter;
import co.desofsi.ahorro.entidades.CategoriaGasto;

public class ListToolsGastosCate extends AppCompatActivity implements RecyclerToolsListaGastosAdapter.OnItemClickListener {


    private RecyclerView recyclerView;
    private ArrayList<CategoriaGasto> arrayList;

    private FloatingActionButton floting;
    //TEMAS APP
    private Window window;
    String primaryDark = "#89c29a";
    String primary = "#B9F6CA";
    String background = "#FFFFFF";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_list_tools_gastos_cate);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Categoría Gastos");

        init();
        window = this.getWindow();
        insertColor();

        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.HORIZONTAL));
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        llenarLista();

        floting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ListToolsGastosCate.this, CategoriaGastosActivity.class);
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

    public void llenarLista() {
        arrayList = new ArrayList<>();


        //obteer datos de la base de datos
        Cursor cursor = MainActivity.sqLiteHelper.getDataTable("SELECT * FROM categoria_gasto WHERE id_user = '" + MainActivity.id_user + "'");

        arrayList.clear();
        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String nombre = cursor.getString(1);
            byte[] image = cursor.getBlob(2);
            double pre = cursor.getDouble(3);
            int estado = cursor.getInt(4);
            arrayList.add(new CategoriaGasto(id, nombre, image, pre, estado, MainActivity.id_user));

        }
        RecyclerToolsListaGastosAdapter adapterTools = new RecyclerToolsListaGastosAdapter(arrayList, this);

        recyclerView.setAdapter(adapterTools);
    }

    public void init() {
        floting = (FloatingActionButton) findViewById(R.id.floating_cate_gastos);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_gastos_tools);
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
