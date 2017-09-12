package com.liangjing.hemodialysisproject.activity;

import android.os.Handler;

import com.liangjing.hemodialysisproject.Base.BaseActivity;
import com.liangjing.hemodialysisproject.R;

public class SplashActivity extends BaseActivity {


    @Override
    protected int setLayoutResourceID() {
        return R.layout.activity_splash_activity_layout;
    }


    @Override
    protected void setUpView() {

    }

    @Override
    protected void initEvents() {
        nextPage();
    }

    /**
     * function:跳转到主页面
     */
    private void nextPage() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivityWithoutExtras(UserMainActivity.class);
                finish();
            }
        },3000);
    }


}
