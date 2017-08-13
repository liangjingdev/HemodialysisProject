package com.liangjing.hemodialysisproject.fragment;

import android.support.design.widget.AppBarLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;

import com.liangjing.hemodialysisproject.Base.BaseFragment;
import com.liangjing.hemodialysisproject.R;
import com.liangjing.hemodialysisproject.adapter.AppointmentAdapter;

/**
 * function:医生详情界面--预约Fragment
 * <p>
 * 需要在该Fragment中添加下拉刷新功能，而不是在AppBar上添加刷新功能，那么该怎么做？
 * <p>
 * 例子：    我见过案例是把SwipeRefreshLayout做为顶级View包在CoordinatorLayout的外面，
 * 我觉得这个做法还是很糟糕的，首先CoordinatorLayout推荐做为顶级View使用，现在又在外面套了个刷新，不伦不类的；
 * 其次，如上面的案例就会出现一个SwipeRefreshLayout会对应三个子列表的刷新，
 * 处理起来还是麻烦。我们是不是可以把SwipeRefreshLayout套在ViewPager外面呢？
 * 也是可以的，但还是麻烦。我们就把下拉刷新这件事交给Fragment自己来做好了。
 * <p>
 * 把下拉刷新这件事交给Fragment自己来做--需要获取到外层Activity的子控件AppBarLayout--getActivity.findViewById()
 */
public class AppointmentFragment extends BaseFragment implements AppBarLayout.OnOffsetChangedListener {

    private RecyclerView mRecyclerView;
    private AppointmentAdapter mAdapter;
    private SwipeRefreshLayout mSwipe;
    private AppBarLayout mAppBarLayout;

    @Override
    protected int setLayoutResourceID() {
        return R.layout.appointment_fragment_layout;
    }

    @Override
    protected void init() {
        mAdapter = new AppointmentAdapter();
    }

    @Override
    protected void initEvents() {
        //设置RecyclerView的item视图分布规则
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

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
                //setRefreshing的作用是设置刷新加载效果的icon是否继续显示.
                // setRefreshing(false)表示加载结束，停止播放加载动画。可以在调用网络请求(延时任务)请求最新数据完成之后调用该方法.
                mSwipe.setRefreshing(false);
            }
        });
    }

    @Override
    protected void setUpView() {
        mRecyclerView = $(R.id.recycler_view);
        mSwipe = $(R.id.swipe_refresh_layout);
        mAppBarLayout = (AppBarLayout) getActivity().findViewById(R.id.app_bar_layout);
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
