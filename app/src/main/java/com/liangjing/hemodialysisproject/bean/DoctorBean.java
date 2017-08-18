package com.liangjing.hemodialysisproject.bean;

/**
 * Created by liangjing on 2017/8/12.
 *
 * function:医生资料实体类
 */

public class DoctorBean {

    private String mDoctorName;

    private String mDoctorIntro;

    private String mOrderDate;

    public String getDoctorName() {
        return mDoctorName;
    }

    public void setDoctorName(String mDoctorName) {
        this.mDoctorName = mDoctorName;
    }

    public String getDoctorIntro() {
        return mDoctorIntro;
    }

    public void setDoctorIntro(String mDoctorIntro) {
        this.mDoctorIntro = mDoctorIntro;
    }

    public String getmOrderDate() {
        return mOrderDate;
    }

    public void setmOrderDate(String mOrderDate) {
        this.mOrderDate = mOrderDate;
    }
}
