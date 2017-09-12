package com.liangjing.hemodialysisproject.activity.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.bm.library.PhotoView;
import com.bumptech.glide.Glide;
import com.liangjing.hemodialysisproject.Base.ToolBarActivity;
import com.liangjing.hemodialysisproject.R;

import static com.liangjing.hemodialysisproject.R.id.toolBarTitle;

public class ShowBigImageActivity extends ToolBarActivity {

    private byte[] mByte;
    private PhotoView mPv;
    private TextView mToolBarTitle;
    private Bundle bundle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_big_image_layout);
    }

    @Override
    protected void init() {
        //获取Bundle
        Intent intent = getIntent();
        bundle = intent.getExtras();
        mByte = bundle.getByteArray("headPortrait");
    }

    @Override
    protected void setUpView() {

        mPv = $(R.id.pv);
    }


    @Override
    protected void initEvents() {
        //首先判断传递过来的字节数组是否为空，若为空则显示默认的头像，若不为空则将显示用户设置的头像
        if (mByte == null) {
            //启用图片缩放功能
            mPv.enable();
            //Glide加载默认的头像
            Glide.with(this).load(R.drawable.avatar).dontTransform().into(mPv);
        } else {
            //启用图片缩放功能
            mPv.enable();
            //Glide加载用户设置的头像
            Glide.with(this).load(mByte).dontTransform().into(mPv);
        }
    }


    /**
     * function:创建Toolbar
     *
     * @param toolbar
     */
    @Override
    public void onCreateCustomToolBar(Toolbar toolbar) {
        super.onCreateCustomToolBar(toolbar);

        //初始化toolBar的title布局，并将其依附到toolBar上。
        View view = getLayoutInflater().inflate(R.layout.toolbar_option, toolbar);

        //获取到设置titleText的TextView控件，然后设置title文字信息
        mToolBarTitle = (TextView) view.findViewById(toolBarTitle);
        mToolBarTitle.setText("头像");
    }

    /**
     * function:返回按钮
     *
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
