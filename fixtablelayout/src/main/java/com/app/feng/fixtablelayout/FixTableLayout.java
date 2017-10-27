package com.app.feng.fixtablelayout;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.content.res.TypedArrayUtils;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.TextView;

import com.app.feng.fixtablelayout.adapter.TableAdapter;
import com.app.feng.fixtablelayout.inter.IDataAdapter;
import com.app.feng.fixtablelayout.inter.ILoadMoreListener;
import com.app.feng.fixtablelayout.widget.SingleLineItemDecoration;
import com.app.feng.fixtablelayout.widget.TableLayoutManager;

import java.lang.ref.WeakReference;

/**
 * Created by feng on 2017/4/2.
 */
public class FixTableLayout extends FrameLayout {
    public static final int MESSAGE_FIX_TABLE_LOAD_COMPLETE = 1001;

    RecyclerView recyclerView;
    HorizontalScrollView titleView;
    RecyclerView leftViews;
    TextView left_top_view;
    View leftViewShadow;
    FrameLayout fl_load_mask;

    int divider_height;
    int divider_color;
    int col_1_color;
    int col_2_color;
    int title_color;
    int item_width;
    int item_padding;
    int item_gravity;

    boolean show_left_shadow = false;
    private IDataAdapter dataAdapter;

    private boolean isLoading = false;
    private ILoadMoreListener loadMoreListener;
    private boolean hasMoreData = true;

    public FixTableLayout(Context context) {
        this(context, null);
    }

    public FixTableLayout(
            Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FixTableLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.FixTableLayout);

        divider_height = array.getDimensionPixelOffset(
                R.styleable.FixTableLayout_fixtable_divider_height,
                getResources().getDimensionPixelOffset(R.dimen.divider_default_value));
        divider_color = array.getColor(R.styleable.FixTableLayout_fixtable_divider_color,
                Color.BLACK);
        col_1_color = array.getColor(R.styleable.FixTableLayout_fixtable_column_1_color,
                Color.WHITE);
        col_2_color = array.getColor(R.styleable.FixTableLayout_fixtable_column_2_color,
                Color.WHITE);
        title_color = array.getColor(R.styleable.FixTableLayout_fixtable_title_color, Color.GRAY);
        item_width = array.getDimensionPixelOffset(R.styleable.FixTableLayout_fixtable_item_width,
                getResources().getDimensionPixelOffset(R.dimen.item_width_default_value));
        item_padding = array.getDimensionPixelOffset(
                R.styleable.FixTableLayout_fixtable_item_top_bottom_padding, 0);
        item_gravity = array.getInteger(R.styleable.FixTableLayout_fixtable_item_gravity, 0);

        switch (item_gravity) {
            case 0:
                item_gravity = Gravity.CENTER;
                break;
            case 1:
                item_gravity = Gravity.START | Gravity.CENTER_VERTICAL;
                break;
            case 2:
                item_gravity = Gravity.END | Gravity.CENTER_VERTICAL;
                break;
        }

        show_left_shadow = array.getBoolean(
                R.styleable.FixTableLayout_fixtable_show_left_view_shadow, false);

        array.recycle();

