package co.desofsi.ahorro.viewholders;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;
import co.desofsi.ahorro.R;
import co.desofsi.ahorro.adaptadores.RecyclerToolsListaIngresosAdapter;

public class ViewHolderIngresosTools extends RecyclerView.ViewHolder implements View.OnClickListener{

    private TextView text_cate_name;
    private ImageView img_lista;

    RecyclerToolsListaIngresosAdapter.OnItemClickListenerIngreso mylistener;

    public ViewHolderIngresosTools(View itemView, final RecyclerToolsListaIngresosAdapter.OnItemClickListenerIngreso listener)   {
        super(itemView);
        text_cate_name = (TextView) itemView.findViewById(R.id.txt_nombre_item_lista_cat_ingresos);

        img_lista = (ImageView) itemView.findViewById(R.id.img_item_lista_ingreso_ingresos);
        mylistener=listener;

        itemView.setOnClickListener(this);

    }
    public void setImage(byte[] image){
        byte[] cat_img = image;
        Bitmap bitmap = BitmapFactory.decodeByteArray(cat_img, 0, cat_img.length);

        img_lista.setImageBitmap(bitmap);
    }

    public void setNameCate(String name){
        text_cate_name.setText(name);
    }

    @Override
    public void onClick(View v) {
        mylistener.onItemClick(getAdapterPosition());
    }
}
