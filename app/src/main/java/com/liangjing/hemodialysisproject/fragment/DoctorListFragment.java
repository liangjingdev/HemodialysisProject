package com.liangjing.hemodialysisproject.fragment;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.View;
import android.widget.Toast;

import com.liangjing.hemodialysisproject.Base.BaseFragment;
import com.liangjing.hemodialysisproject.R;
import com.liangjing.hemodialysisproject.activity.DoctorDetailActivity;
import com.liangjing.hemodialysisproject.adapter.CardViewAdapter;
import com.liangjing.hemodialysisproject.bean.DoctorBean;
import com.liangjing.hemodialysisproject.listener.OnRecyclerItemClickListener;

import java.util.ArrayList;
import java.util.List;

/**
 * function:医生列表(预约挂号Fragment)
 */
public class DoctorListFragment extends BaseFragment {

    private RecyclerView mRecyclerView;
    private CardViewAdapter mAdapter;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private Handler handler;
    private int lastVisibleItem;

    @Override
    protected int setLayoutResourceID() {
        return R.layout.doctor_list_fragment_layout;
    }

    @Override
    protected void init() {

        handler = new Handler();

        mAdapter = new CardViewAdapter(getContext(), new OnRecyclerItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                startActivityWithoutExtras(DoctorDetailActivity.class);
            }
        });
    }


    @Override
    protected void setUpView() {
        mRecyclerView = $(R.id.recycler_view);
        mSwipeRefreshLayout = $(R.id.swipe_refresh_layout);
    }


    @TargetApi(Build.VERSION_CODES.M)
    @Override
    protected void initEvents() {

        //设置RecyclerView的item视图分布规则
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        //设置刷新时动画的颜色，可以设置4个
        mSwipeRefreshLayout.setProgressBackgroundColorSchemeResource(android.R.color.white);
        mSwipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_light,
                android.R.color.holo_red_light, android.R.color.holo_orange_light,
                android.R.color.holo_green_light);
        mSwipeRefreshLayout.setProgressViewOffset(false, 0, (int) TypedValue
                .applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24, getResources()
                        .getDisplayMetrics()));

        //设置下拉刷新功能 -- SwipeRefreshLayout
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                //可进行网络请求获取最新数据
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        List<DoctorBean> beans = new ArrayList<DoctorBean>();
                        for (int i = 0; i < 5; i++) {
                            DoctorBean doctorBean = new DoctorBean();
                            doctorBean.setDoctorName("姓名：" + "哈哈哈" + i);
                            doctorBean.setDoctorIntro("简介：" + getResources().getString(R.string.doctor));
                            beans.add(doctorBean);
                        }
                        mAdapter.addItem(beans);
                        mSwipeRefreshLayout.setRefreshing(false);
                        Toast.makeText(getContext(), "已更新...", Toast.LENGTH_SHORT).show();
                    }
                }, 5000);
            }
        });


        //设置上拉加载功能(监听滚动事件，检查是否已滚动到了最後一条item)
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE && lastVisibleItem + 1 == mAdapter.getItemCount()) {
                    mAdapter.changeStatus(CardViewAdapter.PULL_UP_LOAD_MORE);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            List<DoctorBean> beans = new ArrayList<>();
                            for (int i = 0; i < 5; i++) {
                                DoctorBean doctorBean = new DoctorBean();
                                doctorBean.setDoctorName("姓名：" + "溜溜" + i);
                                doctorBean.setDoctorIntro("简介：" + getResources().getString(R.string.doctor));
                                beans.add(doctorBean);
                            }
                            mAdapter.addMoreItem(beans);
                            mAdapter.changeStatus(CardViewAdapter.LOADING_MORE);
                        }
                    }, 2500);
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();
            }
        });
    }

}
