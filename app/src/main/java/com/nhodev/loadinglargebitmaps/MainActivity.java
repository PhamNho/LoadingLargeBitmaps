package com.nhodev.loadinglargebitmaps;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.io.ByteArrayOutputStream;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    Button btnLoad, btnLoad1;
    ImageView img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initAction();
    }

    private void initAction() {
        int width = this.getResources().getDisplayMetrics().widthPixels;
        Log.d("width", "" + width);
        img.getLayoutParams().height = width / 3 * 2;


        btnLoad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.img);
                img.setImageBitmap(bitmap);

                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inJustDecodeBounds = true;
                options.inSampleSize = 4;
                BitmapFactory.decodeResource(getResources(), R.drawable.img, options);

                options.inSampleSize = calculateInSampleSize(options, 1280, 720);
                options.inJustDecodeBounds = false;
                Bitmap smallBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.img, options);

                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, bos);
                byte[] bitmapdata = bos.toByteArray();
                bitmap.compress(Bitmap.CompressFormat.PNG, 50, bos);

            }
        });

        btnLoad1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Glide.with(getApplicationContext()).load("https://images6.alphacoders.com/805/805648.png").into(img);
            }
        });
    }

    private void initView() {
        btnLoad = findViewById(R.id.btnLoad);
        btnLoad1 = findViewById(R.id.btnLoad1);
        img = findViewById(R.id.img);
    }

    public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            while ((halfHeight / inSampleSize) >= reqHeight
                    && (halfWidth / inSampleSize) >= reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }

    public static Bitmap decodeSampledBitmapFromResource(Resources res, int resId, int reqWidth, int reqHeight) {

        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options);

        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(res, resId, options);
    }
}