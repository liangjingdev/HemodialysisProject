package com.liangjing.hemodialysisproject.db;

import com.liangjing.hemodialysisproject.entity.DoctorEntity;

import org.greenrobot.greendao.AbstractDao;

/**
 * Created by liangjing on 2017/8/31.
 * function:一个实体类对应一个实现类，该类是医生个人资料表的实现
 */

public class DoctorEntityHelper extends BaseDbHelper<DoctorEntity,Long>{

    public DoctorEntityHelper(AbstractDao dao) {
        super(dao);
    }
}
