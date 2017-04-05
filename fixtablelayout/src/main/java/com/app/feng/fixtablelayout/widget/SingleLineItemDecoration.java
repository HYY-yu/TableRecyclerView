package com.app.feng.fixtablelayout.widget;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by feng on 2017/4/2.
 */

public class SingleLineItemDecoration extends RecyclerView.ItemDecoration {

    private int lineHeight = 4;
    private int lineColor = Color.BLACK;

    private Paint paint;

    public SingleLineItemDecoration(int divider_height,int divider_color) {
        lineHeight = divider_height;
        lineColor = divider_color;

        paint = new Paint();

        paint.setColor(lineColor);
        paint.setAlpha(240);
    }

    public int getLineHeight() {
        return lineHeight;
    }

    public SingleLineItemDecoration setLineHeight(int lineHeight) {
        this.lineHeight = lineHeight;
        return this;
    }

    public int getLineColor() {
        return lineColor;
    }

    public SingleLineItemDecoration setLineColor(int lineColor) {
        this.lineColor = lineColor;
        paint.setColor(lineColor);
        return this;
    }

    @Override
    public void onDraw(
            Canvas c,RecyclerView parent,RecyclerView.State state) {
        super.onDraw(c,parent,state);
        int left = parent.getPaddingLeft();
        int right = parent.getWidth() - parent.getPaddingRight();


        for (int i = 0; i < parent.getChildCount(); i++) {
            View child = parent.getChildAt(i);

            int bottom = child.getBottom();

            c.drawRect(left,bottom,right,bottom + lineHeight,paint);
        }
    }

    @Override
    public void getItemOffsets(
            Rect outRect,View view,RecyclerView parent,RecyclerView.State state) {
        outRect.bottom = lineHeight;
    }
}
