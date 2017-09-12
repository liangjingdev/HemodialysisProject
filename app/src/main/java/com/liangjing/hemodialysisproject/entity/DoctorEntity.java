package com.liangjing.hemodialysisproject.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by liangjing on 2017/8/31.
 * <p>
 * function:医生个人信息实体类
 */

@Entity
public class DoctorEntity {

    @Id(autoincrement = true)  //注意：从1开始递增
    private Long id;

    @Property(nameInDb = "doctorName")
    private String doctorName;

    @Property(nameInDb = "doctorRealName")
    private String doctorRealName;

    @Property(nameInDb = "doctorGender")
    private String doctorGender;

    @Property(nameInDb = "doctorLocation")
    private String doctorLocation;

    @Property(nameInDb = "doctorCellPhone")
    private String doctorCellPhone;        //手机号码

    @Property(nameInDb = "doctorIdNumber")
    private String doctorIdNumber;

    @Property(nameInDb = "doctorBirthday")
    private String doctorBirthday;

    @Property(nameInDb = "userEmail")
    private String doctorEmail;

    @Property(nameInDb = "userPassword")
    private String doctorPassword;

    @Property(nameInDb = "doctorHeadPortrait")
    private byte[] doctorHeadPortrait;

    @Generated(hash = 1101480423)
    public DoctorEntity(Long id, String doctorName, String doctorRealName,
            String doctorGender, String doctorLocation, String doctorCellPhone,
            String doctorIdNumber, String doctorBirthday, String doctorEmail,
            String doctorPassword, byte[] doctorHeadPortrait) {
        this.id = id;
        this.doctorName = doctorName;
        this.doctorRealName = doctorRealName;
        this.doctorGender = doctorGender;
        this.doctorLocation = doctorLocation;
        this.doctorCellPhone = doctorCellPhone;
        this.doctorIdNumber = doctorIdNumber;
        this.doctorBirthday = doctorBirthday;
        this.doctorEmail = doctorEmail;
        this.doctorPassword = doctorPassword;
        this.doctorHeadPortrait = doctorHeadPortrait;
    }

    @Generated(hash = 1674811596)
    public DoctorEntity() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDoctorName() {
        return this.doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public String getDoctorRealName() {
        return this.doctorRealName;
    }

    public void setDoctorRealName(String doctorRealName) {
        this.doctorRealName = doctorRealName;
    }

    public String getDoctorGender() {
        return this.doctorGender;
    }

    public void setDoctorGender(String doctorGender) {
        this.doctorGender = doctorGender;
    }


    public String getDoctorCellPhone() {
        return this.doctorCellPhone;
    }

    public void setDoctorCellPhone(String doctorCellPhone) {
        this.doctorCellPhone = doctorCellPhone;
    }

    public String getDoctorIdNumber() {
        return this.doctorIdNumber;
    }

    public void setDoctorIdNumber(String doctorIdNumber) {
        this.doctorIdNumber = doctorIdNumber;
    }

    public String getDoctorBirthday() {
        return this.doctorBirthday;
    }

    public void setDoctorBirthday(String doctorBirthday) {
        this.doctorBirthday = doctorBirthday;
    }

    public String getDoctorEmail() {
        return this.doctorEmail;
    }

    public void setDoctorEmail(String doctorEmail) {
        this.doctorEmail = doctorEmail;
    }

    public String getDoctorPassword() {
        return this.doctorPassword;
    }

    public void setDoctorPassword(String doctorPassword) {
        this.doctorPassword = doctorPassword;
    }

    public byte[] getDoctorHeadPortrait() {
        return this.doctorHeadPortrait;
    }

    public void setDoctorHeadPortrait(byte[] doctorHeadPortrait) {
        this.doctorHeadPortrait = doctorHeadPortrait;
    }

    public String getDoctorLocation() {
        return this.doctorLocation;
    }

    public void setDoctorLocation(String doctorLocation) {
        this.doctorLocation = doctorLocation;
    }
}
