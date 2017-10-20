package dsm.alarmsms;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;

public class MainActivity extends AppCompatActivity {

    FirebaseMessagingService fms = new FirebaseMessagingService();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String token = FirebaseInstanceId.getInstance().getToken();
        Log.d("토큰", token);

//        Intent intent = getIntent();
//        String tels = intent.getExtras().getString("tels");
//        String text = intent.getExtras().getString("text");

//        Log.d("전달 tels", tels);
//        Log.d("전달 text", text);

//        String tels = fms.getTels();
//        String text = fms.getText();
//
//        Log.d("XXX",tels);
//        Log.d("XXX",text);
//        String tels = "01099457580";
//        String text = "제발되라";
//        sendSMS(tels, text);

    }

}
