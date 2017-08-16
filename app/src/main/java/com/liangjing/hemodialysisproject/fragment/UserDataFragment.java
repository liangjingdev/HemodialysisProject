package com.liangjing.hemodialysisproject.fragment;


import android.view.View;

import com.liangjing.hemodialysisproject.Base.BaseFragment;
import com.liangjing.hemodialysisproject.R;
import com.liangjing.hemodialysisproject.activity.ui.ChangeCellPhoneActivity;
import com.liangjing.hemodialysisproject.activity.ui.ChangeIdNumberActivity;
import com.liangjing.hemodialysisproject.activity.ui.ChangeMyLocationActivity;
import com.liangjing.hemodialysisproject.activity.ui.ChangeRealNameActivity;
import com.liangjing.hemodialysisproject.activity.ui.ChangeUserGenderActivity;
import com.liangjing.hemodialysisproject.activity.ui.ChangeUserNameActivity;
import com.liangjing.hemodialysisproject.activity.ui.ImageGridActivity;
import com.liangjing.hemodialysisproject.activity.ui.ShowBigImageActivity;
import com.liangjing.library.OptionItemView;
import com.zhy.autolayout.AutoLinearLayout;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * function:个人资料详情界面Fragment
 */
public class UserDataFragment extends BaseFragment implements View.OnClickListener {

    private AutoLinearLayout mLlHeader; //更换头像
    private CircleImageView mIvHeader; //显示大头像
    private OptionItemView mUserName;
    private OptionItemView mRealName;
    private OptionItemView mUserGender;
    private OptionItemView mMyLocation;
    private OptionItemView mCellPhone;
    private OptionItemView mDiagnosisNumber; //诊断号不允许自行修改
    private OptionItemView mIdNumber;


    @Override
    protected int setLayoutResourceID() {
        return R.layout.user_data_fragment_layout;
    }


    @Override
    protected void setUpView() {

        mLlHeader = $(R.id.llHeader);
        mIvHeader = $(R.id.ivHeader);
        mUserName = $(R.id.userName);
        mRealName = $(R.id.realName);
        mUserGender = $(R.id.userGender);
        mMyLocation = $(R.id.myLocation);
        mCellPhone = $(R.id.cellPhone);
        mDiagnosisNumber = $(R.id.diagnosisNumber);
        mIdNumber = $(R.id.idNumber);
    }

    @Override
    protected void initEvents() {
        mIvHeader.setOnClickListener(this);
        mLlHeader.setOnClickListener(this);
        mUserName.setOnClickListener(this);
        mRealName.setOnClickListener(this);
        mUserGender.setOnClickListener(this);
        mMyLocation.setOnClickListener(this);
        mCellPhone.setOnClickListener(this);
        mIdNumber.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivHeader:
                startActivityWithoutExtras(ShowBigImageActivity.class);
                break;
            case R.id.llHeader:
                startActivityWithoutExtras(ImageGridActivity.class);
                break;
            case R.id.userName:
                startActivityWithoutExtras(ChangeUserNameActivity.class);
                break;
            case R.id.realName:
                startActivityWithoutExtras(ChangeRealNameActivity.class);
                break;
            case R.id.userGender:
                startActivityWithoutExtras(ChangeUserGenderActivity.class);
                break;
            case R.id.myLocation:
                startActivityWithoutExtras(ChangeMyLocationActivity.class);
                break;
            case R.id.cellPhone:
                startActivityWithoutExtras(ChangeCellPhoneActivity.class);
                break;
            case R.id.idNumber:
                startActivityWithoutExtras(ChangeIdNumberActivity.class);
                break;
        }
    }
}
