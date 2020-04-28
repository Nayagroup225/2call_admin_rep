package com.block.voice;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.block.voice.model.BaseResponse;
import com.block.voice.model.UserInfoRes;
import com.block.voice.preferences.AppSharedPreference;
import com.block.voice.preferences.ProgressHelper;
import com.block.voice.retrofit.ApiCall;
import com.block.voice.retrofit.IApiCallback;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import retrofit2.Response;

public class LoginActivity extends AppCompatActivity implements IApiCallback<BaseResponse> {

    EditText mUserNameEt, mPasswordEt;
    Button mLoginBtn;
    String imei = "";
    TelephonyManager telephonyManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getNotificationInstanceId();
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
                if(Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
                    if (checkSelfPermission(Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                        // READ_PHONE_STATE permission has not been granted.
                        requestReadPhoneStatePermission();
                    } else {
                        imei = telephonyManager.getDeviceId();
                    }
                }else{
                    imei = telephonyManager.getDeviceId();
                }
            } else {
                finish();
            }
        }
    }

//    @Override
//    public void onNewIntent(Intent intent){
//        //called when a new intent for this class is created.
//        // The main case is when the app was in background, a notification arrives to the tray, and the user touches the notification
//
//        super.onNewIntent(intent);
//
//        Bundle extras = intent.getExtras();
//        if (extras != null) {
//            for (String key : extras.keySet()) {
//                Object value = extras.get(key);
//            }
//            String title = extras.getString("title");
//            String message = extras.getString("body");
//            if (message!=null && message.length()>0) {
//                getIntent().removeExtra("body");
//                showNotificationInADialog(title, message);
//            }
//        }
//    }
//
//
//    private void showNotificationInADialog(String title, String message) {
//
//        // show a dialog with the provided title and message
//        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        builder.setTitle(title);
//        builder.setMessage(message);
//        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
//            public void onClick(DialogInterface dialog, int whichButton) {
//                dialog.cancel();
//            }
//        });
//        AlertDialog alert = builder.create();
//        alert.show();
//    }

    protected void getLoginResult(){
        ProgressHelper.showDialog(this);
        String userName = mUserNameEt.getText().toString();
        String password = mPasswordEt.getText().toString();
        if(userName.equals("") || password.equals("")){
            Toast.makeText(this, "Please Input User Name or Password.", Toast.LENGTH_SHORT).show();
            return;
        }
        ApiCall.getInstance().adminLogin(userName, password, VoiceActivity.identity, AppSharedPreference.getInstance(LoginActivity.this).getDeviceToken(), imei,  this);
    }

    private void getNotificationInstanceId() {
        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
                            Log.w("ID", "getInstanceId failed", task.getException());
                            return;
                        }

                        // Get new Instance ID token
                        String token = task.getResult().getToken();

                        // Log and toast
                        //  String msg = getString(R.string.msg_token_fmt, token);
                        AppSharedPreference.getInstance(LoginActivity.this).setDeviceToken(token);
                        Log.d("MyToken", token);
                        //  Toast.makeText(SplashActivity.this, token, Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    public void onSuccess(String type, Response<BaseResponse> response) {
        ProgressHelper.dismiss();
        if (type.equals("check")) {
            Response<BaseResponse> res = response;
            if (res.isSuccessful()) {
                if (res.body().getErrorCode().equals("0")) {
                    Intent intent = new Intent(this, VoiceActivity.class);
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
