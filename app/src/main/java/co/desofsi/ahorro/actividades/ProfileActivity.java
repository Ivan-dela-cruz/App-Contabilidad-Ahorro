package co.desofsi.ahorro.actividades;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import co.desofsi.ahorro.*;
import co.desofsi.ahorro.entidades.Usuario;

public class ProfileActivity extends AppCompatActivity {

    //String id, String nombre, String apellido, String email, String fecha_n, String genero, byte[] imagen
    private Usuario usuario;
    private TextView text_nombres,email,fecha_n,genero;
    private ImageView image_user;
    private String id;
    private Button logaout;

    GoogleSignInClient mGoogleSignInClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        init();


        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
       // String id = (String) getIntent().getExtras().getSerializable("id_user");
        /* id = MainActivity.id_user;

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

         */

        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);
        if (acct != null) {
            String personName = acct.getDisplayName();
            String personGivenName = acct.getGivenName();
            String personFamilyName = acct.getFamilyName();
            String personEmail = acct.getEmail();
            String personId = acct.getId();
            Uri personPhoto = acct.getPhotoUrl();
            text_nombres .setText(personName+" "+personGivenName);
            email.setText(personEmail);
            genero.setText(personId);
            fecha_n.setText("12");

            Glide.with(this).load(String.valueOf(personPhoto)).into(image_user);
        }

        logaout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    // ...
                    case R.id.btn_cerrar_sesion:
                        signOut();
                        break;
                    // ...
                }
            }
        });

    }


    private void signOut() {
        mGoogleSignInClient.signOut()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        // ...
                        finish();
                    }
                });
    }

    private void init(){
        text_nombres = (TextView) findViewById(R.id.txt_names_profile);
        email = (TextView) findViewById(R.id.txt_email_profile);
        genero = (TextView) findViewById(R.id.txt_genero_profile);
        fecha_n = (TextView) findViewById(R.id.txt_birthday_profile);
        image_user = (ImageView) findViewById(R.id.img_profile);
        logaout = (Button) findViewById(R.id.btn_cerrar_sesion);

    }
}
