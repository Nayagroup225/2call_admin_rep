package com.block.admin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.block.admin.adapter.DeviceRecyclerAdapter;
import com.block.admin.model.BaseRes;
import com.block.admin.model.UserListRes;
import com.block.admin.preferences.AppSharedPreference;
import com.block.admin.preferences.IRecyclerClickListener;
import com.block.admin.preferences.ProgressHelper;
import com.block.admin.retrofit.ApiCall;
import com.block.admin.retrofit.IApiCallback;

import retrofit2.Response;


public class AdminActivity extends AppCompatActivity implements IApiCallback, IRecyclerClickListener, SwipeRefreshLayout.OnRefreshListener {

    RecyclerView mDeviceList;
    DeviceRecyclerAdapter mAdapter;
    TextView mTitle, mNoResult;
    SwipeRefreshLayout swipeRefreshLayout;
    ImageView notification;

    public static String identity = "antipolice104";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_admin);
        mNoResult = findViewById(R.id.tv_no_coupons);
        mDeviceList = findViewById(R.id.recycler_devices);
        notification = findViewById(R.id.iv_notification);

        notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminActivity.this, NotificationActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        LinearLayoutManager manager = new LinearLayoutManager(this);
        mDeviceList.setLayoutManager(manager);
        mAdapter = new DeviceRecyclerAdapter(this);
        mDeviceList.setAdapter(mAdapter);
        swipeRefreshLayout = findViewById(R.id.swipe_refresh);
        swipeRefreshLayout.setOnRefreshListener(this);

        mTitle = findViewById(R.id.tv_title);
        mTitle.setText("Device List");

        getDeviceData();

        if(AppSharedPreference.getInstance(this).getLastDateForMessage() == 0){
            AppSharedPreference.getInstance(this).setLastDateForMessage();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    protected void getDeviceData(){
        ProgressHelper.showDialog(this);
        mDeviceList.setVisibility(View.VISIBLE);
        mNoResult.setVisibility(View.INVISIBLE);
        ApiCall.getInstance().getList(identity, this);
    }

    @Override
    public void onSuccess(String type, Response response) {
        ProgressHelper.dismiss();
        swipeRefreshLayout.setRefreshing(false);
        if(type.equals("list")) {
            if (response.isSuccessful()) {
                if (((UserListRes) response.body()).getErrorCode().equals("0") && ((UserListRes) response.body()).getErrorMsg().equals("ok")) {
                    mAdapter.clear();
                    mAdapter.addAllItem(((UserListRes) response.body()).getData());
                    if (mAdapter.getItemCount() == 0) {
                        mNoResult.setVisibility(View.VISIBLE);
                    }else{
                        mNoResult.setVisibility(View.INVISIBLE);
                    }
                } else {
                    mNoResult.setVisibility(View.VISIBLE);
                }
            } else {
                mNoResult.setVisibility(View.VISIBLE);
            }
        }else if(type.equals("set_state")){
            if (response.isSuccessful()) {
                if (((BaseRes) response.body()).getErrorCode().equals("0") && ((BaseRes) response.body()).getErrorMsg().equals("ok")) {
                } else {
                    Toast.makeText(this, "Something Wrong.", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "Something Wrong.", Toast.LENGTH_SHORT).show();
            }
        }
    }


    @Override
    public void onFailure(Object data) {
        mNoResult.setVisibility(View.VISIBLE);
        Toast.makeText(this, "Something wrong.", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRecyclerClick(int pos, Object data, Object blockNumber, Object nickname) {
            int state = 0;
            if(data.toString().equals("Lock")){
                state = 1;
            }
            ProgressHelper.showDialog(this);
            ApiCall.getInstance().setState(mAdapter.getItem(pos).getDeviceId(), String.valueOf(state), blockNumber.toString(), nickname.toString(), this);
    }

    @Override
    public void onRefresh() {
        swipeRefreshLayout.setRefreshing(true);
        ApiCall.getInstance().getList(identity,this);
    }
}
