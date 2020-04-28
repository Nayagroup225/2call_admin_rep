package com.block.admin;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.block.admin.model.BaseRes;
import com.block.admin.preferences.ProgressHelper;
import com.block.admin.retrofit.ApiCall;
import com.block.admin.retrofit.IApiCallback;

import retrofit2.Response;

public class LoginActivity extends AppCompatActivity implements IApiCallback<BaseRes> {

    EditText mUserNameEt, mPasswordEt;
    Button mLoginBtn;
    String imei = "";
    TelephonyManager telephonyManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mUserNameEt = findViewById(R.id.et_user_name);
        mPasswordEt = findViewById(R.id.et_password);
        mLoginBtn = findViewById(R.id.btn_login);
        mLoginBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                getLoginResult();
            }
        });

        telephonyManager = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                // READ_PHONE_STATE permission has not been granted.
                requestReadPhoneStatePermission();
            } else {
                imei = telephonyManager.getDeviceId();
            }
        }else{
            imei = telephonyManager.getDeviceId();
        }
    }

    private void requestReadPhoneStatePermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.READ_PHONE_STATE)) {
            new AlertDialog.Builder(LoginActivity.this)
                    .setTitle("Permission Request")
                    .setMessage("Please allow that permission")
                    .setCancelable(false)
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            //re-request
                            ActivityCompat.requestPermissions(LoginActivity.this,
                                    new String[]{Manifest.permission.READ_PHONE_STATE},
                                    1);
                        }
                    }).show();
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_PHONE_STATE},
                    1);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {

        if (requestCode == 1) {
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if(checkSelfPermission(Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                    // READ_PHONE_STATE permission has not been granted.
                    requestReadPhoneStatePermission();
                }else{
                    imei = telephonyManager.getDeviceId();
                }
            } else {
                finish();
            }
        }
    }

    protected void getLoginResult(){
        ProgressHelper.dismiss();
        ProgressHelper.showDialog(this);
        String userName = mUserNameEt.getText().toString();
        String password = mPasswordEt.getText().toString();
        if(userName.equals("") || password.equals("")){
            Toast.makeText(this, "Please Input User Name or Password.", Toast.LENGTH_SHORT).show();
            return;
        }
        ApiCall.getInstance().adminLogin(userName, password, imei, AdminActivity.identity, this);
    }

    @Override
    public void onSuccess(String type, Response<BaseRes> response) {
        ProgressHelper.dismiss();
        if (type.equals("check")) {
            Response<BaseRes> res = response;
            if (res.isSuccessful()) {
                if (res.body().getErrorCode().equals("0")) {
                    Intent intent = new Intent(this, AdminActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
                } else showToast(res.body().getErrorMsg());
            } else showToast("Something Wrong");
        }
    }

    protected void showToast(String text){
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onFailure(Object data) {
        ProgressHelper.dismiss();
        showToast("Network Wrong");
    }
}
