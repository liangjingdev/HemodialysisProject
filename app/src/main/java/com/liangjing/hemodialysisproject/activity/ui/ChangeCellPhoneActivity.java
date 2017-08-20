package com.liangjing.hemodialysisproject.activity.ui;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.liangjing.hemodialysisproject.Base.ToolBarActivity;
import com.liangjing.hemodialysisproject.R;

import static com.liangjing.hemodialysisproject.R.id.toolBarTitle;

public class ChangeCellPhoneActivity extends ToolBarActivity {

    private TextView mToolBarTitle;//设置toolBar上的title的文字信息.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_cell_phone_layout);
    }

    @Override
    public void onCreateCustomToolBar(Toolbar toolbar) {
        super.onCreateCustomToolBar(toolbar);

        //初始化toolBar的title布局，并将其依附到toolBar上。
        View view = getLayoutInflater().inflate(R.layout.toolbar_option, toolbar);

        //获取到设置titleText的TextView控件，然后设置title文字
        mToolBarTitle = (TextView) view.findViewById(toolBarTitle);
        mToolBarTitle.setText("手机号码");
    }

    @Override
    protected void setUpView() {

    }


    /**
     * function:返回按钮
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
