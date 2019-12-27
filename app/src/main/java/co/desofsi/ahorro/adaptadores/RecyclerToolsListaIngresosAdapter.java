package co.desofsi.ahorro.adaptadores;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import co.desofsi.ahorro.R;
import co.desofsi.ahorro.entidades.CategoriaIngreso;
import co.desofsi.ahorro.viewholders.ViewHolderIngresosTools;

public class RecyclerToolsListaIngresosAdapter extends RecyclerView.Adapter<ViewHolderIngresosTools> {

    private ArrayList<CategoriaIngreso> listDatos;

    public RecyclerToolsListaIngresosAdapter(ArrayList<CategoriaIngreso> listDatos) {
        this.listDatos = listDatos;
    }

    @NonNull
    @Override
    public ViewHolderIngresosTools onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_lista_ingresos_cat, parent, false);
        return new ViewHolderIngresosTools(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderIngresosTools holder, int position) {
        holder.setNameCate(listDatos.get(position).getNombre());
        holder.setImage(listDatos.get(position).getImage());
    }

    @Override
    public int getItemCount() {
        return listDatos.size();
    }
}
