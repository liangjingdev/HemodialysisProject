package com.liangjing.hemodialysisproject.activity.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.liangjing.hemodialysisproject.Base.ToolBarActivity;
import com.liangjing.hemodialysisproject.R;
import com.liangjing.hemodialysisproject.db.DbUtil;
import com.liangjing.hemodialysisproject.db.UserEntityHelper;
import com.liangjing.hemodialysisproject.entity.UserEntity;

import static com.liangjing.hemodialysisproject.R.id.toolBarTitle;
import static com.liangjing.hemodialysisproject.R.id.userEmail;

public class ChangeUserEmailActivity extends ToolBarActivity implements View.OnClickListener {


    private TextView mToolBarTitle;//设置toolBar上的title的文字信息.
    private Button saveButton;
    private EditText etUserEmail;
    private Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_user_email_layout);
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
        mToolBarTitle.setText("邮箱");
        saveButton = (Button) view.findViewById(R.id.save);
        saveButton.setVisibility(View.VISIBLE);
    }


    @Override
    protected void init() {
        //获取Bundle
        Intent intent = getIntent();
        bundle = intent.getExtras();
    }

    @Override
    protected void setUpView() {
        etUserEmail = $(userEmail);
    }


    @Override
    protected void initEvents() {
        //设置数据
        etUserEmail.setText(bundle.getString("userEmail"));
        //将光标移动到尾部
        etUserEmail.setSelection(bundle.getString("userEmail") != null ? bundle.getString("userEmail").length() : 0);

        //保存按钮监听
        saveButton.setOnClickListener(this);

        //设置EditText监听
        etUserEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //判断输入框中的字符是否为空，若不为空，才让保存按钮启动。(空格也不行，必须要有字符才能够启动按钮功能)
                if (etUserEmail.getText().toString().trim().length() > 0) {
                    saveButton.setEnabled(true);
                } else {
                    saveButton.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.save:
                String userEmail = etUserEmail.getText().toString().trim();

                //修改该用户在数据库中的用户名
                UserEntityHelper helper = DbUtil.getUserEntityHelper();
                UserEntity entity = helper.query((long) 1);
                entity.setUserEmail(userEmail);
                //更新该用户的数据到数据库中
                helper.update(entity);

                //传送数据会给UserDataFragment
                Intent intent = new Intent();
                intent.putExtra("userEmail", userEmail);
                this.setResult(6, intent);

                //结束该Activity
                this.finish();
                break;
        }
    }

}
