package com.example.infiniteview;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.icu.util.LocaleData;
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

public class InfiniteActivity extends Activity implements View.OnTouchListener, SensorEventListener {
    private static final String TAG = "InfiniteActivity";

    private GridView appList;
    private LinearLayout mLinearLayout;
    private HorizontalScrollView mHorizontal;
    private int gridViewWidth;
    private SensorManager mSensorManager;
    private Sensor mOrientationSensor;
    private int currentX = 0, lastY = 0;
    private int moveX = -1;
    private int width = 0;
    private int scrollWidth = 0;
    private int LineAppSize = 0;// 一行APP的个数
    private int itemWidth = 0;
    private AppInfosAdapter appInfosAdapter;
    private int selectItemPosition = 0; //选中的位置
    private boolean isRun = false;


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

        WindowManager wm = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);
        width = wm.getDefaultDisplay().getWidth();
        mLinearLayout.setOnTouchListener(this);
    }

    public void initSensor() {
        mSensorManager = (SensorManager) this.getSystemService(Context.SENSOR_SERVICE);
        mOrientationSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION);
        mSensorManager.registerListener(this, mOrientationSensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    /**
     * 将GridView改成单行横向布局
     */
    private void changeGridView() {
        List<AppInfo> appInfos = AppUtils.getAppInfoList(this);
        // item宽度
        itemWidth = dip2px(85);
        // item之间的间隔
        int itemPaddingH = dip2px(1);
        LineAppSize = appInfos.size() / 3;
        // 计算GridView宽度
        gridViewWidth = LineAppSize * (itemWidth + itemPaddingH);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                gridViewWidth, LinearLayout.LayoutParams.MATCH_PARENT);
        appList.setLayoutParams(params);
        appList.setColumnWidth(itemWidth);
        appList.setHorizontalSpacing(itemPaddingH);
        appList.setStretchMode(GridView.NO_STRETCH);
        appList.setNumColumns(LineAppSize);
        appInfosAdapter = new AppInfosAdapter(this, appInfos);
        appList.setAdapter(appInfosAdapter);
        selectItemPosition = LineAppSize / 2 + LineAppSize;
        appInfosAdapter.setSelectItem(selectItemPosition);
        mHorizontal.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        if (isRun) {
                            isRun = false;
                            scrollWidth = gridViewWidth - width;
                            currentX = scrollWidth / 2;
                            mHorizontal.smoothScrollTo(currentX, 0);

                        }
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
        switch (motionEvent.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mHorizontal.setVisibility(View.VISIBLE);
                changeGridView();
                initSensor();
                break;
            case MotionEvent.ACTION_UP:
                mHorizontal.setVisibility(View.GONE);
                if (mSensorManager != null) {
                    mSensorManager.unregisterListener(this);
                    mSensorManager = null;
                    resetData();
                    isRun = true;
                }
                AppInfo appInfo = appInfosAdapter.getSelectItem();
                if (appInfo != null){
                    AppUtils.goToAnotherApp(this, appInfo);
                }
                break;
        }
        return true;
    }


    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        switch (sensorEvent.sensor.getType()) {
            case Sensor.TYPE_ORIENTATION:
                int xValue = (int) Math.abs(sensorEvent.values[0]);
                int zValue = (int) Math.abs(sensorEvent.values[2]);
                int yValue = (int) Math.abs(sensorEvent.values[1]);
                checkRotateDirection(xValue, yValue, zValue);
                break;
        }
    }

    private void checkRotateDirection(int X, int Y, int Z) {
        int i = gridViewWidth / 270;
        int j = gridViewWidth / 270;
        calculate(X * i, Y * j);
    }

    private int lastItemWidth = 0;

    private void calculate(int X, int Y) {
        // 会切换到最又边
        if (moveX == -1) {
            moveX = X;
            return;
        }
        // 移动距离
        int movedDistanceX = X - moveX;

        // 判断 与上次item 距离  切换item选中
        currentX = currentX + movedDistanceX;
        if (lastItemWidth == 0) {
            lastItemWidth = currentX;
        }
        // 如果 currentX < lastItemWidth 向右移动  item - 1
        int disparity = lastItemWidth - currentX;
        if (Math.abs(disparity) > itemWidth){
            if (disparity < 0){
                if (selectItemPosition%LineAppSize == 0){
                    return;
                }
                selectItemPosition += 1;
            }else{
                if (selectItemPosition%LineAppSize == 1){
                    return;
                }
                selectItemPosition -= 1;
            }
            appInfosAdapter.setSelectItem(selectItemPosition);
            lastItemWidth = currentX;
        }

        mHorizontal.smoothScrollTo(currentX, 0);
        moveX = X;
    }

    private void resetData() {
        moveX = -1;
        currentX = gridViewWidth - width;
        lastItemWidth = 0;
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

}
