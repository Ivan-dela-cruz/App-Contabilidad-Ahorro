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
import co.desofsi.ahorro.viewholders.ViewHolderGastosHomeItem;
import co.desofsi.ahorro.viewholders.ViewHolderIngresosHome;

public class RecyclerHomeDetailGastoAdapter extends RecyclerView.Adapter<ViewHolderGastosHomeItem> {
    private ArrayList<Gasto> listDatos;


    private OnFiltroHomeClickListener myListener;

    public interface OnFiltroHomeClickListener {
        void onFilroHomeClick(int position);
    }

    public void setOnFiltroClickListener(OnFiltroHomeClickListener listener) {
        myListener = listener;

    }




    public RecyclerHomeDetailGastoAdapter(ArrayList<Gasto> listDatos,OnFiltroHomeClickListener listener) {
        this.listDatos = listDatos;
        this.myListener = listener;
    }

    @NonNull
    @Override
    public ViewHolderGastosHomeItem onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.detail_home_recycler_gastos, parent, false);
        return new ViewHolderGastosHomeItem(view,myListener);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderGastosHomeItem holder, int position) {
        holder.setDescription(listDatos.get(position).getDescripcion());
        holder.setImage(listDatos.get(position).getImagen());
        holder.setValor(String.valueOf(listDatos.get(position).getValor()));
        holder.setFecha(listDatos.get(position).getFecha());
    }

    @Override
    public int getItemCount() {
        return listDatos.size();
    }
}
