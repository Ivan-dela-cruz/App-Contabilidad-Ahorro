package co.desofsi.ahorro.adaptadores;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import co.desofsi.ahorro.R;
import co.desofsi.ahorro.entidades.CategoriaGasto;
import co.desofsi.ahorro.entidades.Gasto;
import co.desofsi.ahorro.viewholders.ViewHolderGastosPresuSlideShow;

public class RecyclerPresuSlideShowListaGastosAdapter extends RecyclerView.Adapter<ViewHolderGastosPresuSlideShow> {
    private ArrayList<CategoriaGasto> listDatos;

    public RecyclerPresuSlideShowListaGastosAdapter(ArrayList<CategoriaGasto> listDatos) {
        this.listDatos = listDatos;
    }

    @NonNull
    @Override
    public ViewHolderGastosPresuSlideShow onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_recycler_presupuesto_slideshow, parent, false);
        return new ViewHolderGastosPresuSlideShow(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderGastosPresuSlideShow holder, int position) {
        holder.setDescription(listDatos.get(position).getNombre());
        holder.setImage(listDatos.get(position).getImage());
        holder.setValor(String.valueOf(listDatos.get(position).getPresupuesto()));
    }

    @Override
    public int getItemCount() {
        return listDatos.size();
    }
}
