package com.app.feng.tablerecyclerview;

import android.widget.TextView;

import com.app.feng.fixtablelayout.adapter.IDataAdapter;

/**
 * Created by feng on 2017/4/4.
 */

public class FixTableAdapter implements IDataAdapter {

    public String[] titles;

    public String[][] data;

    public FixTableAdapter(String[] titles,String[][] data) {
        if(titles.length < 2){
            throw new ArrayIndexOutOfBoundsException("title array length error");
        }
        if(data.length > 0 && data[0].length != titles.length){
            throw new ArrayIndexOutOfBoundsException("data array length error");
        }

        this.titles = titles;
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
        return data.length;
    }

    @Override
    public void convertData(int position,TextView bindView,int columnIndex) {
        String[] oneRow = data[position];

        bindView.setText(oneRow[columnIndex]);
    }

    @Override
    public void convertLeftData(int position,TextView bindView) {
        bindView.setText(data[position][0]);
    }
}
