package dsm.firealarm;

import android.content.Intent;
import android.os.CountDownTimer;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TestActivity extends AppCompatActivity {
    private int count = 180;
    private Boolean check = false;
    private TextView module; private TextView time; private TextView intro;
    private Button btn;
    private CountDownTimer timer;
    private Retrofit retrofit;
    private testRetropit testRetropit;
    private Call<Void> call;
    private String token,code;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        findId();
        module.setText("현재 접속중인 모듈명 : "+code);
        retrofit = new Retrofit.Builder().baseUrl("https://daejava.herokuapp.com").build();
        testRetropit = retrofit.create(testRetropit.class);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                if (!check) {
                    timer = new CountDownTimer(180 * 1000, 1000) {
                        @Override
                        public void onTick(long millisUntilFinished) {
                            call = testRetropit.test(true, token);
                            call.enqueue(new Callback<Void>() {
                                @Override
                                public void onResponse(Call<Void> call, Response<Void> response) {
                                    int code = response.code();
                                    Log.d("Response", Integer.toString(code));
                                }
                                @Override
                                public void onFailure(Call<Void> call, Throwable t) {
                                    Toast.makeText(getApplicationContext(), "정보받아오기 실패", Toast.LENGTH_LONG).show();
                                }
                            });
                            String str = count / 60 + " : " + count % 60;
                            count--;
                            time.setText(str);
                            check = true;
                        }

                        @Override
                        public void onFinish() {
                            call = testRetropit.test(false, token);
                            call.enqueue(new Callback<Void>() {
                                @Override
                                public void onResponse(Call<Void> call, Response<Void> response) {
                                    time.setText("버튼을 눌러 활성화 해주세요");
                                }
                                @Override
                                public void onFailure(Call<Void> call, Throwable t) {
                                    Toast.makeText(getApplicationContext(), "정보받아오기 실패", Toast.LENGTH_LONG).show();
                                }
                            });
                            check = false;
                        }
                    }.start();
                } else {
                    call = testRetropit.test(false, token);
                    call.enqueue(new Callback<Void>() {
                        @Override
                        public void onResponse(Call<Void> call, Response<Void> response) {
                            time.setText("Time Stampe");
                        }
                        @Override
                        public void onFailure(Call<Void> call, Throwable t) {
                            Toast.makeText(getApplicationContext(), "정보받아오기 실패", Toast.LENGTH_LONG).show();
                        }
                    });
                    timer.cancel();
                    count = 180;
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
        intent = getIntent();
//        token = intent.getStringExtra("token");
        token ="dXeG1erpvVQ:APA91bEeSWm0CMszivoBA8hwgqqssg5aHtRESYgFO_mSiRuBKY-J6MCw2NaqdSshNTe2Dloah35Y-OrPPtSmAh_fsVmSHs0iN5Yyu7sqex2wZrDh1KIyQhTOx9n-6Kt8phSGXEfzS5Oa";
        code = intent.getStringExtra("module");


    }
}
