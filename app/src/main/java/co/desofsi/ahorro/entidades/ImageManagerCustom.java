package co.desofsi.ahorro.entidades;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;

public class ImageManagerCustom {

    public ImageManagerCustom() {
    }

    public Bitmap decodificar(byte[] imagen) {
        Bitmap bitmap = BitmapFactory.decodeByteArray(imagen, 0, imagen.length);
        return bitmap;
    }

    public  byte[] imageViewToByte(ImageView imageView) {
        Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }
}
