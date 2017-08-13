package com.liangjing.hemodialysisproject.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.liangjing.hemodialysisproject.R;

/**
 * Created by liangjing on 2017/8/12.
 *
 */

public class AppointmentAdapter extends RecyclerView.Adapter<AppointmentAdapter.ViewHolder> implements View.OnClickListener {

    @Override
    public int getItemCount() {
        return 10;
    }

    @Override
    public void onClick(final View v) {

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.gteat_item, parent, false);
        v.setOnClickListener(this);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.text.setText("Test");

    }

    protected static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView text;
        public FrameLayout container;

        public ViewHolder(View itemView) {
            super(itemView);
            text = (TextView) itemView.findViewById(R.id.text);
            container = (FrameLayout) itemView.findViewById(R.id.container);
        }
    }

}
