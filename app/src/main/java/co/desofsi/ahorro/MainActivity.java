package co.desofsi.ahorro;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.ColorStateList;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;


import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.material.navigation.NavigationView;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import co.desofsi.ahorro.actividades.LoginActivity;
import co.desofsi.ahorro.actividades.ProfileActivity;
import co.desofsi.ahorro.entidades.CategoriaGasto;
import co.desofsi.ahorro.entidades.CategoriaIngreso;
import co.desofsi.ahorro.entidades.Usuario;
import co.desofsi.ahorro.sqlitehelpers.SQLiteHelper;
import de.hdodenhof.circleimageview.CircleImageView;

import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    public static SQLiteHelper sqLiteHelper;

    public static String id_user = "0";
    public static int btn_press = -1;
    public static String url_image_user = "";
    private LoginButton loginButton;
    private TextView txt_name, txt_email;
    private CircleImageView circleImageView;
    private ImageView img_default;


    //TEMAS APP
    private Window window;
    String primaryDark = "#89c29a";
    String primary = "#B9F6CA";
    String background = "#FFFFFF";

    private Usuario usuario;


    int[][] states = new int[][]{
            new int[]{android.R.attr.state_enabled}, // enabled
            new int[]{-android.R.attr.state_enabled}, // disabled
            new int[]{-android.R.attr.state_checked}, // unchecked
            new int[]{android.R.attr.state_pressed}  // pressed
    };

    int[] colors = new int[]{
            Color.GRAY,
            Color.RED,
            Color.GREEN,
            Color.BLUE
    };


    @SuppressLint("WrongThread")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_main);

        ///****************   EJECUCION SQLITE   *****************

        sqLiteHelper = new SQLiteHelper(this, "ahorro.sqlite", null, 1);
        SQLiteDatabase database = sqLiteHelper.getWritableDatabase();
        database.close();


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_ahorros,
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow,
                R.id.nav_tools, R.id.nav_share, R.id.nav_send)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
        init();
        window = this.getWindow();
        ColorStateList myList = new ColorStateList(states, colors);
        navigationView.setItemIconTintList(myList);
        navigationView.setItemTextColor(myList);

        circleImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
                //intent.putExtra("id_user", id_user);
                startActivity(intent);
            }
        });


        if (AccessToken.getCurrentAccessToken() == null) {
            btn_press = 1;
            loadGoogleProfile();
            insertCategories();
            insertColor();

        } else {
            btn_press = 0;
            LoadUserProfile(AccessToken.getCurrentAccessToken());
            insertCategories();
            insertColor();
        }


    }

    private byte[] imageViewToByte(ImageView imageView) {
        Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    private void goLoginScreen() {
        Intent intent = new Intent(this, LoginActivity.class);
        //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_DOCUMENT);
        startActivity(intent);
    }

    public void logout(View view) {
        LoginManager.getInstance().logOut();
        goLoginScreen();
    }

    private void LoadUserProfile(AccessToken newAccessToken) {


        ConnectivityManager con = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = con.getActiveNetworkInfo();

        if (networkInfo != null) {
            GraphRequest graphRequest = GraphRequest.newMeRequest(newAccessToken, new GraphRequest.GraphJSONObjectCallback() {
                @Override
                public void onCompleted(JSONObject object, GraphResponse response) {

                    try {
                        String first_name = object.getString("first_name");
                        String last_name = object.getString("last_name");
                        String email = object.getString("email");
                        String birthday = object.getString("birthday");
                        String id = object.getString("id");

                        String image_url = "https://graph.facebook.com/" + id + "/picture?type=normal";
                        url_image_user = image_url;


                        //  Glide.with(MainActivity.this).load(image_url).into(circleImageView);
                        id_user = id;


                        Bitmap bitmap = ((BitmapDrawable) img_default.getDrawable()).getBitmap();

                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                        byte[] byteArray = stream.toByteArray();

                        Cursor cursor = sqLiteHelper.getDataTable("SELECT * FROM usuarios WHERE id = '" + id + "'");

                        if (cursor.moveToFirst()) {
                            loadProfileUser();
                            // Toast.makeText(MainActivity.this, "Y existe", Toast.LENGTH_SHORT).show();
                        } else {

                            txt_email.setText(email);
                            txt_name.setText(first_name + " " + last_name);
                            RequestOptions requestOptions = new RequestOptions();
                            requestOptions.dontAnimate();

                            sqLiteHelper.insertDataUsuarios(id, first_name, last_name, email, birthday, "Otro", byteArray);
                            insertCategories();
                            insertColor();

                            Toast.makeText(MainActivity.this, "Usuario registrado exitosamente", Toast.LENGTH_SHORT).show();
                        }


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });

            Bundle parameters = new Bundle();

            parameters.putString("fields", "first_name,last_name,email,id,birthday");
            graphRequest.setParameters(parameters);
            graphRequest.executeAsync();
        } else {
            id_user = AccessToken.getCurrentAccessToken().getUserId();
            Toast.makeText(this, "Error de conexión internet", Toast.LENGTH_SHORT).show();
            loadProfileUser();
        }


    }


    public void loadGoogleProfile() {
        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);
        if (acct != null) {

            try {
                String personName = acct.getDisplayName();
                String personGivenName = acct.getGivenName();
                String personFamilyName = acct.getFamilyName();
                String personEmail = acct.getEmail();
                String personId = acct.getId();
                Uri personPhoto = acct.getPhotoUrl();

                url_image_user = String.valueOf(personPhoto);

                id_user = personId;


                Bitmap bitmap = ((BitmapDrawable) img_default.getDrawable()).getBitmap();

                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                byte[] byteArray = stream.toByteArray();

                Cursor cursor = sqLiteHelper.getDataTable("SELECT * FROM usuarios WHERE id = '" + personId + "'");

                if (cursor.moveToFirst()) {
                    loadProfileUser();
                    // Glide.with(MainActivity.this).load(url_image_user).into(circleImageView);
                    // Toast.makeText(MainActivity.this, "Y existe", Toast.LENGTH_SHORT).show();
                } else {


                    txt_email.setText(personEmail);
                    txt_name.setText(personGivenName + " " + personFamilyName);
                    RequestOptions requestOptions = new RequestOptions();
                    requestOptions.dontAnimate();

                    sqLiteHelper.insertDataUsuarios(personId, personGivenName, personFamilyName, personEmail, "00/00/0000", "Otro", byteArray);

                    Toast.makeText(MainActivity.this, "Usuario registrado exitosamente", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {

            }


        } else {
            goLoginScreen();
        }
    }


    public void loadProfileUser() {
        Cursor cursor = MainActivity.sqLiteHelper.getDataTable("SELECT * FROM usuarios WHERE id = '" + id_user + "'");

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


            txt_name.setText(usuario.getNombre() + " " + usuario.getApellido());
            txt_email.setText(usuario.getEmail());

            ConnectivityManager con = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = con.getActiveNetworkInfo();

            if (networkInfo != null) {
                Glide.with(MainActivity.this).load(url_image_user).into(circleImageView);
            } else {
                byte[] img_user = usuario.getImagen();
                Bitmap bitmap = BitmapFactory.decodeByteArray(img_user, 0, img_user.length);
                circleImageView.setImageBitmap(bitmap);
            }


        }
    }


    public void init() {
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);
        loginButton = (LoginButton) findViewById(R.id.login_button);
        txt_email = (TextView) headerView.findViewById(R.id.textView);
        txt_name = (TextView) headerView.findViewById(R.id.textView2);
        circleImageView = (CircleImageView) headerView.findViewById(R.id.img_user);
        img_default = (ImageView) headerView.findViewById(R.id.img_user_face);


    }



    public void insertCategories() {

        //consulta si hay catgorias agregadas sino existen agregara las categorias por defecto
        Cursor cursor = sqLiteHelper.getDataTable("SELECT * FROM categoria_gasto WHERE id_user = '" + MainActivity.id_user + "'");
        if (cursor.moveToFirst()) {

        } else {
            CategoriaGasto cate = new CategoriaGasto();
            cate.loadCategoryGasto(MainActivity.this);
        }

        //consulta si hay catgorias agregadas sino existen agregara las categorias por defecto
        Cursor cursor2 = sqLiteHelper.getDataTable("SELECT * FROM categoria_ingreso WHERE id_user = '" + MainActivity.id_user + "'");
        if (cursor2.moveToFirst()) {

        } else {
            CategoriaIngreso cate = new CategoriaIngreso();
            cate.loadCategoryIngreso(MainActivity.this);
        }
    }


    public void insertColor() {
        Cursor cursor = sqLiteHelper.getDataTable("SELECT * FROM colors WHERE id_user = '" + MainActivity.id_user + "'");
        if (cursor.moveToFirst()) {
            primaryDark = cursor.getString(2);
            primary = cursor.getString(1);
            background = cursor.getString(3);
            cambiarColor(primaryDark, primary, background);
        } else {
            sqLiteHelper.insertDataColors(primary, primaryDark, background, 1, MainActivity.id_user);
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
