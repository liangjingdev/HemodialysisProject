package com.liangjing.hemodialysisproject.loader;

import android.app.Activity;
import android.net.Uri;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.liangjing.hemodialysisproject.R;

import java.io.File;

/**
 * Created by liangjing on 2017/8/28.
 *
 * function:实现ImageLoader接口--实现图片加载策略--图片加载类
 */

public class UniImageLoader implements com.lzy.imagepicker.loader.ImageLoader {

    @Override
    public void displayImage(Activity activity, String path, ImageView imageView, int width, int height) {

        Glide.with(activity)
                .load(Uri.fromFile(new File(path)))  //设置图片路径(fix #8,文件名包含%符号 无法识别和显示)
                .error(R.mipmap.default_image)
                .placeholder(R.mipmap.default_image)
                .centerCrop()
                .override(width,height)
                .skipMemoryCache( true )
                .into(imageView);
    }

    @Override
    public void clearMemoryCache() {
        //这里是清除缓存的方法,根据需要自己实现
    }
}
