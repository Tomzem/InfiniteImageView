package com.example.infiniteview.bean;

import android.graphics.drawable.Drawable;

/**
 * Created by Administrator on 2018/10/31.
 * <p>
 * Description:
 */
public class AppInfo {
    private String appName;
    private String packageName;
    private Drawable drawable;
    private boolean isSelect;

    public AppInfo() {
    }

    public AppInfo(String appName, String packageName, Drawable drawable) {
        this.appName = appName;
        this.packageName = packageName;
        this.drawable = drawable;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public Drawable getDrawable() {
        return drawable;
    }

    public void setDrawable(Drawable drawable) {
        this.drawable = drawable;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }
}
