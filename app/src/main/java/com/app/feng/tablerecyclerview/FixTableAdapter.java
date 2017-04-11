package com.app.feng.tablerecyclerview;

import android.widget.TextView;

import com.app.feng.fixtablelayout.inter.IDataAdapter;
import com.app.feng.tablerecyclerview.bean.DataBean;

import java.util.List;

/**
 * Created by feng on 2017/4/4.
 */

public class FixTableAdapter implements IDataAdapter {

    public String[] titles;

    public List<DataBean> data;

    public FixTableAdapter(String[] titles,List<DataBean> data) {
        this.titles = titles;
        this.data = data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    @Override
    public String getTitleAt(int pos) {
        return titles[pos];
    }

    @Override
    public int getTitleCount() {
        return titles.length;
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public void convertData(int position,List<TextView> bindViews) {
        DataBean dataBean = data.get(position);

        bindViews.get(0)
                .setText(dataBean.id);
        bindViews.get(1)
                .setText(dataBean.data1);
        bindViews.get(2)
                .setText(dataBean.data2);
        bindViews.get(3)
                .setText(dataBean.data3);
        bindViews.get(4)
                .setText(dataBean.data4);
        bindViews.get(5)
                .setText(dataBean.data5);
        bindViews.get(6)
                .setText(dataBean.data6);
        bindViews.get(7)
                .setText(dataBean.data7);
        bindViews.get(8)
                .setText(dataBean.data8);

    }

    @Override
    public void convertLeftData(int position,TextView bindView) {
        bindView.setText(data.get(position).id);
    }
}
