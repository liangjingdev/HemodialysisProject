package com.liangjing.hemodialysisproject.fragment;


import android.content.Intent;
import android.os.Bundle;
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
import com.liangjing.hemodialysisproject.db.DbUtil;
import com.liangjing.hemodialysisproject.db.UserEntityHelper;
import com.liangjing.hemodialysisproject.entity.UserEntity;
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
    private OptionItemView mBirthday;
    private Bundle mBundle;

    //静态常量
    private static final int USER_NAME = 1000;


    @Override
    protected int setLayoutResourceID() {
        return R.layout.user_data_fragment_layout;
    }

    @Override
    protected void init() {

        mBundle = new Bundle();
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
        mBirthday = $(R.id.birthday);
    }

    @Override
    protected void initEvents() {
        showData();
        mIvHeader.setOnClickListener(this);
        mLlHeader.setOnClickListener(this);
        mUserName.setOnClickListener(this);
        mRealName.setOnClickListener(this);
        mUserGender.setOnClickListener(this);
        mMyLocation.setOnClickListener(this);
        mCellPhone.setOnClickListener(this);
        mIdNumber.setOnClickListener(this);
    }

    /**
     * function:显示数据/保存数据进Bundle传递给其它Activity
     */
    private void showData() {
        //从数据库取出数据
        UserEntity entity = DbUtil.getUserEntityHelper().query((long) 1);
        mUserName.setRightText(entity.getUserName());
        mRealName.setRightText(entity.getRealName());
        mUserGender.setRightText(entity.getUserGender());
        mMyLocation.setRightText(entity.getUserLocation());
        mCellPhone.setRightText(entity.getCellPhone());
        mDiagnosisNumber.setRightText(entity.getDiagnosisNumber());
        mIdNumber.setRightText(entity.getIdNumber());
        mBirthday.setRightText(entity.getUserBirthday());

        //保存当前最新的数据到Bundle,便于传递给相应的activity进行显示
        addDataToBundle(entity);
    }


    /**
     * function:添加最新的数据进入Bundle
     */
    private void addDataToBundle(UserEntity entity) {
        mBundle.putString("userName",entity.getUserName());
        mBundle.putString("realName",entity.getRealName());
        mBundle.putString("idNumber",entity.getIdNumber());
        mBundle.putString("userGender",entity.getUserGender());
        mBundle.putString("userLocation",entity.getUserLocation());
        mBundle.putString("cellPhone",entity.getCellPhone());
        mBundle.putString("birthday",entity.getUserBirthday());
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
                startActivityForResult(ChangeUserNameActivity.class,mBundle);
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
            case R.id.birthday:
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (resultCode){
            case USER_NAME:
                mUserName.setRightText(data.getStringExtra("userName"));

                UserEntityHelper helper = DbUtil.getUserEntityHelper();
                UserEntity entity = helper.query((long) 1);
                addDataToBundle(entity);
                break;
        }
    }
}
