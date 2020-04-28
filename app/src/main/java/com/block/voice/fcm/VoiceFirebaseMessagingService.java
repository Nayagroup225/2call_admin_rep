package com.block.voice.fcm;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.media.RingtoneManager;
import android.os.Build;
import android.util.Log;

import com.block.voice.R;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.twilio.voice.CallException;
import com.twilio.voice.CallInvite;
import com.twilio.voice.CancelledCallInvite;
import com.twilio.voice.MessageListener;
import com.twilio.voice.Voice;
import com.block.voice.Constants;
import com.block.voice.IncomingCallNotificationService;

public class VoiceFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "VoiceFCMService";

    @Override
    public void onCreate() {
        super.onCreate();
    }

    /**
     * Called when message is received.
     *
     * @param remoteMessage Object representing the message received from Firebase Cloud Messaging.
     */
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        Log.d(TAG, "Received onMessageReceived()");
        Log.d(TAG, "Bundle data: " + remoteMessage.getData());
        Log.d(TAG, "From: " + remoteMessage.getFrom());

        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            boolean valid = Voice.handleMessage(this, remoteMessage.getData(), new MessageListener() {
                @Override
                public void onCallInvite(@NonNull CallInvite callInvite) {
                    final int notificationId = (int) System.currentTimeMillis();
                    handleInvite(callInvite, notificationId);
                }

                @Override
                public void onCancelledCallInvite(@NonNull CancelledCallInvite cancelledCallInvite, @Nullable CallException callException) {
                    handleCanceledCallInvite(cancelledCallInvite);
                }
            });

            if (!valid) {
                String CHANNEL_ID = "twilio_01";
                // preferences = AppSharedPreferences.getInstance(getApplicationContext());
                //preferences.setNotificationCount(preferences.getNotificationCount()+1);
                NotificationChannel mChanal = null;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    mChanal = new NotificationChannel(CHANNEL_ID, "Notice", NotificationManager.IMPORTANCE_HIGH);
                }
                NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, CHANNEL_ID)
                        .setContentTitle(remoteMessage.getData().get("title"))
                        .setContentText(remoteMessage.getData().get("body"))
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                        .setStyle(new NotificationCompat.BigTextStyle())
                        .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                        .setSmallIcon(R.drawable.profile_call)
//                        .setSmallIcon(R.drawable.ic_notificarion_grey)
                        .setBadgeIconType(NotificationCompat.BADGE_ICON_SMALL)
//                        .setContentIntent(getPaddingInteng())
                        .setChannelId(CHANNEL_ID)
                        .setAutoCancel(true);

                NotificationManager notificationManager =
                        (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    notificationManager.createNotificationChannel(mChanal);
                }
                notificationManager.notify(0, notificationBuilder.build());
            }
        }
    }

//    private PendingIntent getPaddingInteng() {
//        PendingIntent intent = PendingIntent.getActivity(getApplicationContext(), 0,
//                new Intent(getApplicationContext(), NotificationActivity.class), 0);
//        return intent;
//    }

    @Override
    public void onNewToken(String token) {
        super.onNewToken(token);
        Intent intent = new Intent(Constants.ACTION_FCM_TOKEN);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }

    private void handleInvite(CallInvite callInvite, int notificationId) {
        Intent intent = new Intent(this, IncomingCallNotificationService.class);
        intent.setAction(Constants.ACTION_INCOMING_CALL);
        intent.putExtra(Constants.INCOMING_CALL_NOTIFICATION_ID, notificationId);
        intent.putExtra(Constants.INCOMING_CALL_INVITE, callInvite);

        startService(intent);
    }

    private void handleCanceledCallInvite(CancelledCallInvite cancelledCallInvite) {
        Intent intent = new Intent(this, IncomingCallNotificationService.class);
        intent.setAction(Constants.ACTION_CANCEL_CALL);
        intent.putExtra(Constants.CANCELLED_CALL_INVITE, cancelledCallInvite);

        startService(intent);
    }
}
