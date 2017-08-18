package com.liangjing.hemodialysisproject.utils;

import com.liangjing.hemodialysisproject.db.DbUtil;
import com.liangjing.hemodialysisproject.db.UserEntityHelper;
import com.liangjing.hemodialysisproject.entity.UserEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liangjing on 2017/8/17.
 * <p>
 * function: 填充用户个人数据进入数据库。(从服务器上获取到用户数据后将其添加到数据库当中)
 */

public class UserDataUtil {

    private static List<UserEntity> mList = new ArrayList<>();//数据库当前所有的数据的集合
    private static boolean isExist = false; //判断传进来的个人数据是否已经存在于数据表当中


    /**
     * function:
     *
     * @param entity
     */
    public static void addToDb(UserEntity entity) {

        UserEntityHelper helper = DbUtil.getUserEntityHelper();
        mList = helper.queryAll();
        for (int i = 0; i < mList.size(); i++) {
            if (entity.getId() != null && entity.getId() == mList.get(i).getId()) {
                isExist = true;
                return;
            }
        }

        if (!isExist) {
            helper.save(entity);
            isExist = false;
        }else {
            helper.saveOrUpdate(entity);
        }

        //注意将列表清空
        mList.clear();
    }

}
