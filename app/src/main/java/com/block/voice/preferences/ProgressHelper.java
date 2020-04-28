package com.block.voice.preferences;

import android.app.ProgressDialog;
import android.content.Context;

import com.block.voice.R;

public class ProgressHelper {
    private static ProgressDialog dialog;

    public static void showDialog(Context context) {
        try {
            dialog = new ProgressDialog(context, R.style.MyProgressDialogStyle);
            dialog.setCancelable(false);
            dialog.setMessage("Please Wait...");
            dialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void dismiss() {
        try {
            if (dialog != null)
                dialog.dismiss();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}
