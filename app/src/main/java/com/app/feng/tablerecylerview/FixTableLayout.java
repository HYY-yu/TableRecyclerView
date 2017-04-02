package com.app.feng.tablerecylerview;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.TextView;

import com.app.feng.tablerecylerview.adapter.TableAdapter;
import com.app.feng.tablerecylerview.widget.SingleLineItemDecoration;
import com.app.feng.tablerecylerview.widget.TableLayoutManager;

/**
 * Created by feng on 2017/4/2.
 */
public class FixTableLayout extends FrameLayout {

    RecyclerView recyclerView;
    HorizontalScrollView titleView;
    RecyclerView leftViews;
    TextView left_top_view;

    int divider_height;
    int divider_color;

    public FixTableLayout(Context context) {
        this(context,null);
    }

    public FixTableLayout(
            Context context,@Nullable AttributeSet attrs) {
        this(context,attrs,0);
    }

    public FixTableLayout(Context context,@Nullable AttributeSet attrs,int defStyleAttr) {
        super(context,attrs,defStyleAttr);

        View view = inflate(context,R.layout.table_view,null);
        init(view);
        addView(view);
    }

    private void init(View view) {
        divider_color = getResources().getColor(R.color.table_divider_color);
        divider_height = getResources().getDimensionPixelOffset(R.dimen.table_divider_height);

        recyclerView = (RecyclerView) view.findViewById(R.id.recylerView);
        titleView = (HorizontalScrollView) view.findViewById(R.id.titleView);
        leftViews = (RecyclerView) view.findViewById(R.id.leftViews);
        left_top_view = (TextView) view.findViewById(R.id.left_top_view);

        leftViews.setLayoutManager(new LinearLayoutManager(getContext()));
        leftViews.addItemDecoration(new SingleLineItemDecoration(divider_height,divider_color));
        leftViews.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v,MotionEvent event) {
                //将事件发送到RV
                recyclerView.onTouchEvent(event);
                return true;
            }
        });


        recyclerView.setAdapter(new TableAdapter(titleView,leftViews,left_top_view));

        recyclerView.setLayoutManager(new TableLayoutManager(getContext()));
        recyclerView.addItemDecoration(new SingleLineItemDecoration(divider_height,divider_color));

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView,int newState) {
                super.onScrollStateChanged(recyclerView,newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView,int dx,int dy) {
                super.onScrolled(recyclerView,dx,dy);
                titleView.scrollBy(dx,0);
                leftViews.scrollBy(0,dy);
            }
        });
    }


}
