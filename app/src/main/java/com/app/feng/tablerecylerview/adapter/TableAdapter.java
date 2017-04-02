package com.app.feng.tablerecylerview.adapter;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.TextView;

import com.app.feng.tablerecylerview.R;
import com.app.feng.tablerecylerview.widget.SingleLineLinearLayout;


/**
 * Created by feng on 2017/3/28.
 */

public class TableAdapter extends RecyclerView.Adapter<TableAdapter.TableViewHolder> {

    HorizontalScrollView titleView;
    RecyclerView leftViews;
    TextView left_top_view;

    int data_count = 40;

    int bg_color = Color.parseColor("#C5E3EE");

    public TableAdapter(
            HorizontalScrollView titleView,RecyclerView leftViews,TextView left_top_view) {
        super();
        this.titleView = titleView;
        this.leftViews = leftViews;
        this.left_top_view = left_top_view;

        left_top_view.setText("_ titleeeef 0 _ ");
        left_top_view.setMinWidth(300);
        left_top_view.setMinHeight(60);
        left_top_view.setGravity(Gravity.CENTER);
        left_top_view.setBackgroundColor(Color.GRAY);

        leftViews.setAdapter(new LeftViewAdapter());

        SingleLineLinearLayout titleChild = ((SingleLineLinearLayout) titleView.getChildAt(0));

        for (int i = 0; i < 10; i++) {
            TextView textView = new TextView(titleChild.getContext());
            textView.setMinWidth(300);
            textView.setMinHeight(60);
            textView.setText(" _ titleeeef " + i + " _ ");
            textView.setGravity(Gravity.CENTER);
            titleChild.addView(textView,i);
        }
        titleChild.setBackgroundColor(Color.GRAY);
    }

    @Override
    public TableViewHolder onCreateViewHolder(ViewGroup parent,int viewType) {
        //        Log.d("feng","adapter create a Layout");
        SingleLineLinearLayout singleLineLinearLayout = (SingleLineLinearLayout) LayoutInflater.from(
                parent.getContext())
                .inflate(R.layout.table_a_row,parent,false);

        return new TableViewHolder(singleLineLinearLayout);
    }

    @Override
    public void onBindViewHolder(TableViewHolder holder,int position) {
        //        Log.d("feng","bind View to Layout");
        SingleLineLinearLayout ll_content = (SingleLineLinearLayout) holder.itemView;
        ll_content.removeAllViews();
        ll_content.setBackgroundColor(Color.WHITE);

        for (int i = 0; i < 10; i++) {
            TextView textView = new TextView(ll_content.getContext());
            textView.setText(" _zhdef " + i + " _ ");
            textView.setMinWidth(300);
            textView.setGravity(Gravity.CENTER);
            ll_content.addView(textView,i);

        }

        //给奇数列设置背景
        if (position % 2 != 0) {
            ll_content.setBackgroundColor(bg_color);
        }

    }

    @Override
    public int getItemCount() {
        return data_count;
    }

    class TableViewHolder extends RecyclerView.ViewHolder {

        public TableViewHolder(View itemView) {
            super(itemView);
        }
    }

    class LeftViewAdapter extends RecyclerView.Adapter<LeftViewAdapter.LeftViewHolder> {
        //Data

        private void bindView(int position,View v) {
            TextView child = (TextView) v;
            child.setMinWidth(300);
            child.setGravity(Gravity.CENTER);
            child.setBackgroundColor(Color.WHITE);

            child.setText(" _zhdef 0 _ ");
            if (position  % 2 != 0) {
                child.setBackgroundColor(bg_color);
            }
        }

        @Override
        public LeftViewHolder onCreateViewHolder(
                ViewGroup parent,int viewType) {
            return new LeftViewHolder(new TextView(parent.getContext()));
        }

        @Override
        public void onBindViewHolder(
                LeftViewHolder holder,int position) {
            bindView(position,holder.itemView);
        }

        @Override
        public int getItemCount() {
            return data_count;
        }

        class LeftViewHolder extends RecyclerView.ViewHolder {
            public LeftViewHolder(View itemView) {
                super(itemView);
            }
        }
    }
}

