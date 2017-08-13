package com.liangjing.hemodialysisproject.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.liangjing.hemodialysisproject.R;
import com.liangjing.hemodialysisproject.bean.DoctorBean;
import com.liangjing.hemodialysisproject.listener.OnRecyclerItemClickListener;
import com.liangjing.hemodialysisproject.utils.CardDataUtil;

import java.util.List;

/**
 * Created by liangjing on 2017/8/12.
 * <p>
 * function:CardView + RecyclerView + SwipeRefreshLayout(自定义Adapter--为一项item创建视图并且绑定数据)
 */

public class CardViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_ITEM = 0;  //普通Item View
    private static final int TYPE_FOOTER = 1;  //底部FootView

    //上拉加载更多
    public static final int PULL_UP_LOAD_MORE = 0;
    //正在加载中
    public static final int LOADING_MORE = 1;
    //上拉加载更多状态-默认为0
    private int load_more_status = 0;

    private List<DoctorBean> mBeans;
    private LayoutInflater mInflater;
    private View view;
    private View footView;
    private Context mContext;
    private OnRecyclerItemClickListener onRecyclerItemClickListener;


    public CardViewAdapter(Context mContext, OnRecyclerItemClickListener onRecyclerItemClickListener) {
        this.mContext = mContext;
        this.onRecyclerItemClickListener = onRecyclerItemClickListener;
        mBeans = CardDataUtil.getCardViewDatas();
        mInflater = LayoutInflater.from(mContext);
    }

    /**
     * function: item显示类型
     *
     * @param parent
     * @param viewType
     * @return
     */
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        //进行判断显示类型，来创建返回不同的view
        if (viewType == TYPE_ITEM) {
            view = mInflater.inflate(R.layout.doctor_item_layout, parent, false);
            //这边可以做一些属性设置，甚至事件监听绑定
            initEvents();
            ItemCardViewHolder itemCardViewHolder = new ItemCardViewHolder(view);
            return itemCardViewHolder;
        } else if (viewType == TYPE_FOOTER) {
            footView = mInflater.inflate(R.layout.recycler_load_more_layout, parent, false);
            //这边可以做一些属性设置，甚至事件监听绑定
            FootViewHolder footViewHolder = new FootViewHolder(footView);
            return footViewHolder;
        }
        return null;
    }


    /**
     * function:初始化事件
     */
    private void initEvents() {
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //第二个参数position有getTag()来获取，那么需要在onBindViewHolder()方法中设置一下tag.
                onRecyclerItemClickListener.onItemClick(view, (int) view.getTag());
            }
        });
    }

    /**
     * function:数据的绑定显示
     *
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        //进行判断--对相应的ViewHolder绑定相应的数据显示
        if (holder instanceof ItemCardViewHolder) {
            ((ItemCardViewHolder) holder).mNameTv.setText(mBeans.get(position).getDoctorName());
            ((ItemCardViewHolder) holder).mIntroTv.setText(mBeans.get(position).getDoctorIntro());
            ((ItemCardViewHolder) holder).itemView.setTag(position);
        } else if (holder instanceof FootViewHolder) {
            //强制类型转换(引用类型之间的转换只能在具有继承关系的两个类型之间进行)
            FootViewHolder footViewHolder = (FootViewHolder) holder;
            switch (load_more_status) {
                case PULL_UP_LOAD_MORE:
                    footViewHolder.foot_view_item_tv.setText("上拉加载更多...");
                    break;
                case LOADING_MORE:
                    footViewHolder.foot_view_item_tv.setText("正在加载更多数据...");
                    break;
            }
        }
    }


    @Override
    public int getItemCount() {
        //因为添加的底部footView,所以这里需要+1
        return mBeans.size() + 1;
    }


    /**
     * function:自定义的ViewHolder，持有每个Item的的所有界面元素(普通item)
     */
    public static class ItemCardViewHolder extends RecyclerView.ViewHolder {

        private TextView mNameTv;
        private TextView mIntroTv;

        public ItemCardViewHolder(View itemView) {
            super(itemView);
            mNameTv = (TextView) itemView.findViewById(R.id.doctor_name);
            mIntroTv = (TextView) itemView.findViewById(R.id.doctor_intro);
        }
    }


    /**
     * function:刷新数据(下拉刷新)
     *
     * @param beans
     */
    public void addItem(List<DoctorBean> beans) {
        beans.addAll(mBeans);
        mBeans.removeAll(mBeans);
        mBeans.addAll(beans);
        notifyDataSetChanged();
    }

    /**
     * function:增加数据(上拉加载)
     *
     * @param beans
     */
    public void addMoreItem(List<DoctorBean> beans) {
        mBeans.addAll(beans);
        notifyDataSetChanged();
    }

    /**
     * function:重写getItemViewType方法来判断返回需要加载的布局的类型
     *
     * @param position
     * @return
     */
    @Override
    public int getItemViewType(int position) {
        // 最后一个item设置为footerView
        if (position + 1 == getItemCount()) {
            return TYPE_FOOTER;
        } else {
            return TYPE_ITEM;
        }
    }


    /**
     * function:底部FootView布局
     */
    public static class FootViewHolder extends RecyclerView.ViewHolder {

        private TextView foot_view_item_tv;

        public FootViewHolder(View itemView) {
            super(itemView);
            foot_view_item_tv = (TextView) itemView.findViewById(R.id.foot_view_item_tv);
        }
    }


    /**
     * 上拉加载更多
     * PULLUP_LOAD_MORE=0;
     * 正在加载中
     * LOADING_MORE=1;
     * 加载完成已经没有更多数据了
     * NO_MORE_DATA=2;
     * <p>
     * function:更改上拉加载更多的状态(根据是否还有数据来显示相应的信息)
     *
     * @param status
     */
    public void changeStatus(int status) {
        this.load_more_status = status;
        notifyDataSetChanged();
    }
}
