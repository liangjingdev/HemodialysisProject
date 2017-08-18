package com.liangjing.hemodialysisproject.utils;

import com.liangjing.hemodialysisproject.db.DbUtil;
import com.liangjing.hemodialysisproject.db.PatientSchemeHelper;
import com.liangjing.hemodialysisproject.entity.PatientSchemeEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liangjing on 2017/8/18.
 * <p>
 * function: 填充患者个人排班信息数据进入数据库。(从服务器上获取到用户数据后将其添加到数据库当中)
 */

public class PatientSchemaUtil {

    private static List<PatientSchemeEntity> mList = new ArrayList<>();//数据库当前所有的数据的集合
    private static boolean isExist = false; //判断传进来的排班数据是否已经存在于数据表当中


    /**
     * function:
     *
     * @param entity
     */
    public static void addToDb(PatientSchemeEntity entity) {

        PatientSchemeHelper helper = DbUtil.getPatientSchemaHelper();
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
        } else {
            helper.saveOrUpdate(entity);
        }

        //注意将列表清空
        mList.clear();
    }
}
