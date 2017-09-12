package com.liangjing.hemodialysisproject.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.design.widget.AppBarLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.liangjing.hemodialysisproject.Base.BaseFragment;
import com.liangjing.hemodialysisproject.R;
import com.liangjing.hemodialysisproject.widget.CustomDialog;
import com.liangjing.unirecyclerviewlib.adapter.AdapterForRecyclerView;
import com.liangjing.unirecyclerviewlib.adapter.ViewHolderForRecyclerView;
import com.liangjing.unirecyclerviewlib.recyclerview.OptionRecyclerView;

import java.util.ArrayList;
import java.util.List;

import static com.liangjing.hemodialysisproject.R.id.appBarLayout;

/**
 * function:预约挂号fragment
 */
public class AppointmentFragment extends BaseFragment implements AppBarLayout.OnOffsetChangedListener {

    private OptionRecyclerView mRv;
    private AdapterForRecyclerView mAdapter;
    private SwipeRefreshLayout mSwipe;
    private List<String> list;
    private Handler mHandler;
    private AppBarLayout mAppBarLayout;
    private Button mOrderButton;
    private CustomDialog.Builder mBuilder;
    private View.OnClickListener listener;
    private CustomDialog mDialog;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    @Override
    protected int setLayoutResourceID() {
        return R.layout.fragment_appointment_layout;
    }

    @Override
    protected void init() {
        list = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            list.add("10:20-12:2" + i);
        }
        mHandler = new Handler();

        //创建自定义对话框dialog的构建者
        mBuilder = new CustomDialog.Builder(getmContext());

        //获取SharedPreferences对象以及editor对象
        preferences = getmContext().getSharedPreferences("buttonFlag", Context.MODE_PRIVATE);
        editor = preferences.edit();
    }

    @Override
    protected void setUpView() {
        mRv = $(R.id.rv);
        mSwipe = $(R.id.swipe_refresh_layout);
        mAppBarLayout = (AppBarLayout) getActivity().findViewById(appBarLayout);
    }

    @Override
    protected void initEvents() {

        mAdapter = new AdapterForRecyclerView<String>(getmContext(), list, R.layout.item_appointment_layout) {
            @Override
            public void convert(ViewHolderForRecyclerView holder, String item, int position) {
                holder.setText(R.id.orderDate, item);
                holder.setText(R.id.orderTimeQuantum, item);
                holder.setText(R.id.orderNumber, item);
                holder.setText(R.id.canOrderNumber, item);
                Button orderButton = holder.getView(R.id.orderButton);
                orderButton.setOnClickListener(listener);
                //为列表中每个item的按钮设置一个不同的tag值
                orderButton.setTag(position);

                //判断sp中是否存有该按钮的状态值,分别处理
                if (preferences.contains(position + "")) {
                    //取出状态值
                    Boolean flag = preferences.getBoolean(position + "", true);
                    //判断该按钮是可预约状态还是已预约状态
                    if (!flag) {
                        orderButton.setText(R.string.already_order);
                        orderButton.setBackgroundResource(R.drawable.save_button_pressed_shape);
                        orderButton.setEnabled(false);
                    }
                } else {
                    editor.putBoolean(position + "", true);
                    editor.commit();
                }


            }
        };

        //设置刷新时动画的颜色，可以设置4个
        mSwipe.setProgressBackgroundColorSchemeResource(android.R.color.white);
        mSwipe.setColorSchemeResources(android.R.color.holo_blue_light,
                android.R.color.holo_red_light, android.R.color.holo_orange_light,
                android.R.color.holo_green_light);
        mSwipe.setProgressViewOffset(false, 0, (int) TypedValue
                .applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24, getResources()
                        .getDisplayMetrics()));

        mSwipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                //可进行网络请求获取最新数据
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        List<String> list = new ArrayList<>();
                        for (int i = 0; i < 5; i++) {
                            list.add("2017年3月1" + i + "日" + "  14:20");
                        }
                        mAdapter.setData(list);
                        //setRefreshing的作用是设置刷新加载效果的icon是否继续显示.
                        // setRefreshing(false)表示加载结束，停止播放加载动画。可以在调用网络请求(延时任务)请求最新数据完成之后调用该方法.
                        mSwipe.setRefreshing(false);
                        Toast.makeText(getmContext(), "已更新...", Toast.LENGTH_SHORT).show();
                    }
                }, 5000);
            }
        });

        mRv.setAdapter(mAdapter);


        /**
         * function：处理预约按钮的点击事件以及自定义dialog中子view的点击事件
         */
        listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.orderButton:
                        mOrderButton = (Button) v;
                        mDialog = mBuilder.cancelTouchOut(false)
                                .heightdp(250)
                                .widthdp(200)
                                .style(R.style.CustomDialog)
                                .view(R.layout.dialog_appointment_layout)
                                .addViewOnclick(R.id.btn_confirm, listener)
                                .build();
                        mDialog.show();
                        break;
                    case R.id.btn_confirm:
                        mDialog.dismiss();
                        //当点击自定义dialog中的确认按钮时，则改变所对应item中的按钮的状态
                        mOrderButton.setText(R.string.already_order);
                        mOrderButton.setBackgroundResource(R.drawable.save_button_pressed_shape);
                        mOrderButton.setEnabled(false);

                        //保存当前位置按钮的状态值(已预约)
                        editor.remove(mOrderButton.getTag() + "");
                        editor.putBoolean(mOrderButton.getTag() + "", false);
                        editor.commit();
                        break;
                }
            }
        };

    }


    /**
     * function:实现在AppbarLayout没有发生偏移量的前提下的下拉刷新功能。--Fragment中的下拉刷新功能
     *
     * @param appBarLayout
     * @param i
     */
    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int i) {
        if (i == 0) {
            //开启下拉刷新功能
            mSwipe.setEnabled(true);
        } else {
            //关闭下拉刷新功能
            mSwipe.setEnabled(false);
        }
    }


    @Override
    public void onResume() {
        super.onResume();
        //监听
        mAppBarLayout.addOnOffsetChangedListener(this);
    }


    @Override
    public void onPause() {
        super.onPause();
        mAppBarLayout.removeOnOffsetChangedListener(this);
    }

}
