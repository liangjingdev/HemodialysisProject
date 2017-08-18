package com.liangjing.hemodialysisproject.utils;

/**
 * Created by liangjing on 2017/8/7.
 *
 * function:笔记
 */

public class note {

    /**
     *   动态切换添加或者替换碎片。
     *
     *   1.创建待添加的碎片实例
     *   2、获取FragmentManager对象,在活动中可以直接通过调用getSupportFragmentManager()方法得到
     *   3、开启一个事务，通过调用beginTransaction()方法开启
     *   4、向容器内添加或替换碎片，一般使用replace()方法实现，需要传入容器的id和待添加的碎片实例
     *   5、提交事务，调用commit()方法来完成
     */


    /**
     * ViewPager的预加载管理：setOffscreenPageLimit
     *
     * ViewPager 的setOffscreenPageLimit(int arg0);方法用于设置ViewPager的后台加载页面个数。
     *
     *假设ViewPager中设置了3个页面
     *
     *现在给参数为2，即setOffscreenPageLimit(2);那么就是3个页面全部会在第一次加载的时候被加载完成。
     *
     *参数2的含义就是，除去当前显示页面以外需要被预加载的页面数。
     *
     *参数最少设置为1，当给的参数小于等于1的时候，效果与给1同。
     */


}
