package com.example.infiniteview.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

import com.example.infiniteview.R;

/**
 * Created by Administrator on 2018/10/31.
 * <p>
 * Description:
 */
public class CenterShowHorizontalScrollView extends HorizontalScrollView {

    private LinearLayout mShowLinear;
    private Context context;

    public CenterShowHorizontalScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        mShowLinear = new LinearLayout(context);
        mShowLinear.setOrientation(LinearLayout.HORIZONTAL);
        HorizontalScrollView.LayoutParams params = new HorizontalScrollView.LayoutParams(
                LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
        mShowLinear.setGravity(Gravity.CENTER_VERTICAL);
        this.addView(mShowLinear, params);
    }

    public void onClicked(int x, int y) {
        smoothScrollTo(x, y);
    }
}
