package com.liangjing.hemodialysisproject.listener;

import android.view.View;

/**
 * Created by liangjing on 2017/8/12.
 *
 * function:为RecyclerView中的item添加监听器
 */

public interface OnRecyclerItemClickListener {


    /**
     * function: item view 回调方法(自定义RecyclerView 中item view点击回调方法)
     * @param view 被点击的view
     * @param position 点击索引
     */
    void onItemClick(View view,int position);
}
