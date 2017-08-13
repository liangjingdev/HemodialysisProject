package com.liangjing.hemodialysisproject.fragment;

import android.widget.TextView;

import com.liangjing.hemodialysisproject.Base.BaseFragment;
import com.liangjing.hemodialysisproject.R;

/**
 * function:医生资料显示Fragment
 */
public class DoctorIntroFragment extends BaseFragment {

    private TextView mTextView;

    @Override
    protected int setLayoutResourceID() {
        return R.layout.doctor_intro_fragment_layout;
    }

    @Override
    protected void setUpView() {
        mTextView = $(R.id.textView);
    }

    @Override
    protected void initEvents() {
        mTextView.setText(R.string.doctor);
    }
}

