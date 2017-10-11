package com.app.feng.fixtablelayout.inter;

import android.widget.TextView;

import java.util.List;

/**
 * Created by feng on 2017/4/4.
 */

public interface IDataAdapter {

    String getTitleAt(int pos);

    /**
     * 共有几列
     * @return
     */
    int getTitleCount();

    /**
     * 共有几行
     * @return
     */
    int getItemCount();

    void convertData(int position,List<TextView> bindViews);

    void convertLeftData(int position,TextView bindView);

}
