package dsm.firealarm;

import android.content.Intent;
import android.os.CountDownTimer;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class TestActivity extends AppCompatActivity {
    private int count = 180;
    private Boolean check = false;
    TextView module, time, intro;
    Button btn;
    CountDownTimer timer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        Intent intent = getIntent();
        findId();

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                if(!check){
                     timer =  new CountDownTimer(180 * 1000, 1000) {
                        @Override
                        public void onTick(long millisUntilFinished) {
                            String str = count / 60 + " : " + count % 60;
                            count--;
                            time.setText(str);
                            check = true;
                        }

                        @Override
                        public void onFinish() {
                            Snackbar.make(v,"서버전송",2000).show();
                            check = false;
                        }
                    }.start();
                }else{
                    SnackbarManager.createCancelableSnackbar(v,"서버전송",2000).show();
                    timer.cancel();
                    count = 180;
                    time.setText("Time Stampe");
                    check = false;
                }

            }
        });

    }

    private void findId() {
        module = (TextView) findViewById(R.id.module);
        time = (TextView) findViewById(R.id.time);
        intro = (TextView) findViewById(R.id.intro);
        btn = (Button) findViewById(R.id.btn);
    }
}
