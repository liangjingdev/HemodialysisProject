package com.liangjing.hemodialysisproject.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liangjing on 2017/8/17.
 *
 * function:获取医生的可预约时间集
 */

public class OrderTimeUtil {

    public static List<String> getOrderTime(){

        List<String> list = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            list.add("2017年2月1" + i + "日" + "  14:20");
        }
        return list;
    }
}
