package co.desofsi.ahorro.viewholders;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;
import co.desofsi.ahorro.R;

public class ViewHolderGastosPresuSlideShow extends RecyclerView.ViewHolder {

    private TextView text_cate_name;
    private TextView text_saldo;
    private ImageView img_lista;

    public ViewHolderGastosPresuSlideShow(View itemView) {
        super(itemView);
        text_cate_name = (TextView) itemView.findViewById(R.id.txt_nombre_item_presupuesto_gasto);
        text_saldo = (TextView) itemView.findViewById(R.id.txt_valor_item_presupuesto_gasto);
        img_lista = (ImageView) itemView.findViewById(R.id.img_item_lista_presupuesto_gasto);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(),"CLICK",Toast.LENGTH_SHORT).show();

            }
        });
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

    public void setDescription(String name){
        text_cate_name.setText(name);
    }
    public void setValor(String valor){
        text_saldo.setText(valor);
    }

}
