package com.liangjing.hemodialysisproject.db;

import com.liangjing.hemodialysisproject.entity.PatientSchemeEntity;

import org.greenrobot.greendao.AbstractDao;

/**
 * Created by liangjing on 2017/8/18.
 * <p>
 * function:患者个人周期排班表的实现类
 */

public class PatientSchemeHelper extends BaseDbHelper<PatientSchemeEntity, Long> {

    public PatientSchemeHelper(AbstractDao dao) {
        super(dao);
    }
}
