package co.desofsi.ahorro.ui.share;

import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import co.desofsi.ahorro.MainActivity;
import co.desofsi.ahorro.R;

public class ShareFragment extends Fragment {

    private ShareViewModel shareViewModel;

    private Window window;

    private ImageView img_color_primary, img_color_secundary;
    private TextView txt_color_primary, txt_color_secundary;
    private View view_primary, view_secundary;
    private Button btn_color;
    Bitmap bitmap, bitmap_secundary;


    String primaryDark = "#89c29a";
    String primary = "#B9F6CA";
    String background = "#FFFFFF";

    boolean color_primary = true, color_secundary = true;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        shareViewModel =
                ViewModelProviders.of(this).get(ShareViewModel.class);
        View root = inflater.inflate(R.layout.fragment_share, container, false);


        init(root);
        window = getActivity().getWindow();


        img_color_primary.setDrawingCacheEnabled(true);
        img_color_primary.buildDrawingCache(true);


        img_color_secundary.setDrawingCacheEnabled(true);
        img_color_secundary.buildDrawingCache(true);


        img_color_primary.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN || event.getAction() == MotionEvent.ACTION_MOVE) {
                    bitmap = img_color_primary.getDrawingCache();

                    try {
                        int pixel = bitmap.getPixel((int) event.getX(), (int) event.getY());
                        int r = Color.red(pixel);
                        int g = Color.green(pixel);
                        int b = Color.blue(pixel);
                        String hex = "#" + Integer.toHexString(pixel);

                        if(hex.equals("#0")){
                            color_primary = false;
                        }else{

                            primaryDark = hex;
                            view_primary.setBackgroundColor(Color.rgb(r, g, b));
                            txt_color_primary.setText("Color primario " + hex);
                            color_primary = true;
                        }


                    } catch (Exception e) {
                        color_primary = false;
                        txt_color_primary.setText("El color no es válido");
                    }

                }
                return true;
            }
        });

        img_color_secundary.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN || event.getAction() == MotionEvent.ACTION_MOVE) {
                    bitmap_secundary = img_color_secundary.getDrawingCache();

                    try {
                        int pixel = bitmap_secundary.getPixel((int) event.getX(), (int) event.getY());
                        int r = Color.red(pixel);
                        int g = Color.green(pixel);
                        int b = Color.blue(pixel);

                        String hex = "#" + Integer.toHexString(pixel);
                        if(hex.equals("#0")){
                            color_secundary = false;
                        }else{

                            primary = hex;
                            view_secundary.setBackgroundColor(Color.rgb(r, g, b));
                            txt_color_secundary.setText("Color secundario " + hex);
                            color_secundary = true;
                        }

                    } catch (Exception e) {
                        color_secundary = false;
                        txt_color_secundary.setText("El color no es válido");
                    }


                }
                return true;
            }
        });

        btn_color.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(color_primary && color_secundary){
                    cambiarColor(primaryDark, primary, background);
                    Cursor cursor = MainActivity.sqLiteHelper.getDataTable("SELECT * FROM colors WHERE id_user = '"+MainActivity.id_user+"'");
                    if(cursor.moveToFirst()){
                        int id_color = cursor.getInt(0);
                        MainActivity.sqLiteHelper.updateDataTableColors(id_color,primary,primaryDark,background,1);
                    }

                }else {
                    Toast.makeText(getActivity(),"El color no es válido",Toast.LENGTH_SHORT).show();
                }

            }
        });


        return root;
    }


    public void init(View view) {


        img_color_primary = (ImageView) view.findViewById(R.id.id_color_picker_primary);
        img_color_secundary = (ImageView) view.findViewById(R.id.id_color_picker_secundary);
        txt_color_primary = (TextView) view.findViewById(R.id.txt_color_primary);
        txt_color_secundary = (TextView) view.findViewById(R.id.txt_color_secundary);
        view_primary = (View) view.findViewById(R.id.view_primary);
        view_secundary = (View) view.findViewById(R.id.view_secundary);
        btn_color = (Button) view.findViewById(R.id.btn_color);
    }

    private void cambiarColor(String primaryDark, String primary, String background) {

        window.setStatusBarColor(Color.parseColor(primaryDark));
        ((AppCompatActivity) getActivity()).getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor(primary)));
        window.setBackgroundDrawable(new ColorDrawable(Color.parseColor(background)));
        window.setNavigationBarColor(Color.parseColor(primary));

    }


}