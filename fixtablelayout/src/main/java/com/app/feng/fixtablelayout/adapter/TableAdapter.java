package com.app.feng.fixtablelayout.adapter;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.TextView;

import com.app.feng.fixtablelayout.widget.SingleLineLinearLayout;
import com.app.feng.fixtablelayout.widget.TVHelper;


/**
 * Created by feng on 2017/3/28.
 */

public class TableAdapter extends RecyclerView.Adapter<TableAdapter.TableViewHolder> {

    HorizontalScrollView titleView;
    RecyclerView leftViews;
    TextView left_top_view;
    SingleLineLinearLayout titleChild;

    ParametersHolder parametersHolder;

    IDataAdapter dataAdapter;

    private TableAdapter(
            HorizontalScrollView titleView,RecyclerView leftViews,TextView left_top_view,
            ParametersHolder parametersHolder,IDataAdapter dataAdapter) {
        super();
        this.titleView = titleView;
        this.leftViews = leftViews;
        this.left_top_view = left_top_view;
        this.parametersHolder = parametersHolder;
        this.dataAdapter = dataAdapter;

        initViews();
    }

    private void initViews() {
        left_top_view.setBackgroundColor(parametersHolder.title_color);
        TVHelper.setTextView(left_top_view,dataAdapter.getTitleAt(0),parametersHolder.item_gravity,
                             parametersHolder.item_width,parametersHolder.item_padding);

        leftViews.setAdapter(new LeftViewAdapter());
        titleChild = ((SingleLineLinearLayout) titleView.getChildAt(0));

        for (int i = 0; i < dataAdapter.getTitleCount(); i++) {
            TextView textView = TVHelper.generateTextView(titleChild.getContext(),
                                                          dataAdapter.getTitleAt(i),
                                                          parametersHolder.item_gravity,
                                                          parametersHolder.item_width,
                                                          parametersHolder.item_padding);

            titleChild.addView(textView,i);
        }
        titleChild.setBackgroundColor(parametersHolder.title_color);
    }

    @Override
    public TableViewHolder onCreateViewHolder(ViewGroup parent,int viewType) {
        SingleLineLinearLayout singleLineLinearLayout = new SingleLineLinearLayout(
                parent.getContext());

        for (int i = 0; i < dataAdapter.getTitleCount(); i++) {
            TextView textView = TVHelper.generateTextView(singleLineLinearLayout.getContext()," ",
                                                          parametersHolder.item_gravity,
                                                          parametersHolder.item_width,
                                                          parametersHolder.item_padding);
            singleLineLinearLayout.addView(textView,i);
        }

        return new TableViewHolder(singleLineLinearLayout);
    }

    @Override
    public void onBindViewHolder(TableViewHolder holder,int position) {
        SingleLineLinearLayout ll_content = (SingleLineLinearLayout) holder.itemView;
        ll_content.setBackgroundColor(Color.WHITE);

        for (int i = 0; i < dataAdapter.getTitleCount(); i++) {
            TextView textView = (TextView) ll_content.getChildAt(i);

            dataAdapter.convertData(position,textView,i);
        }

        //给奇数列设置背景
        if (position % 2 != 0) {
            ll_content.setBackgroundColor(parametersHolder.s_color);
        }

    }

    @Override
    public int getItemCount() {
        return dataAdapter.getItemCount();
    }

    class TableViewHolder extends RecyclerView.ViewHolder {
        public TableViewHolder(View itemView) {
            super(itemView);
        }
    }

    class LeftViewAdapter extends RecyclerView.Adapter<LeftViewAdapter.LeftViewHolder> {

        private void bindView(int position,View v) {
            TextView child = (TextView) v;
            TVHelper.setTextView(child," ",parametersHolder.item_gravity,
                                 parametersHolder.item_width,parametersHolder.item_padding);

            child.setBackgroundColor(Color.WHITE);
            if (position % 2 != 0) {
                child.setBackgroundColor(parametersHolder.s_color);
            }

            dataAdapter.convertLeftData(position,child);
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
            return dataAdapter.getItemCount();
        }

        class LeftViewHolder extends RecyclerView.ViewHolder {
            public LeftViewHolder(View itemView) {
                super(itemView);
            }
        }
    }

    public static class ParametersHolder {
        int s_color;
        int title_color;
        int item_width;
        int item_padding;
        int item_gravity;

        public ParametersHolder(
                int s_color,int title_color,int item_width,int item_padding,int item_gravity) {
            this.s_color = s_color;
            this.title_color = title_color;
            this.item_width = item_width;
            this.item_padding = item_padding;
            this.item_gravity = item_gravity;
        }
    }

    public static class Builder {
        HorizontalScrollView titleView;
        RecyclerView leftViews;
        TextView left_top_view;

        ParametersHolder parametersHolder;
        IDataAdapter dataAdapter;

        public Builder setTitleView(HorizontalScrollView titleView) {
            this.titleView = titleView;
            return this;
        }

        public Builder setLeftViews(RecyclerView leftViews) {
            this.leftViews = leftViews;
            return this;
        }

        public Builder setLeft_top_view(TextView left_top_view) {
            this.left_top_view = left_top_view;
            return this;
        }

        public Builder setParametersHolder(
                ParametersHolder parametersHolder) {
            this.parametersHolder = parametersHolder;
            return this;
        }

        public TableAdapter create() {
            return new TableAdapter(titleView,leftViews,left_top_view,parametersHolder,dataAdapter);
        }

        public Builder setDataAdapter(IDataAdapter dataAdapter) {
            this.dataAdapter = dataAdapter;
            return this;
        }
    }
}



