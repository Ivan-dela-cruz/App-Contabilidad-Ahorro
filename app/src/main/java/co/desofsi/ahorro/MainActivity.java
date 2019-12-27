package co.desofsi.ahorro;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
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
import com.google.android.material.navigation.NavigationView;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import co.desofsi.ahorro.actividades.LoginActivity;
import co.desofsi.ahorro.actividades.ProfileActivity;
import co.desofsi.ahorro.sqlitehelpers.SQLiteHelper;
import de.hdodenhof.circleimageview.CircleImageView;

import android.view.Menu;
import android.view.View;
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
    private LoginButton loginButton;
    private TextView txt_name, txt_email;
    private CircleImageView circleImageView;
    private ImageView img_default;


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

        circleImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
                intent.putExtra("id_user",id_user);
                startActivity(intent);
            }
        });


        if (AccessToken.getCurrentAccessToken() == null) {
            goLoginScreen();
        } else {
            LoadUserProfile(AccessToken.getCurrentAccessToken());
        }


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
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_DOCUMENT);
        startActivity(intent);
    }

    public void logout(View view) {
        LoginManager.getInstance().logOut();
        goLoginScreen();
    }

    private void LoadUserProfile(AccessToken newAccessToken) {

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

                    // Toast.makeText(MainActivity.this,first_name,Toast.LENGTH_SHORT).show();

                    txt_email.setText(email);
                    txt_name.setText(first_name + " " + last_name);
                    RequestOptions requestOptions = new RequestOptions();
                    requestOptions.dontAnimate();
                    Glide.with(MainActivity.this).load(image_url).into(circleImageView);
                    id_user = id;


                    Bitmap bitmap = ((BitmapDrawable) img_default.getDrawable()).getBitmap();

                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                    byte[] byteArray = stream.toByteArray();

                    Cursor cursor = sqLiteHelper.getDataTable("SELECT * FROM usuarios WHERE id =" + id);

                    if (cursor.moveToFirst()) {
                        Toast.makeText(MainActivity.this, "Y existe", Toast.LENGTH_SHORT).show();
                    } else {
                        sqLiteHelper.insertDataUsuarios(id, first_name, last_name, email, birthday, "Otro", byteArray);

                        Toast.makeText(MainActivity.this, "Registrado", Toast.LENGTH_SHORT).show();
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


}
