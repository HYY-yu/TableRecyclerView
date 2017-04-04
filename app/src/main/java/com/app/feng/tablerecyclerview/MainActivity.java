package com.app.feng.tablerecyclerview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.app.feng.fixtablelayout.FixTableLayout;

public class MainActivity extends AppCompatActivity {

    public String[] title = {"title1","title2","title3","title4","title5","title6","title7",
                             "title8","title9"};

    public String[][] data = {
            {"data1","data2","data3","data4","data5","data6","data7","data8","data9"},
            {"data1","data2","data3","data4","data5","data6","data7","data8","data9"},
            {"data1","data2","data3","data4","data5","data6","data7","data8","data9"},
            {"data1","data2","data3","data4","data5","data6","data7","data8","data9"},
            {"data1","data2","data3","data4","data5","data6","data7","data8","data9"},
            {"data1","data2","data3","data4","data5","data6","data7","data8","data9"},
            {"data1","data2","data3","data4","data5","data6","data7","data8","data9"},
            {"data1","data2","data3","data4","data5","data6","data7","data8","data9"},
            {"data1","data2","data3","data4","data5","data6","data7","data8","data9"},
            {"data1","data2","data3","data4","data5","data6","data7","data8","data9"},
            {"data1","data2","data3","data4","data5","data6","data7","data8","data9"},
            {"data1","data2","data3","data4","data5","data6","data7","data8","data9"},
            {"data1","data2","data3","data4","data5","data6","data7","data8","data9"},
            {"data1","data2","data3","data4","data5","data6","data7","data8","data9"},
            {"data1","data2","data3","data4","data5","data6","data7","data8","data9"},
            {"data1","data2","data3","data4","data5","data6","data7","data8","data9"},
            {"data1","data2","data3","data4","data5","data6","data7","data8","data9"},
            {"data1","data2","data3","data4","data5","data6","data7","data8","data9"},
            {"data1","data2","data3","data4","data5","data6","data7","data8","data9"},
            {"data1","data2","data3","data4","data5","data6","data7","data8","data9"},
            {"data1","data2","data3","data4","data5","data6","data7","data8","data9"},
            {"data1","data2","data3","data4","data5","data6","data7","data8","data9"},
            {"data1","data2","data3","data4","data5","data6","data7","data8","data9"},
            {"data1","data2","data3","data4","data5","data6","data7","data8","data9"},
            {"data1","data2","data3","data4","data5","data6","data7","data8","data9"},
            {"data1","data2","data3","data4","data5","data6","data7","data8","data9"},
            {"data1","data2","data3","data4","data5","data6","data7","data8","data9"},
            {"data1","data2","data3","data4","data5","data6","data7","data8","data9"},
            {"data1","data2","data3","data4","data5","data6","data7","data8","data9"},
            {"data1","data2","data3","data4","data5","data6","data7","data8","data9"}};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FixTableLayout fixTableLayout = (FixTableLayout) findViewById(R.id.fixTableLayout);

        // 一定要设置Adapter 否则看不到TableLayout
        FixTableAdapter fixTableAdapter = new FixTableAdapter(title,data);
        fixTableLayout.setAdapter(fixTableAdapter);

    }
}
