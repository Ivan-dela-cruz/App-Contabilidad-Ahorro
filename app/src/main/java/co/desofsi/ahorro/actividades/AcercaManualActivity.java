package co.desofsi.ahorro.actividades;

import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import co.desofsi.ahorro.*;
import co.desofsi.ahorro.actividades.ui.main.SectionsPagerAdapter;
import co.desofsi.ahorro.fragmentos.ManualGatos;
import co.desofsi.ahorro.fragmentos.Manualingresos;
import co.desofsi.ahorro.fragmentos.graficas;
import co.desofsi.ahorro.fragmentos.introduccion;

public class AcercaManualActivity extends AppCompatActivity implements
        graficas.OnFragmentInteractionListener,
        introduccion.OnFragmentInteractionListener,
        ManualGatos.OnFragmentInteractionListener,
        Manualingresos.OnFragmentInteractionListener {

    ViewPager viewPager;
    LinearLayout linearLayout;
    TextView[] puntosSlide;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);


        setContentView(R.layout.activity_acerca_manual);
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);

        linearLayout = findViewById(R.id.liner_puntos);
        agregarPuntos(0);
        viewPager.addOnPageChangeListener(viewListener);

    }

    public void agregarPuntos(int pos) {
        puntosSlide = new TextView[4];
        linearLayout.removeAllViews();
        for (int i = 0; i < puntosSlide.length; i++) {
            puntosSlide[i] = new TextView(this);
            puntosSlide[i].setText(Html.fromHtml("&#8226;"));
            puntosSlide[i].setTextSize(35);
            puntosSlide[i].setTextColor(getResources().getColor(R.color.colorBlancoTrasparente));
            linearLayout.addView(puntosSlide[i]);
        }

        if (puntosSlide.length > 0) {
            puntosSlide[pos].setTextColor(getResources().getColor(R.color.colorBlanco));
        }

    }

    ViewPager.OnPageChangeListener viewListener = new ViewPager.OnPageChangeListener() {

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            agregarPuntos(position);
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };
    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}