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
import co.desofsi.ahorro.viewholders.ViewHolderGastosTools;

public class RecyclerToolsListaGastosAdapter extends RecyclerView.Adapter<ViewHolderGastosTools> {

    private ArrayList<CategoriaGasto> listDatos;
    private OnItemClickListener myListener;


    public interface OnItemClickListener{
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        myListener = listener;
    }

    public RecyclerToolsListaGastosAdapter(ArrayList<CategoriaGasto> listDatos, OnItemClickListener listener) {
        this.listDatos = listDatos;
        this.myListener = listener;

    }

    @NonNull
    @Override
    public ViewHolderGastosTools onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_lista_gasto_cat, parent, false);
        return new ViewHolderGastosTools(view,myListener);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderGastosTools holder, int position) {


      if(listDatos.get(position).getEstado()==1){
          holder.setNameCate(listDatos.get(position).getNombre()+" (visible)");
      }else {
          holder.setNameCate(listDatos.get(position).getNombre()+" (oculto)");
      }

        holder.setImage(listDatos.get(position).getImage());
        holder.setPresupuesto("pre: "+listDatos.get(position).getPresupuesto());
    }

    @Override
    public int getItemCount() {
        return listDatos.size();
    }
}
