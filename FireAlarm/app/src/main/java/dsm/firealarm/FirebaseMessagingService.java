package dsm.firealarm;

import com.google.firebase.messaging.RemoteMessage;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.net.Uri;
import android.os.PowerManager;
import android.os.Vibrator;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

/**
 * Created by Sohyeon Park on 2017-09-05.
 * 푸시메세지가 들어왔을때 실제 사용자에게 푸시알림을 만들어서 띄워주는 클래스.
 * API를 통해 푸시 알림을 전송하면 입력한 내용이 message에 담겨서 오게 됨.
 */

public class FirebaseMessagingService extends com.google.firebase.messaging.FirebaseMessagingService {
    private static final String TAG = "FirebaseMsgService";

    private String msg;

    // [START receive_message]
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
//        Vibrator vibrator = (Vibrator)getSystemService(Context.VIBRATOR_SERVICE);
//        vibrator.vibrate(5000);

        Log.d(TAG, "From: " + remoteMessage.getFrom());

        if (remoteMessage.getData().size() > 0) {
            Log.d(TAG, "Mesage data payload: " + remoteMessage.getData());
        }

        if (remoteMessage.getNotification() != null) {
            Log.d(TAG, "Mesage Notification Body: " + remoteMessage.getNotification());
        }

        msg = remoteMessage.getNotification().getBody();
        Log.d("onMessageReceived: ", msg);

        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
/*
        PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
        PowerManager.WakeLock wakeLock = pm.newWakeLock(PowerManager.SCREEN_DIM_WAKE_LOCK
                | PowerManager.ACQUIRE_CAUSES_WAKEUP, "TAG");
        wakeLock.acquire(3000);*/

        PendingIntent contentIntent = PendingIntent.getActivity(this, 0 /*Request Code */, new Intent(this, MainActivity.class), 0);

        AudioManager audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        NotificationCompat.Builder mBuilder;
        switch (audioManager.getRingerMode()) {
            case AudioManager.RINGER_MODE_NORMAL:
            case AudioManager.RINGER_MODE_VIBRATE:
            case AudioManager.RINGER_MODE_SILENT:
                mBuilder = new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.splash)
                        .setContentTitle(msg)
                        .setAutoCancel(true)
                        .setSound(Uri.parse("android.resource://"
                                + this.getPackageName() + "/" + R.raw.siren));
                NotificationManager notificationManager =
                        (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                notificationManager.notify(0 /* ID of notification */, mBuilder.build());
                mBuilder.setContentIntent(contentIntent);
        }
    }
}
