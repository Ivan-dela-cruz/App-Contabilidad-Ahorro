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

import co.desofsi.ahorro.R;
import co.desofsi.ahorro.entidades.CategoriaAhorro;
import co.desofsi.ahorro.entidades.CategoriaIngreso;

public class AhorrosGridAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<CategoriaAhorro> arrayList;


    public AhorrosGridAdapter(Context context, ArrayList<CategoriaAhorro> arrayList) {
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
        return arrayList.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.item_grid_ingresos, null);
        }
        CategoriaAhorro catIngreso = arrayList.get(position);
        TextView nombre = (TextView) convertView.findViewById(R.id.txt_nombre);
        ImageView image = (ImageView) convertView.findViewById(R.id.img_cat_ingreso);

        byte[] cat_img = catIngreso.getImage();
        Bitmap bitmap = BitmapFactory.decodeByteArray(cat_img, 0, cat_img.length);

        image.setImageBitmap(bitmap);
        nombre.setText(catIngreso.getNombre());

        return convertView;
    }
}
