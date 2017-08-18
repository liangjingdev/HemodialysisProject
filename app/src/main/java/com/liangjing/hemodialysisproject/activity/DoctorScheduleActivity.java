package com.liangjing.hemodialysisproject.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.liangjing.hemodialysisproject.Base.BaseActivity;
import com.liangjing.hemodialysisproject.R;
import com.liangjing.hemodialysisproject.adapter.FragPagerAdapter;
import com.liangjing.hemodialysisproject.fragment.OrderFragment;
import com.liangjing.hemodialysisproject.fragment.QueryFragment;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class DoctorScheduleActivity extends BaseActivity implements AppBarLayout.OnOffsetChangedListener {

    private ArrayList<Fragment> mFragmentList;
    private ArrayList<String> mTitleList;
    private FragPagerAdapter mAdapter;
    private Bitmap bgBitmap;
    private Bitmap imgBitmap;
    private ViewPager mViewPager;
    private LinearLayout mHeadLayout;
    private TabLayout mTabLayout;
    private CollapsingToolbarLayout mCollapsingToolbarLayout;
    private CircleImageView mCircleImageView;
    private TextView nameTV;
    private TextView positionTV;
    private AppBarLayout mAppBarLayout;
    private Toolbar mToolbar;


    @Override
    protected int setLayoutResourceID() {
        return R.layout.doctor_schedule_activity_layout;
    }

    @Override
    protected void setUpView() {
        //初始化FragmentAdapter
        mAdapter = new FragPagerAdapter(getSupportFragmentManager(), mFragmentList, mTitleList);
        mCollapsingToolbarLayout = $(R.id.collapsing_toolbar_layout);
        mAppBarLayout = $(R.id.app_bar_layout);
        mToolbar = $(R.id.toolbar);
        mViewPager = $(R.id.view_pager);
        setSupportActionBar(mToolbar);
        mHeadLayout = $(R.id.doctor_layout);
        mTabLayout = $(R.id.toolbar_tab);
        mCircleImageView = $(R.id.doctor_img);
        nameTV = $(R.id.doctor_name);
        positionTV = $(R.id.doctor_position);
    }

    @Override
    protected void init() {
        //添加Fragment
        mFragmentList = new ArrayList<>();
        mFragmentList.add(new OrderFragment());
        mFragmentList.add(new QueryFragment());
        //添加Fragment对应的title
        mTitleList = new ArrayList<>();
        mTitleList.add("预约排班");
        mTitleList.add("医生排班");
    }

    @Override
    protected void initEvents() {
        //设置医生的头像、姓名、职位
        imgBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.avatar);
        mCircleImageView.setImageBitmap(imgBitmap);
        nameTV.setText("小军");
        positionTV.setText("主任医师");

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        mViewPager.setAdapter(mAdapter);
        //ViewPager的预加载管理：setOffscreenPageLimit
        mViewPager.setOffscreenPageLimit(1);

        //监听AppBarLayout
        mAppBarLayout.addOnOffsetChangedListener(this);
        mTabLayout.setupWithViewPager(mViewPager, true);
        mTabLayout.setTabsFromPagerAdapter(mAdapter);

        //设置appbar背景图
        bgBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.bg);
        mHeadLayout.setBackground(new BitmapDrawable(bgBitmap));
        //指定CollapsingToolbarLayout完全被滚出到屏幕外时的ColorDrawable
        mCollapsingToolbarLayout.setContentScrim(new BitmapDrawable(bgBitmap));

        //设置返回功能
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }


    /**
     * function:当AppBarLayout垂直方向上的偏移量发生改变时，会回调该接口.
     * 此时这个方法允许子view根据偏移量来实现自定义的行为.
     *
     * @param appBarLayout
     * @param verticalOffset
     */
    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
        if (verticalOffset <= -mHeadLayout.getHeight() / 2) {
            mCollapsingToolbarLayout.setTitle("小军医生");
        } else {
            mCollapsingToolbarLayout.setTitle(" ");
        }
    }
}

