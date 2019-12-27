package co.desofsi.ahorro.viewholders;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;
import co.desofsi.ahorro.R;

public class ViewHolderGastosHomeItem extends RecyclerView.ViewHolder  {

    private TextView text_cate_name;
    private TextView text_saldo;
    private TextView text_fecha;
    private ImageView img_lista;


    public ViewHolderGastosHomeItem(View itemView) {
        super(itemView);
        text_cate_name = (TextView) itemView.findViewById(R.id.txt_nombre_detail_home_gasto);
        text_saldo = (TextView) itemView.findViewById(R.id.txt_valor_detail_home_gasto);
        text_fecha = (TextView) itemView.findViewById(R.id.txt_fecha_detail_home_gasto);
        img_lista = (ImageView) itemView.findViewById(R.id.img_detail_lista_gastos_home);


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
    public void setFecha(String fecha){
        text_fecha.setText(fecha);
    }
}
