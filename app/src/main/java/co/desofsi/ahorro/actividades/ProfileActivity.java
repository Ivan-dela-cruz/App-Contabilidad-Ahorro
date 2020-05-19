package co.desofsi.ahorro.actividades;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import co.desofsi.ahorro.*;
import co.desofsi.ahorro.actividades.ui.main.EditProfileActivity;
import co.desofsi.ahorro.entidades.Usuario;

public class ProfileActivity extends AppCompatActivity {

    //String id, String nombre, String apellido, String email, String fecha_n, String genero, byte[] imagen
    private Usuario usuario;
    private TextView text_nombres, email, fecha_n, genero;
    private ImageView image_user;
    private String id;
    private Button logaout,editar;

    private Bitmap bitmap;
    Intent intent;

    GoogleSignInClient mGoogleSignInClient;
    //TEMAS APP
    private Window window;
    String primaryDark = "#89c29a";
    String primary = "#B9F6CA";
    String background = "#FFFFFF";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        window = this.getWindow();
        insertColor();
        init();
        intent = new Intent(ProfileActivity.this, splahActivity.class);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        // String id = (String) getIntent().getExtras().getSerializable("id_user");
        id = MainActivity.id_user;

        loadProfileUser();

        editar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this, EditProfileActivity.class);
                intent.putExtra("Usuario",usuario);
                startActivity(intent);
            }
        });

        logaout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                switch (v.getId()) {
                    // ...
                    case R.id.btn_cerrar_sesion:
                        switch (MainActivity.btn_press) {
                            case 0:

                                AlertDialog.Builder aletBuilder = new AlertDialog.Builder(ProfileActivity.this);

                                aletBuilder.setMessage("¿Está seguro de cerrar la sesión actual?");
                                aletBuilder.setTitle("Cerrar sesión");
                                aletBuilder.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {


                                        disconnectFromFacebook();
                                        MainActivity.id_user = "";
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



                                break;

                            case 1:

                                AlertDialog.Builder aletBuilder2 = new AlertDialog.Builder(ProfileActivity.this);

                                aletBuilder2.setMessage("¿Está seguro de cerrar la sesión actual?");
                                aletBuilder2.setTitle("Cerrar sesión");
                                aletBuilder2.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        signOut();
                                        MainActivity.id_user = "";
                                        startActivity(intent);
                                    }
                                });
                                aletBuilder2.setNegativeButton("No", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.cancel();
                                    }
                                });
                                AlertDialog dialog2 = aletBuilder2.create();
                                dialog2.show();



                                break;
                        }


                        break;
                    // ...
                }
            }
        });

    }

    public void loadProfileUser() {
        Cursor cursor = MainActivity.sqLiteHelper.getDataTable("SELECT * FROM usuarios WHERE id = '" + id + "'");

        ConnectivityManager connectivityManager = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if (cursor.moveToFirst()) {

            byte[] image = cursor.getBlob(6);
            usuario = new Usuario(
                    cursor.getString(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getString(4),
                    cursor.getString(5),
                    image);

            text_nombres.setText(usuario.getNombre() + " " + usuario.getApellido());
            email.setText(usuario.getEmail());
            genero.setText(usuario.getGenero());
            fecha_n.setText(usuario.getFecha_n());

            byte[] img_user = usuario.getImagen();
            bitmap = BitmapFactory.decodeByteArray(img_user, 0, img_user.length);


            if (networkInfo != null) {
                Glide.with(this).load(String.valueOf(MainActivity.url_image_user)).into(image_user);
            } else {
                image_user.setImageBitmap(bitmap);
            }

        }


    }


    public void disconnectFromFacebook() {

        if (AccessToken.getCurrentAccessToken() == null) {
            return; // already logged out
        }

        new GraphRequest(AccessToken.getCurrentAccessToken(), "/me/permissions/", null, HttpMethod.DELETE.DELETE, new GraphRequest.Callback() {
            @Override
            public void onCompleted(GraphResponse graphResponse) {

                LoginManager.getInstance().logOut();

            }
        }).executeAsync();
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

    private void init() {
        text_nombres = (TextView) findViewById(R.id.txt_names_profile);
        email = (TextView) findViewById(R.id.txt_email_profile);
        genero = (TextView) findViewById(R.id.txt_genero_profile);
        fecha_n = (TextView) findViewById(R.id.txt_birthday_profile);
        image_user = (ImageView) findViewById(R.id.img_profile);
        logaout = (Button) findViewById(R.id.btn_cerrar_sesion);
        editar = (Button) findViewById(R.id.btn_editar_user);

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
