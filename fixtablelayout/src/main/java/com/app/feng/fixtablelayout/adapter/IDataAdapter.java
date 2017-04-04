package com.app.feng.fixtablelayout.adapter;

import android.widget.TextView;

/**
 * Created by feng on 2017/4/4.
 */

public interface IDataAdapter {

    String getTitleAt(int pos);

    int getTitleCount();

    int getItemCount();

    void convertData(int position,TextView bindView,int columnIndex);

    void convertLeftData(int position,TextView bindView);

}
