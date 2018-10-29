package com.example.tomze.infiniteimageview;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.infiniteview.widget.InfiniteImage;

public class MainActivity extends AppCompatActivity {

    InfiniteImage infiniteImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        infiniteImage = findViewById(R.id.img_infinite);

//        infiniteImage.setImageURL("https://img-ads.csdn.net/2018/201810151512545729.jpg");
        infiniteImage.setImageResources(R.drawable.ic_scenery);
        infiniteImage.initSensor();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        infiniteImage.unBindSensor();
    }
}
