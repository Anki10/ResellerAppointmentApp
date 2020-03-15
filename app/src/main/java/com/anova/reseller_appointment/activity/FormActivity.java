package com.anova.reseller_appointment.activity;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.SurfaceTexture;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CaptureRequest;
import android.media.ImageReader;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.HandlerThread;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.content.FileProvider;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Size;
import android.util.SparseIntArray;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.anova.reseller_appointment.Interface.ListAdapterListener;
import com.anova.reseller_appointment.R;
import com.anova.reseller_appointment.View.AppConstant;
import com.anova.reseller_appointment.adapter.ImageShowAdapter;
import com.anova.reseller_appointment.api.APIService;
import com.anova.reseller_appointment.api.ApiUtils;
import com.anova.reseller_appointment.fragment.AssessmentFragment;
import com.anova.reseller_appointment.pojo.Basic_Information_list_Pojo;
import com.anova.reseller_appointment.pojo.GetAllFormData;
import com.anova.reseller_appointment.pojo.ImageUploadPojo;
import com.anova.reseller_appointment.pojo.Physical_Location_List;
import com.anova.reseller_appointment.pojo.SubmitFormRadioRequest;
import com.anova.reseller_appointment.pojo.SubmitFormRequest;
import com.anova.reseller_appointment.pojo.SubmitFormResponse;
import com.anova.reseller_appointment.pojo.VendorDetailsRequestPojo;
import com.anova.reseller_appointment.pojo.VendorDetailsResponsePojo;
import com.anova.reseller_appointment.pojo.VendorDetailsTAB_ResponsePojo;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FormActivity extends BaseActivity implements ListAdapterListener {

    // Physical Location

    LinearLayout row_ll_physical;

    ImageView arrow_physical;

    LinearLayout ll_physical_location;

    ImageView image_physical_geotagged;


    ImageView camera_view;


    // Basic Information

    LinearLayout row_ll_basic;

    ImageView arrow_expand_Basic_Information;

    LinearLayout ll_basic_info;

    EditText ed_basic_offical;

    ImageView image_basic_official;

    ImageView sync_basic_offical;


    EditText ed_basic_ID_proof;

    ImageView image_id_proof;

    ImageView sync_basic_Id_proof;


    // Stores capability

    LinearLayout row_ll_Stores_capability;

    ImageView arrow_Stores_capability;

    LinearLayout ll_Stores_capability;

    ImageView image_Stores_capability;

    ImageView image_inventory_mngmnt;

    ImageView image_brand_outlet;


    // Transportation

    LinearLayout row_ll_Transportation;

    ImageView arrow_expand_Transportation;

    LinearLayout ll_tranportation;

    ImageView image_tranportation;

    //  Safety

    LinearLayout row_ll_safety;

    ImageView arrow_expand_Safety;

    LinearLayout ll_safety;

    RadioButton signs_labels_yes;
    RadioButton signs_labels_no;
    ImageView image_signs_labels;

    RadioButton inhouse_ppe_yes;
    RadioButton inhouse_ppe_no;
    ImageView image_inhouse_ppe;


    RadioButton fire_safety_yes;
    RadioButton fire_safety_no;
    ImageView image_fire_safety;



    RecyclerView physical_recycler_view;
    RecyclerView official_recycler_view;
    RecyclerView Id_proof_recycler_view;
    RecyclerView Stores_capabilit_recycler_view;
    RecyclerView inventory_mngmnt_recycler_view;
    RecyclerView brand_outlet_recycler_view;
    RecyclerView Transportation_recycler_view;
    RecyclerView signs_labels_recycler_view;
    RecyclerView inhouse_ppe_recycler_view;
    RecyclerView fire_safety_recycler_view;

    private Dialog dialogLogout;

    private APIService mAPIService;

    private Boolean expand_basic = false,suppliers_status = false,Physical_Location_status = false,
            Stores_capability_status = false,Transportation_status = false,Quality_status = false;


    private ArrayList<Basic_Information_list_Pojo>physical_location_list;
    private ArrayList<Basic_Information_list_Pojo>basic_info_list;
    private ArrayList<Basic_Information_list_Pojo>stores_capability_list;
    private ArrayList<Basic_Information_list_Pojo>transportation_list;
    private ArrayList<Basic_Information_list_Pojo>Safety_list;
    private ArrayList<Basic_Information_list_Pojo>Transaction_Accounting_list;



    private static final String CAMERA_DIR = "/dcim/";
    private Uri picUri;
    private File imageF;

    ProgressDialog d;

    // camera

    private static final String TAG = "AndroidCameraApi";
    private Button takePictureButton;
    private static final SparseIntArray ORIENTATIONS = new SparseIntArray();
    static {
        ORIENTATIONS.append(Surface.ROTATION_0, 90);
        ORIENTATIONS.append(Surface.ROTATION_90, 0);
        ORIENTATIONS.append(Surface.ROTATION_180, 270);
        ORIENTATIONS.append(Surface.ROTATION_270, 180);
    }
    private String cameraId;
    protected CameraDevice cameraDevice;
    protected CameraCaptureSession cameraCaptureSessions;
    protected CaptureRequest captureRequest;
    protected CaptureRequest.Builder captureRequestBuilder;
    private Size imageDimension;
    private ImageReader imageReader;
    private File file;
    private static final int REQUEST_CAMERA_PERMISSION = 200;
    private boolean mFlashSupported;
    private Handler mBackgroundHandler;
    private HandlerThread mBackgroundThread;

    private String image_physical_store;

    RadioGroup radio_signs_labels,radio_inhouse_ppe,radio_fire_safety;

    private RecyclerView.LayoutManager mLayoutManager;
    private ImageShowAdapter physical_image_adapter;
    private ImageShowAdapter basic_info_adapter;
    private ImageShowAdapter basic_ID_adapter;
    private ImageShowAdapter Stores_capabilit_adapter;
    private ImageShowAdapter inventory_mngmnt_adapter;
    private ImageShowAdapter brand_outlet_adapter;
    private ImageShowAdapter Transportation_adapter;
    private ImageShowAdapter signs_labels_adapter;
    private ImageShowAdapter inhouse_ppe_adapter;
    private ImageShowAdapter fire_safety_adapter;


    private ArrayList<ImageUploadPojo> image_list;
    private ArrayList<ImageUploadPojo> image_official_list;
    private ArrayList<ImageUploadPojo> image_Id_proof_list;
    private ArrayList<ImageUploadPojo> image_Stores_capabilit_list;
    private ArrayList<ImageUploadPojo> image_inventory_mngmnt_list;
    private ArrayList<ImageUploadPojo> image_brand_outlet_list;
    private ArrayList<ImageUploadPojo> image_Transportation_list;
    private ArrayList<ImageUploadPojo> image_signs_labels_list;
    private ArrayList<ImageUploadPojo> image_inhouse_ppe_list;
    private ArrayList<ImageUploadPojo> image_fire_safety_list;


    private Boolean camera_flip_status = false;

    private Boolean other_tab_status = false;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);


        mAPIService = ApiUtils.getAPIService();


        // Physical Location

        row_ll_physical = (LinearLayout) findViewById(R.id.row_ll_physical);
        ll_physical_location = (LinearLayout) findViewById(R.id.ll_physical_location);
        arrow_physical = (ImageView) findViewById(R.id.arrow_physical);
        image_physical_geotagged = (ImageView) findViewById(R.id.image_physical_geotagged);
        camera_view = (ImageView) findViewById(R.id.camera_view);

        // Basic Information


        row_ll_basic = (LinearLayout) findViewById(R.id.row_ll_basic);
        ll_basic_info = (LinearLayout) findViewById(R.id.ll_basic_info);

        sync_basic_offical = (ImageView) findViewById(R.id.sync_basic_offical);
        sync_basic_Id_proof = (ImageView) findViewById(R.id.sync_basic_Id_proof);

        arrow_expand_Basic_Information = (ImageView) findViewById(R.id.arrow_expand_Basic_Information);
        image_basic_official = (ImageView) findViewById(R.id.image_basic_official);
        image_id_proof = (ImageView) findViewById(R.id.image_id_proof);

        ed_basic_offical = (EditText) findViewById(R.id.ed_basic_offical);
        ed_basic_ID_proof = (EditText) findViewById(R.id.ed_basic_ID_proof);

        // Stores capability

        row_ll_Stores_capability = (LinearLayout) findViewById(R.id.row_ll_Stores_capability);
        ll_Stores_capability = (LinearLayout) findViewById(R.id.ll_Stores_capability);

        arrow_Stores_capability = (ImageView) findViewById(R.id.arrow_Stores_capability);
        image_Stores_capability = (ImageView) findViewById(R.id.image_Stores_capability);
        image_inventory_mngmnt = (ImageView) findViewById(R.id.image_inventory_mngmnt);
        image_brand_outlet = (ImageView) findViewById(R.id.image_brand_outlet);

        // Transportation

        row_ll_safety = findViewById(R.id.row_ll_safety);


        image_tranportation = (ImageView) findViewById(R.id.image_tranportation);

        row_ll_Transportation = (LinearLayout) findViewById(R.id.row_ll_Transportation);

        signs_labels_yes = (RadioButton) findViewById(R.id.signs_labels_yes);
        signs_labels_no = (RadioButton) findViewById(R.id.signs_labels_no);
        image_signs_labels = (ImageView) findViewById(R.id.image_signs_labels);

        inhouse_ppe_yes = (RadioButton) findViewById(R.id.inhouse_ppe_yes);
        inhouse_ppe_no = (RadioButton)  findViewById(R.id.inhouse_ppe_no);
        image_inhouse_ppe = (ImageView) findViewById(R.id.image_inhouse_ppe);

        fire_safety_yes = (RadioButton) findViewById(R.id.fire_safety_yes);
        fire_safety_no = (RadioButton) findViewById(R.id.fire_safety_no);
        image_fire_safety = (ImageView) findViewById(R.id.image_fire_safety);

        physical_recycler_view = (RecyclerView) findViewById(R.id.physical_recycler_view);
        official_recycler_view = (RecyclerView) findViewById(R.id.official_recycler_view);
        Id_proof_recycler_view = (RecyclerView) findViewById(R.id.Id_proof_recycler_view);
        Stores_capabilit_recycler_view = (RecyclerView) findViewById(R.id.Stores_capabilit_recycler_view);
        inventory_mngmnt_recycler_view = (RecyclerView) findViewById(R.id.inventory_mngmnt_recycler_view);
        brand_outlet_recycler_view = (RecyclerView) findViewById(R.id.brand_outlet_recycler_view);
        Transportation_recycler_view = (RecyclerView) findViewById(R.id.Transportation_recycler_view);
        signs_labels_recycler_view = (RecyclerView) findViewById(R.id.signs_labels_recycler_view);
        inhouse_ppe_recycler_view = (RecyclerView) findViewById(R.id.inhouse_ppe_recycler_view);
        fire_safety_recycler_view = (RecyclerView) findViewById(R.id.fire_safety_recycler_view);


        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(),4);
        physical_recycler_view.setLayoutManager(gridLayoutManager);
        GridLayoutManager gridLayoutManager_official = new GridLayoutManager(getApplicationContext(),4);
        official_recycler_view.setLayoutManager(gridLayoutManager_official);
        GridLayoutManager gridLayoutManager_ID_proof = new GridLayoutManager(getApplicationContext(),4);
        Id_proof_recycler_view.setLayoutManager(gridLayoutManager_ID_proof);
        GridLayoutManager gridLayoutManager_process_capability = new GridLayoutManager(getApplicationContext(),4);
        Stores_capabilit_recycler_view.setLayoutManager(gridLayoutManager_process_capability);
        GridLayoutManager gridLayoutManager_production_capacity = new GridLayoutManager(getApplicationContext(),4);
        inventory_mngmnt_recycler_view.setLayoutManager(gridLayoutManager_production_capacity);
        GridLayoutManager gridLayoutManager_Quality = new GridLayoutManager(getApplicationContext(),4);
        brand_outlet_recycler_view.setLayoutManager(gridLayoutManager_Quality);
        GridLayoutManager gridLayoutManager_Transportation= new GridLayoutManager(getApplicationContext(),4);
        Transportation_recycler_view.setLayoutManager(gridLayoutManager_Transportation);
        GridLayoutManager gridLayoutManager_safety_signage = new GridLayoutManager(getApplicationContext(),4);
        signs_labels_recycler_view.setLayoutManager(gridLayoutManager_safety_signage);
        GridLayoutManager gridLayoutManager_safety_equipment = new GridLayoutManager(getApplicationContext(),4);
        inhouse_ppe_recycler_view.setLayoutManager(gridLayoutManager_safety_equipment);
        GridLayoutManager gridLayoutManager_development_facility = new GridLayoutManager(getApplicationContext(),4);
        fire_safety_recycler_view.setLayoutManager(gridLayoutManager_development_facility);


        image_list = new ArrayList<>();
        image_official_list = new ArrayList<>();
        image_Id_proof_list = new ArrayList<>();
        image_Stores_capabilit_list = new ArrayList<>();
        image_inventory_mngmnt_list = new ArrayList<>();
        image_brand_outlet_list = new ArrayList<>();
        image_Transportation_list = new ArrayList<>();
        image_signs_labels_list = new ArrayList<>();
        image_inhouse_ppe_list = new ArrayList<>();
        image_fire_safety_list = new ArrayList<>();


        radio_signs_labels = (RadioGroup) findViewById(R.id.radio_signs_labels);

        radio_signs_labels.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // checkedId is the RadioButton selected

                switch(checkedId) {
                    case R.id.signs_labels_yes:
                        PostFormRadioData(true,Safety_list.get(0).getId(),"saftey_radio");
                        break;
                    case R.id.signs_labels_no:
                        PostFormRadioData(false,Safety_list.get(0).getId(),"saftey_radio");
                        break;
                }
            }
        });

        radio_inhouse_ppe = (RadioGroup) findViewById(R.id.radio_inhouse_ppe);

        radio_inhouse_ppe.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch(checkedId) {
                    case R.id.inhouse_ppe_yes:
                        PostFormRadioData(true,Safety_list.get(1).getId(),"saftey_radio_2");
                        break;
                    case R.id.inhouse_ppe_no:
                        PostFormRadioData(false,Safety_list.get(1).getId(),"saftey_radio_2");
                        break;
                }
            }
        });

        radio_fire_safety = (RadioGroup) findViewById(R.id.radio_fire_safety);

        radio_fire_safety.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch(checkedId) {
                    case R.id.fire_safety_yes:
                        PostFormRadioData(true,Safety_list.get(2).getId(),"saftey_radio_3");
                        break;
                    case R.id.fire_safety_no:
                        PostFormRadioData(false,Safety_list.get(2).getId(),"saftey_radio_3");
                        break;
                }
            }
        });

        setOnClick();

        physical_location_list = new ArrayList<>();
        basic_info_list = new ArrayList<>();
        stores_capability_list = new ArrayList<>();
        transportation_list = new ArrayList<>();
        Safety_list = new ArrayList<>();
        Transaction_Accounting_list = new ArrayList<>();

        takePictureButton = (Button) findViewById(R.id.btn_takepicture);
        assert takePictureButton != null;

        if (getArrayList("physical_list") != null){
            if (getArrayList("physical_list").size() > 0){
                image_list = getArrayList("physical_list");

                physical_recycler_view.setVisibility(View.VISIBLE);


                physical_image_adapter = new ImageShowAdapter(this,image_list,"","physical_location", FormActivity.this);
                physical_recycler_view.setAdapter(physical_image_adapter);
            }
        }

        if (getArrayList("official_list") != null){
            if (getArrayList("official_list").size() > 0){
                image_official_list = getArrayList("official_list");

                official_recycler_view.setVisibility(View.VISIBLE);

                basic_info_adapter = new ImageShowAdapter(this,image_official_list,"","basic_Info_official",FormActivity.this);
                official_recycler_view.setAdapter(basic_info_adapter);
            }
        }
        if (getArrayList("Id_proof_list") != null){
            if (getArrayList("Id_proof_list").size() > 0){
                image_Id_proof_list = getArrayList("Id_proof_list");

                Id_proof_recycler_view.setVisibility(View.VISIBLE);

                basic_ID_adapter = new ImageShowAdapter(this,image_Id_proof_list,"","basic_information_Id_Proof",FormActivity.this);
                Id_proof_recycler_view.setAdapter(basic_ID_adapter);
            }
        }



        if (getArrayList("Stores_capabilit_list") != null){
            if (getArrayList("Stores_capabilit_list").size() > 0){
                image_Stores_capabilit_list = getArrayList("Stores_capabilit_list");

                Stores_capabilit_recycler_view.setVisibility(View.VISIBLE);

                Stores_capabilit_adapter = new ImageShowAdapter(this,image_Stores_capabilit_list,"","image_Stores_capabilit",this);
                Stores_capabilit_recycler_view.setAdapter(Stores_capabilit_adapter);
            }
        }


        if (getArrayList("inventory_mngmnt") != null){
            if (getArrayList("inventory_mngmnt").size() > 0){
                image_inventory_mngmnt_list = getArrayList("inventory_mngmnt");

                inventory_mngmnt_recycler_view.setVisibility(View.VISIBLE);

                inventory_mngmnt_adapter = new ImageShowAdapter(this,image_inventory_mngmnt_list,"","image_inventory_mngmnt",this);
                inventory_mngmnt_recycler_view.setAdapter(inventory_mngmnt_adapter);
            }
        }
        if (getArrayList("brand_outlet") != null){
            if (getArrayList("Quality_list").size() > 0){
                image_brand_outlet_list = getArrayList("Quality_list");

                brand_outlet_recycler_view.setVisibility(View.VISIBLE);

                brand_outlet_adapter = new ImageShowAdapter(this,image_brand_outlet_list,"","image_brand_outlet",this);
                brand_outlet_recycler_view.setAdapter(brand_outlet_adapter);
            }
        }

        if (getArrayList("Transportation_list") != null){
            if (getArrayList("Transportation_list").size() > 0){
                image_Transportation_list = getArrayList("Transportation_list");

                Transportation_recycler_view.setVisibility(View.VISIBLE);

                Transportation_adapter = new ImageShowAdapter(this,image_Transportation_list,"","transportation",this);
                Transportation_recycler_view.setAdapter(Transportation_adapter);
            }
        }

        if (getArrayList("image_signs_labels_list") != null){
            if (getArrayList("image_signs_labels_list").size() > 0){
                image_signs_labels_list = getArrayList("image_signs_labels_list");

                signs_labels_recycler_view.setVisibility(View.VISIBLE);

                signs_labels_adapter = new ImageShowAdapter(this,image_signs_labels_list,"","image_signs_labels",this);
                signs_labels_recycler_view.setAdapter(signs_labels_adapter);
            }
        }

        if (getArrayList("image_inhouse_ppe") != null){
            if (getArrayList("image_inhouse_ppe").size() > 0){
                image_inhouse_ppe_list = getArrayList("image_inhouse_ppe");

                inhouse_ppe_recycler_view.setVisibility(View.VISIBLE);

                inhouse_ppe_adapter = new ImageShowAdapter(this,image_inhouse_ppe_list,"","image_inhouse_ppe",this);
                inhouse_ppe_recycler_view.setAdapter(inhouse_ppe_adapter);
            }
        }

        if (getArrayList("saftey_image_3") != null){
            if (getArrayList("saftey_image_3").size() > 0){
                image_fire_safety_list = getArrayList("saftey_image_3");

                fire_safety_recycler_view.setVisibility(View.VISIBLE);

                fire_safety_adapter = new ImageShowAdapter(this,image_fire_safety_list,"","image_fire_safety",this);
                fire_safety_recycler_view.setAdapter(fire_safety_adapter);
            }
        }

        if (getFromPrefs("basic_Info_official_text") != null){
            String msg = getFromPrefs("basic_Info_official_text");
            if (getFromPrefs("basic_Info_official_text").length() > 0){
                ed_basic_offical.setText(getFromPrefs("basic_Info_official_text"));
            }
        }
        if (getFromPrefs("Basic_Info_Id_proof_text") != null){
            if (getFromPrefs("Basic_Info_Id_proof_text").length() > 0){
                ed_basic_ID_proof.setText(getFromPrefs("Basic_Info_Id_proof_text"));
            }
        }

        GetFormData();

    }




    private void setOnClick(){

        row_ll_physical.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Physical_Location_status){
                    ll_physical_location.setVisibility(View.GONE);

                    arrow_physical.setImageResource(R.mipmap.expandable_down_arrow);

                    Physical_Location_status = false;

                }else {
                    ll_physical_location.setVisibility(View.VISIBLE);

                    arrow_physical.setImageResource(R.mipmap.expandable_up_arrow);

                    Physical_Location_status = true;

                }
            }
        });

        image_physical_geotagged.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                captureImage(1);

            }
        });


        row_ll_basic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (expand_basic){

                    ll_basic_info.setVisibility(View.GONE);

                    arrow_expand_Basic_Information.setImageResource(R.mipmap.expandable_down_arrow);

                    expand_basic = false;


                }else {
                    ll_basic_info.setVisibility(View.VISIBLE);

                    arrow_expand_Basic_Information.setImageResource(R.mipmap.expandable_up_arrow);

                    expand_basic = true;

                }
            }
        });

        image_basic_official.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                captureImage(2);
            }
        });

        image_id_proof.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                captureImage(3);
            }
        });

        row_ll_Stores_capability.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Stores_capability_status){
                    ll_Stores_capability.setVisibility(View.GONE);

                    arrow_Stores_capability.setImageResource(R.mipmap.expandable_down_arrow);

                    Stores_capability_status = false;
                }else {
                    ll_Stores_capability.setVisibility(View.VISIBLE);

                    arrow_Stores_capability.setImageResource(R.mipmap.expandable_up_arrow);

                    Stores_capability_status = true;
                }
            }
        });

        image_Stores_capability.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                captureImage(4);


            }
        });

        image_inventory_mngmnt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                captureImage(5);


            }
        });

        image_brand_outlet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                captureImage(6);


            }
        });
        row_ll_Transportation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Transportation_status){

                    arrow_expand_Transportation.setImageResource(R.mipmap.expandable_down_arrow);

                    ll_tranportation.setVisibility(View.GONE);

                    Transportation_status = false;
                }else {

                    ll_tranportation.setVisibility(View.VISIBLE);

                    arrow_expand_Transportation.setImageResource(R.mipmap.expandable_up_arrow);

                    Transportation_status = true;

                }
            }
        });

        image_tranportation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                captureImage(7);

            }
        });


        row_ll_safety.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Quality_status){

                    arrow_expand_Safety.setImageResource(R.mipmap.expandable_down_arrow);

                    ll_safety.setVisibility(View.GONE);

                    Quality_status = false;


                }else {
                    ll_safety.setVisibility(View.VISIBLE);

                    arrow_expand_Safety.setImageResource(R.mipmap.expandable_up_arrow);

                    Quality_status = true;
                }
            }
        });

        image_signs_labels.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                captureImage(8);

            }
        });

        image_inhouse_ppe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                captureImage(9);

            }
        });

        image_fire_safety.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                captureImage(10);

            }
        });

    }


    private void GetFormData(){
        mAPIService.getAllData("application/json").enqueue(new Callback<GetAllFormData>() {
            @Override
            public void onResponse(Call<GetAllFormData> call, Response<GetAllFormData> response) {
                if (response.body() != null){
                    System.out.println("xxxx");

                    try {
                        physical_location_list = response.body().getData().getPhysical_location();

                        basic_info_list = response.body().getData().getBasic_information();

                        stores_capability_list = response.body().getData().getStores_capabilit();

                        transportation_list = response.body().getData().getTransportation();

                        Safety_list = response.body().getData().getSafety();
                        Transaction_Accounting_list = response.body().getData().getTransaction_and_Accounting();
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<GetAllFormData> call, Throwable t) {
                System.out.println("xxx faild");
            }
        });
    }

    private void PostFormData(final String image, final String Question_id, final String from, final int pos){
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {

                String image_store = getFileToByte(image);

                SubmitFormRequest request = new SubmitFormRequest();

                request.setForm_id(getFromPrefs("form_id"));   //
                request.setFile_data(image_store);
                request.setQuestion_id(Question_id);
                request.setIs_submit(false);
                request.setType(getFromPrefs("seller_assessed_as"));

         /*       getActivity().runOnUiThread(new Runnable() {
                    public void run() {
                         d = ImageDialog.showLoading(getActivity());
                        d.setCanceledOnTouchOutside(false);
                    }
                });
*/

                mAPIService.PostForm("application/json",request).enqueue(new Callback<SubmitFormResponse>() {
                    @Override
                    public void onResponse(Call<SubmitFormResponse> call, final Response<SubmitFormResponse> response) {
                    /*    getActivity().runOnUiThread(new Runnable() {
                            public void run() {
                               d.dismiss();
                            }
                        });*/
                        if (response.body() != null){
                            if (response.body().getSuccess()){

                                Toast.makeText(FormActivity.this,"Data sync successfully",Toast.LENGTH_SHORT).show();


                                if (from.equalsIgnoreCase("physical_location")){

                                    ImageUploadPojo pojo = image_list.get(pos);
                                    pojo.setImage_status(true);

                                    image_list.set(pos,pojo);

                                    saveArrayList(image_list,"physical_list");

                                    physical_image_adapter.notifyDataSetChanged();


                                }else if (from.equalsIgnoreCase("basic_Info_official")){

                                    ImageUploadPojo pojo = image_official_list.get(pos);
                                    pojo.setImage_status(true);

                                    image_official_list.set(pos,pojo);

                                    saveArrayList(image_official_list,"official_list");

                                    basic_info_adapter.notifyDataSetChanged();

                                }else if (from.equalsIgnoreCase("Basic_Info_Id_proof")){

                                    ImageUploadPojo pojo = image_Id_proof_list.get(pos);
                                    pojo.setImage_status(true);

                                    image_Id_proof_list.set(pos,pojo);

                                    saveArrayList(image_Id_proof_list,"Id_proof_list");

                                    basic_ID_adapter.notifyDataSetChanged();

                                }else if (from.equalsIgnoreCase("Stores_capabilit")){

                                    ImageUploadPojo pojo = image_Stores_capabilit_list.get(pos);
                                    pojo.setImage_status(true);

                                    image_Stores_capabilit_list.set(pos,pojo);

                                    saveArrayList(image_Stores_capabilit_list,"Stores_capabilit_list");

                                    Stores_capabilit_adapter.notifyDataSetChanged();

                                }else if (from.equalsIgnoreCase("inventory_mngmnt")){
                                    ImageUploadPojo pojo = image_inventory_mngmnt_list.get(pos);
                                    pojo.setImage_status(true);

                                    image_inventory_mngmnt_list.set(pos,pojo);

                                    saveArrayList(image_inventory_mngmnt_list,"image_inventory_mngmnt");

                                    inventory_mngmnt_adapter.notifyDataSetChanged();

                                }
                                else if (from.equalsIgnoreCase("brand_outlet")){
                                    ImageUploadPojo pojo = image_brand_outlet_list.get(pos);
                                    pojo.setImage_status(true);

                                    image_brand_outlet_list.set(pos,pojo);

                                    saveArrayList(image_brand_outlet_list,"image_brand_outlet");

                                    brand_outlet_adapter.notifyDataSetChanged();

                                }


                                else if (from.equalsIgnoreCase("Transportation")){

                                    ImageUploadPojo pojo = image_Transportation_list.get(pos);
                                    pojo.setImage_status(true);

                                    image_Transportation_list.set(pos,pojo);

                                    saveArrayList(image_Transportation_list,"Transportation_list");

                                    Transportation_adapter.notifyDataSetChanged();

                                }else if (from.equalsIgnoreCase("saftey_image")){

                                    ImageUploadPojo pojo = image_signs_labels_list.get(pos);
                                    pojo.setImage_status(true);

                                    image_signs_labels_list.set(pos,pojo);

                                    saveArrayList(image_signs_labels_list,"image_signs_labels");

                                    signs_labels_adapter.notifyDataSetChanged();

                                }else if (from.equalsIgnoreCase("saftey_image_2")){

                                    ImageUploadPojo pojo = image_inhouse_ppe_list.get(pos);
                                    pojo.setImage_status(true);

                                    image_inhouse_ppe_list.set(pos,pojo);

                                    saveArrayList(image_inhouse_ppe_list,"image_inhouse_ppe");

                                    inhouse_ppe_adapter.notifyDataSetChanged();

                                }else if (from.equalsIgnoreCase("saftey_image_3")){

                                    ImageUploadPojo pojo = image_fire_safety_list.get(pos);
                                    pojo.setImage_status(true);

                                    image_fire_safety_list.set(pos,pojo);

                                    saveArrayList(image_fire_safety_list,"image_fire_safety");

                                    fire_safety_adapter.notifyDataSetChanged();

                                }
                            }else {
                                Toast.makeText(FormActivity.this,"Image upload failed",Toast.LENGTH_LONG).show();
                            }
                        }else {
                            Toast.makeText(FormActivity.this,"Image upload failed",Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<SubmitFormResponse> call, Throwable t) {
                        System.out.println("xxx fail");

                        Toast.makeText(FormActivity.this,"Image upload failed",Toast.LENGTH_LONG).show();

                        //                          VideoFragment.getInstance().cameraStart();


                      /*  getActivity().runOnUiThread(new Runnable() {
                            public void run() {
                                d.dismiss();
                            }
                        });*/
                    }
                });
            }
        });


    }

    private void PostFormEditTextData(final String edit_text, final String Question_id, final String from){
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {

                SubmitFormRequest request = new SubmitFormRequest();

                request.setForm_id(getFromPrefs("form_id"));   //
                request.setFile_data(edit_text);
                request.setQuestion_id(Question_id);
                request.setIs_submit(false);
                request.setType(getFromPrefs("seller_assessed_as"));

         /*       getActivity().runOnUiThread(new Runnable() {
                    public void run() {
                         d = ImageDialog.showLoading(getActivity());
                        d.setCanceledOnTouchOutside(false);
                    }
                });
*/

                mAPIService.PostForm("application/json",request).enqueue(new Callback<SubmitFormResponse>() {
                    @Override
                    public void onResponse(Call<SubmitFormResponse> call, final Response<SubmitFormResponse> response) {
                    /*    getActivity().runOnUiThread(new Runnable() {
                            public void run() {
                               d.dismiss();
                            }
                        });*/
                        if (response.body() != null) {
                            if (response.body().getSuccess()) {

                                Toast.makeText(FormActivity.this, "Data sync successfully", Toast.LENGTH_SHORT).show();

                                if (from.equalsIgnoreCase("basic_Info_official_text")) {
                                    saveIntoPrefs("basic_Info_official_text",ed_basic_offical.getText().toString());
                                } else if (from.equalsIgnoreCase("Basic_Info_Id_proof_text")) {
                                    saveIntoPrefs("Basic_Info_Id_proof_text",ed_basic_ID_proof.getText().toString());
                                }
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<SubmitFormResponse> call, Throwable t) {
                        System.out.println("xxx fail");

                      /*  getActivity().runOnUiThread(new Runnable() {
                            public void run() {
                                d.dismiss();
                            }
                        });*/
                    }
                });
            }
        });


    }

    private void PostFormRadioData(final Boolean file_type, final String Question_id, final String from){
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                SubmitFormRadioRequest radio_request = new SubmitFormRadioRequest();

                radio_request.setForm_id(getFromPrefs("form_id"));   //
                radio_request.setFile_data(file_type);
                radio_request.setQuestion_id(Question_id);
                radio_request.setIs_submit(false);
                radio_request.setType(getFromPrefs("seller_assessed_as"));

              /*  getActivity().runOnUiThread(new Runnable() {
                    public void run() {
                        d = ImageDialog.showLoading(getActivity());
                        d.setCanceledOnTouchOutside(false);
                    }
                });
*/
                mAPIService.PostRadioForm("application/json",radio_request).enqueue(new Callback<SubmitFormResponse>() {
                    @Override
                    public void onResponse(Call<SubmitFormResponse> call, Response<SubmitFormResponse> response) {
                        runOnUiThread(new Runnable() {
                            public void run() {
                                //               d.dismiss();
                            }
                        });
                        if (response.body() != null){
                            if (response.body().getSuccess()){
                                Toast.makeText(FormActivity.this,"Data sync successfully",Toast.LENGTH_LONG).show();

                            }else {
                                Toast.makeText(FormActivity.this,response.body().getMessage(),Toast.LENGTH_LONG).show();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<SubmitFormResponse> call, Throwable t) {
                        System.out.println("xxx fail");

                        runOnUiThread(new Runnable() {
                            public void run() {
                                //                     d.dismiss();
                            }
                        });
                    }
                });
            }
        });

    }

    public static String getFileToByte(String filePath){
        Bitmap bmp = null;
        ByteArrayOutputStream bos = null;
        byte[] bt = null;
        String encodeString = null;
        try{
            bmp = BitmapFactory.decodeFile(filePath);
            bos = new ByteArrayOutputStream();
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, bos);
            bt = bos.toByteArray();
            encodeString = Base64.encodeToString(bt, Base64.DEFAULT);
        }catch (Exception e){
            e.printStackTrace();
        }
        return encodeString;
    }

    TextureView.SurfaceTextureListener textureListener = new TextureView.SurfaceTextureListener() {
        @Override
        public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
            //open your camera here
            AsyncTask.execute(new Runnable() {
                @Override
                public void run() {
                    //TODO your background code

                    //               openCamera();
                }
            });
        }
        @Override
        public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {
            // Transform you image captured size according to the surface width and height
        }
        @Override
        public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
            return false;
        }
        @Override
        public void onSurfaceTextureUpdated(SurfaceTexture surface) {
        }
    };



    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_CAMERA_PERMISSION) {
            if (grantResults[0] == PackageManager.PERMISSION_DENIED) {
                // close the app
                Toast.makeText(FormActivity.this, "Sorry!!!, you can't use this app without granting permission", Toast.LENGTH_LONG).show();
                finish();
            }
        }
    }

    /* public void showImageListDialog(ArrayList<String> list, final int position, String from) {
         dialogLogout = new Dialog(getActivity(), android.R.style.Theme_Translucent_NoTitleBar);
         dialogLogout.setContentView(R.layout.image_list_dialog);
         Button ll_ok = (Button) dialogLogout.findViewById(R.id.btn_yes_ok);
         Button btn_add_more = (Button) dialogLogout.findViewById(R.id.btn_add_more);
         ImageView dialog_header_cross = (ImageView) dialogLogout.findViewById(R.id.dialog_header_cross);
         RecyclerView image_recycler_view = (RecyclerView) dialogLogout.findViewById(R.id.image_recycler_view);

         dialogLogout.show();

         GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity().getApplicationContext(),3);
         image_recycler_view.setLayoutManager(gridLayoutManager);

         image_adapter = new ImageShowAdapter(getActivity(),list,from,"HouseKeeping");
         image_recycler_view.setAdapter(image_adapter);

         ll_ok.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 dialogLogout.dismiss();
             }
         });

         dialog_header_cross.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 dialogLogout.dismiss();
             }
         });

         btn_add_more.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 dialogLogout.dismiss();

                 if (getFromPrefs(AppConstant.Video_call_status) != null){
                     if (getFromPrefs(AppConstant.Video_call_status).length() > 0){
                         if (getFromPrefs(AppConstant.Video_call_status).equalsIgnoreCase("pause")){
                             rl_view_camera.setVisibility(View.VISIBLE);

                             physical_location_status = true;

                             openCamera();
                         }else {
                             Toast.makeText(getActivity(),"Please pause your camera",Toast.LENGTH_LONG).show();
                         }
                     }else {
                         Toast.makeText(getActivity(),"Please pause your camera",Toast.LENGTH_LONG).show();
                     }
                 }
             }
         });
     }
 */




    public void saveArrayList(ArrayList<ImageUploadPojo> list, String key){
        SharedPreferences prefs = getSharedPreferences(AppConstant.PREF_NAME, MODE_PRIVATE);
        SharedPreferences.Editor edit = prefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(list);
        edit.putString(key, json);
        edit.apply();     // This line is IMPORTANT !!!
    }

    public ArrayList<ImageUploadPojo> getArrayList(String key){
        SharedPreferences prefs = getSharedPreferences(AppConstant.PREF_NAME, MODE_PRIVATE);
        Gson gson = new Gson();
        String json = prefs.getString(key, null);
        Type type = new TypeToken<ArrayList<ImageUploadPojo>>() {}.getType();
        return gson.fromJson(json, type);
    }




   /* private void DeleteImage(final String question_id, final String data_id, final String from_question , final int position){
        AsyncTask.execute(new Runnable() {

            @Override
            public void run() {

                DeleteRequestPojo requestPojo = new DeleteRequestPojo();
                requestPojo.setForm_id(getFromPrefs("form_id"));
                requestPojo.setQuestion_id(question_id);
                requestPojo.setData_id(data_id);

                mAPIService.GetDeleteResponse("application/json",requestPojo).enqueue(new Callback<DeleteResponsePojo>() {
                    @Override
                    public void onResponse(Call<DeleteResponsePojo> call, Response<DeleteResponsePojo> response) {
                        if (response.body() !=  null){
                            if (response.body().getSuccess()){
                                Toast.makeText(getActivity(),"Image deleted successfully",Toast.LENGTH_LONG).show();

                                try {
                                    if (from_question.equalsIgnoreCase("physical_location")){
                                        image_list.remove(position);

                                        saveArrayList(image_list,"physical_list");

                                        physical_image_adapter.notifyItemRemoved(position);
                                        physical_image_adapter.notifyDataSetChanged();

                                    }else if (from_question.equalsIgnoreCase("basic_information_official")){
                                        image_official_list.remove(position);

                                        image_adapter.notifyItemRemoved(position);
                                        image_adapter.notifyDataSetChanged();

                                        saveArrayList(image_official_list,"official_list");

                                    }else if (from_question.equalsIgnoreCase("basic_information_Id_Proof")){
                                        image_Id_proof_list.remove(position);

                                        image_adapter.notifyItemRemoved(position);
                                        image_adapter.notifyDataSetChanged();

                                        saveArrayList(image_Id_proof_list,"Id_proof_list");

                                    }else if (from_question.equalsIgnoreCase("process_capability")){
                                        image_process_capability_list.remove(position);

                                        image_adapter.notifyItemRemoved(position);
                                        image_adapter.notifyDataSetChanged();

                                        saveArrayList(image_process_capability_list,"process_capability_list");
                                    }
                                    else if (from_question.equalsIgnoreCase("production_capacity")){

                                        image_production_capacity_list.remove(position);

                                        image_adapter.notifyItemRemoved(position);
                                        image_adapter.notifyDataSetChanged();

                                        saveArrayList(image_production_capacity_list,"production_capacity_list");

                                    } else if (from_question.equalsIgnoreCase("Quality")){

                                        image_Quality_list.remove(position);

                                        image_adapter.notifyItemRemoved(position);
                                        image_adapter.notifyDataSetChanged();

                                        saveArrayList(image_Quality_list,"Quality_list");

                                    }else if (from_question.equalsIgnoreCase("transportation")){

                                        image_Transportation_list.remove(position);

                                        image_adapter.notifyItemRemoved(position);
                                        image_adapter.notifyDataSetChanged();


                                        saveArrayList(image_Transportation_list,"Transportation_list");

                                    }
                                    else if (from_question.equalsIgnoreCase("safety1")){

                                        image_safety_signage_list.remove(position);

                                        image_adapter.notifyItemRemoved(position);
                                        image_adapter.notifyDataSetChanged();

                                        saveArrayList(image_safety_signage_list,"safety_signage_list");

                                    }
                                    else if (from_question.equalsIgnoreCase("safety2")){

                                        image_safety_equipment_list.remove(position);

                                        image_adapter.notifyItemRemoved(position);
                                        image_adapter.notifyDataSetChanged();

                                        saveArrayList(image_safety_equipment_list,"safety_equipment_list");
                                    }
                                    else if (from_question.equalsIgnoreCase("Development_1")){

                                        image_development_facility_list.remove(position);

                                        image_adapter.notifyItemRemoved(position);
                                        image_adapter.notifyDataSetChanged();

                                        saveArrayList(image_development_facility_list,"development_facility_list");

                                    }else if (from_question.equalsIgnoreCase("Development_2")){
                                        image_development_obtained_list.remove(position);

                                        image_adapter.notifyItemRemoved(position);
                                        image_adapter.notifyDataSetChanged();

                                        saveArrayList(image_development_obtained_list,"development_obtained_list");


                                    }else if (from_question.equalsIgnoreCase("Development_3")){
                                        image_development_prototype_list.remove(position);

                                        image_adapter.notifyItemRemoved(position);
                                        image_adapter.notifyDataSetChanged();

                                        saveArrayList(image_development_prototype_list,"development_prototype_list");
                                    }
                                }catch (Exception e){
                                    e.printStackTrace();
                                }


                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<DeleteResponsePojo> call, Throwable t) {
                        System.out.println("xxxx");
                    }
                });


            }
        });
    }*/

    // Custom camera

    private void captureImage(int CAMERA_REQUEST) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        try {
            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            String imageFileName = "bmh" + timeStamp + "_";
            File albumF = getAlbumDir();
            imageF = File.createTempFile(imageFileName, "bmh", albumF);
            picUri = Uri.fromFile(imageF);

            if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.LOLLIPOP) {
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(imageF));
            } else {
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, FileProvider.getUriForFile(FormActivity.this, getApplicationContext().getPackageName() + ".provider", imageF));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        startActivityForResult(takePictureIntent, CAMERA_REQUEST);

    }

    private File getAlbumDir() {
        File storageDir = null;

        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.FROYO) {
                storageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "CameraPicture");
            } else {
                storageDir = new File(Environment.getExternalStorageDirectory() + CAMERA_DIR + "CameraPicture");
            }

            if (storageDir != null) {
                if (!storageDir.mkdirs()) {
                    if (!storageDir.exists()) {
                        //		Log.d("CameraSample", "failed to create directory");
                        return null;
                    }
                }
            }

        } else {
            //		Log.v(getString(R.string.app_name), "External storage is not mounted READ/WRITE.");
        }

        return storageDir;
    }


    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == 1) {
                if (picUri != null) {
                    Uri uri = picUri;
                    String image2 = compressImage(uri.toString());
                    //                 saveIntoPrefs(AppConstant.statutory_statePollution,image2);

                    ImageUploadPojo pojo = new ImageUploadPojo();
                    pojo.setImage_path(image2);
                    pojo.setImage_status(false);

                    image_list.add(pojo);
                    saveArrayList(image_list,"physical_list");

                    physical_recycler_view.setVisibility(View.VISIBLE);

                    physical_image_adapter = new ImageShowAdapter(FormActivity.this,image_list,"","physical_location",FormActivity.this);
                    physical_recycler_view.setAdapter(physical_image_adapter);

                    PostFormData(image2,physical_location_list.get(0).getId(),"physical_location",image_list.size()-1);


                }

            }else if (requestCode == 2){
                if (picUri != null){
                    Uri uri = picUri;
                    String image2 = compressImage(uri.toString());

                    ImageUploadPojo pojo = new ImageUploadPojo();
                    pojo.setImage_path(image2);
                    pojo.setImage_status(false);

                    image_official_list.add(pojo);

                    official_recycler_view.setVisibility(View.VISIBLE);

                    saveArrayList(image_official_list,"official_list");

                    basic_info_adapter = new ImageShowAdapter(FormActivity.this,image_official_list,"","basic_Info_official",FormActivity.this);
                    official_recycler_view.setAdapter(basic_info_adapter);

                    PostFormData(image2,basic_info_list.get(0).getId(),"basic_Info_official",image_official_list.size()-1);
                }
            }
            else if (requestCode == 3){
                if (picUri != null){
                    Uri uri = picUri;
                    String image2 = compressImage(uri.toString());


                    ImageUploadPojo pojo = new ImageUploadPojo();
                    pojo.setImage_path(image2);
                    pojo.setImage_status(false);

                    image_Id_proof_list.add(pojo);

                    Id_proof_recycler_view.setVisibility(View.VISIBLE);

                    saveArrayList(image_Id_proof_list,"Id_proof_list");

                    basic_ID_adapter = new ImageShowAdapter(FormActivity.this,image_Id_proof_list,"","Basic_Info_Id_proof",FormActivity.this);
                    Id_proof_recycler_view.setAdapter(basic_ID_adapter);

                    PostFormData(image2,basic_info_list.get(1).getId(),"Basic_Info_Id_proof",image_Id_proof_list.size()-1);
                }
            }
            else if (requestCode == 4){
                if (picUri != null){
                    Uri uri = picUri;
                    String image2 = compressImage(uri.toString());

                    ImageUploadPojo pojo = new ImageUploadPojo();
                    pojo.setImage_path(image2);
                    pojo.setImage_status(false);

                    image_Stores_capabilit_list.add(pojo);

                    saveArrayList(image_Stores_capabilit_list,"Stores_capabilit_list");

                    Stores_capabilit_recycler_view.setVisibility(View.VISIBLE);

                    Stores_capabilit_adapter = new ImageShowAdapter(FormActivity.this,image_Stores_capabilit_list,"","Stores_capabilit",FormActivity.this);
                    Stores_capabilit_recycler_view.setAdapter(Stores_capabilit_adapter);

                    PostFormData(image2,stores_capability_list.get(0).getId(),"Stores_capabilit",image_Stores_capabilit_list.size()-1);
                }
            }

            else if (requestCode == 5){
                if (picUri != null){
                    Uri uri = picUri;
                    String image2 = compressImage(uri.toString());

                    ImageUploadPojo pojo = new ImageUploadPojo();
                    pojo.setImage_path(image2);
                    pojo.setImage_status(false);

                    image_inventory_mngmnt_list.add(pojo);

                    saveArrayList(image_inventory_mngmnt_list,"inventory_mngmnt_list");

                    inventory_mngmnt_recycler_view.setVisibility(View.VISIBLE);

                    inventory_mngmnt_adapter = new ImageShowAdapter(FormActivity.this,image_inventory_mngmnt_list,"","inventory_mngmnt",FormActivity.this);
                    inventory_mngmnt_recycler_view.setAdapter(inventory_mngmnt_adapter);

                    PostFormData(image2,stores_capability_list.get(1).getId(),"inventory_mngmnt",image_inventory_mngmnt_list.size()-1);
                }
            } else if (requestCode == 6){
                if (picUri != null){
                    Uri uri = picUri;
                    String image2 = compressImage(uri.toString());

                    brand_outlet_recycler_view.setVisibility(View.VISIBLE);

                    ImageUploadPojo pojo = new ImageUploadPojo();
                    pojo.setImage_path(image2);
                    pojo.setImage_status(false);

                    image_brand_outlet_list.add(pojo);

                    saveArrayList(image_brand_outlet_list,"brand_outlet_list");

                    brand_outlet_adapter = new ImageShowAdapter(FormActivity.this,image_brand_outlet_list,"","Quality",FormActivity.this);
                    brand_outlet_recycler_view.setAdapter(brand_outlet_adapter);

                    PostFormData(image2,stores_capability_list.get(2).getId(),"brand_outlet",image_brand_outlet_list.size()-1);
                }
            }else if (requestCode == 7){
                if (picUri != null){
                    Uri uri = picUri;
                    String image2 = compressImage(uri.toString());

                    ImageUploadPojo pojo = new ImageUploadPojo();
                    pojo.setImage_path(image2);
                    pojo.setImage_status(false);

                    image_Transportation_list.add(pojo);

                    saveArrayList(image_Transportation_list,"Transportation_list");

                    Transportation_recycler_view.setVisibility(View.VISIBLE);

                    Transportation_adapter = new ImageShowAdapter(FormActivity.this,image_Transportation_list,"","Transportation",FormActivity.this);
                    Transportation_recycler_view.setAdapter(Transportation_adapter);

                    PostFormData(image2,transportation_list.get(0).getId(),"Transportation",image_Transportation_list.size()-1);
                }
            }
            else if (requestCode == 8){
                if (picUri != null){
                    Uri uri = picUri;
                    String image2 = compressImage(uri.toString());

                    ImageUploadPojo pojo = new ImageUploadPojo();
                    pojo.setImage_path(image2);
                    pojo.setImage_status(false);

                    image_signs_labels_list.add(pojo);

                    saveArrayList(image_signs_labels_list,"signs_labels_list");

                    signs_labels_recycler_view.setVisibility(View.VISIBLE);

                    signs_labels_adapter = new ImageShowAdapter(FormActivity.this,image_signs_labels_list,"","signs_labels",FormActivity.this);
                    signs_labels_recycler_view.setAdapter(signs_labels_adapter);

                    PostFormData(image2,Safety_list.get(0).getId(),"saftey_image",image_signs_labels_list.size()-1);

                }
            }
            else if (requestCode == 9){
                if (picUri != null){
                    Uri uri = picUri;
                    String image2 = compressImage(uri.toString());

                    ImageUploadPojo pojo = new ImageUploadPojo();
                    pojo.setImage_path(image2);
                    pojo.setImage_status(false);

                    image_inhouse_ppe_list.add(pojo);

                    saveArrayList(image_inhouse_ppe_list,"inhouse_ppe_list");

                    inhouse_ppe_recycler_view.setVisibility(View.VISIBLE);

                    inhouse_ppe_adapter = new ImageShowAdapter(FormActivity.this,image_inhouse_ppe_list,"","saftey_image_2",FormActivity.this);
                    inhouse_ppe_recycler_view.setAdapter(inhouse_ppe_adapter);

                    PostFormData(image2,Safety_list.get(1).getId(),"saftey_image_2",image_inhouse_ppe_list.size()-1);
                }
            }else if (requestCode == 10){
                if (picUri != null){
                    Uri uri = picUri;
                    String image2 = compressImage(uri.toString());

                    ImageUploadPojo pojo = new ImageUploadPojo();
                    pojo.setImage_path(image2);
                    pojo.setImage_status(false);

                    image_fire_safety_list.add(pojo);

                    saveArrayList(image_fire_safety_list,"fire_safety_list");

                    fire_safety_recycler_view.setVisibility(View.VISIBLE);

                    fire_safety_adapter = new ImageShowAdapter(FormActivity.this,image_fire_safety_list,"","saftey_image_3",FormActivity.this);
                    fire_safety_recycler_view.setAdapter(fire_safety_adapter);

                    PostFormData(image2,Safety_list.get(2).getId(),"saftey_image_3",image_fire_safety_list.size()-1);

                }
            }

        }
    }

    @Override
    public void onClickAtOKButton(String path, int pos, String from) {
        try {
            if (from.equalsIgnoreCase("physical_location")){
                PostFormData(path,physical_location_list.get(0).getId(),from,pos);
            }else if (from.equalsIgnoreCase("basic_Info_official")){
                PostFormData(path,basic_info_list.get(0).getId(),from,pos);
            }else if (from.equalsIgnoreCase("basic_information_Id_Proof")){
                PostFormData(path,basic_info_list.get(1).getId(),from,pos);
            }else if (from.equalsIgnoreCase("Stores_capability")){
                PostFormData(path,stores_capability_list.get(0).getId(),from,pos);
            }else if (from.equalsIgnoreCase("inventory_mngmnt")){
                PostFormData(path,stores_capability_list.get(1).getId(),from,pos);
            }else if (from.equalsIgnoreCase("brand_outlet")){
                PostFormData(path,stores_capability_list.get(2).getId(),from,pos);
            } else if (from.equalsIgnoreCase("transportation")){
                PostFormData(path,transportation_list.get(0).getId(),from,pos);
            }else if (from.equalsIgnoreCase("safety1")){
                PostFormData(path,Safety_list.get(0).getId(),from,pos);
            }else if (from.equalsIgnoreCase("safety2")){
                PostFormData(path,Safety_list.get(1).getId(),from,pos);
            }else if (from.equalsIgnoreCase("safety3")){
                PostFormData(path,Safety_list.get(2).getId(),from,pos);
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
