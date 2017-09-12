package com.liangjing.hemodialysisproject.db;

import android.content.Context;

import com.liangjing.hemodialysisproject.entity.DaoMaster;
import com.liangjing.hemodialysisproject.entity.UserEntityDao;

import org.greenrobot.greendao.database.Database;

/**
 * Created by liangjing on 2017/8/17.
 * <p>
 * function:自定义一个OPenHelper辅助类继承自DaoMaster.OpenHelper,便于数据库升级
 */

public class MyOpenHelper extends DaoMaster.OpenHelper {

    public MyOpenHelper(Context context, String name) {
        super(context, name);
    }

    @Override
    public void onUpgrade(Database db, int oldVersion, int newVersion) {


        switch (oldVersion) {
            case 1:

                //如果旧版本是1的话，则不能先删除表，否则数据都木了
                //  StudentDao.dropTable(db, true);

                UserEntityDao.createTable(db, true);

                // 通知:加入新字段 score
                //db.execSQL("ALTER TABLE 'STUDENT' ADD 'SCORE' TEXT;");

                break;
        }

    }
}
