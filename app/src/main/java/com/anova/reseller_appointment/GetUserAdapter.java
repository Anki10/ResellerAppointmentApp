package com.anova.reseller_appointment;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.anova.reseller_appointment.View.AppConstant;
import com.anova.reseller_appointment.activity.DataGetActivity;
import com.anova.reseller_appointment.pojo.UserList;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GetUserAdapter extends RecyclerView.Adapter<GetUserAdapter.ViewHolder> {

    private Context mcontext;
    private ArrayList<UserList> user_list;


    public GetUserAdapter(Context context,ArrayList<UserList>list){
        this.mcontext = context;
        this.user_list = list;


    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.user_row_view, viewGroup, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

        UserList pojo = user_list.get(i);

     /*   if (pojo.getStatus().equalsIgnoreCase("calling")){
            viewHolder.iv_video_recived.setVisibility(View.VISIBLE);
        }else {
            viewHolder.iv_video_recived.setVisibility(View.GONE);
        }*/

        viewHolder.tv_hospital_main_name.setText(pojo.getUser_name());

        viewHolder.tv_status.setText(pojo.getStatus());

        long val = pojo.getChat_time() * 1000;
   /*     Date date=new Date(val);
        SimpleDateFormat df2 = new SimpleDateFormat("dd/MM/yy");
        String dateText = df2.format(date);
        System.out.println(dateText);*/

        String dateString = String.valueOf(createDate(val));

        viewHolder.tv_chat.setText(dateString);

        // first_time_call_status

        if (getFromPrefs(AppConstant.first_time_call_status) != null){
            if (getFromPrefs(AppConstant.first_time_call_status).equalsIgnoreCase("done")){
                viewHolder.btn_form.setVisibility(View.VISIBLE);
            }else {
                viewHolder.btn_form.setVisibility(View.GONE);
            }
        }

        viewHolder.btn_form.setOnClickListener((DataGetActivity)mcontext);
        viewHolder.btn_form.setTag(R.string.key_vendor,i);
    }

    public String getFromPrefs(String key) {
        SharedPreferences prefs = mcontext.getSharedPreferences(AppConstant.PREF_NAME, mcontext.MODE_PRIVATE);
        return prefs.getString(key, AppConstant.DEFAULT_VALUE);
    }


    public static CharSequence createDate(long timestamp) {
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(timestamp);
        Date d = c.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm");
        return sdf.format(d);
    }


    @Override
    public int getItemCount() {
        return user_list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_hospital_main_name)
        TextView tv_hospital_main_name;

        @BindView(R.id.tv_chat)
        TextView tv_chat;

        @BindView(R.id.tv_status)
        TextView tv_status;

        @BindView(R.id.ll_vendor_main)
        LinearLayout ll_vendor_main;

        @BindView(R.id.btn_form)
        ImageView btn_form;



        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }
    }
}
