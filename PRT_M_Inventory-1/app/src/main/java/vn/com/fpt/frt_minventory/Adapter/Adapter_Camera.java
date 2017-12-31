package vn.com.fpt.frt_minventory.Adapter;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.List;

import vn.com.fpt.frt_minventory.Model.Photo;
import vn.com.fpt.frt_minventory.R;

import static android.support.v4.content.ContextCompat.checkSelfPermission;
import static vn.com.fpt.frt_minventory.Services.Url.url;

/**
 * Created by minhtran on 12/20/17.
 */

public class Adapter_Camera extends BaseAdapter {
    private Context context;
    private List<Photo> list;


    public funcDelete getDelete() {
        return Delete;
    }

    public void setDelete(funcDelete delete) {
        Delete = delete;
    }

    private funcDelete Delete;

    public interface funcDelete {
        void click(int i);
    }

    public Adapter_Camera(Context context, List<Photo> list, funcDelete Delete) {
        this.context = context;
        this.list = list;
        this.Delete = Delete;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_camera, null);
        ImageView imView = (ImageView) v.findViewById(R.id.imView);
        ImageView imDelete = (ImageView) v.findViewById(R.id.imView1);
        final Photo photo = list.get(i);
        if (photo.getUri().contains("http")) {

            Picasso.with(context).load(photo.getUri()).resize(1000,800).into(imView);
            imDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Delete.click(i);
                }
            });
        } else {
            Log.e("bitmap",photo.getBitmap());
//            File myFile = new File(photo.getBitmap());
//            Uri myUri = Uri.fromFile(myFile);
//           // Picasso.with(context).setLoggingEnabled(true);
//            Picasso.with(context).load(myUri).resize(500,500).into(imView);
            imDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Delete.click(i);
                }
            });
            try {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                        && ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                            23);
                    File myFile = new File(photo.getBitmap());
                    Uri myUri = Uri.fromFile(myFile);
                    // Picasso.with(context).setLoggingEnabled(true);
                    Picasso.with(context).load(myUri).resize(500,500).into(imView);

                }

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        return v;
    }
    public void check(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                && ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    23);

            return;
        }
    }
    private int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) > reqHeight
                    && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }

    private Bitmap decodeSampledBitmap(String pathName,
                                       int reqWidth, int reqHeight) {

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(pathName, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(pathName, options);
    }


}