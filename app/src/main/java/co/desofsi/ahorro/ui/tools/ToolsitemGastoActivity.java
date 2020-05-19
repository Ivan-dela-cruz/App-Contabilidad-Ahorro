package co.desofsi.ahorro.ui.tools;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

import androidx.core.app.ActivityCompat;
import co.desofsi.ahorro.*;
import co.desofsi.ahorro.entidades.CategoriaGasto;
import co.desofsi.ahorro.entidades.ImageManagerCustom;

public class ToolsitemGastoActivity extends AppCompatActivity {


    private ImageView imageView;
    private EditText txt_nombre, txt_presupuesto;
    private TextView txt_position;
    private Button btn_elije, btn_actualizar, btn_eliminar;
    private Switch switch_estado;

    private ImageManagerCustom imageManagerCustom;
    final int REQUEST_CODE_GALLERY = 999;
    private int id, estado;


    DecimalFormat decimalFormat;
    DecimalFormatSymbols separador;
    //TEMAS APP
    private Window window;
    String primaryDark = "#89c29a";
    String primary = "#B9F6CA";
    String background = "#FFFFFF";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_toolsitem_gasto);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Edición categoría");
        window = this.getWindow();
        insertColor();

        init();
        separador = new DecimalFormatSymbols();
        separador.setDecimalSeparator('.');
        decimalFormat = new DecimalFormat("#.00", separador);
        final CategoriaGasto cate = (CategoriaGasto) getIntent().getExtras().getSerializable("CategoriaGasto");

        id = cate.getId();
        txt_position.setText(cate.getNombre());
        imageView.setImageBitmap(imageManagerCustom.decodificar(cate.getImage()));
        txt_nombre.setText(cate.getNombre());
        txt_presupuesto.setText(String.valueOf(cate.getPresupuesto()));

        if(cate.getEstado()==1){
            switch_estado.setChecked(true);
        }else {
            switch_estado.setChecked(false);
        }

        btn_elije.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityCompat.requestPermissions(ToolsitemGastoActivity.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        REQUEST_CODE_GALLERY);

            }
        });



        btn_eliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder aletBuilder = new AlertDialog.Builder(ToolsitemGastoActivity.this);

                aletBuilder.setMessage("Se borrarán todos los gastos relacionados a está categoría");
                aletBuilder.setTitle("Eliminar categoría");
                aletBuilder.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String sql = "DELETE FROM categoria_gasto WHERE id = '" + id + "'";
                        MainActivity.sqLiteHelper.deletedDataTable(sql);

                        Intent intent = new Intent(ToolsitemGastoActivity.this, ListToolsGastosCate.class);
                        startActivity(intent);
                    }
                });
                aletBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                AlertDialog dialog = aletBuilder.create();
                dialog.show();

            }
        });

        btn_actualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {




                AlertDialog.Builder aletBuilder = new AlertDialog.Builder(ToolsitemGastoActivity.this);
                aletBuilder.setTitle("Actualizar categoría");
                aletBuilder.setMessage("¿Está seguro de modificar está categoría");

                aletBuilder.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        double valor_choose = Double.parseDouble(txt_presupuesto.getText().toString());
                        MainActivity.sqLiteHelper.updateDataTableCategory(cate.getId(),txt_nombre.getText().toString(),imageManagerCustom.imageViewToByte(imageView),Double.parseDouble(decimalFormat.format(valor_choose)),estado);

                        Intent intent = new Intent(ToolsitemGastoActivity.this, ListToolsGastosCate.class);
                        startActivity(intent);
                    }
                });
                aletBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                AlertDialog dialog = aletBuilder.create();
                dialog.show();
            }
        });


        switch_estado.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(isChecked){
                    estado = 1;
                }else {
                    estado=0;
                }
            }
        });

    }

    public void init() {
        switch_estado = (Switch) findViewById(R.id.txt_edit_cat_gasto_estado);
        btn_elije = (Button) findViewById(R.id.btn_elije_edit_gasto);
        btn_actualizar = (Button) findViewById(R.id.btn_edit_cate_gasto_acutualizar);
        btn_eliminar = (Button) findViewById(R.id.btn_edit_cate_gasto_eliminar);
        imageManagerCustom = new ImageManagerCustom();
        txt_position = (TextView) findViewById(R.id.text_position);
        imageView = (ImageView) findViewById(R.id.txt_edit_cat_gasto_imagen);
        txt_nombre = (EditText) findViewById(R.id.txt_edit_cat_gasto_nombre);
        txt_presupuesto = (EditText) findViewById(R.id.txt_edit_cat_gasto_presupuesto);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (requestCode == REQUEST_CODE_GALLERY) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, REQUEST_CODE_GALLERY);

            } else {
                Toast.makeText(getApplicationContext(), "No se puede acceder a los archivos!", Toast.LENGTH_SHORT).show();
            }
            return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == REQUEST_CODE_GALLERY && resultCode == RESULT_OK && data != null) {
            Uri uri = data.getData();
            try {
                InputStream inputStream = getContentResolver().openInputStream(uri);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                imageView.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    private byte[] imageViewToByte(ImageView imageView) {
        Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
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
