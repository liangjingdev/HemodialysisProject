package com.liangjing.hemodialysisproject.db;


import com.liangjing.hemodialysisproject.entity.DoctorEntityDao;
import com.liangjing.hemodialysisproject.entity.PatientSchemeEntityDao;
import com.liangjing.hemodialysisproject.entity.UserEntityDao;

/**
 * Created by liangjing on 2017/8/17.
 * <p>
 * function: 获取表 Helper 的工具类
 */

public class DbUtil {

    private static UserEntityHelper sUserEntityHelper;
    private static PatientSchemeHelper sSchemeHelper;
    private static DoctorEntityHelper sDoctorEntityHelper;

    private static UserEntityDao getUserDao() {
        return DbCore.getDaoSession().getUserEntityDao();
    }

    private static PatientSchemeEntityDao getSchemaDao() {
        return DbCore.getDaoSession().getPatientSchemeEntityDao();
    }

    private static DoctorEntityDao getDoctorDao() {
        return DbCore.getDaoSession().getDoctorEntityDao();
    }

    public static UserEntityHelper getUserEntityHelper() {
        if (sUserEntityHelper == null) {
            sUserEntityHelper = new UserEntityHelper(getUserDao());
        }
        return sUserEntityHelper;
    }

    public static PatientSchemeHelper getPatientSchemaHelper() {
        if (sSchemeHelper == null) {
            sSchemeHelper = new PatientSchemeHelper(getSchemaDao());
        }
        return sSchemeHelper;
    }

    public static DoctorEntityHelper getDoctorEntityHelper() {
        if (sDoctorEntityHelper == null) {
            sDoctorEntityHelper = new DoctorEntityHelper(getDoctorDao());
        }
        return sDoctorEntityHelper;
    }
}
