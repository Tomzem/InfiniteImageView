package com.example.infiniteview.utils;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;

import com.example.infiniteview.bean.AppInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/10/31.
 * <p>
 * Description:
 */
public class AppUtils {

    private  static List<AppInfo> appInfos = new ArrayList<>();

    public static List<AppInfo> getAppInfoList(Context context){
        PackageManager pm = context.getPackageManager();
        List<PackageInfo>  packageInfoList = pm.getInstalledPackages(PackageManager.GET_UNINSTALLED_PACKAGES);
        appInfos = new ArrayList<AppInfo>();
        for(PackageInfo packageInfo : packageInfoList){
            if ((packageInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) != 0) {
                continue;
            }
            String appName = packageInfo.applicationInfo.loadLabel(pm).toString();
            String packageName = packageInfo.packageName;
            Drawable drawable = packageInfo.applicationInfo.loadIcon(pm);
            AppInfo appInfo = new AppInfo(appName, packageName,drawable);
            appInfos.add(appInfo);
        }
        return appInfos;
    }
}
