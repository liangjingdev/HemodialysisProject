package com.liangjing.hemodialysisproject.fragment;

import android.widget.TextView;

import com.liangjing.hemodialysisproject.Base.BaseFragment;
import com.liangjing.hemodialysisproject.R;

/**
 * function:医生详细资料显示Fragment
 */
public class DoctorIntroFragment extends BaseFragment {

    private TextView mTextView;

    @Override
    protected int setLayoutResourceID() {
        return R.layout.fragment_doctor_intro_layout;
    }

    @Override
    protected void setUpView() {
        mTextView = $(R.id.tv);
    }

    @Override
    protected void initEvents() {
        mTextView.setText(R.string.doctor);
    }
}
