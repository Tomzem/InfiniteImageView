package com.example.infiniteview.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.LinearLayout;

import com.example.infiniteview.bean.AppInfo;
import com.example.infiniteview.utils.AppUtils;

/**
 * Created by Administrator on 2018/10/31.
 * <p>
 * Description:
 */
public class InfiniteGridView extends GridView{

    private Context mContext;
    /**
     *  item 宽度
     */
    private int itemWidth = 0;

    /**
     * item 之间的间隔
     */
    private int itemPaddingH = 0;

    /**
     *  一行图标个数
     */
    private int size = 0;

    /**
     *  GridView的宽度
     */
    private int gridViewWidth = 0;

    /**
     *  行数
     */
    private int lineNumber = 4;

    public InfiniteGridView(Context context) {
        this(context, null);
    }

    public InfiniteGridView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public InfiniteGridView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        initView();
    }

    private void initView() {
        itemWidth = dip2px(100);
        itemPaddingH = dip2px(2);
        size = AppUtils.getAppInfoList(mContext).size() / lineNumber;
        gridViewWidth = size * (itemWidth + itemPaddingH);
    }

    @Override
    public void setLayoutParams(ViewGroup.LayoutParams params) {
        LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(
                gridViewWidth, LinearLayout.LayoutParams.MATCH_PARENT);
        super.setLayoutParams(param);
    }

    @Override
    public void setColumnWidth(int columnWidth) {
        super.setColumnWidth(itemWidth);
    }

    @Override
    public void setHorizontalSpacing(int horizontalSpacing) {
        super.setHorizontalSpacing(itemPaddingH);
    }

    @Override
    public void setStretchMode(int stretchMode) {
        super.setStretchMode(GridView.NO_STRETCH);
    }

    @Override
    public void setNumColumns(int numColumns) {
        super.setNumColumns(size);
    }

    /**
     *  根据手机的分辨率从 dp 的单位 转成为 px(像素)
     * @param dpValue
     * @return
     */
    public int dip2px(float dpValue) {
        final float scale = mContext.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}
