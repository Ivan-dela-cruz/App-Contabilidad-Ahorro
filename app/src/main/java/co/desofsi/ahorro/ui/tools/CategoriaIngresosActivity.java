package co.desofsi.ahorro.ui.tools;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import androidx.core.app.ActivityCompat;
import co.desofsi.ahorro.*;

public class CategoriaIngresosActivity extends AppCompatActivity {


    //layout icons
    private ImageView sandwich1, pan1, pan2, pan3, papas, sopa, pizza, emparedado, pollo, pez, brocheta, asador, soda, helado, manzana, limon, pastel, cafe, coctelcoco, coctel, bebidas, viveres, verduras, legumbres, nachos, plato;
    private ImageView taxi, carro, ambulancia, basurero, barco, avion, busescolar, camion, lavadoraauto, gasolinera, gasolina, bicicleta, carretera, elicoptero, garaje, motocicleta, pare, patineta, radiador, remolque, autos, tren, tranvia;
    private ImageView abrigo, armario, calcetin, carrito, cepillo, cigarro, collar, corbata, crema, espejo, gafas, gorra, labial, libros, mascara, panial, pantalon, pantuflas, pasta, perfume;
    private ImageView joystick, baloncesto, football, billar, bolos, cartas, dados, esquiar, fichas, footballamericano, natacion, peliculas, sala, tenis, velero;
    private ImageView pesas, maquinacorrer, bicimaquina, karate, baile, boxeo, cuerda, gimnacia, pesista, yoga;
    private ImageView camahospital, dentista, doctor, emfermera, frascopastilla, gotero, hospital, jeringa, lecion, maletin, pildora, tratamiento;



    ///familia
    private ImageView icon_bebe_fa, icon_biberon_fa, icon_sonajero_fa, icon_coche_fa, icon_osito_fa, icon_pato_fa, icon_gato_fa;
    //muebles
    private ImageView icon_llave_mu, icon_foco_mu, icon_maceta_mu, icon_rosa_mu, icon_tostadora, icon_microondas, icon_refrigerador, icon_lavadora, icon_ducha, icon_baniera, icon_banio, icon_llavein, icon_calefactor, icon_secadora, icon_armario_mu, icon_cama, icon_escritorio;
    //electronica
    private ImageView icon_telefono, icon_camara, icon_reloj, icon_audifonos, icon_tv, icon_laptop, icon_impresora;
    //educacion
    private ImageView icon_escuela, icon_piano, icon_paleta, icon_compas, icon_guitara, icon_cuaderno, icon_calculadora;
    //personal
    private ImageView icon_boda, icon_documento, icon_unia, icon_peinado;
    //vida
    private ImageView icon_pareja, icon_montania, icon_acampar, icon_palmera;
    //ingresos
    private ImageView icon_tarjeta, icon_dinero, icon_transferencia;




