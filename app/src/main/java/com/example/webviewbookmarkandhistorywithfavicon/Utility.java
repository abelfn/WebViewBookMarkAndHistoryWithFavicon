package com.example.webviewbookmarkandhistorywithfavicon;

import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import java.io.ByteArrayOutputStream;

public class Utility {
    // convert from bitmap to byte array
    public static byte[] getBytes(Bitmap bitmap) {
        if(bitmap == null){
            return null;
        }else {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(CompressFormat.PNG, 0, stream);
            return stream.toByteArray();
        }

    }

    // convert from byte array to bitmap
    public static Bitmap getFavicon(byte[] image) {
        if(image == null){
            return null;
        }else {
            return BitmapFactory.decodeByteArray(image, 0, image.length);
        }

    }
}
