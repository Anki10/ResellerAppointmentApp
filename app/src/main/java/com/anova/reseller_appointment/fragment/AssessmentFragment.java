package com.anova.reseller_appointment.fragment;

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
import android.view.LayoutInflater;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
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


public class AssessmentFragment extends BaseFragment implements ListAdapterListener {

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

    EditText ed_physical_location;

    ImageView image_physical_location;
    ImageView sync_physical_location;

    EditText ed_inventory_mngmnt;

    ImageView image_inventory_mngmnt;

    ImageView sync_inventory_mngmnt;


    EditText ed_brand_outlet;

    ImageView image_brand_outlet;

    ImageView sync_brand_outlet;

    // Transportation

    LinearLayout row_ll_Transportation;

    ImageView arrow_Transportation;

    LinearLayout ll_Transportation;

    ImageView image_Transportation;

    //  safety

    LinearLayout row_ll_safety;

    ImageView arrow_expand_safety;

    LinearLayout ll_safety;
    EditText ed_signs_labels;
    RadioButton signs_labels_yes;
    RadioButton signs_labels_no;
    ImageView image_signs_labels;
    ImageView sync_signs_labels;
    EditText ed_inhouse_ppe;
    RadioButton inhouse_ppe_yes;
    RadioButton inhouse_ppe_no;
    ImageView image_inhouse_ppe;

    ImageView sync_inhouse_ppe;

    EditText ed_fire_safety;
    RadioButton fire_safety_yes;
    RadioButton fire_safety_no;
    ImageView image_fire_safety;
    ImageView sync_fire_safety;



    // Transaction & Accounting          //pri1

    LinearLayout row_ll_transaction_accounting;

    ImageView arrow_expand_transaction_accounting;

    LinearLayout ll_transaction_accounting;

    EditText edit_text_brand_outlet;

    RadioButton brand_outlet_yes;
    RadioButton brand_outlet_no;

    ImageView image_brand_outlet;
    ImageView sync_brand_outlet;

    EditText edit_text_availability_products;

    RadioButton availability_products_yes;
    RadioButton availability_products_no;

    ImageView image_available_products;
    ImageView sync_available_products;

    EditText edit_text_transactions_notes;

    RadioButton transactions_notes_yes;
    RadioButton transactions_notes_no;

    ImageView image_transactions_notes;
    ImageView sync_transactions_notes;


    EditText edit_text_notification_warning;

    RadioButton notification_warning_yes;
    RadioButton notification_warning_no;

    ImageView image_notification_warning;
    ImageView sync_notification_warning;

    EditText edit_text_tracking_recording;

    RadioButton tracking_recording_yes;
    RadioButton tracking_recording_no;

    ImageView image_tracking_recording;
    ImageView sync_tracking_recording;

// Additional image

    LinearLayout row_ll_additional_image;

    ImageView arrow_additional_image;

    ImageView image_additional;

    RecyclerView development_additional;

    LinearLayout ll_additional_image;


//pink1

    RecyclerView physical_recycler_view;
    RecyclerView official_recycler_view;
    RecyclerView Id_proof_recycler_view;
    RecyclerView physical_location_recycler_view;
    RecyclerView inventory_mngmnt_recycler_view;
    RecyclerView brand_outlet_recycler_view;
    RecyclerView transport_recycler_view;
    RecyclerView signs_labels_recycler_view;
    RecyclerView inhouse_ppe_recycler_view;
    RecyclerView fire_safety_recycler_view;
    RecyclerView brand_outlet_recycler_view;
    RecyclerView availability_products_recycler_view;
    RecyclerView transaction_notes_recycler_view;
    RecyclerView notification_warning_recycler_view;
    RecyclerView tracking_recording_recycler_view;

    private Dialog dialogLogout;
    private View view;

    private APIService mAPIService;

    private Boolean expand_basic = false,suppliers_status = false,Physical_Location_status = false,
    Process_capability_status = false,Production_Capacity_status = false,Quality_status = false,
            Transportation_status = false,Safety_status = false,Research_Development_status = false,additional_status = false;


    private ArrayList<Basic_Information_list_Pojo>physical_location_list;
    private ArrayList<Basic_Information_list_Pojo>basic_info_list;
    private ArrayList<Basic_Information_list_Pojo>Stores_capability_list;
    private ArrayList<Basic_Information_list_Pojo>safety_list;
    private ArrayList<Basic_Information_list_Pojo>transportation_list;
    private ArrayList<Basic_Information_list_Pojo>Transaction_accounting_list;
    private ArrayList<Basic_Information_list_Pojo>additional_list;


    private ArrayList<VendorDetailsTAB_ResponsePojo>suppliers_vendor_list;
    private ArrayList<VendorDetailsTAB_ResponsePojo>physical_location_vendor_list;
    private ArrayList<VendorDetailsTAB_ResponsePojo>basic_info_vendor_list;
    private ArrayList<VendorDetailsTAB_ResponsePojo>process_capability_vendor_list;
    private ArrayList<VendorDetailsTAB_ResponsePojo>product_capability_vendor_list;
    private ArrayList<VendorDetailsTAB_ResponsePojo>Quality_vendor_list;
    private ArrayList<VendorDetailsTAB_ResponsePojo>transportation_vendor_list;
    private ArrayList<VendorDetailsTAB_ResponsePojo>safety_vendor_list;
    private ArrayList<VendorDetailsTAB_ResponsePojo>development_vendor_list;


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

    RadioGroup radioGroup,radio_equipment;


    TextView tv_video_switch;




    private RecyclerView.LayoutManager mLayoutManager;
    private ImageShowAdapter physical_image_adapter;
    private ImageShowAdapter basic_info_adapter;
    private ImageShowAdapter basic_ID_adapter;
    private ImageShowAdapter physical_location_adapter;
    private ImageShowAdapter inventory_mngmnt_adapter;
    private ImageShowAdapter brand_outlet_adapter;
    private ImageShowAdapter Transportation_adapter;
    private ImageShowAdapter signs_labels_adapter;
    private ImageShowAdapter inhouse_ppe_adapter;
    private ImageShowAdapter fire_safety_adapter;
    private ImageShowAdapter brand_outlet_adapter;
    private ImageShowAdapter transaction_notes_adapter;
    private ImageShowAdapter notification_warning_adapter;
    private ImageShowAdapter tracking_recording_adapter;
    private ImageShowAdapter additional_adapter;


