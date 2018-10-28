package com.example.tomze.infiniteimageview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.infiniteview.InfiniteImage;

public class MainActivity extends AppCompatActivity {

    InfiniteImage infiniteImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        infiniteImage = findViewById(R.id.img_infinite);
        infiniteImage.initSensor();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        infiniteImage.unBindSensor();
    }
}
