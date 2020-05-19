package co.desofsi.ahorro.viewholders;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;
import co.desofsi.ahorro.R;

public class ViewHolderGastosPresupuesto extends RecyclerView.ViewHolder {

    private TextView text_cate_name;
    private TextView text_saldo;
    private ImageView img_lista;
    private ProgressBar progressBarGastos;
    private TextView text_porcentaje;

    public ViewHolderGastosPresupuesto(View itemView) {
        super(itemView);
        text_cate_name = (TextView) itemView.findViewById(R.id.txt_nombre_item_gallery_gastos);
        text_saldo = (TextView) itemView.findViewById(R.id.txt_valor_item_gallery_gastos);
        img_lista = (ImageView) itemView.findViewById(R.id.img_item_lista_gallery_gastos);
        progressBarGastos = (ProgressBar) itemView.findViewById(R.id.progres_gastos);
        text_porcentaje = (TextView) itemView.findViewById(R.id.txt_porcentaje_item_gallery_gastos);
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
    public void setText_porcentaje(String valor){
        text_porcentaje.setText(valor);
    }
    public void setProgresbar(String valor){ progressBarGastos.setProgress((int) Double.parseDouble(valor)); }


}
