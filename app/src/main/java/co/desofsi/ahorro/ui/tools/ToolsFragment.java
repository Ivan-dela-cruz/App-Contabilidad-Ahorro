package co.desofsi.ahorro.ui.tools;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import co.desofsi.ahorro.R;
import co.desofsi.ahorro.ui.home.Home_Gastos_Activity;
import co.desofsi.ahorro.ui.home.Home_Ingresos_Activity;

public class ToolsFragment extends Fragment {

    private ToolsViewModel toolsViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        toolsViewModel =
                ViewModelProviders.of(this).get(ToolsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_tools, container, false);


        ///***************CATEGORIA INGREOS
        final LinearLayout linearLayout = (LinearLayout) root.findViewById(R.id.layout_cat_ingresos);
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ListToolsIngresosCate.class);
                startActivity(intent);
            }
        });



        ///***************CATEGORIA GASTOS
        final LinearLayout linearLayoutGastos = (LinearLayout) root.findViewById(R.id.layout_cat_gasto);
        linearLayoutGastos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ListToolsGastosCate.class);
                startActivity(intent);
            }
        });


        ///*************** INGREOS
        final LinearLayout linearLayoutRegistroIngreso = (LinearLayout) root.findViewById(R.id.layout_ingresos);
        linearLayoutRegistroIngreso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Home_Ingresos_Activity.class);
                startActivity(intent);
            }
        });



        ///*************** GASTOS
        final LinearLayout linearLayoutRegistroGastos = (LinearLayout) root.findViewById(R.id.layout_gastos);
        linearLayoutRegistroGastos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Home_Gastos_Activity.class);
                startActivity(intent);
            }
        });






        return root;
    }
}