package co.desofsi.ahorro.adaptadores;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.text.DecimalFormat;
import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import co.desofsi.ahorro.R;
import co.desofsi.ahorro.entidades.CategoriaGasto;
import co.desofsi.ahorro.entidades.Gasto;
import co.desofsi.ahorro.viewholders.ViewHolderGastosGallery;
import co.desofsi.ahorro.viewholders.ViewHolderGastosPresupuesto;

public class RecyclerPresupuestoListaGastosAdapter extends RecyclerView.Adapter<ViewHolderGastosPresupuesto> {

    private ArrayList<CategoriaGasto> listDatos;


    public RecyclerPresupuestoListaGastosAdapter(ArrayList<CategoriaGasto> listDatos) {
        this.listDatos = listDatos;

    }

    @NonNull
    @Override
    public ViewHolderGastosPresupuesto onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_recycler_galllery_gastos, parent, false);
        return new ViewHolderGastosPresupuesto(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderGastosPresupuesto holder, int position) {

        DecimalFormat formato2 = new DecimalFormat("#.#");
        double porcentaje = (listDatos.get(position).getGasto_mensual() * 100) / (listDatos.get(position).getPresupuesto());

        holder.setDescription(listDatos.get(position).getNombre());
        holder.setImage(listDatos.get(position).getImage());
        holder.setValor(String.valueOf(listDatos.get(position).getPresupuesto()));
        holder.setProgresbar(String.valueOf(porcentaje));
        holder.setText_porcentaje(formato2.format(porcentaje)+" %");
    }

    @Override
    public int getItemCount() {
        return listDatos.size();
    }
}
