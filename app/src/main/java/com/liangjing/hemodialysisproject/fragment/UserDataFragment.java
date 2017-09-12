package com.liangjing.hemodialysisproject.fragment;


import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;

import com.bumptech.glide.Glide;
import com.jzxiang.pickerview.TimePickerDialog;
import com.jzxiang.pickerview.data.Type;
import com.jzxiang.pickerview.listener.OnDateSetListener;
import com.liangjing.hemodialysisproject.Base.BaseFragment;
import com.liangjing.hemodialysisproject.R;
import com.liangjing.hemodialysisproject.activity.ui.ChangeCellPhoneActivity;
import com.liangjing.hemodialysisproject.activity.ui.ChangeIdNumberActivity;
import com.liangjing.hemodialysisproject.activity.ui.ChangeRealNameActivity;
import com.liangjing.hemodialysisproject.activity.ui.ChangeUserEmailActivity;
import com.liangjing.hemodialysisproject.activity.ui.ChangeUserLocationActivity;
import com.liangjing.hemodialysisproject.activity.ui.ChangeUserNameActivity;
import com.liangjing.hemodialysisproject.activity.ui.ChangeUserPasswordActivity;
import com.liangjing.hemodialysisproject.activity.ui.ShowBigImageActivity;
import com.liangjing.hemodialysisproject.db.DbUtil;
import com.liangjing.hemodialysisproject.entity.UserEntity;
import com.liangjing.hemodialysisproject.utils.DateUtil;
import com.liangjing.library.OptionItemView;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.zhy.autolayout.AutoLinearLayout;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * function:个人资料详情界面Fragment
 */
public class UserDataFragment extends BaseFragment implements View.OnClickListener, OnDateSetListener {

    private AutoLinearLayout mLlHeader; //更换头像
    private CircleImageView mIvHeader; //显示大头像
    private OptionItemView mUserName;
    private OptionItemView mRealName;
    private OptionItemView mUserGender;
    private OptionItemView mUserLocation;
    private OptionItemView mCellPhone;
    private OptionItemView mDiagnosisNumber; //诊断号不允许自行修改
    private OptionItemView mUserIdNumber;
    private OptionItemView mUserBirthday;
    private OptionItemView mUserEmail;
    private OptionItemView mUserPassword;
    private TimePickerDialog mDialog;
    private UserEntity mEntity;

    private int mPosition = 0; //控制性别选择框的默认选中
    private String[] mSexArray;
    private Bundle mBundle;
    private String mImagePath;

    //静态常量(resultCode)
    private static final int RESULT_CHANGE_USER_NAME = 1;
    private static final int RESULT_CHANGE_REAL_NAME = 2;
    private static final int RESULT_CHANGE_USER_LOCATION = 3;
    private static final int RESULT_CHANGE_USER_CELL_PHONE = 4;
    private static final int RESULT_CHANGE_USER_IDNumber = 5;
    private static final int RESULT_CHANGE_USER_EMAIL = 6;
    private static final int RESULT_CHANGE_USER_PASSWORD = 7;

    //静态常量(requestCode)
    private static final int REQUEST_IMAGE = 0;
    private static final int REQUEST_CHANGE_USER_NAME = 10;
    private static final int REQUEST_CHANGE_REAL_NAME = 20;
    private static final int REQUEST_CHANGE_USER_LOCATION = 30;
    private static final int REQUEST_CHANGE_USER_CELL_PHONE = 40;
    private static final int REQUEST_CHANGE_USER_IDNumber = 50;
    private static final int REQUEST_CHANGE_USER_EMAIL = 60;
    private static final int REQUEST_CHANGE_USER_PASSWORD = 70;

    @Override
    protected int setLayoutResourceID() {
        return R.layout.fragment_user_data_layout;
    }

    @Override
    protected void init() {
        mEntity = getUserEntity((long) 1);
        mBundle = new Bundle();
        mSexArray = new String[]{"男", "女"};
    }

