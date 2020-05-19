package co.desofsi.ahorro.adaptadores;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import co.desofsi.ahorro.R;
import co.desofsi.ahorro.entidades.Ingresos;
import co.desofsi.ahorro.viewholders.ViewHolderIngresosHome;

public class RecyclerHomeListaIngresosAdapter extends RecyclerView.Adapter<ViewHolderIngresosHome> {
    private ArrayList<Ingresos> listDatos;

    private OnFiltroIngresoClick  myListener;

    public interface OnFiltroIngresoClick{
        void OnFltroIngresoClick(int position);
    }

    public void setOnItemClickListener(OnFiltroIngresoClick listener) {
        myListener = listener;
    }

    public RecyclerHomeListaIngresosAdapter(ArrayList<Ingresos> listDatos,OnFiltroIngresoClick listener) {
        this.listDatos = listDatos;
        this.myListener = listener;
    }

    @NonNull
    @Override
    public ViewHolderIngresosHome onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.detail_home_recycler_ingresos, parent, false);
        return new ViewHolderIngresosHome(view,myListener);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderIngresosHome holder, int position) {
        holder.setDescription(listDatos.get(position).getDescripcion());
        holder.setImage(listDatos.get(position).getImagen());
        holder.setValor(String.valueOf(listDatos.get(position).getValor()));

        holder.setFecha(String.valueOf(listDatos.get(position).getFecha()));
    }

    @Override
    public int getItemCount() {
        return listDatos.size();
    }
}
