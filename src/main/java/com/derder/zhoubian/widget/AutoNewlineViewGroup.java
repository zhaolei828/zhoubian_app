package com.derder.zhoubian.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created with IntelliJ IDEA.
 * User: zhaolei
 * Date: 14-8-18
 * Time: 下午3:07
 */
public class AutoNewlineViewGroup extends ViewGroup{
    private int mCellWidth;
    private int mCellHeight;

    public AutoNewlineViewGroup(Context context) {
        super(context);
    }

    public AutoNewlineViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AutoNewlineViewGroup(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int mWidth = MeasureSpec.getSize(widthMeasureSpec);
        int mCount = getChildCount();
        int mX = 0;
        int mY = 0;
        int mRow = 0;

        for (int index = 0; index < mCount; index++)
        {
            final View child = getChildAt(index);
            if(mCellWidth > 0 && mCellHeight > 0){
                child.measure(MeasureSpec.makeMeasureSpec(mCellWidth, MeasureSpec.AT_MOST), MeasureSpec.makeMeasureSpec(mCellHeight, MeasureSpec.AT_MOST));
            }else {
                child.measure(MeasureSpec.UNSPECIFIED, MeasureSpec.UNSPECIFIED);
            }

            // 此处增加onlayout中的换行判断，用于计算所需的高度
            int width = child.getMeasuredWidth();
            int height = child.getMeasuredHeight();
            mX += width;
            mY = mRow * height + height;
            if (mX > mWidth)
            {
                mX = width;
                mRow++;
                mY = mRow * height + height;
            }
        }
        // 设置容器所需的宽度和高度
        setMeasuredDimension(mWidth, mY);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        final int count = getChildCount();
        t = 0;
        int mX = l;
        int mY = t;
        int mRow = 0;
        for (int i = 0; i < count; i++)
        {
            final View child = this.getChildAt(i);
            int width = child.getMeasuredWidth();
            int height = child.getMeasuredHeight();
            mX += width;
            mY = mRow * height + height + t;
            if (mX > r)
            {
                mX = width + l;
                mRow++;
                mY = mRow * height + height + t;
            }
            child.layout(mX - width, mY - height, mX, mY);
        }
    }

    public void setmCellWidth(int mCellWidth) {
        this.mCellWidth = mCellWidth;
    }

    public void setmCellHeight(int mCellHeight) {
        this.mCellHeight = mCellHeight;
    }
}
