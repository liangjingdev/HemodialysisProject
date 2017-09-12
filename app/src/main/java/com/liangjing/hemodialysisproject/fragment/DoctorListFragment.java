package com.liangjing.hemodialysisproject.fragment;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.liangjing.hemodialysisproject.Base.BaseFragment;
import com.liangjing.hemodialysisproject.R;
import com.liangjing.hemodialysisproject.activity.DoctorDetailActivity;
import com.liangjing.hemodialysisproject.db.DbUtil;
import com.liangjing.hemodialysisproject.entity.DoctorEntity;
import com.liangjing.unirecyclerviewlib.adapter.AdapterForRecyclerView;
import com.liangjing.unirecyclerviewlib.adapter.ViewHolderForRecyclerView;
import com.liangjing.unirecyclerviewlib.recyclerview.OptionRecyclerView;
import com.mancj.materialsearchbar.MaterialSearchBar;
import com.mancj.materialsearchbar.adapter.SuggestionsAdapter;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.liangjing.hemodialysisproject.R.id.doctorName;

/**
 * function:可进行预约的医生列表
 */
public class DoctorListFragment extends BaseFragment {

    private OptionRecyclerView mRecyclerView;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private Handler handler;
    private List<DoctorEntity> mEntity; //所有医生集合
    private AdapterForRecyclerView mAdapter;
    private MaterialSearchBar mSearchBar;
    private Bundle mBundle;

    @Override
    protected int setLayoutResourceID() {
        return R.layout.fragment_doctor_list_layout;
    }

    @Override
    protected void init() {
        mEntity = DbUtil.getDoctorEntityHelper().queryAll();
        mBundle = new Bundle();
        handler = new Handler();
        mSearchBar = (MaterialSearchBar) getContentView().findViewById(R.id.searchBar);
    }


    @Override
    protected void setUpView() {
        mRecyclerView = $(R.id.rv);
        mSwipeRefreshLayout = $(R.id.swipeRefreshLayout);
    }


    @Override
    protected void initEvents() {

        //为搜索框设置监听功能--监听用户输入的内容
        mSearchBar.setOnSearchActionListener(new MaterialSearchBar.OnSearchActionListener() {
            @Override
            public void onSearchStateChanged(boolean enabled) {

            }

            @Override
            public void onSearchConfirmed(CharSequence text) {

                //首先需要判断用户输入的内容是否为空(包括空格字符)
                if (isBlank(text)) {
                    Toast.makeText(getActivity(), "输入有误，请重新输入！", Toast.LENGTH_SHORT).show();
                } else {
                    if (isDoctorExist(text.toString())) {
                        //首先判断该医生是否存在，若存在那么就去拿到相应的实体类接着去获取其个人资料
                        DoctorEntity doctorEntity = obtainDoctorData(text.toString());
                        mBundle = obtainBundle(doctorEntity);
                        startActivityWithExtras(DoctorDetailActivity.class, mBundle);
                    } else {
                        Toast.makeText(getActivity(), "该医生不存在！", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onButtonClicked(int buttonCode) {

            }
        });


        //为搜索框的建议列表进行监听
        mSearchBar.setSuggstionsClickListener(new SuggestionsAdapter.OnItemViewClickListener() {
            @Override
            public void OnItemClickListener(int position, View v) {
                if (isDoctorExist((String) v.getTag())) {
                    DoctorEntity doctorEntity = obtainDoctorData((String) v.getTag());
                    mBundle = obtainBundle(doctorEntity);
                    startActivityWithExtras(DoctorDetailActivity.class, mBundle);
                } else {
                    Toast.makeText(getmContext(), "该医生不存在！", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void OnItemDeleteListener(int position, View v) {
            }
        });


        //获取AdapterForRecyclerView对象并且对item进行填充数据
        mAdapter = new AdapterForRecyclerView<DoctorEntity>(getmContext(), mEntity, R.layout.item_doctor_layout) {
            @Override
            public void convert(ViewHolderForRecyclerView holder, DoctorEntity doctorEntity, int position) {
                //item布局所对应的view
                View view = LayoutInflater.from(getmContext()).inflate(R.layout.item_doctor_layout, null);
                //圆形头像
                CircleImageView imageView = (CircleImageView) view.findViewById(R.id.doctorImg);
                if (doctorEntity.getDoctorHeadPortrait() != null) {
                    Glide.with(getActivity()).load(doctorEntity.getDoctorHeadPortrait()).centerCrop().into(imageView);
                } else {
                    holder.setImageBitmap(R.id.doctorImg, BitmapFactory.decodeResource(getResources(), R.drawable.avatar));
                }
                holder.setText(doctorName, "姓名：" + doctorEntity.getDoctorRealName());
            }
        };


        //设置适配器
        mRecyclerView.setAdapter(mAdapter);

        //设置刷新时动画的颜色，可以设置4个
        mSwipeRefreshLayout.setProgressBackgroundColorSchemeResource(android.R.color.white);
        mSwipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_light,
                android.R.color.holo_red_light, android.R.color.holo_orange_light,
                android.R.color.holo_green_light);
        mSwipeRefreshLayout.setProgressViewOffset(false, 0, (int) TypedValue
                .applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24, getResources()
                        .getDisplayMetrics()));

        //设置下拉刷新功能 -- SwipeRefreshLayout
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                //可进行网络请求获取最新数据
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mSwipeRefreshLayout.setRefreshing(false);
                        Toast.makeText(getmContext(), "已更新...", Toast.LENGTH_SHORT).show();
                    }
                }, 5000);
            }
        });

        mAdapter.setOnItemClickListener((optionViewHolder, viewGroup, view, i) -> {
            DoctorEntity doctorEntity = mEntity.get(i);
            mBundle = obtainBundle(doctorEntity);
            startActivityWithExtras(DoctorDetailActivity.class, mBundle);
        });
    }


    /**
     * 判断字符串是否为空（空格字符串也是blank）
     *
     * @param s
     * @return
     */
    public static boolean isBlank(final CharSequence s) {
        if (s == null) {
            return true;
        }
        for (int i = 0; i < s.length(); i++) {
            if (!Character.isWhitespace(s.charAt(i))) {
                return false;
            }
        }
        return true;
    }


    /**
     * function:判断用户所输入的医生的姓名是否存在于数据库中，若存在，则返回true，若不存在,则返回false(未考虑到医生同名的情况)
     *
     * @param doctorRealName
     * @return
     */
    private boolean isDoctorExist(String doctorRealName) {

        for (int i = 0; i < mEntity.size(); i++) {
            if (doctorRealName.equals(mEntity.get(i).getDoctorRealName()))
            return true;
        }
        return false;
    }

    /**
     * function:传入医生姓名，然后在数据库中找到对应的医生，接着返回该医生的实体类，供调用处获取该医生的资料
     */
    private DoctorEntity obtainDoctorData(String doctorRealName) {

        for (int i = 0; i < mEntity.size(); i++) {
            if (doctorRealName.equals(mEntity.get(i).getDoctorRealName())) {
                return mEntity.get(i);
            }
        }
        return null;
    }


    /**
     * function:从该医生实体类中拿出其个人资料，然后将其存进Bundle中，以便传递去给其它activity
     *
     * @param doctorEntity
     * @return
     */
    private Bundle obtainBundle(DoctorEntity doctorEntity) {
        Bundle bundle = new Bundle();
        bundle.putString("doctorRealName", doctorEntity.getDoctorRealName());
        bundle.putByteArray("doctorImage", doctorEntity.getDoctorHeadPortrait());
        return bundle;
    }

}
