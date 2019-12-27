package co.desofsi.ahorro.ui.share;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import co.desofsi.ahorro.R;

public class ShareFragment extends Fragment {

    private ShareViewModel shareViewModel;
    private Button color1, color2, color3;
    private Window window;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        shareViewModel =
                ViewModelProviders.of(this).get(ShareViewModel.class);
        View root = inflater.inflate(R.layout.fragment_share, container, false);


        init(root);
        window = getActivity().getWindow();

        color1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String primaryDark = "#c2185b";
                String primary = "#e91e63";
                String background = "#FFFFFF";

                cambiarColor(primaryDark, primary, background);
            }
        });
        color2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String primaryDark = "#1976d2";
                String primary = "#2196f3";
                String background = "#FFFFFF";

                cambiarColor(primaryDark, primary, background);
            }
        });
        color3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String primaryDark = "#5d4037";
                String primary = "#795548";
                String background = "#FFFFFF";

                cambiarColor(primaryDark, primary, background);
            }
        });



        return root;
    }



    public void init(View view) {
        color1 = (Button) view.findViewById(R.id.btn_color1);
        color2 = (Button) view.findViewById(R.id.btn_color2);
        color3 = (Button) view.findViewById(R.id.btn_color3);
    }

    private void cambiarColor(String primaryDark, String primary, String background) {

        window.setStatusBarColor(Color.parseColor(primaryDark));
        ((AppCompatActivity) getActivity()).getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor(primary)));
        window.setBackgroundDrawable(new ColorDrawable(Color.parseColor(background)));
        window.setNavigationBarColor(Color.parseColor(primary));

    }


}