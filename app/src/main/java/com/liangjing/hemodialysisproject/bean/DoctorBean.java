package com.liangjing.hemodialysisproject.bean;

import java.io.File;

/**
 * Created by liangjing on 2017/8/12.
 * <p>
 * function:(解析json数据对应的医生实体类)
 */

public class DoctorBean {

    private String mDoctorName;

    private String mDoctorRealName;

    private String mDoctorGender;

    private String mUserLocation;

    private String mDoctorCellPhone;

    private String mDoctorIdNumber;

    private String mDoctorBirthday;

    private String mDoctorEmail;

    private String mDoctorPassword;

    private File mDoctorHeadPortrait;

    public String getDoctorName() {
        return mDoctorName;
    }

    public void setDoctorName(String doctorName) {
        this.mDoctorName = doctorName;
    }

    public String getDoctorRealName() {
        return mDoctorRealName;
    }

    public void setDoctorRealName(String doctorRealName) {
        this.mDoctorRealName = doctorRealName;
    }

    public String getDoctorGender() {
        return mDoctorGender;
    }

    public void setDoctorGender(String doctorGender) {
        this.mDoctorGender = doctorGender;
    }

    public String getUserLocation() {
        return mUserLocation;
    }

    public void setUserLocation(String userLocation) {
        this.mUserLocation = userLocation;
    }

    public String getDoctorCellPhone() {
        return mDoctorCellPhone;
    }

    public void setDoctorCellPhone(String doctorCellPhone) {
        this.mDoctorCellPhone = doctorCellPhone;
    }

    public String getDoctorIdNumber() {
        return mDoctorIdNumber;
    }

    public void setDoctorIdNumber(String doctorIdNumber) {
        this.mDoctorIdNumber = doctorIdNumber;
    }

    public String getDoctorBirthday() {
        return mDoctorBirthday;
    }

    public void setDoctorBirthday(String doctorBirthday) {
        this.mDoctorBirthday = doctorBirthday;
    }

    public String getDoctorEmail() {
        return mDoctorEmail;
    }

    public void setDoctorEmail(String doctorEmail) {
        this.mDoctorEmail = doctorEmail;
    }

    public String getDoctorPassword() {
        return mDoctorPassword;
    }

    public void setDoctorPassword(String doctorPassword) {
        this.mDoctorPassword = doctorPassword;
    }

    public File getDoctorHeadPortrait() {
        return mDoctorHeadPortrait;
    }

    public void setDoctorHeadPortrait(File doctorHeadPortrait) {
        this.mDoctorHeadPortrait = doctorHeadPortrait;
    }
}
