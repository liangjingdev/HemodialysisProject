package com.liangjing.hemodialysisproject.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by liangjing on 2017/8/18.
 *
 * function:患者个人周期排班信息--实体类
 */

@Entity
public class PatientSchemeEntity {

    @Id(autoincrement = true)
    private Long id;

    private Long schemeId;

    private String patientName;

    private String doctorName;

    private String dialysisTime;

    private String location;

    private String machineNumber;

    private String dialysisScheme;

    @Generated(hash = 1459431684)
    public PatientSchemeEntity(Long id, Long schemeId, String patientName,
            String doctorName, String dialysisTime, String location,
            String machineNumber, String dialysisScheme) {
        this.id = id;
        this.schemeId = schemeId;
        this.patientName = patientName;
        this.doctorName = doctorName;
        this.dialysisTime = dialysisTime;
        this.location = location;
        this.machineNumber = machineNumber;
        this.dialysisScheme = dialysisScheme;
    }

    @Generated(hash = 564350701)
    public PatientSchemeEntity() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSchemeId() {
        return this.schemeId;
    }

    public void setSchemeId(Long schemeId) {
        this.schemeId = schemeId;
    }

    public String getPatientName() {
        return this.patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getDoctorName() {
        return this.doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public String getDialysisTime() {
        return this.dialysisTime;
    }

    public void setDialysisTime(String dialysisTime) {
        this.dialysisTime = dialysisTime;
    }

    public String getLocation() {
        return this.location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getMachineNumber() {
        return this.machineNumber;
    }

    public void setMachineNumber(String machineNumber) {
        this.machineNumber = machineNumber;
    }

    public String getDialysisScheme() {
        return this.dialysisScheme;
    }

    public void setDialysisScheme(String dialysisScheme) {
        this.dialysisScheme = dialysisScheme;
    }

}
