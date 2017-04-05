package com.app.feng.fixtablelayout.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 * 其将所有子View，单行排列。
 * Created by feng on 2017/3/29.
 */

public class SingleLineLinearLayout extends ViewGroup {

    public SingleLineLinearLayout(Context context) {
        this(context,null);
    }

    public SingleLineLinearLayout(
            Context context,@Nullable AttributeSet attrs) {
        this(context,attrs,0);
    }

    public SingleLineLinearLayout(
            Context context,@Nullable AttributeSet attrs,int defStyleAttr) {
        super(context,attrs,defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec,int heightMeasureSpec) {
        // 父View 不能限制我们。模式是UNSPECIFIED  宽度所有子View宽总和, 高度取子View最大
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        int width = 0;
        int height = 0;
//        Log.i("feng",
//              "single 调用 onMeasure - widthMode :" + widthMode + " width size:  " + widthSize + " height size " + heightSize);

        for (int i = 0; i < getChildCount(); i++) {
            View childView = getChildAt(i);
            int widthChild = MeasureSpec.makeMeasureSpec(widthSize,MeasureSpec.UNSPECIFIED);
            int heightChild = MeasureSpec.makeMeasureSpec(heightSize,MeasureSpec.UNSPECIFIED);

            childView.measure(widthChild,heightChild);
            width += childView.getMeasuredWidth();
            height = Math.max(height,childView.getMeasuredHeight());
        }
        setMeasuredDimension(width,height);
    }

    @Override
    protected void onLayout(boolean changed,int l,int t,int r,int b) {
        int tempLeft = 0;
        int tempHeight = 0;
        //非常简单，每个子View都一行摆放。
//        Log.i("feng","single 调用 onLayout");

        for (int i = 0; i < getChildCount(); i++) {
            View childView = getChildAt(i);

            int tempRight = tempLeft + childView.getMeasuredWidth();
            int tempT = 0;
            int tempB = childView.getMeasuredHeight();
            if (tempHeight == 0) {
                tempHeight = tempB;
            } else if (tempB != tempHeight) {
                tempB = tempHeight;
            }
            childView.layout(tempLeft,tempT,tempRight,tempB);
            tempLeft += childView.getMeasuredWidth();
        }
    }
}
