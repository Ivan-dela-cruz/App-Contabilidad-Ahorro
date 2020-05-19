package co.desofsi.ahorro.actividades.ui.main;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import co.desofsi.ahorro.MainActivity;
import co.desofsi.ahorro.R;
import co.desofsi.ahorro.actividades.ProfileActivity;
import co.desofsi.ahorro.entidades.ImageManagerCustom;
import co.desofsi.ahorro.entidades.Usuario;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Calendar;

public class EditProfileActivity extends AppCompatActivity {


    //TEMAS APP
    private Window window;
    String primaryDark = "#89c29a";
    String primary = "#B9F6CA";
    String background = "#FFFFFF";


    private ImageView imageView;
    private EditText txt_nombre, txt_apellido,txt_genero,txt_fecha;
    private TextView txt_position;
    private Button btn_actualizar, btn_eliminar;
    private String id;
    private ImageManagerCustom imageManagerCustom;
    Calendar calendar;
    ///fechas
    private int dia, mes, anio;
    final int REQUEST_CODE_GALLERY = 999;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Editar Usuario");
        window = this.getWindow();
        insertColor();
        calendar = Calendar.getInstance();
        init();
        final Usuario user = (Usuario) getIntent().getExtras().getSerializable("Usuario");

        id = user.getId();
        txt_position.setText(user.getId());
        imageView.setImageBitmap(imageManagerCustom.decodificar(user.getImagen()));
        txt_nombre.setText(user.getNombre());
        txt_apellido.setText(user.getApellido());
        txt_genero.setText(user.getGenero());
        txt_fecha.setText(String.valueOf(user.getFecha_n()));







        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityCompat.requestPermissions(EditProfileActivity.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        REQUEST_CODE_GALLERY);
            }
        });












        btn_eliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EditProfileActivity.this, ProfileActivity.class);
                startActivity(intent);

            }
        });

        btn_actualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {




                AlertDialog.Builder aletBuilder = new AlertDialog.Builder(EditProfileActivity.this);
                aletBuilder.setTitle("Actualizar categoría");
                aletBuilder.setMessage("¿Está seguro de modificar está categoría");

                aletBuilder.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {


                        MainActivity.sqLiteHelper.updateDataTableUser(user.getId(),
                                txt_nombre.getText().toString(),
                                txt_apellido.getText().toString(),
                                txt_genero.getText().toString(),
                                txt_fecha.getText().toString(),
                                imageManagerCustom.imageViewToByte(imageView)

                        );

                        Intent intent = new Intent(EditProfileActivity.this, ProfileActivity.class);
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


        txt_fecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dia = calendar.get(Calendar.DAY_OF_MONTH);
                mes = calendar.get(Calendar.MONTH);
                anio = calendar.get(Calendar.YEAR);

                DatePickerDialog datePickerDialog = new DatePickerDialog(EditProfileActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        if(dayOfMonth<10){

                            if((month+1)<10){
                                txt_fecha.setText("0"+dayOfMonth + "/0" + (month + 1) + "/" + year);
                            }else{
                                txt_fecha.setText("0"+dayOfMonth + "/" + (month + 1) + "/" + year);
                            }

                        }else {
                            if((month+1)<10){
                                txt_fecha.setText(dayOfMonth + "/0" + (month + 1) + "/" + year);
                            }else{
                                txt_fecha.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
                            }

                        }
                    }
                }, anio, mes, dia);
                datePickerDialog.show();
            }
        });


    }


    public void init() {
        btn_actualizar = (Button) findViewById(R.id.btn_edit_user_acutualizar);
        btn_eliminar = (Button) findViewById(R.id.btn_edit_user_eliminar);
        txt_position = (TextView) findViewById(R.id.text_position);
        imageView = (ImageView) findViewById(R.id.txt_edit_user_imagen);
        txt_nombre = (EditText) findViewById(R.id.txt_edit_user_nombre);
        txt_apellido = (EditText) findViewById(R.id.txt_edit_user_apellido);
        txt_genero = (EditText) findViewById(R.id.txt_edit_user_genero) ;
        txt_fecha = (EditText) findViewById(R.id.txt_edit_user_fecha);
        imageManagerCustom = new ImageManagerCustom();
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

}
