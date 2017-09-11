package dsm.firealarm;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView record, test, message, settings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView record = (TextView) findViewById(R.id.record);
        record.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.test,0,0,0);
        record.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), RecordActivity.class);
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

        TextView settings = (TextView) findViewById(R.id.settings);
        settings.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.test,0,0,0);
        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SettingActivity.class);
                startActivityForResult(intent, 1000);
            }
        });
    }
}