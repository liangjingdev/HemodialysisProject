package com.liangjing.hemodialysisproject.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liangjing on 2017/8/17.
 *
 * function:更新医生的可预约时间表
 */

public class UpdateTimeUtil {

    public static List<String> UpdateTime(){

        List<String> list = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            list.add("侃侃大山" + i);
        }
        return list;
    }
}
