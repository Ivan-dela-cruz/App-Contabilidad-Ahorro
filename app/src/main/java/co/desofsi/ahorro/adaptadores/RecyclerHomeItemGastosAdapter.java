package co.desofsi.ahorro.adaptadores;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import co.desofsi.ahorro.R;
import co.desofsi.ahorro.entidades.Gasto;
import co.desofsi.ahorro.viewholders.ViewHolderGastosHome;
import co.desofsi.ahorro.viewholders.ViewHolderGastosHomeDetaill;

public class RecyclerHomeItemGastosAdapter extends RecyclerView.Adapter<ViewHolderGastosHomeDetaill> {
    private ArrayList<Gasto> listDatos;
    private OnItemHomeClickListener myListener;

    public interface OnItemHomeClickListener {
        void onItemHomeClick(int position);
    }

    public void setOnItemClickListener(OnItemHomeClickListener listener) {
        myListener = listener;
    }

    public RecyclerHomeItemGastosAdapter(ArrayList<Gasto> listDatos, OnItemHomeClickListener listener) {
        this.listDatos = listDatos;
        this.myListener = listener;
    }

    @NonNull
    @Override
    public ViewHolderGastosHomeDetaill onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_home_recycler_gastos, parent, false);
        return new ViewHolderGastosHomeDetaill(view, myListener);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderGastosHomeDetaill holder, int position) {
        holder.setDescription(listDatos.get(position).getDescripcion());
        holder.setImage(listDatos.get(position).getImagen());
        holder.setValor(String.valueOf(listDatos.get(position).getValor()));
    }

    @Override
    public int getItemCount() {
        return listDatos.size();
    }
}
