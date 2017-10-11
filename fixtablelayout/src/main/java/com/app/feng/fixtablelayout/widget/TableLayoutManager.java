package com.app.feng.fixtablelayout.widget;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by feng on 2017/3/27.
 */

public class TableLayoutManager extends RecyclerView.LayoutManager {

    private int verticalOffset;
    private int horizontalOffset;

    private int firstVisPos;
    private int lastVisPos;

    private SparseArray<Rect> mItemAnchorMap = new SparseArray<>();

    private int oldChildCount = 1;

    public TableLayoutManager() {
        super();
        setAutoMeasureEnabled(true);
    }

    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        if (state.getItemCount() == 0) {
            detachAndScrapAttachedViews(recycler);
            return;
        }

        if (getChildCount() == 0 && state.isPreLayout()) {//state.isPreLayout()是支持动画的
            return;
        }

        //        Log.i("feng",
        //              " tag  ?  count " + getChildCount() + " change: " + state.didStructureChange() + " obj : " + this.toString() + " PreLayout :" + state.isPreLayout() + " Measure : " + state.isMeasuring());

        if (getChildCount() > 0 && state.didStructureChange()) {
            //Adapter DataSetChange
            // 运行 下列语句, 且过后的可能还有一个再次调用,拦截它
            oldChildCount = getChildCount();
            fill(recycler, state, 0);
            return;
        } else if (getChildCount() - oldChildCount > 0 && !state.didStructureChange()) {
            fill(recycler, state, 0);
            return;
        }