    EditText txt_nom;
    Button btn_elije;
    ImageButton btn_grabar;
    ImageView img_elije;
    final int REQUEST_CODE_GALLERY = 999;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_categoria_ingresos);


        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Nueva categoría ahorros");
        init();

        btn_elije.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityCompat.requestPermissions(CategoriaIngresosActivity.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        REQUEST_CODE_GALLERY);

            }
        });

        btn_grabar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (txt_nom.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(), "Ingrese un nombre porfavor!", Toast.LENGTH_SHORT).show();
                } else {


                    try {
                        MainActivity.sqLiteHelper.insertDataCategoriaIngresos(
                                txt_nom.getText().toString().trim(),
                                imageViewToByte(img_elije), 1,MainActivity.id_user

                        );
                        Toast.makeText(getApplicationContext(), "Agregado exitosamente!", Toast.LENGTH_SHORT).show();
                        txt_nom.setText("");
                        img_elije.setImageResource(R.mipmap.ic_launcher);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

            }
        });


        ///sandwich1, pan1, pan2, pan3,

        sandwich1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                img_elije.setImageResource(R.drawable.sandwich2);
            }
        });
        pan1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                img_elije.setImageResource(R.drawable.pan);
            }
        });
        pan2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                img_elije.setImageResource(R.drawable.pan2);
            }
        });
        pan3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                img_elije.setImageResource(R.drawable.pan3);
            }
        });

        // papas, sopa, pizza, emparedado, pollo, pez,

        papas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                img_elije.setImageResource(R.drawable.papas);
            }
        });
        sopa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                img_elije.setImageResource(R.drawable.sopa);
            }
        });
        pizza.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                img_elije.setImageResource(R.drawable.pizza);
            }
        });
        emparedado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                img_elije.setImageResource(R.drawable.sandwich);
            }
        });
        pollo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                img_elije.setImageResource(R.drawable.pollo);
            }
        });
        pez.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                img_elije.setImageResource(R.drawable.pez);
            }
        });


        // brocheta, asador, soda, helado, manzana, limon,
        brocheta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                img_elije.setImageResource(R.drawable.brocheta);
            }
        });
        asador.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                img_elije.setImageResource(R.drawable.asador);
            }
        });
        soda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                img_elije.setImageResource(R.drawable.soda);
            }
        });
        helado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                img_elije.setImageResource(R.drawable.helado);
            }
        });
        manzana.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                img_elije.setImageResource(R.drawable.manzana);
            }
        });
        limon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                img_elije.setImageResource(R.drawable.limon);
            }
        });

        // pastel, cafe, coctelcoco, coctel, bebidas, viveres, verduras, legumbres, nachos, plato;
        pastel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                img_elije.setImageResource(R.drawable.pastel);
            }
        });
        cafe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                img_elije.setImageResource(R.drawable.cafe);
            }
        });
        coctelcoco.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                img_elije.setImageResource(R.drawable.coctelcoco);
            }
        });
        coctel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                img_elije.setImageResource(R.drawable.coctel);
            }
        });
        bebidas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                img_elije.setImageResource(R.drawable.bebidas);
            }
        });
        viveres.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                img_elije.setImageResource(R.drawable.viveres);
            }
        });
        verduras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                img_elije.setImageResource(R.drawable.verduras);
            }
        });
        legumbres.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                img_elije.setImageResource(R.drawable.legunbres);
            }
        });
        nachos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                img_elije.setImageResource(R.drawable.nachos);
            }
        });
        plato.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                img_elije.setImageResource(R.drawable.plato);
            }
        });


        ///****************************TRASPORTE***************************************************************

        /// taxi, carro, ambulancia, basurero, barco, avion, busescolar, camion, lavadoraauto, gasolinera,
        taxi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                img_elije.setImageResource(R.drawable.taxi);
            }
        });
        carro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                img_elije.setImageResource(R.drawable.car);
            }
        });
        ambulancia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                img_elije.setImageResource(R.drawable.ambulancia);
            }
        });
        basurero.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                img_elije.setImageResource(R.drawable.basura);
            }
        });
        barco.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                img_elije.setImageResource(R.drawable.barco);
            }
        });
        avion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                img_elije.setImageResource(R.drawable.avion);
            }
        });
        busescolar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                img_elije.setImageResource(R.drawable.busescolar);
            }
        });
        camion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                img_elije.setImageResource(R.drawable.camion);
            }
        });
        lavadoraauto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                img_elije.setImageResource(R.drawable.autolavadora);
            }
        });
        gasolinera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                img_elije.setImageResource(R.drawable.gasolinera);
            }
        });


        // gasolina, bicicleta, carretera,elicoptero,  garaje, motocicleta, pare, patineta, radiador, remolque, autos, tren, tranvia;
        gasolina.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                img_elije.setImageResource(R.drawable.gasolina);
            }
        });
        bicicleta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                img_elije.setImageResource(R.drawable.bici);
            }
        });
        carretera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                img_elije.setImageResource(R.drawable.carretera);
            }
        });
        elicoptero.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                img_elije.setImageResource(R.drawable.elicoptero);
            }
        });
        garaje.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                img_elije.setImageResource(R.drawable.garaje);
            }
        });
        motocicleta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                img_elije.setImageResource(R.drawable.motocicleta);
            }
        });
        pare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                img_elije.setImageResource(R.drawable.pare);
            }
        });
        patineta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                img_elije.setImageResource(R.drawable.patineta);
            }
        });
        radiador.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                img_elije.setImageResource(R.drawable.radiador);
            }
        });
        remolque.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                img_elije.setImageResource(R.drawable.remolque);
            }
        });
        autos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                img_elije.setImageResource(R.drawable.autos);
            }
        });
        tren.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                img_elije.setImageResource(R.drawable.tren);
            }
        });
        tranvia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                img_elije.setImageResource(R.drawable.tanvia);
            }
        });


        ////+**********************COMPRAS ************************************************

        ///abrigo, armario,calcetin,carrito,cepillo,cigarro,collar,corbata,crema,espejo,gafas,gorra,

        abrigo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                img_elije.setImageResource(R.drawable.abrigo);
            }
        });
        armario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                img_elije.setImageResource(R.drawable.armario);
            }
        });
        calcetin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                img_elije.setImageResource(R.drawable.calctin);
            }
        });
        carrito.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                img_elije.setImageResource(R.drawable.carrito);
            }
        });
        cepillo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                img_elije.setImageResource(R.drawable.cepillo);
            }
        });
        cigarro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                img_elije.setImageResource(R.drawable.cigarro);
            }
        });
        collar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                img_elije.setImageResource(R.drawable.collar);
            }
        });
        corbata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                img_elije.setImageResource(R.drawable.corbata);
            }
        });
        crema.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                img_elije.setImageResource(R.drawable.crema);
            }
        });
        espejo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                img_elije.setImageResource(R.drawable.espejo);
            }
        });
        gafas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                img_elije.setImageResource(R.drawable.gafas);
            }
        });
        gorra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                img_elije.setImageResource(R.drawable.gorra);
            }
        });

        // labial,libros,mascara,panial,pantalon,pantuflas,pasta,perfume ;

        labial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                img_elije.setImageResource(R.drawable.labial);
            }
        });
        libros.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                img_elije.setImageResource(R.drawable.libros);
            }
        });
        mascara.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                img_elije.setImageResource(R.drawable.mascara);
            }
        });
        panial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                img_elije.setImageResource(R.drawable.panial);
            }
        });
        pantalon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                img_elije.setImageResource(R.drawable.pantalon);
            }
        });
        pantuflas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                img_elije.setImageResource(R.drawable.pantuflas);
            }
        });
        pasta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                img_elije.setImageResource(R.drawable.pasta);
            }
        });
        perfume.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                img_elije.setImageResource(R.drawable.perfume);
            }
        });


        ///*****************ENTRETENIMIENTO********************************
        //joystick,baloncesto,football,billar,bolos,cartas,dados,esquiar,fichas,footballamericano,natacion,peliculas,sala,tenis,velero;
        joystick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                img_elije.setImageResource(R.drawable.joytick);
            }
        });
        baloncesto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                img_elije.setImageResource(R.drawable.baloncesto);
            }
        });
        football.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                img_elije.setImageResource(R.drawable.balonfootball);
            }
        });
        billar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                img_elije.setImageResource(R.drawable.billar);
            }
        });
        bolos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                img_elije.setImageResource(R.drawable.bolos);
            }
        });
        cartas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                img_elije.setImageResource(R.drawable.cartas);
            }
        });
        dados.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                img_elije.setImageResource(R.drawable.dado);
            }
        });
        esquiar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                img_elije.setImageResource(R.drawable.esquiar);
            }
        });
        fichas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                img_elije.setImageResource(R.drawable.ficha);
            }
        });
        footballamericano.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                img_elije.setImageResource(R.drawable.footamericano);
            }
        });
        natacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                img_elije.setImageResource(R.drawable.natacion);
            }
        });
        peliculas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                img_elije.setImageResource(R.drawable.pelicula);
            }
        });
        sala.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                img_elije.setImageResource(R.drawable.sala);
            }
        });
        tenis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                img_elije.setImageResource(R.drawable.tenis);
            }
        });
        velero.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                img_elije.setImageResource(R.drawable.velero);
            }
        });

        ///*****************APTITUD************************
        // pesas,maquinacorrer,bicimaquina,karate,baile,boxeo,cuerda,gimnacia,pesista,yoga;

        pesas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                img_elije.setImageResource(R.drawable.pesa);
            }
        });
        maquinacorrer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                img_elije.setImageResource(R.drawable.maquinacorre);
            }
        });
        bicimaquina.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                img_elije.setImageResource(R.drawable.bicicletamaquina);
            }
        });
        karate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                img_elije.setImageResource(R.drawable.karate);
            }
        });
        baile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                img_elije.setImageResource(R.drawable.baile);
            }
        });
        boxeo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                img_elije.setImageResource(R.drawable.box);
            }
        });
        cuerda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                img_elije.setImageResource(R.drawable.cuerda);
            }
        });
        gimnacia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                img_elije.setImageResource(R.drawable.gimnacia);
            }
        });
        pesista.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                img_elije.setImageResource(R.drawable.pesista);
            }
        });
        yoga.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                img_elije.setImageResource(R.drawable.yoga);
            }
        });


        ///***************************MEDICINA ************************************
        //camahospital,dentista,doctor,emfermera,frascopastilla,gotero,hospital,jeringa,lecion,maletin,pildora,tratamiento;
        camahospital.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                img_elije.setImageResource(R.drawable.camamedica);
            }
        });
        dentista.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                img_elije.setImageResource(R.drawable.diente);
            }
        });
        doctor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                img_elije.setImageResource(R.drawable.doctora);
            }
        });
        emfermera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                img_elije.setImageResource(R.drawable.empermera);
            }
        });
        frascopastilla.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                img_elije.setImageResource(R.drawable.frascopastilla);
            }
        });
        gotero.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                img_elije.setImageResource(R.drawable.gotero);
            }
        });
        hospital.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                img_elije.setImageResource(R.drawable.hospital);
            }
        });
        jeringa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                img_elije.setImageResource(R.drawable.inyeccion);
            }
        });
        lecion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                img_elije.setImageResource(R.drawable.lecion);
            }
        });
        maletin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                img_elije.setImageResource(R.drawable.maletin);
            }
        });
        pildora.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                img_elije.setImageResource(R.drawable.pastillas);
            }
        });
        tratamiento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                img_elije.setImageResource(R.drawable.tratamiento);
            }
        });




        ///ultimAS ADICIONES *************************************
        icon_bebe_fa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                img_elije.setImageResource(R.drawable.bebe_fa);
            }
        });

        icon_biberon_fa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                img_elije.setImageResource(R.drawable.biberon_fa);
            }
        });

        icon_sonajero_fa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                img_elije.setImageResource(R.drawable.sonajero_fa);
            }
        });

        icon_coche_fa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                img_elije.setImageResource(R.drawable.coche_fa);
            }
        });

        icon_osito_fa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                img_elije.setImageResource(R.drawable.oso_fa);
            }
        });

        icon_pato_fa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                img_elije.setImageResource(R.drawable.pato_fa);
            }
        });

        icon_gato_fa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                img_elije.setImageResource(R.drawable.gato_fa);
            }
        });


        icon_llave_mu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                img_elije.setImageResource(R.drawable.llave_mu);
            }
        });

        icon_foco_mu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                img_elije.setImageResource(R.drawable.foco_mu);
            }
        });

        icon_maceta_mu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                img_elije.setImageResource(R.drawable.maceta_mu);
            }
        });

        icon_rosa_mu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                img_elije.setImageResource(R.drawable.rosa_mu);
            }
        });

        icon_tostadora.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                img_elije.setImageResource(R.drawable.tostador_mu);
            }
        });

        icon_microondas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                img_elije.setImageResource(R.drawable.microondas_mu);
            }
        });


        icon_secadora.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                img_elije.setImageResource(R.drawable.secadoraca_mu);
            }
        });

        icon_refrigerador.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                img_elije.setImageResource(R.drawable.refrigerador_mu);
            }
        });


        icon_lavadora.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                img_elije.setImageResource(R.drawable.lavadora);
            }
        });

        icon_ducha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                img_elije.setImageResource(R.drawable.ducha_mu);
            }
        });

        icon_baniera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                img_elije.setImageResource(R.drawable.baniera_mu);
            }
        });

        icon_banio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                img_elije.setImageResource(R.drawable.banio_mu);
            }
        });

        icon_llavein.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                img_elije.setImageResource(R.drawable.llavein_mu);
            }
        });

        icon_calefactor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                img_elije.setImageResource(R.drawable.calefactor_mu);
            }
        });

        icon_armario_mu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                img_elije.setImageResource(R.drawable.armario);
            }
        });


        icon_cama.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                img_elije.setImageResource(R.drawable.cama_mu);
            }
        });

        icon_escritorio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                img_elije.setImageResource(R.drawable.escritorio_mu);
            }
        });


        icon_telefono.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                img_elije.setImageResource(R.drawable.telefono_ele);
            }
        });

        icon_camara.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                img_elije.setImageResource(R.drawable.camara_ele);
            }
        });

        icon_reloj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                img_elije.setImageResource(R.drawable.reloj_ele);
            }
        });

        icon_audifonos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                img_elije.setImageResource(R.drawable.auriculares_ele);
            }
        });

        icon_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                img_elije.setImageResource(R.drawable.tv_ele);
            }
        });

        icon_laptop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                img_elije.setImageResource(R.drawable.laptop_ele);
            }
        });

        icon_impresora.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                img_elije.setImageResource(R.drawable.impresora_ele);
            }
        });


        icon_escuela.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                img_elije.setImageResource(R.drawable.escuela_edu);
            }
        });

        icon_piano.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                img_elije.setImageResource(R.drawable.piano_edu);
            }
        });

        icon_paleta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                img_elije.setImageResource(R.drawable.peleta_edu);
            }
        });

        icon_compas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                img_elije.setImageResource(R.drawable.compas_edu);
            }
        });

        icon_guitara.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                img_elije.setImageResource(R.drawable.guitarra_edu);
            }
        });

        icon_cuaderno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                img_elije.setImageResource(R.drawable.cuaderno_edu);
            }
        });

        icon_calculadora.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                img_elije.setImageResource(R.drawable.calculadora_edu);
            }
        });


        icon_boda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                img_elije.setImageResource(R.drawable.casador_per);
            }
        });

        icon_documento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                img_elije.setImageResource(R.drawable.documento_per);
            }
        });

        icon_unia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                img_elije.setImageResource(R.drawable.manicure_per);
            }
        });

        icon_peinado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                img_elije.setImageResource(R.drawable.peinado_per);
            }
        });


        icon_pareja.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                img_elije.setImageResource(R.drawable.pareja_vi);
            }
        });

        icon_montania.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                img_elije.setImageResource(R.drawable.montania_vi);
            }
        });

        icon_acampar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                img_elije.setImageResource(R.drawable.acampar_vi);
            }
        });

        icon_palmera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                img_elije.setImageResource(R.drawable.palmera_vi);
            }
        });


        icon_tarjeta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                img_elije.setImageResource(R.drawable.tarjeta_in);
            }
        });

        icon_dinero.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                img_elije.setImageResource(R.drawable.dinero_in);
            }
        });

        icon_transferencia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                img_elije.setImageResource(R.drawable.transferencia_in);
            }
        });


    }


    private byte[] imageViewToByte(ImageView imageView) {
        Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
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
                img_elije.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }


    public void init() {

        ///sandwich1, pan1, pan2, pan3, papas, sopa, pizza, emparedado, pollo, pez, brocheta, asador, soda, helado, manzana, limon,


        txt_nom = (EditText) findViewById(R.id.txt_nombre_cat);
        btn_elije = (Button) findViewById(R.id.btn_elije);
        btn_grabar = (ImageButton) findViewById(R.id.btn_salvar);
        img_elije = (ImageView) findViewById(R.id.img_categoria);


        sandwich1 = (ImageView) findViewById(R.id.icon_sandwich);
        pan1 = (ImageView) findViewById(R.id.icon_pan);
        pan2 = (ImageView) findViewById(R.id.icon_pan2);
        pan3 = (ImageView) findViewById(R.id.icon_pan3);

        papas = (ImageView) findViewById(R.id.icon_papa);
        sopa = (ImageView) findViewById(R.id.icon_sopa);
        pizza = (ImageView) findViewById(R.id.icon_pizza);
        emparedado = (ImageView) findViewById(R.id.icon_emparedado);

        pollo = (ImageView) findViewById(R.id.icon_pollo);
        pez = (ImageView) findViewById(R.id.icon_pez);
        brocheta = (ImageView) findViewById(R.id.icon_brocheta);
        asador = (ImageView) findViewById(R.id.icon_asador);

        soda = (ImageView) findViewById(R.id.icon_soda);
        helado = (ImageView) findViewById(R.id.icon_helado);
        manzana = (ImageView) findViewById(R.id.icon_manzana);
        limon = (ImageView) findViewById(R.id.icon_limon);

        // pastel, cafe, coctelcoco, coctel, bebidas, viveres, verduras, legumbres, nachos, plato;
        pastel = (ImageView) findViewById(R.id.icon_pastel);
        cafe = (ImageView) findViewById(R.id.icon_cafe);
        coctelcoco = (ImageView) findViewById(R.id.icon_coctel_coco);
        coctel = (ImageView) findViewById(R.id.icon_coctel);


        verduras = (ImageView) findViewById(R.id.icon_verduras);
        viveres = (ImageView) findViewById(R.id.icon_vivieres);
        bebidas = (ImageView) findViewById(R.id.icon_bedidas);
        legumbres = (ImageView) findViewById(R.id.icon_legumbres);


        nachos = (ImageView) findViewById(R.id.icon_nacho);
        plato = (ImageView) findViewById(R.id.icon_plato);


/// taxi, carro, ambulancia, basurero, barco, avion, busescolar, camion, lavadoraauto, gasolinera, gasolina, bicicleta, carretera,

        taxi = (ImageView) findViewById(R.id.icon_taxi);
        carro = (ImageView) findViewById(R.id.icon_car);
        ambulancia = (ImageView) findViewById(R.id.icon_ambulancia);
        basurero = (ImageView) findViewById(R.id.icon_basura);


        barco = (ImageView) findViewById(R.id.icon_barco);
        avion = (ImageView) findViewById(R.id.icon_avion);
        busescolar = (ImageView) findViewById(R.id.icon_busescolar);
        camion = (ImageView) findViewById(R.id.icon_camion);

        lavadoraauto = (ImageView) findViewById(R.id.icon_auto_lavadora);
        gasolinera = (ImageView) findViewById(R.id.icon_gasolinera);
        gasolina = (ImageView) findViewById(R.id.icon_gasolina);
        bicicleta = (ImageView) findViewById(R.id.icon_bicicleta);

        carretera = (ImageView) findViewById(R.id.icon_carretera);
        // elicoptero,  garaje, motocicleta, pare, patineta, radiador, remolque, autos, tren, tranvia;
        elicoptero = (ImageView) findViewById(R.id.icon_elicoptero);
        garaje = (ImageView) findViewById(R.id.icon_garaje);
        motocicleta = (ImageView) findViewById(R.id.icon_motocicleta);

        pare = (ImageView) findViewById(R.id.icon_pare);
        patineta = (ImageView) findViewById(R.id.icon_patineta);
        radiador = (ImageView) findViewById(R.id.icon_radiador);
        remolque = (ImageView) findViewById(R.id.icon_remolque);

        autos = (ImageView) findViewById(R.id.icon_autos);
        tren = (ImageView) findViewById(R.id.icon_tren);
        tranvia = (ImageView) findViewById(R.id.icon_tranvia);


        ////+**********************COMPRAS ************************************************

        ///abrigo, armario,calcetin,carrito,cepillo,cigarro,collar,corbata,crema,espejo,gafas,gorra,

        abrigo = (ImageView) findViewById(R.id.icon_abrigo);
        armario = (ImageView) findViewById(R.id.icon_armario);
        calcetin = (ImageView) findViewById(R.id.icon_calcetin);
        carrito = (ImageView) findViewById(R.id.icon_carrito);

        cepillo = (ImageView) findViewById(R.id.icon_cepillo);
        cigarro = (ImageView) findViewById(R.id.icon_cigarro);
        collar = (ImageView) findViewById(R.id.icon_collar);
        corbata = (ImageView) findViewById(R.id.icon_corbata);

        crema = (ImageView) findViewById(R.id.icon_crema);
        espejo = (ImageView) findViewById(R.id.icon_espejo);
        gafas = (ImageView) findViewById(R.id.icon_gafas);
        gorra = (ImageView) findViewById(R.id.icon_gorra);

        // labial,libros,mascara,panial,pantalon,pantuflas,pasta,perfume ;
        labial = (ImageView) findViewById(R.id.icon_labial);
        libros = (ImageView) findViewById(R.id.icon_libros);
        mascara = (ImageView) findViewById(R.id.icon_mascara);
        panial = (ImageView) findViewById(R.id.icon_panial);

        pantalon = (ImageView) findViewById(R.id.icon_pantalon);
        pantuflas = (ImageView) findViewById(R.id.icon_patuflas);
        pasta = (ImageView) findViewById(R.id.icon_pasta);
        perfume = (ImageView) findViewById(R.id.icon_perfume);


        ///*****************ENTRETENIMIENTO********************************
        //joystick,baloncesto,football,billar,bolos,cartas,dados,esquiar,fichas,footballamericano,natacion,peliculas,sala,tenis,velero;

        joystick = (ImageView) findViewById(R.id.icon_joysticks);
        baloncesto = (ImageView) findViewById(R.id.icon_baloncesto);
        football = (ImageView) findViewById(R.id.icon_balonfootball);
        billar = (ImageView) findViewById(R.id.icon_billar);

        bolos = (ImageView) findViewById(R.id.icon_bolos);
        cartas = (ImageView) findViewById(R.id.icon_cartas);
        dados = (ImageView) findViewById(R.id.icon_dado);
        esquiar = (ImageView) findViewById(R.id.icon_equiar);

        fichas = (ImageView) findViewById(R.id.icon_ficha);
        footballamericano = (ImageView) findViewById(R.id.icon_footamericano);
        natacion = (ImageView) findViewById(R.id.icon_natacion);
        peliculas = (ImageView) findViewById(R.id.icon_pelicula);

        sala = (ImageView) findViewById(R.id.icon_sala);
        tenis = (ImageView) findViewById(R.id.icon_tenis);
        velero = (ImageView) findViewById(R.id.icon_velero);


        ///*****************APTITUD************************
        // pesas,maquinacorrer,bicimaquina,karate,baile,boxeo,cuerda,gimnacia,pesista,yoga;
        pesas = (ImageView) findViewById(R.id.icon_pesas);
        maquinacorrer = (ImageView) findViewById(R.id.icon_maquinacorredora);
        bicimaquina = (ImageView) findViewById(R.id.icon_bicletamaquina);
        karate = (ImageView) findViewById(R.id.icon_karate);

        baile = (ImageView) findViewById(R.id.icon_baile);
        boxeo = (ImageView) findViewById(R.id.icon_boxeo);
        cuerda = (ImageView) findViewById(R.id.icon_cuerda);
        gimnacia = (ImageView) findViewById(R.id.icon_gimnacia);

        pesista = (ImageView) findViewById(R.id.icon_pesista);
        yoga = (ImageView) findViewById(R.id.icon_yoga);


        ///***************************MEDICINA ************************************
        //camahospital,dentista,doctor,emfermera,frascopastilla,gotero,hospital,jeringa,lecion,maletin,pildora,tratamiento;
        camahospital = (ImageView) findViewById(R.id.icon_camamedica);
        dentista = (ImageView) findViewById(R.id.icon_diente);
        doctor = (ImageView) findViewById(R.id.icon_doctora);
        emfermera = (ImageView) findViewById(R.id.icon_enfermera);

        frascopastilla = (ImageView) findViewById(R.id.icon_frasco);
        gotero = (ImageView) findViewById(R.id.icon_gotero);
        hospital = (ImageView) findViewById(R.id.icon_hospital);
        jeringa = (ImageView) findViewById(R.id.icon_inyeccion);

        lecion = (ImageView) findViewById(R.id.icon_lecion);
        maletin = (ImageView) findViewById(R.id.icon_maletinmedico);
        pildora = (ImageView) findViewById(R.id.icon_pastilla);
        tratamiento = (ImageView) findViewById(R.id.icon_tratamiento);

        /*
        pan1 = (ImageView) findViewById(R.id.icon_pan);
        pan2 = (ImageView) findViewById(R.id.icon_pan2);
        pan3 = (ImageView) findViewById(R.id.icon_pan3);
        pan3 = (ImageView) findViewById(R.id.icon_pan3);

        pan1 = (ImageView) findViewById(R.id.icon_pan);
        pan2 = (ImageView) findViewById(R.id.icon_pan2);
        pan3 = (ImageView) findViewById(R.id.icon_pan3);
        pan3 = (ImageView) findViewById(R.id.icon_pan3);
        */




        icon_bebe_fa = (ImageView) findViewById(R.id.icon_bebe_fa);

        icon_biberon_fa = (ImageView) findViewById(R.id.icon_biberon_fa);

        icon_sonajero_fa = (ImageView) findViewById(R.id.icon_sonajero_fa);

        icon_coche_fa = (ImageView) findViewById(R.id.icon_coche_fa);

        icon_osito_fa = (ImageView) findViewById(R.id.icon_osito_fa);

        icon_pato_fa = (ImageView) findViewById(R.id.icon_pato_fa);

        icon_pato_fa = (ImageView) findViewById(R.id.icon_pato_fa);

        icon_gato_fa = (ImageView) findViewById(R.id.icon_gato_fa);


        icon_llave_mu = (ImageView) findViewById(R.id.icon_llave_mu);

        icon_foco_mu = (ImageView) findViewById(R.id.icon_foco_mu);

        icon_maceta_mu = (ImageView) findViewById(R.id.icon_maceta_mu);

        icon_rosa_mu = (ImageView) findViewById(R.id.icon_rosa_mu);

        icon_tostadora = (ImageView) findViewById(R.id.icon_tostadora);

        icon_microondas = (ImageView) findViewById(R.id.icon_microondas);

        icon_refrigerador = (ImageView) findViewById(R.id.icon_refrigerador);

        icon_lavadora = (ImageView) findViewById(R.id.icon_lavadora);

        icon_ducha = (ImageView) findViewById(R.id.icon_ducha);

        icon_baniera = (ImageView) findViewById(R.id.icon_baniera);

        icon_banio = (ImageView) findViewById(R.id.icon_banio);

        icon_llavein = (ImageView) findViewById(R.id.icon_llavein);

        icon_calefactor = (ImageView) findViewById(R.id.icon_calefactor);

        icon_secadora = (ImageView) findViewById(R.id.icon_secadora);

        icon_armario_mu = (ImageView) findViewById(R.id.icon_armario_mu);

        icon_cama = (ImageView) findViewById(R.id.icon_cama);

        icon_escritorio = (ImageView) findViewById(R.id.icon_escritorio);


        icon_telefono = (ImageView) findViewById(R.id.icon_telefono);

        icon_camara = (ImageView) findViewById(R.id.icon_camara);

        icon_reloj = (ImageView) findViewById(R.id.icon_reloj);

        icon_audifonos = (ImageView) findViewById(R.id.icon_audifonos);

        icon_tv = (ImageView) findViewById(R.id.icon_tv);

        icon_laptop = (ImageView) findViewById(R.id.icon_laptop);

        icon_impresora = (ImageView) findViewById(R.id.icon_impresora);


        icon_escuela = (ImageView) findViewById(R.id.icon_escuela);

        icon_piano = (ImageView) findViewById(R.id.icon_piano);

        icon_paleta = (ImageView) findViewById(R.id.icon_paleta);

        icon_compas = (ImageView) findViewById(R.id.icon_compas);

        icon_guitara = (ImageView) findViewById(R.id.icon_guitara);

        icon_cuaderno = (ImageView) findViewById(R.id.icon_cuaderno);

        icon_calculadora = (ImageView) findViewById(R.id.icon_calculadora);


        icon_boda = (ImageView) findViewById(R.id.icon_boda);

        icon_documento = (ImageView) findViewById(R.id.icon_documento);

        icon_unia = (ImageView) findViewById(R.id.icon_unia);

        icon_peinado = (ImageView) findViewById(R.id.icon_peinado);


        icon_pareja = (ImageView) findViewById(R.id.icon_pareja);

        icon_montania = (ImageView) findViewById(R.id.icon_montania);

        icon_acampar = (ImageView) findViewById(R.id.icon_acampar);

        icon_palmera = (ImageView) findViewById(R.id.icon_palmera);


        icon_tarjeta = (ImageView) findViewById(R.id.icon_tarjeta);

        icon_dinero = (ImageView) findViewById(R.id.icon_dinero);

        icon_transferencia = (ImageView) findViewById(R.id.icon_transferencia);

    }
}
