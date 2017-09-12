package com.liangjing.hemodialysisproject.bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by liangjing on 2017/9/1.
 * <p>
 * function:(json数据对应的用户实体类)
 */

public class UserBean {

    @SerializedName("id")
    private int userId;

    @SerializedName("username")
    private String mUserName;

    @SerializedName("realname")
    private String mUserRealName;

    @SerializedName("address")
    private String mUserLocation;

    @SerializedName("phone")
    private String mUserCellPhone;

    @SerializedName("code")
    private String mDiagnosisNumber;

    @SerializedName("idcard")
    private String mUserIdNumber;

    @SerializedName("birthday")
    private String mUserBirthday;

    @SerializedName("email")
    private String mUserEmail;

    @SerializedName("password")
    private String mUserPassword;

    @SerializedName("pictureId")
    private String mUserHeadPortrait;


    public String getmUserHeadPortrait() {
        return mUserHeadPortrait;
    }

    public void setmUserHeadPortrait(String mUserHeadPortrait) {
        this.mUserHeadPortrait = mUserHeadPortrait;
    }

    public String getUserName() {
        return mUserName;
    }

    public void setUserName(String mUserName) {
        this.mUserName = mUserName;
    }

    public String getUserRealName() {
        return mUserRealName;
    }

    public void setUserRealName(String mUserRealName) {
        this.mUserRealName = mUserRealName;
    }


    public String getUserLocation() {
        return mUserLocation;
    }

    public void setUserLocation(String mUserLocation) {
        this.mUserLocation = mUserLocation;
    }

    public String getUserCellPhone() {
        return mUserCellPhone;
    }

    public void setUserCellPhone(String mUserCellPhone) {
        this.mUserCellPhone = mUserCellPhone;
    }

    public String getDiagnosisNumber() {
        return mDiagnosisNumber;
    }

    public void setDiagnosisNumber(String mDiagnosisNumber) {
        this.mDiagnosisNumber = mDiagnosisNumber;
    }

    public String getUserIdNumber() {
        return mUserIdNumber;
    }

    public void setUserIdNumber(String mUserIdNumber) {
        this.mUserIdNumber = mUserIdNumber;
    }

    public String getUserBirthday() {
        return mUserBirthday;
    }

    public void setUserBirthday(String mUserBirthday) {
        this.mUserBirthday = mUserBirthday;
    }

    public String getUserEmail() {
        return mUserEmail;
    }

    public void setUserEmail(String mUserEmail) {
        this.mUserEmail = mUserEmail;
    }

    public String getUserPassword() {
        return mUserPassword;
    }

    public void setUserPassword(String mUserPassword) {
        this.mUserPassword = mUserPassword;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "UserBean{" +
                "userId=" + userId +
                ", mUserName='" + mUserName + '\'' +
                ", mUserRealName='" + mUserRealName + '\'' +
                ", mUserLocation='" + mUserLocation + '\'' +
                ", mUserCellPhone='" + mUserCellPhone + '\'' +
                ", mDiagnosisNumber='" + mDiagnosisNumber + '\'' +
                ", mUserIdNumber='" + mUserIdNumber + '\'' +
                ", mUserBirthday='" + mUserBirthday + '\'' +
                ", mUserEmail='" + mUserEmail + '\'' +
                ", mUserPassword='" + mUserPassword + '\'' +
                ", mUserHeadPortrait='" + mUserHeadPortrait + '\'' +
                '}';
    }
}