    @Override
    protected void setUpView() {
        mLlHeader = $(R.id.llHeader);
        mIvHeader = $(R.id.ivHeader);
        mUserName = $(R.id.userName);
        mRealName = $(R.id.userRealName);
        mUserGender = $(R.id.userGender);
        mUserLocation = $(R.id.userLocation);
        mCellPhone = $(R.id.userCellPhone);
        mDiagnosisNumber = $(R.id.diagnosisNumber);
        mUserIdNumber = $(R.id.userIdNumber);
        mUserBirthday = $(R.id.userBirthday);
        mUserEmail = $(R.id.userEmail);
        mUserPassword = $(R.id.userPassword);
    }

    @Override
    protected void initEvents() {
        showData();
        setDefaultChoose();
        setDateDialog();
        mIvHeader.setOnClickListener(this);
        mLlHeader.setOnClickListener(this);
        mUserName.setOnClickListener(this);
        mRealName.setOnClickListener(this);
        mUserGender.setOnClickListener(this);
        mUserLocation.setOnClickListener(this);
        mCellPhone.setOnClickListener(this);
        mUserIdNumber.setOnClickListener(this);
        mUserBirthday.setOnClickListener(this);
        mUserEmail.setOnClickListener(this);
        mUserPassword.setOnClickListener(this);
    }

    /**
     * function:配置日期选择器
     */
    private void setDateDialog() {
        mDialog = new TimePickerDialog.Builder()
                .setCallBack(this)
                .setCancelStringId("取消")
                .setSureStringId("确定")
                .setTitleStringId("请选择出生日期")
                .setYearText("年")
                .setMonthText("月")
                .setDayText("日")
                .setCyclic(true)
                .setMinMillseconds(System.currentTimeMillis() - (long) 3600 * 1000 * 24 * 366 * 60)
                .setMaxMillseconds(System.currentTimeMillis())
                .setCurrentMillseconds(DateUtil.transformMillis(mEntity.getUserBirthday()))
                .setThemeColor(getResources().getColor(R.color.timepicker_dialog_bg))
                .setType(Type.YEAR_MONTH_DAY)
                .setWheelItemTextNormalColor(getResources().getColor(R.color.timetimepicker_default_text_color))
                .setWheelItemTextSelectorColor(getResources().getColor(R.color.timepicker_toolbar_bg))
                .setWheelItemTextSize(16)
                .build();
    }


    /**
     * function:设置弹出框默认选中的性别
     */
    private void setDefaultChoose() {
        if (mEntity.getUserGender().equals("男")) {
            mPosition = 0;
        } else {
            mPosition = 1;
        }
    }


    /**
     * function:显示数据/保存数据进Bundle传递给其它Activity
     */
    private void showData() {
        mUserName.setRightText(mEntity.getUserName() != null ? mEntity.getUserName() : getResources().getString(R.string.not_fill));
        mRealName.setRightText(mEntity.getRealName() != null ? mEntity.getRealName() : getResources().getString(R.string.not_fill));
        mUserGender.setRightText(mEntity.getUserGender() != null ? mEntity.getUserGender() : getResources().getString(R.string.not_fill));
        mUserLocation.setRightText(mEntity.getUserLocation() != null ? mEntity.getUserLocation() : getResources().getString(R.string.not_fill));
        mCellPhone.setRightText(mEntity.getCellPhone() != null ? mEntity.getCellPhone() : getResources().getString(R.string.not_fill));
        mDiagnosisNumber.setRightText(mEntity.getDiagnosisNumber());
        mUserIdNumber.setRightText(mEntity.getIdNumber() != null ? mEntity.getIdNumber() : getResources().getString(R.string.not_fill));
        mUserBirthday.setRightText(mEntity.getUserBirthday() != null ? mEntity.getUserBirthday() : getResources().getString(R.string.not_fill));
        mUserEmail.setRightText(mEntity.getUserEmail() != null ? mEntity.getUserEmail() : getResources().getString(R.string.not_fill));
        if (mEntity.getHeadPortrait() != null) {
            Glide.with(getActivity()).load(mEntity.getHeadPortrait()).centerCrop().into(mIvHeader);
        } else {
            Glide.with(getActivity()).load(R.drawable.avatar).centerCrop().into(mIvHeader);
        }

        //保存当前最新的数据到Bundle,便于传递给相应的activity进行显示
        addDataToBundle(mEntity);
    }


