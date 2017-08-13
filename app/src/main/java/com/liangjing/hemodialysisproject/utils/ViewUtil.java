package com.liangjing.hemodialysisproject.utils;

import com.liangjing.hemodialysisproject.Base.BaseFragment;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by liangjing on 2017/8/8.
 * <p>
 * function: 工具类--创建Fragment对象(避免重复创建对象)
 */

public class ViewUtil {

    private static Map<String, BaseFragment> fragmentList = new HashMap<>();


    /**
     * function:根据传进来的.class文件来创建Fragment
     *
     * @param clazz
     * @param isObtain
     * @return
     */
    public static BaseFragment createFragment(Class<?> clazz, boolean isObtain) {

        BaseFragment resultFragment = null;
        //获取到传进来的Fragment的.class的名称（字符串类型）
        String className = clazz.getName();
        //首先需要进行判断该Fragment是否存在于fragmentList中，即判断该Fragment是否之前有被创建过？--避免重复创建Fragment实例对象
        if (fragmentList.containsKey(className)) {
            resultFragment = fragmentList.get(className);
        } else {
            try {
                //Class.forName(名称).newInstance()--创建Fragment对象
                resultFragment = (BaseFragment) Class.forName(className).newInstance();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        if (isObtain) {
            fragmentList.put(className, resultFragment);
        }

        return resultFragment;
    }

    public static BaseFragment createFragment(Class<?> clazz) {
        return createFragment(clazz, true);
    }


}
