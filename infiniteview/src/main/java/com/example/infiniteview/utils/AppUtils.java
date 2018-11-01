package com.example.infiniteview.utils;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.widget.Toast;

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
            String className = packageInfo.applicationInfo.className;
            Drawable drawable = packageInfo.applicationInfo.loadIcon(pm);
            AppInfo appInfo = new AppInfo(appName, packageName,className,drawable);
            appInfos.add(appInfo);
        }
        return appInfos;
    }

    /**
     *
     * 打开另一个app
     * @param packageName
     * @param className
     */
    public static void goToAnotherApp(Context context, AppInfo appInfo) {
        context.startActivity(context.getPackageManager().getLaunchIntentForPackage(appInfo.getPackageName()));
    }

}
