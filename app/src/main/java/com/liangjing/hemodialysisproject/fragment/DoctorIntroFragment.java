package com.liangjing.hemodialysisproject.fragment;

import android.widget.TextView;

import com.liangjing.hemodialysisproject.Base.BaseFragment;
import com.liangjing.hemodialysisproject.R;
import com.liangjing.unirecyclerviewlib.adapter.AdapterForRecyclerView;
import com.liangjing.unirecyclerviewlib.adapter.ViewHolderForRecyclerView;
import com.liangjing.unirecyclerviewlib.recyclerview.OptionRecyclerView;

import java.util.ArrayList;

/**
 * function:医生详细资料显示Fragment
 */
public class DoctorIntroFragment extends BaseFragment {

    private OptionRecyclerView mRv;
    private TextView mTv;
    private AdapterForRecyclerView mAdapter;
    private ArrayList<Integer> list;

    @Override
    protected int setLayoutResourceID() {
        return R.layout.fragment_doctor_intro_layout;
    }

    @Override
    protected void init() {
        list = new ArrayList();
        list.add(R.string.doctor);
    }

    @Override
    protected void setUpView() {
        mRv = $(R.id.rv);
    }

    @Override
    protected void initEvents() {


        mAdapter = new AdapterForRecyclerView<Integer>(getmContext(), list, R.layout.item_doctor_intro_layout) {
            @Override
            public void convert(ViewHolderForRecyclerView holder, Integer stringId, int position) {
                mTv = holder.getView(R.id.tv);
                mTv.setText(stringId);
            }
        };
        mRv.setAdapter(mAdapter);
    }
}