        detachAndScrapAttachedViews(recycler);
        verticalOffset = 0;
        firstVisPos = 0;
        lastVisPos = state.getItemCount();
        fill(recycler, state, 0);
    }

    private int fill(RecyclerView.Recycler recycler, RecyclerView.State state, int dy) {
        int offsetTop = 0;

        //回收越界子View
        if (getChildCount() > 0) {//滑动时进来的
            for (int i = getChildCount() - 1; i >= 0; i--) {
                View child = getChildAt(i);

                if (dy > 0) {//需要回收当前屏幕，上越界的View
                    if (getDecoratedBottom(child) < 0) {
                        removeAndRecycleView(child, recycler);
                        firstVisPos++;
                    }
                } else if (dy < 0) {//回收当前屏幕，下越界的View
                    if (getDecoratedTop(child) > getHeight() - getPaddingBottom()) {
                        removeAndRecycleView(child, recycler);
                        lastVisPos--;
                    }
                }
            }
        }

        if (dy >= 0) {
            int minPos = firstVisPos;
            lastVisPos = getItemCount() - 1;

            if (getChildCount() > 0) {
                View lastView = getChildAt(getChildCount() - 1);
                minPos = getPosition(lastView) + 1; //从最后一个View + 1开始吧
                offsetTop = getDecoratedBottom(lastView);
            }

            // 填充View
            for (int i = minPos; i <= lastVisPos; i++) {
                View child = recycler.getViewForPosition(i);
                addView(child);

                measureChild(child, 0, 0);

                if (offsetTop - dy > getHeight()) {
                    // 到了屏幕的末尾 退出布局
                    removeAndRecycleView(child, recycler);
                    lastVisPos = i - 1;
                } else {
                    int w = getDecoratedMeasuredWidth(child);
                    int h = getDecoratedMeasuredHeight(child);

                    Rect aRect = mItemAnchorMap.get(i);
                    if (aRect == null) {
                        aRect = new Rect();
                    }
                    aRect.set(0, offsetTop + verticalOffset, w, offsetTop + h + verticalOffset);
                    mItemAnchorMap.put(i, aRect);

                    // 布局到RV上
                    layoutDecorated(child, -horizontalOffset, offsetTop, -horizontalOffset + w,
                            offsetTop + h);
                    offsetTop += h;

                }
            }
            //添加完后，判断是否已经没有更多的ItemView，并且此时屏幕仍有空白，则需要修正dy
            View lastChild = getChildAt(getChildCount() - 1);
            if (getPosition(lastChild) == getItemCount() - 1) {
                int gap = getHeight() - getDecoratedBottom(lastChild);
                if (gap > 0) {
                    dy -= gap;
                }
            }
        } else {
            //上滑 , 通过mItemAnchorMap 拿到布局信息
            int maxPos = getItemCount() - 1;
            firstVisPos = 0;
            if (getChildCount() > 0) {
                View firstView = getChildAt(0);
                maxPos = getPosition(firstView) - 1;
            }

            for (int i = maxPos; i >= firstVisPos; i--) {
                Rect aRect = mItemAnchorMap.get(i);

                if (aRect != null) {
                    if (aRect.bottom - verticalOffset - dy < 0) {
                        firstVisPos = i + 1;
                        break;
                    } else {
                        View child = recycler.getViewForPosition(i);
                        addView(child, 0);
                        measureChild(child, 0, 0);

                        layoutDecorated(child, aRect.left - horizontalOffset,
                                aRect.top - verticalOffset, aRect.right - horizontalOffset,
                                aRect.bottom - verticalOffset);
                    }
                }
            }
        }

        //        Log.d("TAG",
        //              "count= [" + getChildCount() + "]" + ",[recycler.getScrapList().size():" + recycler.getScrapList()
        //                      .size() + ", dy:" + dy + ",  mVerticalOffset" + verticalOffset + ", ");

        return dy;
    }

    @Override
    public RecyclerView.LayoutParams generateDefaultLayoutParams() {
        return new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    @Override
    public boolean canScrollVertically() {
        return true;
    }

    @Override
    public boolean canScrollHorizontally() {
        return true;
    }

    @Override
    public int scrollHorizontallyBy(
            int dx, RecyclerView.Recycler recycler, RecyclerView.State state) {
        View firstView = getChildAt(0);

        int firstViewWidth = firstView.getMeasuredWidth();
        if (firstViewWidth <= getWidth()) {
            return 0;
        }

        if (horizontalOffset + dx > firstViewWidth - getWidth()) {
            dx = 0;
        } else if (horizontalOffset + dx <= 0) {
            dx = 0;
        }

        //        Log.i("feng"," hOff" + horizontalOffset);

        horizontalOffset += dx;
        offsetChildrenHorizontal(-dx);
        return dx;
    }

    @Override
    public int scrollVerticallyBy(
            int dy, RecyclerView.Recycler recycler, RecyclerView.State state) {
        if (dy == 0 || getChildCount() == 0) {
            return 0;
        }
        int realOffset = dy;

        View firstView = getChildAt(0);
        View lastView = getChildAt(getChildCount() - 1);

        //Optimize the case where the entire data set is too small to scroll
        int viewSpan = getDecoratedBottom(lastView) - getDecoratedTop(firstView);
        if (viewSpan < getVerticalSpace()) {
            return 0;
        }

        if (verticalOffset + realOffset < 0) {
            //下划到了顶部
            realOffset = -verticalOffset;
        } else if (realOffset > 0) {
            //利用最后一个子View比较修正
            if (getPosition(lastView) == getItemCount() - 1) {
                int gap = getHeight() - getPaddingBottom() - getDecoratedBottom(lastView);
                if (gap > 0) {
                    realOffset = -gap;
                } else if (gap == 0) {
                    realOffset = 0;
                } else {
                    realOffset = Math.min(realOffset, -gap);
                }
            }
        }

        realOffset = fill(recycler, state, realOffset);
        verticalOffset += realOffset;
        offsetChildrenVertical(-realOffset);

        return realOffset;
    }

    //获取控件的竖直高度
    private int getVerticalSpace() {
        return getHeight() - getPaddingBottom() - getPaddingTop();
    }

    //获取控件的水平宽度
    private int getHorizontalSpace() {
        return getWidth() - getPaddingLeft() - getPaddingRight();
    }

}
