package co.desofsi.ahorro.ui.ahorros;

import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;

import co.desofsi.ahorro.R;
import co.desofsi.ahorro.entidades.CategoriaAhorro;

public class AhorrosFragment extends Fragment {

    private AhorrosViewModel ahorroViewModel;


    private FloatingActionButton refrescar, aniadir;
    private FloatingActionsMenu menu_botones;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ahorroViewModel =
                ViewModelProviders.of(this).get(AhorrosViewModel.class);
        View root = inflater.inflate(R.layout.ahorros_fragment, container, false);

        init(root);

        Button btn_caegoria = (Button) root.findViewById(R.id.btn_aniadir_categoria_ahorro);

        btn_caegoria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), CategoriaAhorroActivity.class);

                startActivity(intent);
            }
        });


        aniadir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AhorrosActivity.class);

                startActivity(intent);

                menu_botones.collapse();
            }
        });



        return root;
    }

    public void init(View view){

        refrescar = (FloatingActionButton) view.findViewById(R.id.flt_refrescar);
        aniadir = (FloatingActionButton) view.findViewById(R.id.flt_aniadir_ahorro);
        menu_botones = (FloatingActionsMenu) view.findViewById(R.id.grupoFlotante);
    }

}
