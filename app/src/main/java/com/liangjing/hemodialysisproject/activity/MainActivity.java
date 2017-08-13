package com.liangjing.hemodialysisproject.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;

import com.liangjing.hemodialysisproject.Base.BaseActivity;
import com.liangjing.hemodialysisproject.R;
import com.liangjing.hemodialysisproject.fragment.AboutFragment;
import com.liangjing.hemodialysisproject.fragment.DoctorListFragment;
import com.liangjing.hemodialysisproject.fragment.MainFragment;
import com.liangjing.hemodialysisproject.fragment.UserInformationFragment;
import com.liangjing.hemodialysisproject.utils.ViewUtil;

public class MainActivity extends BaseActivity {

    private DrawerLayout mDrawerLayout; //侧边菜单视图
    private ActionBarDrawerToggle mDrawerToggle;  //菜单开关
    private Toolbar mToolbar;
    private NavigationView mNavigationView;//侧边菜单项
    private FragmentManager mFragmentManager;
    private Fragment mCurrentFragment;//当前的Fragment
    private MenuItem mPreMenuItem; //当前选中的menu item

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    /**
     * function:设置当前视图的id
     *
     * @return
     */
    @Override
    protected int setLayoutResourceID() {
        return R.layout.main_activity_layout;
    }


    /**
     * function:初始化view
     */
    @Override
    protected void setUpView() {
        mToolbar = $(R.id.toolbar);
        mDrawerLayout = $(R.id.drawer_layout);
        mNavigationView = $(R.id.navigation_view);

        mToolbar.setTitle("首页");
        //这句一定要在下面几句之前调用，不然就会出现点击无反应
        setSupportActionBar(mToolbar);
        setNavigationViewItemClickListener();
        //ActionBarDrawerToggle配合Toolbar，实现Toolbar上菜单按钮开关效果
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.drawer_open, R.string.drawer_close);
        mDrawerToggle.syncState();
        mDrawerLayout.addDrawerListener(mDrawerToggle);

        initDefaultFragment();
    }


    /**
     * function:初始化必要操作
     */
    protected void init() {
        mFragmentManager = getSupportFragmentManager();
    }


    /**
     * function:初始化默认显示的Fragment
     */
    private void initDefaultFragment() {

        mCurrentFragment = ViewUtil.createFragment(MainFragment.class);
        mFragmentManager.beginTransaction().add(R.id.frame_content, mCurrentFragment).commit();
        //设置侧边栏中的menu中的主页选项为选中状态(主页--MainFragment)
        mPreMenuItem = mNavigationView.getMenu().getItem(0);
        mPreMenuItem.setCheckable(true);
    }


    /**
     * function:监听侧边栏menu中的item
     */
    private void setNavigationViewItemClickListener() {

        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                if (mPreMenuItem != null) {
                    mPreMenuItem.setCheckable(false);
                }

                switch (item.getItemId()) {
                    case R.id.navigation_item_home:
                        mToolbar.setTitle("首页");
                        switchFragment(MainFragment.class);
                        break;
                    case R.id.navigation_item_user_information:
                        mToolbar.setTitle("个人信息");
                        switchFragment(UserInformationFragment.class);
                        break;
                    case R.id.navigation_item_user_data:
                        mToolbar.setTitle("个人资料");
                        switchFragment(UserInformationFragment.class);
                        break;
                    case R.id.navigation_item_order:
                        mToolbar.setTitle("预约挂号");
                        switchFragment(DoctorListFragment.class);
                        break;
                    case R.id.navigation_item_about:
                        mToolbar.setTitle("关于");
                        switchFragment(AboutFragment.class);
                        break;
                }
                item.setCheckable(true);
                //关闭抽屉
                mDrawerLayout.closeDrawer(Gravity.LEFT);
                mPreMenuItem = item;
                return false;
            }
        });
    }


    /**
     * function:切换Fragment
     * 1、commitAllowingStateLoss()--很像commit()但是其允许在保存活动的状态后执行提交。这是危险的，
     * 因为如果活动需要从其状态中恢复，则可能会丢失该提交，因此只有在UI状态可以意外地更改用户的情况下才可以使用。
     * 2、isAdded()--如果片段当前已添加到该活动中，则返回true。
     *
     * @param clazz
     */
    private void switchFragment(Class<?> clazz) {

        Fragment to = ViewUtil.createFragment(clazz);
        if (to.isAdded()) {
            mFragmentManager.beginTransaction().hide(mCurrentFragment).show(to).commitAllowingStateLoss();
        } else {
            mFragmentManager.beginTransaction().hide(mCurrentFragment).add(R.id.frame_content, to).commitAllowingStateLoss();
        }
        mCurrentFragment = to;
    }


    /**
     * function:监听-返回键
     */
    @Override
    public void onBackPressed() {

        //如果当前抽屉是打开的，则关闭
        if (mDrawerLayout.isDrawerOpen(Gravity.LEFT)) {
            mDrawerLayout.closeDrawer(Gravity.LEFT);
        }

    }
}
