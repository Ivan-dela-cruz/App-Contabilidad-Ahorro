package co.desofsi.ahorro.adaptadores;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import co.desofsi.ahorro.R;
import co.desofsi.ahorro.entidades.Gasto;
import co.desofsi.ahorro.entidades.Ingresos;
import co.desofsi.ahorro.viewholders.ViewHolderGastosHomeDetaill;
import co.desofsi.ahorro.viewholders.ViewHolderIngresosHomeDetaill;

public class RecyclerHomeItemIngresosAdapter extends RecyclerView.Adapter<ViewHolderIngresosHomeDetaill> {
    private ArrayList<Ingresos> listDatos;
    private OnItemHomeIngresosClickListener myListener;

    public interface OnItemHomeIngresosClickListener {
        void onItemHomeIngresosClick(int position);
    }

    public void setOnItemClickListener(OnItemHomeIngresosClickListener listener) {
        myListener = listener;
    }

    public RecyclerHomeItemIngresosAdapter(ArrayList<Ingresos> listDatos, OnItemHomeIngresosClickListener listener) {
        this.listDatos = listDatos;
        this.myListener = listener;
    }

    @NonNull
    @Override
    public ViewHolderIngresosHomeDetaill onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_home_recycler_ingresos, parent, false);
        return new ViewHolderIngresosHomeDetaill(view, myListener);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderIngresosHomeDetaill holder, int position) {
        holder.setDescription(listDatos.get(position).getDescripcion());
        holder.setImage(listDatos.get(position).getImagen());
        holder.setValor(String.valueOf(listDatos.get(position).getValor()));


    }

    @Override
    public int getItemCount() {
        return listDatos.size();
    }
}
