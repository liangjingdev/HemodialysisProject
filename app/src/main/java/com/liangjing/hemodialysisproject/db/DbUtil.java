package com.liangjing.hemodialysisproject.db;

import com.liangjing.hemodialysisproject.PatientSchemeEntityDao;
import com.liangjing.hemodialysisproject.UserEntityDao;

/**
 * Created by liangjing on 2017/8/17.
 * <p>
 * function: 获取表 Helper 的工具类
 */

public class DbUtil {

    private static UserEntityHelper sUserEntityHelper;
    private static PatientSchemeHelper sSChemeHelper;

    private static UserEntityDao getUserDao() {
        return DbCore.getDaoSession().getUserEntityDao();
    }

    private static PatientSchemeEntityDao getSchemaDao() {
        return DbCore.getDaoSession().getPatientSchemeEntityDao();
    }

    public static UserEntityHelper getUserEntityHelper() {
        if (sUserEntityHelper == null) {
            sUserEntityHelper = new UserEntityHelper(getUserDao());
        }
        return sUserEntityHelper;
    }

    public static PatientSchemeHelper getPatientSchemaHelper() {
        if (sSChemeHelper == null) {
            sSChemeHelper = new PatientSchemeHelper(getSchemaDao());
        }
        return sSChemeHelper;
    }
}
