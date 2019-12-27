package co.desofsi.ahorro.actividades;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import co.desofsi.ahorro.*;
import co.desofsi.ahorro.entidades.Usuario;

public class ProfileActivity extends AppCompatActivity {

    //String id, String nombre, String apellido, String email, String fecha_n, String genero, byte[] imagen
    private Usuario usuario;
    private TextView text_nombres,email,fecha_n,genero;
    private ImageView image_user;
    private String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        init();
       // String id = (String) getIntent().getExtras().getSerializable("id_user");
         id = MainActivity.id_user;

        Cursor cursor = MainActivity.sqLiteHelper.getDataTable("SELECT * FROM usuarios WHERE id = "+id);

        if (cursor.moveToFirst()){

            byte[] image = cursor.getBlob(6);
            usuario = new Usuario(
                    cursor.getString(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getString(4),
                    cursor.getString(5),
                    image);

            text_nombres .setText(usuario.getNombre()+" "+usuario.getApellido());
            email.setText(usuario.getEmail());
            genero.setText(usuario.getGenero());
            fecha_n.setText(usuario.getFecha_n());

            byte[] img_user = usuario.getImagen();
            Bitmap bitmap = BitmapFactory.decodeByteArray(img_user, 0, img_user.length);
            image_user.setImageBitmap(bitmap);



        }



    }

    private void init(){
        text_nombres = (TextView) findViewById(R.id.txt_names_profile);
        email = (TextView) findViewById(R.id.txt_email_profile);
        genero = (TextView) findViewById(R.id.txt_genero_profile);
        fecha_n = (TextView) findViewById(R.id.txt_birthday_profile);
        image_user = (ImageView) findViewById(R.id.img_profile);

    }
}
