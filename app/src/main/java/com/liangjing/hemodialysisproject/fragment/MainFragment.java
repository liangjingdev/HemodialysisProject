package com.liangjing.hemodialysisproject.fragment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.liangjing.hemodialysisproject.Base.BaseFragment;
import com.liangjing.hemodialysisproject.R;
import com.zhouwei.mzbanner.MZBannerView;
import com.zhouwei.mzbanner.holder.MZHolderCreator;
import com.zhouwei.mzbanner.holder.MZViewHolder;

import java.util.ArrayList;

/**
 * function:主页面Fragment
 */
public class MainFragment extends BaseFragment {

    private MZBannerView mMZBanner;
    private ArrayList<Integer> list;

    @Override
    protected int setLayoutResourceID() {
        return R.layout.fragment_main_layout;
    }

    @Override
    protected void setUpView() {
        //获取轮播图对象
        mMZBanner = (MZBannerView) getContentView().findViewById(R.id.banner);
    }

    @Override
    protected void init() {
        list = new ArrayList<>();
        list.add(R.drawable.bg1);
        list.add(R.drawable.bg2);
        list.add(R.drawable.bg3);
    }

    @Override
    protected void initEvents() {

        //设置轮播图数据
        mMZBanner.setPages(list, new MZHolderCreator() {
            @Override
            public MZViewHolder createViewHolder() {
                return new BannerViewHolder();
            }
        });

    }

    public static class BannerViewHolder implements MZViewHolder<Integer> {

        private ImageView mImageView;

        @Override
        public View createView(Context context) {
            //返回页面布局
            View view = LayoutInflater.from(context).inflate(R.layout.item_banner_layout, null);
            mImageView = (ImageView) view.findViewById(R.id.banner_image);
            return view;
        }

        @Override
        public void onBind(Context context, int position, Integer data) {
            //数据绑定
            mImageView.setImageResource(data);
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        //开始轮播
        mMZBanner.start();
    }

    @Override
    public void onPause() {
        super.onPause();
        //停止轮播
        mMZBanner.pause();
    }
}
