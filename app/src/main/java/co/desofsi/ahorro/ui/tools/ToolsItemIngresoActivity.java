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

import androidx.core.app.ActivityCompat;
import co.desofsi.ahorro.*;
import co.desofsi.ahorro.entidades.CategoriaIngreso;
import co.desofsi.ahorro.entidades.ImageManagerCustom;

public class ToolsItemIngresoActivity extends AppCompatActivity {
    private int id, estado;
    private ImageManagerCustom imageManagerCustom;
    final int REQUEST_CODE_GALLERY = 999;

    private ImageView imageView;
    private EditText txt_nombre;
    private TextView txt_position;
    private Button btn_elije, btn_actualizar, btn_eliminar;
    private Switch switch_estado;
    //TEMAS APP
    private Window window;
    String primaryDark = "#89c29a";
    String primary = "#B9F6CA";
    String background = "#FFFFFF";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tools_item_ingreso);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Edición categoría");
        window = this.getWindow();
        insertColor();

        init();

        final CategoriaIngreso cate = (CategoriaIngreso) getIntent().getExtras().getSerializable("CategoriaIngreso");

        id = cate.getId();
        txt_position.setText(cate.getNombre());
        imageView.setImageBitmap(imageManagerCustom.decodificar(cate.getImage()));
        txt_nombre.setText(cate.getNombre());

        if(cate.getEstado()==1){
            switch_estado.setChecked(true);
        }else {
            switch_estado.setChecked(false);
        }

        btn_elije.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityCompat.requestPermissions(ToolsItemIngresoActivity.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        REQUEST_CODE_GALLERY);

            }
        });



        btn_eliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder aletBuilder = new AlertDialog.Builder(ToolsItemIngresoActivity.this);

                aletBuilder.setMessage("Se borrarán todos los ingresos relacionados a está categoría");
                aletBuilder.setTitle("Eliminar categoría");
                aletBuilder.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String sql = "DELETE FROM categoria_ingreso WHERE id = '" + id + "'";
                        MainActivity.sqLiteHelper.deletedDataTable(sql);

                        Intent intent = new Intent(ToolsItemIngresoActivity.this, ListToolsIngresosCate.class);
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




                AlertDialog.Builder aletBuilder = new AlertDialog.Builder(ToolsItemIngresoActivity.this);
                aletBuilder.setTitle("Actualizar categoría");
                aletBuilder.setMessage("¿Está seguro de modificar está categoría");

                aletBuilder.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        MainActivity.sqLiteHelper.updateDataTableCategoryIngresos(cate.getId(),txt_nombre.getText().toString(),imageManagerCustom.imageViewToByte(imageView),estado);

                        Intent intent = new Intent(ToolsItemIngresoActivity.this, ListToolsIngresosCate.class);
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
        switch_estado = (Switch) findViewById(R.id.txt_edit_cat_ingreso_estado);
        btn_elije = (Button) findViewById(R.id.btn_elije_edit_ingreso);
        btn_actualizar = (Button) findViewById(R.id.btn_edit_cate_ingreso_acutualizar);
        btn_eliminar = (Button) findViewById(R.id.btn_edit_cate_ingreso_eliminar);
        imageManagerCustom = new ImageManagerCustom();
        txt_position = (TextView) findViewById(R.id.text_position);
        imageView = (ImageView) findViewById(R.id.txt_edit_cat_ingreso_imagen);
        txt_nombre = (EditText) findViewById(R.id.txt_edit_cat_ingreso_nombre);
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
