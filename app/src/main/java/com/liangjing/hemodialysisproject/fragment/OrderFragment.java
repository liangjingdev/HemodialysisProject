package com.liangjing.hemodialysisproject.fragment;

import com.liangjing.hemodialysisproject.Base.BaseFragment;
import com.liangjing.hemodialysisproject.R;
import com.liangjing.unirecyclerviewlib.adapter.AdapterForRecyclerView;
import com.liangjing.unirecyclerviewlib.adapter.ViewHolderForRecyclerView;
import com.liangjing.unirecyclerviewlib.recyclerview.OptionRecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * function:预约排班
 */
public class OrderFragment extends BaseFragment {

    private OptionRecyclerView mRv;
    private AdapterForRecyclerView mAdapter;
    private List<String> list;

    @Override
    protected int setLayoutResourceID() {
        return R.layout.order_fragment_layout;
    }

    @Override
    protected void init() {
        list = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            list.add("10:20-12:2" + i);
        }
    }

    @Override
    protected void setUpView() {
        mRv = $(R.id.rv);
    }

    @Override
    protected void initEvents() {
        mAdapter = new AdapterForRecyclerView<String>(getmContext(), list, R.layout.item_order_class_layout) {
            @Override
            public void convert(ViewHolderForRecyclerView holder, String item, int position) {
                holder.setText(R.id.orderDate, item);
                holder.setText(R.id.timeQuantum, item);
                holder.setText(R.id.orderNumber, item);
                holder.setText(R.id.maxNumber, item);
            }
        };

        mRv.setAdapter(mAdapter);
    }
}
