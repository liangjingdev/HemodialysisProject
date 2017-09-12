package com.liangjing.hemodialysisproject.bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by liangjing on 2017/9/1.
 *
 * function:患者个人周期排班信息--json数据解析对应的实体类
 */

public class PatientSchemeBean {

    @SerializedName("")
    private String mPatientName;

    @SerializedName("")
    private String mDoctorName;

    @SerializedName("")
    private String mDialysisTime;

    @SerializedName("")
    private String mLocation;

    @SerializedName("")
    private String mMachineNumber;

    @SerializedName("dialysis_program_id")
    private String mDialysisScheme;

    public String getPatientName() {
        return mPatientName;
    }

    public void setPatientName(String mPatientName) {
        this.mPatientName = mPatientName;
    }

    public String getDoctorName() {
        return mDoctorName;
    }

    public void setDoctorName(String mDoctorName) {
        this.mDoctorName = mDoctorName;
    }

    public String getDialysisTime() {
        return mDialysisTime;
    }

    public void setDialysisTime(String mDialysisTime) {
        this.mDialysisTime = mDialysisTime;
    }

    public String getLocation() {
        return mLocation;
    }

    public void setLocation(String mLocation) {
        this.mLocation = mLocation;
    }

    public String getMachineNumber() {
        return mMachineNumber;
    }

    public void setMachineNumber(String mMachineNumber) {
        this.mMachineNumber = mMachineNumber;
    }

    public String getDialysisScheme() {
        return mDialysisScheme;
    }

    public void setDialysisScheme(String mDialysisScheme) {
        this.mDialysisScheme = mDialysisScheme;
    }
}
