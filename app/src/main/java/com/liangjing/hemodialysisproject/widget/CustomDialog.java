package com.liangjing.hemodialysisproject.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.liangjing.hemodialysisproject.utils.DimentionUtil;

/**
 * Created by liangjing on 2017/9/10.
 * <p>
 * function：自定义提示对话框
 *           采用Builder构建者模式
 */

public class CustomDialog extends Dialog {

    private Context mContext;
    private View mView; //自定义dialog的布局view
    private int mHeight;
    private int mWidth;
    private boolean mCancelTouchOut;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(mView);

        //处理Dialog呈现的自定义布局的大小
        Window win = getWindow();
        WindowManager.LayoutParams lp = win.getAttributes();
        lp.gravity = Gravity.CENTER;
        lp.height = mHeight;
        lp.width = mWidth;
        win.setAttributes(lp);

        //默认点击对话框外面的区域，不让对话框消失。
        setCancelable(mCancelTouchOut);

    }


    /**
     * function:默认风格dialog
     * @param builder
     */
    private CustomDialog(Builder builder) {
        super(builder.context);
        mContext = builder.context;
        mView = builder.view;
        mHeight = builder.height;
        mWidth = builder.width;
        mCancelTouchOut = builder.cancelTouchOut;
    }

    /**
     * function:自定义风格dialog
     * @param builder
     * @param resStyle
     */
    private CustomDialog(Builder builder, int resStyle) {
        super(builder.context, resStyle);
        mContext = builder.context;
        mHeight = builder.height;
        mWidth = builder.width;
        mCancelTouchOut = builder.cancelTouchOut;
        mView = builder.view;
    }


    public static final class Builder {
        private Context context;
        private View view;
        private int height;
        private int width;
        private boolean cancelTouchOut;
        private int resStyle = -1; //默认情况下自定义的dialog是默认风格

        public Builder(Context context) {
            this.context = context;
        }

        public Builder view(int view) {
            //初始化自定义dialog的布局view
            this.view = LayoutInflater.from(context).inflate(view, null);
            return this;
        }

        /**
         * function:传入px值
         * @param height
         * @return
         */
        public Builder heightPx(int height) {
            this.height = height;
            return this;
        }

        public Builder widthPx(int width) {
            this.width = width;
            return this;
        }

        /**
         * function:传入dp值
         * @param val
         * @return
         */
        public Builder heightdp(int val) {
            height = DimentionUtil.dip2px(context, val);
            return this;
        }

        public Builder widthdp(int val) {
            width = DimentionUtil.dip2px(context, val);
            return this;
        }

        /**
         * function:传入dimens.xml中指定的id
         * @param dimenRes
         * @return
         */
        public Builder heightDimenRes(int dimenRes) {
            height = context.getResources().getDimensionPixelOffset(dimenRes);
            return this;
        }

        public Builder widthDimenRes(int dimenRes) {
            width = context.getResources().getDimensionPixelOffset(dimenRes);
            return this;
        }

        public Builder style(int resStyle) {
            this.resStyle = resStyle;
            return this;
        }

        public Builder cancelTouchOut(boolean cancelTouchOut) {
            this.cancelTouchOut = cancelTouchOut;
            return this;
        }

        /**
         * function:添加点击事件(传入指定的view以及View.OnClickListener对象)
         * @param viewRes
         * @param listener
         * @return
         */
        public Builder addViewOnclick(int viewRes,View.OnClickListener listener){
            //根据id值拿到相应的子View，并设置监听功能
            view.findViewById(viewRes).setOnClickListener(listener);
            return this;
        }

        public CustomDialog build() {

            //首先判断是否传入style
            if (resStyle != -1) {
                return new CustomDialog(this,resStyle);
            } else {
                return new CustomDialog(this);
            }
        }
    }
}