        View view = inflate(context, R.layout.table_view, null);
        init(view);
        addView(view);
    }

    private void init(View view) {
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        titleView = (HorizontalScrollView) view.findViewById(R.id.titleView);
        leftViews = (RecyclerView) view.findViewById(R.id.leftViews);
        left_top_view = (TextView) view.findViewById(R.id.left_top_view);
        leftViewShadow = view.findViewById(R.id.leftView_shadows);
        fl_load_mask = (FrameLayout) view.findViewById(R.id.load_mask);

        TableLayoutManager t1 = new TableLayoutManager();
        TableLayoutManager t2 = new TableLayoutManager();

//        Log.i("feng"," -- t : " + t1.toString().substring(54) + " t_left: " + t2.toString()
//                .substring(54));

        recyclerView.setLayoutManager(t1);
        leftViews.setLayoutManager(t2);

        leftViews.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                //将事件发送到RV
                recyclerView.onTouchEvent(event);
                return true;
            }
        });

        titleView.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                recyclerView.onTouchEvent(event);
                return true;
            }
        });

        if (show_left_shadow) {
            leftViewShadow.setVisibility(VISIBLE);
        } else {
            leftViewShadow.setVisibility(GONE);
        }

        SingleLineItemDecoration itemDecoration = new SingleLineItemDecoration(divider_height, divider_color);

        leftViews.addItemDecoration(itemDecoration);
        recyclerView.addItemDecoration(itemDecoration);

        //titleView 只做横向滚动   leftView 只做纵向滚动
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                titleView.scrollBy(dx, 0);
                leftViews.scrollBy(0, dy);
            }
        });

    }

    public void setAdapter(IDataAdapter dataAdapter) {
        this.dataAdapter = dataAdapter;
        initRecyclerViewAdapter();
    }

    int lastVisablePos = -1;
    FixTableHandler fixTableHandler;

    public void enableLoadMoreData() {
        fixTableHandler = new FixTableHandler(FixTableLayout.this, recyclerView);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                // 当用户滑动到底部并且使用fling手势
                if (!isLoading && hasMoreData &&
                        newState == RecyclerView.SCROLL_STATE_IDLE &&
                        lastVisablePos == recyclerView.getAdapter().getItemCount() - 1) {

                    isLoading = true;
                    fl_load_mask.setVisibility(VISIBLE);

                    if (loadMoreListener != null) {
                        loadMoreListener.loadMoreData(
                                fixTableHandler.obtainMessage(FixTableLayout.MESSAGE_FIX_TABLE_LOAD_COMPLETE));
                    }
                }
                //                    Log.i("feng","滑动到底部 -- 此时的View Bottom：" + recyclerView.getLayoutManager()
                //                            .getDecoratedBottom
                //                            (bottomView) + " recyclerView Height:" +recyclerView.getHeight());

            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                View bottomView = recyclerView.getChildAt(recyclerView.getChildCount() - 1);
                lastVisablePos = recyclerView.getChildAdapterPosition(bottomView);
            }
        });
    }

    /**
     * 只有 enableLoadMoreData()被执行此方法设置才有效果
     * @param loadMoreListener
     */
    public void setLoadMoreListener(ILoadMoreListener loadMoreListener) {
        this.loadMoreListener = loadMoreListener;
    }

    private void initRecyclerViewAdapter() {
        TableAdapter.Builder builder = new TableAdapter.Builder();
        TableAdapter tableAdapter = builder.setLeft_top_view(left_top_view)
                .setTitleView(titleView)
                .setParametersHolder(
                        new TableAdapter.ParametersHolder(col_1_color, col_2_color, title_color,
                                item_width, item_padding, item_gravity))
                .setLeftViews(leftViews)
                .setDataAdapter(dataAdapter)
                .create();

        recyclerView.setAdapter(tableAdapter);
    }

    public void dataUpdate() {
        TableAdapter tableAdapter = (TableAdapter) recyclerView.getAdapter();
        tableAdapter.notifyLoadData();
    }

    private static class FixTableHandler extends Handler {
        WeakReference<RecyclerView> recyclerViewWeakReference;
        WeakReference<FixTableLayout> fixTableLayoutWeakReference;

        FixTableHandler(FixTableLayout fixTableLayout, RecyclerView recyclerView) {
            recyclerViewWeakReference = new WeakReference<>(recyclerView);
            fixTableLayoutWeakReference = new WeakReference<>(fixTableLayout);
        }

        @Override
        public void handleMessage(Message msg) {
            if (msg.what == MESSAGE_FIX_TABLE_LOAD_COMPLETE) {
                RecyclerView recyclerView = recyclerViewWeakReference.get();
                FixTableLayout fixTableLayout = fixTableLayoutWeakReference.get();

                TableAdapter tableAdapter = (TableAdapter) recyclerView.getAdapter();
                int startPos = tableAdapter.getItemCount() - 1;
                int loadNum = msg.arg1;
                if (loadNum > 0) {
                    //通知Adapter更新数据
                    tableAdapter.notifyLoadData();

//                    Log.i("fixtablelayout","load more completed loadNum :" + loadNum + "scrollTo " +
//                            ":" + fixTableLayout.lastVisableMask);

                } else {
                    //没有数据了
                    fixTableLayout.hasMoreData = false;
                }

                fixTableLayout.fl_load_mask.setVisibility(GONE);
                fixTableLayout.isLoading = false;
            }
        }
    }
}
