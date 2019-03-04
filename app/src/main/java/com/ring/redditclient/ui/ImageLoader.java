package com.ring.redditclient.ui;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.util.Log;
import android.widget.ImageView;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import java.io.File;
import java.io.FileOutputStream;

public class ImageLoader implements Target {

    private static final String TAG = ImageLoader.class.getSimpleName();
    private final String name;
    private ImageView imageView;

    ImageLoader(String name, ImageView imageView) {
        this.name = name;
        this.imageView = imageView;
    }

    @Override
    public void onPrepareLoad(Drawable arg0) {
    }

    @Override
    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom arg1) {
        File file = new File(Environment.getExternalStorageDirectory().getPath() + "/" + name);
        try {
            file.createNewFile();
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 75, fileOutputStream);
            fileOutputStream.close();
            imageView.setImageBitmap(bitmap);
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }
    }

    @Override
    public void onBitmapFailed(Exception e, Drawable errorDrawable) {}
}
