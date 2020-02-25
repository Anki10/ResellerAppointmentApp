package com.anova.reseller_appointment.api;

/**
 * Created by raj on 3/16/2018.
 */

public class ApiUtils {

    private ApiUtils() {}


  //    public static final String BASE_URL = "https://assessment.qcin.org/opentok/"; // 13.233.90.40:5002
   //   public static final String BASE_URL = "http://192.168.15.161:5000/opentok/"; // 13.233.90.40:5002
      public static final String BASE_URL = "http://13.127.83.224:5000/opentok/"; // 13.233.90.40:5002


    // public static final String BASE_URL = "http://13.127.83.224:59017/api/";

    public static APIService getAPIService() {

        return RetrofitClient.getClient(BASE_URL).create(APIService.class);
    }
}
