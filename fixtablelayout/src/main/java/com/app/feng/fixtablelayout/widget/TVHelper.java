package com.app.feng.fixtablelayout.widget;

import android.content.Context;
import android.widget.TextView;

/**
 * Created by feng on 2017/4/4.
 */

public class TVHelper {

    public static TextView generateTextView(
            Context context,String text,int gravity,int minWidth,int padding) {
        TextView textView = new TextView(context);
        setTextView(textView,text,gravity,minWidth,padding);
        return textView;
    }

    public static void setTextView(
            TextView textView,String text,int gravity,int minWidth,int padding) {
        textView.setText(text);
        textView.setGravity(gravity);
        textView.setMinWidth(minWidth);

        textView.setPadding(0,padding / 2,0,padding / 2);
    }
}
