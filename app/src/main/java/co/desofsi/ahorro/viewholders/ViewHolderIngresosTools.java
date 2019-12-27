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

public class ViewHolderIngresosTools extends RecyclerView.ViewHolder {

    private TextView text_cate_name;
    private Button btn_cate_eliminar;
    private ImageView img_lista;

    public ViewHolderIngresosTools(View itemView)   {
        super(itemView);
        text_cate_name = (TextView) itemView.findViewById(R.id.txt_nombre_item_lista_cat_ingresos);
        btn_cate_eliminar = (Button) itemView.findViewById(R.id.btn_delete_item_lista_cat_ingresos);
        img_lista = (ImageView) itemView.findViewById(R.id.img_item_lista_ingreso_ingresos);

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
        });*/
    }
    public void setImage(byte[] image){
        byte[] cat_img = image;
        Bitmap bitmap = BitmapFactory.decodeByteArray(cat_img, 0, cat_img.length);

        img_lista.setImageBitmap(bitmap);
    }

    public void setNameCate(String name){
        text_cate_name.setText(name);
    }
}
