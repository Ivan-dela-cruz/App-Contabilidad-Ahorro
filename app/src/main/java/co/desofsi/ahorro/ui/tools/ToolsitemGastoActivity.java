package co.desofsi.ahorro.ui.tools;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import co.desofsi.ahorro.*;
import co.desofsi.ahorro.entidades.CategoriaGasto;

public class ToolsitemGastoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_toolsitem_gasto);

        CategoriaGasto cate = (CategoriaGasto) getIntent().getExtras().getSerializable("CategoriaGasto");

        TextView txt_position = (TextView) findViewById(R.id.text_position);
        txt_position.setText(cate.getNombre());

    }
}
