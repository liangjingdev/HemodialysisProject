package com.liangjing.hemodialysisproject.db;

import com.liangjing.hemodialysisproject.entity.UserEntity;

import org.greenrobot.greendao.AbstractDao;

/**
 * Created by liangjing on 2017/8/18.
 * function:一个实体类对应一个实现类，该类是用户个人资料表的实现
 */

public class UserEntityHelper extends BaseDbHelper<UserEntity, Long> {
    public UserEntityHelper(AbstractDao dao) {
        super(dao);
    }
}
