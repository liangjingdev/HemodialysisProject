package com.liangjing.hemodialysisproject.fragment;


import com.liangjing.hemodialysisproject.Base.BaseFragment;
import com.liangjing.hemodialysisproject.R;
import com.liangjing.hemodialysisproject.db.DbUtil;
import com.liangjing.hemodialysisproject.db.UserEntityHelper;
import com.liangjing.hemodialysisproject.entity.PatientSchemeEntity;
import com.liangjing.hemodialysisproject.entity.UserEntity;
import com.liangjing.unirecyclerviewlib.adapter.AdapterForRecyclerView;
import com.liangjing.unirecyclerviewlib.adapter.ViewHolderForRecyclerView;
import com.liangjing.unirecyclerviewlib.recyclerview.OptionRecyclerView;

import java.util.ArrayList;
import java.util.List;

public class CycleScheduleFragment extends BaseFragment {

    private OptionRecyclerView mRv;
    private AdapterForRecyclerView mAdapter;
    private List<PatientSchemeEntity> mOldList;//个人周期排班数据的集合(数据从旧的开始排)
    private List<PatientSchemeEntity> mNewList;//个人周期排班数据的集合(数据从新的开始排)


    @Override
    protected int setLayoutResourceID() {
        return R.layout.cycle_schedule_fragment_layout;
    }

    @Override
    protected void init() {
        mOldList = new ArrayList<>();
        mNewList = new ArrayList<>();

        //首先获取当前用户,然后再获取该用户的个人周期排班数据的集合
        UserEntityHelper helper = DbUtil.getUserEntityHelper();
        UserEntity entity = helper.query((long) 1);
        mOldList = entity.getSchemeLiist();

        //将数据进行整理，从新的开始排
        for (int i = 0; i < mOldList.size(); i++) {
            mNewList.add(mOldList.get((mOldList.size() - 1) - i));
        }
    }

    @Override
    protected void setUpView() {
        mRv = $(R.id.rv);
    }

    @Override
    protected void initEvents() {

        mAdapter = new AdapterForRecyclerView<PatientSchemeEntity>(getmContext(), mNewList, R.layout.cycle_schedule_item_layout) {
            @Override
            public void convert(ViewHolderForRecyclerView holder, PatientSchemeEntity entity, int position) {
                holder.setText(R.id.patientName, entity.getPatientName());
                holder.setText(R.id.doctorName, entity.getDoctorName());
                holder.setText(R.id.dialysisLocation, entity.getLocation());
                holder.setText(R.id.dialysisScheme, entity.getDialysisScheme());
                holder.setText(R.id.dialysisTime, entity.getDialysisTime());
                holder.setText(R.id.machineNumber, entity.getMachineNumber());
            }
        };

        mRv.setAdapter(mAdapter);
    }


}