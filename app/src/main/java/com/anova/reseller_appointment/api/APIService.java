package com.anova.reseller_appointment.api;



import com.anova.reseller_appointment.pojo.CallDisconnectRequest;
import com.anova.reseller_appointment.pojo.CallDisconnectResponse;
import com.anova.reseller_appointment.pojo.DeleteRequestPojo;
import com.anova.reseller_appointment.pojo.DeleteResponsePojo;
import com.anova.reseller_appointment.pojo.GetAllFormData;
import com.anova.reseller_appointment.pojo.GetUserResponse;
import com.anova.reseller_appointment.pojo.LoginRequest;
import com.anova.reseller_appointment.pojo.LoginResponse;
import com.anova.reseller_appointment.pojo.PostVideoRequest;
import com.anova.reseller_appointment.pojo.SubmitFormRadioRequest;
import com.anova.reseller_appointment.pojo.SubmitFormRequest;
import com.anova.reseller_appointment.pojo.SubmitFormResponse;
import com.anova.reseller_appointment.pojo.UserCallResponse;
import com.anova.reseller_appointment.pojo.VendorDetailsRequestPojo;
import com.anova.reseller_appointment.pojo.VendorDetailsResponsePojo;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

/**
 * Created by raj on 3/16/2018.
 */

public interface APIService {

    @POST("login")
    Call<LoginResponse> loginrequest(@Header("Content-Type") String Content_Type, @Body LoginRequest request);

    @GET("getUsers")
    Call<GetUserResponse>getUserList(@Header("Authorization") String Authorization);

    @POST("createSession")
    Call<UserCallResponse>PostUserCall(@Header("Authorization") String Authorization, @Body PostVideoRequest request);

    @POST("disconnectSession")
    Call<CallDisconnectResponse> PostCallDisconnect(@Header("Authorization") String Authorization, @Body CallDisconnectRequest request);

    @GET("getform")
    Call<GetAllFormData> getAllData(@Header("Content-Type") String Content_Type);

    @POST("submitresponse")
    Call<SubmitFormResponse> PostForm(@Header("Content-Type") String Content_Type, @Body SubmitFormRequest request);

    @POST("submitresponse")
    Call<SubmitFormResponse> PostRadioForm(@Header("Content-Type") String Content_Type, @Body SubmitFormRadioRequest request);

    @POST("vendordetails")
    Call<VendorDetailsResponsePojo> GetVendorDetails(@Header("Content-Type") String Content_Type, @Body VendorDetailsRequestPojo request);

    @POST("deleteimage")
    Call<DeleteResponsePojo> GetDeleteResponse(@Header("Content-Type") String Content_Type, @Body DeleteRequestPojo request);


}
