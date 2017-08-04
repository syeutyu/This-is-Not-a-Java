package dsm.firealarm;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView history;
    private TextView test;
    private TextView message;
    private TextView settings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView history = (TextView) findViewById(R.id.history);
        history.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.test,0,0,0);
        history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), HistoryActivity.class);
                startActivityForResult(intent, 1000);
            }
        });

        TextView test = (TextView) findViewById(R.id.test);
        test.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.test,0,0,0);
        test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), TestActivity.class);
                startActivityForResult(intent, 1000);
            }
        });

        TextView message = (TextView) findViewById(R.id.message);
        message.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.test,0,0,0);
//        message.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(getApplicationContext(), Activity.class);
//                startActivityForResult(intent, 1000);
//            }
//        });

        TextView settings = (TextView) findViewById(R.id.settings);
        settings.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.test,0,0,0);
        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SettingActivity.class);
                startActivityForResult(intent, 1000);
            }
        });


//
//        btnTest = (Button) findViewById(R.id.btnTest);
//        btnTest.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(getApplicationContext(), TestActivity.class);
//                startActivityForResult(intent, 1000);
//            }
//        });

        // 관리자만 사용할 수 있는 기능 비활성화
/*
        btnSending = (Button) findViewById(R.id.btnSending);
        btnSending.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Sending.class 생성해야 함);
                startActivityForResult(intent, 1000);
            }
        });
*/

//        btnSettings = (Button) findViewById(R.id.btnSettings);
//        btnSettings.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(getApplicationContext(), SettingActivity.class);
//                startActivityForResult(intent, 1000);
//            }
//        });
    }
}