    private ArrayList<ImageUploadPojo> image_list;
    private ArrayList<ImageUploadPojo> image_official_list;
    private ArrayList<ImageUploadPojo> image_Id_proof_list;
    private ArrayList<ImageUploadPojo> image_physical_location_list;
    private ArrayList<ImageUploadPojo> image_inventory_mngmnt_list;
    private ArrayList<ImageUploadPojo> image_brand_outlet_list;
    private ArrayList<ImageUploadPojo> image_Transportation_list;
    private ArrayList<ImageUploadPojo> image_signs_labels_list;
    private ArrayList<ImageUploadPojo> image_inhouse_ppe_list;
    private ArrayList<ImageUploadPojo> image_fire_safety_list;
    private ArrayList<ImageUploadPojo> image_brand_outlet_list;
    private ArrayList<ImageUploadPojo> image_availability_products_list;
    private ArrayList<ImageUploadPojo> image_transactions_notes_list;
    private ArrayList<ImageUploadPojo> image_notification_warning_list;
    private ArrayList<ImageUploadPojo> image_tracking_recording_list;
    private ArrayList<ImageUploadPojo> image_additional_list;

    private Boolean camera_flip_status = false;

    private Boolean other_tab_status = false;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mAPIService = ApiUtils.getAPIService();

        GetFormData();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         container.removeAllViews();
        view =  inflater.inflate(R.layout.fragment_assessment, container, false);


        row_ll_physical = (LinearLayout) view.findViewById(R.id.row_ll_physical);
        ll_physical_location = (LinearLayout) view.findViewById(R.id.ll_physical_location);

        arrow_physical = (ImageView) view.findViewById(R.id.arrow_physical);
        image_physical_geotagged = (ImageView) view.findViewById(R.id.image_physical_geotagged);

        camera_view = (ImageView) view.findViewById(R.id.camera_view);


        row_ll_basic = (LinearLayout) view.findViewById(R.id.row_ll_basic);
        ll_basic_info = (LinearLayout) view.findViewById(R.id.ll_basic_info);

        sync_basic_offical = (ImageView) view.findViewById(R.id.sync_basic_offical);
        sync_basic_Id_proof = (ImageView) view.findViewById(R.id.sync_basic_Id_proof);

        arrow_expand_Basic_Information = (ImageView) view.findViewById(R.id.arrow_expand_Basic_Information);
        image_basic_official = (ImageView) view.findViewById(R.id.image_basic_official);
        image_id_proof = (ImageView) view.findViewById(R.id.image_id_proof);

        ed_basic_offical = (EditText) view.findViewById(R.id.ed_basic_offical);
        ed_basic_ID_proof = (EditText) view.findViewById(R.id.ed_basic_ID_proof);

        row_ll_Stores_capability = (LinearLayout) view.findViewById(R.id.row_ll_Stores_capability);
        ll_Stores_capability = (LinearLayout) view.findViewById(R.id.ll_Stores_capability);


        arrow_Stores_capability = (ImageView) view.findViewById(R.id.arrow_Stores_capability);
        image_physical_location = (ImageView) view.findViewById(R.id.image_physical_location);
        image_inventory_mngmnt = (ImageView) view.findViewById(R.id.image_inventory_mngmnt);
        image_brand_outlet = (ImageView) view.findViewById(R.id.image_brand_outlet);

        ed_brand_outlet= (EditText) view.findViewById(R.id.ed_brand_outlet);
        ed_physical_location= (EditText) view.findViewById(R.id.ed_physical_location);
        ed_inventory_mngmnt= (EditText) view.findViewById(R.id.ed_inventory_mngmnt);

        sync_physical_location= (ImageView) view.findViewById(R.id.sync_physical_location);
        sync_inventory_mngmnt= (ImageView) view.findViewById(R.id.sync_inventory_mngmnt);
        sync_brand_outlet= (ImageView) view.findViewById(R.id.sync_brand_outlet);



        tv_video_switch = (TextView) view.findViewById(R.id.tv_video_switch);


        row_ll_Transportation = (LinearLayout) view.findViewById(R.id.row_ll_Transportation);
        ll_Transportation = (LinearLayout) view.findViewById(R.id.ll_tranportation);

        arrow_Transportation = (ImageView) view.findViewById(R.id.arrow_expand_Transportation);
        image_Transportation = (ImageView) view.findViewById(R.id.image_tranportation);

        row_ll_safety = (LinearLayout) view.findViewById(R.id.row_ll_safety);
        ll_safety = (LinearLayout) view.findViewById(R.id.ll_safety);

        arrow_expand_safety = (ImageView) view.findViewById(R.id.arrow_expand_Safety);

        signs_labels_yes = (RadioButton) view.findViewById(R.id.signs_labels_yes);
        signs_labels_no = (RadioButton) view.findViewById(R.id.signs_labels_yes);

        image_signs_labels = (ImageView) view.findViewById(R.id.image_signs_labels);

        inhouse_ppe_yes = (RadioButton) view.findViewById(R.id.inhouse_ppe_yes);
        inhouse_ppe_no = (RadioButton) view.findViewById(R.id.inhouse_ppe_no);

        image_inhouse_ppe = (ImageView) view.findViewById(R.id.image_inhouse_ppe);

        fire_safety_yes = (RadioButton) view.findViewById(R.id.fire_safety_yes);
        fire_safety_no = (RadioButton) view.findViewById(R.id.fire_safety_no);

        image_fire_safety = (ImageView) view.findViewById(R.id.image_fire_safety);




        row_ll_transaction_accounting = (LinearLayout) view.findViewById(R.id.row_ll_transaction_accounting);
        ll_transaction_accounting = (LinearLayout) view.findViewById(R.id.ll_transaction_accounting);

        arrow_expand_transaction_accounting = (ImageView) view.findViewById(R.id.arrow_expand_transaction_accounting);

        brand_outlet_yes= (RadioButton) view.findViewById(R.id.brand_outlet_yes);
        brand_outlet_no= (RadioButton) view.findViewById(R.id.brand_outlet_no);
        image_brand_outlet= (ImageView) view.findViewById(R.id.image_brand_outlet);

        availability_products_yes= (RadioButton) view.findViewById(R.id.availability_products_yes);
        availability_products_no= (RadioButton) view.findViewById(R.id.availability_products_no);
        image_available_products =(ImageView) view.findViewById(R.id.image_available_products);


