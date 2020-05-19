package co.desofsi.ahorro.viewholders;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.nfc.Tag;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import co.desofsi.ahorro.*;
import androidx.recyclerview.widget.RecyclerView;
import co.desofsi.ahorro.adaptadores.RecyclerToolsListaGastosAdapter;

public class ViewHolderGastosTools extends RecyclerView.ViewHolder implements View.OnClickListener {

    private TextView text_cate_name;
    private TextView txt_cat_gasto_presu;
    private ImageView img_lista;

    RecyclerToolsListaGastosAdapter.OnItemClickListener mylistener;



    public ViewHolderGastosTools(View itemView, final RecyclerToolsListaGastosAdapter.OnItemClickListener listener) {
        super(itemView);
        text_cate_name = (TextView) itemView.findViewById(R.id.txt_nombre_item_lista_cat);
        txt_cat_gasto_presu = (TextView) itemView.findViewById(R.id.txt_cat_gasto_presu);
        img_lista = (ImageView) itemView.findViewById(R.id.img_item_lista_gastos_tools);
        mylistener=listener;

        itemView.setOnClickListener(this);

       /* itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(listener!=null){
                    int position = getAdapterPosition();
                    if(position!=RecyclerView.NO_POSITION){
                        listener.onItemClick(position);
                    }
                }


            }
        });*/
        /*btn_cate_eliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("LogItemView","BotonCliok");
            }
        });
        */
    }
    public void setImage(byte[] image){
        byte[] cat_img = image;
        Bitmap bitmap = BitmapFactory.decodeByteArray(cat_img, 0, cat_img.length);

        img_lista.setImageBitmap(bitmap);
    }

    public void setNameCate(String name){
        text_cate_name.setText(name);
    }
    public void setPresupuesto(String valor){txt_cat_gasto_presu.setText(valor);}

    @Override
    public void onClick(View v) {
        mylistener.onItemClick(getAdapterPosition());
    }
}
