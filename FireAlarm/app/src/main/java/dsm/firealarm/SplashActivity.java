package dsm.firealarm;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class SplashActivity extends AppCompatActivity {
    Retrofit mretrofit;
    ApiService mApiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Log.d(this.getClass().getName(), "SplashActivity income!");

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mretrofit = new Retrofit.Builder().baseUrl(ApiService.API_URL).build();
                mApiService = mretrofit.create(ApiService.class);

                Call<ResponseBody> comment = mApiService.postComment("1:306850044248:android:0777117ce512bbb9");
                comment.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        try {
                            Log.i("클라이언트 ID 전송", response.body().string());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        if (response.code() == 201) {
                            /**회원이 아닐 경우*/
                            Intent intent = new Intent(getApplication(), LogInActivity.class);
                        } else if (response.code() == 202) {
                            /**회원일 경우, 자동로그인*/
                            /**쿠키 처리*/
                            Intent intent = new Intent(getApplication(), MainActivity.class);
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), "서버 오류가 발생하였습니다.", Toast.LENGTH_LONG);
                    }
                });

                finish();
            }
        }, 3000);
    }
}
