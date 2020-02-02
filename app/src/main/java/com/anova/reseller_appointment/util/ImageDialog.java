package com.anova.reseller_appointment.util;

import android.app.Activity;
import android.app.ProgressDialog;

/**
 * Created by Ankit on 30-01-2019.
 */

public class ImageDialog {

    public static ProgressDialog showLoading(Activity activity) {
        @SuppressWarnings("deprecation") ProgressDialog mProgressDialog = new ProgressDialog(activity);
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setMessage("Uploading");
        if (mProgressDialog != null) {
            mProgressDialog.show();
        }
        return mProgressDialog;
    }
}
