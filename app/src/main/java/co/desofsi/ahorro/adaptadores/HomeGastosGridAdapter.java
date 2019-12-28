package co.desofsi.ahorro.adaptadores;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import co.desofsi.ahorro.entidades.CategoriaGasto;
import co.desofsi.ahorro.*;
public class HomeGastosGridAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<CategoriaGasto> arrayList;

    public HomeGastosGridAdapter(Context context, ArrayList<CategoriaGasto> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.item_grid_gastos, null);
        }
        CategoriaGasto catIngreso = arrayList.get(position);
        TextView nombre = (TextView) convertView.findViewById(R.id.txt_cat_nombre);
        ImageView image = (ImageView) convertView.findViewById(R.id.img_cat_gasto);
        ImageView indicador = (ImageView) convertView.findViewById(R.id.indicador_presupuesto);

        byte[] cat_img = catIngreso.getImage();
        Bitmap bitmap = BitmapFactory.decodeByteArray(cat_img, 0, cat_img.length);

        image.setImageBitmap(bitmap);
        nombre.setText(catIngreso.getNombre());


        double ciencuenta = catIngreso.getPresupuesto()*0.5;
        double setenta = catIngreso.getPresupuesto()*0.75;


        if(catIngreso.getGasto_mensual()<=ciencuenta){
            indicador.setImageResource(R.drawable.verde);
        }
        if (catIngreso.getGasto_mensual()>ciencuenta && catIngreso.getGasto_mensual()<=setenta){
            indicador.setImageResource(R.drawable.amarillo);
        }
        if (catIngreso.getGasto_mensual()>setenta){
            indicador.setImageResource(R.drawable.rojo);
        }

        return convertView;
    }
}
