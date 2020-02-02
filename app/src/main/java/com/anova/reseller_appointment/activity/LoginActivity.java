package com.anova.reseller_appointment.activity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.anova.reseller_appointment.R;
import com.anova.reseller_appointment.View.AppConstant;
import com.anova.reseller_appointment.api.APIService;
import com.anova.reseller_appointment.api.ApiUtils;
import com.anova.reseller_appointment.pojo.LoginRequest;
import com.anova.reseller_appointment.pojo.LoginResponse;
import com.anova.reseller_appointment.util.AppDialog;
import com.anova.reseller_appointment.util.ConnectionDetector;
import com.anova.reseller_appointment.util.PermissionResultCallback;
import com.anova.reseller_appointment.util.PermissionUtils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity implements ActivityCompat.OnRequestPermissionsResultCallback, PermissionResultCallback {

    @BindView(R.id.login_userName)
    EditText login_userName;

    @BindView(R.id.login_password)
    EditText login_password;

    @BindView(R.id.btn_yes_ok)
    Button btn_yes_ok;

    private APIService mAPIService;

    Boolean isInternetPresent = false;
    // Connection detector class
    ConnectionDetector cd;

    PermissionUtils permissionUtils;
    protected static final int REQUEST_CHECK_SETTINGS = 0x1;

    ArrayList<String> permissions=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ButterKnife.bind(this);

        permissionUtils=new PermissionUtils(LoginActivity.this);

        permissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        permissions.add(Manifest.permission.CAMERA);
        permissions.add(Manifest.permission.RECORD_AUDIO);

        permissionUtils.check_permission(permissions,"All the permissions are required to access the app functionality",1);

        mAPIService = ApiUtils.getAPIService();

        cd = new ConnectionDetector(getApplicationContext());
        // get Internet status
        isInternetPresent = cd.isConnectingToInternet();

        login_userName.setText("testing456@gmail.com");
        login_password.setText("IoQ1%yIg");

        btn_yes_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                isInternetPresent = cd.isConnectingToInternet();
                if (isInternetPresent){
                   Login();
                }else {
                    Toast.makeText(LoginActivity.this, AppConstant.NO_INTERNET_CONNECTED,Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void Login(){
        final ProgressDialog d = AppDialog.showLoading(LoginActivity.this);
        d.setCanceledOnTouchOutside(false);

        LoginRequest request = new LoginRequest();
        request.setUser_name(login_userName.getText().toString());
        request.setPassword(login_password.getText().toString());

        mAPIService.loginrequest("application/json",request).enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                d.dismiss();
                if (response.body() != null){
                   if (response.body().getSuccess()){
                       Toast.makeText(LoginActivity.this,response.body().getMessage(),Toast.LENGTH_LONG).show();

                       saveIntoPrefs("token_id",response.body().getToken());

                        Intent intent = new Intent(LoginActivity.this, DataGetActivity.class);
                        startActivity(intent);


                   }else {
                       Toast.makeText(LoginActivity.this,response.body().getMessage(),Toast.LENGTH_LONG).show();
                   }
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                System.out.println("xx faill");

                d.dismiss();
            }
        });
    }

    public void saveIntoPrefs(String key, String value) {
        SharedPreferences prefs = getSharedPreferences(AppConstant.PREF_NAME, MODE_PRIVATE);
        SharedPreferences.Editor edit = prefs.edit();
        edit.putString(key, value);
        edit.commit();
    }

    @Override
    public void PermissionGranted(int request_code) {

    }

    @Override
    public void PartialPermissionGranted(int request_code, ArrayList<String> granted_permissions) {

    }

    @Override
    public void PermissionDenied(int request_code) {

    }

    @Override
    public void NeverAskAgain(int request_code) {

    }
}
