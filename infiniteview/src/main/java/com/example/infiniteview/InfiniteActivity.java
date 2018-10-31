package com.example.infiniteview;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.widget.GridView;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

import com.example.infiniteview.adapter.AppInfosAdapter;
import com.example.infiniteview.bean.AppInfo;
import com.example.infiniteview.utils.AppUtils;

import java.util.List;

public class InfiniteActivity extends Activity implements View.OnTouchListener,SensorEventListener {
    private static final String TAG = "InfiniteActivity";

    private GridView appList;
    private LinearLayout mLinearLayout;
    private HorizontalScrollView mHorizontal;
    private int gridViewWidth;
    private SensorManager mSensorManager;
    private Sensor mOrientationSensor;
    private Sensor mProximitySensor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_infinite);

        appList = findViewById(R.id.app_list);
        mHorizontal = findViewById(R.id.horizontal);
        mLinearLayout = findViewById(R.id.full_screen);
        mHorizontal.setSmoothScrollingEnabled(true);


        mHorizontal.setVisibility(View.VISIBLE);
        changeGridView();

        mLinearLayout.setOnTouchListener(this);
    }

    public void initSensor() {
        mSensorManager = (SensorManager) this.getSystemService(Context.SENSOR_SERVICE);
        mOrientationSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION);
        mProximitySensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mSensorManager.registerListener(this, mOrientationSensor, SensorManager.SENSOR_DELAY_NORMAL);
        mSensorManager.registerListener(this, mProximitySensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    /**
     * 将GridView改成单行横向布局
     */
    private void changeGridView() {
        List<AppInfo> appInfos = AppUtils.getAppInfoList(this);
        // item宽度
        int itemWidth = dip2px(85);
        // item之间的间隔
        int itemPaddingH = dip2px(1);
        int size = appInfos.size()/4;
        // 计算GridView宽度
        gridViewWidth = size * (itemWidth + itemPaddingH);
        appInfos.get(size/2).setSelect(true);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                gridViewWidth, LinearLayout.LayoutParams.MATCH_PARENT);
        appList.setLayoutParams(params);
        appList.setColumnWidth(itemWidth);
        appList.setHorizontalSpacing(itemPaddingH);
        appList.setStretchMode(GridView.NO_STRETCH);
        appList.setNumColumns(size);
        AppInfosAdapter appInfosAdapter = new AppInfosAdapter(this, appInfos);
        appList.setAdapter(appInfosAdapter);
        mHorizontal.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        mHorizontal.smoothScrollTo(gridViewWidth/2 -  dip2px(100), 0);
                    }
                }
        );
    }

    public int dip2px(float dpValue) {
        final float scale = this.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        switch (motionEvent.getAction()){
            case MotionEvent.ACTION_DOWN:
                mHorizontal.setVisibility(View.VISIBLE);
                changeGridView();
                break;
            case MotionEvent.ACTION_UP:
                mHorizontal.setVisibility(View.GONE);
                break;
        }
        return true;
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}
