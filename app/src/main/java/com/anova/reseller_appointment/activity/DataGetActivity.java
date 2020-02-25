package com.anova.reseller_appointment.activity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.anova.reseller_appointment.GetUserAdapter;
import com.anova.reseller_appointment.R;
import com.anova.reseller_appointment.View.AppConstant;
import com.anova.reseller_appointment.api.APIService;
import com.anova.reseller_appointment.api.ApiUtils;
import com.anova.reseller_appointment.opentalk.OpenTokConfig;
import com.anova.reseller_appointment.pojo.CallDisconnectRequest;
import com.anova.reseller_appointment.pojo.CallDisconnectResponse;
import com.anova.reseller_appointment.pojo.GetUserResponse;
import com.anova.reseller_appointment.pojo.PostVideoRequest;
import com.anova.reseller_appointment.pojo.UserCallResponse;
import com.anova.reseller_appointment.pojo.UserList;
import com.anova.reseller_appointment.util.AppDialog;
import com.anova.reseller_appointment.util.ConnectionDetector;

import java.io.IOException;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DataGetActivity extends BaseActivity implements View.OnClickListener {

    private APIService mAPIService;

    Boolean isInternetPresent = false;
    // Connection detector class
    ConnectionDetector cd;

    @BindView(R.id.list_recycler_view)
    RecyclerView list_recycler_view;

    private RecyclerView.LayoutManager mLayoutManager;
    private GetUserAdapter adapter;

    private ArrayList<UserList>userlist;

    private final int FIVE_SECONDS = 5000;

    final Handler handler = new Handler();

    ProgressDialog d;

    private Context context = DataGetActivity.this;

    private Dialog dialogLogout;

    private MediaPlayer mPlayer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_get);

        ButterKnife.bind(this);

        setDrawerAndToolbar("Assessor Schedule");

        mAPIService = ApiUtils.getAPIService();

        cd = new ConnectionDetector(getApplicationContext());
        // get Internet status
        isInternetPresent = cd.isConnectingToInternet();

        userlist = new ArrayList<>();

        mLayoutManager = new LinearLayoutManager(DataGetActivity.this);
        list_recycler_view.setLayoutManager(mLayoutManager);

        getUserList();

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //Do something after 20 seconds
                getUserList();
                handler.postDelayed(this, 10000);
            }
        }, 10000);

    }

    public String getFromPrefs(String key) {
        SharedPreferences prefs = getSharedPreferences(AppConstant.PREF_NAME, MODE_PRIVATE);
        return prefs.getString(key, AppConstant.DEFAULT_VALUE);
    }

    private void getUserList(){

        mAPIService.getUserList(getFromPrefs("token_id")).enqueue(new Callback<GetUserResponse>() {
            @Override
            public void onResponse(Call<GetUserResponse> call, Response<GetUserResponse> response) {
                if (response.body() != null){

                    if (response.body().getSuccess()){
                        userlist = response.body().getAll_user_data();

                        if (userlist != null){
                            if (userlist.size() > 0){
                                adapter = new GetUserAdapter(DataGetActivity.this,userlist);
                                list_recycler_view.setAdapter(adapter);
                            }
                        }
                        try {
                            for (int i=0;i<userlist.size();i++){
                                if (userlist.get(i).getForm_id() != null){
                                    if (userlist.get(i).getStatus().equalsIgnoreCase("calling")) {

                                        CallRecivedDialog(i);

                                        break;
                                    }
                                }
                            }
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<GetUserResponse> call, Throwable t) {
                System.out.println("xx faill");

            }
        });
    }

    public void callStart(int i){

        OpenTokConfig.API_KEY = userlist.get(i).getApi_key();
        OpenTokConfig.SESSION_ID = userlist.get(i).getSession_id();
        OpenTokConfig.TOKEN = userlist.get(i).getToken();

        saveIntoPrefs(AppConstant.CHAT_TIME, String.valueOf(userlist.get(i).getChat_time()));

        saveIntoPrefs("form_id",userlist.get(i).getForm_id());

        Intent intent = new Intent(DataGetActivity.this, VideoAssessmentActivity.class);
        startActivity(intent);


    }

    private void PostVendorCall(int position ){

        final ProgressDialog d = AppDialog.showLoading(DataGetActivity.this);
        d.setCanceledOnTouchOutside(false);

        PostVideoRequest request = new PostVideoRequest();
        request.setCalling_user_id(userlist.get(position).getUser_id());
        request.setChat_time(userlist.get(position).getChat_time());
        request.setForm_id(userlist.get(position).getForm_id());
        request.setType(getFromPrefs("seller_assessed_as"));

        saveIntoPrefs("form_id",userlist.get(position).getForm_id());




        mAPIService.PostUserCall(getFromPrefs("token_id"),request).enqueue(new Callback<UserCallResponse>() {
            @Override
            public void onResponse(Call<UserCallResponse> call, Response<UserCallResponse> response) {
                d.dismiss();
                if (response.body() != null){
                    if (response.body().getSuccess()){

                        OpenTokConfig.API_KEY = response.body().getApi_key();
                        OpenTokConfig.SESSION_ID = response.body().getSession_id();
                        OpenTokConfig.TOKEN = response.body().getToken();

                 /*       saveIntoPrefs(AppConstant.API_KEY,response.body().getApi_key());
                        saveIntoPrefs(AppConstant.SESSION_ID,response.body().getSession_id());
                        saveIntoPrefs(AppConstant.Token,response.body().getToken());*/


                        handler.removeCallbacksAndMessages(null);
                        handler.removeCallbacksAndMessages(null);


                        Intent intent = new Intent(DataGetActivity.this, VideoAssessmentActivity.class);
                        startActivity(intent);


                    }
                }
            }

            @Override
            public void onFailure(Call<UserCallResponse> call, Throwable t) {
               d.dismiss();
            }
        });
    }

    @Override
    public void onClick(View v) {
       switch (v.getId()){
           case R.id.btn_form:

               int position = (int) v.getTag(R.string.key_vendor);

               Intent intent = new Intent(DataGetActivity.this,FormActivity.class);
               startActivity(intent);

//               saveIntoPrefs(AppConstant.CHAT_TIME, String.valueOf(userlist.get(position).getChat_time()));
//
//               PostVendorCall(position);


               break;
       }
    }

    public void saveIntoPrefs(String key, String value) {
        SharedPreferences prefs = getSharedPreferences(AppConstant.PREF_NAME, MODE_PRIVATE);
        SharedPreferences.Editor edit = prefs.edit();
        edit.putString(key, value);
        edit.commit();
    }

    @Override
    protected void onStop() {
        super.onStop();

        handler.removeCallbacksAndMessages(null);
        handler.removeCallbacksAndMessages(null);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        finish();

    }

    public void playRingtone() {
        AudioManager audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

        // Honour silent mode
        switch (audioManager.getRingerMode()) {
            case AudioManager.RINGER_MODE_NORMAL:
                mPlayer = new MediaPlayer();
                mPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);

                try {
                    mPlayer.setDataSource(DataGetActivity.this,
                            Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.phone_loud1));
                    mPlayer.prepare();
                } catch (IOException e) {
            //        Log.e(LOG_TAG, "Could not setup media player for ringtone");
                    mPlayer = null;
                    return;
                }
                mPlayer.setLooping(true);
                mPlayer.start();
                break;
        }
    }

    public void stopRingtone() {
        if (mPlayer != null) {
            mPlayer.stop();
            mPlayer.release();
            mPlayer = null;
        }
    }

    private void CallRecivedDialog(final int i){

        handler.removeCallbacksAndMessages(null);
        handler.removeCallbacksAndMessages(null);

        playRingtone();


        dialogLogout = new Dialog(DataGetActivity.this, android.R.style.Theme_Translucent_NoTitleBar);
        dialogLogout.setContentView(R.layout.image_list_dialog);

       TextView tv_person_name = (TextView) dialogLogout.findViewById(R.id.tv_person_name);

        Button btn_decline = (Button)dialogLogout.findViewById(R.id.btn_decline);

        Button btn_answer = (Button) dialogLogout.findViewById(R.id.btn_answer);

        tv_person_name.setText(userlist.get(i).getUser_name());

        dialogLogout.show();

        btn_decline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopRingtone();
                PostCallDiscoonect(userlist.get(i).getSession_id(),userlist.get(i).getChat_time(),userlist.get(i).getForm_id());
                dialogLogout.dismiss();

                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //Do something after 20 seconds
                        getUserList();
                        handler.postDelayed(this, 10000);
                    }
                }, 10000);
            }
        });

        btn_answer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopRingtone();
                dialogLogout.dismiss();
                callStart(i);
            }
        });
    }

    private void PostCallDiscoonect(String session_id,long chat_time,String form_id){
        CallDisconnectRequest request = new CallDisconnectRequest();
        request.setSession_id(session_id);
        request.setEpoch(chat_time);
        request.setForm_id(form_id);
        request.setType(getFromPrefs("seller_assessed_as"));


        mAPIService.PostCallDisconnect(getFromPrefs("token_id"),request).enqueue(new Callback<CallDisconnectResponse>() {
            @Override
            public void onResponse(Call<CallDisconnectResponse> call, Response<CallDisconnectResponse> response) {

                if (response.body() != null){
                    if (response.body().getSuccess()){
                        System.out.println("xx sucess");
                    }
                }
            }

            @Override
            public void onFailure(Call<CallDisconnectResponse> call, Throwable t) {
                System.out.println("xx fail");

            }
        });
    }
}
