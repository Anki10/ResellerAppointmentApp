package com.anova.reseller_appointment.fragment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.anova.reseller_appointment.R;
import com.anova.reseller_appointment.View.AppConstant;
import com.anova.reseller_appointment.opentalk.BasicCustomVideoRenderer;

public class VideoFragment extends BaseFragment {

    // TODO: Rename and change types of parameters


    private Button btn_endCall,btn_STARTCall;

    ImageView iv_endCall;
    ImageView iv_PauseCall;
    ImageView iv_flipCall;


    // Suppressing this warning. mWebServiceCoordinator will get GarbageCollected if it is local.
    @SuppressWarnings("FieldCanBeLocal")


    public static FrameLayout mPublisherViewContainer;
    public static FrameLayout mSubscriberViewContainer;



    private Boolean call_status = false;

    private View view;

    private static VideoFragment instance = null;

    private Boolean audio_status = false;

    private Boolean call_status_now = false;

    private TextView tv_form;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        instance = this;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_video, container, false);

        // initialize view objects from your layout
        mPublisherViewContainer = (FrameLayout)view.findViewById(R.id.publisher_container);
        mSubscriberViewContainer = (FrameLayout)view.findViewById(R.id.subscriber_container);


        iv_endCall = (ImageView) view.findViewById(R.id.iv_endCall);
        iv_PauseCall = (ImageView) view.findViewById(R.id.iv_PauseCall);

        iv_flipCall = (ImageView) view.findViewById(R.id.iv_flipCall);

        tv_form = (TextView) view.findViewById(R.id.tv_form);

        tv_form.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               /* call_status_change();

                PostCallDiscoonect();*/

               if (mPublisher != null){
                   mPublisher.setPublishVideo(false);
               }

               if (mSubscriber != null){
                   mSubscriber.setSubscribeToVideo(false);
               }


         /*       mPublisher.setPublishAudio(true);
                mSubscriber.setSubscribeToAudio(true);*/

                final FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.fragment1, new AssessmentFragment(), "Assessment Fragment");
                ft.addToBackStack(null);
                ft.commit();
            }
        });

        iv_flipCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPublisher.cycleCamera();
            }
        });


        iv_endCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PostCallDiscoonect();

                if (mSession != null){
                    mSession.disconnect();
                }
            }
        });

        iv_PauseCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mPublisher == null) {
                    return;
                }
                ((BasicCustomVideoRenderer) mPublisher.getRenderer()).saveScreenshot(true);
                Toast.makeText(getActivity(), "Image clicked, Please wait", Toast.LENGTH_LONG).show();

               /*
                if (audio_status){
                    mSubscriber.setSubscribeToVideo(true);
                    mPublisher.setPublishVideo(true);

                    mPublisherViewContainer.addView(mPublisher.getView());
                    mSession.publish(mPublisher);

                    audio_status = false;

                    call_status_now = "video";

                    iv_PauseCall.setImageResource(R.mipmap.no_video);

                }else {
                    mSubscriber.setSubscribeToVideo(false);
                    mPublisher.setPublishVideo(false);

                    if (mPublisher != null){
                        mPublisherViewContainer.removeView(mPublisher.getView());

                        mSession.unpublish(mPublisher);
                    }

                    audio_status = true;

                    call_status_now = "audio";

                    iv_PauseCall.setImageResource(R.mipmap.video);

                }*/

            }
        });

     /*   iv_PauseCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (call_status){
                    mPublisherViewContainer.addView(mPublisher.getView());
                    mSession.publish(mPublisher);

                    call_status = false;

                    iv_PauseCall.setImageResource(R.mipmap.pause_alert_call);

                    saveIntoPrefs(AppConstant.Video_call_status,"start");


                }else {
                    mPublisherViewContainer.removeView(mPublisher.getView());

                    mSession.unpublish(mPublisher);

                    call_status = true;

                    iv_PauseCall.setImageResource(R.mipmap.start_call);

                    saveIntoPrefs(AppConstant.Video_call_status,"pause");
                }


            }
        });*/






    /*    btn_endCall = (Button) view.findViewById(R.id.btn_endCall);
        btn_STARTCall = (Button) view.findViewById(R.id.btn_STARTCall);*/

    /*    btn_STARTCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPublisherViewContainer.addView(mPublisher.getView());

                mSession.publish(mPublisher);
            }
        });

        btn_endCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               *//* ((BasicCustomVideoRenderer) mSubscriber.getRenderer()).saveScreenshot(true);
                Toast.makeText(getActivity(), "Screenshot saved.", Toast.LENGTH_LONG).show();*//*

              *//*  mPublisherViewContainer.removeView(mPublisher.getView());

                mSession.unpublish(mPublisher);*//*

                PauseCall();

*//*
                if (mSession != null){
                    mSession.disconnect();

          //          PostCallDiscoonect();
                }*//*
            }
        });*/

        return view;
    }

    public void PictureClick(){
        /*if (mPublisher == null) {
            return;
        }
        ((BasicCustomVideoRenderer) mPublisher.getRenderer()).saveScreenshot(true);
        Toast.makeText(getActivity(), "Screenshot saved.", Toast.LENGTH_LONG).show();*/

        mPublisher.setPublishVideo(false);
        mSubscriber.setSubscribeToVideo(false);


    }

 /*   public void CameraPause(){
        if (call_status_now.equalsIgnoreCase("video")){
            if (mPublisher != null){
     //           mPublisherViewContainer.removeView(mPublisher.getView());

                mSession.unpublish(mPublisher);
            }
        }
    }

    public void cameraStart(){

        if (call_status_now.equalsIgnoreCase("video")){
//                mPublisherViewContainer.addView(mPublisher.getView());
                mSession.publish(mPublisher);
            }
    }*/

    public static VideoFragment getInstance() {
        return instance;
    }

    public void saveIntoPrefs(String key, String value) {
        SharedPreferences prefs = getActivity().getSharedPreferences(AppConstant.PREF_NAME, getActivity().MODE_PRIVATE);
        SharedPreferences.Editor edit = prefs.edit();
        edit.putString(key, value);
        edit.commit();
    }

    @Override
    public void onPause() {
        super.onPause();

        if (mSession != null) {
            mSession.onPause();
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        if (mSession != null) {
            mSession.onResume();
        }
    }





}
