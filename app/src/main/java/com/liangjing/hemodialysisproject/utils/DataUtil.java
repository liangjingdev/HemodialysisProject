package com.liangjing.hemodialysisproject.utils;

import com.liangjing.hemodialysisproject.db.DbUtil;
import com.liangjing.hemodialysisproject.db.DoctorEntityHelper;
import com.liangjing.hemodialysisproject.db.UserEntityHelper;
import com.liangjing.hemodialysisproject.entity.DoctorEntity;
import com.liangjing.hemodialysisproject.entity.UserEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liangjing on 2017/8/17.
 * <p>
 * function: 填充用户个人数据进入数据库。(从服务器上获取到用户数据后将其添加到数据库当中)
 */

public class DataUtil {

    private static List<UserEntity> mUserList = new ArrayList<>();//数据库当前所有的数据的集合(所有用户)
    private static List<DoctorEntity> mDoctorList = new ArrayList<>();//数据库当前所有的数据的集合(所有医生)
    private static boolean isExist = false; //判断传进来的数据是否已经存在于数据表当中


    /**
     * function:添加用户数据进入数据库
     *
     * @param entity
     */
    public static void addUserToDb(UserEntity entity) {

        UserEntityHelper helper = DbUtil.getUserEntityHelper();
        mUserList = helper.queryAll();
        for (int i = 0; i < mUserList.size(); i++) {
            if (entity.getId() != null && entity.getId() == mUserList.get(i).getId()) {
                isExist = true;
                return;
            }
        }

        if (!isExist) {
            helper.save(entity);
            isExist = false;
        } else {
            helper.saveOrUpdate(entity);
        }
        //注意将列表清空
        mUserList.clear();
    }

    /**
     * function:添加医生数据进入数据库
     *
     * @param entity
     */
    public static void addDoctorToDb(DoctorEntity entity) {

        DoctorEntityHelper helper = DbUtil.getDoctorEntityHelper();
        mDoctorList = helper.queryAll();
        for (int i = 0; i < mDoctorList.size(); i++) {
            if (entity.getId() != null && entity.getId() == mDoctorList.get(i).getId()) {
                isExist = true;
                return;
            }
        }

        if (!isExist) {
            helper.save(entity);
            isExist = false;
        } /*else {
            helper.update(entity);
        }*/
        //注意将列表清空
        mDoctorList.clear();
    }

}
