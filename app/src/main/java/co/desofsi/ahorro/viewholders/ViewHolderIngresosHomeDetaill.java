package co.desofsi.ahorro.viewholders;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;
import co.desofsi.ahorro.R;
import co.desofsi.ahorro.adaptadores.RecyclerHomeItemGastosAdapter;
import co.desofsi.ahorro.adaptadores.RecyclerHomeItemIngresosAdapter;

public class ViewHolderIngresosHomeDetaill extends RecyclerView.ViewHolder implements View.OnClickListener {

    private TextView text_cate_name;
    private TextView text_saldo;
    private ImageView img_lista;


    RecyclerHomeItemIngresosAdapter.OnItemHomeIngresosClickListener mylistener;


    public ViewHolderIngresosHomeDetaill(View itemView, final RecyclerHomeItemIngresosAdapter.OnItemHomeIngresosClickListener listener) {
        super(itemView);
        text_cate_name = (TextView) itemView.findViewById(R.id.txt_nombre_item_home_ingresos);
        text_saldo = (TextView) itemView.findViewById(R.id.txt_valor_item_home_ingresos);
        img_lista = (ImageView) itemView.findViewById(R.id.img_item_lista_ingresos_home);



        mylistener = listener;

        itemView.setOnClickListener(this);


    }

    public void setImage(byte[] image) {
        byte[] cat_img = image;
        Bitmap bitmap = BitmapFactory.decodeByteArray(cat_img, 0, cat_img.length);

        img_lista.setImageBitmap(bitmap);
    }

    public void setDescription(String name) {
        text_cate_name.setText(name);
    }

    public void setValor(String valor) {
        text_saldo.setText(valor);
    }



    @Override
    public void onClick(View v) {
        mylistener.onItemHomeIngresosClick(getAdapterPosition());
    }

}
