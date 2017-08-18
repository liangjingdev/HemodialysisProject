package com.liangjing.hemodialysisproject.utils;

import com.liangjing.hemodialysisproject.R;
import com.liangjing.hemodialysisproject.bean.DoctorBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liangjing on 2017/8/12.
 * <p>
 * function:为RecyclerView的CardView(item视图)填充数据
 */

public class CardDataUtil {

    public static List<DoctorBean> getCardViewData() {

        List<DoctorBean> beans = new ArrayList<>();

        for (int i = 0; i < 20; i++) {
            DoctorBean mDoctorBean = new DoctorBean();
            mDoctorBean.setDoctorName("姓名：" + "牛逼" + ":" + i);
            mDoctorBean.setDoctorIntro("简介：" + String.valueOf(R.string.doctor));
            mDoctorBean.setmOrderDate("2017年9月8日");
            beans.add(mDoctorBean);
        }
        return beans;
    }
}
