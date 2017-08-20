package com.liangjing.hemodialysisproject.fragment;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.liangjing.hemodialysisproject.Base.BaseFragment;
import com.liangjing.hemodialysisproject.R;
import com.liangjing.hemodialysisproject.activity.DoctorDetailActivity;
import com.liangjing.hemodialysisproject.bean.DoctorBean;
import com.liangjing.hemodialysisproject.utils.CardDataUtil;
import com.liangjing.unirecyclerviewlib.adapter.AdapterForRecyclerView;
import com.liangjing.unirecyclerviewlib.adapter.OptionViewHolder;
import com.liangjing.unirecyclerviewlib.adapter.ViewHolderForRecyclerView;
import com.liangjing.unirecyclerviewlib.listener.OnItemClickListener;
import com.liangjing.unirecyclerviewlib.recyclerview.OptionRecyclerView;
import com.mancj.materialsearchbar.MaterialSearchBar;

import java.util.ArrayList;
import java.util.List;

/**
 * function:医生列表
 */
public class DoctorListFragment extends BaseFragment implements MaterialSearchBar.OnSearchActionListener {

    private OptionRecyclerView mRecyclerView;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private Handler handler;
    private List<DoctorBean> mBeans;
    private AdapterForRecyclerView mAdapter;
    private MaterialSearchBar mSearchBar;

    @Override
    protected int setLayoutResourceID() {
        return R.layout.fragment_doctor_list_layout;
    }

    @Override
    protected void init() {
        mBeans = new ArrayList<>();
        //给mBeans添加数据
        mBeans = CardDataUtil.getCardViewData();
        handler = new Handler();
        mSearchBar = (MaterialSearchBar) getContentView().findViewById(R.id.searchBar);
    }


    @Override
    protected void setUpView() {
        mRecyclerView = $(R.id.rv);
        mSwipeRefreshLayout = $(R.id.swipeRefreshLayout);
    }


    @TargetApi(Build.VERSION_CODES.M)
    @Override
    protected void initEvents() {

        //为搜索框设置监听功能
        mSearchBar.setOnSearchActionListener(this);

        //获取AdapterForRecyclerView对象
        mAdapter = new AdapterForRecyclerView<DoctorBean>(getmContext(), mBeans, R.layout.item_doctor_layout) {
            @Override
            public void convert(ViewHolderForRecyclerView holder, DoctorBean doctorBean, int i) {
                holder.setText(R.id.doctorName, doctorBean.getDoctorName());
                holder.setText(R.id.doctorIntro, doctorBean.getDoctorIntro());
            }
        };

        //设置适配器
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
                        List<DoctorBean> beans = new ArrayList<>();
                        for (int i = 0; i < 5; i++) {
                            DoctorBean doctorBean = new DoctorBean();
                            doctorBean.setDoctorName("姓名：" + "哈哈哈" + i);
                            doctorBean.setDoctorIntro("简介：" + getResources().getString(R.string.doctor));
                            beans.add(doctorBean);
                        }
                        mAdapter.setData(beans);
                        mSwipeRefreshLayout.setRefreshing(false);
                        Toast.makeText(getmContext(), "已更新...", Toast.LENGTH_SHORT).show();
                    }
                }, 5000);
            }
        });

        mAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(OptionViewHolder optionViewHolder, ViewGroup viewGroup, View view, int i) {
                startActivityWithoutExtras(DoctorDetailActivity.class);
            }
        });

    }


    @Override
    public void onSearchStateChanged(boolean enabled) {
        String s = enabled ? "enabled" : "disabled";
        Toast.makeText(getmContext(), "Search " + s, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSearchConfirmed(CharSequence text) {
        if (TextUtils.isEmpty(text)) {
            Toast.makeText(getmContext(), "搜索数据为空，请重新输入！", Toast.LENGTH_SHORT).show();
        } else {
            startActivityWithoutExtras(DoctorDetailActivity.class);
        }
    }

    @Override
    public void onButtonClicked(int buttonCode) {

    }

}
