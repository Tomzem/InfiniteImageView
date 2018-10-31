package com.example.infiniteview.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.infiniteview.R;
import com.example.infiniteview.bean.AppInfo;

import java.util.List;

/**
 * Created by Administrator on 2018/10/31.
 * <p>
 * Description:
 */
public class AppInfosAdapter extends BaseAdapter {

    Context context;
    List<AppInfo> appInfos;

    public AppInfosAdapter(){}

    public AppInfosAdapter(Context context , List<AppInfo> infos ){
        this.context = context;
        this.appInfos = infos;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public List<AppInfo> getAppInfos() {
        return appInfos;
    }

    public void setAppInfos(List<AppInfo> appInfos) {
        this.appInfos = appInfos;
    }

    @Override
    public int getCount() {
        int count = 0;
        if(null != appInfos){
            return appInfos.size();
        };
        return count;
    }

    @Override
    public Object getItem(int index) {
        return appInfos.get(index);
    }

    @Override
    public long getItemId(int index) {
        return index;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup arg2) {
        AppInfo appInfo = appInfos.get(position);
        ViewHolder viewHolder = null;
        if(null == convertView){
            viewHolder = new ViewHolder();
            LayoutInflater mInflater = LayoutInflater.from(context);

            convertView = mInflater.inflate(R.layout.item_grid, null);
            viewHolder.appIconImg = convertView.findViewById(R.id.app_icon);
            viewHolder.appNameText = convertView.findViewById(R.id.app_info_name);
            viewHolder.itemLayout = convertView.findViewById(R.id.item_id);

            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder)convertView.getTag();
        }

        if(null != appInfo){
            viewHolder.appIconImg.setImageDrawable(appInfo.getDrawable());
            viewHolder.appNameText.setText(appInfo.getAppName());
        }

        if(appInfo.isSelect() == true){
            viewHolder.itemLayout.setBackgroundColor(Color.RED);
        } else {
            viewHolder.itemLayout.setBackgroundColor(Color.BLACK);
        }

        return convertView;
    }

    private class ViewHolder{
        ImageView appIconImg;
        TextView  appNameText;
        TextView  appPackageText;
        RelativeLayout itemLayout;
    }
}
