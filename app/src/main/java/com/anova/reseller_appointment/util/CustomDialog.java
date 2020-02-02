package com.anova.reseller_appointment.util;

import android.app.Activity;
import android.app.ProgressDialog;

public class CustomDialog {

    public static ProgressDialog showLoading(Activity activity) {
        @SuppressWarnings("deprecation") ProgressDialog mProgressDialog = new ProgressDialog(activity);
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setMessage("Loading");
        if (mProgressDialog != null) {
            mProgressDialog.show();
        }
        return mProgressDialog;
    }
}
