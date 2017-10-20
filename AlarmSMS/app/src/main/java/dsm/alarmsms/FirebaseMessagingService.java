package dsm.alarmsms;

import android.app.Activity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.RingtoneManager;
import android.support.v4.app.NotificationCompat;
import android.telephony.SmsManager;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.messaging.RemoteMessage;

/**
 * Created by Sohyeon Park on 2017-09-05.
 * 푸시메세지가 들어왔을때 실제 사용자에게 푸시알림을 만들어서 띄워주는 클래스.
 * API를 통해 푸시 알림을 전송하면 입력한 내용이 message에 담겨서 오게 됨.
 */

public class FirebaseMessagingService extends com.google.firebase.messaging.FirebaseMessagingService {
    private static final String TAG = "FirebaseMsgService";
    // private String msg;
    String text, tels;

    // [START receive_message]
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Log.d(TAG, "From: " + remoteMessage.getFrom()); // 306850044248
        Log.i("textCheck", remoteMessage.getData().get("text"));
        Log.i("telsCheck", remoteMessage.getData().get("tels"));
        Log.i("allData", remoteMessage.getData().toString());

        text = remoteMessage.getData().get("text");
        tels = remoteMessage.getData().get("tels");

        Log.i("xxx", tels);
        Log.i("yyy", text);

        if (remoteMessage.getData().size() > 0) {
            Log.d(TAG, "Mesage data payload: " + remoteMessage.getData());
        }

/*        if (remoteMessage.getNotification() != null) {
            Log.d(TAG, "Mesage Notification Body: " + remoteMessage.getNotification());
        }*/

//        msg = remoteMessage.getNotification().getBody();

/*        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
                new Intent(this, MainActivity.class), 0);

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this).setSmallIcon(R.mipmap.ic_launcher)
                .setContentText(msg)
                .setAutoCancel(true)
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .setVibrate(new long[]{1, 1000});

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0*//* ID of notification *//*, mBuilder.build());

        mBuilder.setContentIntent(contentIntent);*/
        sendSMS(tels, text);
    }

    private void sendSMS(String tels, String text) {
        String SENT = "SMS SENT";
        String DELIVERED = "SMS_DELIVERED";

        PendingIntent sentPI = PendingIntent.getBroadcast(this, 0, new Intent(SENT), 0);
        PendingIntent deliveredPI = PendingIntent.getBroadcast(this, 0, new Intent(DELIVERED), 0);

        /** When the SMS has been sent  */
        registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                switch (getResultCode()) {
                    case Activity.RESULT_OK:
                        Toast.makeText(getBaseContext(), "알림 문자 메시지가 전송되었습니다.", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        }, new IntentFilter(SENT));

        SmsManager sms = SmsManager.getDefault();
        sms.sendTextMessage(tels, null, text, sentPI, deliveredPI);
    }
}