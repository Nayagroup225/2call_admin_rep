package com.block.admin;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.block.admin.adapter.NotificationRecyclerAdapter;
import com.block.admin.model.NotificationInfoRes;
import com.block.admin.preferences.ProgressHelper;
import com.block.admin.retrofit.ApiCall;
import com.block.admin.retrofit.IApiCallback;

import retrofit2.Response;


public class NotificationActivity extends AppCompatActivity implements IApiCallback, SwipeRefreshLayout.OnRefreshListener {

    RecyclerView mNotificationList;
    NotificationRecyclerAdapter mAdapter;
    TextView mNoResult;
    SwipeRefreshLayout swipeRefreshLayout;
    private int PAGE_SIZE = 10, page = 1;
    private boolean isLoading, isLastPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_notification);
        mNoResult = findViewById(R.id.tv_no_notification);
        mNotificationList = findViewById(R.id.recycler_notifications);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        mNotificationList.setLayoutManager(manager);
        mAdapter = new NotificationRecyclerAdapter();
        mNotificationList.setAdapter(mAdapter);
        swipeRefreshLayout = findViewById(R.id.swipe_refresh);
        swipeRefreshLayout.setOnRefreshListener(this);

        setPageLimit(manager, mNotificationList);

    }

    private void setPageLimit(final LinearLayoutManager manager, RecyclerView recyclerView) {
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int visibleItemCount = manager.getChildCount();
                int totalItemCount = manager.getItemCount();
                int firstVisibleItemPosition = manager.findFirstVisibleItemPosition();
                if (!isLoading && !isLastPage) {
                    if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount && firstVisibleItemPosition >= 0 && totalItemCount >= PAGE_SIZE) {
                        page = page + 1;
                        getNotificationData();
                    }
                }
            }
        });
        getNotificationData();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    protected void getNotificationData(){
        ProgressHelper.showDialog(this);
        mNotificationList.setVisibility(View.VISIBLE);
        mNoResult.setVisibility(View.INVISIBLE);
        isLoading = true;
        ApiCall.getInstance().notificationList(String.valueOf(page), this);
    }

    @Override
    public void onSuccess(String type, Response response) {
        ProgressHelper.dismiss();
        swipeRefreshLayout.setRefreshing(false);
        if(type.equals("notification_list")) {
            if (response.isSuccessful()) {
                if (((NotificationInfoRes) response.body()).getErrorCode().equals("0")) {
                    mAdapter.clear();
                    mAdapter.addAllItem(((NotificationInfoRes) response.body()).getData());
                    if (page == 1)
                        mAdapter.clear();
                    mAdapter.addAllItem(((NotificationInfoRes) response.body()).getData());
                    isLastPage = ((NotificationInfoRes) response.body()).getData().size() != PAGE_SIZE;
                    setEmpty();
                } else {
                    mNoResult.setVisibility(View.VISIBLE);
                }
            } else {
                mNoResult.setVisibility(View.VISIBLE);
            }
        }
    }

    private void setEmpty() {
        mNoResult.setVisibility(mAdapter.getItemCount() > 0 ? View.GONE : View.VISIBLE);
    }


    @Override
    public void onFailure(Object data) {
        mNoResult.setVisibility(View.VISIBLE);
        Toast.makeText(this, "Something wrong.", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRefresh() {
        swipeRefreshLayout.setRefreshing(true);
        ApiCall.getInstance().notificationList(String.valueOf(page), this);
        page = 1;
    }
}
