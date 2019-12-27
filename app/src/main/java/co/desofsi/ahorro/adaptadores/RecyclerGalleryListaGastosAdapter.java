package co.desofsi.ahorro.adaptadores;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.text.DecimalFormat;
import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import co.desofsi.ahorro.R;
import co.desofsi.ahorro.entidades.Gasto;
import co.desofsi.ahorro.viewholders.ViewHolderGastosGallery;

public class RecyclerGalleryListaGastosAdapter extends RecyclerView.Adapter<ViewHolderGastosGallery> {

    private ArrayList<Gasto> listDatos;
    private double total;

    public RecyclerGalleryListaGastosAdapter(ArrayList<Gasto> listDatos, double total) {
        this.listDatos = listDatos;
        this.total = total;
    }

    @NonNull
    @Override
    public ViewHolderGastosGallery onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_recycler_galllery_gastos, parent, false);
        return new ViewHolderGastosGallery(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderGastosGallery holder, int position) {

        DecimalFormat formato2 = new DecimalFormat("#.#");
        double porcentaje = (listDatos.get(position).getValor() * 100) / total;

        holder.setDescription(listDatos.get(position).getDescripcion());
        holder.setImage(listDatos.get(position).getImagen());
        holder.setValor(String.valueOf(listDatos.get(position).getValor()));
        holder.setProgresbar(String.valueOf(porcentaje));
        holder.setText_porcentaje(formato2.format(porcentaje)+" %");
    }

    @Override
    public int getItemCount() {
        return listDatos.size();
    }
}
