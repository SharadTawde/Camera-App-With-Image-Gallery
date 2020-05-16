package com.sharad.camera;

import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

import java.io.File;

public class ImageActivity extends AppCompatActivity {
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);
        File file = (File) getIntent().getExtras().get("img");
        imageView = findViewById(R.id.imageView);
        Glide.with(this).load(file).into(imageView);

    }

}
