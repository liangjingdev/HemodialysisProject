package com.liangjing.hemodialysisproject.fragment;

import android.view.View;
import android.view.ViewGroup;

import com.liangjing.hemodialysisproject.Base.BaseFragment;
import com.liangjing.hemodialysisproject.R;
import com.liangjing.hemodialysisproject.activity.DoctorScheduleActivity;
import com.liangjing.hemodialysisproject.bean.DoctorBean;
import com.liangjing.hemodialysisproject.utils.CardDataUtil;
import com.liangjing.unirecyclerviewlib.adapter.AdapterForRecyclerView;
import com.liangjing.unirecyclerviewlib.adapter.OptionViewHolder;
import com.liangjing.unirecyclerviewlib.adapter.ViewHolderForRecyclerView;
import com.liangjing.unirecyclerviewlib.listener.OnItemClickListener;
import com.liangjing.unirecyclerviewlib.recyclerview.OptionRecyclerView;

import java.util.ArrayList;
import java.util.List;


/**
 * function:列出已经预约过的医生列表，点击进入预约排班
 */
public class AppointedListFragment extends BaseFragment {

    private OptionRecyclerView mRv;
    private AdapterForRecyclerView mAdapter;
    private List<DoctorBean> list;


    @Override
    protected int setLayoutResourceID() {
        return R.layout.fragment_appointed_list_layout;
    }

    @Override
    protected void init() {
        list = new ArrayList<>();
        list = CardDataUtil.getCardViewData();
    }

    @Override
    protected void setUpView() {
        mRv = $(R.id.rv);
    }

    @Override
    protected void initEvents() {

        mAdapter = new AdapterForRecyclerView<DoctorBean>(getmContext(), list, R.layout.item_already_oder_layout) {
            @Override
            public void convert(ViewHolderForRecyclerView holder, DoctorBean doctorBean, int position) {
                holder.setText(R.id.doctorName, doctorBean.getDoctorName());
                holder.setText(R.id.orderDate, "2017-9-23");
            }
        };
        mRv.setAdapter(mAdapter);

        //点击事件监听
        mAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(OptionViewHolder optionViewHolder, ViewGroup viewGroup, View view, int position) {
                startActivityWithoutExtras(DoctorScheduleActivity.class);
            }
        });
    }

}