    /**
     * function:添加最新的数据进入Bundle
     */
    private void addDataToBundle(UserEntity entity) {
        mBundle.putString("userName", entity.getUserName());
        mBundle.putString("realName", entity.getRealName());
        mBundle.putString("userIdNumber", entity.getIdNumber());
        mBundle.putString("userGender", entity.getUserGender());
        mBundle.putString("userLocation", entity.getUserLocation());
        mBundle.putString("userCellPhone", entity.getCellPhone());
        mBundle.putString("birthday", entity.getUserBirthday());
        mBundle.putString("userEmail", entity.getUserEmail());
        mBundle.putString("userPassword", entity.getUserPassword());
        mBundle.putByteArray("headPortrait", entity.getHeadPortrait());
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivHeader:
                startActivityWithExtras(ShowBigImageActivity.class, mBundle);
                break;
            case R.id.llHeader:
                //开启相册
                startActivityForResult(ImageGridActivity.class, 0);
                break;
            case R.id.userName:
                startActivityForResult(ChangeUserNameActivity.class, mBundle, 10);
                break;
            case R.id.userRealName:
                startActivityForResult(ChangeRealNameActivity.class, mBundle, 20);
                break;
            case R.id.userGender:
                showSexChooseDialog(mSexArray, mPosition);
                break;
            case R.id.userLocation:
                startActivityForResult(ChangeUserLocationActivity.class, mBundle, 30);
                break;
            case R.id.userCellPhone:
                startActivityForResult(ChangeCellPhoneActivity.class, mBundle, 40);
                break;
            case R.id.userIdNumber:
                startActivityForResult(ChangeIdNumberActivity.class, mBundle, 50);
                break;
            case R.id.userEmail:
                startActivityForResult(ChangeUserEmailActivity.class, mBundle, 60);
                break;
            case R.id.userPassword:
                startActivityForResult(ChangeUserPasswordActivity.class, mBundle, 70);
                break;
            case R.id.userBirthday:
                //首先进行判断若mDialog不为空且mDialog并没有已经被添加到活动中(避免重复添加到活动中)，则显示该对话框(日期选择器本质上就是一个对话框--note)。
                if (mDialog != null) {
                    if (!mDialog.isAdded()) {
                        mDialog.show(getFragmentManager(), "year_month_day");
                    }
                }
                break;
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (resultCode) {
            case RESULT_CHANGE_USER_NAME:
                //(坑)注意：一定要先判断requestCode,然后再进行相关操作，否则系统无法识别出是哪里发出来的点击事件，然后根据回调获取到的信息将无法传递到与其相对应的点击事件处、
                if (requestCode == REQUEST_CHANGE_USER_NAME) {
                    mUserName.setRightText(data.getStringExtra("userName"));
                    //获取最新的UserEntity(数据),并且更新Bundle中的数据
                    mEntity = getUserEntity((long) 1);
                    addDataToBundle(mEntity);
                }
                break;
            case ImagePicker.RESULT_CODE_ITEMS:
                if (data != null && requestCode == REQUEST_IMAGE) {
                    ArrayList<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                    ImageItem imageItem = images.get(0);

                    //裁剪后的图片的路径(存储在内部存储空间中)
                    mImagePath = imageItem.path;

                    //将头像文件存储到数据库
                    File file = new File(mImagePath);
                    byte[] headPortrait = transformByte(file);
                    mEntity.setHeadPortrait(headPortrait);
                    DbUtil.getUserEntityHelper().update(mEntity);
                    addDataToBundle(mEntity);

                    //显示裁剪后的图片
                    mIvHeader.setImageBitmap(BitmapFactory.decodeFile(mImagePath));
                }
                break;
            case RESULT_CHANGE_REAL_NAME:
                if (requestCode == REQUEST_CHANGE_REAL_NAME)
                    mRealName.setRightText(data.getStringExtra("realName"));
                //获取最新的UserEntity(数据),并且更新Bundle中的数据
                mEntity = getUserEntity((long) 1);
                addDataToBundle(mEntity);
                break;
            case RESULT_CHANGE_USER_LOCATION:
                if (requestCode == REQUEST_CHANGE_USER_LOCATION)
                    mUserLocation.setRightText(data.getStringExtra("userLocation"));
                //获取最新的UserEntity(数据),并且更新Bundle中的数据
                mEntity = getUserEntity((long) 1);
                addDataToBundle(mEntity);
                break;
            case RESULT_CHANGE_USER_CELL_PHONE:
                if (requestCode == REQUEST_CHANGE_USER_CELL_PHONE)
                    mCellPhone.setRightText(data.getStringExtra("userCellPhone"));
                //获取最新的UserEntity(数据),并且更新Bundle中的数据
                mEntity = getUserEntity((long) 1);
                addDataToBundle(mEntity);
                break;
            case RESULT_CHANGE_USER_IDNumber:
                if (requestCode == REQUEST_CHANGE_USER_IDNumber)
                    mUserIdNumber.setRightText(data.getStringExtra("userIdNumber"));
                //获取最新的UserEntity(数据),并且更新Bundle中的数据
                mEntity = getUserEntity((long) 1);
                addDataToBundle(mEntity);
                break;
            case RESULT_CHANGE_USER_EMAIL:
                if (requestCode == REQUEST_CHANGE_USER_EMAIL)
                    mUserEmail.setRightText(data.getStringExtra("userEmail"));
                //获取最新的UserEntity(数据),并且更新Bundle中的数据
                mEntity = getUserEntity((long) 1);
                addDataToBundle(mEntity);
                break;
            case RESULT_CHANGE_USER_PASSWORD:
                if (requestCode == REQUEST_CHANGE_USER_PASSWORD) {
                    //获取最新的UserEntity(数据),并且更新Bundle中的数据
                    mEntity = getUserEntity((long) 1);
                    addDataToBundle(mEntity);
                }
                break;
        }
    }


