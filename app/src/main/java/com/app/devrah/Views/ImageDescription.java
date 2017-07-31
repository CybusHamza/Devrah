package com.app.devrah.Views;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.app.devrah.R;

public class ImageDescription extends AppCompatActivity {


    Bitmap bitmap;
    ImageView img;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_description);
        Intent intent = getIntent();

        bitmap = (Bitmap)intent.getParcelableExtra("BitmapImage");
        img = (ImageView)findViewById(R.id.imageFromRV);
        img.setImageBitmap(bitmap);

    }
}
