package com.liangjing.hemodialysisproject.Base;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.liangjing.hemodialysisproject.helper.ToolBarHelper;

/**
 * Created by liangjing on 2017/8/14.
 *
 * Function:对(ui包)中所有Activity的ToolBar的封装
 */

public abstract class ToolBarActivity extends AppCompatActivity {


    private ToolBarHelper mToolBarHelper;

    public Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**
     * function:重写setContentView方法--目的：让ToolBarHelper工具类来生成布局(带有ToolBar)。
     *          因为按道理来说，如果不重写setContentView()方法的话，那么生成的布局就是该activity的布局文件，
     *          所以我们如果想要从内部去为该activity的布局文件添加上ToolBar控件的话，那么我们就需要去拦截
     *          setContentView()方法，然后对该方法进行重写(利用某些工具类来生成新的一个布局),
     *          之后又再通过setContentView(新的布局内容view)继续上传至顶级父类(AppCompatActivity)，
     *          最后顶级父类将会把最新的内容view给绘制出来。
     *
     *         这里的layoutResID即子类传递过来的R.layout.activity_main.
     * @param layoutResID
     */
    @Override
    public void setContentView(int layoutResID) {

        mToolBarHelper = new ToolBarHelper(this, layoutResID);//这个this指的是当前的context。
        toolbar = mToolBarHelper.getToolBar();
        setContentView(mToolBarHelper.getContentView());//为活动设置视图
        /*把 toolbar 设置到Activity 中*/
        setSupportActionBar(toolbar);
        /*自定义的一些操作*/
        onCreateCustomToolBar(toolbar);
        init();
        setUpView();
        initEvents();
    }


    /**
     * function:此处可以利用ToolBar的对象进行相关操作(设置),并且可以添加title布局到ToolBar中
     *
     * @param toolbar
     */
    public void onCreateCustomToolBar(Toolbar toolbar) {
        toolbar.setContentInsetsRelative(0, 0);
    }


    /**
     * function:初始化一些(需要处理的)事件
     */
    protected void initEvents() {
    }


    /**
     * function:初始化view
     */
    protected abstract void setUpView();


    /**
     * function:初始化操作
     */
    protected void init() {

    }

}
