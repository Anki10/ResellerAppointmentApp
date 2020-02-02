package com.anova.reseller_appointment.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.anova.reseller_appointment.Interface.ListAdapterListener;
import com.anova.reseller_appointment.R;
import com.anova.reseller_appointment.pojo.ImageUploadPojo;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;


import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Ankit on 03-01-2019.
 */

public class ImageShowAdapter extends RecyclerView.Adapter<ImageShowAdapter.ViewHolder> {

    private ArrayList<ImageUploadPojo> image;
    private Context mcontext;
    private String from_name;
    private String activity_name;

    public ListAdapterListener mListener;

    public ImageShowAdapter(Context context, ArrayList<ImageUploadPojo>list, String from, String name,ListAdapterListener Listener){
        this.image = list;
        this.mcontext = context;
        from_name = from;
        this.activity_name = name;
        this.mListener = Listener;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_show_row_view, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        try {
            Glide.with(mcontext).load(image.get(position).getImage_path())
                    //           .thumbnail(0.5f)
                    .crossFade()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(holder.camera_view);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (image.get(position).getImage_status()){
            holder.iv_sync.setVisibility(View.GONE);
        }else {
            holder.iv_sync.setVisibility(View.VISIBLE);
        }

        holder.iv_sync.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mListener.onClickAtOKButton(image.get(position).getImage_path(),position,activity_name);
            }
        });

    }

    @Override
    public int getItemCount() {
        return image.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.camera_view)
        ImageView camera_view;

        @BindView(R.id.iv_sync)
        ImageView iv_sync;



        public ViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }
    }

}