        transactions_notes_yes= (RadioButton) view.findViewById(R.id.transactions_notes_yes);
        transactions_notes_no= (RadioButton) view.findViewById(R.id.transactions_notes_no);
        image_transactions_notes =(ImageView) view.findViewById(R.id.image_available_products);


        notification_warning_yes= (RadioButton) view.findViewById(R.id.notification_warning_yes);
        notification_warning_no= (RadioButton) view.findViewById(R.id.notification_warning_no);
        image_notification_warning=(ImageView) view.findViewById(R.id.image_notification_warning);

        tracking_recording_yes= (RadioButton) view.findViewById(R.id.tracking_recording_yes);
        tracking_recording_no= (RadioButton) view.findViewById(R.id.tracking_recording_no);
        image_tracking_recording=(ImageView) view.findViewById(R.id.image_tracking_recording);

        row_ll_additional_image = (LinearLayout) view.findViewById(R.id.row_ll_additional_image);
        arrow_additional_image = (ImageView) view.findViewById(R.id.arrow_additional_image);
        image_additional = (ImageView) view.findViewById(R.id.image_additional);
        development_additional = (RecyclerView) view.findViewById(R.id.development_additional);
        ll_additional_image = (LinearLayout) view.findViewById(R.id.ll_additional_image);



//pink3
        physical_recycler_view = (RecyclerView) view.findViewById(R.id.physical_recycler_view);
        official_recycler_view = (RecyclerView) view.findViewById(R.id.official_recycler_view);
        Id_proof_recycler_view = (RecyclerView) view.findViewById(R.id.Id_proof_recycler_view);
        physical_location_recycler_view = (RecyclerView) view.findViewById(R.id.physical_location_recycler_view);
        inventory_mngmnt_recycler_view = (RecyclerView) view.findViewById(R.id.inventory_mngmnt_recycler_view);
        brand_outlet_recycler_view = (RecyclerView) view.findViewById(R.id.brand_outlet_recycler_view);
        transport_recycler_view = (RecyclerView) view.findViewById(R.id.transport_recycler_view);
        signs_labels_recycler_view = (RecyclerView) view.findViewById(R.id.signs_labels_recycler_view);
        inhouse_ppe_recycler_view = (RecyclerView) view.findViewById(R.id.inhouse_ppe_recycler_view);
        fire_safety_recycler_view = (RecyclerView) view.findViewById(R.id.fire_safety_recycler_view);
        brand_outlet_recycler_view = (RecyclerView) view.findViewById(R.id.brand_outlet_recycler_view);
        availability_products_recycler_view = (RecyclerView) view.findViewById(R.id.availability_products_recycler_view);
        transaction_notes_recycler_view = (RecyclerView) view.findViewById(R.id.transaction_notes_recycler_view);
        notification_warning_recycler_view = (RecyclerView) view.findViewById(R.id.notification_warning_recycler_view);
        tracking_recording_recycler_view = (RecyclerView) view.findViewById(R.id.tracking_recording_recycler_view);



        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity().getApplicationContext(),4);
        physical_recycler_view.setLayoutManager(gridLayoutManager);
        GridLayoutManager gridLayoutManager_official = new GridLayoutManager(getActivity().getApplicationContext(),4);
        official_recycler_view.setLayoutManager(gridLayoutManager_official);
        GridLayoutManager gridLayoutManager_ID_proof = new GridLayoutManager(getActivity().getApplicationContext(),4);
        Id_proof_recycler_view.setLayoutManager(gridLayoutManager_ID_proof);
        GridLayoutManager gridLayoutManager_process_capability = new GridLayoutManager(getActivity().getApplicationContext(),4);
        physical_location_recycler_view.setLayoutManager(gridLayoutManager_physical_location);
        GridLayoutManager gridLayoutManager_production_capacity = new GridLayoutManager(getActivity().getApplicationContext(),4);
        inventory_mngmnt_recycler_view.setLayoutManager(gridLayoutManager_inventory_mngmnt);
        GridLayoutManager gridLayoutManager_Quality = new GridLayoutManager(getActivity().getApplicationContext(),4);
        brand_outlet_recycler_view.setLayoutManager(gridLayoutManager_brand_outlet);
        GridLayoutManager gridLayoutManager_Transportation= new GridLayoutManager(getActivity().getApplicationContext(),4);
        transport_recycler_view.setLayoutManager(gridLayoutManager_Transport);
        GridLayoutManager gridLayoutManager_safety_signage = new GridLayoutManager(getActivity().getApplicationContext(),4);
        signs_labels_recycler_view.setLayoutManager(gridLayoutManager_signs_labels);
        GridLayoutManager gridLayoutManager_safety_equipment = new GridLayoutManager(getActivity().getApplicationContext(),4);
        inhouse_ppe_recycler_view.setLayoutManager(gridLayoutManager_inhouse_ppe);
        GridLayoutManager gridLayoutManager_development_facility = new GridLayoutManager(getActivity().getApplicationContext(),4);
        fire_safety_recycler_view.setLayoutManager(gridLayoutManager_fire_safety);
        GridLayoutManager gridLayoutManager_development_obtained = new GridLayoutManager(getActivity().getApplicationContext(),4);
        brand_outlet_recycler_view.setLayoutManager(gridLayoutManager_brand_outlet);
        GridLayoutManager gridLayoutManager_development_prototype = new GridLayoutManager(getActivity().getApplicationContext(),4);
        availability_products_recycler_view.setLayoutManager(gridLayoutManager_availability_products);
        GridLayoutManager gridLayoutManager_development_prototype = new GridLayoutManager(getActivity().getApplicationContext(),4);
        transaction_notes_recycler_view.setLayoutManager(gridLayoutManager_transaction_notes);
        GridLayoutManager gridLayoutManager_development_prototype = new GridLayoutManager(getActivity().getApplicationContext(),4);
        notification_warning_recycler_view.setLayoutManager(gridLayoutManager_notification_waring);

        GridLayoutManager gridLayoutManager_development_prototype = new GridLayoutManager(getActivity().getApplicationContext(),4);
        tracking_recording_recycler_view.setLayoutManager(gridLayoutManager_tracking_recording);

        GridLayoutManager gridLayoutManager_additional_image = new GridLayoutManager(getActivity().getApplicationContext(),4);
        development_additional.setLayoutManager(gridLayoutManager_additional_image);

        image_list = new ArrayList<>();
        image_official_list = new ArrayList<>();
        image_Id_proof_list = new ArrayList<>();
        image_physical_location_list = new ArrayList<>();
        image_inventory_mngmnt_list = new ArrayList<>();
        image_brand_outlet_list = new ArrayList<>();
        image_Transportation_list = new ArrayList<>();
        image_signs_labels_list = new ArrayList<>();
        image_inhouse_ppe_list = new ArrayList<>();
        image_fire_safety_list= new ArrayList<>();
        image_brand_outlet_list = new ArrayList<>();
        image_transactions_notes_list = new ArrayList<>();
        image_notification_warning_list= new ArrayList<>();
        image_tracking_recording_list= new ArrayList<>();
        image_additional_list = new ArrayList<>();


        sync_safety = (ImageView) view.findViewById(R.id.sync_safety);
        sync_transaction_accounting=(ImageView) view.findViewById(R.id.sync_transaction_accounting);
        radioGroup = (RadioGroup) view.findViewById(R.id.radio_signage);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // checkedId is the RadioButton selected                       //pink4

                switch(checkedId) {
                    case R.id.signs_labels_yes:
                        PostFormRadioData(true,safety_list.get(0).getId(),"saftey_radio");
                        break;
                    case R.id.signs_labels_no:
                        PostFormRadioData(false,safety_list.get(0).getId(),"saftey_radio");
                        break;
                }
            }
        });

        radio_inhouse = (RadioGroup) view.findViewById(R.id.radio_inhouse);

        radio_inhouse.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch(checkedId) {
                    case R.id.inhouse_ppe_yes:
                        PostFormRadioData(true,safety_list.get(1).getId(),"saftey_radio_2");
                        break;
                    case R.id.inhouse_ppe_no:
                        PostFormRadioData(false,safety_list.get(1).getId(),"saftey_radio_2");
                        break;
                }
            }
        });
        radio_fire_safety = (RadioGroup) view.findViewById(R.id.radio_fire_safety);

        radio_fire_safety.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch(checkedId) {
                    case R.id.fire_safety_yes:
                        PostFormRadioData(true,safety_list.get(1).getId(),"saftey_radio_3");
                        break;
                    case R.id.fire_safety_no:
                        PostFormRadioData(false,safety_list.get(1).getId(),"saftey_radio_3");
                        break;
                }
            }
        });
        radio_brand_outlet = (RadioGroup) view.findViewById(R.id.radio_fire_safety);

        radio_brand_outlet.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch(checkedId) {
                    case R.id.brand_outlet_yes:
                        PostFormRadioData(true,transaction_notes_list.get(1).getId(),"transaction_notes_radio");
                        break;
                    case R.id.brand_outlet_no:
                        PostFormRadioData(false,transaction_notes_list.get(1).getId(),"transaction_notes_radio");
                        break;
                }
            }
        });   ///priyanka
        setOnClick();

        suppliers_list = new ArrayList<>();

        physical_location_list = new ArrayList<>();

        basic_info_list = new ArrayList<>();
        process_capability_list = new ArrayList<>();
        product_capability_list = new ArrayList<>();
        Quality_list = new ArrayList<>();
        transportation_list = new ArrayList<>();
        safety_list = new ArrayList<>();
        development_list = new ArrayList<>();
        additional_list = new ArrayList<>();

        suppliers_vendor_list = new ArrayList<>();
        physical_location_vendor_list = new ArrayList<>();
        basic_info_vendor_list = new ArrayList<>();
        process_capability_vendor_list = new ArrayList<>();
        product_capability_vendor_list = new ArrayList<>();
        Quality_vendor_list = new ArrayList<>();
        transportation_vendor_list = new ArrayList<>();
        safety_vendor_list = new ArrayList<>();
        development_vendor_list = new ArrayList<>();

        takePictureButton = (Button) view.findViewById(R.id.btn_takepicture);
        assert takePictureButton != null;

        if (getArrayList("physical_list") != null){
            if (getArrayList("physical_list").size() > 0){
                image_list = getArrayList("physical_list");

                physical_recycler_view.setVisibility(View.VISIBLE);


                physical_image_adapter = new ImageShowAdapter(getActivity(),image_list,"","physical_location",AssessmentFragment.this);
                physical_recycler_view.setAdapter(physical_image_adapter);
            }
        }

        if (getArrayList("official_list") != null){
            if (getArrayList("official_list").size() > 0){
                image_official_list = getArrayList("official_list");

                official_recycler_view.setVisibility(View.VISIBLE);

                basic_info_adapter = new ImageShowAdapter(getActivity(),image_official_list,"","basic_Info_official",AssessmentFragment.this);
                official_recycler_view.setAdapter(basic_info_adapter);
            }
        }
        if (getArrayList("Id_proof_list") != null){
            if (getArrayList("Id_proof_list").size() > 0){
                image_Id_proof_list = getArrayList("Id_proof_list");

                Id_proof_recycler_view.setVisibility(View.VISIBLE);

                basic_ID_adapter = new ImageShowAdapter(getActivity(),image_Id_proof_list,"","basic_information_Id_Proof",AssessmentFragment.this);
                Id_proof_recycler_view.setAdapter(basic_ID_adapter);
            }
        }



        if (getArrayList("process_capability_list") != null){
            if (getArrayList("process_capability_list").size() > 0){
                image_process_capability_list = getArrayList("process_capability_list");

                process_capability_recycler_view.setVisibility(View.VISIBLE);

                process_capability_adapter = new ImageShowAdapter(getActivity(),image_process_capability_list,"","process_capability",this);
                process_capability_recycler_view.setAdapter(process_capability_adapter);
            }
        }


        if (getArrayList("production_capacity_list") != null){
            if (getArrayList("production_capacity_list").size() > 0){
                image_production_capacity_list = getArrayList("production_capacity_list");

                production_capacity_recycler_view.setVisibility(View.VISIBLE);

                production_capacity_adapter = new ImageShowAdapter(getActivity(),image_production_capacity_list,"","production_capacity",this);
                production_capacity_recycler_view.setAdapter(production_capacity_adapter);
            }
        }
        if (getArrayList("Quality_list") != null){
            if (getArrayList("Quality_list").size() > 0){
                image_Quality_list = getArrayList("Quality_list");

                Quality_recycler_view.setVisibility(View.VISIBLE);

                Quality_adapter = new ImageShowAdapter(getActivity(),image_Quality_list,"","Quality",this);
                Quality_recycler_view.setAdapter(Quality_adapter);
            }
        }

        if (getArrayList("Transportation_list") != null){
            if (getArrayList("Transportation_list").size() > 0){
                image_Transportation_list = getArrayList("Transportation_list");

                Transportation_recycler_view.setVisibility(View.VISIBLE);

                Transportation_adapter = new ImageShowAdapter(getActivity(),image_Transportation_list,"","transportation",this);
                Transportation_recycler_view.setAdapter(Transportation_adapter);
            }
        }

        if (getArrayList("safety_signage_list") != null){
            if (getArrayList("safety_signage_list").size() > 0){
                image_safety_signage_list = getArrayList("safety_signage_list");

                safety_signage_recycler_view.setVisibility(View.VISIBLE);

                safety_signage_adapter = new ImageShowAdapter(getActivity(),image_safety_signage_list,"","safety1",this);
                safety_signage_recycler_view.setAdapter(safety_signage_adapter);
            }
        }

        if (getArrayList("safety_equipment_list") != null){
            if (getArrayList("safety_equipment_list").size() > 0){
                image_safety_equipment_list = getArrayList("safety_equipment_list");

                safety_equipment_recycler_view.setVisibility(View.VISIBLE);

                safety_equipment_adapter = new ImageShowAdapter(getActivity(),image_safety_equipment_list,"","safety2",this);
                safety_equipment_recycler_view.setAdapter(safety_equipment_adapter);
            }
        }

        if (getArrayList("development_facility_list") != null){
            if (getArrayList("development_facility_list").size() > 0){
                image_development_facility_list = getArrayList("development_facility_list");

                development_facility_recycler_view.setVisibility(View.VISIBLE);

                development_facility_adapter = new ImageShowAdapter(getActivity(),image_development_facility_list,"","Development_1",this);
                development_facility_recycler_view.setAdapter(development_facility_adapter);
            }
        }

        if (getArrayList("development_obtained_list") != null){
            if (getArrayList("development_obtained_list").size() > 0){
                image_development_obtained_list = getArrayList("development_obtained_list");

                development_obtained_recycler_view.setVisibility(View.VISIBLE);

                development_obtained_adapter = new ImageShowAdapter(getActivity(),image_development_obtained_list,"","Development_2",this);
                development_obtained_recycler_view.setAdapter(development_obtained_adapter);
            }
        }

        if (getArrayList("development_prototype_list") != null){
            if (getArrayList("development_prototype_list").size() > 0){
                image_development_prototype_list = getArrayList("development_prototype_list");

                development_prototype_recycler_view.setVisibility(View.VISIBLE);

                development_prototype_adapter = new ImageShowAdapter(getActivity(),image_development_prototype_list,"","Development_3",this);
                development_prototype_recycler_view.setAdapter(development_prototype_adapter);
            }
        }

        if (getArrayList("Additional") != null){
            if (getArrayList("Additional").size() > 0){
                image_additional_list = getArrayList("Additional");

                development_additional.setVisibility(View.VISIBLE);

                additional_adapter = new ImageShowAdapter(getActivity(),image_additional_list,"","Additional",this);
                development_additional.setAdapter(additional_adapter);
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

        if (getFromPrefs("suppliers_text") != null){
            if (getFromPrefs("suppliers_text").length() > 0){
                edit_text_suppliers.setText(getFromPrefs("suppliers_text"));
            }
        }


        return view;
    }

    private void setOnClick(){

        tv_video_switch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                call_status_change();

                PostCallDiscoonect();

              /*  call_status_change();

                call_value = "video";

                final FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.fragment1, new VideoFragment(), "Video Fragment");
                ft.addToBackStack(null);
                ft.commit();*/

            }
        });
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

        row_ll_process_capability.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    if (Process_capability_status){
                        ll_proess_capability.setVisibility(View.GONE);

                        arrow_Process_capability.setImageResource(R.mipmap.expandable_down_arrow);

                        Process_capability_status = false;
                    }else {
                        ll_proess_capability.setVisibility(View.VISIBLE);

                        arrow_Process_capability.setImageResource(R.mipmap.expandable_up_arrow);

                        Process_capability_status = true;
                    }
                }
        });

        image_process_capability.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                captureImage(4);


            }
        });

        row_ll_production.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    if (Production_Capacity_status){

                        arrow_Production_Capacity.setImageResource(R.mipmap.expandable_down_arrow);

                        ll_production_capability.setVisibility(View.GONE);

                        Production_Capacity_status = false;
                    }else {

                        ll_production_capability.setVisibility(View.VISIBLE);

                        arrow_Production_Capacity.setImageResource(R.mipmap.expandable_up_arrow);

                        Production_Capacity_status = true;

                    }
                }
        });

        image_production_capability.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                captureImage(5);

            }
        });


        row_ll_Quality.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    if (Quality_status){

                        arrow_expand_Quality.setImageResource(R.mipmap.expandable_down_arrow);

                        ll_Quality.setVisibility(View.GONE);

                        Quality_status = false;


                    }else {
                        ll_Quality.setVisibility(View.VISIBLE);

                        arrow_expand_Quality.setImageResource(R.mipmap.expandable_up_arrow);

                        Quality_status = true;
                    }
                }
        });

        image_Quality.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                captureImage(6);

            }
        });

        row_ll_suppliers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    if (suppliers_status){
                        arrow_expand_Suppliers.setImageResource(R.mipmap.expandable_down_arrow);

                        ll_suppliers.setVisibility(View.GONE);

                        suppliers_status = false;
                    }else {
                        ll_suppliers.setVisibility(View.VISIBLE);

                        arrow_expand_Suppliers.setImageResource(R.mipmap.expandable_down_arrow);

                        suppliers_status = true;
                    }
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
                    if (Safety_status){

                        ll_safety.setVisibility(View.GONE);

                        arrow_expand_Safety.setImageResource(R.mipmap.expandable_down_arrow);

                        Safety_status = false;

                    }else {

                        ll_safety.setVisibility(View.VISIBLE);

                        arrow_expand_Safety.setImageResource(R.mipmap.expandable_up_arrow);

                        Safety_status = true;

                    }
                }
        });

        image_safety_signage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                captureImage(8);
            }
        });

        image_safety_equipment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                captureImage(9);
            }
        });

        row_ll_development.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    if (Research_Development_status){

                        arrow_expand_Development.setImageResource(R.mipmap.expandable_down_arrow);

                        ll_development.setVisibility(View.GONE);

                        Research_Development_status = false;
                    }else {

                        arrow_expand_Development.setImageResource(R.mipmap.expandable_up_arrow);

                        ll_development.setVisibility(View.VISIBLE);

                        Research_Development_status = true;
                    }
                }
        });

        row_ll_additional_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (additional_status){
                    arrow_additional_image.setImageResource(R.mipmap.expandable_down_arrow);

                    ll_additional_image.setVisibility(View.GONE);

                    additional_status = false;

                }else {
                    arrow_additional_image.setImageResource(R.mipmap.expandable_up_arrow);

                    ll_additional_image.setVisibility(View.VISIBLE);

                    additional_status = true;


                }
            }
        });

        image_development_facility.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                captureImage(10);
            }
        });

        image_development_obtained.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                captureImage(11);

            }
        });

        image_development_prototype.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                captureImage(12);

            }
        });

        image_additional.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                captureImage(13);
            }
        });

        sync_basic_offical.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                PostFormEditTextData(ed_basic_offical.getText().toString(),basic_info_list.get(0).getId(),"basic_Info_official_text");
            }
        });

        sync_basic_Id_proof.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                PostFormEditTextData(ed_basic_ID_proof.getText().toString(),basic_info_list.get(1).getId(),"Basic_Info_Id_proof_text");
            }
        });


        sync_suppliers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PostFormEditTextData(edit_text_suppliers.getText().toString(),suppliers_list.get(0).getId(),"suppliers");
            }
        });

    }


    private void GetFormData(){
        mAPIService.getAllData("application/json").enqueue(new Callback<GetAllFormData>() {
            @Override
            public void onResponse(Call<GetAllFormData> call, Response<GetAllFormData> response) {
                if (response.body() != null){
                    System.out.println("xxxx");

                    suppliers_list = response.body().getData().getSuppliers();

                    physical_location_list = response.body().getData().getPhysical_location();

                    basic_info_list = response.body().getData().getBasic_information();

                    process_capability_list = response.body().getData().getProcess_capability();

                    product_capability_list = response.body().getData().getProduction_capacity();

                    transportation_list = response.body().getData().getTransportation();

                    safety_list = response.body().getData().getSafety();

                    development_list = response.body().getData().getResearch_and_development();

                    Quality_list = response.body().getData().getQuality();

                    additional_list = response.body().getData().getAdditional_images();

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

                                Toast.makeText(getActivity(),"Data sync successfully",Toast.LENGTH_SHORT).show();


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

                                }else if (from.equalsIgnoreCase("process_capability")){

                                    ImageUploadPojo pojo = image_process_capability_list.get(pos);
                                    pojo.setImage_status(true);

                                    image_process_capability_list.set(pos,pojo);

                                    saveArrayList(image_process_capability_list,"process_capability_list");

                                    process_capability_adapter.notifyDataSetChanged();

                                }else if (from.equalsIgnoreCase("production_capability")){
                                    ImageUploadPojo pojo = image_production_capacity_list.get(pos);
                                    pojo.setImage_status(true);

                                    image_production_capacity_list.set(pos,pojo);

                                    saveArrayList(image_production_capacity_list,"production_capacity_list");

                                    production_capacity_adapter.notifyDataSetChanged();

                                }else if (from.equalsIgnoreCase("Transportation")){

                                    ImageUploadPojo pojo = image_Transportation_list.get(pos);
                                    pojo.setImage_status(true);

                                    image_Transportation_list.set(pos,pojo);

                                    saveArrayList(image_Transportation_list,"Transportation_list");

                                    Transportation_adapter.notifyDataSetChanged();

                                }else if (from.equalsIgnoreCase("saftey_image")){

                                    ImageUploadPojo pojo = image_safety_signage_list.get(pos);
                                    pojo.setImage_status(true);

                                    image_safety_signage_list.set(pos,pojo);

                                    saveArrayList(image_safety_signage_list,"safety_signage_list");

                                    safety_signage_adapter.notifyDataSetChanged();

                                }else if (from.equalsIgnoreCase("saftey_image_2")){

                                    ImageUploadPojo pojo = image_safety_equipment_list.get(pos);
                                    pojo.setImage_status(true);

                                    image_safety_equipment_list.set(pos,pojo);

                                    saveArrayList(image_safety_equipment_list,"safety_equipment_list");

                                    safety_equipment_adapter.notifyDataSetChanged();

                                }else if (from.equalsIgnoreCase("Development_1")){

                                    ImageUploadPojo pojo = image_development_facility_list.get(pos);
                                    pojo.setImage_status(true);

                                    image_development_facility_list.set(pos,pojo);

                                    saveArrayList(image_development_facility_list,"development_facility_list");

                                    development_facility_adapter.notifyDataSetChanged();

                                }else if (from.equalsIgnoreCase("Development_2")){

                                    ImageUploadPojo pojo = image_development_obtained_list.get(pos);
                                    pojo.setImage_status(true);

                                    image_development_obtained_list.set(pos,pojo);

                                    saveArrayList(image_development_obtained_list,"development_obtained_list");

                                    development_obtained_adapter.notifyDataSetChanged();

                                }else if (from.equalsIgnoreCase("Development_3")){
                                    ImageUploadPojo pojo = image_development_prototype_list.get(pos);
                                    pojo.setImage_status(true);

                                    image_development_prototype_list.set(pos,pojo);

                                    saveArrayList(image_development_prototype_list,"development_prototype_list");

                                    development_prototype_adapter.notifyDataSetChanged();

                                }else if (from.equalsIgnoreCase("Quality")){

                                    ImageUploadPojo pojo = image_Quality_list.get(pos);
                                    pojo.setImage_status(true);

                                    image_Quality_list.set(pos,pojo);

                                    saveArrayList(image_Quality_list,"Quality_list");

                                    Quality_adapter.notifyDataSetChanged();

                                }else if (from.equalsIgnoreCase("Additional")){
                                    ImageUploadPojo pojo = image_additional_list.get(pos);
                                    pojo.setImage_status(true);

                                    saveArrayList(image_additional_list,"Additional");

                                    additional_adapter.notifyDataSetChanged();


                                }
                            }else {
                           Toast.makeText(getActivity(),"Image upload failed",Toast.LENGTH_LONG).show();
                            }
                        }else {
                            Toast.makeText(getActivity(),"Image upload failed",Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<SubmitFormResponse> call, Throwable t) {
                        System.out.println("xxx fail");

                        Toast.makeText(getActivity(),"Image upload failed",Toast.LENGTH_LONG).show();

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

                                Toast.makeText(getActivity(), "Data sync successfully", Toast.LENGTH_SHORT).show();

                                if (from.equalsIgnoreCase("basic_Info_official_text")) {
                                    saveIntoPrefs("basic_Info_official_text",ed_basic_offical.getText().toString());
                                } else if (from.equalsIgnoreCase("Basic_Info_Id_proof_text")) {
                                    saveIntoPrefs("Basic_Info_Id_proof_text",ed_basic_ID_proof.getText().toString());
                                } else if (from.equalsIgnoreCase("suppliers")) {
                                    saveIntoPrefs("suppliers_text",edit_text_suppliers.getText().toString());
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
                        getActivity().runOnUiThread(new Runnable() {
                            public void run() {
                 //               d.dismiss();
                            }
                        });
                        if (response.body() != null){
                            if (response.body().getSuccess()){
                                Toast.makeText(getActivity(),"Data sync successfully",Toast.LENGTH_LONG).show();

                            }else {
                                Toast.makeText(getActivity(),response.body().getMessage(),Toast.LENGTH_LONG).show();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<SubmitFormResponse> call, Throwable t) {
                        System.out.println("xxx fail");

                        getActivity().runOnUiThread(new Runnable() {
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
                Toast.makeText(getActivity(), "Sorry!!!, you can't use this app without granting permission", Toast.LENGTH_LONG).show();
                getActivity().finish();
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
    private void getVendorDetails(final String from,final int pos){
        VendorDetailsRequestPojo requestPojo = new VendorDetailsRequestPojo();
        requestPojo.setForm_id(getFromPrefs("form_id"));
        requestPojo.setType(getFromPrefs("seller_assessed_as"));
        mAPIService.GetVendorDetails("application/json",requestPojo).enqueue(new Callback<VendorDetailsResponsePojo>() {
            @Override
            public void onResponse(Call<VendorDetailsResponsePojo> call, Response<VendorDetailsResponsePojo> response) {
                if (response.body() != null){
                    if (response.body().getSuccess()){
                        suppliers_vendor_list = response.body().getData().getSuppliers();
                        physical_location_vendor_list = response.body().getData().getPhysical_location();
                        basic_info_vendor_list = response.body().getData().getBasic_information();
                        process_capability_vendor_list = response.body().getData().getProcess_capability();
                        product_capability_vendor_list = response.body().getData().getProduction_capacity();
                        Quality_vendor_list = response.body().getData().getQuality();
                        transportation_vendor_list = response.body().getData().getTransportation();
                        safety_vendor_list = response.body().getData().getSafety();
                        development_vendor_list = response.body().getData().getResearch_and_development();

                    }
                }

            }

            @Override
            public void onFailure(Call<VendorDetailsResponsePojo> call, Throwable t) {

            }
        });
    }



    public void saveArrayList(ArrayList<ImageUploadPojo> list, String key){
        SharedPreferences prefs = getActivity().getSharedPreferences(AppConstant.PREF_NAME, getActivity().MODE_PRIVATE);
        SharedPreferences.Editor edit = prefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(list);
        edit.putString(key, json);
        edit.apply();     // This line is IMPORTANT !!!
    }

    public ArrayList<ImageUploadPojo> getArrayList(String key){
        SharedPreferences prefs = getActivity().getSharedPreferences(AppConstant.PREF_NAME, getActivity().MODE_PRIVATE);
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
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, FileProvider.getUriForFile(getActivity(), getActivity().getApplicationContext().getPackageName() + ".provider", imageF));
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

                    physical_image_adapter = new ImageShowAdapter(getActivity(),image_list,"","physical_location",AssessmentFragment.this);
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

                    basic_info_adapter = new ImageShowAdapter(getActivity(),image_official_list,"","basic_Info_official",AssessmentFragment.this);
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

                    basic_ID_adapter = new ImageShowAdapter(getActivity(),image_Id_proof_list,"","Basic_Info_Id_proof",AssessmentFragment.this);
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

                    image_process_capability_list.add(pojo);

                    saveArrayList(image_process_capability_list,"process_capability_list");

                    process_capability_recycler_view.setVisibility(View.VISIBLE);

                    process_capability_adapter = new ImageShowAdapter(getActivity(),image_process_capability_list,"","process_capability",AssessmentFragment.this);
                    process_capability_recycler_view.setAdapter(process_capability_adapter);

                    PostFormData(image2,process_capability_list.get(0).getId(),"process_capability",image_process_capability_list.size()-1);
                }
            }

            else if (requestCode == 5){
                if (picUri != null){
                    Uri uri = picUri;
                    String image2 = compressImage(uri.toString());

                    ImageUploadPojo pojo = new ImageUploadPojo();
                    pojo.setImage_path(image2);
                    pojo.setImage_status(false);

                    image_production_capacity_list.add(pojo);

                    saveArrayList(image_production_capacity_list,"production_capacity_list");

                    production_capacity_recycler_view.setVisibility(View.VISIBLE);

                    production_capacity_adapter = new ImageShowAdapter(getActivity(),image_production_capacity_list,"","production_capability",AssessmentFragment.this);
                    production_capacity_recycler_view.setAdapter(production_capacity_adapter);

                    PostFormData(image2,product_capability_list.get(0).getId(),"production_capability",image_production_capacity_list.size()-1);
                }
            } else if (requestCode == 6){
                if (picUri != null){
                    Uri uri = picUri;
                    String image2 = compressImage(uri.toString());

                    Quality_recycler_view.setVisibility(View.VISIBLE);

                    ImageUploadPojo pojo = new ImageUploadPojo();
                    pojo.setImage_path(image2);
                    pojo.setImage_status(false);

                    image_Quality_list.add(pojo);

                    saveArrayList(image_Quality_list,"Quality_list");

                    Quality_adapter = new ImageShowAdapter(getActivity(),image_Quality_list,"","Quality",AssessmentFragment.this);
                    Quality_recycler_view.setAdapter(Quality_adapter);

                    PostFormData(image2,Quality_list.get(0).getId(),"Quality",image_Quality_list.size()-1);
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

                    Transportation_adapter = new ImageShowAdapter(getActivity(),image_Transportation_list,"","Transportation",AssessmentFragment.this);
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

                    image_safety_signage_list.add(pojo);

                    saveArrayList(image_safety_signage_list,"safety_signage_list");

                    safety_signage_recycler_view.setVisibility(View.VISIBLE);

                    safety_signage_adapter = new ImageShowAdapter(getActivity(),image_safety_signage_list,"","saftey_image",AssessmentFragment.this);
                    safety_signage_recycler_view.setAdapter(safety_signage_adapter);

                    PostFormData(image2,safety_list.get(0).getId(),"saftey_image",image_safety_signage_list.size()-1);

                }
            }
            else if (requestCode == 9){
                if (picUri != null){
                    Uri uri = picUri;
                    String image2 = compressImage(uri.toString());

                    ImageUploadPojo pojo = new ImageUploadPojo();
                    pojo.setImage_path(image2);
                    pojo.setImage_status(false);

                    image_safety_equipment_list.add(pojo);

                    saveArrayList(image_safety_equipment_list,"safety_equipment_list");

                    safety_equipment_recycler_view.setVisibility(View.VISIBLE);

                    safety_equipment_adapter = new ImageShowAdapter(getActivity(),image_safety_equipment_list,"","saftey_image_2",AssessmentFragment.this);
                    safety_equipment_recycler_view.setAdapter(safety_equipment_adapter);

                    PostFormData(image2,safety_list.get(1).getId(),"saftey_image_2",image_safety_equipment_list.size()-1);
                }
            }else if (requestCode == 10){
                if (picUri != null){
                    Uri uri = picUri;
                    String image2 = compressImage(uri.toString());

                    ImageUploadPojo pojo = new ImageUploadPojo();
                    pojo.setImage_path(image2);
                    pojo.setImage_status(false);

                    image_development_facility_list.add(pojo);

                    saveArrayList(image_development_facility_list,"development_facility_list");

                    development_facility_recycler_view.setVisibility(View.VISIBLE);

                    development_facility_adapter = new ImageShowAdapter(getActivity(),image_development_facility_list,"","Development_1",AssessmentFragment.this);
                    development_facility_recycler_view.setAdapter(development_facility_adapter);

                    PostFormData(image2,development_list.get(0).getId(),"Development_1",image_development_facility_list.size()-1);

                }
            }
            else if (requestCode == 11){
                if (picUri != null){
                    Uri uri = picUri;
                    String image2 = compressImage(uri.toString());

                    ImageUploadPojo pojo = new ImageUploadPojo();
                    pojo.setImage_path(image2);
                    pojo.setImage_status(false);

                    image_development_obtained_list.add(pojo);

                    saveArrayList(image_development_obtained_list,"development_obtained_list");

                    development_obtained_recycler_view.setVisibility(View.VISIBLE);

                    development_obtained_adapter = new ImageShowAdapter(getActivity(),image_development_obtained_list,"","Development_2",AssessmentFragment.this);
                    development_obtained_recycler_view.setAdapter(development_obtained_adapter);

                    PostFormData(image2,development_list.get(1).getId(),"Development_2",image_development_obtained_list.size()-1);
                }


            }

            else if (requestCode == 12){
                if (picUri != null){
                    Uri uri = picUri;
                    String image2 = compressImage(uri.toString());

                    ImageUploadPojo pojo = new ImageUploadPojo();
                    pojo.setImage_path(image2);
                    pojo.setImage_status(false);

                    image_development_prototype_list.add(pojo);

                    saveArrayList(image_development_prototype_list,"development_prototype_list");

                    development_prototype_recycler_view.setVisibility(View.VISIBLE);

                    development_prototype_adapter = new ImageShowAdapter(getActivity(),image_development_prototype_list,"","Development_3",AssessmentFragment.this);
                    development_prototype_recycler_view.setAdapter(development_prototype_adapter);

                    PostFormData(image2,development_list.get(2).getId(),"Development_3",image_development_prototype_list.size()-1);
                }
            }
            else if (requestCode == 13){
                if (picUri != null){
                    Uri uri = picUri;
                    String image = compressImage(uri.toString());

                    ImageUploadPojo Pojo = new ImageUploadPojo();
                    Pojo.setImage_path(image);
                    Pojo.setImage_status(false);

                    image_additional_list.add(Pojo);

                    saveArrayList(image_additional_list,"additional_image");

                    development_additional.setVisibility(View.VISIBLE);

                    additional_adapter = new ImageShowAdapter(getActivity(),image_additional_list,"","Additional",AssessmentFragment.this);
                    development_additional.setAdapter(additional_adapter);

                    PostFormData(image,additional_list.get(0).getId(),"Additional",image_additional_list.size()-1);


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
            }else if (from.equalsIgnoreCase("process_capability")){
                PostFormData(path,process_capability_list.get(0).getId(),from,pos);
            }else if (from.equalsIgnoreCase("production_capacity")){
                PostFormData(path,product_capability_list.get(0).getId(),from,pos);
            }else if (from.equalsIgnoreCase("Quality")){
                PostFormData(path,Quality_list.get(0).getId(),from,pos);
            }else if (from.equalsIgnoreCase("transportation")){
                PostFormData(path,transportation_list.get(0).getId(),from,pos);
            }else if (from.equalsIgnoreCase("safety1")){
                PostFormData(path,safety_list.get(0).getId(),from,pos);
            }else if (from.equalsIgnoreCase("safety2")){
                PostFormData(path,safety_list.get(1).getId(),from,pos);
            }else if (from.equalsIgnoreCase("Development_1")){
                PostFormData(path,development_list.get(0).getId(),from,pos);
            }else if (from.equalsIgnoreCase("Development_2")){
                PostFormData(path,development_list.get(1).getId(),from,pos);
            }else if (from.equalsIgnoreCase("Development_3")){
                PostFormData(path,development_list.get(2).getId(),from,pos);
            }else if (from.equalsIgnoreCase("Additional")){
                PostFormData(path,additional_list.get(0).getId(),from,pos);
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}


