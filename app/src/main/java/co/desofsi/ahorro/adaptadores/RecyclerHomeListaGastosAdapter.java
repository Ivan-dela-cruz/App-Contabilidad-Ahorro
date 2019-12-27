package co.desofsi.ahorro.adaptadores;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import co.desofsi.ahorro.R;
import co.desofsi.ahorro.entidades.Ingresos;
import co.desofsi.ahorro.viewholders.ViewHolderGastosHomeDetaill;
import co.desofsi.ahorro.viewholders.ViewHolderGastosHomeItem;
import co.desofsi.ahorro.viewholders.ViewHolderIngresosHome;

public class RecyclerHomeListaGastosAdapter extends RecyclerView.Adapter<ViewHolderGastosHomeItem> {
    private ArrayList<Ingresos> listDatos;

    public RecyclerHomeListaGastosAdapter(ArrayList<Ingresos> listDatos) {
        this.listDatos = listDatos;
    }

    @NonNull
    @Override
    public ViewHolderGastosHomeItem onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_home_recycler_ingresos, parent, false);
        return new ViewHolderGastosHomeItem(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderGastosHomeItem holder, int position) {
        holder.setDescription(listDatos.get(position).getDescripcion());
        holder.setImage(listDatos.get(position).getImagen());
        holder.setValor(String.valueOf(listDatos.get(position).getValor()));
    }

    @Override
    public int getItemCount() {
        return listDatos.size();
    }
}
