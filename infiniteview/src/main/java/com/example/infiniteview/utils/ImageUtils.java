package com.example.infiniteview.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by Administrator on 2018/10/29.
 * <p>
 * Description:
 */
public class ImageUtils {
    private static final String TAG = "ImageUtils";

    /**
     * 将Drawable 转换为BItmap
     *
     * @param drawable
     * @return
     */
    public static Bitmap drawableToBitmap(Drawable drawable) {
        // 取 drawable 的长宽
        int w = drawable.getIntrinsicWidth();
        int h = drawable.getIntrinsicHeight();

        // 取 drawable 的颜色格式
        Bitmap.Config config = drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
                : Bitmap.Config.RGB_565;
        // 建立对应 bitmap
        Bitmap bitmap = Bitmap.createBitmap(w, h, config);
        // 建立对应 bitmap 的画布
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, w, h);
        // 把 drawable 内容画到画布中
        drawable.draw(canvas);
        return bitmap;
    }
    public  static  void  returnBitMap(final String imageUri, final OnDownLoadListener onDownLoadListener) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Bitmap bitmap = null;
                try {
                    URL myFileUrl = new URL(imageUri);
                    HttpURLConnection conn = (HttpURLConnection) myFileUrl
                            .openConnection();
                    conn.setDoInput(true);
                    conn.connect();
                    InputStream is = conn.getInputStream();
                    bitmap = BitmapFactory.decodeStream(is);
                    is.close();
                    onDownLoadListener.onSuccess(bitmap);
                } catch (OutOfMemoryError e) {
                    e.printStackTrace();
                    bitmap = null;
                    onDownLoadListener.onFail();
                } catch (IOException e) {
                    e.printStackTrace();
                    bitmap = null;
                    onDownLoadListener.onFail();
                }
            }
        }).start();
    }

    public interface OnDownLoadListener{
        void onSuccess(Bitmap bitmap);
        void onFail();
    }

}