    /**
     * function:弹出选择性别界面--对话框
     *
     * @param sexArray
     */
    private void showSexChooseDialog(String[] sexArray, int pos) {
        //自定义对话框
        AlertDialog.Builder builder = new AlertDialog.Builder(getmContext());
        //pos--默认选中的选项的位置
        builder.setSingleChoiceItems(sexArray, pos, (dialog, position) -> {

            if (pos == position) {
                return;
            } else {
                mPosition = position;
                mEntity.setUserGender(position == 0 ? "男" : "女");
                DbUtil.getUserEntityHelper().update(mEntity);
                mUserGender.setRightText(mEntity.getUserGender());
            }
            dialog.dismiss();
        });
        //让弹出框显示
        builder.show();
    }


    /**
     * function:获取与id相对应的用户的实体类(从数据库取出该用户的数据)
     *
     * @param id
     * @return
     */
    private UserEntity getUserEntity(Long id) {
        UserEntity entity = DbUtil.getUserEntityHelper().query(id);
        return entity;
    }


    /**
     * function:日期选择器--选择日期后的回调方法(millSeconds是你当前选择的日期所对应的毫秒数)
     *
     * @param timePickerView
     * @param millSeconds
     */
    @Override
    public void onDateSet(TimePickerDialog timePickerView, long millSeconds) {
        String userBirthday = DateUtil.transformDate(millSeconds);
        mUserBirthday.setRightText(userBirthday);
        mEntity.setUserBirthday(userBirthday);
        DbUtil.getUserEntityHelper().update(mEntity);
        //注意：用户出生日期发生改变之后需要重新配置日期选择器
        setDateDialog();
    }


    /**
     * function:将头像文件转化为字节数据存储到数据库当中，使用的时候再将其转化为文件类型(因为SQLite数据库不支持存储File类型的数据)
     *
     * @return
     */
    private byte[] transformByte(File file) {

        byte[] ret = null;
        FileInputStream in = null;
        int n;

        if (file == null) {
            return null;
        }
        try {
            in = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        ByteArrayOutputStream out = new ByteArrayOutputStream(4096);
        byte[] b = new byte[4096];

        try {
            while ((n = in.read(b)) != -1) {
                out.write(b, 0, n);
            }
            ret = out.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return ret;
    }
}
