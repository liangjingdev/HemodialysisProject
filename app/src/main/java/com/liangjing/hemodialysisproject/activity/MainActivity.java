package com.liangjing.hemodialysisproject.activity;

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
import com.liangjing.hemodialysisproject.entity.PatientSchemeEntity;
import com.liangjing.hemodialysisproject.entity.UserEntity;
import com.liangjing.hemodialysisproject.fragment.AboutFragment;
import com.liangjing.hemodialysisproject.fragment.CycleScheduleFragment;
import com.liangjing.hemodialysisproject.fragment.DoctorListFragment;
import com.liangjing.hemodialysisproject.fragment.MainFragment;
import com.liangjing.hemodialysisproject.fragment.AppointedListFragment;
import com.liangjing.hemodialysisproject.fragment.UserDataFragment;
import com.liangjing.hemodialysisproject.utils.PatientSchemaUtil;
import com.liangjing.hemodialysisproject.utils.UserDataUtil;
import com.liangjing.hemodialysisproject.utils.ViewUtil;

public class MainActivity extends BaseActivity {

    private DrawerLayout mDrawerLayout; //侧边菜单视图
    private ActionBarDrawerToggle mDrawerToggle;  //菜单开关
    private Toolbar mToolbar;
    private NavigationView mNavigationView;//侧边菜单项
    private FragmentManager mFragmentManager;
    private Fragment mCurrentFragment;//当前的Fragment
    private MenuItem mPreMenuItem; //当前选中的menu item


    /**
     * function:设置当前视图的id
     *
     * @return
     */
    @Override
    protected int setLayoutResourceID() {
        return R.layout.activity_main_layout;
    }


    /**
     * function:初始化view
     */
    @Override
    protected void setUpView() {
        mToolbar = $(R.id.toolbar);
        mDrawerLayout = $(R.id.drawer_layout);
        mNavigationView = $(R.id.navigationView);

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
        //测试：添加数据进入数据库
        addDataToDb();
    }

    private void addDataToDb() {
        //填充一个用户资料进入数据库(测试)
        UserEntity entity = new UserEntity();
        entity.setId((long) 1);
        entity.setCellPhone("2312");
        entity.setUserName("哈哈");
        entity.setRealName("小明");
        entity.setDiagnosisNumber("20");
        entity.setUserLocation("点解啊开封警方吗切记哦去");
        entity.setIdNumber("27489239393");
        entity.setUserBirthday("1989-1-21");
        entity.setUserGender("男");
        UserDataUtil.addToDb(entity);

        PatientSchemeEntity entity1 = new PatientSchemeEntity();
        entity1.setSchemeId(entity.getId());
        entity1.setDialysisScheme("HD");
        entity1.setDialysisTime("2017年11月2日至11月8日");
        entity1.setDoctorName("小军");
        entity1.setPatientName(entity.getRealName());
        entity1.setLocation("广东省广州市某某医院");
        entity1.setMachineNumber("78");
        PatientSchemaUtil.addToDb(entity1);

        PatientSchemeEntity entity2 = new PatientSchemeEntity();
        entity2.setSchemeId(entity.getId());
        entity2.setDialysisScheme("HD");
        entity2.setDialysisTime("2017年12月2日至12月8日");
        entity2.setDoctorName("小军");
        entity2.setPatientName(entity.getRealName());
        entity2.setLocation("广东省广州市某某医院");
        entity2.setMachineNumber("99");
        PatientSchemaUtil.addToDb(entity2);
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
                    case R.id.navigation_item_user_data:
                        mToolbar.setTitle("个人资料");
                        switchFragment(UserDataFragment.class);
                        break;
                    case R.id.navigation_item_order:
                        mToolbar.setTitle("预约挂号");
                        switchFragment(DoctorListFragment.class);
                        break;
                    case R.id.navigation_order_schedule:
                        mToolbar.setTitle("预约排班");
                        switchFragment(AppointedListFragment.class);
                        break;
                    case R.id.navigation_patient_scheme:
                        mToolbar.setTitle("个人周期排班信息");
                        switchFragment(CycleScheduleFragment.class);
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


}
